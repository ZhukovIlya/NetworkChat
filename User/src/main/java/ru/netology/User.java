package ru.netology;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class User {
    public final int PORT = Integer.parseInt(getSetting("port"));

    private static final String IP = getSetting("localhost");
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            User user = new User();
        } catch (IOException e) {
            //TODO залогировать ошибку
            e.printStackTrace();
        }
    }

    public User() throws IOException {

        Socket socket = new Socket(IP, PORT);
        try (PrintWriter out = new PrintWriter((new OutputStreamWriter(socket.getOutputStream())), true)) {
            String msg;
            while (true) {
                msg = scanner.nextLine();
                if ("/exit".equals(msg)) {
                    out.println(msg);
                    break;
                }
                out.printf(msg);
                //TODO добавить логировние
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            socket.close();
        }
    }
    private static String getSetting(String setting) {
        String rootPath;
        try {
            rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        } catch (Exception e) {
            rootPath = "";
        }
        String settingsPath = rootPath + "settings.properties";

        Properties settingProps = new Properties();
        try {
            settingProps.load(new FileInputStream(settingsPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return settingProps.getProperty(setting);
    }
}



