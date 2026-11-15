package org.example.phase3y4;

/**
 * Clase encargada de verificar y decodificar una representación binaria en Complemento a Dos
 * a su valor decimal original. Permite trabajar con cantidades de 8, 16 y 32 bits.
 */
public class TwosComplementDecoder {

    /**
     * Comprueba si la longitud de palabra en bits especificada es permitida por el codificador.
     *
     * @param bits Cantidad de bits que se desea utilizar.
     * @return {@code true} si la longitud es {@code 8}, {@code 16} o {@code 32};
     *         {@code false} en caso contrario.
     */
    public boolean isValidBitSize(int bits) {
        return bits == 8 || bits == 16 || bits == 32;
    }

    /**
     * Valida que la cadena esté compuesta de manera exclusiva por dígitos binarios ({@code '0'} y {@code '1'}).
     *
     * @param binary Cadena de caracteres a verificar.
     * @throws IllegalArgumentException Si se detecta un carácter distinto de {@code '0'} o {@code '1'}.
     */
    private void validateBinaryCharacters(String binary){
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);

            if (bit != '0' && bit != '1') {
                throw new IllegalArgumentException("ERROR: carácter binario inválido detectado ('" + bit + "').");
            }

        }

    }

    /**
     * Invierte los bits de una cadena binaria.
     * Cada cero se convierte en uno y cada uno en cero.
     *
     * @param binary Cadena binaria cuyos bits se desean invertir.
     * @return Nueva cadena binaria con los bits invertidos.
     */
    private String invertBits(String binary) {
        StringBuilder inverted = new StringBuilder();
        for (int i = 0; i < binary.length(); i++) {
            inverted.append(binary.charAt(i) ==  '0' ? '1' : '0');

        }

        return inverted.toString();
    }

    /**
     * Suma uno a una cadena binaria utilizando el acarreo.
     * Recorre los bits desde la derecha hacia la izquierda.
     *
     * @param binary Cadena binaria a la que se sumará uno.
     * @return Cadena binaria con el resultado de la suma.
     */
    private String addOne(String binary) {
        StringBuilder result = new StringBuilder();
        int carry = 1;

        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);

            if (bit == '1' && carry == 1) {
                result.insert(0, '0');

            } else if (bit == '0' && carry == 1) {
                result.insert(0, '1');
                carry = 0;

            } else {
                result.insert(0, bit);

            }

        }

        return result.toString();
    }

    /**
     * Decodifica una cadena binaria codificada en Complemento a Dos a su valor decimal con signo.
     * Evalúa el bit más significativo (MSB) para definir ell signo y aplica el algoritmo inverso si es negativo.
     *
     * @param binaryStr Cadena binaria que se desea decodificar.
     * @param bits Cantidad de bits con la que fue representada (8, 16 o 32).
     * @return El número entero decimal decodificado de tipo {@code long}.
     * @throws IllegalArgumentException Si la longitud de bits no es válida, la longitud de la cadena no coincide
     *                                  con los bits especificados o existen caracteres no binarios.
     */
    public long decode(String binaryStr, int bits) {
        if (!isValidBitSize(bits)) {
            throw new IllegalArgumentException("ERROR: la cantidad de bits debe ser 8, 16 o 32.");

        }

        if (binaryStr == null || binaryStr.length() != bits) {
            throw new IllegalArgumentException("ERROR: la cadena binaria no coincide (" +
                                                (binaryStr == null ? 0 : binaryStr.length()) + ") no coincide con los " +
                                                bits + " bits especificados.");

        }

        validateBinaryCharacters(binaryStr);

        // Identificar si el primer bit es '0' (positivo) o '1' (negativo)
        char firstBit = binaryStr.charAt(0);

        if (firstBit == '0') {
            // Si es positivo, convertir directamente a decimal
            return Long.parseLong(binaryStr, 2);

        } else {
            // Si es negativo (proceso inverso del complemento a dos):
            // 1. Invertir los bits
            String inverted = invertBits(binaryStr);
            // 2. Sumar uno
            String originalMagBinary = addOne(inverted);
            // 3. Convertir a decimal y aplicar el signo negativo
            long magnitude = Long.parseLong(originalMagBinary, 2);
            return -magnitude;
        }

    }

}