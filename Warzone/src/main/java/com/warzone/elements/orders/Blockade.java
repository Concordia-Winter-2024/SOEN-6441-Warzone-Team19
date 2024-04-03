package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Player;

/**
 * Whenever blockade card is used, triple the armies present in one of the country
 * they control and in return looses control over that country,
 * neutral player gets the control of the country.
 */
public class Blockade implements Orders {

	private Player d_player;
	private int d_country;

	/**
	 * Constructor for Blockade class
	 * 
	 * @param p_player  gets the object of Player class
	 * @param p_country gets the id of country
	 */
	public Blockade(Player p_player, int p_country) {
		d_player = p_player;
		d_country = p_country;
	}

	/**
	 * This method is used to get the order in String format.
	 *
	 * @return command in String form.
	 */
	public String getOrder() {
		return "blockade " + d_country;
	}

	/**
	 * This method is used to execute the blockade command
	 * 
	 * @param p_game gets the object of GameEngine class
	 * @return string
	 */
	@Override
	public String execute(GameEngine p_game) {
		String validationResult = validateBlockade(p_game);
		if (validationResult != null) {
			return validationResult;
		}

		return executeBlockade(p_game);
	}

	private String validateBlockade(GameEngine p_game) {
		int l_blockadeCardCount = d_player.d_cardsOwned.get("blockade");
		if (l_blockadeCardCount == 0) {
			return String.format("Player \"%s\" does not have a blockade card.", d_player.getName());
		}
		if (!d_player.getCountries().containsKey(d_country)) {
			return String.format("Player \"%s\" does not own country \"%d\".", d_player.getName(), d_country);
		}
		return null;
	}

	private String executeBlockade(GameEngine p_game) {
		int l_armiesPresent = d_player.getCountries().get(d_country).getNumberOfArmiesPresent();
		d_player.getCountries().get(d_country).setNumberOfArmiesPresent(l_armiesPresent * 3);
		d_player.getCountries().get(d_country).setPlayer(p_game.d_neutralPlayer);
		p_game.d_neutralPlayer.addCountry(p_game.getGameMap().getCountries().get(d_country));
		d_player.removeCountry(d_country);

		int l_blockadeCardCount = d_player.d_cardsOwned.get("blockade");
		d_player.d_cardsOwned.replace("blockade", l_blockadeCardCount - 1);
		return String.format("Blockade Card utilized successfully by player  \"%s\" on country  \"%d\".",
				d_player.getName(), d_country);
	}
}
