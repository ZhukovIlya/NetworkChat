package ru.netology;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static final String SETTINGS = "settings.txt";
    private static final String SERVER_FILE = "serverFile.log";
    private volatile List<ConnectedUser> connectedUsers = new ArrayList<>();
    int PORT = getSetting("PORT");

    public Server() {
        Socket userSocket = null;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log("Сервер запущен");
            while (true) {
                userSocket = serverSocket.accept();
                ConnectedUser connectedUser = new ConnectedUser(this, userSocket);
                connectedUsers.add(connectedUser);
                new Thread(connectedUser).start();
            }

        } catch (IOException e) {
            log("Ошибка serverSocket");
            e.printStackTrace();
        }

    }

    public synchronized void sendToAll(String msg, ConnectedUser thisUser) {
        log(msg);
        for (ConnectedUser connectedUser : connectedUsers) {
            if (connectedUser != thisUser) {
                connectedUser.sendMessage(msg);
            }
        }
    }

    public static int getSetting(String st) {
        String s = "";
        Scanner in;
        in = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(SETTINGS));
        while (in.hasNext()) {
            s += in.nextLine();
        }
        in.close();
        s = s.substring(st.length() + 1);
        return Integer.parseInt(s);
    }

    public void removeClient(ConnectedUser client) {
        connectedUsers.remove(client);
    }

    public static void log(String msg) {
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        System.out.printf("[%s] %s\n", dateNow, msg);

        File log = new File(SERVER_FILE);
        try (FileWriter fw = new FileWriter(log, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.format("[%s] %s\n", dateNow, msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


