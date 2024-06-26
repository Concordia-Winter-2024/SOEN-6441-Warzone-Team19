package com.warzone.controller.state.gamephase.gamesetup;

import com.warzone.controller.GameEngine;
import com.warzone.controller.state.gamephase.GamePhase;
import dnl.utils.text.table.TextTable;

import com.warzone.strategy.*;
import com.warzone.elements.Player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract class of game setup the represents the initial process of enetering
 * the gameplay
 */
public abstract class GameSetup extends GamePhase {

	/**
	 * constructor method of the class that takes game engine object from the parent
	 * class
	 * 
	 * @param p_gameEngine object of the game engine
	 */
	public GameSetup(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String assignArmies() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to allow users to provide orders
	 * 
	 * @return string conveying that game has not yet been setup to use this command
	 */
	@Override
	public String issueOrders() {
		return String.format("Game not yet Setup");
	}

	/**
	 * This method is used to assign countries to the players in the game
	 * 
	 * @return string conveying that game has not yet been setup to use this command
	 */
	@Override
	public String assignCountries() {
		return String.format("Game not yet Setup");
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	public String deploy(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	public String advance(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	public String airlift(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	public String bomb(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	public String blockade(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	public String diplomacy(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @return string to print the invalid command message
	 */
	public String executeOrders() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @return string to print the invalid command message
	 */
	public String checkContinentOwnership() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_playerName name of the winner of the game
	 * @return string to print the invalid command message
	 */
	public String printWinner(String p_playerName) {
		return printInvalidCommandMessage();
	}

	/**
	 * Method to print invalid command message as this cannot be used in this phase
	 *
	 * @return string to print the invalid command message
	 */
	@Override
	public String returnWinner() {
		return printInvalidCommandMessage();
	}

	/**
	 * Method to implement the tournament mode of the game
	 *
	 * @param p_maps list of maps
	 * @param p_players list of players
	 * @param p_games number of games to be played
	 * @param p_turns number of turns allowed
	 * @return the current state of the game as a map
	 */
	@Override
	public String tournament(ArrayList<String> p_maps, ArrayList<String> p_players, int p_games, int p_turns) {
		HashMap<String, ArrayList<String>> l_winnersMap = new HashMap<>();
		ArrayList<String> l_gameWinner;
		for (String l_map : p_maps) {
			l_gameWinner = new ArrayList<>();
			for (int l_i = 0; l_i < p_games; l_i++) {
				d_gameEngine.setMaxTurns(p_turns);
				d_gameEngine.setPhase(new PreLoad(d_gameEngine));
				d_gameEngine.getPhase().loadMap(l_map);
				for (String l_player : p_players) {
					d_gameEngine.getPhase().addPlayer(l_player);
					Player l_playerObj = d_gameEngine.d_players.get(l_player);
                    switch (l_player) {
                        case "Random" -> l_playerObj.setStrategy(new RandomPlayer(l_playerObj, d_gameEngine));
                        case "Aggressive" -> l_playerObj.setStrategy(new Aggressive(l_playerObj, d_gameEngine));
                        case "Benevolent" -> l_playerObj.setStrategy(new Benevolent(l_playerObj, d_gameEngine));
                        case "Cheater" -> l_playerObj.setStrategy(new Cheater(l_playerObj, d_gameEngine));
                        default -> throw new IllegalStateException("Unexpected value: " + l_player);
                    }
				}
				d_gameEngine.getPhase().assignCountries();
				l_gameWinner.add(d_gameEngine.getPhase().returnWinner());
				d_gameEngine = new GameEngine();
			}
			l_winnersMap.put(l_map, l_gameWinner);
		}

		String[] l_column = {"Map"};
		String l_row1 = "Map";
		for(int l_index = 1; l_index<=p_games;l_index++) {
			l_row1+=",Game"+l_index;
		}
		l_column = l_row1.split(",");

		Object[][] l_data = new Object[p_maps.size()][l_column.length];
		TextTable l_tt;
		final ByteArrayOutputStream l_baos = new ByteArrayOutputStream();
		String l_finalData;
		int l_count = 0;

		for(l_count = 0; l_count<l_winnersMap.keySet().size();l_count++) {
			ArrayList<String> l_result = new ArrayList<String>();
			l_result.add(p_maps.get(l_count));
			for(int l_innerCount = 0; l_innerCount<p_games;l_innerCount++) {
				l_result.add(l_winnersMap.get(p_maps.get(l_count)).get(l_innerCount));
			}
			l_data[l_count] = l_result.toArray();
		}
		l_tt = new TextTable(l_column, l_data);
		l_tt.setAddRowNumbering(false);

		try (PrintStream l_ps = new PrintStream(l_baos, true, "UTF-8")) {
			l_tt.printTable(l_ps, 0);
		} catch (UnsupportedEncodingException p_e) {
			p_e.printStackTrace();
		}

		l_finalData = new String(l_baos.toByteArray(), StandardCharsets.UTF_8);
		System.out.println(l_finalData);

		return l_winnersMap.toString();
	}

	/**
	 * Method to save whole game
	 *
	 * @param p_fileName name of game file that is to be saved
	 * @return string conveying that game has not yet been setup to use this command
	 */
	@Override
	public String saveGame(String p_fileName) {
		return String.format("Game not yet Setup");
	}
}
