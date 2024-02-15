public class Country{

    private int d_id;
	private Set<Country> d_neighborCountries;
	private int d_armiesPresent;
	private Continent d_continent;
	private Player d_owner;

    public Country(int p_id, Continent p_continent) {
		d_id = p_id;
		d_continent = p_continent;
		d_neighborCountries = new HashSet<Country>();
		d_armiesPresent = 0;
	}

    public Set<Country> getNeighborCountries() {
		return d_neighborCountries;
	}

    public void addNeighbor(Country p_addCountry) {
		d_neighborCountries.add(p_addCountry);
	}

    public boolean removeNeighbor(Country p_removeCountry) {
		if (!d_neighborCountries.contains(p_removeCountry)) {
			return false;
		}
		d_neighborCountries.remove(p_removeCountry);
		return true;
	}

    public Continent getContinent() {
		return d_continent;
	}

}