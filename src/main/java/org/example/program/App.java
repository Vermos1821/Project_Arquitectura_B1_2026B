package org.example.program;

import org.example.phase1y2smenu.ConverterMenu;
import org.example.phase3y4smenu.ArithmeticMenu;
import org.example.util.Color;

import java.util.Scanner;

/**
 * Clase principal la cual actúa como punto de entrada de la aplicación PPT.
 * Gestiona el menú interactivo por consola, que permite al usuario navegar
 * entre las funcionalidades de conversión de bases numéricas y aritmética binaria.
 */
public class App {

    /**
     * Método main que inicia la ejecución del programa.
     * Instanciamos el flujo de entrada de consola y lanza el bucle del menú principal.
     *
     * @param args Argumentos de la línea de comandos (utilizados en esta aplicación).
     */
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }

    /**
     * Muestra el menú principal iterativo de la aplicación y direcciona la ejecución
     * hacia los submenús correspondientes según la elección del usuario.
     * Se mantiene en ejecución hasta que el usuario selecciona la opción de salida.
     *
     * @param scanner Objeto {@link Scanner} inyectado para capturar la entrada estándar del usuario.
     */
    static void mainMenu(Scanner scanner) {
        int option;
        ConverterMenu converterMenu = new ConverterMenu();

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

            option  = scanner.nextInt();
            switch(option) {
                case 1 -> converterMenu.decimalToOtherBases(scanner);
                case 2 -> converterMenu.otherBasesToDecimal(scanner);
                case 3 -> {
                    ArithmeticMenu arithmeticMenu = new ArithmeticMenu();
                    arithmeticMenu.showMenu(scanner);
                }
            }

        } while (option != 0);
    }
}
