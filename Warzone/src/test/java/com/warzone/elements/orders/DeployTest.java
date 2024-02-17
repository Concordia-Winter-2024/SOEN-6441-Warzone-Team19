package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeployTest {
    GameEngine gameEngine;
    Player player;
    Deploy deploy;

    @BeforeEach
    public void setUp() {
        gameEngine = new GameEngine();
        player = new Player("John");
        gameEngine.addPlayer(String.valueOf(player));
        gameEngine.loadMap("risk.map");
        deploy = new Deploy(player, 1, 5);
    }

    @AfterEach
    public void tearDown() {
        gameEngine = null;
        player = null;
        deploy = null;
    }

    @Test
    public void testExecute() {
        // Test when player does not control the country
        String result = deploy.execute(gameEngine);
        assertEquals(String.format("Player \"%s\" does not control country \"%d\"", player.getName(), 1), result);

        // Assign country to player and test when player does not have enough armies
        player.addCountry(gameEngine.getGameMap().getCountries().get(1));
        result = deploy.execute(gameEngine);
        assertEquals(String.format("Player \"%s\" does not enough armies", player.getName()), result);
    }
}