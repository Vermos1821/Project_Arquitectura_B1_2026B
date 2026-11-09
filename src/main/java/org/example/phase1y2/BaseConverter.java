package org.example.phase1y2;

/**
 * Interfaz que define el contrato estándar para las operaciones de conversión
 * entre el sistema numérico decimal (base 10) y otras bases numéricas.
 */
public interface BaseConverter {
    /**
     * Convierte un número decimal a su representación en la base de destino.
     *
     * @param numberDecimal El número en sistema decimal (base 10) que desea convertir.
     * @returnUna cadena de texto ({@code String}) con la representación del número en la base correspondiente.
     */
    String convertFromDecimal(long numberDecimal);

    /**
     * Convierte una cadena de texto que representa un númnero en una base específica
     * a su valor equivalente en el sistema decimal.
     *
     * @param numberInBase El número en la base de origen representado como cadena de texto.
     * @return El valor equivalente en sistema decimal (base 10) como un entero largo ({@code long}).
     */
    long convertToDecimal(String numberInBase);
}
