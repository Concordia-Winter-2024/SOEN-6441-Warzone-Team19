package com.warzone.controller;

import com.warzone.elements.GameMap;
import com.warzone.elements.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    GameEngine d_gameEngine;
    String d_mapName1 = "risk.map";

    /**
     * This is the before method (fixture) for testing, which will run before any
     * other test function is being executed. This is used to instantiate the Class
     * which this tests.
     */
    @BeforeEach
    public void setUp() {
        d_gameEngine = new GameEngine();
    }

    /**
     * This is the after method (fixture) for testing, which will run after any
     * other test function is being executed. This is used to deallocate the member
     * variables.
     */
    @AfterEach
    public void tearDown() {
        d_gameEngine = null;
    }

    /**
     * This test function tests the loadMap function with multiple criteria.
     */
    @Test
    public void testLoadMap() {
        String l_resultString;
        l_resultString = d_gameEngine.loadMap(d_mapName1);
        assertEquals("Map \"" + d_mapName1 + "\" loaded successfully", l_resultString);
        assertNotNull(l_resultString);

        String l_resultString1;
        d_gameEngine = new GameEngine();
        d_mapName1 = "WorldMapFail.map";
        l_resultString1 = d_gameEngine.loadMap(d_mapName1);
        assertEquals(" The graph is not connected. Countries are not traverseble.", l_resultString1);
        assertNotNull(l_resultString);
    }

    /**
     * This test function tests the addPlayer function with multiple players being
     * added to the game.
     */
    @Test
    public void testAddPlayer() {
        String l_playerNameString = "John";
        String l_resultString1 = d_gameEngine.addPlayer(l_playerNameString);
        assertEquals("Player \"" + l_playerNameString + "\" added to game", l_resultString1);

        l_playerNameString = "John";
        String l_resultString2 = d_gameEngine.addPlayer(l_playerNameString);
        assertEquals("Player \"" + l_playerNameString + "\" already present in game", l_resultString2);

        l_playerNameString = "Doe";
        String l_resultString3 = d_gameEngine.addPlayer(l_playerNameString);
        assertEquals("Player \"" + l_playerNameString + "\" added to game", l_resultString3);
    }

    /**
     * This test function tests the removePlayer function with multiple players
     * being added first and then remove them from the game.
     */
    @Test
    public void testRemovePlayer() {
        String l_playerNameString;
        d_gameEngine.addPlayer("John");
        d_gameEngine.addPlayer("Doe");
        d_gameEngine.addPlayer("Smith");
        d_gameEngine.addPlayer("Jack");
        d_gameEngine.addPlayer("Arnold");
        d_gameEngine.addPlayer("Paul");

        l_playerNameString = "John";
        String l_resultString1 = d_gameEngine.removePlayer(l_playerNameString);
        assertEquals("Player \"" + l_playerNameString + "\" removed from game", l_resultString1);

        l_playerNameString = "John";
        String l_resultString2 = d_gameEngine.removePlayer(l_playerNameString);
        assertEquals("Player \"" + l_playerNameString + "\" not present in game", l_resultString2);

        l_playerNameString = "Paul";
        String l_resultString3 = d_gameEngine.removePlayer(l_playerNameString);
        assertEquals("Player \"" + l_playerNameString + "\" removed from game", l_resultString3);

        l_playerNameString = "Arnold";
        String l_resultString4 = d_gameEngine.removePlayer(l_playerNameString);
        assertEquals("Player \"" + l_playerNameString + "\" removed from game", l_resultString4);
    }

    /**
     * This function tests the object returned by the getGameMap function.
     */
    @Test
    public void testGetGameMap() {

        d_gameEngine.loadMap(d_mapName1);
        GameMap l_resultGameMap = d_gameEngine.getGameMap();
        GameEngine l_resultGameEngine = d_gameEngine;
        l_resultGameEngine.loadMap("risk.map");

        assertEquals(l_resultGameMap, l_resultGameEngine.getGameMap());

        GameEngine l_resultGameEngine1 = new GameEngine();
        l_resultGameEngine1.loadMap("risk.map");
        GameMap l_resultGameMap1 = l_resultGameEngine1.getGameMap();
        assertNotEquals(l_resultGameMap1, l_resultGameEngine.getGameMap());
    }

    /**
     * This function tests the HashMap returned after adding the players in the
     * Game.
     */
    @Test
    public void testGetPlayers() {
        boolean l_result;

        d_gameEngine.addPlayer("John");
        d_gameEngine.addPlayer("Doe");
        d_gameEngine.addPlayer("Erick");
        HashMap<String, Player> l_resultPlayersExpected = d_gameEngine.getPlayers();
        Set<String> l_resultPlayersExpectedKeySet = l_resultPlayersExpected.keySet();

        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.addPlayer("John");
        l_gameEngine.addPlayer("Doe");
        l_gameEngine.addPlayer("Erick");

        l_result = l_resultPlayersExpectedKeySet.equals(l_gameEngine.getPlayers().keySet());

        assertTrue(l_result);

        l_gameEngine.addPlayer("Arnold");
        l_result = l_resultPlayersExpectedKeySet.equals(l_gameEngine.getPlayers().keySet());
        assertFalse(l_result);
    }

    /**
     * This function tests the validateMap function with different conditions like
     * if the map is null, without countries, not traverseble and other different
     * conditions.
     */
    @Test
    public void testValidateMap() {
        d_gameEngine.loadMap(d_mapName1);
        String l_resultString = d_gameEngine.validateMap();
        assertEquals(" The graph is connected. Countries are traverseble.", l_resultString);

        GameEngine l_gameEngine1 = new GameEngine();
        String l_resultString2 = l_gameEngine1.validateMap();
        assertEquals("The Map does not contain any countries.", l_resultString2);

        GameEngine l_gameEngine2 = new GameEngine();
        l_gameEngine2.d_gameMap = null;
        String l_resultString3 = l_gameEngine2.validateMap();
        assertEquals("Cannot validate map", l_resultString3);
    }

    /**
     * This function tests the "checkContinentOwnership" method with various test
     * cases of checking the ownership of each player and which player owns how many
     * continents.
     */
    @Test
    public void testCheckContinentOwnership() {
        d_gameEngine.loadMap("uk.map");

        d_gameEngine.addPlayer("John");
        d_gameEngine.addPlayer("Doe");
        d_gameEngine.addPlayer("Erick");

        d_gameEngine.getPlayers().get("John").addCountry(d_gameEngine.getGameMap().getCountries().get(1));
        d_gameEngine.getPlayers().get("John").addCountry(d_gameEngine.getGameMap().getCountries().get(2));
        d_gameEngine.getPlayers().get("Doe").addCountry(d_gameEngine.getGameMap().getCountries().get(3));
        d_gameEngine.getPlayers().get("Erick").addCountry(d_gameEngine.getGameMap().getCountries().get(4));
        d_gameEngine.getPlayers().get("Erick").addCountry(d_gameEngine.getGameMap().getCountries().get(5));

        d_gameEngine.checkContinentOwnership();

        assertEquals(1, d_gameEngine.getPlayers().get("John").getContinents().keySet().size());
        assertTrue(
                d_gameEngine.getPlayers().get("John").getContinents().keySet().equals(new HashSet<>(Arrays.asList(1))));

        assertEquals(0, d_gameEngine.getPlayers().get("Doe").getContinents().keySet().size());
        assertTrue(
                d_gameEngine.getPlayers().get("Doe").getContinents().keySet().equals(new HashSet<>(Arrays.asList())));

        assertEquals(1, d_gameEngine.getPlayers().get("Erick").getContinents().keySet().size());
        assertTrue(d_gameEngine.getPlayers().get("Erick").getContinents().keySet()
                .equals(new HashSet<>(Arrays.asList(3))));
    }

    /**
     * This function tests the "gamePlayer" method with various test cases of adding
     * and removing players from the game.
     */
    @Test
    public void testGamePlayer() {
        // Load a map to allow adding/removing players
        d_gameEngine.loadMap("risk.map");

        // Test adding a player
        String[] addCommand = {"-add", "John"};
        String result = d_gameEngine.gamePlayer(addCommand);
        assertEquals("Player \"John\" added to game", result);

        // Test removing a player
        String[] removeCommand = {"-remove", "John"};
        result = d_gameEngine.gamePlayer(removeCommand);
        assertEquals("Player \"John\" removed from game", result);

        // Test adding a player when it's not allowed
        d_gameEngine.d_isEditMap = true;
        result = d_gameEngine.gamePlayer(addCommand);
        assertEquals("Players cannot be added/removed in this phase", result);

        // Test removing a player when it's not allowed
        result = d_gameEngine.gamePlayer(removeCommand);
        assertEquals("Players cannot be added/removed in this phase", result);
    }

    /**
     * This function tests the "editContinent" method with various test cases of loading
     * and saving maps.
     */
    @Test
    public void testEditContinent() {
        // Test adding a continent
        d_gameEngine.d_isEditMap = true;
        String[] addCommand = {"-add", "1", "5"};
        String result = d_gameEngine.editContinent(addCommand);
        assertEquals("Continent \"1\" successfully added to map", result);

        // Test removing a continent
        String[] removeCommand = {"-remove", "1"};
        result = d_gameEngine.editContinent(removeCommand);
        assertEquals("Continent \"1\" successfully removed from map", result);

        // Test adding a continent when it's not allowed
        d_gameEngine.d_isEditMap = false;
        result = d_gameEngine.editContinent(addCommand);
        assertEquals("Map can only be edited when file is open in edit phase", result);

        // Test removing a continent when it's not allowed
        result = d_gameEngine.editContinent(removeCommand);
        assertEquals("Map can only be edited when file is open in edit phase", result);
    }

    /**
     * This function tests the "AssignCountries" method with various test cases of adding
     * and removing player.
     */
    @Test
    public void testAssignCountries() {
        // Test with less than two players
        String result = d_gameEngine.assignCountries();
        assertEquals("There must be at least two player", result);

    }
}