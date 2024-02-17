package com.warzone.elements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests Country class
 */
public class CountryTest {

    Continent d_Continent = new Continent(1, 2);
    Country d_country = new Country(1, d_Continent);

    /**
     * Checks if country object is null.
     */
    @Test
    public void testNull()
    {
        assertNotNull(d_country);
    }

    /**
     * This is used to check id of country
     */
    @Test
    public void testId()
    {
        assertEquals(1, d_country.getId());
    }


    /**
     * This is used to compare the 2 continents.
     */
    @Test
    public void testContinent()
    {
        assertEquals(d_Continent, d_country.getContinent());
    }

    /**
     * This is used to checks if correct number of armies are placed.
     */
    @Test
    public void testArmiesPlaced()
    {
        d_country.placeArmies(10);
        assertEquals(10, d_country.getNumberOfArmiesPresent());
    }

    /**
     * This is used to checks if correct number of armies are removed.
     */
    @Test
    public void testArmiesRemoved()
    {
        d_country.placeArmies(10);
        d_country.removeArmies(10);
        assertEquals(0, d_country.getNumberOfArmiesPresent());
    }

    /**
     * This is used to checks if neighbors are added.
     */
    @Test
    public void testNeighborAdded()
    {
        Country l_neighborCountry_1 = new Country(1, new Continent(1, 2));
        d_country.addNeighbor(l_neighborCountry_1);
        assertEquals(1, d_country.getNeighborCountries().size());
        assertTrue(d_country.getNeighborCountries().contains(l_neighborCountry_1));

        Country l_neighborCountry_2 = new Country(2, new Continent(1, 2));
        d_country.addNeighbor(l_neighborCountry_2);
        assertEquals(2, d_country.getNeighborCountries().size());
        assertTrue(d_country.getNeighborCountries().contains(l_neighborCountry_2));
    }

    /**
     * This is used to check if neighbors are removed
     */
    @Test
    public void testNeighborRemoved()
    {
        Country l_neighborCountry = new Country(1, new Continent(1, 2));
        d_country.addNeighbor(l_neighborCountry);
        d_country.removeNeighbor(l_neighborCountry);
        assertEquals(0, d_country.getNeighborCountries().size());
    }
}
