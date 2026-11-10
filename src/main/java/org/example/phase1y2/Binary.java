package org.example.phase1y2;

/**
 * Clase que implementa la interfaz {@link BaseConverter} para la gestión
 * de la conversión en ambas direcciones entre el sistema decimal y
 * el sistema binario (base 2).
 */
public class Binary implements BaseConverter {
    /**
     * Base numérica usada para las conversiones (Binario = 2).
     */
    private final int base = 2;

    /**
     * Convertimos un número decimal positivo a su representación en binario.
     * Usa el método de divisiones sucesivas entre 2.
     *
     * @param numberDecimal El número decimal (base 10) a convertir.
     * @return Una cadena de texto ({@code String}) que representa el número en sistema binario.
     */
    @Override
    public String convertFromDecimal(long numberDecimal) {
        if (numberDecimal == 0) {
            return "0";
        }

        String result = "";

        while (numberDecimal > 0) {
            long remainder = numberDecimal % base;

            result = remainder + result;

            numberDecimal = numberDecimal / base;
        }
        return result;
    }

    /**
     * Convertimos una cadena de texto el cual representa un número binario a su valor decimal.
     * Incluye validación de overflow para estar seguros qué el resultado
     * no exceda el límite máximo que soporta por el tipo de dato {@code long}
     *
     * @param numberInBase El número binario representado como cadena de texto.
     * @return El valor que equivale en sistema decimal (base 10) como {@code long}.
     * @throws ArithmeticException Si el número convertido se excede del límite máximo de {@code Long.MAX_VALUE}.
     */
    @Override
    public long convertToDecimal(String numberInBase) {
        long decimalResult = 0;
        long powerValue = 1;
        boolean powerOverflow = false;

        for (int i = numberInBase.length() - 1; i >= 0; i--) {
            char character = numberInBase.charAt(i);

            int digit = Character.getNumericValue(character);

            if (digit > 0) {
                if (powerOverflow || powerValue > Long.MAX_VALUE / digit) {
                throw new ArithmeticException("El número excede el límite de long.");
            }

            long positionValue = digit * powerValue;

            if (decimalResult > Long.MAX_VALUE - positionValue) {
                throw new ArithmeticException("El número excede el límite de long.");
                }

                decimalResult += positionValue;
            }

            long positionValue = digit * powerValue;

            if (i > 0 && !powerOverflow) {
                if (powerValue > Long.MAX_VALUE / base) {
                    powerOverflow = true;
                } else {
                    powerValue = powerValue * base;
                }
            }
        }
        return decimalResult;
    }
}