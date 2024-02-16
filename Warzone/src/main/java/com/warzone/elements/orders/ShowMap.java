package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;

/**
 * Class for implementing Orders and is used to show map in game phase
 *
 */
public class ShowMap implements Orders {

	/**
	 * method to execute showMap order
	 * 
	 * @param p_game object calling this function
	 * @return map in string format
	 */
	@Override
	public String execute(GameEngine p_game) {
		return p_game.showMap();
	}

}