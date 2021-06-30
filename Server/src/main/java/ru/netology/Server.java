package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Properties;

public class Server {
    private ArrayList<ConnectedUser> connectedUsers = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        final int PORT = getSetting("port");


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


    private static int getSetting(String setting) {
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
        return Integer.parseInt(settingProps.getProperty(setting));
    }
}

