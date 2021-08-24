package ru.netology;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.Server.*;

class ServerTest {

    @Test
    void main() {
        int expected = 23433;
        int actual = getSetting("PORT");
        assertEquals(expected, actual);
    }
    @Test
    void logTest() throws IOException {
        String expected = "Test";
        log(expected);
        File file = new File("serverFile.log");
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = in.readLine();
        String actual = line.substring(line.length() - expected.length());
        assertEquals(expected,actual);
    }


}
