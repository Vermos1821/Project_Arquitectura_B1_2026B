package org.example.phase1y2;

public class Octal implements BaseConverter {

    private final int base = 8;

    @Override
    public String convertFromDecimal(long numberDecimal) {
        if (numberDecimal == 0) {
            return "0";
        }

        String result = "";

        while (numberDecimal > 0) {
            long remainder = numberDecimal % base;

            result = remainder + result;

            numberDecimal = numberDecimal / base;
        }
        return result;
    }

    @Override
    public long convertToDecimal(String numberInBase) {
        long decimalResult = 0;
        long powerValue = 1;
        boolean powerOverflow = false;

        for (int i = numberInBase.length() - 1; i >= 0; i--) {
            char character =  numberInBase.charAt(i);

            int digit = Character.getNumericValue(character);

            if (digit > 0) {
                if (powerOverflow || powerValue > Long.MAX_VALUE / digit) {
                    throw new ArithmeticException("El número excede el límite de long.");
                }

                long positionValue = digit * powerValue;

                if (decimalResult > Long.MAX_VALUE - positionValue) {
                    throw new ArithmeticException("El número excede el límite de long.");
                }
                decimalResult += positionValue;
            }

            if (i > 0 && !powerOverflow) {
                if (powerValue > Long.MAX_VALUE / base) {
                    powerOverflow = true;
                } else {
                    powerValue = powerValue * base;
                }
            }
        }

        return decimalResult;
    }
}
