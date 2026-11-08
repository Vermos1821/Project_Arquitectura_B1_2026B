package org.example;

// Clase de pruebas
public class TwosComplementTest {
    public static void main(String[] args) {
        TwosComplementEncoder encoder = new TwosComplementEncoder();

        System.out.println("Prueba 1: " + encoder.encode(13,16));
        System.out.println("Prueba 2: " + encoder.encode(-13,16));
        System.out.println("Prueba 3: " + encoder.encode(13,32));
        System.out.println("Prueba 4: " + encoder.encode(-2147483648L,32));
        System.out.println("Prueba 5: " + encoder.encode(13,10));
    }
}
