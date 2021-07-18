package ru.netology;

import java.io.*;
import java.net.Socket;

public class ConnectedUser implements Runnable {
    private Server server;
    private PrintWriter out;
    private BufferedReader in;


    public ConnectedUser(Server server, Socket socket) {
        this.server = server;
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
            server.sendToAll("Новый участник вошел в чат.", null);
            String msg;
            while (true) {
                while ((msg = in.readLine()) != null) {
                    if ("exit".equals(msg)) {
                        break;
                    }
                    server.sendToAll( msg, this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            server.removeClient(this);
        }

    }
}

