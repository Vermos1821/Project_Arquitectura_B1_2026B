package org.example;

public class TwosComplementEncoder {
    public boolean isValidBitSize(int bits) {
        if (bits == 8 || bits == 16 || bits == 32) {
            return true;
        } else {
            return false;
        }
    }
}
