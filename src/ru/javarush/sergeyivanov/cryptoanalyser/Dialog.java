package ru.javarush.sergeyivanov.cryptoanalyser;

import java.util.Scanner;

public class Dialog {
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
        switch (console.nextLine()) {
            case "1" :
                System.out.println("Введите адрес файла в формате .txt, в котором находится текст для шифрования");

                String path = console.nextLine();
                Checks.ofPath(path);

                System.out.println("Введите ключ");
                String key = console.nextLine();
                while (Checks.notKey(key)) {
                    key = console.nextLine();
                }

        }
    }
}
