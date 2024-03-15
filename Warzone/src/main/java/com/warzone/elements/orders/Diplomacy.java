package com.warzone.elements.orders;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Player;

/**
 * This class represents the diplomacy card. The Diplomacy Card allows you to
 * negotiate with another player for one time. Once the negotiation is done,
 * the players cannot attack each other for the rest of the turn.
 *
 */
public class Diplomacy implements Orders {
	private Player d_player;
	private Player d_otherPlayer;
	private String d_otherPlayerName;

	/**
	 * Constructor for Diplomacy class
	 *
	 * @param p_player      Player object of the player who wants to negotiate with
	 *                      other player for one time.
	 * @param p_otherPlayer Name of the other player who is being negotiated for one
	 *                      time.
	 */
	public Diplomacy(Player p_player, String p_otherPlayer) {
		d_player = p_player;
		d_otherPlayerName = p_otherPlayer;
	}

	/**
	 * Method to execute the diplomacy command
	 *
	 * @param p_game gets the object of GameEngine class
	 * @return string Result based on the execution of the Diplomacy Card.
	 */
	@Override
	public String execute(GameEngine p_game) {
		if (!p_game.d_playerName.contains(d_otherPlayerName)) {
			return "Player \"" + d_otherPlayerName + "\" does not exist";
		} else {
			int l_diplomacyCardCount = d_player.d_cardsOwned.get("diplomacy");
			if (l_diplomacyCardCount < 1) {
				return String.format("Player \"%s\" does not have Diplomacy Card.", d_player.getName());
			} else {
				d_otherPlayer = p_game.d_players.get(d_otherPlayerName);
				d_otherPlayer.d_negotiatedPlayerNames.add(d_player.getName());
				d_player.d_negotiatedPlayerNames.add(d_otherPlayer.getName());
				d_player.d_cardsOwned.replace("diplomacy", (l_diplomacyCardCount - 1));
				return "Diplomacy between Players \"" + d_player.getName() + "\" and \"" + d_otherPlayer.getName()
						+ "\" established successfully.";
			}
		}
	}
}
