package ru.javarush.sergeyivanov.cryptoanalyser;

public class Checks {

    public static void ofPath(String fileInit, String file_encrypt, String file_decrypt) {

        if (file_encrypt == null) {
            throw new NullPointerException("path equals null");
        }
    }
}
