package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;
import com.warzone.controller.state.gamephase.gamesetup.PostLoad;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Test to check that bomb command works perfectly
 */
public class BombTest {

	GameEngine d_game;

	/**
	 * Setup context for test to happen, object of game is created and players,
	 * countries and neighbors are setup and armies are assigned
	 */
	@Before
	public void setUp() {
		d_game = new GameEngine();
		d_game.setPhase(new PostLoad(d_game));
		String[] l_newStrings = new String[] { "gameplayer", "-add", "Nen", "-add", "Meet" };
		d_game.executeCommand(l_newStrings);
		d_game.getGameMap().addContinent(1, 5);
		d_game.getGameMap().addCountry(1, 1);
		d_game.getGameMap().addCountry(2, 1);
		d_game.getGameMap().addNeighbor(1, 2);
		d_game.getGameMap().getCountries().get(1).setPlayer(d_game.d_players.get("Nen"));
		d_game.getGameMap().getCountries().get(2).setPlayer(d_game.d_players.get("Meet"));
		d_game.d_players.get("Nen").addCountry(d_game.getGameMap().getCountries().get(1));
		d_game.d_players.get("Nen").setNumberOfArmies();
		d_game.d_players.get("Meet").addCountry(d_game.getGameMap().getCountries().get(2));
		d_game.d_players.get("Meet").setNumberOfArmies();

		Deploy l_deploy1 = new Deploy(d_game.d_players.get("Nen"), 1, 2);
		Deploy l_deploy2 = new Deploy(d_game.d_players.get("Meet"), 2, 3);
		l_deploy1.execute(d_game);
		l_deploy2.execute(d_game);
	}

	/**
	 * This function resets the variables to null.
	 */
	@After
	public void tearDown() {
		d_game = null;
	}

	/**
	 * test where bomb card is used on one's own country
	 */
	@Test
	public void testExecuteOrder1() {
		d_game.d_players.get("Nen").d_cardsOwned.put("bomb", 1);
		Bomb l_bombCmd = new Bomb(d_game.d_players.get("Nen"), 1);
		assertEquals("Cannot bomb country \"1\" as it is controlled by player \"Nen\".",
				l_bombCmd.execute(d_game));
	}

	/**
	 * test where bomb card is used successfully
	 */
	@Test
	public void testExecuteOrder2() {
		d_game.d_players.get("Nen").d_cardsOwned.put("bomb", 1);
		Bomb l_bombCmd = new Bomb(d_game.d_players.get("Nen"), 2);
		assertEquals("Player \"Nen\" bombed country \"2\" successfully.", l_bombCmd.execute(d_game));
	}

	/**
	 * test where bomb card is used even when a player does not own one
	 */
	@Test
	public void testExecuteOrder3() {
		Bomb l_bombCmd = new Bomb(d_game.d_players.get("Nen"), 1);
		assertEquals("Player \"Nen\" does not have a bomb card.", l_bombCmd.execute(d_game));
	}

	/**
	 * test where bomb card is used on a non neighboring country of the player
	 */
	@Test
	public void testExecuteOrder4() {
		d_game.d_players.get("Meet").d_cardsOwned.put("bomb", 1);
		Bomb l_bombCmd = new Bomb(d_game.d_players.get("Meet"), 1);
		assertEquals("The country \"1\" is not a neighbour country of the countries owned by player \"Meet\".",
				l_bombCmd.execute(d_game));
	}

	/**
	 * test where bomb card is used while there exist diplomacy between 2 players
	 */
	@Test
	public void testExecuteOrder5() {
		d_game.d_players.get("Nen").d_cardsOwned.put("bomb", 1);
		Bomb l_bombCmd = new Bomb(d_game.d_players.get("Nen"), 2);
		d_game.d_players.get("Nen").d_negotiatedPlayerNames.add("Meet");
		assertEquals("Cannot bomb, as diplomacy is established between \"Nen\" and \"Meet\".",
				l_bombCmd.execute(d_game));
	}
}
