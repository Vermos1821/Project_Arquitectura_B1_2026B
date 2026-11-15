package org.example.program;

import org.example.phase1y2smenu.ConverterMenu;
import org.example.phase3y4smenu.ArithmeticMenu;
import org.example.util.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal la cual actúa como punto de entrada de la aplicación PPT.
 * <p>
 * Gestiona el menú interactivo por consola, que permite al usuario navegar
 * entre los módulos de conversión de bases numéricas y aritmética binaria
 * con representación en Complemento a Dos.
 * </p>
 */
public class App {

    /**
     * Punto de entrada principal que inicia la ejecución de la aplicación.
     * Instancia el flujo de entrada de consola a través de {@link Scanner} y lanza
     * el bucle del menú principal.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }

    /**
     * Muestra el menú principal iterativo de la aplicación y direcciona la ejecución
     * hacia los submenús correspondientes según la elección del usuario.
     * <p>
     * Contiene validaciones de rango numérico y captura de {@link InputMismatchException}
     * para evitar interrupciones ante entradas no numéricas. El ciclo se mantiene activo
     * hasta que el usuario seleccione la opción de salida ({@code 0}).
     * </p>
     *
     * @param scanner Instancia de {@link Scanner} inyectada para capturar la entrada estándar del usuario.
     */
    static void mainMenu(Scanner scanner) {
        int option;
        ConverterMenu converterMenu = new ConverterMenu();
        ArithmeticMenu arithmeticMenu = new ArithmeticMenu();

        do{
            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "    ✨ BIENVENIDO A PPT - MENÚ PRINCIPAL ✨    " + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] ➔ Decimal a otras bases");
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET + "] ➔ Otras bases a decimal");
            System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET + "] ➔ Aritmética Binaria y Complemento a Dos");
            System.out.println(Color.CYAN + "-------------------------------------------------" + Color.RESET);
            System.out.println("  [" + Color.RED + " 0 " + Color.RESET + "] ➔ Salir del sistema");
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.print(Color.YELLOW + " ➤ Ingrese una opción: " + Color.RESET);

            try{
                option  = scanner.nextInt();
                switch(option) {
                    case 1 -> converterMenu.decimalToOtherBases(scanner);
                    case 2 -> converterMenu.otherBasesToDecimal(scanner);
                    case 3 -> arithmeticMenu.showMenu(scanner);
                    case 0 -> System.out.println(Color.GREEN + "\n👋 Saliendo del sistema. ¡Hasta pronto!" + Color.RESET);
                    default -> System.out.println(Color.RED + "❌ Opción inválida. Por favor, ingrese una opción entre 0 y 3." + Color.RESET);
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println(Color.RED + "❌ ERROR: entrada no válida. Debe ingresar un número entero." + Color.RESET);
                scanner.nextLine(); // Limpia el búfer de entrada para descartar el token inválido
                option = -1;
            }

        } while (option != 0);
    }
}
