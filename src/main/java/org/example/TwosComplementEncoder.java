package org.example;

// Comprueba si la cantidad de bits esta permitida
public class TwosComplementEncoder {
    public boolean isValidBitSize(int bits) {
        return bits == 8 || bits == 16 || bits == 32;
    }
// Comprueba si el numero esta dentro del rango seleccionado
    public boolean isRepresentable(long number, int bits) {
        switch (bits) {
            case 8:
                return number >= -128 && number <= 127;
            case 16:
                return number >= -32768 && number <= 32767;
            case 32:
                return number >= -2147483648L && number <= 2147483647L;
            default:
                return false;
        }
    }
    // Convierte un numero decimal positivo a binario
    private String decimalToBinary(long number, int bits) {
        String binary = "";

        if (number == 0) {
            binary = "0";
        }

        while (number > 0) {
            int remainder = (int) (number % 2);
            binary = remainder + binary;
            number = number / 2;
        }

        while (binary.length() < bits) {
            binary = "0" + binary;
        }

        return binary;
    }
    // Convierte numeros negativos usando complemento a dos
    // Invierte todos los bits
    private String invertBits(String binary){
        String inverted = "";
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            if (bit == '0') {
                inverted = inverted + "1";
            } else {
                inverted = inverted + "0";
            }
        }
        return inverted;
    }
    // Sumar 1 al numero binario invertido
    private String addOne(String binary) {
        String result = "";
        int carry = 1;
        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);
            if (bit == '1' && carry == 1) {
                result = "0" + result;
            } else if (bit == '0' && carry == 1) {
                result = "1" + result;
                carry = 0;
            } else {
                result = bit + result;
            }
        }
        return result;
    }

    public String encode(long number, int bits) {
        if (!isValidBitSize(bits)) {
            return "ERROR: la cantidad de bits debe ser 8, 16 o 32.";
        }
        if (!isRepresentable(number, bits)) {
            return "ERROR: el numero esta fuera del rango permitido.";
        }

        if (number >= 0) {
            return decimalToBinary(number, bits);
        }

        long absolute = -number;
        String binary = decimalToBinary(absolute, bits);
        String ones = invertBits(binary);
        return addOne(ones);
    }
}
