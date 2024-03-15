package com.warzone.elements;

import java.util.*;

import com.warzone.controller.GameInitialization;

import com.warzone.controller.state.gamephase.gameplay.IssueOrders;
import com.warzone.elements.orders.Advance;
import com.warzone.elements.orders.Deploy;
import com.warzone.elements.orders.Orders;

import com.warzone.elements.orders.*;

/**
 * Player class in a game
 */
public class Player {
    private String d_name;
    private HashMap<Integer, Country> d_countries;
    private HashMap<Integer, Continent> d_continents;
    public Queue<Orders> d_orders;
    private int d_numberOfArmies;
    private boolean d_isCommit;
    public HashMap<String, Integer> d_cardsOwned;
    public ArrayList<String> d_negotiatedPlayerNames;
    public boolean d_isConquered;

    /**
     * Constructor of player which sets initial values for player data
     *
     * @param p_name name of the player
     */
    public Player(String p_name) {
        d_name = p_name;
        d_countries = new HashMap<>();
        d_continents = new HashMap<>();
        d_orders = new LinkedList<>();
        d_numberOfArmies = 0;
        d_isCommit = false;
        d_cardsOwned = new HashMap<>();
        d_cardsOwned.put("bomb", 0);
        d_cardsOwned.put("blockade", 0);
        d_cardsOwned.put("airlift", 0);
        d_cardsOwned.put("diplomacy", 0);
        d_negotiatedPlayerNames = new ArrayList<String>();
        d_isConquered = false;
    }

    /**
     * This method is used to get the name of player
     *
     * @return d_name name of player
     */
    public String getName() {
        return d_name;
    }

    /**
     * This method is used to get the countries occupied by player
     *
     * @return d_countries HashMap of countries and their id occupied by player
     */
    public HashMap<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * This method is used to get the continents occupied by player
     *
     * @return d_continents HashMap of continents and their id occupied by player
     */
    public HashMap<Integer, Continent> getContinents() {
        return d_continents;
    }

    /**
     * This method is used to get the number of armies player has
     *
     * @return d_numberOfArmies number of armies
     */
    public int getNumberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * This method is used to remove armies to player
     *
     * @param p_numberArmies number of armies to be removed
     */
    public void removeArmies(int p_numberArmies) {
        d_numberOfArmies -= p_numberArmies;
    }

    /**
     * This is method to add country to player when player wins the country
     *
     * @param p_country Object of country
     */
    public void addCountry(Country p_country) {
        d_countries.put(p_country.getId(), p_country);
    }

    /**
     * This method is used to remove country from player when player loses the country
     *
     * @param p_countryId Id of the country
     */
    public void removeCountry(int p_countryId) {
        d_countries.remove(p_countryId);
    }

    /**
     * This method is used to add continent to player when player wins the all countries of continent
     *
     * @param p_continent object of the continent
     */
    public void addContinent(Continent p_continent) {
        d_continents.put(p_continent.getId(), p_continent);
    }

    /**
     * This method is used to remove continent from player when player loses the all countries of continent
     *
     * @param p_continentId Id of continent
     */
    public void removeContinent(int p_continentId) {
        d_continents.remove(p_continentId);
    }

    /**
     * This method is used for setting the number of armies
     */
    public void setNumberOfArmies() {
        d_numberOfArmies = d_countries.size() / 3;
        for (Continent l_continent : d_continents.values()) {
            d_numberOfArmies += l_continent.getControlValue();
        }
        if (d_numberOfArmies < 3) {
            d_numberOfArmies = 3;
        }
    }

    /**
     * Get boolean value which depicts that player has no more orders in this turn.
     *
     * @return true if player has no more orders; else false
     */
    public boolean getIsCommit() {
        return d_isCommit;
    }

    /**
     * Used to set whenever player has no more values or when the turn has started.
     * Set to false at start of each turn and to true when player has no more
     * orders.
     *
     * @param p_isCommit true if player has no more orders; else false.
     */
    public void setIsCommit(boolean p_isCommit) {
        d_isCommit = p_isCommit;
    }

