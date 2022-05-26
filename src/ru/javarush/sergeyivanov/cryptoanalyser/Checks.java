package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;

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

    public  static boolean autoSelectOfCorrectDecryption(String pathTo) {
        try (FileInputStream fileInputStream = new FileInputStream(pathTo);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            int symbol;
            int count = 0;
            String wordFromFile = null;
            String secondWordFromFile = null;
            StringBuilder stringBuilder = new StringBuilder();

            while ((symbol = bufferedReader.read()) != -1) {

                if (Character.isLetter(symbol) || Character.isWhitespace(symbol)) {

                    if (Character.isWhitespace(symbol)) {

                        wordFromFile = stringBuilder.toString();
                        stringBuilder.delete(0, wordFromFile.length());

                        if (Checks.isCorrespondFrequentWords(wordFromFile) &&
                                !(wordFromFile.equalsIgnoreCase(secondWordFromFile))) {

                            count++;
                            secondWordFromFile = wordFromFile;

                            // если count > 1, значит два разных слова из Alphabets.STRINGS
                            // совпали с содержимым pathFrom
                            if (count > 1) {

                                return true;
                            }
                        }
                    } else {
                        stringBuilder.append(symbol);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
