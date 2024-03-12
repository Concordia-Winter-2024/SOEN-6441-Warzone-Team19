package com.warzone.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
* Test cases for GameInitialization
*/
class GameInitializationTest {
    GameInitialization gameInitialization;

    @BeforeEach
    public void setUp() {
        gameInitialization = new GameInitialization();
    }

    @AfterEach
    public void tearDown() {
        gameInitialization = null;
    }

    /**
    * Test to verify if the command entered is correct or not
    */
    @Test
    public void testGetCommand() {
        java.lang.String input = "test command";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        gameInitialization.l_scanner = new Scanner(System.in);

        String[] result = gameInitialization.getCommand();
        assertArrayEquals(new String[]{"test", "command"}, result);
    }
}
