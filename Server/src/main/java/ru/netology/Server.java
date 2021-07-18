package ru.netology;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private ArrayList<ConnectedUser> connectedUsers = new ArrayList<>();
    int PORT = getSetting();

    public static void main(String[] args) {
        Server server = new Server();
    }

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

    static int getSetting() {
        String s = "";
        Scanner in;
        in = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.txt"));
        while (in.hasNext()) {
            s += in.nextLine();
        }
        in.close();
        s = s.substring(5);
        return Integer.parseInt(s);
    }

    public void removeClient(ConnectedUser client) {
        connectedUsers.remove(client);
    }

    void log(String msg) {
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        System.out.printf("[%s] %s\n", dateNow, msg);

        File log = new File("serverFile.log");
        try (FileWriter fw = new FileWriter(log, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.format("[%s] %s\n", dateNow, msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


