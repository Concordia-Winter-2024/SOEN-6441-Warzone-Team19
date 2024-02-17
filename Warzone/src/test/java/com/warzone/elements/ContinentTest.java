package com.warzone.elements;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Continent
 */
public class ContinentTest {

    /**
     * This method is used to check if countries are removed
     */
    @Test
    public void testCountriesRemoved()
    {
        Country l_Country = new Country(1, d_continent);
        d_continent.addCountry(l_Country);
        d_continent.removeCountry(l_Country);
        assertEquals(0, d_continent.getCountriesSet().size());
        assertFalse(d_continent.getCountriesSet().contains(l_Country));
    }

    Continent d_continent = new Continent(1, 3);

    /**
     * This is used to check if continent object is not null
     */
    @Test
    public void testCheckNotNull()
    {
        assertNotNull(d_continent);
    }

    /**
     * This method is used to check id of continent
     */
    @Test
    public void testID()
    {
        assertEquals(1, d_continent.getId());
    }

    /**
     * This is used to check control value of continent
     */
    @Test
    public void testControlValue()
    {
        assertEquals(3, d_continent.getControlValue());
    }

    /**
     * This is used to check if countries are added
     */
    @Test
    public void testCountriesAdded()
    {
        Country l_Country = new Country(1, d_continent);
        d_continent.addCountry(l_Country);
        assertEquals(1, d_continent.getCountriesSet().size());
        assertTrue(d_continent.getCountriesSet().contains(l_Country));
    }
}