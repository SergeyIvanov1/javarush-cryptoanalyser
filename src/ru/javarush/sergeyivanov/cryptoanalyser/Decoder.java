package ru.javarush.sergeyivanov.cryptoanalyser;

public class Decoder {

    private Decoder() {
    }

    public static void decryptionWithKey(String pathFrom, String pathTo, int key) {
        key *= -1;
        Coder.encryption(pathFrom, pathTo, key);
    }

    public static void manualDecryptionBruteForce(String pathFrom, String pathTo) {

        for (int key = 1; key < TextProcessing.choiceOfAlphabet(TextProcessing.language).length; key++) {

            String pathKey = getNewFileNameBF(key, pathTo);
            Coder.encryption(pathFrom, pathKey, key);
        }
    }

    public static void autoDecryptionBruteForce(String pathFrom, String pathTo) {

        for (int key = 1; key < TextProcessing.choiceOfAlphabet(TextProcessing.language).length; key++) {

            Coder.encryption(pathFrom, pathTo, key);

            if (Checks.autoSelectOfCorrectDecryption(pathTo)) {
                break;
            }
        }
    }

    public static void manualDecryptionWithStatistic(String pathFrom, String pathTo) {

        char[] chars = TextProcessing.getArrayGreatestFrequentLettersOfAlphabets(TextProcessing.language);
        int indexOfMostFrequentLetterOfText = TextProcessing.getIndex(TextProcessing.getMostFrequentLetterOfText(pathFrom),
                TextProcessing.language);

        for (int ind = 0; ind < chars.length; ind++) {

            int foundKey = indexOfMostFrequentLetterOfText
                    - TextProcessing.getIndex(chars[ind], TextProcessing.language);

            String pathKey = getNewFileNameSA(foundKey, pathTo);

            decryptionWithKey(pathFrom, pathKey, foundKey);
        }
    }

    public static void autoDecryptionWithStatistic(String pathFrom, String pathTo) {

        char[] chars = TextProcessing.getArrayGreatestFrequentLettersOfAlphabets(TextProcessing.language);
        int indexOfMostFrequentLetterOfText = TextProcessing.getIndex(
                TextProcessing.getMostFrequentLetterOfText(pathFrom), TextProcessing.language);

        for (int ind = 0; ind < chars.length; ind++) {

            int foundKey = indexOfMostFrequentLetterOfText
                    - TextProcessing.getIndex(chars[ind], TextProcessing.language);

            decryptionWithKey(pathFrom, pathTo, foundKey);

            if (Checks.autoSelectOfCorrectDecryption(pathTo)) {
                break;
            }
        }
    }

    public static String getNewFileNameBF(int key, String pathTo) {
        return pathTo + System.getProperty("file.separator") + "BruteForce_key" + key + ".txt";
    }

    public static String getNewFileNameSA(int key, String pathTo) {
        return pathTo + System.getProperty("file.separator") + "StatisticAnalise_key" + key + ".txt";
    }

}
