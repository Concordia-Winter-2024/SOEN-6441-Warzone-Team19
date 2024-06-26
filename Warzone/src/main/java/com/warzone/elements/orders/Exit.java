package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;

/**
 * This is the exit class order, while in IssueOrder phase, if the player types exit to commit its command
 * then, this class is used.
 */
public class Exit implements Orders {

	/**
	 * This is the exit order which does nothing.
	 * 
	 * @param p_game Object of Game Engine.
	 * @return String format for output of execute order.
	 */
	@Override
	public String execute(GameEngine p_game) {
		return null;
	}

	/**
	 * This method is used to get the order in String format.
	 * 
	 * @return command in String form.
	 */
	public String getOrder() {
		return "exit";
	}
}
