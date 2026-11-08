package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }

    static void mainMenu(Scanner scanner) {
        int option;

        do{
            System.out.println(CYAN + "\n=================================================" + RESET);
            System.out.println(BOLD + YELLOW + "    ✨ BIENVENIDO AL TUTI - MENÚ PRINCIPAL ✨    " + RESET);
            System.out.println(CYAN + "=================================================" + RESET);
            System.out.println("  [" + GREEN + " 1 " + RESET + "] ➔ Decimal a otras bases");
            System.out.println("  [" + GREEN + " 2 " + RESET + "] ➔ Otras bases a decimal");
            System.out.println("  [" + GREEN + " 3 " + RESET + "] ➔ Aritmética Binaria y Complemento a Dos");
            System.out.println(CYAN + "-------------------------------------------------" + RESET);
            System.out.println("  [" + RED + " 0 " + RESET + "] ➔ Salir del sistema");
            System.out.println(CYAN + "=================================================" + RESET);
            System.out.print(YELLOW + " ➤ Ingrese una opción: " + RESET);

            option  = scanner.nextInt();
            switch(option) {
                case 1:
                    decimalToOtherBases(scanner);
                    break;
                case 2:
                    otherBasesToDecimal(scanner);
                    break;
                case 3:
                    ArithmeticMenu arithmeticMenu = new ArithmeticMenu();
                    arithmeticMenu.showMenu(scanner);
                    break;

            }

        } while (option != 0);
    }

    static void decimalToOtherBases(Scanner scanner) {
        int numberDecimal = 0;
        boolean flag = false;

        while (!flag) {
            System.out.print(YELLOW + "\n ➤ Ingrese el número decimal positivo a convertir: " + RESET);

            try{
                numberDecimal = scanner.nextInt();
                if (numberDecimal < 0){
                    System.out.println(RED + " ❌ Entrada inválida, debe ser un número entero positivo." + RESET);
                } else {
                    flag = true;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println(RED + " ❌ Error: Ingresaste un carácter inválido. Debe ingresar un número entero" +
                                    " positivo." + RESET);
                scanner.nextLine();
            }
        }

        System.out.println(CYAN + "\n=================================================" + RESET);
        System.out.println(BOLD + YELLOW + "       ✨ DECIMAL A OTRAS BASES ✨       " + RESET);
        System.out.println(CYAN + "=================================================" + RESET);
        System.out.println("  [" + GREEN + " 1 " + RESET + "] ➔ Binario");
        System.out.println("  [" + GREEN + " 2 " + RESET + "] ➔ Hexadecimal");
        System.out.println("  [" + GREEN + " 3 " + RESET + "] ➔ Octal");
        System.out.println(CYAN + "-------------------------------------------------" + RESET);
        System.out.println("  [" + RED + " 0 " + RESET + "] ➔ Cancelar y volver");
        System.out.println(CYAN + "=================================================" + RESET);
        System.out.print(YELLOW + " ➤ Seleccione la base destino: " + RESET);

        int option = 0;

        try{
            option = scanner.nextInt();
        } catch (InputMismatchException inputMismatchException) {
            System.out.println(RED + " ❌ Error: opción inválida." + RESET);
            scanner.nextLine();
            return;
        }

        BaseConverter baseConverter = null;

        switch (option) {
            case 1:
                baseConverter = new Binary();
                break;
            case 2:
                baseConverter = new Hexadecimal();
                break;
            case 3:
                baseConverter = new Octal();
                break;
            case 0:
                System.out.println(YELLOW + " ↩ Saliendo del conversor..." + RESET);
                return;
            default:
                System.out.println(RED + " ❌ Opción de base no válida." + RESET);
                return;
        }

        System.out.println(CYAN + "\n ➔ Resultado de la conversión: " + BOLD + GREEN +
                            baseConverter.convertFromDecimal(numberDecimal) + RESET);
    }

    static void otherBasesToDecimal(Scanner scanner) {
        String numberInBase = "0";
        boolean flag = false;

        System.out.println(CYAN + "\n=================================================" + RESET);
        System.out.println(BOLD + YELLOW + "       ✨ OTRAS BASES A DECIMAL ✨       " + RESET);
        System.out.println(CYAN + "=================================================" + RESET);
        System.out.println("  [" + GREEN + " 1 " + RESET + "] ➔ Desde Binario");
        System.out.println("  [" + GREEN + " 2 " + RESET + "] ➔ Desde Hexadecimal");
        System.out.println("  [" + GREEN + " 3 " + RESET + "] ➔ Desde Octal");
        System.out.println(CYAN + "-------------------------------------------------" + RESET);
        System.out.println("  [" + RED + " 0 " + RESET + "] ➔ Cancelar y volver");
        System.out.println(CYAN + "=================================================" + RESET);
        System.out.print(YELLOW + " ➤ Seleccione la base de origen: " + RESET);

        int option = 0;

        try{
            option = scanner.nextInt();
        } catch (InputMismatchException inputMismatchException) {
            System.out.println(RED + " ❌ Error: opción inválida." + RESET);
            scanner.nextLine();
            return;
        }

        BaseConverter baseConverter = null;

        String regex = "";

        switch (option) {
            case 1:
                baseConverter = new Binary();
                regex = "[0-1]+";
                break;
            case 2:
                baseConverter = new Hexadecimal();
                regex = "[0-9A-Fa-f]+";
                break;
            case 3:
                baseConverter = new Octal();
                regex = "[0-7]+";
                break;
            case 0:
                System.out.println(YELLOW + " ↩ Saliendo del conversor..." + RESET);
                return;
            default:
                System.out.println(RED + " ❌ Opción de base no válida." + RESET);
                return;
        }

        while (!flag) {
            System.out.print(YELLOW + "\n ➤ Ingrese el número para convertir a decimal: " + RESET);
            numberInBase = scanner.next();

            if (numberInBase.matches(regex)) {
                flag = true;
            } else {
                System.out.println(RED + " ❌ Error: El número ingresado es inválido para la base que eligió." +
                                    " Vuelva a ingresar." + RESET);
            }
        }

        System.out.println(CYAN + "\n ➔ El número representado en decimal es: " + BOLD + GREEN +
                            baseConverter.convertToDecimal(numberInBase) + RESET);
    }
}
