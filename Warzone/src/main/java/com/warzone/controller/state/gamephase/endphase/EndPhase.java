package com.warzone.controller.state.gamephase.endphase;

import com.warzone.controller.GameEngine;
import com.warzone.controller.state.gamephase.GamePhase;

/**
 * This class  inherits GamePhase class whose fucntion is to print
 * the winner of  game
 */
public class EndPhase extends GamePhase {

	/**
	 * constructor that takes  the object of GameEngine
	 * 
	 * @param p_gameEngine object of GameEngine class
	 */
	public EndPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * This method is used to print invalid command
	 * 
	 * @param p_fileName name of the file used to laod
	 * @return string to print the invalid command message
	 */
	@Override
	public String loadMap(String p_fileName) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
	public String gamePlayer(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @param p_playerName name of the player to add
	 * @return string to print the invalid command message
	 */
	@Override
	public String addPlayer(String p_playerName) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @param p_playerName name of the player to remove
	 * @return string to print the invalid command message
	 */
	@Override
	public String removePlayer(String p_playerName) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String assignCountries() {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print invalid command 
	 * 
	 * @return string to print the invalid command message
	 */
	@Override
	public String assignArmies() {
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
	 * This method is used to print invalid command as the following command cannot be used in
	 * this phase
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string to print the invalid command message
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public String diplomacy(String[] p_commandSplitted) {
		return printInvalidCommandMessage();
	}

	/**
	 * This method is used to print the winner of the game
	 * 
	 * @param p_playerName name of the player that has won
	 * @return string to print winner of the game
	 */
	public String printWinner(String p_playerName) {
		return "\nWinner:  " + p_playerName;
	}

	/**
	 * This method is used to proceed to the next phase of the game but as this is the final
	 * phase of the game this function does not execute
	 */
	@Override
	public void next() {
		System.exit(0);
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
	public String checkContinentOwnership() {
		return printInvalidCommandMessage();
	}
}
