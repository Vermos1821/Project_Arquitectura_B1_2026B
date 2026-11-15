package org.example.phase3y4;

/**
 * Clase que se encarga de ejecutar la resta aritmética binaria de dos números enteros con signo
 * a través de codificación en Complemento a Dos.
 * <p>
 * Implementa la sustracción reduciéndola a una suma algebraica con el opuesto aditivo del
 * sustraendo ($A - B = A + (-B)$), donde $-B$ se obtiene aplicando el Complemento a Dos
 * sobre el segundo operando. Permite estructuras de palabra fija de 8, 16 y 32 bits,
 * asignando la codificación inicial a {@link TwosComplementEncoder} y agregando detección
 * de desbordamiento aritmético.
 * </p>
 */
public class BinarySubtraction {

    /**
     * Valida el minuendo y el sustraendo, los codifica en Complemento a Dos dependiendo de la longitud
     * de palabra especificada y ejecuta la resta binaria mediante la suma del minuendo con el
     * complemento a dos del sustraendo.
     * <p>
     * Muestra en consola los operandos binarios, el complemento a dos del sustraendo, el
     * resultado binario obtenido, su equivalente decimal con signo, la diferencia matemática
     * exacta y el indicador de desbordamiento.
     * </p>
     *
     * @param firstNumber Minuendo entero decimal con signo ($A$).
     * @param secondNumber Sustraendo entero decimal con signo ($B$).
     * @param bits Longitud de palabra en bits para la operación ({@code 8}, {@code 16} o {@code 32}).
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
     * Aplica el operador lógico NOT bit a bit (Complemento a Uno), invirtiendo cada
     * dígito binario de la cadena de entrada ({@code '0'} por {@code '1'} y viceversa).
     *
     * @param binary Cadena binaria original cuyos bits serán invertidos.
     * @return Nueva cadena binaria con todos los bits complementados.
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
     * Ejecuta el incremento binario en una unidad ({@code +1}) recorriendo la palabra desde
     * el bit menos significativo (LSB) hasta el más significativo (MSB) con propagación de acarreo.
     *
     * @param binary Cadena binaria en Complemento a Uno a la que se sumará {@code 1}.
     * @return Cadena binaria resultante tras completar la propagación del acarreo.
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
     * Determina si ocurrió un desbordamiento aritmético en la sustracción con signo.
     * <p>
     * En una resta $A - B$, el desbordamiento ocurre únicamente cuando el minuendo y el
     * sustraendo tienen signos opuestos y el signo del resultado es diferente del signo del minuendo
     * (restar un negativo a un positivo obteniendo un negativo, o restar un positivo a un
     * negativo obteniendo un positivo).
     * </p>
     *
     * @param firstBinary Representación binaria del minuendo ($A$).
     * @param secondBinary Representación binaria original del sustraendo ($B$).
     * @param binaryResult Representación binaria del resultado de la resta.
     * @return {@code true} si se detecta desbordamiento; {@code false} en caso contrario.
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
     * Ejecuta la suma binaria bit a bit de dos cadenas de igual longitud propagando el acarreo
     * desde el bit menos significativo (LSB) hacia el más significativo (MSB).
     * <p>
     * En estructura de palabra fija, el acarreo saliente final se descarta de manera automática.
     * </p>
     *
     * @param firstBinary Cadena binaria del primer operando (minuendo).
     * @param secondBinary Cadena binaria del segundo operando (sustraendo complementado).
     * @return Cadena binaria resultante con la misma longitud de palabra que los operandos.
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
     * Decodifica una cadena binaria codificada en Complemento a Dos hacia su valor
     * decimal con signo.
     * <p>
     * Si el bit más significativo (MSB) es {@code '1'}, aplica el proceso inverso del
     * Complemento a Dos a través de {@link #invertBits(String)} y {@link #addOne(String)}
     * para recuperar la magnitud negativa. Si es {@code '0'}, realiza la conversión
     * posicional directa.
     * </p>
     *
     * @param binary Cadena binaria en Complemento a Dos a decodificar.
     * @return Valor decimal que equivale al tipo {@code long}.
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