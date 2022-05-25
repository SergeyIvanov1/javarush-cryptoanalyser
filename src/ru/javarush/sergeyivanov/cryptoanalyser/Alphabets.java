package ru.javarush.sergeyivanov.cryptoanalyser;

import java.util.Arrays;

public class Alphabets {

    public static final char[] RUSSIAN = new char[]{'а', 'б', 'в', 'г',
            'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х',
            'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};

    private static final char[] ENGLISH = new char[]{'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final char[] SYMBOLS = {'.', ',', '"', '\'', ':', '-', '!', '?', ' '};


//   private static final char[][] alphabetsArray = {RUSSIAN,ENGLISH};

    private Alphabets() {
    }

    public static int indexOfRussian(char letter) {
        for (int i = 0; i < RUSSIAN.length; i++) {
            if (letter == RUSSIAN[i]) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOfEnglish(char letter) {

        int index = Arrays.binarySearch(ENGLISH, letter);
        if (index  >= 0) {
            return index;
        } else {
            return -1;
        }
    }

    public static char[] getRussian() {
        return RUSSIAN;
    }

    public static char[] getEnglish() {
        return ENGLISH;
    }
}
