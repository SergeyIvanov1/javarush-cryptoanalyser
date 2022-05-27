package ru.javarush.sergeyivanov.cryptoanalyser;

import java.util.Scanner;

public class Dialog {

    private static String pathFrom;
    private static String pathTo;
    private static String key;
    private static final String SELECTEDFUNCTIONENCODE = "шифрования";
    private static final String SELECTEDFUNCTIONDECODE = "расшифровки";
    private static final String QUERYCONTINUATION = "Функция завершена. "
            + "Для продолжения программы выберите пункт меню и напишите его номер, а для выхода \"exit\"";

    private Dialog() {}

    public static void start(){
        Scanner console = new Scanner(System.in);

        System.out.println("Программа криптоанализатор.\n");

        System.out.println("Выберите язык текста для шифрования/расшифрования:" +
                " английский или русский (введите \"en\" или \"ru\")");

        while (true) {
            String answer = console.nextLine();

            if (answer.equalsIgnoreCase("en")) {
                Alphabets.language = "Latin";
                break;
            } else if (answer.equalsIgnoreCase("ru")) {
                Alphabets.language = "Cyrillic";
                break;
            } else {
                System.out.println("введите \"en\" или \"ru\"");
            }
        }

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
                    Dialog.requestPath(console, SELECTEDFUNCTIONENCODE);
                    Dialog.requestKey(console);

                    Coder.encryption(pathFrom, pathTo, Integer.parseInt(key));

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "2":
                    Dialog.requestPath(console, SELECTEDFUNCTIONDECODE);
                    Dialog.requestKey(console);


                    Decoder.decryptionWithKey(pathFrom, pathTo, Integer.parseInt(key));

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "3":
                    Dialog.requestPath(console, SELECTEDFUNCTIONDECODE);

                    Decoder.manualDecryptionBruteForce(pathFrom, pathTo);

                    System.out.println("Результат работы программы сохранен в файлы по адресу: " + pathTo
                            + "+ key");

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "4":
                    Dialog.requestPath(console, SELECTEDFUNCTIONDECODE);

                    Decoder.autoDecryptionBruteForce(pathFrom, pathTo);

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "5":
                    Dialog.requestPath(console, SELECTEDFUNCTIONDECODE);

                    Decoder.manualDecryptionWithStatistic(pathFrom, pathTo);

                    System.out.println(QUERYCONTINUATION);
                    break;

                case "6":
                    Dialog.requestPath(console, SELECTEDFUNCTIONDECODE);

                    Decoder.autoDecryptionWithStatistic(pathFrom, pathTo);

                    System.out.println(QUERYCONTINUATION);
                    break;

                default:
                    System.out.println("Программа ожидает номер пункта меню либо \"exit\" для выхода");
            }
        }
    }

    private static void requestPath(Scanner console, String selectFunction) {
        System.out.println("Введите адрес файла в формате .txt, в котором находится текст для " + selectFunction);
        pathFrom = console.nextLine();
        Checks.ofPath(pathFrom);

        System.out.println("Введите адрес файла в формате .txt, "
                + "в который необходимо сохранить текст после " + selectFunction);
        pathTo = console.nextLine();
        Checks.ofPath(pathTo);
    }

    private static void requestKey(Scanner console){
        System.out.println("Введите ключ");
        key = console.nextLine();
        while (Checks.notKey(key, Alphabets.language)) {
            key = console.nextLine();
        }
    }
}
