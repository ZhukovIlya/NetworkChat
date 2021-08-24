package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

public class InMessageRunnable implements Runnable {
    private User user;
    private BufferedReader in;

    public InMessageRunnable(User user, Socket socket) {
        this.user = user;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            this.user.log("Ошибка создания in");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line;
            while (true) {
                while ((line = in.readLine()) != null){
                    Consumer<String> msg = x -> user.inMessage(x);
                    msg.accept(line);
                }
            }
        } catch (IOException e) {
            user.log("Ошибка отправки сообщения");
            e.printStackTrace();
        }
    }
}
