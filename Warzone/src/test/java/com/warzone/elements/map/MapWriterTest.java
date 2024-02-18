package com.warzone.elements.map;

import static org.junit.Assert.*;

import com.warzone.elements.GameMap;
import org.junit.Test;

/**
 * Testcases for MapWriterTest
 */
public class MapWriterTest {

    /**
     * It checks if map is written properly
     */
    @Test
    public void testWriteFullMap() {
        MapWriter l_writeMap = new MapWriter(new GameMap());
        boolean l_testVar = l_writeMap.writeFullMap("WorldMapTest.map");
        assertTrue(l_testVar);
    }

}
