package com.warzone.controller.state.gamephase.gamesetup;

import com.warzone.controller.GameEngine;
import com.warzone.controller.state.gamephase.gameplay.AssignArmies;
import com.warzone.elements.Country;
import com.warzone.elements.Player;

import com.warzone.strategy.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Game entered this phase after preload class, it contains methods to add or remove
 * players from game and to assign countries randomly to players.
 */
public class PostLoad extends GameSetup {

	/**
	 * constructor method that takes game engine object from the parent class
	 * 
	 * @param p_gameEngine object of the game engine
	 */
	public PostLoad(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * This method is used to load the map for playing the game
	 * 
	 * @param p_fileName Name of the map file to be loaded
	 * @return string suggesting that map has already been loaded as it is in the
	 *         postLoad phase
	 */
	@Override
	public String loadMap(String p_fileName) {
		return "Map already loaded";
	}

	/**
	 * This method is used to support adding and removing of players to the game
	 * 
	 * @param p_commandSplitted splitted command parts used for execution of command
	 * @return the result of the command provided
	 */
	@Override
	public String gamePlayer(String[] p_commandSplitted) {
		String l_result;
		if ("-add".equals(p_commandSplitted[0])) {
			l_result = addPlayer(p_commandSplitted[1]);
		} else {
			l_result = removePlayer(p_commandSplitted[1]);
		}
		return l_result;
	}

	/**
	 * This method is used to assign countries to the players present in the game
	 * 
	 * @return string indicating that countries are assigned to the players
	 */
	@Override
	public String assignCountries() {
		if (d_gameEngine.d_players.size() < 2) {
			return "There must be at least two player";
		}
		for (Player l_player : d_gameEngine.d_players.values()) {
			boolean l_isCorrect = false;
			if (l_player.d_strategy == null) {
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println(" _______________________________ ");
				System.out.println("| Player Strategy Selection\t|");
				System.out.println("|===============================|");
				System.out.println("| 1. Random Player\t\t|");
				System.out.println("| 2. Human Player\t\t|");
				System.out.println("| 3. Benevolent Player\t\t|");
				System.out.println("| 4. Aggressive Player\t\t|");
				System.out.println("| 5. Cheater Player\t\t|");
				System.out.println("|_______________________________|");
				while (!l_isCorrect) {
					System.out.print("Enter strategy for player " + l_player.getName() + ": ");
                    switch (new Scanner(System.in).nextInt()) {
                        case 1 -> {
                            l_player.setStrategy(new RandomPlayer(l_player, d_gameEngine));
                            d_gameEngine.d_logEntryBuffer.setString(String.format("Player %s strategy set to %s.", l_player.getName(), "Random"));
                            l_isCorrect = true;
                        }
                        case 2 -> {
                            l_player.setStrategy(new HumanPlayer(l_player, d_gameEngine));
                            d_gameEngine.d_logEntryBuffer.setString(String.format("Player %s strategy set to %s.", l_player.getName(), "Human"));
                            l_isCorrect = true;
                        }
                        case 3 -> {
                            l_player.setStrategy(new Benevolent(l_player, d_gameEngine));
                            d_gameEngine.d_logEntryBuffer.setString(String.format("Player %s strategy set to %s.", l_player.getName(), "Benevolent"));
                            l_isCorrect = true;
                        }
                        case 4 -> {
                            l_player.setStrategy(new Aggressive(l_player, d_gameEngine));
                            d_gameEngine.d_logEntryBuffer.setString(String.format("Player %s strategy set to %s.", l_player.getName(), "Aggressive"));
                            l_isCorrect = true;
                        }
                        case 5 -> {
                            l_player.setStrategy(new Cheater(l_player, d_gameEngine));
                            d_gameEngine.d_logEntryBuffer.setString(String.format("Player %s strategy set to %s.", l_player.getName(), "Cheater"));
                            l_isCorrect = true;
                        }
                        default -> System.out.println("Please enter valid behaviour");
                    }
				}

			}
		}
		HashMap<Integer, Country> l_countries = d_gameEngine.getGameMap().getCountries();
        List<Country> l_countryObjects = new ArrayList<>(l_countries.values());
		while (true) {
			for (Player p_player : d_gameEngine.d_players.values()) {
				if (l_countryObjects.isEmpty()) {
					break;
				}
				int l_idOfCountry = d_gameEngine.d_random.nextInt(l_countryObjects.size());
				p_player.addCountry(l_countryObjects.get(l_idOfCountry));
				l_countryObjects.get(l_idOfCountry).setPlayer(p_player);
				l_countryObjects.remove(l_countryObjects.get(l_idOfCountry));
			}
			if (l_countryObjects.isEmpty()) {
				break;
			}
		}
		System.out.println("Countries Assigned");
		d_gameEngine.d_logEntryBuffer.setString("Countries Assigned");
		System.out.println("Countries Assigned");
		next();
		d_gameEngine.getPhase().assignArmies();
		return "";
	}

	/**
	 * This method is used to add a player in the game and checks whether the player is already
	 * present in the game or not
	 * 
	 * @param p_playerName Name of the player to be added
	 * @return string indicating whether player is added or not
	 */
	@Override
	public String addPlayer(String p_playerName) {
		if (d_gameEngine.d_players.containsKey(p_playerName)) {
			d_gameEngine.d_logEntryBuffer
					.setString(String.format("Player \"%s\" already present in game", p_playerName));
			return String.format("Player \"%s\" already present in game", p_playerName);
		}
		d_gameEngine.d_players.put(p_playerName, new Player(p_playerName));
		d_gameEngine.d_playerName.add(p_playerName);
		d_gameEngine.d_logEntryBuffer.setString(String.format("Player \"%s\" added to game", p_playerName));
		return String.format("Player \"%s\" added to game", p_playerName);
	}

	/**
	 * This method is used to remove a player from the game and checks whether the player is
	 * already present in the game or not
	 * 
	 * @param p_playerName Name of the player to be removed
	 * @return sting indicating the whether player is removed or not
	 */
	@Override
	public String removePlayer(String p_playerName) {
		if (!d_gameEngine.d_players.containsKey(p_playerName)) {
			d_gameEngine.d_logEntryBuffer.setString(String.format("Player \"%s\" not present in game", p_playerName));
			return String.format("Player \"%s\" not present in game", p_playerName);
		}
		d_gameEngine.d_players.remove(p_playerName);
		d_gameEngine.d_playerName.remove(p_playerName);
		d_gameEngine.d_logEntryBuffer.setString(String.format("Player \"%s\" removed from game", p_playerName));
		return String.format("Player \"%s\" removed from game", p_playerName);
	}

	/**
	 * This method is used that sets the phase to proceed in the game
	 */
	@Override
	public void next() {
		d_gameEngine.setPhase(new AssignArmies(d_gameEngine));
	}

	/**
	 * Method to load a previously saved game
	 *
	 * @param p_fileName name of the file to be loaded
	 * @return string representing the result of loading the file
	 */
	@Override
	public String loadGame(String p_fileName) {
		return printInvalidCommandMessage();
	}
}
