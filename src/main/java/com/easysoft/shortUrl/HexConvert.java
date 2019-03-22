package com.easysoft.shortUrl;

import java.util.Stack;

/**
 * @author Administrator
 */
public class HexConvert {

    public static final char[] ARRAY = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};

    public static String tenToSixTwo(long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        do {
            Long pos = rest - (rest / 62) * 62;
            stack.add(ARRAY[pos.intValue()]);
            rest = rest / 62;
        } while (rest != 0);
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();

    }

    public static long sixTwoToTen(String sixTwoStr) {
        long multiple = 1;
        long result = 0;
        Character c;
        for (int i = 0; i < sixTwoStr.length(); i++) {
            c = sixTwoStr.charAt(sixTwoStr.length() - i - 1);
            result += sixTwoValue(c) * multiple;
            multiple = multiple * 62;
        }
        return result;
    }

    private static int sixTwoValue(Character c) {
        for (int i = 0; i < ARRAY.length; i++) {
            if (c == ARRAY[i]) {
                return i;
            }
        }
        return -1;
    }

}