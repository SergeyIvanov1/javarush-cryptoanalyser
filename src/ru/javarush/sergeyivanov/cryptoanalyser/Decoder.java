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

            String pathKey = getNewFileName(pathTo, key);
            Coder.encryption(pathFrom, pathKey, key);
        }
    }

    public static boolean autoDecryptionBruteForce(String pathFrom, String pathTo) {

        try (FileInputStream fileInputStream = new FileInputStream(pathFrom);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
             FileOutputStream fileOutputStream = new FileOutputStream(pathTo);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {

            int symbol;
            int count = 0;
            String wordFromFile = null;
            String secondWordFromFile = null;
            StringBuilder stringBuilder = new StringBuilder();

            while ((symbol = bufferedReader.read()) != -1) {

                if (Character.isLetter(symbol) || Character.isWhitespace(symbol)) {
//                    char symbolChar = (char) symbol;

                    if (Character.isWhitespace(symbol)) {

                        wordFromFile = stringBuilder.toString();
                        stringBuilder.delete(0, wordFromFile.length());

                        if (Checks.isCorrespondFrequentWords(wordFromFile) &&
                                !(wordFromFile.equalsIgnoreCase(secondWordFromFile))) {

                            count++;
                            secondWordFromFile = wordFromFile;

                            // если count > 1, значит два разных слова из списка частых слов
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
            Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < chars.length; i++) {

                int foundKey = Alphabets.getIndex(mostFrequent, Alphabets.language)
                        - Alphabets.getIndex(chars[i], Alphabets.language);

                System.out.println(mostFrequent + " " + biggest);
                System.out.println(moreFrequent + " " + bigger);
                System.out.println(foundKey);

                System.out.println(mapa);

                decryptionWithKey(pathFrom, pathTo, foundKey);

                System.out.println("is result true or false? (true/false)");
                switch (scanner.nextLine()) {
                    case "true":
                        return;
                    case "false":
                        continue;
                    default:
                        System.out.println("true or false");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void autoDecryptionWithStatistic(String pathTo, String fileForInstance) {

    }

    public static String getNewFileName(String pathTo, int key) {
        int indexOfDot = pathTo.lastIndexOf(".");
        return pathTo.substring(0, indexOfDot) + key + pathTo.substring(indexOfDot);
    }
}
