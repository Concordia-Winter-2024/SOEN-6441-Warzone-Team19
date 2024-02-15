public class Continent{
    private int d_id;
	private int d_controlValue;
	private Set<Country> d_countriesSet;

    public Continent(int p_id, int p_controlValue) {
		d_id = p_id;
		d_controlValue = p_controlValue;
		d_countriesSet = new HashSet<Country>();
	}

    public int getControlValue() {
		return d_controlValue;
	}

    public Set<Country> getCountriesSet() {
		return d_countriesSet;
	}

    public int getId() {
		return d_id;
	}

    public Set<Integer> getCountriesIds() {
		Set<Integer> l_countryNameSet = new HashSet<>();
		for (Country l_country : d_countriesSet) {
			l_countryNameSet.add(l_country.getId());
		}
		return l_countryNameSet;
	}

    public void addCountry(Country p_country) {
		d_countriesSet.add(p_country);
	}

    public void removeCountry(Country p_country) {
		d_countriesSet.remove(p_country);
	}

}