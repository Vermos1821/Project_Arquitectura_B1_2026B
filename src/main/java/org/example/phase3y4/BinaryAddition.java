package org.example.phase3y4;

/**
 * Realiza la suma de dos números enteros utilizando complemento a dos.
 * Permite elegir con representaciones de 8, 16 y 32 bits.
 */
public class BinaryAddition {

    /**
     * Suma dos números enteros usando la cantidad de bits que el usuario
     * ha seleccionado.
     *
     * @param firstNumber primer número que se desea sumar.
     * @param secondNumber segundo número que se desea sumar.
     * @param bits cantidad de bits que se usará (8, 16 o 32).
     */

    public void addNumbers(int firstNumber, int secondNumber, int bits) {
        // Creamos el codificador para reutilizar sus métodos de validación y conversión.
        TwosComplementEncoder twosComplementEncoder = new TwosComplementEncoder();

        // Comprobamos que la cantidad de bits sea válida
        if (!twosComplementEncoder.isValidBitSize(bits)) {
            System.out.println("ERROR: la cantidad de bits debe ser de 8, 16 o 32");
            return;
        }

        // Verificamos que ambos números puedan representarse con los bits que se seleccionó
        if(!twosComplementEncoder.isRepresentable(firstNumber, bits) ||
                !twosComplementEncoder.isRepresentable(secondNumber, bits)) {
            System.out.println("ERROR: uno o ambas entradas está fuera del rango permitido.");
            return;
        }

        // Convertimos ambos números a complemento a dos reutilizando el codificador.
        String firstBinary = twosComplementEncoder.encode(firstNumber, bits);
        String secondBinary = twosComplementEncoder.encode(secondNumber, bits);

        // Suma binaria de los dos operandos
        String binaryResult = addBinary(firstBinary, secondBinary);

        // Validación para saber si ocurrió un desbordamiento
        boolean overflow = hasOverflow(firstBinary, secondBinary, binaryResult);

        // Convertimos el resultado de binario a decimal
        long decimalResult = toDecimal(binaryResult);

        // Calculamos la suma matemática sin la necesidad de la cantidad de bits
        long mathematicalSum = (long) firstNumber + secondNumber;

        //se muestran los resultados de la operacion
        System.out.println("\n SUMA BINARIA ");
        System.out.println("Número de bits: " + bits);

        System.out.println("Primer número: " + firstNumber);
        System.out.println("Segundo número: " + secondNumber);

        System.out.println("\nPrimer operando:  " + firstBinary);
        System.out.println("Segundo operando: " + secondBinary);

        System.out.println("-------------------------------------");

        System.out.println("Resultado binario: " + binaryResult);
        System.out.println("Resultado decimal (" + bits + "-bit con signo): " + decimalResult);
        System.out.println("Suma matemática: " + mathematicalSum);
        System.out.println("Overflow: " + (overflow ? "YES" : "NO"));

    }

    /**
     * Suma dos cadenas binarias de igual longitud usando el acarreo.
     *
     * @param firstBinary primera cadena binaria.
     * @param secondBinary segunda cadena binaria.
     * @return resultado a binario con la misma cantidad de bits
     */
    private String addBinary(String firstBinary, String secondBinary) {

        int bitLength = firstBinary.length();
        char[] result = new char[bitLength];
        int carry = 0;

        // Suma de los bits desde la posición más significativa a la menos significativa
        for (int i = bitLength - 1; i >= 0; i--) {

            int firstBit = firstBinary.charAt(i) - '0';
            int secondBit = secondBinary.charAt(i) - '0';

            int bitSum = firstBit + secondBit + carry;

            result[i] = (char) ('0' + (bitSum % 2));

            carry = bitSum / 2;
        }

        // El acarreo final se descarta porque estamos trabajando con una longitud fija.
        return new String(result);
    }

    /**
     *
     * @param firstBinary representación del primer operando.
     * @param secondBinary representación del segundo operando.
     * @param binaryResult representación binaria del resultado.
     * @return true si es que existe overflow; false si no es caso.
     */
    private boolean hasOverflow(String firstBinary, String secondBinary, String binaryResult) {
        char firstSign = firstBinary.charAt(0);
        char secondSign = secondBinary.charAt(0);
        char resultSign = binaryResult.charAt(0);

        // Verifica si los dos operandos tienen el mismo signo
        return firstSign == secondSign && firstSign != resultSign;
    }

    /**
     *Convierte una representación en complemento a dos a decimal.
     *
     * @param binary cadena binaria la cual representa el resultado.
     * @return valor decimal con signo.
     */
    private long toDecimal(String binary) {
        boolean negative = binary.charAt(0) == '1';
        long decimalResult = 0;

        if (negative) {
            // En números negativos, se obtiene la magnitud a través del complemento a dos.
            String magnitudeBinary = addBinary(invertBits(binary), createOne(binary.length()));

            for (int i = 0; i < magnitudeBinary.length(); i++) {
                int bit = magnitudeBinary.charAt(i) - '0';
                decimalResult = decimalResult* 2 + bit;
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

    /**
     * Invierte los bits de una cadena binaria.
     *
     * @param binary cadena binaria original.
     * @return cadena con los bits invertidos.
     */
    private String invertBits(String binary) {
        StringBuilder inverted = new StringBuilder();

        for (int i = 0; i < binary.length(); i++) {
            inverted.append(binary.charAt(i) == '0' ? '1' : '0');
        }

        return inverted.toString();
    }

    /**
     * Creamos una cadena binaria la cual representa el número uno
     * con la longitud indicada.
     *
     * @param bitLength longitud de la cadena.
     * @return cadena binaria con el valor uno.
     */
    private String createOne(int bitLength) {
        return "0".repeat(bitLength - 1) + "1";
    }
}

