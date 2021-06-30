package ru.netology;


import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private ArrayList<ConnectedUser> connectedUsers = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        int PORT = getSetting();


        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                ConnectedUser connectedUser = new ConnectedUser(this, serverSocket.accept());
                // TODO добавить логирование
                connectedUsers.add(connectedUser);
                new Thread(connectedUser).start();
            }

        } catch (IOException e) {
            // TODO добавить логирование
            e.printStackTrace();
        }

    }

    public synchronized void sendToAll(String msg, ConnectedUser thisUser) {
        //TODO добавить логирование
        for (ConnectedUser connectedUser : connectedUsers) {
            if (connectedUser != thisUser) {
                connectedUser.sendMessage(msg);
            }
        }
    }

    static int getSetting() {
        String s = "";
        Scanner in = null;
        try {
            in = new Scanner(new File("src/main/resources/settings.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(in.hasNext()) {
            s += in.nextLine();
        }
        in.close();

        s = s.substring(5);
        return Integer.parseInt(s);
    }
}

