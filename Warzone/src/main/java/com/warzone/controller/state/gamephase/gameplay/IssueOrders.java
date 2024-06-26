package com.warzone.controller.state.gamephase.gameplay;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Player;
import com.warzone.strategy.HumanPlayer;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * IssueOrder class that inherits GamePlay interface to support functions during
 * this command
 */
public class IssueOrders extends GamePlay {

	/**
	 * constructor method that takes game engine object from the parent class
	 * 
	 * @param p_gameEngine object of the game engine
	 */
	public IssueOrders(GameEngine p_gameEngine) {
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
	 * This method is used to support deploy command for further processing
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return merged string of the deploy order provided
	 */
	@Override
	public String deploy(String[] p_commandSplitted) {
		StringBuilder l_sb = new StringBuilder();
		for (String l_str : p_commandSplitted) {
			l_sb.append(l_str);
			l_sb.append(" ");
		}
		return l_sb.toString();
	}

	/**
	 * This method is used to support advance command for further processing
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return merged string of the advance order provided
	 */
	@Override
	public String advance(String[] p_commandSplitted) {
		StringBuilder l_sb = new StringBuilder();
		for (String l_str : p_commandSplitted) {
			l_sb.append(l_str);
			l_sb.append(" ");
		}
		return l_sb.toString();
	}

	/**
	 * This method is used to support airlift command for further processing
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return merged string of the airlift order provided
	 */
	@Override
	public String airlift(String[] p_commandSplitted) {
		StringBuilder l_sb = new StringBuilder();
		for (String l_str : p_commandSplitted) {
			l_sb.append(l_str);
			l_sb.append(" ");
		}
		return l_sb.toString();
	}

	/**
	 * This method is used to support bomb command for further processing
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return merged string of the bomb order provided
	 */
	@Override
	public String bomb(String[] p_commandSplitted) {
		StringBuilder l_sb = new StringBuilder();
		for (String l_str : p_commandSplitted) {
			l_sb.append(l_str);
			l_sb.append(" ");
		}
		return l_sb.toString();
	}

	/**
	 * This method is used to support blockade command for further processing
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return merged string of the blockade order provided
	 */
	@Override
	public String blockade(String[] p_commandSplitted) {
		StringBuilder l_sb = new StringBuilder();
		for (String l_str : p_commandSplitted) {
			l_sb.append(l_str);
			l_sb.append(" ");
		}
		return l_sb.toString();
	}

	/**
	 * This method is used to support diplomacy command for further processing
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return merged string of the diplomacy order provided
	 */
	@Override
	public String diplomacy(String[] p_commandSplitted) {
		StringBuilder l_sb = new StringBuilder();
		for (String l_str : p_commandSplitted) {
			l_sb.append(l_str);
			l_sb.append(" ");
		}
		return l_sb.toString();
	}

	/**
	 * This method is used that takes player's and that adds to them to the orders queue
	 * 
	 * @return string to output result of issue orders
	 */
	@Override
	public String issueOrders() {
		int l_currentPlayer = 0;
		HashSet<String> l_playersCompleted = new HashSet<>();
		System.out.println("\nIssue orders phase entered");
		d_gameEngine.d_logEntryBuffer.setString("Issue orders phase entered");
		System.out.println(org.apache.commons.lang3.StringUtils.repeat("-", 20));

		while (l_playersCompleted.size() < d_gameEngine.d_playerName.size()) {
			if (!d_gameEngine.d_players.get(d_gameEngine.d_playerName.get(l_currentPlayer)).getIsCommit()) {
				System.out.println(d_gameEngine.getGameMap().showMapPlay());
				System.out.println("Player " + d_gameEngine.d_playerName.get(l_currentPlayer) + "'s turn");
				d_gameEngine.d_logEntryBuffer
						.setString("Player " + d_gameEngine.d_playerName.get(l_currentPlayer) + "'s turn");
				System.out.println("Armies: " + d_gameEngine.d_players
						.get(d_gameEngine.d_playerName.get(l_currentPlayer)).getNumberOfArmies());
				System.out.println("Cards: "
						+ d_gameEngine.d_players.get(d_gameEngine.d_playerName.get(l_currentPlayer)).d_cardsOwned);
				d_gameEngine.d_players.get(d_gameEngine.d_playerName.get(l_currentPlayer)).issue_order();
				if (!d_gameEngine.d_players.get(d_gameEngine.d_playerName.get(l_currentPlayer)).getIsCommit()) {
					d_gameEngine
							.addPlayerOrder(d_gameEngine.d_players.get(d_gameEngine.d_playerName.get(l_currentPlayer)));
				}

				if (!(d_gameEngine.d_players.get(d_gameEngine.d_playerName.get(l_currentPlayer))
						.getPlayerBehaviour() instanceof HumanPlayer)) {
					d_gameEngine.d_players.get(d_gameEngine.d_playerName.get(l_currentPlayer)).setIsCommit(true);
				}
			} else {
				l_playersCompleted.add(d_gameEngine.d_playerName.get(l_currentPlayer));
			}
			++l_currentPlayer;
			if (l_currentPlayer == d_gameEngine.d_playerName.size()) {
				l_currentPlayer = 0;
			}
		}
		for (Player l_player : d_gameEngine.d_players.values()) {
			l_player.setIsCommit(false);
			l_player.d_negotiatedPlayerNames = new ArrayList<String>();
			l_player.d_isConquered = false;
		}
		next();
		d_gameEngine.d_logEntryBuffer.setString("Issue orders phase completed");
		d_gameEngine.getPhase().executeOrders();
		return "";
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String executeOrders() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String showMap() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String checkContinentOwnership() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to proceed to the next phase of the game
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new ExecuteOrders(d_gameEngine));
	}

	/**
	 * Method to support savegame command for further processing
	 *
	 * @param p_fileName is the Name of the File which will save the Game.
	 * @return Output string message after saving the game
	 */
	@Override
	public String saveGame(String p_fileName) {
		StringBuilder l_sb = new StringBuilder();
		l_sb.append("savegame ");
		l_sb.append(p_fileName);
		return l_sb.toString();
	}
}
