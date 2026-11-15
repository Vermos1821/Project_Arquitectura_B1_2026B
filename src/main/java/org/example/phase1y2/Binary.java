package org.example.phase1y2;

/**
 * Implementación de la interfaz {@link BaseConverter} especializada en la conversión
 * bidireccional entre el sistema numérico decimal (base 10) y el sistema binario (base 2).
 * <p>
 * Utiliza el algoritmo de divisiones sucesivas para la codificación a binario y la
 * evaluación polinómica posicional con control de desbordamiento (overflow) para la
 * decodificación a decimal.
 * </p>
 */
public class Binary implements BaseConverter {
    /**
     * Base numérica usada para las conversiones (Binario = 2).
     */
    private final int base = 2;

    /**
     * Convierte un número decimal positivo a su representación en base 2
     * aplicando el método de divisiones sucesivas entre {@code 2}.
     *
     * @param numberDecimal Número decimal (base 10) a convertir.
     * @return Cadena de texto ({@code String}) que representa el número en sistema binario.
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
     * Decodifica una cadena binaria (base 2) a su valor entero equivalente en base 10
     * a través de expansión posicional de potencias de 2.
     * <p>
     * Agrega verificaciones preventivas de overflow aritmético antes de cada
     * multiplicación y suma acumulada para asegurarnos de que el resultado no supere
     * {@link Long#MAX_VALUE}.
     * </p>
     *
     * @param numberInBase Cadena binaria compuesta por dígitos {@code '0'} y {@code '1'}.
     * @return Valor decimal equivalente de tipo {@code long}.
     * @throws ArithmeticException Si la magnitud del número binario supera la capacidad
     *                             máxima representable por {@code Long.MAX_VALUE}.
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