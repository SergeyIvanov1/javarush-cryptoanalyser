package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Decoder {

    private Decoder() {}

    public static void decryptionWithKey(String pathFrom, String pathTo, int key) {
        key *= -1;
        Coder.encryption(pathFrom, pathTo, key);
    }

    public static void manualDecryptionBruteForce(String pathFrom, String pathTo) {

        Scanner scanner = new Scanner(System.in);

        int key = 2;
        while (!scanner.nextLine().equals("exit")) {
            if (key == 0) {
                key++;
                continue;
            }

            if (key == 33) {
                break;
            }
            Coder.encryption(pathFrom, pathTo, key);
            key++;
        }
    }

    public static void autoDecryptionBruteForce(String pathFrom, String pathTo) {

        Scanner scanner = new Scanner(System.in);

        int key = 2;
        while (!scanner.nextLine().equals("exit")) {
            if (key == 0) {
                key++;
                continue;
            }

            if (key == 33) {
                break;
            }
            Coder.encryption(pathFrom, pathTo, key);
            key++;
        }

    }

    public static void manualDecryptionWithStatistic(String pathTo, String fileForInstance) {

        try (FileInputStream fileInputStream = new FileInputStream(pathTo);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            HashMap<Character, Integer> mapa = new HashMap<>();

            int value;
            while ((value = bufferedReader.read()) != -1) {

                char wantedChar = Character.toLowerCase((char) value);

                if (Character.isLetter(wantedChar) && (Alphabets.indexOfRussian(wantedChar) >= 0)) {

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

                int foundKey = Alphabets.indexOfRussian(mostFrequent)
                        - Alphabets.indexOfRussian(chars[i]);

                System.out.println(mostFrequent + " " + biggest);
                System.out.println(moreFrequent + " " + bigger);
                System.out.println(foundKey);

                System.out.println(mapa);

                decryptionWithKey(pathTo, fileForInstance, foundKey);

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
}
