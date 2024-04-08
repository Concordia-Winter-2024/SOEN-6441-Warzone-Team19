package com.warzone.elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.warzone.elements.map.MapReader;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.warzone.elements.map.MapWriter;
import com.warzone.elements.map.MapValidation;

import com.warzone.elements.map.*;

/**
 * GameMap Class
 *
 */
public class GameMap {
    private HashMap<Integer, Continent> d_continents;
    private HashMap<Integer, Country> d_countries;
    private boolean d_isValid;
    private boolean d_isConquestMap = false;

    /**
     * Constructor for GameMap
     */
    public GameMap() {
        d_continents = new HashMap<>();
        d_countries = new HashMap<>();
        d_isValid = false;
    }

    /**
     * This method is used to add a continent to the map
     *
     * @param p_continentId  Id of continent
     * @param p_controlValue Control value of continent
     * @return True response if continent added successfully
     */
    public String addContinent(int p_continentId, int p_controlValue) {
        if (d_continents.containsKey(p_continentId)) {
            return String.format("Continent \"%d\" already present in map", p_continentId);
        }
        d_continents.put(p_continentId, new Continent(p_continentId, p_controlValue));
        return String.format("Continent \"%d\" added to map", p_continentId);
    }

    /**
     * This method is used to remove a continent from the map
     *
     * @param p_continentId Continent id to be removed
     * @return True response if continent removed successfully
     */
    public String removeContinent(int p_continentId) {
        if (!d_continents.containsKey(p_continentId)) {
            return String.format("Continent \"%d\" not present in map", p_continentId);
        }
        if (!d_continents.get(p_continentId).getCountriesSet().isEmpty()) {
            for (Country l_country : d_continents.get(p_continentId).getCountriesSet()) {
                removeCountry(l_country.getId());
            }
        }
        d_continents.remove(p_continentId);
        return String.format("Continent \"%s\" successfully removed from map", p_continentId);
    }

    /**
     * This method is used to add a country to the map
     *
     * @param p_countryId   Id of country
     * @param p_continentId Id of continent where country present
     * @return True response if country added successfully
     */
    public String addCountry(int p_countryId, int p_continentId) {
        if (d_countries.containsKey(p_countryId)) {
            return String.format("Country \"%d\" already present in map", p_countryId);
        }
        Country l_newCountry = new Country(p_countryId, d_continents.get(p_continentId));
        d_countries.put(p_countryId, l_newCountry);
        d_continents.get(p_continentId).addCountry(l_newCountry);
        return String.format("Country \"%d\" successfully added to map", p_countryId);
    }

    /**
     * This method is used to remove country from the map
     *
     * @param p_countryId id of country
     * @return True response if country removed successfully
     */
    public String removeCountry(int p_countryId) {
        if (!d_countries.containsKey(p_countryId)) {
            return String.format("Country \"%d\" not present in map", p_countryId);
        }
        for (Country l_country : d_countries.values()) {
            if (l_country.getNeighborIds().contains(p_countryId)) {
                removeNeighbor(l_country.getId(), p_countryId);
            }
        }
        d_countries.remove(p_countryId);
        return String.format("Country \"%d\" successfully removed from map", p_countryId);
    }

    /**
     * This method is used to add a neighbor to a respective country
     *
     * @param p_sourceCountryId Country id
     * @param p_destCountryId   Neighbor country id
     * @return True response if neighbor added successfully
     */
    public String addNeighbor(int p_sourceCountryId, int p_destCountryId) {
        if (!d_countries.containsKey(p_sourceCountryId) && !d_countries.containsKey(p_destCountryId)) {
            return "Ensure that both countries are present in map";
        }
        Country l_country1 = d_countries.get(p_sourceCountryId);
        Country l_country2 = d_countries.get(p_destCountryId);
        if (l_country1 == null || l_country2 == null) {
            return "Country not present";
        }
        if (l_country1.getNeighborCountries().contains(l_country2)) {
            return String.format("Country \"%d\" already a neighbor of \"%d\"", p_destCountryId, p_sourceCountryId);
        }
        l_country1.addNeighbor(l_country2);
        return String.format("Country \"%d\" is now a neighbor of country \"%d\"", p_destCountryId, p_sourceCountryId);
    }

    /**
     * This method is used to remove a neighbor from a respective country
     *
     * @param p_countryId  Country id
     * @param p_neighborId Neighbor country id
     * @return True response if neighbor removed successfully
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
     * This method is used to load a map from a file
     *
     * @param p_fileName Name of a .map file
     * @return False response if map file is failed to load successfully, returns True otherwise.
     */
    public String loadMap(String p_fileName) {
        MapReader l_mapRead;
        Boolean l_loadCheck;
        if (isConquestMap(p_fileName)) {
            l_mapRead = new MapReaderAdapter(new ConquestMapReader(this), this);
            l_loadCheck = l_mapRead.readFullMap(p_fileName);
            if (!l_loadCheck) {
                return String.format("Map \"%s\" cannot be loaded", p_fileName);
            } else {
                return String.format("Conquest Map \"%s\" loaded successfully", p_fileName);
            }
        } else {
            l_mapRead = new MapReader(this);
            l_loadCheck = l_mapRead.readFullMap(p_fileName);
            if (!l_loadCheck) {
                return String.format("Map \"%s\" cannot be loaded", p_fileName);
            } else {
                return String.format("Domination Map \"%s\" loaded successfully", p_fileName);
            }
        }
    }

