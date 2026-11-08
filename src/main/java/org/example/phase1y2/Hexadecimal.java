package org.example.phase1y2;

public class Hexadecimal implements BaseConverter {

    private final int base = 16;

    @Override
    public String convertFromDecimal(int numberDecimal){
        if (numberDecimal == 0) {
            return "0";
        }

        String result = "";

        while (numberDecimal > 0) {
            int remainder = numberDecimal % base;

            switch (remainder) {
                case 10 -> result = "A" + result;
                case 11 -> result = "B" + result;
                case 12 -> result = "C" +  result;
                case 13 -> result = "D" + result;
                case 14 -> result = "E" +  result;
                case 15 -> result = "F" + result;
                default -> result = remainder + result;
            }

            numberDecimal = numberDecimal / base;

        }
        return result;
    }

    @Override
    public int convertToDecimal(String numberInBase) {
        int decimalResult = 0;
        int power = 0;

        for (int i = numberInBase.length() - 1; i >= 0; i--) {
            char character =  numberInBase.charAt(i);

            int digit = Character.getNumericValue(character);
            int positionValue = digit * (int) Math.pow(base, power);

            decimalResult += positionValue;
            power++;
        }

        return decimalResult;
    }
}
