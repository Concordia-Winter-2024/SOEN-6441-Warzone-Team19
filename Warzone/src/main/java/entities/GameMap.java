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

    public String addContinent(int p_continentId, int p_controlValue) {
		if (d_continents.containsKey(p_continentId)) {
			return String.format("Continent \"%d\" already present in map", p_continentId);
		}
		d_continents.put(p_continentId, new Continent(p_continentId, p_controlValue));
		return String.format("Continent \"%d\" added to map", p_continentId);
	}

    public String removeContinent(int p_continentId) {
		if (!d_continents.containsKey(p_continentId)) {
			return String.format("Continent \"%d\" not present in map", p_continentId);
		}
		if (d_continents.get(p_continentId).getCountriesSet().size() > 0) {
			for (Country l_country : d_continents.get(p_continentId).getCountriesSet()) {
				removeCountry(l_country.getId());
			}
		}
		d_continents.remove(p_continentId);
		return String.format("Continent \"%s\" successfully removed from map", p_continentId);
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