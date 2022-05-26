package ru.javarush.sergeyivanov.cryptoanalyser;

public class Checks {

    private Checks() {
    }

    public static void ofPath(String path) {

        if (path == null) {
            throw new NullPointerException("path equals null");
        }
    }

    public static boolean notKey(String key, String alphabetName) {

        boolean marker = false;

        try {
            int valueKey = Integer.parseInt(key);

            if ((valueKey % Alphabets.choiceOfAlphabet(alphabetName).length) == 0) {

                System.out.println("Ключ не должен равняться 0 или "
                        + Alphabets.choiceOfAlphabet(alphabetName).length + ", т.к. текст не изменится");
                marker = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите цифру");
            marker = true;
        }
        return marker;
    }

    public static boolean isCorrespondFrequentWords(String word) {

        for (int i = 0; i < Alphabets.STRINGS.size(); i++) {

            if (word.equalsIgnoreCase(Alphabets.STRINGS.get(i))) {

                return true;
            }
        }
        return false;
    }
}
