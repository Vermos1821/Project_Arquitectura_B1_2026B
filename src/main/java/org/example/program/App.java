package org.example.program;

import org.example.phase1y2smenu.ConverterMenu;
import org.example.phase3y4smenu.ArithmeticMenu;
import org.example.util.Color;

import java.util.Scanner;

public class App {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }

    static void mainMenu(Scanner scanner) {
        int option;
        ConverterMenu converterMenu = new ConverterMenu();

        do{
            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "    ✨ BIENVENIDO AL TUTI - MENÚ PRINCIPAL ✨    " + Color.RESET);
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
                case 1:
                    converterMenu.decimalToOtherBases(scanner);
                    break;
                case 2:
                    converterMenu.otherBasesToDecimal(scanner);
                    break;
                case 3:
                    ArithmeticMenu arithmeticMenu = new ArithmeticMenu();
                    arithmeticMenu.showMenu(scanner);
                    break;

            }

        } while (option != 0);
    }
}
