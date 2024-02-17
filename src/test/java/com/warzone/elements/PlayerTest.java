package com.warzone.elements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player d_player = new Player("Chris");

    /**
     *test the name is correct
     */
    @Test
    public void testCheckName(){
        assertEquals("Chris",d_player.getName());
    }

    /**
    * test for country assignment to the player
    **/
    @Test
    public void testCheckCountry(){
        Continent l_continent = new Continent(2, 5);
        Country l_country = new Country(2, l_continent);
        d_player.addCountry(l_country);
        assertTrue(d_player.getCountries().keySet().contains(2));
    }

    /**
     * test if countries gets removed by the player
     */
    @Test
    public void testCheckRemoveCountry() {
        Continent l_continent = new Continent(3, 7);
        Country l_country1 = new Country(3, l_continent);
        d_player.addCountry(l_country1);
        Country l_country2 = new Country(5, l_continent);
        d_player.addCountry(l_country2);
        d_player.removeCountry(5);
        assertFalse(d_player.getCountries().keySet().contains(5));
    }

    /**
     * tests if continents gets removed by the player
     */
    @Test
    public void testCheckRemoveContinent() {
        Continent l_continent1 = new Continent(6, 4);
        d_player.addContinent(l_continent1);
        Continent l_continent2 = new Continent(2, 5);
        d_player.addContinent(l_continent2);
        d_player.removeContinent(6);
        assertFalse(d_player.getContinents().containsKey(6));
    }

    /**
     * test to validate the countries of a continent are owned by player
     */
    @Test
    public void testCheckContinentOwnership() {
        Continent l_continent = new Continent(5, 2);
        Country l_country1 = new Country(5, l_continent);
        d_player.addCountry(l_country1);
        Country l_country2 = new Country(8, l_continent);
        d_player.addCountry(l_country2);
        assertTrue(d_player.checkContinent(l_continent));
    }

    /**
     * test to check that number of armies assigned to player is correct
     */
    @Test
    public void testCheckNumberOfArmies() {
        d_player.setNumberOfArmies();
        assertEquals(3, d_player.getNumberOfArmies());

        Continent l_continent = new Continent(4, 4);
        Country l_country1 = new Country(4, l_continent);
        d_player.addCountry(l_country1);
        Country l_country2 = new Country(5, l_continent);
        d_player.addCountry(l_country2);
        Country l_country3 = new Country(8, l_continent);
        d_player.addCountry(l_country3);
        Country l_country4 = new Country(6, l_continent);
        d_player.addCountry(l_country4);
        Country l_country5 = new Country(3, l_continent);
        d_player.addCountry(l_country5);
        Country l_country6 = new Country(9, l_continent);
        d_player.addCountry(l_country6);
        d_player.addContinent(l_continent);
        d_player.setNumberOfArmies();
        assertEquals(6, d_player.getNumberOfArmies());
    }


}