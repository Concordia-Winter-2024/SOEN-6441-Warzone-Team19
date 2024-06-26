 package com.warzone.elements.map;

import java.util.HashSet;
import java.util.Set;

import com.warzone.elements.Continent;
import com.warzone.elements.Country;
import com.warzone.elements.GameMap;

/**
 * This class will validate the map of the game connection of all countries,
 * continents with their respective countries mapping
 *
 */
public class MapValidation {
    private boolean d_connectedGraph;
    private boolean d_connectedSubGraph = true;
    private GameMap d_gameMap;
    private boolean d_emptyMap = false;
    private boolean d_emptyContinent = false;
    private boolean d_flag = false;
    private boolean d_iteratingContinent = false;
    private int d_currentContinentIteration = 0;

    /**
     * Constructor of MapValidation
     *
     * @param p_gameMap the map which you want to validate
     */
    public MapValidation(GameMap p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    /**
     * This method is used to get the instant status of map validation in the form
     * of boolean. True signifies that the map validation is successful whereas
     * false signifies it wasn't. (Do not call this method before calling the
     * validate method)
     *
     * @return instant status of the validated map in the boolean format
     */
    public boolean getMapValidationStatus() {
        if (d_flag) {
            return d_connectedGraph && (!d_emptyContinent) && (!d_emptyMap) && d_connectedSubGraph;
        } else {
            System.out.println("Please validate the map before getting the status of map.");
            return false;
        }
    }

    /**
     * This is the prime method to validate whole graph it will show the status of
     * validation of map
     *
     * @return the validation result to print based on all the validation criteria
     *         like connected countries, connected continent, empty continents.
     */
    public String validate() {
        d_flag = true;
        StringBuilder l_validationResult = new StringBuilder();
        boolean l_result = checkAll();

        if (!l_result) {
            if (d_emptyMap) {
                l_validationResult.append("The Map does not contain any countries.");
            } else {
                if (!d_connectedGraph) {
                    l_validationResult.append(" The graph is not connected. Countries are not traversable.");
                }
                if (d_emptyContinent) {
                    l_validationResult.append(" Empty Continent(s) found.");
                }
                if (!d_connectedSubGraph) {
                    l_validationResult.append(" Subgraph not connected.");
                }
            }
        } else {
            if (d_connectedGraph) {
                l_validationResult.append(" The graph is connected. Countries are traversable.");
            }
            if (d_emptyContinent) {
                l_validationResult.append(" Empty Continent(s) found.");
            }
            if (!d_connectedSubGraph) {
                l_validationResult.append(" Subgraph not connected.");
            }
        }
        return l_validationResult.toString();
    }

    /**
     * method to gather information about all types of validation
     *
     * @return false if all the validation of the Map isn't successful. If
     *         everything in map is correct, then it will return true
     */
    public boolean checkAll() {
        if (d_gameMap.getCountries().isEmpty()) {
            d_emptyMap = true;
            return false;
        }

        Set<Integer> l_countryIds = d_gameMap.getCountries().keySet();
        this.d_connectedGraph = isConnected(d_gameMap.getCountries().values().iterator().next(), l_countryIds);

        for (Continent l_continent : d_gameMap.getContinents().values()) {
            l_countryIds = l_continent.getCountriesIds();
            d_currentContinentIteration = l_continent.getId();
            d_iteratingContinent = true;
            if (l_countryIds.isEmpty()) {
                d_emptyContinent = true;
                continue;
            }
            this.d_connectedSubGraph &= isConnected(l_continent.getCountriesSet().iterator().next(), l_countryIds);
            d_iteratingContinent = false;
        }
        return d_connectedGraph;
    }

    /**
     * method to validate whether all the countries are traversable or not starting
     * from any one country
     *
     * @param p_firstCountry it starts traversing through one country to check the
     *                       connection of graph
     * @param p_countryIds   it compares with this parameter if all the countries
     *                       are traversed
     * @return returns the status whether map is connected or not. Returns true if
     *         connected.
     */
    public boolean isConnected(Country p_firstCountry, Set<Integer> p_countryIds) {
        Set<Integer> l_countryIdsVisited = new HashSet<>();
        l_countryIdsVisited = countryIterator(p_firstCountry, l_countryIdsVisited);
        return l_countryIdsVisited.containsAll(p_countryIds);
    }

    /**
     * a recursive method that visits all the adjacent country and traverse in the
     * BFS manner to the adjacent countries.
     *
     * @param p_currentCountry    this is the current country that is being visited
     *                            for traversing
     * @param p_visitedCountryIds set of all the countries that are already visited
     * @return p_visitedCountryIds returns the set of visited countries
     */
    public Set<Integer> countryIterator(Country p_currentCountry, Set<Integer> p_visitedCountryIds) {
        if (p_visitedCountryIds.contains(p_currentCountry.getId())) {
            return p_visitedCountryIds;
        } else {
            p_visitedCountryIds.add(p_currentCountry.getId());
            for (Country l_nextCountry : p_currentCountry.getNeighborCountries()) {
                if (d_iteratingContinent) {
                    if (l_nextCountry.getContinent().getId() == d_currentContinentIteration) {
                        p_visitedCountryIds = countryIterator(l_nextCountry, p_visitedCountryIds);
                    }
                } else {
                    p_visitedCountryIds = countryIterator(l_nextCountry, p_visitedCountryIds);
                }
            }
        }
        return p_visitedCountryIds;
    }
}
