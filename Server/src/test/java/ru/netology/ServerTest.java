package ru.netology;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.Server.getSetting;

class ServerTest {

    @Test
    void main() {
        int s;
        s = getSetting();
        System.out.println(s);

    }

    @Test
    void sendToAll() {
    }
}