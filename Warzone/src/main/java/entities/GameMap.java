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