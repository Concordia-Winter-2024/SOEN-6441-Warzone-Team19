package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;

/**
 * Interface implemented by all the order classes
 *
 */
public interface Orders {
	/**
	 * Executes the order
	 * 
	 * @param p_game GameEngine
	 * @return True response if command was successful, otherwise false
	 */
	String execute(GameEngine p_game);
}
