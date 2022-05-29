package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Checks {

    private static final int NUMBER_OF_LETTERS_FROM_BEGINNING = 4;

    private Checks() {
    }

    public static void ofFile(String path) {

        Path filePath = null;
        try {
            filePath = Path.of(path);
        } catch (InvalidPathException ex) {
            System.err.println("path: " + path + ", cannot be converted to a Path, it is invalid");
            System.err.println("Error details: " + ex.getMessage());
            System.exit(2);
        }
    }

    public static void ofDirectory(String path) {

        Path filePath = null;
        try {
            filePath = Path.of(path);
        } catch (InvalidPathException ex) {
            System.err.println("path: " + path + ", cannot be converted to a Path, it is invalid");
            System.err.println("Error details: " + ex.getMessage());
            System.exit(2);
        }

        try {
            if (!Files.isDirectory(filePath)) {

                System.err.println("Error: " + filePath
                        + " is not a directory. It does not exist or can't be determined, what file is a "
                        + "directory or not");
                System.exit(3);
            }
        } catch (SecurityException e) {
            System.err.println("Invalid read access to the file: " + filePath);
            System.err.println("Error details: " + e.getMessage());
            System.exit(4);
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

        for (int i = 0; i < Alphabets.getArrayFrequentWords(Alphabets.language).length; i++) {
            String value;
            String fromStrings = Alphabets.getArrayFrequentWords(Alphabets.language)[i];

            if (word.length() > NUMBER_OF_LETTERS_FROM_BEGINNING
                    && fromStrings.length() > NUMBER_OF_LETTERS_FROM_BEGINNING) {

                value = word.substring(0, NUMBER_OF_LETTERS_FROM_BEGINNING);
                fromStrings = fromStrings.substring(0, NUMBER_OF_LETTERS_FROM_BEGINNING);

                if (value.equalsIgnoreCase(fromStrings)) {
                    return true;
                }

            } else if (word.equalsIgnoreCase(fromStrings)) {

                return true;
            }
        }
        return false;
    }

    public static boolean autoSelectOfCorrectDecryption(String pathTo) {

        try (FileInputStream fileInputStream = new FileInputStream(pathTo);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            int symbol;
            int count = 0;
            String wordFromFile;
            String secondWordFromFile = null;
            StringBuilder stringBuilder = new StringBuilder();

            while ((symbol = bufferedReader.read()) != -1) {

                if (Character.isLetter(symbol) || Character.isWhitespace(symbol)) {

                    if (Character.isWhitespace(symbol) || Alphabets.isSymbol((char) symbol)) {

                        wordFromFile = stringBuilder.toString();
                        stringBuilder.delete(0, wordFromFile.length());

                        if (Checks.isCorrespondFrequentWords(wordFromFile) &&
                                !(wordFromFile.equalsIgnoreCase(secondWordFromFile))) {

                            count++;

                            // если count > 1, значит два разных слова из Alphabets.STRINGS
                            // совпали с содержимым pathFrom
                            if (count > 1) {

                                return true;
                            }
                            secondWordFromFile = wordFromFile;
                        }
                    } else {
                        stringBuilder.append((char) symbol);
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
