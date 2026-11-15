package org.example.util;

/**
 * Clase utilitaria que centraliza las secuencias de escape ANSI usadas para aplicar
 * estilos tipográficos y colores de primer plano en la salida estándar de consola.
 * <p>
 * Esta clase no es instanciable y provee de manera única constantes estáticas de acceso global.
 * </p>
 */
public class Color {
    /**
     * Secuencia de escape ANSI que restablece todos los atributos de color y estilo
     * a los valores predeterminados de la terminal.
     */
    public static final String RESET = "\u001B[0m";

    /**
     * Secuencia de escape ANSI para aplicar estilo de texto en negrita.
     */
    public static final String BOLD = "\u001B[1m";

    /**
     * Secuencia de escape ANSI para texto en color cian (utilizado en encabezados y separadores).
     */
    public static final String CYAN = "\u001B[36m";

    /**
     * Secuencia de escape ANSI para texto en color verde (utilizado en opciones válidas y resultados).
     */
    public static final String GREEN = "\u001B[32m";

    /**
     * Secuencia de escape ANSI para texto en color amarillo (utilizado en títulos y solicitudes de entrada).
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * Secuencia de escape ANSI para texto en color rojo (utilizado en alertas y mensajes de error).
     */
    public static final String RED = "\u001B[31m";

    /**
     * Constructor privado para restringir la instanciación de esta clase utilitaria.
     */
    private Color() {
    }

}

