import java.util.HashSet;
import java.util.Set;

import entities.Continent;
import entities.Country;
import entities.GameMap;

/**
 * MapValidation class to validate the map
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
     * MapValidation constructor
     * @param p_gameMap map to validate
     */
    public MapValidation(GameMap p_gameMap) {
		this.d_gameMap = p_gameMap;
	}

    /**
     * This method is used to valid the whole map
     * @return string status based on validation
     */
    public String validateStatus(){
        d_flag = true;
		StringBuilder l_validationResult = new StringBuilder();
		boolean l_result = validateAll();

		if (!l_result) {
			if (d_emptyMap) {
				l_validationResult.append("The Map does not contain any countries.");
			} else {
				if (!d_connectedGraph) {
					l_validationResult.append(" The graph is not connected. Countries are not traverseble.");
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
				l_validationResult.append(" The graph is connected. Countries are traverseble.");
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
     * This method provide instant validation status for a map
     * @return true or false
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
     * This method is used to check if the country is traversible or not.
     * @param p_firstCountry country
     * @param p_countryIds country id
     * @return true or false
     */
    public boolean isConnectedCountry(Country p_firstCountry, Set<Integer> p_countryIds) {
		Set<Integer> l_countryIdsVisited = new HashSet<Integer>();
		l_countryIdsVisited = iterateCountry(p_firstCountry, l_countryIdsVisited);
		return l_countryIdsVisited.containsAll(p_countryIds);
	}

    /**
     * This method is used to iterate and visit to all adjacent country
     * @param p_currentCountry current country
     * @param p_visitedCountryIds visited country id
     * @return returns the set of visited countries
     */
    public Set<Integer> iterateCountry(Country p_currentCountry, Set<Integer> p_visitedCountryIds) {
		if (p_visitedCountryIds.contains(p_currentCountry.getId())) {
			return p_visitedCountryIds;
		} else {
			p_visitedCountryIds.add(p_currentCountry.getId());
			for (Country l_nextCountry : p_currentCountry.getNeighborCountries()) {
				if (d_iteratingContinent) {
					if (l_nextCountry.getContinent().getId() == d_currentContinentIteration) {
						p_visitedCountryIds = iterateCountry(l_nextCountry, p_visitedCountryIds);
					}
				} else {
					p_visitedCountryIds = iterateCountry(l_nextCountry, p_visitedCountryIds);
				}
			}
		}
		return p_visitedCountryIds;
	}

    /**
     * This method is used to check validation of all types
     * @return true or false
     */
    public boolean validateAll(){
        if (d_gameMap.getCountries().size() == 0) {
			d_emptyMap = true;
			return false;
		}

		Set<Integer> l_countryIds = d_gameMap.getCountries().keySet();
		this.d_connectedGraph = isConnectedCountry(d_gameMap.getCountries().values().iterator().next(), l_countryIds);

		for (Continent l_continent : d_gameMap.getContinents().values()) {
			l_countryIds = l_continent.getCountriesIds();
			d_currentContinentIteration = l_continent.getId();
			d_iteratingContinent = true;
			if (l_countryIds.isEmpty()) {
				d_emptyContinent = true;
				continue;
			}
			this.d_connectedSubGraph &= isConnectedCountry(l_continent.getCountriesSet().iterator().next(), l_countryIds);
			d_iteratingContinent = false;
		}
		return d_connectedGraph;
    }


}