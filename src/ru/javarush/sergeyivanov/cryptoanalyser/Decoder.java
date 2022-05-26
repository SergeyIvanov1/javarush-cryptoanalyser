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

            char mostFrequent = 0;
            char moreFrequent = 0;
            int biggest = 0;
            int bigger = 0;

            Set<Map.Entry<Character, Integer>> entries = mapa.entrySet();
            for (Map.Entry<Character, Integer> pair : entries) {

                char character = pair.getKey();
                int amount = pair.getValue();

                if (amount > biggest) {

                    moreFrequent = mostFrequent;
                    bigger = biggest;
                    mostFrequent = character;
                    biggest = amount;

                } else if (amount == biggest) {

                    moreFrequent = character;
                    bigger = amount;

                } else if (amount > bigger) {

                    moreFrequent = character;
                    bigger = amount;
                }
            }

            /*
             * According to statistics, the most frequent letters are "о", "е", "а", "и", "т", "н".
             * Suppose, what "mostFrequent" is one of these letters, therefore calculate "keys" these letters un order.
             */

            char[] chars = new char[]{'о', 'е', 'а', 'и', 'т', 'н'};

            for (int i = 0; i < chars.length; i++) {

                int foundKey = Alphabets.getIndex(mostFrequent, Alphabets.language)
                        - Alphabets.getIndex(chars[i], Alphabets.language);

                String pathKey = getNewFileNameSA(pathTo, foundKey);

                decryptionWithKey(pathFrom, pathKey, foundKey);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void autoDecryptionWithStatistic(String pathTo, String fileForInstance) {

    }

    public static String getNewFileNameBF(String pathTo, int key) {
        int indexOfDot = pathTo.lastIndexOf(".");
        return pathTo.substring(0, indexOfDot) + "bruteForce" + key + pathTo.substring(indexOfDot);
    }

    public static String getNewFileNameSA(String pathTo, int key) {
        int indexOfDot = pathTo.lastIndexOf(".");
        return pathTo.substring(0, indexOfDot) + "statisticAnalise" + key + pathTo.substring(indexOfDot);
    }
}
