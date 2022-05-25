package ru.javarush.sergeyivanov.cryptoanalyser;

import java.util.Scanner;

public class Dialog {

    private static String pathFrom;
    private static String pathTo;
    private static String key;
    private static final String QUERYCONTINUATION = "Функция завершена."
            + "Для продолжения программы напишите номер пункта меню, а для выхода \"exit\"";

    private Dialog() {}

    public static void start(){
        Scanner console = new Scanner(System.in);

        System.out.println("Программа криптоаланализатор.\n"
                + "Выберите пункт меню:\n"
                + "1 - Зашифровать текст\n"
                + "2 - Расшифровать текст, используя известный ключ\n"
                + "3 - Расшифровать текст, используя метод \"Грубой силы\", путем ручного подбора\n"
                + "4 - Расшифровать текст, используя метод \"Грубой силы\", путем автоматического подбора\n"
                + "5 - Расшифровать текст, используя метод \"Статического анализа\", путем ручного подбора\n"
                + "6 - Расшифровать текст, используя метод \"Статического анализа\", путем автоматического подбора\n");

        String choice = null;
        while (!(choice = console.nextLine()).equalsIgnoreCase("exit")) {
            switch (choice) {
                case "1":
                    String selectFunction = "шифрования";
                    Dialog.requestInfo(console, selectFunction);

                    Coder.encryption(pathFrom, pathTo, Integer.parseInt(key));

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "2":
                    selectFunction = "расшифровки";
                    Dialog.requestInfo(console, selectFunction);

                    Decoder.decryptionWithKey(pathFrom, pathTo, Integer.parseInt(key));

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "3":
                    selectFunction = "расшифровки";
                    Dialog.requestInfo(console, selectFunction);

                    Decoder.manualDecryptionBruteForce(pathFrom, pathTo);

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "4":
                    selectFunction = "расшифровки";
                    Dialog.requestInfo(console, selectFunction);

                    Decoder.autoDecryptionBruteForce(pathFrom, pathTo);

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "5":
                    selectFunction = "расшифровки";
                    Dialog.requestInfo(console, selectFunction);

                    Decoder.manualDecryptionWithStatistic(pathFrom, pathTo);

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "6":
                    selectFunction = "расшифровки";
                    Dialog.requestInfo(console, selectFunction);

                    Decoder.autoDecryptionWithStatistic(pathFrom, pathTo);

                    System.out.println(QUERYCONTINUATION);
                    break;

                default:
                    System.out.println("Программа ожидает номер пункта меню либо \"exit\"");
            }
        }
    }

    private static void requestInfo (Scanner console, String value){
        System.out.println("Введите адрес файла в формате .txt, в котором находится текст для " + value);
        pathFrom = console.nextLine();
        Checks.ofPath(pathFrom);

        System.out.println("Введите адрес файла в формате .txt,"
                + "в который необходимо сохранить текст после " + value);
        pathTo = console.nextLine();
        Checks.ofPath(pathTo);

        System.out.println("Введите ключ");
        key = console.nextLine();
        while (Checks.notKey(key)) {
            key = console.nextLine();
        }
    }
}