    /**
     * This method is used to issue order
     */
    public void issueOrder() {
        GameInitialization l_gameInitialization = new GameInitialization();
        l_gameInitialization.setPhase(new IssueOrders(null));
        String[] l_splittedOrder = null;
        boolean l_isCorrect = false;
        while (!l_isCorrect) {
            try {
                String l_result = (l_gameInitialization.getCommand());
                if ("exit".equals(l_result)) {
                    l_isCorrect = true;
                    d_isCommit = true;
                    return;
                } else {
                    l_splittedOrder = l_result.split(" ");

                    switch (l_splittedOrder[0]) {
                        case "deploy":
                            if (l_splittedOrder.length != 3) {
                                String l_temp = "Invalid command. Correct command is - deploy countryId numarmies";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else if (!isNumeric(l_splittedOrder[1]) || !isNumeric(l_splittedOrder[2])) {
                                String l_temp = "After deploy keyword, you can only use integer to represent the countryId and numarmies";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else {
                                Deploy l_deploy = new Deploy(this, Integer.parseInt(l_splittedOrder[1]),
                                        Integer.parseInt(l_splittedOrder[2]));
                                d_orders.add(l_deploy);
                                String l_temp = "deploy " + Integer.parseInt(l_splittedOrder[1]) + " "
                                        + Integer.parseInt(l_splittedOrder[2]);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                l_isCorrect = true;
                            }
                            break;
                        case "advance":
                            if (l_splittedOrder.length != 4) {
                                String l_temp = "Invalid command. Correct command is - advance countryFrom countryTo numarmies";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else if (!isNumeric(l_splittedOrder[1]) || !isNumeric(l_splittedOrder[2])
                                    || !isNumeric(l_splittedOrder[3])) {
                                String l_temp = "After advance keyword, you can only use integer to represent the countryFrom, countryTo and numarmies";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else {
                                Advance l_advance = new Advance(this, Integer.parseInt(l_splittedOrder[1]),
                                        Integer.parseInt(l_splittedOrder[2]), Integer.parseInt(l_splittedOrder[3]));
                                d_orders.add(l_advance);
                                String l_temp = "advance " + Integer.parseInt(l_splittedOrder[1]) + " "
                                        + Integer.parseInt(l_splittedOrder[2]) + " " + Integer.parseInt(l_splittedOrder[3]);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                l_isCorrect = true;
                            }
                            break;
                        case "bomb":
                            if (l_splittedOrder.length != 2) {
                                String l_temp = "Invalid command. Correct command is - bomb countryId";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else if (!isNumeric(l_splittedOrder[1])) {
                                String l_temp = "After bomb keyword, you can only use integer to represent the countryId";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else {
                                Bomb l_bomb = new Bomb(this, Integer.parseInt(l_splittedOrder[1]));
                                d_orders.add(l_bomb);
                                String l_temp = "bomb " + Integer.parseInt(l_splittedOrder[1]);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                l_isCorrect = true;
                            }
                            break;
                        case "blockade":
                            if (l_splittedOrder.length != 2) {
                                String l_temp = "Invalid command. Correct command is - blockade countryId";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else if (!isNumeric(l_splittedOrder[1])) {
                                String l_temp = "After blockade keyword, you can only use integer to represent the countryId";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else {
                                Blockade l_blockade = new Blockade(this, Integer.parseInt(l_splittedOrder[1]));
                                d_orders.add(l_blockade);
                                String l_temp = "blockade " + Integer.parseInt(l_splittedOrder[1]);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                l_isCorrect = true;
                            }
                            break;
                        case "airlift":
                            if (l_splittedOrder.length != 4) {
                                String l_temp = "Invalid command. Correct command is - airlift sourceCountryId targetCountryId numarmies";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else if (!isNumeric(l_splittedOrder[1]) || !isNumeric(l_splittedOrder[2])
                                    || !isNumeric(l_splittedOrder[3])) {
                                String l_temp = "After airlift keyword, you can only use integer to represent the sourceCountryId, targetCountryId and numarmies";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else {
                                Airlift l_airlift = new Airlift(this, Integer.parseInt(l_splittedOrder[1]),
                                        Integer.parseInt(l_splittedOrder[2]), Integer.parseInt(l_splittedOrder[3]));
                                d_orders.add(l_airlift);
                                String l_temp = "airlift " + Integer.parseInt(l_splittedOrder[1]) + " "
                                        + Integer.parseInt(l_splittedOrder[2]) + " " + Integer.parseInt(l_splittedOrder[3]);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                l_isCorrect = true;
                            }
                            break;
                        case "negotiate":
                            if (l_splittedOrder.length != 2) {
                                String l_temp = "Invalid command. Correct command is - negotiate playerId";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else if (isNumeric(l_splittedOrder[1])) {
                                String l_temp = "After negotiate keyword, you can not use integer to represent the playerName";
                                System.out.println(l_temp);
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                continue;
                            } else {
                                Diplomacy l_diplomacy = new Diplomacy(this, l_splittedOrder[1]);
                                d_orders.add(l_diplomacy);
                                String l_temp = "negotiate " + l_splittedOrder[1];
                                l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_temp);
                                l_isCorrect = true;
                            }
                            break;
                        default:
                            System.out.println(l_result);
                            l_gameInitialization.d_gameEngine.d_logEntryBuffer.setString(l_result);
                            break;
                    }
                }
            } catch (Exception p_exception) {
                System.out.println("Something went wront. Exception occured.");
            }
        }
    }

    /**
     * This function is used to check if a string can be converted to integer or
     * not.
     *
     * @param p_str represents the string to be casted to Integer value.
     * @return true if the string can be parsed to an Integer.
     */
    public static boolean isNumeric(String p_str) {
        try {
            Integer.parseInt(p_str);
            return true;
        } catch (NumberFormatException p_e) {
            return false;
        } catch (Exception p_e) {
            return false;
        }
    }

    /**
     * This method to check if player owns all the countries of continent
     *
     * @param p_continent continent for which ownership is to be checked
     * @return true if player owns all the countries of continent; else false
     */
    public boolean checkContinent(Continent p_continent) {
        return d_countries.keySet().containsAll(p_continent.getCountriesIds());
    }

    /**
     * This method is used to get the next order
     *
     * @return order
     */
    public Orders next_order() {
        return d_orders.remove();
    }
}
