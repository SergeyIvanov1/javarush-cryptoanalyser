package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static int unencryptedChar; //незашифрованный символ
    private static int unencryptedCharInd; // unencryptedChar, который находится на позиции с индексом "i"
    private static int secretCharInd; // secretChar, который находится на позиции с индексом "i"
    private static char secretChar; // зашифрованный символ
    private static int key = 5; // секретный ключ
    private static final ArrayList<Character> ALPHABET = new ArrayList<>(Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я'));
    private static final char[] SYMBOLS = {'.', ',', '"', '\'', ':', '-', '!', '?', ' '};


    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input the key");
//        String keyLine = scanner.nextLine();
//        int key = Integer.valueOf(keyLine);
//
//        if (key <= 0 || key > ((int)Math.pow(2, 31) - 33)) {
//            throw new IndexOutOfBoundsException("Key out of bounds available values");
//        }
//
//        System.out.println("Input the path to the file containing the text");
//        String pathToFile = scanner.nextLine();
        String pathFrom = "file";
        String pathTo = "file_encrypt";
        String fileDecrypt = "file_decrypt";
        String fileForInstance = "fileForInstance";

        checkPaths(pathFrom, pathTo, fileDecrypt);

        encryption(pathFrom, pathTo, key);
//        decryptionWithKey(pathTo, fileDecrypt, key);
//        decryptionBruteForce(pathTo, fileDecrypt, fileForInstance);
        decryptionWithStatistic(pathTo, fileForInstance);
    }

    public static void encryption(String pathFrom, String pathTo, int key) {

        try (FileInputStream fileInputStream = new FileInputStream(pathFrom);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
             FileOutputStream fileOutputStream = new FileOutputStream(pathTo);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {

            while ((unencryptedChar = bufferedReader.read()) != -1) { //запускаю цикл чтения по 1 символу из файла

                if (Character.isLetter(unencryptedChar)) {

                    // проверяю есть ли unencryptedChar в ALPHABET или SYMBOLS.
                    // Если unencryptedChar не совпадает, пропускаем его.
                    for (int i = 0; i < ALPHABET.size(); i++) {

                        char tempToLowerChar = 0;

                        if (Character.isUpperCase(unencryptedChar)) {
                            tempToLowerChar = (char) Character.toLowerCase(unencryptedChar);

                            if (tempToLowerChar == ALPHABET.get(i)) {

                                secretCharInd = (i + key) % ALPHABET.size();
                                if (secretCharInd < 0) {
                                    secretCharInd = ALPHABET.size() - Math.abs(secretCharInd);
                                }
                                secretChar = ALPHABET.get(secretCharInd);

                                bufferedWriter.append(Character.toUpperCase(secretChar));
                            }
                        }

                        if (unencryptedChar == ALPHABET.get(i)) {

                            secretCharInd = (i + key) % ALPHABET.size();
                            if (secretCharInd < 0) {
                                secretCharInd = ALPHABET.size() - Math.abs(secretCharInd);
                            }
                            secretChar = ALPHABET.get(secretCharInd);

                            bufferedWriter.append(secretChar);
                        }
                    }

                } else {

                    for (int j = 0; j < SYMBOLS.length; j++) {
                        if (unencryptedChar == SYMBOLS[j]) {

                            secretChar = SYMBOLS[j];
                            bufferedWriter.append(secretChar);
                        }
                    }
                }
            }
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decryptionWithKey(String pathFrom, String pathTo, int key) {
        key *= -1;
        encryption(pathFrom, pathTo, key);
    }

    public static void decryptionBruteForce(String pathFrom, String pathTo, String fileForInstance) {

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
            encryption(pathFrom, pathTo, key);
            key++;
        }

    }

    public static void decryptionWithStatistic(String pathTo, String fileForInstance) {

        try (FileInputStream fileInputStream = new FileInputStream(pathTo);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            HashMap<Character, Integer> mapa = new HashMap<>();

            int value;
            while ((value = bufferedReader.read()) != -1) {

                secretChar = Character.toLowerCase((char) value);

                if (Character.isLetter(secretChar) && isInsideOfALPHABET(secretChar)) {

                    if (mapa.containsKey(secretChar)) {
                        mapa.put(secretChar, mapa.get(secretChar) + 1);
                    } else {
                        mapa.put(secretChar, 1);
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

        /* According to statistics, the most frequent letters are "о", "е", "а", "и", "т", "н".
          Suppose, what "mostFrequent" is one of these letters, therefore calculate "keys" these letters un order. */

            char[] chars = new char[]{'о', 'е', 'а', 'и', 'т', 'н'};
            Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < chars.length; i++) {

                int negativeKey = 0;
                int positiveKey = ALPHABET.indexOf(mostFrequent) - ALPHABET.indexOf(chars[i]);
                if (positiveKey > 0) {
                    negativeKey = -1 * (33 - Math.abs(ALPHABET.indexOf(mostFrequent) - ALPHABET.indexOf(chars[i])) % ALPHABET.size());
                } else {
                    negativeKey = 33 - Math.abs(ALPHABET.indexOf(mostFrequent) - ALPHABET.indexOf(chars[i])) % ALPHABET.size();
                }

                System.out.println(mostFrequent + " " + biggest);
                System.out.println(moreFrequent + " " + bigger);
                System.out.println(positiveKey);
                System.out.println(negativeKey);

                System.out.println(mapa);

                decryptionWithKey(pathTo, fileForInstance, positiveKey);

                System.out.println("is result true or false? (true/false)");
                switch (scanner.nextLine()) {
                    case "true" :
                        return;
                    case "false" :
                        continue;
                    default :
                        System.out.println("true or false");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void checkPaths(String fileInit, String file_encrypt, String file_decrypt) {

        if (file_encrypt == null) {
            throw new NullPointerException("path equals null");
        }
    }

    public static boolean isInsideOfALPHABET(char letter) {

        for (int i = 0; i < ALPHABET.size(); i++) {
            if (letter == ALPHABET.get(i)) {
                return true;
            }
        }
        return false;
    }
}
