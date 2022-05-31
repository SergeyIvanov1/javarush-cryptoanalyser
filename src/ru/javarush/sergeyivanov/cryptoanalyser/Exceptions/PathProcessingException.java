package ru.javarush.sergeyivanov.cryptoanalyser.Exceptions;

public class PathProcessingException extends RuntimeException {

    public PathProcessingException(){}

    public PathProcessingException(String message) {super(message);}

    public PathProcessingException(String message, Throwable cause) {super(message, cause);}
}
