package com.warzone.strategy;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Player;
import com.warzone.elements.orders.Orders;

/**
 * Abstract class to be implemented by different strategies.
 */
public abstract class PlayerStrategy {

	Player d_player;
	GameEngine d_gameEngine;

	/**
     * constructor method to set player name and GameEngine object
	 * 
	 * @param p_player     the player's name whose strategy is to be set
	 * @param p_gameEngine object of GameEngine class
	 */
	public PlayerStrategy(Player p_player, GameEngine p_gameEngine) {
		d_player = p_player;
		d_gameEngine = p_gameEngine;
	}

	/**
	 * This method is implemented by strategy classes
	 * 
	 * @return order to be executed of a strategy type 
	 */
	public abstract Orders createOrder();
}
