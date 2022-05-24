package ru.javarush.sergeyivanov.cryptoanalyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Alphabets {

    private static final ArrayList<Character> CYRILLIC = new ArrayList<>(Arrays.asList('а', 'б', 'в', 'г',
            'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х',
            'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'));

    private static final ArrayList<Character> LATIN = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

    private Alphabets() {
    }

    public static int isSymbolCyrillic(char letter) {
        for (int i = 0; i < CYRILLIC.size(); i++) {
            if (letter == CYRILLIC.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public static int isSymbolLatin(char letter) {
        int index = Collections.binarySearch(LATIN, letter);
        if (index  >= 0) {
            return index;
        } else {
            return -1;
        }
    }
}
