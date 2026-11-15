package org.example.phase1y2;

/**
 * Implementación de la interfaz {@link BaseConverter} especializada en la conversión
 * bidireccional entre el sistema numérico decimal (base 10) y el sistema hexadecimal (base 16).
 * <p>
 * Utiliza el algoritmo de divisiones sucesivas con mapeo alfanumérico ({@code 0-9}),
 * para la codificación a hexadecimal y la evaluación polinómica posicional con control de
 * desbordamiento (overflow) para la decodificación a decimal.
 * </p>
 */
public class Hexadecimal implements BaseConverter {

    /**
     * Base numérica usada para las conversiones (Hexadecimal = 2).
     */
    private final int base = 16;

    /**
     * Convierte un número decimal positivo a su representación en base 16
     * a través de divisiones sucesivas entre {@code 16}, asignando los símbolos alfabéticos
     * de {@code A} a {@code F} para residuos comprendidos entre {@code 10} y {@code 15}.
     *
     * @param numberDecimal Número decimal (base 10) a convertir.
     * @return Cadena de texto ({@code String}) en mayúsculas con la representación hexadecimal resultante.
     */
    @Override
    public String convertFromDecimal(long numberDecimal){
        if (numberDecimal == 0) {
            return "0";
        }

        String result = "";

        while (numberDecimal > 0) {
            int remainder = (int) (numberDecimal % base);

            switch (remainder) {
                case 10 -> result = "A" + result;
                case 11 -> result = "B" + result;
                case 12 -> result = "C" +  result;
                case 13 -> result = "D" + result;
                case 14 -> result = "E" +  result;
                case 15 -> result = "F" + result;
                default -> result = remainder + result;
            }

            numberDecimal = numberDecimal / base;

        }
        return result;
    }

    /**
     * Decodifica una cadena hexadecimal (base 16) a su valor equivalente en base 10
     * a través de expansión posicional de potencias de 16.
     * <p>
     * Permite dígitos hexadecimales tanto en mayúsculas como en minúsculas gracias a
     * {@link Character#getNumericValue(char)} y agrega verificaciones preventivas de
     * desbordamiento aritmético para asegurarnos que el resultado no supere {@link Long#MAX_VALUE}.
     * </p>
     *
     * @param numberInBase Cadena hexadecimal compuesta por dígitos {@code 0-9} y letras {@code A-F} (o {@code a-f}).
     * @return Valor decimal equivalente de tipo {@code long}.
     * @throws ArithmeticException Si la magnitud del número hexadecimal supera la capacidad
     *                             máxima representable por {@code Long.MAX_VALUE}.
     */
    @Override
    public long convertToDecimal(String numberInBase) {
        long decimalResult = 0;
        long powerValue = 1;
        boolean powerOverflow = false;

        for (int i = numberInBase.length() - 1; i >= 0; i--) {
            char character =  numberInBase.charAt(i);

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
