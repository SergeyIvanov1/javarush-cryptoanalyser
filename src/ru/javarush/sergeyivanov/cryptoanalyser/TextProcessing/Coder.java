package ru.javarush.sergeyivanov.cryptoanalyser.TextProcessing;

import ru.javarush.sergeyivanov.cryptoanalyser.Exceptions.PathProcessingException;
import ru.javarush.sergeyivanov.cryptoanalyser.Exceptions.ReadWrightFileException;
import ru.javarush.sergeyivanov.cryptoanalyser.TextProcessing.TextProcessing;

import java.io.*;

public class Coder {

    private static final String FILE_NOT_FOUND = "File: \"%s\" not found\n";
    private static final String INVALID_READ_ACCESS_TO_THE_FILE_S = "Invalid read access to the file: \"%s\"";

    private Coder() {
    }

    public static void encryption(String pathFrom, String pathTo, int key) {

        try (FileInputStream fileInputStream = new FileInputStream(pathFrom);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            try (FileOutputStream fileOutputStream = new FileOutputStream(pathTo);
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

                            char[] arrayAlphabet = TextProcessing.choiceOfAlphabet(TextProcessing.language);

                            int secretCharInd = (index + key) % arrayAlphabet.length;
                            if (secretCharInd < 0) {
                                secretCharInd = arrayAlphabet.length - Math.abs(secretCharInd);
                            }

                            wantedChar = arrayAlphabet[secretCharInd];

                            if (flagUpperCase) {
                                bufferedWriter.append(Character.toUpperCase(wantedChar));
                            } else {
                                bufferedWriter.append(wantedChar);
                            }
                        }

                    } else {

                        char[] array = TextProcessing.choiceOfAlphabet(TextProcessing.ALPHABET_OF_SYMBOLS);

                        for (int j = 0; j < array.length; j++) {
                            if (unencryptedChar == array[j]) {

                                wantedChar = array[j];
                                bufferedWriter.append(wantedChar);
                            }
                        }
                    }
                }
                bufferedWriter.flush();

            } catch (FileNotFoundException e) {

                String message = String.format(FILE_NOT_FOUND, pathTo);
                throw new PathProcessingException(message, e);

            } catch (SecurityException e) {

                String message = String.format(INVALID_READ_ACCESS_TO_THE_FILE_S, pathTo);
                throw new PathProcessingException(message, e);

            } catch (IOException e) {

                String message = "An Input error occurs with file \"" + pathTo + "\"";
                throw new ReadWrightFileException(message, e);
            }

        } catch (FileNotFoundException e) {

            String message = String.format(FILE_NOT_FOUND, pathFrom);
            throw new PathProcessingException(message, e);

        } catch (SecurityException e) {

            String message = String.format(INVALID_READ_ACCESS_TO_THE_FILE_S, pathFrom);
            throw new PathProcessingException(message, e);

        } catch (IOException e) {

            String message = "An Output error occurs with file \"" + pathFrom + "\"";
            throw new ReadWrightFileException(message, e);
        }
    }
}
