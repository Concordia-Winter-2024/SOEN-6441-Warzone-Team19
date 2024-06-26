package com.warzone.controller.state.gamephase.gamesetup;

import com.warzone.controller.GameEngine;
import com.warzone.elements.GameMap;
import com.warzone.elements.savegameplay.LoadGame;

/**
 * Game entered this phase after map is saved successfully or at the start of the game, it
 * contains methods to load a map.
 */
public class PreLoad extends GameSetup {

	/**
	 * constructor method that takes game engine object from the parent class
	 * 
	 * @param p_gameEngine object of the game engine
	 */
	public PreLoad(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * This method is used to load the map file for play
	 * 
	 * @param p_fileName name of the file to be loaded
	 */
	@Override
	public String loadMap(String p_fileName) {
		String l_result;
		d_gameEngine.setGameMap(new GameMap());
		l_result = d_gameEngine.getGameMap().loadMap(p_fileName);
		if (l_result.equals(String.format("Map \"%s\" cannot be loaded", p_fileName))) {
			return l_result;
		}
		String l_validMsg = d_gameEngine.getGameMap().validateMap();
		Boolean l_validateResult = d_gameEngine.getGameMap().getValidateStatus();
		if (!l_validateResult) {
			l_result = l_validMsg;
			d_gameEngine.setGameMap(new GameMap());
			return l_result;
		}
		d_gameEngine.d_logEntryBuffer.setString("Game Setup Phase Entered");
		d_gameEngine.d_logEntryBuffer.setString(l_result);
		next();
		return l_result;
	}

	/**
	 * This method is used to add and remove players
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return string indicating that map has to be loaded to use this command
	 */
	@Override
	public String gamePlayer(String[] p_commandSplitted) {
		return String.format("Map Not Loaded yet");
	}

	/**
	 * This method is used to add players in the game
	 * 
	 * @param p_playerName name of the player to be added
	 * @return string indicating that map has to be loaded to use this command
	 */
	@Override
	public String addPlayer(String p_playerName) {
		return String.format("Map Not Loaded yet");
	}

	/**
	 * This method is used to remove players from the game
	 * 
	 * @param p_playerName splitted name of the player to be removed
	 * @return string indicating that map has to be loaded to use this command
	 */
	@Override
	public String removePlayer(String p_playerName) {
		return String.format("Map Not Loaded yet");
	}

	/**
	 * This method is used to allow proceeding to the next phase of the game
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

	/**
	 * Method to load map from the save file
	 *
	 * @param p_fileName name of the file to load
	 * @return result of the loading of the file
	 */
	@Override
	public String loadGame(String p_fileName) {
		String l_result;
		d_gameEngine.setGameMap(new GameMap());
		LoadGame l_loadGame = new LoadGame(d_gameEngine);
		return l_loadGame.loadGame(p_fileName);
	}
}
