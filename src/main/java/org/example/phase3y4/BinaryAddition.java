package org.example.phase3y4;

/**
 * Clase que se encarga de ejecutar la suma aritmética binaria de dos números enteros con signo
 * utilizando codificación en Complemento a Dos.
 * <p>
 * Permite estructuras de palabra fija de 8, 16 y 32 bits, asignando la codificación
 * inicial a {@link TwosComplementEncoder} e incluyendo detección formal de desbordamiento
 * aritmético a través del análisis de los bits de signo (MSB).
 * </p>
 */
public class BinaryAddition {

    /**
     * Valida los operandos de entrada, los codifica en Complemento a Dos dependiendo de la longitud
     * de palabra especificada y ejecuta la suma binaria bit a bit, desplazando en consola
     * los operandos binarios, el resultado codificado, su equivalente decimal con signo,
     * la suma matemática exacta y el indicador de desbordamiento (overflow).
     *
     * @param firstNumber Primer operando decimal con signo.
     * @param secondNumber Segundo operando decimal con signo.
     * @param bits Longitud de palabra en bits para la operación ({@code 8}, {@code 16} o {@code 32}).
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
     * Ejecuta la suma binaria bit a bit de dos cadenas de igual longitud propagando el acarreo
     * desde el bit menos significativo (LSB) hasta el más significativo (MSB).
     * <p>
     * En aritmética de longitud fija en Complemento a Dos, cualquier acarreo saliente tras
     * procesar el MSB se descarta automáticamente.
     * </p>
     *
     * @param firstBinary Cadena binaria del primer operando.
     * @param secondBinary Cadena binaria del segundo operando.
     * @return Cadena binaria resultante con la misma longitud de palabra que los operandos.
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
     * Determina si ocurrió un desbordamiento aritmético en la suma con signo.
     * <p>
     * El desbordamiento se ocurre únicamente cuando ambos operandos comparten el mismo
     * bit de signo (MSB) y el resultado que se obtiene presenta el bit de signo opuesto
     * (suma de dos positivos que produce un negativo, o suma de dos negativos que produce un positivo).
     * </p>
     *
     * @param firstBinary Representación binaria del primer operando.
     * @param secondBinary Representación binaria del segundo operando.
     * @param binaryResult Representación binaria resultante de la suma.
     * @return {@code true} si se detecta desbordamiento; {@code false} en caso contrario.
     */
    private boolean hasOverflow(String firstBinary, String secondBinary, String binaryResult) {
        char firstSign = firstBinary.charAt(0);
        char secondSign = secondBinary.charAt(0);
        char resultSign = binaryResult.charAt(0);

        // Verifica si los dos operandos tienen el mismo signo
        return firstSign == secondSign && firstSign != resultSign;
    }

    /**
     * Decodifica una cadena binaria codificada en Complemento a Dos hacia su valor
     * decimal con signo.
     * <p>
     * Si el bit más significativo (MSB) es {@code '1'}, aplica el proceso inverso del
     * Complemento a Dos (inversión de bits a través de {@link #invertBits(String)} y suma de
     * {@code 1}) para recuperar la magnitud negativa. Si es {@code '0'}, realiza la
     * evaluación polinómica directa.
     * </p>
     *
     * @param binary Cadena binaria en Complemento a Dos a decodificar.
     * @return Valor decimal equivalente de tipo {@code long}.
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
     * Aplica la operación lógica NOT bit a bit (Complemento a Uno), invirtiendo cada
     * dígito binario de la cadena de entrada ({@code '0'} por {@code '1'} y viceversa).
     *
     * @param binary Cadena binaria original cuyos bits serán invertidos.
     * @return Nueva cadena binaria con los bits complementados.
     */
    private String invertBits(String binary) {
        StringBuilder inverted = new StringBuilder();

        for (int i = 0; i < binary.length(); i++) {
            inverted.append(binary.charAt(i) == '0' ? '1' : '0');
        }

        return inverted.toString();
    }

    /**
     * Genera una palabra binaria de longitud fija el cual representa la constante entera {@code 1}
     * rellenada con ceros a la izquierda.
     *
     * @param bitLength Longitud total en bits de la palabra binaria a construir.
     * @return Cadena binaria compuesta por {@code bitLength - 1} ceros seguidos de un {@code '1'}.
     */
    private String createOne(int bitLength) {
        return "0".repeat(bitLength - 1) + "1";
    }
}

