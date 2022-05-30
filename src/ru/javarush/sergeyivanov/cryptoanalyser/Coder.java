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

                    int index = TextProcessing.getIndex((char) unencryptedChar, TextProcessing.language);
                    if (index >= 0) {

                        int secretCharInd = (index + key) % TextProcessing.choiceOfAlphabet(TextProcessing.language).length;
                        if (secretCharInd < 0) {
                            secretCharInd = TextProcessing.choiceOfAlphabet(TextProcessing.language).length
                                    - Math.abs(secretCharInd);
                        }

                        wantedChar = TextProcessing.choiceOfAlphabet(TextProcessing.language)[secretCharInd];

                        if (flagUpperCase) {
                            bufferedWriter.append(Character.toUpperCase(wantedChar));
                        } else {
                            bufferedWriter.append(wantedChar);
                        }
                    }

                } else {

                    for (int j = 0; j < TextProcessing.SYMBOLS.length; j++) {
                        if (unencryptedChar == TextProcessing.SYMBOLS[j]) {

                            wantedChar = TextProcessing.SYMBOLS[j];
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
