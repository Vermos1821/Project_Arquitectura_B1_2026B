package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }

    static void mainMenu(Scanner scanner) {
        int option;

        do{
            System.out.println("\nBienvenido al tuti");
            System.out.println("1. Decimal a otras bases");
            System.out.println("2. Otras bases a decimal");
            System.out.println("3. Aritmética Binaria y Complemento a Dos");
            System.out.println("4. Suma y Resta en binario ");
            System.out.println("0. Exit");
            System.out.print("Ingrese una opción: ");

            option  = scanner.nextInt();
            switch(option) {
                case 1:
                    decimalToOtherBases(scanner);
                    break;
                case 2:
                    otherBasesToDecimal(scanner);
                    break;
                case 3:
                    binaryAddition(scanner);
                    break;
            }

        } while (option != 0);
    }


    static void binaryAddition(Scanner scanner) {

        int firstNumber;
        int secondNumber;

        try {
            System.out.print("\nEnter the first integer (-128 to 127): ");
            firstNumber = scanner.nextInt();

            System.out.print("Enter the second integer (-128 to 127): ");
            secondNumber = scanner.nextInt();

        } catch (InputMismatchException inputMismatchException) {

            System.out.println("Error: Please enter valid integers.");

            scanner.nextLine();
            return;
        }

        BinaryAddition binaryAddition = new BinaryAddition();

        binaryAddition.addNumbers(firstNumber, secondNumber);
    }


    static void decimalToOtherBases(Scanner scanner) {
        int numberDecimal = 0;
        boolean flag = false;

        while (!flag) {
            System.out.println("\nIngrese el número decimal positivo a convertir: ");

            try{
                numberDecimal = scanner.nextInt();
                if (numberDecimal < 0){
                    System.out.println("Entrada inválida, debe ser un número entero positivo ");
                } else {
                    flag = true;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Error: Ingresaste un carácter inválido. Debe ingresar un número entero positivo. ");
                scanner.nextLine();
            }
        }

        System.out.println("Bases a convertir ");
        System.out.println("1. Binario");
        System.out.println("2. Hexadecimal");
        System.out.println("3. Octal");
        System.out.println("0. Exit");
        System.out.println("Ingrese una opción: ");

        int option = 0;

        try{
            option = scanner.nextInt();
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Error: opción inválida");
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
                System.out.println("Saliendo del conversor...");
                return;
            default:
                System.out.println("Opción de base no válida.");
                return;
        }

        System.out.println(baseConverter.convertFromDecimal(numberDecimal));
    }

    static void otherBasesToDecimal(Scanner scanner) {
        String numberInBase = "0";
        boolean flag = false;

        System.out.println("\n¿De que base es el número que ingresará?");
        System.out.println("1. Binario");
        System.out.println("2. Hexadecimal");
        System.out.println("3. Octal");
        System.out.println("0. Exit");
        System.out.println("Ingrese una opción: ");

        int option = 0;

        try{
            option = scanner.nextInt();
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Error: opción inválida");
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
                System.out.println("Saliendo del conversor...");
                return;
            default:
                System.out.println("Opción de base no válida.");
                return;
        }

        while (!flag) {
            System.out.println("Ingrese el número para convertir a decimal: ");
            numberInBase = scanner.next();

            if (numberInBase.matches(regex)) {
                flag = true;
            } else {
                System.out.println("Error: El número ingresado es inválido para la base que eligió. Vuelva a ingresar. ");
            }
        }

        System.out.println("El número representado a decimal: " + baseConverter.convertToDecimal(numberInBase));
    }
}
