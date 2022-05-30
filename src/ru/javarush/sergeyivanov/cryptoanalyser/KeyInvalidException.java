package ru.javarush.sergeyivanov.cryptoanalyser;

public class KeyInvalidException extends RuntimeException {

    public KeyInvalidException() {}

    public KeyInvalidException(String message) {super(message);
    }

    public KeyInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
