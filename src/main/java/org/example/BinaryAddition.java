package org.example;

public class BinaryAddition {

    private static final int BIT_LENGTH = 8;
    private static final int MIN_VALUE = -128;
    private static final int MAX_VALUE = 127;

    public void addNumbers(int firstNumber, int secondNumber) {

        if (firstNumber < MIN_VALUE || firstNumber > MAX_VALUE ||
                secondNumber < MIN_VALUE || secondNumber > MAX_VALUE) {

            System.out.println("Error: Numbers must be between -128 and 127.");
            return;
        }

        String firstBinary = toTwosComplement(firstNumber);
        String secondBinary = toTwosComplement(secondNumber);

        String binaryResult = addBinary(firstBinary, secondBinary);

        boolean overflow = hasOverflow(
                firstBinary, secondBinary, binaryResult
        );

        int decimalResult = toDecimal(binaryResult);
        int mathematicalSum = firstNumber + secondNumber;

        System.out.println("\n BINARY ADDITION ");

        System.out.println("First number: " + firstNumber);
        System.out.println("Second number: " + secondNumber);

        System.out.println("\nFirst operand:  " + firstBinary);
        System.out.println("Second operand: " + secondBinary);


        System.out.println("Binary result: " + binaryResult);
        System.out.println("Decimal result (8-bit signed): " + decimalResult);
        System.out.println("Mathematical sum: " + mathematicalSum);
        System.out.println("Overflow: " + (overflow ? "YES" : "NO"));

    }

    private String toTwosComplement(int number) {

        if (number >= 0) {
            return toBinary(number);
        }

        String binary = toBinary(Math.abs(number));
        String invertedBinary = invertBits(binary);

        return addBinary(invertedBinary, "00000001");
    }

    private String toBinary(int number) {

        String binary = "";

        do {
            int remainder = number % 2;

            binary = remainder + binary;
            number = number / 2;

        } while (number > 0);

        while (binary.length() < BIT_LENGTH) {
            binary = "0" + binary;
        }

        return binary;
    }

    private String invertBits(String binary) {

        String invertedBinary = "";

        for (int i = 0; i < binary.length(); i++) {

            if (binary.charAt(i) == '0') {
                invertedBinary += "1";
            } else {
                invertedBinary += "0";
            }
        }

        return invertedBinary;
    }

    private String addBinary(String firstBinary, String secondBinary) {

        char[] result = new char[BIT_LENGTH];

        int carry = 0;

        for (int i = BIT_LENGTH - 1; i >= 0; i--) {

            int firstBit = firstBinary.charAt(i) - '0';
            int secondBit = secondBinary.charAt(i) - '0';

            int bitSum = firstBit + secondBit + carry;

            result[i] = (char) ('0' + (bitSum % 2));

            carry = bitSum / 2;
        }

        return new String(result);
    }

    private boolean hasOverflow(
            String firstBinary,
            String secondBinary,
            String binaryResult) {

        char firstSign = firstBinary.charAt(0);
        char secondSign = secondBinary.charAt(0);
        char resultSign = binaryResult.charAt(0);

        return firstSign == secondSign &&
                firstSign != resultSign;
    }

    private int toDecimal(String binary) {

        boolean negative = binary.charAt(0) == '1';

        String magnitudeBinary;

        if (negative) {
            magnitudeBinary = addBinary(
                    invertBits(binary), "00000001"
            );
        } else {
            magnitudeBinary = binary;
        }

        int decimalResult = 0;

        for (int i = 0; i < magnitudeBinary.length(); i++) {

            int bit = magnitudeBinary.charAt(i) - '0';

            decimalResult = decimalResult * 2 + bit;
        }

        return negative ? -decimalResult : decimalResult;
    }
}

