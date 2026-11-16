package org.example.phase3y4smenu;

import org.example.phase3y4.BinaryAddition;
import org.example.phase3y4.BinarySubtraction;
import org.example.phase3y4.TwosComplementDecoder;
import org.example.phase3y4.TwosComplementEncoder;
import org.example.util.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controlador de interfaz de consola que se encarga de la gestión del submenú de aritmética binaria
 * y codificación/decodificación en Complemento a Dos (Fases 3 y 4).
 *<p>
 * Mantiene el estado de la última representación generada en memoria para permitir su posterior
 * verificación inversa sin la necesidad de reingresar la cadena binaria.
 *</p>
 */
public class ArithmeticMenu {
    /**
     * Almacena la última cadena binaria válida generada en Complemento a Dos.
     * Su valor es {@code null} si aún no se ha efectuado ninguna codificación exitosa en la sesión.
     */
    private String lastEncodedBinary = null;

    /**
     * Almacena la longitud de palabra en bits (8, 16, 32) que corresponde a {@link #lastEncodedBinary}.
     */
    private int lastEncodedBits = 0;

    /**
     *  Muestra las opciones de aritmética binaria y complemento a dos, el cual gestiona
     *  el flujo de navegación y la captura de excepciones de entrada por teclado.
     *
     * @param scanner Instancia de {@link Scanner} usada para la lectura del flujo de entrada estándar.
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
                    case 2 -> verifyTwosComplement(scanner);
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
     * Solicita al usuario un número decimal con signo y una estructura de bits (8, 16 o 32)
     * para codificar a través de {@link TwosComplementEncoder}.
     * <p>
     * Si la codificación es exitosa, el resultado se almacena en los atributos {@link #lastEncodedBinary}
     * y {@link #lastEncodedBits} para que se pueda hacer la posterior verificación inversa.
     * </p>
     *
     * @param scanner Instancia de {@link Scanner} para capturar el número decimal y la longitud de palabra.
     */
    private void representNumberToTwosComplement(Scanner scanner) {
        long number;
        int bits;

        try {
            System.out.print(Color.YELLOW + "\n➤ Ingrese un número decimal entero: " + Color.RESET);
            number = scanner.nextLong();

            bits = promptBitSize(scanner);
            if (bits == -1) {
                return;
            }

            TwosComplementEncoder twosComplementEncoder = new TwosComplementEncoder();
            String result = twosComplementEncoder.encode(number, bits);

            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "             RESULTADO DE LA CONVERSIÓN" + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);

            System.out.println("Número decimal: " + Color.GREEN + number + Color.RESET);
            System.out.println("Cantidad de bits: " + Color.GREEN + bits + Color.RESET);

            if (result.startsWith("ERROR:")) {
                System.out.println(Color.BOLD + Color.RED + "❌ " + result.replace("ERROR: ", "") +
                                    Color.RESET);
            } else {
                this.lastEncodedBinary = result;
                this.lastEncodedBits = bits;
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
     * Hace la gestión del proceso de verificación y decodificación inversa de Complemento a Dos a decimal.
     * <p>
     * Presenta un submenú que permite elegir entre:
     * </p>
     * <ol>
     *     <li>Decodificar y detallar el proceso inverso de la última representación que se almacenó en memoria</li>
     *     <li>Ingresar de manera manual una nueva cadena binaria en Complemento a Dos y su longitud en bits.</li>
     * </ol>
     *
     * @param scanner Instancia de {@link Scanner} usada para leer la selección y entradas del usuario.
     */
    private void verifyTwosComplement(Scanner scanner){
        try{
            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "     VERIFICACIÓN DE COMPLEMENTO A DOS" + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] ➔ Verificar el último número convertido"
                                + (lastEncodedBinary != null ? Color.CYAN + " (" + lastEncodedBinary + " - " +
                                lastEncodedBits + " bits)" + Color.RESET : Color.RED +
                                " (Sin registro previo)" + Color.RESET));
            System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET +
                                "] ➔ Ingresar un nuevo número en Complemento a Dos");
            System.out.println("  [" + Color.RED + " 0 " + Color.RESET + "] ➔ Cancelar");
            System.out.println(Color.CYAN + "-------------------------------------------------" + Color.RESET);
            System.out.print(Color.YELLOW + "➤ Seleccione una opción: " + Color.RESET);

            int subOption = scanner.nextInt();
            scanner.nextLine(); // Limpiar el salto de línea residual del buffer

            String targetBinary;
            int targetBits;

            switch (subOption) {
                case 1 -> {
                    if (lastEncodedBinary == null) {
                        System.out.println(Color.RED + "\n❌ No existe una conversión previa en esta sesión. "
                                            + "Ejecute primero la opción [1] o elija ingresar un nuevo número."
                                            + Color.RESET);
                        return;
                    }
                    targetBinary = this.lastEncodedBinary;
                    targetBits = this.lastEncodedBits;
                }
                case 2 -> {
                    targetBits = promptBitSize(scanner);

                    if (targetBits == -1) {
                        return;
                    }

                    scanner.nextLine();

                    System.out.print(Color.YELLOW + "\n➤ Ingrese la cadena binaria de " + targetBits +
                                        " bits: " + Color.RESET);
                    targetBinary = scanner.nextLine().trim();
                }
                case 0 -> {
                    return;
                }
                default -> {
                    System.out.println(Color.RED + "❌ Opción inválida." + Color.RESET);
                    return;
                }
            }

            executeDecodingAndTrace(targetBinary, targetBits);
        } catch (InputMismatchException inputMismatchException){
            System.out.println(Color.RED + "❌ Error: entrada numérica inválida." + Color.RESET);
            scanner.nextLine();
        }
    }

    /**
     * Ejecuta la decodificación a través de {@link TwosComplementDecoder} e imprime en consola
     * la traza técnica del proceso inverso paso a paso (análisis del MSB, complemento a uno,
     * suma de acarreo y magnitud decimal final).
     *
     * @param binaryStr Cadena binaria en Complemento a Dos a decodificar.
     * @param bits Longitud de palabra en bits asociada a la cadena.
     */
    private void executeDecodingAndTrace(String binaryStr, int bits){
        TwosComplementDecoder twosComplementDecoder = new TwosComplementDecoder();

        try{
            long decimalValue = twosComplementDecoder.decode(binaryStr, bits);
            char msb = binaryStr.charAt(0);

            System.out.println(Color.CYAN + "\n=================================================" + Color.RESET);
            System.out.println(Color.BOLD + Color.YELLOW + "        TRAZA DE DECODIFICACIÓN INVERSA" + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);
            System.out.println("Entrada binaria (" + bits + " bits): " + Color.GREEN + binaryStr + Color.RESET);
            System.out.println("Bit más significativo (MSB): " + Color.BOLD + msb + Color.RESET
                                + (msb == '0' ? " ➔ Número Positivo / Cero" : " ➔ Número Negativo"));

            if (msb == '0'){
                System.out.println("Proceso aplicado: Conversión posicional directa base 2 a base 10.");
            } else {
                String inverted = binaryStr.replace('0', 'x').replace('1', '0').replace('x', '1');
                String magnitudeBin = Long.toBinaryString(Math.abs(decimalValue));

                while(magnitudeBin.length() < bits){
                    magnitudeBin = "0" + magnitudeBin;
                }

                System.out.println("Paso 1 (Complemento a Uno / NOT): " + Color.YELLOW + inverted + Color.RESET);
                System.out.println("Paso 2 (Suma de +1 / Magnitud):   " + Color.YELLOW + magnitudeBin + Color.RESET);
                System.out.println("Paso 3 (Aplicación de signo):     " + Color.YELLOW + "-" + Math.abs(decimalValue) + Color.RESET);

            }

            System.out.println(Color.CYAN + "-------------------------------------------------" + Color.RESET);
            System.out.println("Valor decimal recuperado: " + Color.BOLD + Color.GREEN + decimalValue + Color.RESET);
            System.out.println(Color.CYAN + "=================================================" + Color.RESET);

        } catch (IllegalArgumentException illegalArgumentException){
            System.out.println(Color.RED + "\n-------------------------------------------------" + Color.RESET);
            System.out.println(Color.BOLD + Color.RED + "❌ ERROR DE DECODIFICACIÓN:" + Color.RESET);
            System.out.println(Color.RED + "   " + illegalArgumentException.getMessage() + Color.RESET);
            System.out.println(Color.RED + "-------------------------------------------------" + Color.RESET);
        }
    }

    /**
     * Despliega las opciones de estructura de bits que soporta (8, 16 o 32 bits)
     * y retorna la longitud seleccionada.
     *
     * @param scanner Instancia {@link Scanner} para capturar lo que seleccionó el usuario.
     * @return Cantidad de bits seleccionada ({@code 8}, {@code 16} o {@code 32}),
     *         o {@code -1} si la opción ingresada es inválida.
     */
    private int promptBitSize(Scanner scanner) {
        System.out.println(Color.CYAN + "\nSeleccione la cantidad de bits:" + Color.RESET);
        System.out.println("  [" + Color.GREEN + " 1 " + Color.RESET + "] 8 bits");
        System.out.println("  [" + Color.GREEN + " 2 " + Color.RESET + "] 16 bits");
        System.out.println("  [" + Color.GREEN + " 3 " + Color.RESET + "] 32 bits");
        System.out.print(Color.YELLOW + "➤ Ingrese una opción: " + Color.RESET);

        int option = scanner.nextInt();
        return switch (option) {
            case 1 -> 8;
            case 2 -> 16;
            case 3 -> 32;
            default -> {
                System.out.println(Color.RED + "❌ Opción de bits inválida." + Color.RESET);
                yield -1;
            }
        };
    }

    /**
     * Solicita al usuario dos operandos enteros y la longitud de palabra en bits para asignarle
     * la suma binaria a la clase {@link BinaryAddition}
     *
     * @param scanner Instancia de {@link Scanner} para leer los operandos y la cantidad de bits
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

            int bits = promptBitSize(scanner);
            if (bits == -1) {
                return;
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
     * Solicita al usuario dos operandos enteros y la longitud de palabra en bits para asignarle
     * la resta binaria a la clase {@link BinarySubtraction}
     *
     * @param scanner Instancia de {@link Scanner} para leer los operandos y la cantidad de bits
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

            int bits = promptBitSize(scanner);
            if (bits == -1) {
                return;
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
