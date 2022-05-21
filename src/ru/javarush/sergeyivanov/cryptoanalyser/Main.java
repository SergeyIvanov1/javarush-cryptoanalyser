package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static int unencryptedChar; //незашифрованный символ
    private static int unencryptedCharInd; // unencryptedChar, который находится на позиции с индексом "i"
    private static int secretCharInd; // secretChar, который находится на позиции с индексом "i"
    private static char secretChar; // зашифрованный символ
    private static int key; // секретный ключ
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    private static final char[] SYMBOLS = {'.', ',', '"', '\'', ':', '-', '!', '?', ' '};


    public static void main(String[] args) throws IOException {
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
        String pathFromFile = "file";
        String pathToFile = "file2";

        checkPaths(pathFromFile, pathToFile);
        encryption(pathFromFile, pathToFile, 5);

    }

    public static void encryption(String pathFromFile, String pathToFile, int key) throws IOException {

        try (FileInputStream fileInputStream = new FileInputStream(pathFromFile);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
             FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
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

                                secretCharInd = (i + key) % 33;
                                secretChar = ALPHABET[secretCharInd];

                                bufferedWriter.append(Character.toUpperCase(secretChar));
                            }
                        }

                        if (unencryptedChar == ALPHABET[i]) {

                            secretCharInd = (i + key) % 33;
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
        }

    }

    public static void decryption(Path path) {


    }

    public static void bruteForceCriptanalyze(Path path) {


    }

    public static void staticCriptanalyze(Path path) {


    }

    public static void checkPaths(String pathFromFile, String pathToFile) {

        if (pathToFile == null) {
            throw new NullPointerException("path equals null");
        }
    }
}
