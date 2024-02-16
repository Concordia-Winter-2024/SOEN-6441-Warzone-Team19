package entities.orders;

import controller.GameEngine;

/**
 * Class to implement the orders and is used to display the map in the game phase
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
	public String executeOrder(GameEngine p_game) {
		return p_game.showMap();
	}

}
