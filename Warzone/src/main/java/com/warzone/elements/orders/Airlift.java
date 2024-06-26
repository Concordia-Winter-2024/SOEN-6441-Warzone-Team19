package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Player;

/**
 * This class represents the airlift card. The Airlift Card allows you to
 * transfer your armies long distances. Each time you play one, you can do a
 * single transfer from any of your territories to any other territory of yours.
 * Similar to a normal transfer, those armies can't do any other action that
 * turn.
 */
public class Airlift implements Orders {

	private Player d_player;
	private int d_countryNameFrom;
	private int d_countryNameTo;
	private int d_armies;

	/**
	 * Constructor for Airlift class
	 * 
	 * @param p_player          player who is firing airlift command
	 * @param p_countryNameFrom country from which the armies are to be
	 *                          taken
	 * @param p_countryNameTo   country to which the reinforcements are to be placed
	 * @param p_armies          number of armies to be moved
	 */
	public Airlift(Player p_player, int p_countryNameFrom, int p_countryNameTo, int p_armies) {
		d_player = p_player;
		d_countryNameFrom = p_countryNameFrom;
		d_countryNameTo = p_countryNameTo;
		d_armies = p_armies;
	}

	/**
	 * This method is used to execute the airlift command
	 * 
	 * @param p_game Gets the object of GameEngine class
	 * @return string Result as per the operations performed
	 */
	@Override
	public String execute(GameEngine p_game) {
		String validationResult = validateAirlift(p_game);
		if (validationResult != null) {
			return validationResult;
		}

		return executeAirlift(p_game);
	}

	/**
	 * This method is used to get the order in String format.
	 *
	 * @return command in String form.
	 */
	public String getOrder() {
		return "airlift " + d_countryNameFrom + " " + d_countryNameTo + " " + d_armies;
	}

	private String validateAirlift(GameEngine p_game) {
		int l_airliftCardCount = d_player.d_cardsOwned.get("airlift");
		if (l_airliftCardCount == 0) {
			return String.format("Player \"%s\" does not have a airlift card", d_player.getName());
		}
		if (!d_player.getCountries().containsKey(d_countryNameFrom)) {
			return String.format("Player \"%s\" does not own country \"%d\"", d_player.getName(), d_countryNameFrom);
		}
		if (!d_player.getCountries().containsKey(d_countryNameTo)) {
			return String.format("Player \"%s\" does not own country \"%d\"", d_player.getName(), d_countryNameTo);
		}
		if (d_player.getCountries().get(d_countryNameFrom).getNumberOfArmiesPresent() < d_armies) {
			return String.format("Country \"%d\" does not have enough armies", d_countryNameFrom);
		}
		if ((d_player.getCountries().get(d_countryNameFrom).getNumberOfArmiesPresent() - d_armies) < 1) {
			return String.format(
					"Country \"%d\" should remain with at least 1 armies after moving the armies during Airlift",
					d_countryNameFrom);
		}
		return null;
	}

	private String executeAirlift(GameEngine p_game) {
		d_player.getCountries().get(d_countryNameFrom).removeArmies(d_armies);
		d_player.getCountries().get(d_countryNameTo).placeArmies(d_armies);

		int l_airliftCardCount = d_player.d_cardsOwned.get("airlift");
		d_player.d_cardsOwned.replace("airlift", l_airliftCardCount - 1);
		return String.format("Armies successfully moved from country \"%d\" to country \"%d\"", d_countryNameFrom,
				d_countryNameTo);
	}
}
