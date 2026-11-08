package org.example.util;

/**
 * Contiene los códigos ANSI que se usaran para dar formato y color
 * a los mensajes mostrados en la consola.
 */
public class Color {

    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";

    /**
     * Constructor privado para evitar crear objetos de esta clase.
     */
    private Color() {
    }

}

