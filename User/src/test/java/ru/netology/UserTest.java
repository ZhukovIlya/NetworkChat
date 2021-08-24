package ru.netology;


import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.User.*;

class UserTest {

    @Test
    void getSettingUserTestPORT() {
        int expected = 23433;
        int actual = Integer.parseInt(getSettingUser("PORT"));

        assertEquals(expected, actual);
    }

    @Test
    void getSettingUserTestIP() {
        String expected = "localhost";
        String actual = getSettingUser("IP");
        assertEquals(expected, actual);
    }

    @Test
    void logTest() throws IOException {
        String expected = "Test";
        log(expected);
        File file = new File("userFile.log");
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = in.readLine();
        String actual = line.substring(line.length() - expected.length());
        assertEquals(expected,actual);
    }


}