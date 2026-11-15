package org.example.phase3y4;

/**
 * Clase que se encarga de convertir números decimales enteros con signos a su representación
 * binaria en formato de Complemento a Dos.
 * <p>
 * Permite longitudes de palabra estándar de 8, 16 y 32 bits, el cual valida de manera previa
 * que el valor decimal se encuentre dentro del rango representable $[-2^{n-1}, 2^{n-1} - 1]$
 * que corresponde a la estructura seleccionada.
 * </p>
 */
public class TwosComplementEncoder {
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
     * Comprueba si un número decimal está ubicado dentro del dominio representable
     * para una longitud de palabra específica en Complemento a Dos:
     * <ul>
     *     <li><b>8 bits:</b> $[-128, 127]$</li>
     *     <li><b>16 bits:</b> $[-32768, 32767]$</li>
     *     <li><b>32 bits:</b> $[-2147483648, 2147483647]$</li>
     * </ul>
     *
     * @param number Valor entero decimal con signo a evaluar.
     * @param bits Longitud de palabra en bits ({@code 8}, {@code 16} o {@code 32}).
     * @return {@code true} si el número cabe dentro del rango permitido para dicha estructura;
     *         {@code false} si provoca desbordamiento (overflow) o si los bits son inválidos.
     */
    public boolean isRepresentable(long number, int bits) {
        switch (bits) {
            case 8 -> {
                return number >= -128 && number <= 127;
            }
            case 16 -> {
                return number >= -32768 && number <= 32767;
            }
            case 32 -> {
                return number >= -2147483648L && number <= 2147483647L;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Transforma un decimal no negativo a su representación binaria pura a través
     * divisiones sucesivas entre 2, aplicando relleno de ceros a la izquierda hasta completar
     * la longitud de palabra indicada.
     *
     * @param number Número entero decimal mayor o igual a cero.
     * @param bits Longitud fija en bits que debe tener la cadena resultante.
     * @return Cadena binaria de longitud {@code bits} correspondiente a la magnitud del número.
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
     * Invierte los bits de una cadena binaria.
     * Cada cero se convierte en uno y cada uno en cero.
     *
     * @param binary Cadena binaria cuyos bits se desean invertir.
     * @return Nueva cadena binaria con los bits invertidos.
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
     * Suma uno a una cadena binaria utilizando el acarreo.
     * Recorre los bits desde la derecha hacia la izquierda.
     *
     * @param binary CXadena binaria a la que se sumará uno.
     * @return Cadena binaria con el resultado de la suma.
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
     * Convierte un número decimal a su representación binaria en Complemento a Dos.
     * <p>
     * Para valores positivos o cero, retorna de manera directa la magnitud binaria con extensión de ceros.
     * Para valores negativos, obtiene la magnitud absoluta en binario, aplica el Complemento a Uno
     * a través de {@link #invertBits(String)} y suma una unidad con {@link #addOne(String)}.
     * </p>
     *
     * @param number Número decimal con signo que se desea convertir.
     * @param bits Longitud de palabra deseada ({@code 8}, {@code 16} o {@code 32} bits).
     * @return Cadena binaria que representa el número en Complemento a Dos, o un mensaje descriptivo con prefijo
     *         {@code "ERROR:"} si la estructura de bits o el rango numérico no son válidos.
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
