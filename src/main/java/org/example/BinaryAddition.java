package org.example;

public class BinaryAddition {

    //definir la cantidad de bits que va hacer utilizada en las operaciones
    //valor minimo que utiliza el signo: 8 bits
    private static final int BIT_LENGTH = 8;

    //valor maximo para representar utilizano los 8 bits
    private static final int MIN_VALUE = -128;
    //complemento a dos y muestra los resultados de la suma de dos numeros enteros
    private static final int MAX_VALUE = 127;

    public void addNumbers(int firstNumber, int secondNumber) {
        //verifica que ambos numeros esten en el rango correspondiente
        if (firstNumber < MIN_VALUE || firstNumber > MAX_VALUE ||
                secondNumber < MIN_VALUE || secondNumber > MAX_VALUE) {

            System.out.println("Error: Numbers must be between -128 and 127.");
            return;
        }

        //convertir ambos numero en su expresion binaria con complemento a dos
        String firstBinary = toTwosComplement(firstNumber);
        String secondBinary = toTwosComplement(secondNumber);

        //suma de los dos operandos
        String binaryResult = addBinary(firstBinary, secondBinary);

        //validacion para saber si ocurrio un desbordamiento
        boolean overflow = hasOverflow(
                firstBinary, secondBinary, binaryResult
        );

        //convertir el resultado de binario a decimal
        int decimalResult = toDecimal(binaryResult);

        //calcular la suma de los dos numeros originales
        int mathematicalSum = firstNumber + secondNumber;

        //se muestran los resultados de la operacion
        System.out.println("\n BINARY ADDITION ");

        System.out.println("First number: " + firstNumber);
        System.out.println("Second number: " + secondNumber);

        System.out.println("\nFirst operand:  " + firstBinary);
        System.out.println("Second operand: " + secondBinary);

        System.out.println("-------------------------------------");

        System.out.println("Binary result: " + binaryResult);
        System.out.println("Decimal result (8-bit signed): " + decimalResult);
        System.out.println("Mathematical sum: " + mathematicalSum);
        System.out.println("Overflow: " + (overflow ? "YES" : "NO"));

    }

    //convierte un numero entero en su representacion binaria utilizando a dos
    private String toTwosComplement(int number) {

        //si dicho numero es positivo o cero, se comvierte a binario
        if (number >= 0) {
            return toBinary(number);
        }

        String binary = toBinary(Math.abs(number));
        //invierte todos los bits para obtener el complemneto a 1
        String invertedBinary = invertBits(binary);
        //sumar uno al complemento a 1 para que sea el complemento a 2
        return addBinary(invertedBinary, "00000001");
    }

    private String toBinary(int number) {

        String binary = "";

        //conversion de un numero decimal a binario mediante divisiones sucesivas
        do {
            int remainder = number % 2;

            binary = remainder + binary;
            number = number / 2;

        } while (number > 0);

        //agregar ceros a la izquierda para completar los 8 bits
        while (binary.length() < BIT_LENGTH) {
            binary = "0" + binary;
        }

        return binary;
    }

    private String invertBits(String binary) {

        String invertedBinary = "";

        //recorre la cadena y cambia cada bit su valor contrario
        for (int i = 0; i < binary.length(); i++) {

            if (binary.charAt(i) == '0') {
                invertedBinary += "1";
            } else {
                invertedBinary += "0";
            }
        }

        return invertedBinary;
    }

    private String addBinary(String firstBinary, String secondBinary) {

        char[] result = new char[BIT_LENGTH];

        int carry = 0;

        //suma de los bits desde la posicion mas significativa a la menos significativa
        for (int i = BIT_LENGTH - 1; i >= 0; i--) {

            int firstBit = firstBinary.charAt(i) - '0';
            int secondBit = secondBinary.charAt(i) - '0';

            //suma los dos bits junto con el acarreo anterior
            int bitSum = firstBit + secondBit + carry;

            //Guarda el bit correspondiente al resultado
            result[i] = (char) ('0' + (bitSum % 2));

            //calcula el acarreo para la siguiente posicion.
            carry = bitSum / 2;
        }

        return new String(result);
    }

    private boolean hasOverflow(
            String firstBinary,
            String secondBinary,
            String binaryResult) {

        //se obtiene el bit de signo de cada operando y del resultado
        char firstSign = firstBinary.charAt(0);
        char secondSign = secondBinary.charAt(0);
        char resultSign = binaryResult.charAt(0);

        //verifica si los dos operandos tienen el mismo signo
        return firstSign == secondSign &&
                firstSign != resultSign;
    }

    private int toDecimal(String binary) {

        //comprobar que el bit mas significativo indica un numero negativo
        boolean negative = binary.charAt(0) == '1';

        String magnitudeBinary;

        if (negative) {
            //invierte los bits y suma uno para obtener la magnitud
            magnitudeBinary = addBinary(
                    invertBits(binary), "00000001"
            );
        } else {
            //si el numero es positivo conserva su representacion binaria
            magnitudeBinary = binary;
        }

        int decimalResult = 0;

        //convierte la magnitud binaria a decimal por medio de multiplicaciones sucesivas
        for (int i = 0; i < magnitudeBinary.length(); i++) {

            int bit = magnitudeBinary.charAt(i) - '0';

            decimalResult = decimalResult * 2 + bit;
        }

        //proporciona el resultado con el signo correspondiente
        return negative ? -decimalResult : decimalResult;
    }
}

