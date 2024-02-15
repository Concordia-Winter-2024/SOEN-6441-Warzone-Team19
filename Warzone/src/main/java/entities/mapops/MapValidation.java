import java.util.HashSet;
import java.util.Set;

import entities.Continent;
import entities.Country;
import entities.GameMap;

public class MapValidation {

    private boolean d_connectedGraph;
	private boolean d_connectedSubGraph = true;
	private GameMap d_gameMap;
	private boolean d_emptyMap = false;
	private boolean d_emptyContinent = false;
	private boolean d_flag = false;
	private boolean d_iteratingContinent = false;
	private int d_currentContinentIteration = 0;

    public MapValidation(GameMap p_gameMap) {
		this.d_gameMap = p_gameMap;
	}

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

    public String validate(){
        d_flag = true;
		StringBuilder l_validationResult = new StringBuilder();
		boolean l_result = checkAll();

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

    public boolean isConnected(Country p_firstCountry, Set<Integer> p_countryIds) {
		Set<Integer> l_countryIdsVisited = new HashSet<Integer>();
		l_countryIdsVisited = countryIterator(p_firstCountry, l_countryIdsVisited);
		return l_countryIdsVisited.containsAll(p_countryIds);
	}

    public boolean checkAll(){
        if (d_gameMap.getCountries().size() == 0) {
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

    public boolean getMapValidationStatus() {
		if (d_flag) {
			return d_connectedGraph && (!d_emptyContinent) && (!d_emptyMap) && d_connectedSubGraph;
		} else {
			System.out.println("Please validate the map before getting the status of map.");
			return false;
		}
	}

}