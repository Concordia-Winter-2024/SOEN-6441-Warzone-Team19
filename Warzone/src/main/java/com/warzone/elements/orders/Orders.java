package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;

/**
 * Interface for the orders
 *
 */
public interface Orders {
	/**
	 * Executes the order
	 * 
	 * @param p_game GameEngine
	 * @return true response if command was successful, otherwise false
	 */
	String execute(GameEngine p_game);

	/**
	 * This function will give you the command format of Order.
	 *
	 * @return Command of Order in String format.
	 */
	public String getOrder();
}
