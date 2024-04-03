package com.warzone.strategy;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Country;
import com.warzone.elements.Player;
import com.warzone.elements.orders.Dummy;
import com.warzone.elements.orders.Orders;
import java.util.*;

/**
 * Class to implement the Cheater Player
 */
public class Cheater extends PlayerStrategy {

	/**
	 * Constructor to initialize the player and gameEngine object
	 * 
	 * @param p_player     Cheater player
	 * @param p_gameEngine Game Engine object
	 */
	public Cheater(Player p_player, GameEngine p_gameEngine) {
		super(p_player, p_gameEngine);
	}

	/**
	 * This method is used to implement logic of acquiring neighbouring countries 
     * and doubling armies for the Cheater Player.
	 * 
	 * @return The dummy order.
	 */
	@Override
	public Orders createOrder() {
		Collection<Country> l_playerOwnedCountriesBefore = d_player.getCountries().values();
		Collection<Integer> l_playerOwnedCountriesBeforeIds = d_player.getCountries().keySet();
		HashSet<Integer> l_playerOwnedCountriesAfter = new HashSet<>();
		for (Country l_currentCountry : l_playerOwnedCountriesBefore) {
			l_playerOwnedCountriesAfter.addAll(l_currentCountry.getNeighborIds());
		}

		for (Integer l_currentCountryIdInteger : l_playerOwnedCountriesAfter) {
			Country l_country = d_gameEngine.getGameMap().getCountries().get(l_currentCountryIdInteger);
			if (!l_playerOwnedCountriesBeforeIds.contains(l_country.getId())) {
				l_country.placeArmies(l_country.getNumberOfArmiesPresent());
				d_player.addCountry(l_country);
				l_country.getPlayer().removeCountry(l_country.getId());
				l_country.setPlayer(d_player);
			}
		}
		return new Dummy();
	}
}
