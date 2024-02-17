package com.warzone.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testGetCommand() {
        String input = "test command";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        gameInitialization.l_scanner = new Scanner(System.in);

        String[] result = gameInitialization.getCommand();
        assertArrayEquals(new String[]{"test", "command"}, result);
    }
}