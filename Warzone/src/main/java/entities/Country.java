import java.util.HashSet;
import java.util.Set;

/**
 * class for country
 */
public class Country{

    private int d_id;
	private Set<Country> d_neighborCountries;
	private int d_armiesPresent;
	private Continent d_continent;
	private Player d_owner;

    /**
     * country constructor
     * @param p_id country id
     * @param p_continent parent continent
     */
    public Country(int p_id, Continent p_continent) {
		d_id = p_id;
		d_continent = p_continent;
		d_neighborCountries = new HashSet<Country>();
		d_armiesPresent = 0;
	}

    /**
     * This method is used to get neighbouring countries
     * @return set of neighbouring countries
     */
    public Set<Country> getNeighborCountries() {
		return d_neighborCountries;
	}

    /**
     * This method is used to add neigbour of a country
     * @param p_addCountry country to be added
     */
    public void addNeighbor(Country p_addCountry) {
		d_neighborCountries.add(p_addCountry);
	}

    /**
     * This method is used to remove neighbour country
     * @param p_removeCountry country to be removed
     * @return true or false
     */
    public boolean removeNeighbor(Country p_removeCountry) {
		if (!d_neighborCountries.contains(p_removeCountry)) {
			return false;
		}
		d_neighborCountries.remove(p_removeCountry);
		return true;
	}

    /**
     * This method is used to get the continent for which country belongs.
     * @return continent
     */
    public Continent getContinent() {
		return d_continent;
	}

}