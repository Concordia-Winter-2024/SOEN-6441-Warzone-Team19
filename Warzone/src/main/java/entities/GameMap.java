import entities.mapops.MapValidation;

public class GameMap{

    private HashMap<Integer, Continent> d_continents;
	private HashMap<Integer, Country> d_countries;
	private boolean d_isValid;

    public GameMap() {
		d_continents = new HashMap<>();
		d_countries = new HashMap<>();
		d_isValid = false;
	}

    public String addContinent(int p_addContinentId, int p_controlValue) {
		if (d_continents.containsKey(p_addContinentId)) {
			return String.format("Continent \"%d\" already present in map", p_addContinentId);
		}
		d_continents.put(p_addContinentId, new Continent(p_continentId, p_controlValue));
		return String.format("Continent \"%d\" added to map", p_addContinentId);
	}

    public String removeContinent(int p_removeContinentId) {
		if (!d_continents.containsKey(p_removeContinentId)) {
			return String.format("Continent \"%d\" not present in map", p_removeContinentId);
		}
		if (d_continents.get(p_removeContinentId).getCountriesSet().size() > 0) {
			for (Country l_country : d_continents.get(p_removeContinentId).getCountriesSet()) {
				removeCountry(l_country.getId());
			}
		}
		d_continents.remove(p_removeContinentId);
		return String.format("Continent \"%s\" successfully removed from map", p_removeContinentId);
	}

    public String addCountry(int p_addCountryId, int p_addContinentId) {
		if (d_countries.containsKey(p_addCountryId)) {
			return String.format("Country \"%d\" already present in map", p_addCountryId);
		}
		Country l_newCountry = new Country(p_addCountryId, d_continents.get(p_addContinentId));
		d_countries.put(p_addCountryId, l_newCountry);
		d_continents.get(p_addContinentId).addCountry(l_newCountry);
		return String.format("Country \"%d\" successfully added to map", p_addCountryId);
	}

    public String removeCountry(int p_removeCountryId) {
		if (!d_countries.containsKey(p_removeCountryId)) {
			return String.format("Country \"%d\" not present in map", p_removeCountryId);
		}
		for (Country l_country : d_countries.values()) {
			if (l_country.getNeighborIds().contains(p_removeCountryId)) {
				removeNeighbor(l_country.getId(), p_removeCountryId);
			}
		}
		d_countries.remove(p_removeCountryId);
		return String.format("Country \"%d\" successfully removed from map", p_removeCountryId);
	}

    public String addNeighbor(int p_sourceCountryId, int p_destCountryId) {
		if (!d_countries.containsKey(p_sourceCountryId) && !d_countries.containsKey(p_destCountryId)) {
			return String.format("Ensure that both countries are present in map");
		}
		Country l_country1 = d_countries.get(p_sourceCountryId);
		Country l_country2 = d_countries.get(p_destCountryId);
		if (l_country1 == null || l_country2 == null) {
			return String.format("Country not present");
		}
		if (l_country1.getNeighborCountries().contains(l_country2)) {
			return String.format("Country \"%d\" already a neighbor of \"%d\"", p_destCountryId, p_sourceCountryId);
		}
		l_country1.addNeighbor(l_country2);
		return String.format("Country \"%d\" is now a neighbor of country \"%d\"", p_destCountryId, p_sourceCountryId);
	}

    public String removeNeighbor(int p_countryId, int p_neighborId) {
		Country l_country = d_countries.get(p_countryId);
		Country l_neighbor = d_countries.get(p_neighborId);
		if (!l_country.getNeighborCountries().contains(l_neighbor)) {
			return String.format("Country \"%d\" is not a neighbor of \"%d\"", p_neighborId, p_countryId);
		}
		l_country.removeNeighbor(l_neighbor);
		return String.format("Country \"%d\" removed from neighbors of \"%d\"", p_neighborId, p_countryId);
	}

    public HashMap<Integer, Continent> getContinents() {
		return d_continents;
	}

    public HashMap<Integer, Country> getCountries() {
		return d_countries;
	}

    public boolean getValidateStatus() {
		return d_isValid;
	}

    public String validateMap(){
        MapValidation l_mapValidation = new MapValidation(this);
		String l_valCheck = l_mapValidation.validateStatus();
		d_isValid = l_mapValidation.getMapValidationStatus();
		return String.format(l_valCheck);
    }

    

    

}