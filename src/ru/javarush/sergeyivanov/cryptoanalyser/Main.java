package ru.javarush.sergeyivanov.cryptoanalyser;

public class Main {

    public static void main(String[] args) {

//        Dialog.start();

//        int key = 65;
        String pathFrom = "file_encrypt.txt";
        String pathTo = "fileForInstance.txt";
//        Decoder.manualDecryptionBruteForce(pathFrom, pathTo);
        Decoder.autoDecryptionBruteForce(pathFrom, pathTo);
//
    }
}
