package ru.netology;


import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class User {
    private static final String SETTINGS_USER = "settingsUser.txt";
    private static final String USER_FILE = "userFile.log";
    public final int PORT = Integer.parseInt(getSettingUser("PORT"));

    private static final String IP = getSettingUser("IP");
    Scanner scanner = new Scanner(System.in);


    public User() throws IOException {
        String name;
        System.out.println("Введите имя: ");
        name = scanner.nextLine();

        Socket socket = new Socket(IP, PORT);
        new Thread(new InMessageRunnable(this, socket)).start();
        System.out.println("Для выхода из чата введите /exit");
        try (PrintWriter out = new PrintWriter((new OutputStreamWriter(socket.getOutputStream())), true)) {
            String line;
            while (true) {
                line = scanner.nextLine();
                if ("/exit".equals(line)) {
                    out.printf("Польхователь %s вышел", name);
                    break;
                } else {
                    out.println(name + " : " + line);
                    log(name + " : " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            socket.close();
        }
    }

    public static void inMessage(String msg) {
        System.out.println(msg);
        log(msg);
    }


    public static void log(String msg) {
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        File log = new File(USER_FILE);
        try (FileWriter fw = new FileWriter(log, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.format("[%s] %s\n", dateNow, msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSettingUser(String st) {
        String s = "";
        Scanner in;
        in = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(SETTINGS_USER));
        while (in.hasNext()) {
            s += in.nextLine();
        }
        in.close();
        if ("PORT".equals(st)) {
            s = s.substring(s.lastIndexOf((st + "=")) + 5, 10);
        } else {
            s = s.substring(s.lastIndexOf((st + "=")) + 3, 22);
        }
        return s;
    }

}



