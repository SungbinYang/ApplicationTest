package me.sungbin.prejavatest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void create() {
        App app = new App();
        assertNotNull(app);
    }
}