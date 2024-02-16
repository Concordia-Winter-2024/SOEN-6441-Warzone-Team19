package com.warzone.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.warzone.elements.map.MapReader;
import dnl.utils.text.table.TextTable;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.warzone.elements.map.MapWriter;
import com.warzone.elements.map.MapValidation;

/**
 * GameMap Class
 *
 */
public class GameMap {
    private HashMap<Integer, Continent> d_continents;
    private HashMap<Integer, Country> d_countries;
    private boolean d_isValid;

    /**
     * Constructor for GameMap
     */
    public GameMap() {
        d_continents = new HashMap<>();
        d_countries = new HashMap<>();
        d_isValid = false;
    }

    /**
     * This method is to ad continent to a map
     * @param p_addContinentId continent id
     * @param p_controlValue control value of continent
     * @return string message based on conditions
     */
    public String addContinent(int p_addContinentId, int p_controlValue) {
		if (d_continents.containsKey(p_addContinentId)) {
			return String.format("Continent \"%d\" already present in map", p_addContinentId);
		}
		d_continents.put(p_addContinentId, new Continent(p_continentId, p_controlValue));
		return String.format("Continent \"%d\" added to map", p_addContinentId);
	}

    /**
     * This method is for removing continent from a map
     * @param p_removeContinentId continent id
     * @return string message based on conditions
     */
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

    /**
     * This method is used to add country to a map
     * @param p_addCountryId country id
     * @param p_addContinentId continent id
     * @return string message based on conditions
     */
    public String addCountry(int p_addCountryId, int p_addContinentId) {
		if (d_countries.containsKey(p_addCountryId)) {
			return String.format("Country \"%d\" already present in map", p_addCountryId);
		}
		Country l_newCountry = new Country(p_addCountryId, d_continents.get(p_addContinentId));
		d_countries.put(p_addCountryId, l_newCountry);
		d_continents.get(p_addContinentId).addCountry(l_newCountry);
		return String.format("Country \"%d\" successfully added to map", p_addCountryId);
	}

    /**
     * This method is used to remove country from a map
     * @param p_removeCountryId country id
     * @return string message based on conditions
     */
    public String removeCountry(int p_removeCountryId) {
		if (!d_countries.containsKey(p_removeCountryId)) {
			return String.format("Country \"%d\" not present in map", p_removeCountryId);
		}
		for (Country l_country : d_countries.values()) {
			if (l_country.getNeighborIds().contains(p_removeCountryId)) {
				removeNeighbor(l_country.getId(), p_removeCountryId);
			}
		}
		d_countries.remove(p_removeCountryId);
		return String.format("Country \"%d\" successfully removed from map", p_removeCountryId);
	}

    /**
     * This method is used to add neighbour to a country
     * @param p_sourceCountryId counrty id
     * @param p_destCountryId neighbour country id
     * @return string message based on conditions
     */
    public String addNeighbor(int p_sourceCountryId, int p_destCountryId) {
		if (!d_countries.containsKey(p_sourceCountryId) && !d_countries.containsKey(p_destCountryId)) {
			return String.format("Ensure that both countries are present in map");
		}
		Country l_country1 = d_countries.get(p_sourceCountryId);
		Country l_country2 = d_countries.get(p_destCountryId);
		if (l_country1 == null || l_country2 == null) {
			return String.format("Country not present");
		}
		if (l_country1.getNeighborCountries().contains(l_country2)) {
			return String.format("Country \"%d\" already a neighbor of \"%d\"", p_destCountryId, p_sourceCountryId);
		}
		l_country1.addNeighbor(l_country2);
		return String.format("Country \"%d\" is now a neighbor of country \"%d\"", p_destCountryId, p_sourceCountryId);
	}

    /**
     * This method is used to remove neighbour to a country
     * @param p_countryId country id
     * @param p_neighborId neighbour country id
     * @return string message based on conditions
     */
    public String removeNeighbor(int p_countryId, int p_neighborId) {
		Country l_country = d_countries.get(p_countryId);
		Country l_neighbor = d_countries.get(p_neighborId);
		if (!l_country.getNeighborCountries().contains(l_neighbor)) {
			return String.format("Country \"%d\" is not a neighbor of \"%d\"", p_neighborId, p_countryId);
		}
		l_country.removeNeighbor(l_neighbor);
		return String.format("Country \"%d\" removed from neighbors of \"%d\"", p_neighborId, p_countryId);
	}

