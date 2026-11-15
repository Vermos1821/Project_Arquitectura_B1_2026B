package org.example.phase1y2smenu;

import org.example.phase1y2.BaseConverter;
import org.example.phase1y2.Binary;
import org.example.phase1y2.Hexadecimal;
import org.example.phase1y2.Octal;
import org.example.util.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controlador de interfaz de consola que se encarga de la gestión de los submenús de conversión
 * bidireccional entre el sistema decimal (base 10) y sistemas de numeración posicional
 * (binario, hexadecimal y octal).
 * <p>
 * Implementa polimorfismo mediante la interfaz {@link BaseConverter} y aplica bucles de
 * validación persistentes para evitar interrupciones ante entradas inválidas.
 * </p>
 */
public class ConverterMenu {

    /**
     * Muestra el flujo interactivo para convertir un número entero decimal positivo
     * hacia una base de destino que seleccionó el usuario (Binario, Hexadecimal u Octal).
     * <p>
     * Primero valida que el operando decimal sea mayor o igual a cero. Posteriormente,
     * mantiene al usuario dentro del submenú de selección de base hasta que elija una opción
     * válida ({@code 1-3}) o decida cancelar ({@code 0}).
     * </p>
     *
     * @param scanner Instancia de {@link Scanner} usada para capturar las entradas por consola.
     */
    public void decimalToOtherBases(Scanner scanner) {
        long numberDecimal = 0;
        boolean flag = false;

        while (!flag) {
            System.out.print(Color.YELLOW + "\n ➤ Ingrese el número decimal positivo a convertir: " + Color.RESET);

            try {
                numberDecimal = scanner.nextLong();

                if (numberDecimal < 0) {
                    System.out.println(Color.RED + " ❌ Entrada inválida, debe ser un número entero positivo." + Color.RESET);
                } else {
                    flag = true;
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println(Color.RED + " ❌ Error: Ingresaste un carácter inválido." +
                                    " Debe ingresar un número entero positivo." + Color.RESET);
                scanner.nextLine();
            }
        }

        int option = 0;
        BaseConverter baseConverter = null;

        do {
            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "       ✨ DECIMAL A OTRAS BASES ✨       " + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] ➔ Binario");
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET + "] ➔ Hexadecimal");
            System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET + "] ➔ Octal");
            System.out.println(Color.CYAN + "-------------------------------------------------" + Color.RESET);
            System.out.println("  [" + Color.RED + " 0 " + Color.RESET + "] ➔ Cancelar y volver");
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.print(Color.YELLOW + " ➤ Seleccione la base destino: " + Color.RESET);

            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1 -> baseConverter = new Binary();
                    case 2 -> baseConverter = new Hexadecimal();
                    case 3 -> baseConverter = new Octal();
                    case 0 -> {
                        System.out.println(Color.GREEN + "\n ↩ Saliendo del conversor..." + Color.RESET);
                        return;
                    }
                    default -> {
                        System.out.println(Color.RED + " ❌ Opción de base no válida." + Color.RESET);
                        return;
                    }
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println(Color.RED + " ❌ Error: debe ingresar un número entero válido." + Color.RESET);
                scanner.nextLine();
                option = -1;
            }

        } while (baseConverter == null);

        System.out.println(Color.CYAN + "\n ➔ Resultado de la conversión: " + Color.BOLD + Color.GREEN +
                            baseConverter.convertFromDecimal(numberDecimal) + Color.RESET);
    }

    /**
     * Muestra el submenú para decodificar una cadena numérica expresada en binario,
     * hexadecimal u octal hacia su valor equivalente en el sistema decimal (base 10).
     * <p>
     * Mantiene el submenú activo ante selecciones erróneas o caracteres no numéricos.
     * Una vez elegida la base de origen, valida el formato de la cadena ingresada a través de
     * expresiones regulares específicas y controla posibles desbordamientos aritméticos
     * ({@link ArithmeticException}) al superar {@link Long#MAX_VALUE}.
     * </p>
     *
     * @param scanner Instancia de {@link Scanner} usada para capturar las entradas por consola.
     */
    public void otherBasesToDecimal(Scanner scanner) {
        int option = 0;
        BaseConverter baseConverter = null;
        String regex = "";

        do {
            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "       ✨ OTRAS BASES A DECIMAL ✨       " + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] ➔ Desde Binario");
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET + "] ➔ Desde Hexadecimal");
            System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET + "] ➔ Desde Octal");
            System.out.println(Color.CYAN + "-------------------------------------------------" + Color.RESET);
            System.out.println("  [" + Color.RED + " 0 " + Color.RESET + "] ➔ Cancelar y volver");
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.print(Color.YELLOW + " ➤ Seleccione la base de origen: " + Color.RESET);

            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1 -> {
                        baseConverter = new Binary();
                        regex = "[0-1]+";
                    }
                    case 2 -> {
                        baseConverter = new Hexadecimal();
                        regex = "[0-9A-Fa-f]+";
                    }
                    case 3 -> {
                        baseConverter = new Octal();
                        regex = "[0-7]+";
                    }
                    case 0 -> {
                        System.out.println(Color.GREEN + "\n ↩ Saliendo del conversor..." + Color.RESET);
                        return;
                    }
                    default -> {
                        System.out.println(Color.RED + " ❌ Opción de base no válida." + Color.RESET);
                        return;
                    }
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println(Color.RED + " ❌ Error: debe ingresar un número entero válido." + Color.RESET);
                scanner.nextLine();
                option = -1;
            }

        }while (baseConverter == null);

        String numberInBase = "0";
        boolean flag = false;

        while (!flag) {
            System.out.print(Color.YELLOW + "\n ➤ Ingrese el número para convertir a decimal: " + Color.RESET);

            numberInBase = scanner.next();

            if (numberInBase.matches(regex)) {
                flag = true;
            } else {
                System.out.println(Color.RED + " ❌ Error: El número ingresado es inválido para la base que eligió." +
                                    " Vuelva a ingresar." + Color.RESET);
            }
        }

        try {
            System.out.println(Color.CYAN + "\n ➔ El número representado en decimal es: " + Color.BOLD + Color.GREEN +
                    baseConverter.convertToDecimal(numberInBase) + Color.RESET);
        } catch (ArithmeticException arithmeticException) {
            System.out.println(Color.RED + " ❌ Error: El número excede el límite permitido para long." + Color.RESET);
        }

    }

}
