package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;
import java.nio.file.Path;
import java.util.Date;
import java.util.Scanner;

public class Main {

    private static int unencryptedChar; //незашифрованный символ
    private static int unencryptedCharInd; // unencryptedChar, который находится на позиции с индексом "i"
    private static int secretCharInd; // secretChar, который находится на позиции с индексом "i"
    private static char secretChar; // зашифрованный символ
    private static int key = 1; // секретный ключ
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    private static final char[] SYMBOLS = {'.', ',', '"', '\'', ':', '-', '!', '?', ' '};


    public static void main(String[] args)  {
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

//        encryption(fileInit, fileForEncrypt, key);
//        decryptionWithKey(pathTo, fileDecrypt, key);
        decryptionBruteForce(pathTo, fileDecrypt, fileForInstance);
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
                    for (int i = 0; i < ALPHABET.length; i++) {

                        char tempToLowerChar = 0;

                        if (Character.isUpperCase(unencryptedChar)) {
                            tempToLowerChar = (char) Character.toLowerCase(unencryptedChar);

                            if (tempToLowerChar == ALPHABET[i]) {

                                secretCharInd = (i + key) % ALPHABET.length;
                                if (secretCharInd < 0) {
                                    secretCharInd = ALPHABET.length - Math.abs(secretCharInd);
                                }
                                secretChar = ALPHABET[secretCharInd];

                                bufferedWriter.append(Character.toUpperCase(secretChar));
                            }
                        }

                        if (unencryptedChar == ALPHABET[i]) {

                            secretCharInd = (i + key) % ALPHABET.length;
                            if (secretCharInd < 0) {
                                secretCharInd = ALPHABET.length - Math.abs(secretCharInd);
                            }
                            secretChar = ALPHABET[secretCharInd];

                            bufferedWriter.append(secretChar);
                        }
                    }

                } else {

                    for (int j = 0; j < SYMBOLS.length; j++) {
                        if (unencryptedChar == SYMBOLS[j]) {

                            secretChar = SYMBOLS[j];
                            bufferedWriter.append((char) secretChar);
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
        encryption(pathFrom, pathTo,  key);
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
            encryption(pathFrom, pathTo,  key);
            key++;
        }

    }

    public static void decryptionWithStatistic(Path path) {


    }

    public static void checkPaths(String fileInit, String file_encrypt, String file_decrypt) {

        if (file_encrypt == null) {
            throw new NullPointerException("path equals null");
        }
    }
}
