package com.warzone.controller.state;

import com.warzone.controller.GameEngine;

/**
 * An abstract class for the Phase implementation of state pattern
 */
public abstract class Phase {
	public GameEngine d_gameEngine;

	/**
	 * Constructor method to initialize the phase
	 * 
	 * @param p_gameEngine object of game Engine
	 */
	public Phase(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * abstract method to support editMap command
	 * 
	 * @param p_fileName the name of the file used for editing
	 * @return output string of execution of command
	 */
	abstract public String editMap(String p_fileName);

	/**
	 * abstract method to support editContinent command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String editContinent(String[] p_commandSplitted);

	/**
	 * abstract method to support editCountry command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String editCountry(String[] p_commandSplitted);

	/**
	 * abstract method to support editNeighbor command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String editNeighbor(String[] p_commandSplitted);

	/**
	 * abstract method to support saveMap command
	 * 
	 * @param p_fileName the name of the file used for editing
	 * @return output string of execution of command
	 */
	abstract public String saveMap(String p_fileName);

	/**
	 * abstract method to support loadMap command
	 * 
	 * @param p_fileName the name of the file used for editing
	 * @return output string of execution of command
	 */
	abstract public String loadMap(String p_fileName);

	/**
	 * abstract method to support gamePlayer command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String gamePlayer(String[] p_commandSplitted);

	/**
	 * abstract method to support addPlayer command
	 * 
	 * @param p_playerName name of the player to be added
	 * @return output string of execution of command
	 */
	abstract public String addPlayer(String p_playerName);

	/**
	 * abstract method to support addPlayer command
	 * 
	 * @param p_playerName name of the player to be removed
	 * @return output string of execution of command
	 */
	abstract public String removePlayer(String p_playerName);

	/**
	 * Abstract method to assign countries
	 * 
	 * @return output string of execution of command
	 */
	abstract public String assignCountries();

	/**
	 * Abstract method to assign Armies
	 * 
	 * @return output string of execution of command
	 */
	abstract public String assignArmies();

	/**
	 * Abstract method to issue Orders
	 * 
	 * @return output string of execution of command
	 */
	abstract public String issueOrders();

	/**
	 * abstract method to support deploy command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String deploy(String[] p_commandSplitted);

	/**
	 * abstract method to support advance command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String advance(String[] p_commandSplitted);

	/**
	 * abstract method to support bomb command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String bomb(String[] p_commandSplitted);

	/**
	 * abstract method to support blockade command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String blockade(String[] p_commandSplitted);

	/**
	 * abstract method to support airlift command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String airlift(String[] p_commandSplitted);

	/**
	 * abstract method to support diplomacy command
	 * 
	 * @param p_commandSplitted splitted parts of the command used to execution of
	 *                          the command
	 * @return output string of execution of command
	 */
	abstract public String diplomacy(String[] p_commandSplitted);

	/**
	 * abstract method to support proceeding to the next phase of the game
	 */
	abstract public void next();

	/**
	 * Abstract method to execute Orders
	 * 
	 * @return output string of execution of command
	 */
	abstract public String executeOrders();

	/**
	 * Abstract method to check Continent Ownership
	 * 
	 * @return output string of execution of command
	 */
	abstract public String checkContinentOwnership();

	/**
	 * abstract method display the winner of the game
	 * 
	 * @param p_playerName name of the player that has won
	 * @return output string of execution of command
	 */
	abstract public String printWinner(String p_playerName);

	/**
	 * method to show the map with all the information at all times
	 * 
	 * @return output string of execution of command
	 */
	public String showMap() {
		return d_gameEngine.getGameMap().showMapEdit();
	}

	/**
	 * Validation method to check validity of the game map in the phases
	 * 
	 * @return the validation string representing the validity or not
	 */
	public String validateMap() {
		String l_result;
		if (d_gameEngine.getGameMap() != null) {
			l_result = d_gameEngine.getGameMap().validateMap();
		} else {
			l_result = String.format("Cannot validate map");
		}
		return l_result;
	}

	/**
	 * Common method to print invalid command message when a command is executed in
	 * the wrong phase
	 * 
	 * @return string containing the message
	 */
	public String printInvalidCommandMessage() {
		return "Invalid command in phase " + this.getClass().getSimpleName();
	}
}
