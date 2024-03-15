package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Test to check that deploy command works perfectly
 */
public class DeployTest {

	GameEngine d_game;

	/**
	 * Setup context for test to happen, object of game is created and player and
	 * countries are setup
	 */
	@Before
	public void setUp() {
		d_game = new GameEngine();
		d_game.setPhase(new PostLoad(d_game));
		String[] l_newStrings = new String[] { "gameplayer", "-add", "Meet" };
		String l_result = d_game.executeCommand(l_newStrings);
		d_game.getGameMap().addContinent(1, 5);
		d_game.getGameMap().addCountry(1, 1);
		d_game.d_players.get("Meet").addCountry(d_game.getGameMap().getCountries().get(1));
		d_game.d_players.get("Meet").setNumberOfArmies();
	}

	/**
	 * Test where player armies are placed on country occupied by player
	 */
	@Test
	public void testExecuteOrder1() {
		int l_country = 1;
		int l_armies = 2;
		Deploy l_Deploy = new Deploy(d_game.d_players.get("Meet"), l_country, l_armies);
		assertEquals("Player \"Meet\" deployed \"2\" armies to country \"1\"", l_Deploy.execute(d_game));
	}

	/**
	 * Test where armies greater than player has, are tried to placed on a country,
	 * rejected command with error message
	 */
	@Test
	public void testExecuteOrder2() {
		int l_country = 1;
		int l_armies = 5;
		Deploy l_Deploy = new Deploy(d_game.d_players.get("Meet"), l_country, l_armies);
		assertEquals("Player \"Meet\" does not enough armies", l_Deploy.execute(d_game));
	}

	/**
	 * Test where player tries to deploy armies to country which player doesn't
	 * possess, rejected command with error message
	 */
	@Test
	public void testExecuteOrder3() {
		int l_country = 4;
		int l_armies = 1;
		Deploy l_Deploy = new Deploy(d_game.d_players.get("Meet"), l_country, l_armies);
		assertEquals("Player \"Meet\" does not control country \"4\"", l_Deploy.execute(d_game));
	}
}
