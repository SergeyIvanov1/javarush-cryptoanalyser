package ru.javarush.sergeyivanov.cryptoanalyser;

import java.io.*;

public class Coder {

    private Coder() {}

    public static void encryption(String pathFrom, String pathTo, int key) {

        try (FileInputStream fileInputStream = new FileInputStream(pathFrom);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
             FileOutputStream fileOutputStream = new FileOutputStream(pathTo);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {

            int unencryptedChar;
            while ((unencryptedChar = bufferedReader.read()) != -1) {

                char wantedChar;
                if (Character.isLetter(unencryptedChar)) {

                    boolean flagUpperCase = Character.isUpperCase(unencryptedChar);
                    if (flagUpperCase) {
                        unencryptedChar = (char) Character.toLowerCase(unencryptedChar);
                    }

                    int index = Alphabets.getIndex((char) unencryptedChar, Alphabets.language);
                    if (index >= 0) {

                        int secretCharInd = (index + key) % Alphabets.choiceOfAlphabet(Alphabets.language).length;
                        if (secretCharInd < 0) {
                            secretCharInd = Alphabets.choiceOfAlphabet(Alphabets.language).length
                                    - Math.abs(secretCharInd);
                        }


                        wantedChar = Alphabets.choiceOfAlphabet(Alphabets.language)[secretCharInd];

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
