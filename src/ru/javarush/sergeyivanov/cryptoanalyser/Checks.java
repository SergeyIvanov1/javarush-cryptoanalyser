package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Checks {

    private static final int NUMBER_OF_LETTERS_FROM_BEGINNING = 4;

    private Checks() {
    }

    public static void whetherPathIsFile(String path) {

        Path filePath = null;
        try {
            filePath = Path.of(path);
        } catch (InvalidPathException ex) {

            String message = "path: " + path + ", cannot be converted to a Path, it is invalid\n"
                    + "Error details: " + ex.getMessage();
            throw new PathProcessingException(message, ex);
        }
    }

    public static void whetherPathIsDirectory(String path) {

        Path filePath = null;
        try {
            filePath = Path.of(path);

        } catch (InvalidPathException ex) {

            String message = "path: " + path + ", cannot be converted to a Path, it is invalid\n"
                    + "Error details: " + ex.getMessage();
            throw new PathProcessingException(message, ex);
        }

        try {
            Files.isDirectory(filePath);

//                System.err.println("Error: " + filePath
//                        + " is not a directory. It does not exist or can't be determined, what file is a "
//                        + "directory or not");
//                System.exit(3);

        } catch (SecurityException e) {

            String message = "Invalid read access to the file: " + filePath
                    + "\nError details: " + e.getMessage();
            throw new PathProcessingException(message, e);
        }
    }

    public static void notKey(String key, String alphabetName) {

        try {
            int valueKey = Integer.parseInt(key);

            if ((valueKey % TextProcessing.choiceOfAlphabet(alphabetName).length) == 0) {

                String message = "Ключ не должен равняться 0 или длинне алфавита ("
                        + TextProcessing.choiceOfAlphabet(alphabetName).length + "), т.к. текст не изменится";
                throw new KeyInvalidException(message);
            }
        } catch (NumberFormatException e) {
            String message = "The string \"" + key + "\" does not contain a parsable integer"
                    + "\nError details: " + e.getMessage();
            throw new KeyInvalidException(message, e);
        }
    }

    public static boolean isCorrespondFrequentWords(String word) {

        for (int i = 0; i < TextProcessing.getArrayFrequentWords(TextProcessing.language).length; i++) {
            String value;
            String fromStrings = TextProcessing.getArrayFrequentWords(TextProcessing.language)[i];

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

                    if (Character.isWhitespace(symbol) || TextProcessing.isSymbol((char) symbol)) {

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