    /**
     * This method is used to load map from the map file.
     * @param p_mapFileName name of map file
     * @return string message based on conditions
     */
    public String loadMap(String p_mapFileName) {
		ReadMap l_mapRead = new ReadMap(this);
		Boolean l_loadCheck = l_mapRead.readFullMap(p_mapFileName);
		if (!l_loadCheck) {
			return String.format("Map \"%s\" cannot be loaded", p_mapFileName);
		}
		return String.format("Map \"%s\" loaded successfully", p_mapFileName);
	}

    /**
     * This method is used to show the map file in edit phase with proper formatting.
     * @return l_final_data Table containing data in string format
     */
    public String showMapEdit() {
        Object[][] l_data = new Object[d_countries.size()][3];
        System.out.println("=".repeat(50));
        System.out.println("Country , Continent; Control Value , Neighbors");
        System.out.println("=".repeat(50));
        Country l_country;
        //TextTable l_tt;
        final ByteArrayOutputStream l_baos = new ByteArrayOutputStream();
        String l_final_data;

        int l_count = 0;

        for (HashMap.Entry<Integer, Country> l_item : d_countries.entrySet()) {
            l_country = l_item.getValue();
            l_data[l_count] = fillCountryData(l_country, true);

            System.out.println(Arrays.toString(l_data[l_count]));
            l_count++;
        }

        l_final_data = new String(l_baos.toByteArray(), StandardCharsets.UTF_8);
        return l_final_data;
    }

    /**
     * This method is used to show the map file in gameplay phase with proper formatting.
     * It prints String of Countries, Continent, Owner, Armies present, Corresponding Neighbors
     * @return l_final_data Table containing data in string format
     */
    public String showMapPlay() {
        Object[][] l_data = new Object[d_countries.size()][5];
        System.out.println("=".repeat(70));
        System.out.println("Country, Continent; Control Value , Owner , Armies , Neighbors");
        System.out.println("=".repeat(70));
        Country l_country;
        TextTable l_tt;
        final ByteArrayOutputStream l_baos = new ByteArrayOutputStream();
        String l_final_data;
        int l_count = 0;

        for (HashMap.Entry<Integer, Country> l_item : d_countries.entrySet()) {
            l_country = l_item.getValue();
            l_data[l_count] = fillCountryData(l_country, false);
            System.out.println(Arrays.toString(l_data[l_count]));
            l_count++;
        }
        l_final_data = new String(l_baos.toByteArray(), StandardCharsets.UTF_8);
        return l_final_data;
    }

    /**
     * This method is used for filling the data of a specific country
     *
     * @param p_country country for which data is to be present
     * @param p_isEdit  true if showMap called in edit phase, false if called in
     *                  gameplay phase
     * @return Country data in array format
     */
    public String[] fillCountryData(Country p_country, boolean p_isEdit) {
        ArrayList<String> l_result = new ArrayList<String>();
        int l_id;
        String l_neighborsAsCsv = p_country.getNeighborCountries().stream().map(Country::getId)
                .collect(Collectors.toSet()).toString();

        l_id = p_country.getId();
        l_result.add(l_id + "       ");
        l_result.add(p_country.getContinent().getId() + "       ;       " + p_country.getContinent().getControlValue()+"    ");
        if (!p_isEdit) {
            l_result.add(p_country.getPlayer() != null ? p_country.getPlayer().getName() : "        ");
            l_result.add(p_country.getNumberOfArmiesPresent() + "       ");
        }
        l_result.add(l_neighborsAsCsv);
        return l_result.toArray(new String[0]);
    }

    /**
     * This method is used to save the map to a file
     *
     * @param p_fileName File name
     * @return True response if map written to file successfully
     */
    public String saveMap(String p_fileName) {
        MapWriter l_mapWriter = new MapWriter(this);
        if (!l_mapWriter.writeFullMap(p_fileName)) {
            return String.format("Map file \"%s\" cannot be saved", p_fileName);
        }
        return String.format("Map file \"%s\" saved successfully", p_fileName);
    }

    /**
     * This method is used to validate the map
     * @return string message based on conditions
     */
    public String validateMap(){
        MapValidation l_mapValidation = new MapValidation(this);
		String l_valCheck = l_mapValidation.validateStatus();
		d_isValid = l_mapValidation.getMapValidationStatus();
		return String.format(l_valCheck);
    }

    /**
     * This method is used to return all the continents
     * @return d_continents Set of continents
     */
    public HashMap<Integer, Continent> getContinents() {
		return d_continents;
	}

    /**
     * This method is used to return all the countries
     * @return d_countries Set of countries
     */
    public HashMap<Integer, Country> getCountries() {
		return d_countries;
	}

    /**
     * This method is used to get the status of validation of map
     * @return true or false based on validation
     */
    public boolean getValidateStatus() {
		return d_isValid;
	}
}
