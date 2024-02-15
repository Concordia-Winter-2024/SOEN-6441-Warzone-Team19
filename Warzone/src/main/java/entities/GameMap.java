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