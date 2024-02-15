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

}