package org.example.phase3y4;

/**
 * Realiza la resta de dos números enteros utilizando complemento a dos.
 * Permite elegir con representaciones de 8, 16 y 32 bits.
 */
public class BinarySubtraction {

    /**
     * Resta dos números enteros usando la cantidad de bits seleccionada.
     *
     * @param firstNumber primer número (minuendo).
     * @param secondNumber segundo número (sustraendo).
     * @param bits cantidad de bits que se usará (8, 16 o 32).
     */
    public void subtractNumbers(int firstNumber, int secondNumber, int bits) {
        // Creamos el codificador para reutilizar sus métodos de validación y conversión.
        TwosComplementEncoder twosComplementEncoder = new TwosComplementEncoder();

        // Comprobamos que la cantidad de bits sea válida
        if (!twosComplementEncoder.isValidBitSize(bits)) {
            System.out.println("ERROR: la cantidad de bits debe ser de 8, 16 o 32");
            return;
        }

        // Verificamos que ambos números puedan representarse con los bits que se seleccionó
        if (!twosComplementEncoder.isRepresentable(firstNumber, bits) ||
                !twosComplementEncoder.isRepresentable(secondNumber, bits)) {
            System.out.println("ERROR: uno o ambas entradas está fuera del rango permitido.");
            return;
        }

        // Convertimos ambos números a complemento a dos reutilizando el codificador.
        String firstBinary = twosComplementEncoder.encode(firstNumber, bits);
        String secondBinary = twosComplementEncoder.encode(secondNumber, bits);

        // Obtenemos el complemento a dos del segundo operando (invertir bits y sumar uno)
        String secondComplement = addOne(invertBits(secondBinary));

        // Suma del primer operando con el complemento del segundo (A - B = A + (-B))
        String binaryResult = addBinary(firstBinary, secondComplement);

        // Validación para saber si ocurrió un desbordamiento
        boolean overflow = hasOverflow(firstBinary, secondBinary, binaryResult);

        // Convertimos el resultado de binario a decimal
        long decimalResult = toDecimal(binaryResult);

        // Calculamos la resta matemática sin la necesidad de la cantidad de bits
        long mathematicalDifference = (long) firstNumber - secondNumber;

        // Se muestran los resultados de la operación
        System.out.println("\n RESTA BINARIA ");
        System.out.println("Número de bits: " + bits);

        System.out.println("Primer número: " + firstNumber);
        System.out.println("Segundo número: " + secondNumber);

        System.out.println("\nPrimer operando:  " + firstBinary);
        System.out.println("Segundo operando: " + secondBinary);
        System.out.println("Complemento a dos del segundo operando: " + secondComplement);

        System.out.println("-------------------------------------");

        System.out.println("Resultado binario: " + binaryResult);
        System.out.println("Resultado decimal (" + bits + "-bit con signo): " + decimalResult);
        System.out.println("Resta matemática: " + mathematicalDifference);
        System.out.println("Overflow: " + (overflow ? "YES" : "NO"));
    }

    /**
     * Invierte los bits de una cadena binaria.
     *
     * @param binary cadena binaria original.
     * @return cadena con los bits invertidos.
     */
    private String invertBits(String binary) {
        String inverted = "";

        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                inverted = inverted + "1";
            } else {
                inverted = inverted + "0";
            }
        }

        return inverted;
    }

    /**
     * Suma uno a una cadena binaria usando el acarreo, de derecha a izquierda.
     *
     * @param binary cadena binaria a la que se sumará uno.
     * @return cadena binaria con el resultado.
     */
    private String addOne(String binary) {
        String result = "";
        int carry = 1;

        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);

            if (bit == '1' && carry == 1) {
                result = "0" + result;
            } else if (bit == '0' && carry == 1) {
                result = "1" + result;
                carry = 0;
            } else {
                result = bit + result;
            }
        }

        return result;
    }

    /**
     * Suma dos cadenas binarias de igual longitud usando el acarreo.
     *
     * @param firstBinary primera cadena binaria.
     * @param secondBinary segunda cadena binaria.
     * @return resultado en binario con la misma cantidad de bits.
     */
    private String addBinary(String firstBinary, String secondBinary) {
        int bitLength = firstBinary.length();
        char[] result = new char[bitLength];
        int carry = 0;

        // Suma de los bits desde la posición menos significativa a la más significativa
        for (int i = bitLength - 1; i >= 0; i--) {
            int firstBit = firstBinary.charAt(i) - '0';
            int secondBit = secondBinary.charAt(i) - '0';

            int bitSum = firstBit + secondBit + carry;

            result[i] = (char) ('0' + (bitSum % 2));

            carry = bitSum / 2;
        }

        return new String(result);
    }

    /**
     * Determina si la resta produjo desbordamiento.
     *
     * @param firstBinary representación del primer operando.
     * @param secondBinary representación del segundo operando.
     * @param binaryResult representación binaria del resultado.
     * @return true si existe overflow; false en caso contrario.
     */
    private boolean hasOverflow(String firstBinary, String secondBinary, String binaryResult) {
        char firstSign = firstBinary.charAt(0);
        char secondSign = secondBinary.charAt(0);
        char resultSign = binaryResult.charAt(0);

        // En la resta hay overflow si los operandos tienen signos distintos
        // y el signo del resultado es diferente al del primer operando
        return firstSign != secondSign && firstSign != resultSign;
    }

    /**
     * Convierte una representación en complemento a dos a decimal.
     *
     * @param binary cadena binaria que representa el resultado.
     * @return valor decimal con signo.
     */
    private long toDecimal(String binary) {
        boolean negative = binary.charAt(0) == '1';
        long decimalResult = 0;

        if (negative) {
            // En números negativos, se obtiene la magnitud a través del complemento a dos.
            String magnitudeBinary = addOne(invertBits(binary));

            for (int i = 0; i < magnitudeBinary.length(); i++) {
                int bit = magnitudeBinary.charAt(i) - '0';
                decimalResult = decimalResult * 2 + bit;
            }

            return -decimalResult;
        }

        // Convierte de manera directa los números positivos.
        for (int i = 0; i < binary.length(); i++) {
            int bit = binary.charAt(i) - '0';
            decimalResult = decimalResult * 2 + bit;
        }

        return decimalResult;
    }
}