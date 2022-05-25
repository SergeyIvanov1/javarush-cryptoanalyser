package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;

public class Coder {

    public static void encryption(String pathFrom, String pathTo, int key) {

        try (FileInputStream fileInputStream = new FileInputStream(pathFrom);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
             FileOutputStream fileOutputStream = new FileOutputStream(pathTo);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {

            //незашифрованный символ
            int unencryptedChar;
            while ((unencryptedChar = bufferedReader.read()) != -1) {

                // зашифрованный символ
                char wantedChar;
                if (Character.isLetter(unencryptedChar)) {

                    boolean flagUpperCase = Character.isUpperCase(unencryptedChar);
                    if (flagUpperCase) {
                        unencryptedChar = (char) Character.toLowerCase(unencryptedChar);
                    }

                    int index = Alphabets.indexOfRussian((char) unencryptedChar);
                    if (index >= 0) {

                        // secretChar, который находится на позиции с индексом "i"
                        int secretCharInd = (index + key) % Alphabets.getRussian().length;
                        if (secretCharInd < 0) {
                            secretCharInd = Alphabets.getRussian().length - Math.abs(secretCharInd);
                        }


                        wantedChar = Alphabets.getRussian()[secretCharInd];

                        if (flagUpperCase) {
                            bufferedWriter.append(Character.toUpperCase(wantedChar));
                        } else {
                            bufferedWriter.append(wantedChar);
                        }
                    }

                } else {

                    for (int j = 0; j < Alphabets.SYMBOLS.length; j++) {
                        if (unencryptedChar == Alphabets.SYMBOLS[j]) {

                            wantedChar = Alphabets.SYMBOLS[j];
                            bufferedWriter.append(wantedChar);
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
}
