package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InMessageRunnable implements Runnable {
    private User user;
    private BufferedReader in;

    public InMessageRunnable(User user, Socket socket) {
        this.user = user;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            String line;
            while (true) {
                while ((line = in.readLine()) != null){
                    user.inMessage(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
