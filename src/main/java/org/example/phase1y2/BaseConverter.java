package org.example.phase1y2;

/**
 * Interfaz que define el contrato estándar para las operaciones de conversión
 * bidireccional entre el sistema numérico decimal (base 10) y otros sistemas
 * de numeración posicional.
 */
public interface BaseConverter {
    /**
     * Convierte un número decimal a su representación en la base de destino.
     *
     * @param numberDecimal Número en sistema decimal (base 10) que desea convertir.
     * @return Cadena de texto ({@code String}) con la representación del número en la base correspondiente.
     */
    String convertFromDecimal(long numberDecimal);

    /**
     * Convierte una cadena de texto que representa un número en una base específica
     * a su valor equivalente en el sistema decimal (base 10).
     *
     * @param numberInBase Cadena de texto que representa el número en la base de origen.
     * @return Valor equivalente en sistema decimal (base 10) como un entero largo ({@code long}).
     * @throws ArithmeticException Si el valor convertido excede el límite superior que está permitido
     *                             por el tipo de dato {@code long} ({@link Long#MAX_VALUE}).
     */
    long convertToDecimal(String numberInBase);
}
