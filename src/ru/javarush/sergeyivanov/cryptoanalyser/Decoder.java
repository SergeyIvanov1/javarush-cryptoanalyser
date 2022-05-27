package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;
import java.util.*;

public class Decoder {

    private Decoder() {
    }

    public static void decryptionWithKey(String pathFrom, String pathTo, int key) {
        key *= -1;
        Coder.encryption(pathFrom, pathTo, key);
    }

    public static void manualDecryptionBruteForce(String pathFrom, String pathTo) {

        for (int key = 1; key < Alphabets.choiceOfAlphabet(Alphabets.language).length; key++) {

            String pathKey = getNewFileNameBF(pathTo, key);
            Coder.encryption(pathFrom, pathKey, key);
        }
    }

    public static void autoDecryptionBruteForce(String pathFrom, String pathTo) {

        for (int key = 1; key < Alphabets.choiceOfAlphabet(Alphabets.language).length; key++) {

            Coder.encryption(pathFrom, pathTo, key);

            if (Checks.autoSelectOfCorrectDecryption(pathTo)) {
                break;
            }
        }
    }

    public static void manualDecryptionWithStatistic(String pathFrom, String pathTo) {

        char[] chars = Alphabets.getGreatestFrequentLettersOfAlphabets(Alphabets.language);

        for (int ind = 0; ind < chars.length; ind++) {

            int foundKey = Alphabets.getIndex(getMostFrequentLetter(pathFrom), Alphabets.language)
                    - Alphabets.getIndex(chars[ind], Alphabets.language);

            String pathKey = getNewFileNameSA(pathTo, foundKey);

            decryptionWithKey(pathFrom, pathKey, foundKey);
        }
    }

    public static void autoDecryptionWithStatistic(String pathFrom, String pathTo) {

        char[] chars = Alphabets.getGreatestFrequentLettersOfAlphabets(Alphabets.language);

        for (int ind = 0; ind < chars.length; ind++) {

            int foundKey = Alphabets.getIndex(getMostFrequentLetter(pathFrom), Alphabets.language)
                    - Alphabets.getIndex(chars[ind], Alphabets.language);

            decryptionWithKey(pathFrom, pathTo, foundKey);

            if (Checks.autoSelectOfCorrectDecryption(pathTo)) {
                break;
            }
        }
    }

    public static String getNewFileNameBF(String pathTo, int key) {
        int indexOfDot = pathTo.lastIndexOf(".");
        return pathTo.substring(0, indexOfDot) + "bruteForce" + key + pathTo.substring(indexOfDot);
    }

    public static String getNewFileNameSA(String pathTo, int key) {
        int indexOfDot = pathTo.lastIndexOf(".");
        return pathTo.substring(0, indexOfDot) + "statisticAnalise" + key + pathTo.substring(indexOfDot);
    }

    public static char getMostFrequentLetter(String pathFrom) {

        try (FileInputStream fileInputStream = new FileInputStream(pathFrom);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            HashMap<Character, Integer> mapa = new HashMap<>();

            int value;
            while ((value = bufferedReader.read()) != -1) {

                char wantedChar = Character.toLowerCase((char) value);

                if (Character.isLetter(wantedChar) && (Alphabets.getIndex(wantedChar, Alphabets.language) >= 0)) {

                    if (mapa.containsKey(wantedChar)) {
                        mapa.put(wantedChar, mapa.get(wantedChar) + 1);
                    } else {
                        mapa.put(wantedChar, 1);
                    }
                }
            }

            char maxRepetitions = 0;
            int max = 0;

            Set<Map.Entry<Character, Integer>> entries = mapa.entrySet();
            for (Map.Entry<Character, Integer> pair : entries) {

                char character = pair.getKey();
                int amount = pair.getValue();

                if (amount > max) {

                    maxRepetitions = character;
                    max = amount;
                }
            }

            return maxRepetitions;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