    /**
     * The method shows the map file in edit phase with proper formatting.
     *
     * @return l_final_data represents table containing data in string format
     */
    public String showMapEdit() {
        Object[][] l_data = new Object[d_countries.size()][3];
        System.out.println("=".repeat(50));
        System.out.println("Country , Continent; Control Value , Neighbors");
        System.out.println("=".repeat(50));
        Country l_country;
        final ByteArrayOutputStream l_baos = new ByteArrayOutputStream();
        String l_final_data;

        int l_count = 0;

        for (HashMap.Entry<Integer, Country> l_item : d_countries.entrySet()) {
            l_country = l_item.getValue();
            l_data[l_count] = fillCountryData(l_country, true);

            System.out.println(Arrays.toString(l_data[l_count]));
            l_count++;
        }

        l_final_data = l_baos.toString(StandardCharsets.UTF_8);
        return l_final_data;
    }

    /**
     * The method shows the map file in gameplay phase with proper formatting.
     * It prints String of Countries, Continent, Owner, Armies present, Corresponding Neighbors
     *
     * @return l_final_data Table containing data in string format
     */
    public String showMapPlay() {
        Object[][] l_data = new Object[d_countries.size()][5];
        System.out.println("=".repeat(70));
        System.out.println("Country, Continent; Control Value , Owner , Armies , Neighbors");
        System.out.println("=".repeat(70));
        Country l_country;
        final ByteArrayOutputStream l_baos = new ByteArrayOutputStream();
        String l_final_data;
        int l_count = 0;

        for (HashMap.Entry<Integer, Country> l_item : d_countries.entrySet()) {
            l_country = l_item.getValue();
            l_data[l_count] = fillCountryData(l_country, false);
            System.out.println(Arrays.toString(l_data[l_count]));
            l_count++;
        }

        l_final_data = l_baos.toString(StandardCharsets.UTF_8);
        return l_final_data;
    }

    /**
     * The method fills the data with a specific country
     *
     * @param p_country country for which data is to be present
     * @param p_isEdit  false if showMap called in gameplay phase, true if called in
     *                  edit phase
     * @return Country returns data in array format
     */
    public String[] fillCountryData(Country p_country, boolean p_isEdit) {
        ArrayList<String> l_result = new ArrayList<>();
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
     * The method saves the map to a file
     *
     * @param p_fileName File name
     * @return True response if map written to file successfully
     */
    public String saveMap(String p_fileName) {
        MapWriter l_writeMap;
        boolean l_result;
        boolean l_isConquest = p_fileName.indexOf("conquest") != -1 ? true : false;

        if (l_isConquest) {
            l_writeMap = new MapWriterAdapter(new ConquestMapWriter(this), this);
            l_result = l_writeMap.writeFullMap(p_fileName);
            if (!l_result) {
                return String.format("Map file \"%s\" cannot be saved", p_fileName);
            }
            return String.format("Conquest Map file \"%s\" saved successfully", p_fileName);
        } else {
            l_writeMap = new MapWriter(this);
            l_result = l_writeMap.writeFullMap(p_fileName);
            if (!l_result) {
                return String.format("Map file \"%s\" cannot be saved", p_fileName);
            }
            return String.format("Domination Map file \"%s\" saved successfully", p_fileName);
        }

    }

    /**
     * Map validation method
     *
     * @return the result of map validation
     */
    public String validateMap() {
        MapValidation l_mapValidation = new MapValidation(this);
        String l_valCheck = l_mapValidation.validate();
        d_isValid = l_mapValidation.getMapValidationStatus();
        return String.format(l_valCheck);
    }

    /**
     * Status of map validation
     *
     * @return d_isValid boolean whether the map is valid or not.
     */
    public boolean getValidateStatus() {
        return d_isValid;
    }

    /**
     * The method gets all the continents to load
     *
     * @return d_continents Set of continents
     */
    public HashMap<Integer, Continent> getContinents() {
        return d_continents;
    }

    /**
     * The method gets all the countries to load
     *
     * @return d_countries Set of countries
     */
    public HashMap<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * method that check map is of type Conquest or Domination
     *
     * @param p_filePath the file passed to the function
     * @return true if it is conquest map or false if it is domination map.
     */
    public Boolean isConquestMap(String p_filePath) {
        File l_mapFile = new File(
                Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps/" + p_filePath).toString());
        Scanner l_reader = null;
        String l_dataString;
        try {
            l_reader = new Scanner(l_mapFile);
            while (l_reader.hasNextLine()) {
                l_dataString = l_reader.nextLine();
                if ("[Territories]".equals(l_dataString)) {
                    d_isConquestMap = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        if (d_isConquestMap) {
            return true;
        } else {
            return false;
        }
    }
}
