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

}