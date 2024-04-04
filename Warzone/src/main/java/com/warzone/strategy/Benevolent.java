package com.warzone.strategy;

import com.warzone.controller.GameEngine;
import com.warzone.elements.Country;
import com.warzone.elements.Player;
import com.warzone.elements.orders.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Benevolent class which is used to protect its weak countries.
 */
public class Benevolent extends PlayerStrategy {

    /**
     * Constructor method for the class
     *
     * @param p_player     name of the aggressive player
     * @param p_gameEngine GameEngine object
     */
    public Benevolent(Player p_player, GameEngine p_gameEngine) {
        super(p_player, p_gameEngine);
    }

    /**
     * This method is used to get neighboring countries of the country
     *
     * @return neighbors of the countries
     */
    public HashSet getNeighborCountries() {
        HashSet<Integer> l_neighbours = new HashSet<>();
        for (Country l_country : d_gameEngine.getGameMap().getCountries().values()) {
            l_neighbours.addAll(l_country.getNeighborIds());
        }
        return l_neighbours;
    }

    /**
     * This method is used to create orders to deploy on its weakest country
     * moves its armies in order to reinforce its weaker country
     */
    @Override
    public Orders createOrder() {
        Orders l_order = null;
        int l_randomOrder;
        List<Integer> l_playerCountries = new ArrayList<>(d_player.getCountries().keySet());
        int l_randomArmies;
        Country l_weakCountry;
        boolean l_isComplete = false;

        if (l_playerCountries.isEmpty()) {
            return new Dummy();
        }

        while (!l_isComplete) {
            l_randomOrder = d_gameEngine.d_random.nextInt(4 - 1) + 1;
            switch (l_randomOrder) {
                case 1:
                    l_weakCountry = d_player.getCountries().values().iterator().next();
                    for (Country l_country : d_player.getCountries().values()) {
                        if (l_weakCountry.getNumberOfArmiesPresent() > l_country.getNumberOfArmiesPresent()
                                && d_player.getCountries().containsKey(l_country.getId())) {
                            l_weakCountry = l_country;
                        }
                    }
                    l_randomArmies = d_gameEngine.d_random.nextInt(d_player.getNumberOfArmies());
                    l_randomArmies = l_randomArmies == 0 ? 1 : l_randomArmies;
                    l_order = new Deploy(d_player, l_weakCountry.getId(), l_randomArmies);
                    System.out.println("Deploy " + l_weakCountry.getId() + ",armies:  " + l_randomArmies);
                    d_gameEngine.d_logEntryBuffer
                            .setString("Deploy " + l_weakCountry.getId() + ",armies:  " + l_randomArmies);
                    l_isComplete = true;
                    break;

                case 2:
                    Country l_strongNeighbour = null;
                    l_weakCountry = d_player.getCountries().values().iterator().next();
                    for (Country l_country : d_player.getCountries().values()) {

                        if (l_weakCountry.getNumberOfArmiesPresent() > l_country.getNumberOfArmiesPresent()
                                && d_player.getCountries().containsKey(l_country.getId())) {
                            l_weakCountry = l_country;
                        }
                    }

                    for (Country l_country : d_player.getCountries().values()) {
                        if (!l_country.equals(l_weakCountry)
                                && (l_country.getNeighborIds().contains(l_weakCountry.getId()))) {
                            if (l_strongNeighbour == null) {
                                l_strongNeighbour = l_country;
                            } else {
                                if (l_country.getNumberOfArmiesPresent() > l_strongNeighbour.getNumberOfArmiesPresent()) {
                                    l_strongNeighbour = l_country;
                                }
                            }
                        }
                    }

                    if (l_strongNeighbour == null || l_strongNeighbour.getNumberOfArmiesPresent() < 2) {
                        continue;
                    }
                    l_randomArmies = d_gameEngine.d_random.nextInt(d_player.getNumberOfArmies());
                    l_randomArmies = l_randomArmies == 0 ? 1 : l_randomArmies;

                    l_order = new Advance(d_player, l_strongNeighbour.getId(), l_weakCountry.getId(), l_randomArmies);
                    System.out.println("Advance from: " + l_strongNeighbour.getId() + ", to: " + l_weakCountry.getId()
                            + ", armies:  " + l_randomArmies);
                    d_gameEngine.d_logEntryBuffer.setString("Advance from: " + l_strongNeighbour.getId() + ", to: "
                            + l_weakCountry.getId() + ", armies:  " + l_randomArmies);
                    l_isComplete = true;
                    break;

                case 3:
                    Country l_strongCountry = null;
                    if (d_player.d_cardsOwned.get("airlift") < 1) {
                        continue;
                    }
                    l_weakCountry = d_player.getCountries().get(0);
                    for (Country l_country : d_player.getCountries().values()) {
                        if (l_weakCountry.getNumberOfArmiesPresent() > l_country.getNumberOfArmiesPresent()
                                && d_player.getCountries().containsKey(l_country.getId())) {
                            l_weakCountry = l_country;
                        }
                    }
                    for (Country l_country : d_player.getCountries().values()) {
                        if (!l_country.equals(l_weakCountry)) {
                            if (l_strongCountry == null) {
                                l_strongCountry = l_country;
                            } else {
                                if (l_country.getNumberOfArmiesPresent() > l_strongCountry.getNumberOfArmiesPresent()) {
                                    l_strongCountry = l_country;
                                }
                            }
                        }
                    }
                    if (l_strongCountry == null || l_strongCountry.getNumberOfArmiesPresent() < 2) {
                        continue;
                    }
                    l_randomArmies = d_gameEngine.d_random.nextInt(d_player.getNumberOfArmies());
                    l_randomArmies = l_randomArmies == 0 ? 1 : l_randomArmies;
                    l_order = new Airlift(d_player, l_strongCountry.getId(), l_weakCountry.getId(), l_randomArmies);
                    System.out.println("Move from: " + l_strongCountry.getId() + ", to: " + l_weakCountry.getId()
                            + ", armies:  " + l_randomArmies);
                    d_gameEngine.d_logEntryBuffer.setString("Move from: " + l_strongCountry.getId() + ", to: "
                            + l_weakCountry.getId() + ", armies:  " + l_randomArmies);
                    l_isComplete = true;
                    break;

                default:
                    System.out.println("EXIT::" + l_randomOrder);
                    break;
            }
        }

        return l_order;
    }


}