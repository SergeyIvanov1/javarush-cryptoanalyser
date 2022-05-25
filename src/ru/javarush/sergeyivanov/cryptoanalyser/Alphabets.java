package ru.javarush.sergeyivanov.cryptoanalyser;

import jdk.internal.vm.annotation.Stable;

import java.util.Arrays;

public class Alphabets {

    @Stable
    public static final char[] CYRILLIC = new char[]{'а', 'б', 'в', 'г',
            'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х',
            'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};

    private static final char[] LATIN = new char[]{'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final char[] SYMBOLS = {'.', ',', '"', '\'', ':', '-', '!', '?', ' '};


//   private static final char[][] alphabetsArray = {RUSSIAN,ENGLISH};

    private Alphabets() {
    }

    public static int indexOfRussian(char letter) {
        for (int i = 0; i < CYRILLIC.length; i++) {
            if (letter == CYRILLIC[i]) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOfEnglish(char letter) {

        int index = Arrays.binarySearch(LATIN, letter);
        if (index  >= 0) {
            return index;
        } else {
            return -1;
        }
    }

    public static char[] getCyrillic() {
        return CYRILLIC;
    }

    public static char[] getLatin() {
        return LATIN;
    }
}
