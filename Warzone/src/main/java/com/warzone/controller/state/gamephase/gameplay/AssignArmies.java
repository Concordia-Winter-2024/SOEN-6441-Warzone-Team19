package com.warzone.controller.state.gamephase.gameplay;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Continent;
import com.warzone.elements.Player;

/**
 * AssignArmies class that inherits GamePlay interface to support functions
 * during this command
 */
public class AssignArmies extends GamePlay {

	/**
	 * constructor takes game engine object from the parent class
	 * 
	 * @param p_gameEngine object of the game engine
	 */
	public AssignArmies(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * This method is used to assign armies to the players
	 */
	@Override
	public String assignArmies() {
		try {
			checkContinentOwnership();
			for (Player l_player : d_gameEngine.d_players.values()) {
				l_player.setNumberOfArmies();
			}
			System.out.println("Armies assigned");
			return "Assigned armies to players";
		} finally {
			next();
			d_gameEngine.d_logEntryBuffer.setString("Assigned Reinforcements to players");
			d_gameEngine.getPhase().issueOrders();
		}
	}

	/**
	 * This method is used to check whether continent is owned by the players
	 * 
	 * @return string to output result of continent ownership
	 */
	@Override
	public String checkContinentOwnership() {
		for (Player l_player : d_gameEngine.d_players.values()) {
			for (Continent l_continent : d_gameEngine.getGameMap().getContinents().values()) {
				if (l_player.checkContinent(l_continent)) {
					l_player.addContinent(l_continent);
				} else if (!l_player.checkContinent(l_continent)
						&& l_player.getContinents().containsValue(l_continent)) {
					l_player.removeContinent(l_continent.getId());
				}
			}
		}
		return "Checked continent ownership";
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
	 * This method is used to print invalid command
	 * 
	 * @param p_splittedCommand splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
	public String deploy(String[] p_splittedCommand) {
		return printInvalidCommandMessage();
	}

	/**
	 * Method to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
	public String advance(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
	public String airlift(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
	public String bomb(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
	public String blockade(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
	public String diplomacy(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String issueOrders() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String executeOrders() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to proceed to the next phase of the game
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new IssueOrders(d_gameEngine));
	}

	/**
	 * Method to print invalid command as the following command cannot be used in
	 * this phase
	 *
	 * @param p_fileName File that saves the game.
	 * @return string to print the invalid command message
	 */
	@Override
	public String saveGame(String p_fileName) {
		return printInvalidCommandMessage();
	}
}
