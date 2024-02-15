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

    public String validateMap(){
        MapValidation l_mapValidation = new MapValidation(this);
		String l_valCheck = l_mapValidation.validate();
		d_isValid = l_mapValidation.getMapValidationStatus();
		return String.format(l_valCheck);
    }

    public boolean getValidateStatus() {
		return d_isValid;
	}

    public HashMap<Integer, Continent> getContinents() {
		return d_continents;
	}

    public HashMap<Integer, Country> getCountries() {
		return d_countries;
	}

}