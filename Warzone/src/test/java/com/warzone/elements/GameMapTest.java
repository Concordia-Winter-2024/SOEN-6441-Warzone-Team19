package com.warzone.elements;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testcases for GameMap
 */
public class GameMapTest {

    final GameMap d_map = new GameMap();
    /**
     * Tests addition of country
     */
    @org.junit.Test
    public void testAddCountry() {
        d_map.addContinent(1, 2);
        d_map.addCountry(1, 1);
        assertEquals(1, d_map.getCountries().size());
        assertTrue(d_map.getCountries().keySet().contains(1));
    }

    /**
     * Tests removal of country
     */
    @org.junit.Test
    public void testRemoveCountry() {
        d_map.addContinent(1, 2);
        d_map.addCountry(1, 1);
        d_map.addCountry(2, 1);
        d_map.removeCountry(2);
        assertEquals(1, d_map.getCountries().size());
        assertFalse(d_map.getCountries().keySet().contains(2));

        d_map.addCountry(2, 1);
        d_map.addCountry(3, 1);
        d_map.addNeighbor(2, 3);
        d_map.addNeighbor(3, 2);
        d_map.removeCountry(2);
        assertFalse(d_map.getCountries().get(3).getNeighborIds().contains(2));
    }
    /**
     * Tests addition of continent
     */
    @org.junit.Test
    public void testAddContinent() {
        d_map.addContinent(1, 2);
        assertTrue(d_map.getContinents().keySet().contains(1));
        assertEquals(2, d_map.getContinents().get(1).getControlValue());
    }

    /**
     * Tests removal of continent
     */
    @org.junit.Test
    public void testRemoveContinent() {
        d_map.addContinent(1, 2);
        d_map.addContinent(2, 3);
        d_map.addCountry(1, 2);
        d_map.removeContinent(2);
        assertEquals(1, d_map.getContinents().size());
        assertFalse(d_map.getContinents().containsKey(2));
    }



    /**
     * Tests addition of neighbor
     */
    @org.junit.Test
    public void testAddNeighbor() {
        d_map.addContinent(1, 2);
        d_map.addCountry(1, 1);
        d_map.addCountry(2, 1);
        String l_resultString = d_map.addNeighbor(1, 2);
        assertEquals(1, d_map.getCountries().get(1).getNeighborCountries().size());
        assertEquals("Country \"2\" is now a neighbor of country \"1\"", l_resultString);
    }

    /**
     * Tests deletion of neighbor
     */
    @org.junit.Test
    public void testRemoveNeighbor() {
        d_map.addContinent(1, 2);
        d_map.addCountry(1, 1);
        d_map.addCountry(2, 1);
        d_map.addNeighbor(1, 2);
        String l_resultString = d_map.removeNeighbor(1, 2);
        assertEquals(0, d_map.getCountries().get(1).getNeighborCountries().size());
        assertEquals("Country \"2\" removed from neighbors of \"1\"", l_resultString);
    }

    /**
     * Tests loading of Map
     */
    @Test
    public void testLoadMap() {
        assertEquals(String.format("Map \"world.map\" loaded successfully"), d_map.loadMap("world.map"));
        assertEquals(String.format("Map \"uk1.map\" cannot be loaded"), d_map.loadMap("uk1.map"));
    }

}