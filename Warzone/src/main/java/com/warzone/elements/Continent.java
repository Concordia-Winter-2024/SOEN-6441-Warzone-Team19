package com.warzone.elements;

import java.util.HashSet;
import java.util.Set;

/**
 * Continent class
 */
public class Continent{
    private int d_id;
	private int d_controlValue;
	private Set<Country> d_countriesSet;

    /**
     * Continent constructor
     * @param p_id continent id
     * @param p_controlValue control value
     */
    public Continent(int p_id, int p_controlValue) {
		d_id = p_id;
		d_controlValue = p_controlValue;
		d_countriesSet = new HashSet<Country>();
	}

    /**
     * This method is used to get control value of continent
     * @return control value
     */
    public int getControlValue() {
		return d_controlValue;
	}

    /**
	 * This method is used to get the set of countries in the continent
	 * @return set of countries
	 */
    public Set<Country> getCountriesSet() {
		return d_countriesSet;
	}

    /**
	 * This method is used to get id of the continent
	 * @return id of continent
	 */
    public int getId() {
		return d_id;
	}

    /**
	 * This method is used to get all countries id present
	 * @return set of country id
	 */
    public Set<Integer> getCountriesIds() {
		Set<Integer> l_countryNameSet = new HashSet<>();
		for (Country l_country : d_countriesSet) {
			l_countryNameSet.add(l_country.getId());
		}
		return l_countryNameSet;
	}

    /**
     * This method is used to add country to a continent
     * @param p_addCountry country
     */
    public void addCountry(Country p_addCountry) {
		d_countriesSet.add(p_addCountry);
	}

    /**
     * This method is used to remove country from a continent
     * @param p_addCountry country
     */
    public void removeCountry(Country p_addCountry) {
		d_countriesSet.remove(p_addCountry);
	}

}