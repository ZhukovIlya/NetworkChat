package ru.netology;

import java.io.*;
import java.net.Socket;

public class ConnectedUser implements Runnable {
    private Socket socket = new Socket();
    private Server server = new Server();
    private PrintWriter out;
    private BufferedReader in;
    private String name;


    public ConnectedUser(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            this.sendMessage("Введите имя");
            name = in.readLine();
            server.sendToAll(name + " подключился к чату", this);
            //TODO добавить логирование
            String msg;
            while (true) {
                while ((msg = in.readLine()) != null) {
                    if ("exit".equals(msg)) {
                        break;
                    }
                    server.sendToAll(name + ": " + msg, this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

