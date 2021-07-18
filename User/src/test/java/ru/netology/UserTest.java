package ru.netology;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.User.getSettingUser;

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

}