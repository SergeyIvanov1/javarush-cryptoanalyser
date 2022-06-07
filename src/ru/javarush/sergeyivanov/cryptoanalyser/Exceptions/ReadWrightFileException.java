package ru.javarush.sergeyivanov.cryptoanalyser.Exceptions;

public class ReadWrightFileException extends RuntimeException{

    public ReadWrightFileException() {}

    public ReadWrightFileException(String message) {super(message);}

    public ReadWrightFileException(String message, Throwable cause) {super(message, cause);}
}
