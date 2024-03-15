package com.warzone.elements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testcases for PlayerTest
 */
public class PlayerTest {

    Player d_player = new Player("ABCD");

    /**
     * Tests if name is set correctly
     */
    @org.junit.Test
    public void testGetName() {
        assertEquals("ABCD", d_player.getName());
    }

    /**
     * Tests if countries are assigned to player
     */
    @org.junit.Test
    public void testAddCountry() {
        Continent l_continent = new Continent(1, 3);
        Country l_country = new Country(1, l_continent);
        d_player.addCountry(l_country);
        assertTrue(d_player.getCountries().keySet().contains(1));
    }

    /**
     * Tests if countries are removed from player
     */
    @org.junit.Test
    public void testRemoveCountry() {
        Continent l_continent = new Continent(1, 3);
        Country l_country1 = new Country(1, l_continent);
        d_player.addCountry(l_country1);
        Country l_country2 = new Country(2, l_continent);
        d_player.addCountry(l_country2);
        d_player.removeCountry(2);
        assertFalse(d_player.getCountries().keySet().contains(2));

    }

    /**
     * Tests if continents are assigned to player
     */
    @org.junit.Test
    public void testAddContinent() {
        Continent l_continent = new Continent(1, 3);
        d_player.addContinent(l_continent);
        assertTrue(d_player.getContinents().containsKey(1));
    }

    /**
     * Tests if continents are removed from player
     */
    @org.junit.Test
    public void testRemoveContinent() {
        Continent l_continent1 = new Continent(1, 3);
        d_player.addContinent(l_continent1);
        Continent l_continent2 = new Continent(2, 3);
        d_player.addContinent(l_continent2);
        d_player.removeContinent(2);
        assertFalse(d_player.getContinents().containsKey(2));
    }

    /**
     * Test to check if all countries in a continent are owned by player
     */
    @org.junit.Test
    public void testContinentOwnership() {
        Continent l_continent = new Continent(1, 3);
        Country l_country1 = new Country(1, l_continent);
        d_player.addCountry(l_country1);
        Country l_country2 = new Country(2, l_continent);
        d_player.addCountry(l_country2);
        assertTrue(d_player.checkContinent(l_continent));
    }

    /*
     * Tests if correct number of armies are assigned to player
     */
    @Test
    public void testSetNumberOfArmies() {
        d_player.setNumberOfArmies();
        assertEquals(3, d_player.getNumberOfArmies());

        Continent l_continent = new Continent(1, 5);
        Country l_country1 = new Country(1, l_continent);
        d_player.addCountry(l_country1);
        Country l_country2 = new Country(2, l_continent);
        d_player.addCountry(l_country2);
        Country l_country3 = new Country(3, l_continent);
        d_player.addCountry(l_country3);
        Country l_country4 = new Country(4, l_continent);
        d_player.addCountry(l_country4);
        Country l_country5 = new Country(5, l_continent);
        d_player.addCountry(l_country5);
        Country l_country6 = new Country(6, l_continent);
        d_player.addCountry(l_country6);
        d_player.addContinent(l_continent);
        d_player.setNumberOfArmies();
        assertEquals(7, d_player.getNumberOfArmies());
    }

    /**
     * Test to check Non-existent country ID
     */
    @Test
    public void testRemoveCountryNonExistentID() {
        d_player.removeCountry(10);
        assertFalse(d_player.getCountries().keySet().contains(10));
    }

    @Test
    public void testAddDuplicateContinent() {
        Continent l_continent = new Continent(1, 3);
        d_player.addContinent(l_continent);
        d_player.addContinent(l_continent);
        assertEquals(1, d_player.getContinents().size()-1);
    }

}
