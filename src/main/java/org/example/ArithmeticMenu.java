package org.example;

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
            System.out.println("\n===== ARITMÉTICA BINARIA Y COMPLEMENTO A DOS =====");
            System.out.println("1. Representar número en Complemento a Dos");
            System.out.println("2. Verificar representación en Complemento a Dos");
            System.out.println("3. Suma binaria");
            System.out.println("4. Resta binaria");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Ingrese una opción: ");

            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        representNumberToTwosComplement(scanner);
                        break;
                    case 2:

                        break;
                    case 3:
                        binaryAddition(scanner);
                        break;

                }
            }catch (InputMismatchException inputMismatchException) {
                System.out.println("Error: ingrese un número entero. ");
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
            System.out.println("\nIngrese un número decimal entero: ");
            number = scanner.nextLong();

            System.out.println("\nSeleccione la cantidad de bits:");
            System.out.println("1. 8 bits");
            System.out.println("2. 16 bits");
            System.out.println("3. 32 bits");
            System.out.print("Ingrese una opción: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    bits = 8;
                    break;
                case 2:
                    bits = 16;
                    break;
                case 3:
                    bits = 32;
                    break;
                default:
                    System.out.println("Opción de bits inválida.");
                    return;

            }

            TwosComplementEncoder twosComplementEncoder = new TwosComplementEncoder();
            String result = twosComplementEncoder.encode(number, bits);

            System.out.println("\n===== RESULTADO =====");
            System.out.println("Número decimal: " + number);
            System.out.println("Cantidad de bits: " + bits);
            System.out.println("Representación binaria: " + result);
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Error: ingrese un número entero válido.");
            scanner.nextLine();
        }
    }

    /**
     * Gestiona la entrada de los operandos y ejecuta
     * la operación de suma binaria
     *
     * @param scanner objeto Scanner para leer los números
     */
    private void binaryAddition(Scanner scanner) {
        int firstNumber;
        int secondNumber;

        try {
            System.out.println("\nIngrese el primer número (-128 a 127): ");
            firstNumber = scanner.nextInt();

            System.out.println("Ingrese el segundo número (-128 a 127): ");
            secondNumber = scanner.nextInt();

        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Error: ingrese números enteros válidos. ");
            scanner.nextLine();
            return;
        }

        BinaryAddition binaryAddition = new BinaryAddition();
        binaryAddition.addNumbers(firstNumber, secondNumber);
    }
}
