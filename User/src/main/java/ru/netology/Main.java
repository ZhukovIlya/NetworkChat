package ru.netology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    private static final String FILE_NAME = "userFile.log";
    public static void main(String[] args) {

        try {
            User user = new User();
        } catch (IOException e) {
            log("ошибка при создании User");
            e.printStackTrace();
        }
    }
    private static void log(String msg) {
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        File log = new File(FILE_NAME);
        try (FileWriter fw = new FileWriter(log, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.format("[%s] %s\n", dateNow, msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
