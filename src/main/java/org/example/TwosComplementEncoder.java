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
}
