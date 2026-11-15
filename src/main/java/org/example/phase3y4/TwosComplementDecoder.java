package org.example.phase3y4;

/**
 * Clase encargada de verificar y decodificar una representación binaria en Complemento a Dos
 * a su valor decimal original. Permite trabajar con cantidades de 8, 16 y 32 bits.
 */
public class TwosComplementDecoder {

    /**
     * Comprueba si la cantidad de bits seleccionada es válida.
     *
     * @param bits cantidad de bits que se desea utilizar.
     * @return true si la cantidad es 8, 16 o 32; caso contrario, false.
     */
    public boolean isValidBitSize(int bits) {
        return bits == 8 || bits == 16 || bits == 32;
    }

    /**
     * Invierte los bits de una cadena binaria. Cada cero se convierte en uno y cada uno en cero.
     *
     * @param binary cadena binaria cuyos bits se desean invertir.
     * @return nueva cadena binaria con los bits invertidos.
     */
    private String invertBits(String binary) {
        StringBuilder inverted = new StringBuilder();
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            if (bit == '0') {
                inverted.append('1');
            } else if (bit == '1') {
                inverted.append('0');
            } else {
                throw new IllegalArgumentException("Caracter binario inválido: " + bit);
            }
        }
        return inverted.toString();
    }

    /**
     * Suma uno a una cadena binaria utilizando el acarreo. Recorre los bits desde la derecha hacia la izquierda.
     *
     * @param binary cadena binaria a la que se sumará uno.
     * @return cadena binaria con el resultado de la suma.
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
     * Decodifica una cadena binaria en Complemento a Dos a su valor decimal original.
     *
     * @param binaryStr cadena binaria que se desea decodificar.
     * @param bits cantidad de bits con la que fue representada (8, 16 o 32).
     * @return el número entero decimal recuperado, o lanza un error si los datos no son válidos.
     */
    public long decode(String binaryStr, int bits) {
        if (!isValidBitSize(bits)) {
            throw new IllegalArgumentException("ERROR: la cantidad de bits debe ser 8, 16 o 32.");
        }
        if (binaryStr == null || binaryStr.length() != bits) {
            throw new IllegalArgumentException("ERROR: la cadena binaria no coincide con la cantidad de bits especificada.");
        }

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