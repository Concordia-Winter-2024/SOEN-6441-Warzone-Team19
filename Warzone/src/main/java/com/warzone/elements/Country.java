package com.warzone.elements;

import java.util.HashSet;
import java.util.Set;

/**
 * Country class
 *
 */
public class Country {
    private int d_id;
    private Set<Country> d_neighborCountries;
    private int d_armiesPresent;
    private Continent d_continent;
    private Player d_owner;

    /**
     * Constructor
     *
     * @param p_id        id of the country
     * @param p_continent Parent continent
     */
    public Country(int p_id, Continent p_continent) {
        d_id = p_id;
        d_continent = p_continent;
        d_neighborCountries = new HashSet<Country>();
        d_armiesPresent = 0;
    }

    /**
     * This method is used to get the id of country
     *
     * @return d_id Id of a country
     */
    public int getId() {
        return d_id;
    }

    /**
     * This method is used to get the set of neighboring countries
     *
     * @return d_neighborCountries set of neighboring countries
     */
    public Set<Country> getNeighborCountries() {
        return d_neighborCountries;
    }

    /**
     * This method is used to add neigbour of a country
     * @param p_addCountry country to be added
     */
    public void addNeighbor(Country p_addCountry) {
		d_neighborCountries.add(p_addCountry);
	}

    /**
     * This method is used to remove neighbour country
     * @param p_removeCountry country to be removed
     * @return true or false
     */
    public boolean removeNeighbor(Country p_removeCountry) {
		if (!d_neighborCountries.contains(p_removeCountry)) {
			return false;
		}
		d_neighborCountries.remove(p_removeCountry);
		return true;
	}

    /**
     * This is used to get the number of armies present in the country
     *
     * @return d_armiesPresent number of armies
     */
    public int getNumberOfArmiesPresent() {
        return d_armiesPresent;
    }

    /**
     * This method is used to get the continent for which country belongs.
     * @return continent
     */
    public Continent getContinent() {
		return d_continent;
	}

    /**
     * This method returns the set of neighbor names
     *
     * @return l_neighborNameSet Set of neighbor names
     */
    public Set<Integer> getNeighborIds() {
        Set<Integer> l_neighborNameSet = new HashSet<>();
        for (Country l_country : d_neighborCountries) {
            l_neighborNameSet.add(l_country.getId());
        }
        return l_neighborNameSet;
    }

    /**
     * This method is used to set the owner of the country
     *
     * @param p_player player object
     */
    public void setPlayer(Player p_player) {
        d_owner = p_player;
    }

    /**
     * This method is used to get the owner of country
     *
     * @return d_owner owner player object
     */
    public Player getPlayer() {
        return d_owner;
    }

    /**
     * This method is used to place number of armies
     *
     * @param p_armiesNumber number of armies to be added
     */
    public void placeArmies(int p_armiesNumber) {
        d_armiesPresent += p_armiesNumber;
    }

    /**
     * This method is used to remove armies from country
     *
     * @param p_armiesNumber number of armies to be removed
     */
    public void removeArmies(int p_armiesNumber) {
        d_armiesPresent -= p_armiesNumber;
    }

}
