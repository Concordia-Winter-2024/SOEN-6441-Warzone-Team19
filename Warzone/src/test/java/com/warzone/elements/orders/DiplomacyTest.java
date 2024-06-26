package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;
import com.warzone.controller.state.gamephase.gamesetup.PostLoad;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Test to check that diplomacy card and negotiate command works perfectly.
 *
 */
public class DiplomacyTest {

	GameEngine d_game;

	/**
	 * Setup context for test to happen, object of game is created and players,
	 * countries and neighbors are setup and armies are assigned
	 */
	@Before
	public void setUp() {
		d_game = new GameEngine();
		d_game.setPhase(new PostLoad(d_game));
		String[] l_newStrings = new String[] { "gameplayer", "-add", "Meet", "-add", "Meet" };
		d_game.executeCommand(l_newStrings);
		d_game.getGameMap().addContinent(1, 5);
		d_game.getGameMap().addCountry(1, 1);
		d_game.getGameMap().addCountry(2, 1);
		d_game.getGameMap().addCountry(3, 1);
		d_game.getGameMap().addNeighbor(1, 2);
		d_game.getGameMap().addNeighbor(1, 3);
		d_game.getGameMap().getCountries().get(1).setPlayer(d_game.d_players.get("Meet"));
		d_game.getGameMap().getCountries().get(2).setPlayer(d_game.d_players.get("Meet"));
		d_game.getGameMap().getCountries().get(3).setPlayer(d_game.d_players.get("Meet"));
		d_game.d_players.get("Meet").addCountry(d_game.getGameMap().getCountries().get(1));
		d_game.d_players.get("Meet").addCountry(d_game.getGameMap().getCountries().get(3));
		d_game.d_players.get("Meet").setNumberOfArmies();
		d_game.d_players.get("Meet").addCountry(d_game.getGameMap().getCountries().get(2));
		d_game.d_players.get("Meet").setNumberOfArmies();

		Deploy l_deploy1 = new Deploy(d_game.d_players.get("Meet"), 1, 2);
		Deploy l_deploy2 = new Deploy(d_game.d_players.get("Meet"), 2, 3);
		l_deploy1.execute(d_game);
		l_deploy2.execute(d_game);
	}

	/**
	 * This method resets the variables to null.
	 */
	@After
	public void tearDown() {
		d_game = null;
	}

	/**
	 * Test where diplomacy card is executed successfully between 2 players
	 */
	@Test
	public void testExecuteOrder1() {
		d_game.d_players.get("Meet").d_cardsOwned.put("diplomacy", 1);
		Diplomacy l_diplomacyCmd = new Diplomacy(d_game.d_players.get("Meet"), "Meet");
		assertEquals("Diplomacy between Players \"Meet\" and \"Meet\" established successfully.",
				l_diplomacyCmd.execute(d_game));
	}

	/**
	 * Test where diplomacy card is used with a player that does not exist
	 */
	@Test
	public void testExecuteOrder2() {
		d_game.d_players.get("Meet").d_cardsOwned.put("diplomacy", 1);
		Diplomacy l_diplomacyCmd = new Diplomacy(d_game.d_players.get("Meet"), "Vandit");
		assertEquals("Player \"Vandit\" does not exist", l_diplomacyCmd.execute(d_game));
	}

	/**
	 * Test where diplomacy card is used even when a player does not have a
	 * diplomacy card
	 */
	@Test
	public void testExecuteOrder3() {
		Diplomacy l_diplomacyCmd = new Diplomacy(d_game.d_players.get("Meet"), "Meet");
		assertEquals("Player \"Meet\" does not have Diplomacy Card.", l_diplomacyCmd.execute(d_game));
	}
}
