package ru.javarush.sergeyivanov.cryptoanalyser;

public class Main {

    public static void main(String[] args) {

//        Dialog.start();

        int key = -28;
        String pathFrom = "file";
        String pathTo = "file_encrypt";
        String fileDecrypt = "file_decrypt";
        String fileForInstance = "fileForInstance";

        Coder.encryption(pathFrom, pathTo, key);
//        Decoder.decryptionWithKey(pathTo, fileDecrypt, key);
//        Decoder.decryptionBruteForce(pathTo, fileDecrypt, fileForInstance);
        Decoder.manualDecryptionWithStatistic(pathTo, fileForInstance);
//        Checks.forComplianceWithFrequentWords();
    }
}
