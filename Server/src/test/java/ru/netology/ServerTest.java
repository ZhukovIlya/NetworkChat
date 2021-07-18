package ru.netology;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.Server.getSetting;

class ServerTest {

    @Test
    void main() {
        int expected = 23432;
        int actual = getSetting();

        assertEquals(expected, actual);

    }

}