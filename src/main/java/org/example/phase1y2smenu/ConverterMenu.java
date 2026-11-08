package org.example.phase1y2smenu;

import org.example.phase1y2.BaseConverter;
import org.example.phase1y2.Binary;
import org.example.phase1y2.Hexadecimal;
import org.example.phase1y2.Octal;
import org.example.util.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConverterMenu {

    public void decimalToOtherBases(Scanner scanner) {
        int numberDecimal = 0;
        boolean flag = false;

        while (!flag) {
            System.out.print(Color.YELLOW + "\n ➤ Ingrese el número decimal positivo a convertir: " + Color.RESET);
            try {
                numberDecimal = scanner.nextInt();
                if (numberDecimal < 0) {
                    System.out.println(Color.RED + " ❌ Entrada inválida, debe ser un número entero positivo." + Color.RESET);
                } else {
                    flag = true;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println(Color.RED + " ❌ Error: Ingresaste un carácter inválido. Debe ingresar un número entero positivo." + Color.RESET);
                scanner.nextLine();
            }
        }

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

        int option = 0;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException inputMismatchException) {
            System.out.println(Color.RED + " ❌ Error: opción inválida." + Color.RESET);
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
                System.out.println(Color.YELLOW + " ↩ Saliendo del conversor..." + Color.RESET);
                return;
            default:
                System.out.println(Color.RED + " ❌ Opción de base no válida." + Color.RESET);
                return;
        }

        System.out.println(Color.CYAN + "\n ➔ Resultado de la conversión: " + Color.BOLD + Color.GREEN +
                baseConverter.convertFromDecimal(numberDecimal) + Color.RESET);
    }

    public void otherBasesToDecimal(Scanner scanner) {
        String numberInBase = "0";
        boolean flag = false;

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

        int option = 0;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException inputMismatchException) {
            System.out.println(Color.RED + " ❌ Error: opción inválida." + Color.RESET);
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
                System.out.println(Color.YELLOW + " ↩ Saliendo del conversor..." + Color.RESET);
                return;
            default:
                System.out.println(Color.RED + " ❌ Opción de base no válida." + Color.RESET);
                return;
        }

        while (!flag) {
            System.out.print(Color.YELLOW + "\n ➤ Ingrese el número para convertir a decimal: " + Color.RESET);
            numberInBase = scanner.next();
            if (numberInBase.matches(regex)) {
                flag = true;
            } else {
                System.out.println(Color.RED +
                                    " ❌ Error: El número ingresado es inválido para la base que eligió." +
                                    " Vuelva a ingresar." + Color.RESET);
            }
        }

        System.out.println(Color.CYAN + "\n ➔ El número representado en decimal es: " + Color.BOLD + Color.GREEN +
                            baseConverter.convertToDecimal(numberInBase) + Color.RESET);
    }
}
