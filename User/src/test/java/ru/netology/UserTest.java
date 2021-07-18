package ru.netology;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.User.getSettingUser;

class UserTest {

    @Test
    void getSettingUserTestPORT() {
        String s = getSettingUser("PORT");
        System.out.println((s));
    }
    @Test
    void getSettingUserTestIP() {
        String s = getSettingUser("IP");
        System.out.println((s));
    }

}