package ru.javarush.sergeyivanov.cryptoanalyser;

public class Main {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input the key");
//        String keyLine = scanner.nextLine();
//        int key = Integer.valueOf(keyLine);
//
//
//
//        System.out.println("Input the path to the file containing the text");
//        String pathToFile = scanner.nextLine();
        int key = -28;
        String pathFrom = "file";
        String pathTo = "file_encrypt";
        String fileDecrypt = "file_decrypt";
        String fileForInstance = "fileForInstance";

        Coder.encryption(pathFrom, pathTo, key);
//        Decoder.decryptionWithKey(pathTo, fileDecrypt, key);
//        Decoder.decryptionBruteForce(pathTo, fileDecrypt, fileForInstance);
        Decoder.decryptionWithStatistic(pathTo, fileForInstance);
//        Checks.forComplianceWithFrequentWords();


    }
}
