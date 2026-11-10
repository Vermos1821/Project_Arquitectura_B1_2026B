package org.example.phase3y4smenu;

import org.example.phase3y4.BinaryAddition;
import org.example.phase3y4.BinarySubtraction;
import org.example.phase3y4.TwosComplementEncoder;
import org.example.util.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Se encarga de gestionar el menú de aritmética binaria
 * y complemento dos.
 * Permite acceder a las funcionalidades correspondientes
 * de las fases 3 y 4
 */
public class ArithmeticMenu {

    /**
     *  Muestra las opciones de aritmética binaria y complemento a dos.
     *  Le permite al usuario seleccionar la operación que desea realizar.
     *
     * @param scanner objeto Scanner utilizado para leer las entradas.
     */
    public void showMenu(Scanner scanner) {
        int option;

        do {
            System.out.println(Color.CYAN
                    + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW
                    + "  ✨ ARITMÉTICA BINARIA Y COMPLEMENTO A DOS ✨"
                    + Color.RESET);
            System.out.println(Color.CYAN
                    + "=================================================" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET
                    + "] ➔ Representar número en Complemento a Dos");
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET
                    + "] ➔ Verificar representación en Complemento a Dos");
            System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET
                    + "] ➔ Suma binaria");
            System.out.println("  [" + Color.GREEN + " 4 " + Color.RESET
                    + "] ➔ Resta binaria");
            System.out.println(Color.CYAN
                    + "-------------------------------------------------" + Color.RESET);
            System.out.println("  [" + Color.RED + " 0 " + Color.RESET
                    + "] ➔ Regresar al menú principal");
            System.out.println(Color.CYAN
                    + "=================================================" + Color.RESET);
            System.out.print(Color.YELLOW
                    + " ➤ Ingrese una opción: " + Color.RESET);

            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> representNumberToTwosComplement(scanner);
                    case 2 -> {
                        // Pendiente de implementar
                    }
                    case 3 -> binaryAddition(scanner);
                    case 4 -> binarySubtraction(scanner);
                    case 0 -> System.out.println(Color.GREEN + "\n↩ Regresando al menú principal..." + Color.RESET);
                    default -> System.out.println(Color.RED + "❌ Opción inválida. Vuelva a intentarlo." + Color.RESET);
                }
            }catch (InputMismatchException inputMismatchException) {
                System.out.println(Color.RED + "❌ ERROR: ingrese un número entero. " + Color.RESET);
                scanner.nextLine();
                option = -1;
            }
        } while (option != 0);
    }

    /**
     * Solicita un número decimal y la cantidad de bits
     * para convertirlo a su representación en complemento a dos.
     *
     * @param scanner objeto Scanner utilizado para leer los datos
     */
    private void representNumberToTwosComplement(Scanner scanner) {
        long number;
        int bits;

        try {
            System.out.print(Color.YELLOW + "\n➤ Ingrese un número decimal entero: " + Color.RESET);
            number = scanner.nextLong();

            System.out.println(Color.CYAN + "\nSeleccione la cantidad de bits:" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] 8 bits");
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET + "] 16 bits");
            System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET + "] 32 bits");

            System.out.print(Color.YELLOW + "➤ Ingrese una opción: " + Color.RESET);

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> bits = 8;
                case 2 -> bits = 16;
                case 3 -> bits = 32;
                default -> {
                    System.out.println(Color.RED + "❌ Opción de bits inválida." + Color.RESET);
                    return;
                }
            }

            TwosComplementEncoder twosComplementEncoder = new TwosComplementEncoder();
            String result = twosComplementEncoder.encode(number, bits);

            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "             RESULTADO DE LA CONVERSIÓN" + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);

            System.out.println("Número decimal: " + Color.GREEN + number + Color.RESET);
            System.out.println("Cantidad de bits: " + Color.GREEN + bits + Color.RESET);

            if (result.startsWith("ERROR:")) {
                System.out.println(Color.RED + result + Color.RESET);
            } else {
                System.out.println("Representación en complemento a dos: " + Color.BOLD + Color.GREEN + result
                                    + Color.RESET);
            }

            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
        } catch (InputMismatchException inputMismatchException) {
            System.out.println(Color.RED + "❌ Error: ingrese un número entero válido." + Color.RESET);
            scanner.nextLine();
        }
    }

    /**
     * Solicita los números y la cantidad de bits para realizar una suma binaria.
     *
     * @param scanner objeto Scanner para leer los datos ingresados.
     */
    private void binaryAddition(Scanner scanner) {
        try {
            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "                 SUMA BINARIA" + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);

            System.out.print(Color.YELLOW + "➤ Ingrese el primer número: " + Color.RESET);
            int firstNumber = scanner.nextInt();

            System.out.print(Color.YELLOW + "➤ Ingrese el segundo número: " + Color.RESET);
            int secondNumber = scanner.nextInt();

            System.out.println(Color.CYAN + "\nSeleccione la cantidad de bits:" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] 8 bits");
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET + "] 16 bits");
            System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET + "] 32 bits");

            System.out.print(Color.YELLOW + "➤ Ingrese una opción: " + Color.RESET);

            int option = scanner.nextInt();
            int bits;

            switch (option) {
                case 1 -> bits = 8;
                case 2 -> bits = 16;
                case 3 -> bits = 32;
                default -> {
                    System.out.println(Color.RED + "❌ Opción de bits inválida." + Color.RESET);
                    return;
                }
            }

            // Creamos el objeto que se encargará de realizar la suma
            BinaryAddition binaryAddition = new BinaryAddition();

            // Ejecutamos la operación con los números y bits seleccionados.
            binaryAddition.addNumbers(firstNumber, secondNumber, bits);

        } catch (InputMismatchException inputMismatchException) {
            System.out.println(Color.RED + "❌ Error: ingrese números enteros válidos." + Color.RESET);
            scanner.nextLine();
        }
    }

    /**
     * Solicita los números y la cantidad de bits para realizar una resta binaria.
     *
     * @param scanner objeto Scanner para leer los datos ingresados.
     */
    private void binarySubtraction(Scanner scanner) {
        try {
            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "                 RESTA BINARIA" + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);

            System.out.print(Color.YELLOW + "➤ Ingrese el primer número: " + Color.RESET);
            int firstNumber = scanner.nextInt();

            System.out.print(Color.YELLOW + "➤ Ingrese el segundo número: " + Color.RESET);
            int secondNumber = scanner.nextInt();

            System.out.println(Color.CYAN + "\nSeleccione la cantidad de bits:" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] 8 bits");
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET + "] 16 bits");
            System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET + "] 32 bits");

            System.out.print(Color.YELLOW + "➤ Ingrese una opción: " + Color.RESET);

            int option = scanner.nextInt();
            int bits;

            switch (option) {
                case 1 -> bits = 8;
                case 2 -> bits = 16;
                case 3 -> bits = 32;
                default -> {
                    System.out.println(Color.RED + "❌ Opción de bits inválida." + Color.RESET);
                    return;
                }
            }

            // Creamos el objeto que se encargará de realizar la resta.
            BinarySubtraction binarySubtraction = new BinarySubtraction();

            // Ejecutamos la operación con los números y bits seleccionados
            binarySubtraction.subtractNumbers(firstNumber, secondNumber, bits);

        } catch (InputMismatchException inputMismatchException) {
            System.out.println(Color.RED + "❌ Error: ingrese números enteros válidos." + Color.RESET);
            scanner.nextLine();

        }
    }
}
