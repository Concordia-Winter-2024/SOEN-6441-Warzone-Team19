package com.warzone.elements.savegameplay;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Continent;
import com.warzone.elements.Country;
import com.warzone.elements.GameMap;
import com.warzone.elements.Player;
import com.warzone.elements.orders.Orders;
import com.warzone.strategy.Cheater;
import com.warzone.strategy.Aggressive;
import com.warzone.strategy.Benevolent;
import com.warzone.strategy.HumanPlayer;
import com.warzone.strategy.RandomPlayer;

/**
 * Class for saving the game into ".game" file.
 */
public class SaveGame {

    private GameMap d_gameMap;
    private LinkedHashMap<Integer, Integer> d_continentsMap;
    private LinkedHashMap<Integer, Integer> d_countriesMap;
    private BufferedWriter d_writer;
    private GameEngine d_gameEngine;

    /**
     * Save Game class Constructor.
     *
     * @param p_gameEngine object of the game engine provides access to additional
     *                     game resources.
     */
    public SaveGame(GameEngine p_gameEngine) {
        d_gameMap = new GameMap();
        d_continentsMap = new LinkedHashMap<>();
        d_countriesMap = new LinkedHashMap<>();
        d_gameEngine = p_gameEngine;
    }

    /**
     * This method is saves the game in a formatted way that can be used
     * afterward to load.
     *
     * @param p_fileName    File which will save the game.
     * @param p_callPlayer Name of the player who wants to save the game.
     * @return Returns the message indicating the status of save game.
     */
    public String saveGame(String p_fileName, Player p_callPlayer) {
        d_gameMap = d_gameEngine.getGameMap();
        int l_countryCtn = 0, l_continentCtn = 0;
        try {
            FileWriter l_fw = new FileWriter(
                    Paths.get(Paths.get("").toAbsolutePath() + "/games/" + p_fileName).toString());
            d_writer = new BufferedWriter(l_fw);

            HashMap<Integer, Continent> l_continents;
            l_continents = d_gameMap.getContinents();
            d_writer.write("[continents]");
            d_writer.newLine();
            for (int p_continents : l_continents.keySet()) {
                d_continentsMap.put(p_continents, ++l_continentCtn);
                d_writer.write(p_continents + " " + l_continents.get(p_continents).getControlValue());
                d_writer.newLine();
            }

            d_writer.write("\n");
            d_writer.write("[countries]");
            d_writer.newLine();
            for (int p_continents : d_continentsMap.keySet()) {
                Set<Integer> l_countriesId = l_continents.get(p_continents).getCountriesIds();
                for (int p_countriesId : l_countriesId) {
                    d_countriesMap.put(p_countriesId, ++l_countryCtn);
                    d_writer.write(l_countryCtn + " " + p_countriesId + " " + d_continentsMap.get(p_continents));
                    d_writer.newLine();
                }
            }

            HashMap<Integer, Country> l_countries;
            l_countries = d_gameMap.getCountries();
            d_writer.write("\n");
            d_writer.write("[borders]");
            d_writer.newLine();
            for (int p_countries : d_countriesMap.keySet()) {
                Set<Integer> l_neighborIds = l_countries.get(p_countries).getNeighborIds();
                StringBuilder l_sb = new StringBuilder();
                l_sb.append(d_countriesMap.get(p_countries).toString()).append(" ");

                for (int p_neighborIds : l_neighborIds) {
                    l_sb.append(d_countriesMap.get(p_neighborIds).toString()).append(" ");
                }
                d_writer.write(l_sb.toString());
                d_writer.newLine();
            }

            d_writer.newLine();
            d_writer.write(
                    "[PlayerName|Strategy|#Continents|#Countries|NumArmies|[ContinentId]|[CountryId CountryArmies]|[Airlift,Blockade,Bomb,Diplomacy]|[NegotiatedPlayersList]]");
            d_writer.newLine();
            for (Player l_currentPlayer : d_gameEngine.d_players.values()) {
                // player name
                d_writer.write(l_currentPlayer.getName());

                // behavior of the player
                d_writer.write("|");
                {
                    if (l_currentPlayer.getPlayerBehaviour() instanceof Aggressive) {
                        d_writer.write("aggressivePlayer");
                    } else if (l_currentPlayer.getPlayerBehaviour() instanceof Benevolent) {
                        d_writer.write("benevolentPlayer");
                    } else if (l_currentPlayer.getPlayerBehaviour() instanceof Cheater) {
                        d_writer.write("cheaterPlayer");
                    } else if (l_currentPlayer.getPlayerBehaviour() instanceof HumanPlayer) {
                        d_writer.write("humanPlayer");
                    } else if (l_currentPlayer.getPlayerBehaviour() instanceof RandomPlayer) {
                        d_writer.write("randomPlayer");
                    }
                }

                d_writer.write("|");
                d_writer.write(l_currentPlayer.getContinents().size() + "");


                d_writer.write("|");
                d_writer.write(l_currentPlayer.getCountries().size() + "");


                d_writer.write("|");
                d_writer.write(l_currentPlayer.getNumberOfArmies() + "");


                d_writer.write("|");
                d_writer.write("[");
                boolean l_tempFlag = false;
                for (Continent l_continent : l_currentPlayer.getContinents().values()) {
                    if (l_tempFlag) {
                        d_writer.write(",");
                    }
                    l_tempFlag = true;
                    d_writer.write(l_continent.getId() + "");
                }
                d_writer.write("]");


                d_writer.write("|");
                d_writer.write("[");
                l_tempFlag = false;
                for (Country l_country : l_currentPlayer.getCountries().values()) {
                    if (l_tempFlag) {
                        d_writer.write(",");
                    }
                    l_tempFlag = true;
                    d_writer.write(l_country.getId() + " " + l_country.getNumberOfArmiesPresent());
                }
                d_writer.write("]");


                d_writer.write("|");
                d_writer.write("[");
                HashMap<String, Integer> l_cardsOwned = l_currentPlayer.d_cardsOwned;
                d_writer.write("airlift " + l_cardsOwned.get("airlift"));
                d_writer.write(",blockade " + l_cardsOwned.get("blockade"));
                d_writer.write(",bomb " + l_cardsOwned.get("bomb"));
                d_writer.write(",diplomacy " + l_cardsOwned.get("diplomacy"));
                d_writer.write("]");
                d_writer.write("|");
                d_writer.write("[");
                l_tempFlag = false;
                for (String l_negotiatedPlayer : l_currentPlayer.d_negotiatedPlayerNames) {
                    if (l_tempFlag) {
                        d_writer.write(",");
                    }
                    l_tempFlag = true;
                    d_writer.write(l_negotiatedPlayer);
                }
                d_writer.write("]");
                d_writer.newLine();
            }

            Player l_neutralPlayer = d_gameEngine.d_neutralPlayer;
            d_writer.newLine();
            d_writer.write("[neutralPlayer]");
            d_writer.newLine();
            d_writer.write("neutralPlayer#1|neutral|");
            d_writer.write(l_neutralPlayer.getContinents().size() + "|" + l_neutralPlayer.getCountries().size() + "|"
                    + l_neutralPlayer.getNumberOfArmies() + "|");

            d_writer.write("[");
            boolean l_tempFlag = false;
            for (Continent l_continent : l_neutralPlayer.getContinents().values()) {
                if (l_tempFlag) {
                    d_writer.write(",");
                }
                l_tempFlag = true;
                d_writer.write(l_continent.getId() + "");
            }
            d_writer.write("]|[");
            l_tempFlag = false;
            for (Country l_country : l_neutralPlayer.getCountries().values()) {
                if (l_tempFlag) {
                    d_writer.write(",");
                }
                l_tempFlag = true;
                d_writer.write(l_country.getId() + " " + l_country.getNumberOfArmiesPresent());
            }
            d_writer.write("]|[airlift 0,blockade 0,bomb 0,diplomacy 0]|[]");
            d_writer.newLine();


            d_writer.newLine();
            d_writer.write("[orders]");
            d_writer.newLine();

            HashMap<String, Queue<String>> l_playerOrderHashMap = new HashMap<>();
            for (Player l_currentPlayer : d_gameEngine.d_players.values()) {
                Queue<String> l_currentPlayerQueue = new LinkedList<String>();
                for (Orders l_tempOrders : l_currentPlayer.d_orders) {
                    l_currentPlayerQueue.add(l_tempOrders.getOrder());
                }
                l_playerOrderHashMap.put(l_currentPlayer.getName(), l_currentPlayerQueue);
            }


            boolean l_hasMoreOrders;
            do {
                l_hasMoreOrders = false;
                for (String l_currentPlayer : l_playerOrderHashMap.keySet()) {
                    if (l_playerOrderHashMap.get(l_currentPlayer).isEmpty()) {
                        l_hasMoreOrders = false;
                        continue;
                    }

                    l_hasMoreOrders = true;
                    String l_commandString = l_playerOrderHashMap.get(l_currentPlayer).remove();
                    d_writer.write(l_currentPlayer + " " + l_commandString);
                    d_writer.newLine();
                }
            } while (l_hasMoreOrders);


            d_writer.newLine();
            d_writer.write("[committedPlayer]");
            d_writer.newLine();
            for (Player l_currentPlayer : d_gameEngine.d_players.values()) {
                if (l_currentPlayer.getIsCommit()) {
                    d_writer.write(l_currentPlayer.getName());
                    d_writer.newLine();
                }
            }


            d_writer.newLine();
            d_writer.write("[nextPlayer]");
            d_writer.newLine();
            d_writer.write(p_callPlayer.getName());
            d_writer.newLine();

            d_writer.close();
            l_fw.close();
            return "Game saved successfully in " + p_fileName;
        } catch (Exception p_e) {
            System.out.println("Exception " + p_e.getMessage());
            p_e.printStackTrace();
            d_gameEngine.d_logEntryBuffer.setString("Saving Game Unsuccessful..");
            return "Saving Game Unsuccessful..";
        }
    }
}
