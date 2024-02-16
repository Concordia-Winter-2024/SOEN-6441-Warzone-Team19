package com.warzone.elements;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContinentTest {
    Continent d_continent = new Continent(2,5);
    @Test
    public void testCheckNull() {
        assertNotNull(d_continent);
    }


}