package ru.javarush.sergeyivanov.cryptoanalyser;

import java.util.Scanner;

public class Dialog {

    private static String pathFrom;
    private static String pathTo;
    private static String key;
    private static final String SELECTED_FUNCTION_ENCODE = "шифрования";
    private static final String SELECTED_FUNCTION_DECODE = "расшифровки";
    private static final String QUERY_OF_CONTINUATION = "Функция завершена. "
            + "Для продолжения программы выберите пункт меню и напишите его номер, а для выхода \"exit\"";

    private Dialog() {
    }

    public static void start() {
        Scanner console = new Scanner(System.in);

        System.out.println("Программа \"Криптоанализатор\".\n");

        System.out.println("Выберите язык текста для шифрования/расшифрования:" +
                " английский или русский (введите \"en\" или \"ru\")");

//        while (true) {
//            String answer = console.nextLine();
//
//            if (answer.equalsIgnoreCase("en")) {
//                TextProcessing.language = "Latin";
//                break;
//            } else if (answer.equalsIgnoreCase("ru")) {
//                TextProcessing.language = "Cyrillic";
//                break;
//            } else {
//                System.out.println("введите \"en\" или \"ru\"");
//            }
//        }

        System.out.println("\nВыберите пункт меню:\n"
                + "1 - Зашифровать текст\n"
                + "2 - Расшифровать текст, используя известный ключ\n"
                + "3 - Расшифровать текст, используя метод \"Грубой силы\", путем ручного подбора\n"
                + "4 - Расшифровать текст, используя метод \"Грубой силы\", путем автоматического подбора\n"
                + "5 - Расшифровать текст, используя метод \"Статического анализа\", путем ручного подбора\n"
                + "6 - Расшифровать текст, используя метод \"Статического анализа\", путем автоматического подбора\n");

        String choice;
        while (!(choice = console.nextLine()).equalsIgnoreCase("exit")) {
            switch (choice) {
                case "1":
                    try {

                        Dialog.requestPath(console, SELECTED_FUNCTION_ENCODE);
                    } catch (PathProcessingException ex) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(1);
                    }

                    try {

                        Dialog.requestKey(console);
                    } catch (KeyInvalidException e) {

                        System.out.println("\nWrong key: " + key);
                        System.err.println("Error details: " + e.getMessage());
                        System.exit(2);
                    }

                    try {
                        Coder.encryption(pathFrom, pathTo, Integer.parseInt(key));
                    } catch (PathProcessingException e) {

                        System.out.println("Path specified wrong.");
                        System.err.println("Error details " + e.getMessage());
                        System.exit(3);

                    } catch (ReadWrightFileException ex) {

                        System.out.println("\nError reading/writing file");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(4);
                    }

                    System.out.println(QUERY_OF_CONTINUATION);
                    break;

                case "2":
                    try {

                        Dialog.requestPath(console, SELECTED_FUNCTION_DECODE);
                    } catch (PathProcessingException ex) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(1);
                    }

                    try {

                        Dialog.requestKey(console);
                    } catch (KeyInvalidException e) {

                        System.out.println("\nWrong key: " + key);
                        System.err.println("Error details: " + e.getMessage());
                        System.exit(2);
                    }

                    try {
                        Decoder.decryptionWithKey(pathFrom, pathTo, Integer.parseInt(key));
                    } catch (PathProcessingException e) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details " + e.getMessage());
                        System.exit(3);

                    } catch (ReadWrightFileException ex) {

                        System.out.println("\nError reading/writing file");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(4);
                    }

                    System.out.println(QUERY_OF_CONTINUATION);
                    break;

                case "3":

                    try {

                        Dialog.requestFileAddress(console);
                        Dialog.requestDirectoryAddress(console);
                    } catch (PathProcessingException ex) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(1);
                    }

                    try {
                        Decoder.manualDecryptionBruteForce(pathFrom, pathTo);
                    } catch (PathProcessingException e) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details " + e.getMessage());
                        System.exit(3);

                    } catch (ReadWrightFileException ex) {

                        System.out.println("\nError reading/writing file");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(4);
                    }

                    System.out.println(QUERY_OF_CONTINUATION);
                    break;

                case "4":

                    try {

                        Dialog.requestPath(console, SELECTED_FUNCTION_DECODE);
                    } catch (PathProcessingException ex) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(1);
                    }

                    try {

                        Decoder.autoDecryptionBruteForce(pathFrom, pathTo);
                    } catch (PathProcessingException e) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details " + e.getMessage());
                        System.exit(3);

                    } catch (ReadWrightFileException ex) {

                        System.out.println("\nError reading/writing file");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(4);
                    }

                    System.out.println(QUERY_OF_CONTINUATION);
                    break;

                case "5":

                    try {

                        Dialog.requestFileAddress(console);
                        Dialog.requestDirectoryAddress(console);
                    } catch (PathProcessingException ex) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(1);
                    }

                    try {
                        Decoder.manualDecryptionWithStatistic(pathFrom, pathTo);
                    } catch (PathProcessingException e) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details " + e.getMessage());
                        System.exit(3);

                    } catch (ReadWrightFileException ex) {

                        System.out.println("\nError reading/writing file");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(4);
                    }

                    System.out.println(QUERY_OF_CONTINUATION);
                    break;

                case "6":

                    try {

                        Dialog.requestPath(console, SELECTED_FUNCTION_DECODE);
                    } catch (PathProcessingException ex) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(1);
                    }

                    try {
                        Decoder.autoDecryptionWithStatistic(pathFrom, pathTo);
                    } catch (PathProcessingException e) {

                        System.out.println("\nPath specified wrong.");
                        System.err.println("Error details " + e.getMessage());
                        System.exit(3);

                    } catch (ReadWrightFileException ex) {

                        System.out.println("\nError reading/writing file");
                        System.err.println("Error details: " + ex.getMessage());
                        System.exit(4);
                    }

                    System.out.println(QUERY_OF_CONTINUATION);
                    break;

                default:
                    System.out.println("Программа ожидает номер пункта меню либо \"exit\" для выхода");
            }
        }
    }

    private static void requestPath(Scanner console, String selectFunction) {
        System.out.println("Введите адрес файла в формате .txt, в котором находится текст для " + selectFunction);
        pathFrom = console.nextLine();
        Checks.whetherPathIsFile(pathFrom);

        System.out.println("Введите адрес файла в формате .txt, "
                + "в который необходимо сохранить текст после " + selectFunction);
        pathTo = console.nextLine();
        Checks.whetherPathIsFile(pathTo);
    }

    private static void requestFileAddress(Scanner console) {
        System.out.println("Введите адрес файла в формате .txt, в котором находится текст для расшифровки");
        pathFrom = console.nextLine();
        Checks.whetherPathIsFile(pathFrom);
    }

    private static void requestDirectoryAddress(Scanner console) {
        System.out.println("Введите адрес папки, в которую необходимо сохранить результат расшифровки");
        pathTo = console.nextLine();
        Checks.whetherPathIsDirectory(pathTo);
    }

    private static void requestKey(Scanner console) {
        System.out.println("Введите ключ");
        key = console.nextLine();
        Checks.notKey(key, TextProcessing.language);
    }
}
