package org.example.phase3y4;

/**
 * Clase encargada de convertir números decimales enteros a su representación binaria usando complemento a dos. Permite
 * trabajar con cantidades de 8, 16 y 32 bits.
 */
public class TwosComplementEncoder {
    /**
     * Comprueba si la cantidad de bits seleccionada es válida.
     *
     * @param bits cantidad de bits que se desea utilizar.
     * @return true si la cantidad es 8, 16 o 32; caso contrario, false.
     */
    public boolean isValidBitSize(int bits) {
        return bits == 8 || bits == 16 || bits == 32;
    }

    /** Comprueba si un número decimal se encuentra dentro del rango permitido para la cantidad de bits seleccionada.
     *
     * @param number número decimal que se desea representar.
     * @param bits cantidad de bits que se utilizará.
     * @return true si el número está dentro del rango permitido; caso contrario, false.
     */
    public boolean isRepresentable(long number, int bits) {
        switch (bits) {
            case 8:
                return number >= -128 && number <= 127;
            case 16:
                return number >= -32768 && number <= 32767;
            case 32:
                return number >= -2147483648L && number <= 2147483647L;
            default:
                return false;
        }
    }

    /** Convierte un número decimal positivo a su representación binaria. Completa la cadena con ceros a la izquierda
     * hasta alcanzar la cantidad de bits indicada.
     *
     * @param number número decimal positivo que se desea convertir.
     * @param bits cantidad de bits que debe tener el resultado.
     * @return cadena con la representación binaria del número.
     */
    private String decimalToBinary(long number, int bits) {
        String binary = "";

        if (number == 0) {
            binary = "0";
        }

        while (number > 0) {
            int remainder = (int) (number % 2);
            binary = remainder + binary;
            number = number / 2;
        }

        while (binary.length() < bits) {
            binary = "0" + binary;
        }

        return binary;
    }

    /**
     * Invierte los bits de una cadena binaria. Cada cero se convierte en uno y cada uno en cero.
     *
     * @param binary cadena binaria cuyos bits se desean invertir.
     * @return nueva cadena binaria con los bits invertidos.
     */
    private String invertBits(String binary){
        String inverted = "";
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            if (bit == '0') {
                inverted = inverted + "1";
            } else {
                inverted = inverted + "0";
            }
        }
        return inverted;
    }

    /**
     * Suma uno a una cadena binaria utilizando el acarreo. Recorre los bits desde la derecha hacia la izquierda.
     *
     * @param binary cadena binaria a la que se sumará uno.
     * @return cadena binaria con el resultado de la suma.
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
     * Convierte un número decimal a su representación en complemento a dos. Primero se verifica que la cantidad de bits
     * sea válida y que el número se encuentre dentro del rango permitido. Para números negativos, se obtiene su valor
     * absoluto, se invierte los bits y se suma uno.
     *
     * @param number número decimal que se desea convertir.
     * @param bits cantidad de bits para representar el número (8, 16 o 32).
     * @return cadena binaria que representa el número en complemento a dos, o un mensaje de error si los datos no son
     * válidos.
     */
    public String encode(long number, int bits) {
        if (!isValidBitSize(bits)) {
            return "ERROR: la cantidad de bits debe ser 8, 16 o 32.";
        }
        if (!isRepresentable(number, bits)) {
            return "ERROR: el numero esta fuera del rango permitido.";
        }

        if (number >= 0) {
            return decimalToBinary(number, bits);
        }

        long absolute = -number;
        String binary = decimalToBinary(absolute, bits);
        String ones = invertBits(binary);
        return addOne(ones);
    }
}
