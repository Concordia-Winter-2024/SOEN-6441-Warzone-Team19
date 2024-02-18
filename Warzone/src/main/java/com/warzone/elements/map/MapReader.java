package com.warzone.elements.map;

import java.util.Set;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import com.warzone.elements.GameMap;

/**
 * Class to read map file
 *
 */
public class MapReader {
    private GameMap d_gameMap;

    private final HashMap<String, Integer> d_continentsMap;
    private HashMap<Integer, String> d_countriesMap;
    Scanner d_reader;

    /**
     * MapReader Constructor
     *
     * @param p_gameMap GameMap object
     */
    public MapReader(GameMap p_gameMap) {
        d_gameMap = p_gameMap;
        d_continentsMap = new HashMap<>();
        d_countriesMap = new HashMap<>();
    }

    /**
     * method to return the gameMap object of the map file.
     *
     * @return gameMap object.
     */
    public GameMap getGameMap() {
        return d_gameMap;
    }

    /**
     * method to read a file and feed data to GameMap object
     *
     * @param p_filePath path to .map file
     * @return true if map loaded successfully else false
     */
    public boolean readFullMap(String p_filePath) {
        File l_mapFile = new File(
                Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps/" + p_filePath).toString());
        String l_line, l_dataString;
        int l_countryCtn = 0, l_continentCtn = 0;

        try {
            d_reader = new Scanner(l_mapFile);
            while (d_reader.hasNextLine()) {
                l_dataString = d_reader.nextLine();

//				Read continents
                switch (l_dataString) {
                    case "[continents]" -> {
                        while (d_reader.hasNextLine()) {
                            l_line = d_reader.nextLine();
                            if (!l_line.isEmpty()) {
                                String[] l_continents = l_line.split(" ");
                                ++l_continentCtn;
                                d_continentsMap.put(l_continents[0], l_continentCtn);
                                d_gameMap.addContinent(l_continentCtn, Integer.parseInt(l_continents[1]));
                            } else {
                                break;
                            }
                        }
                    }

//				Read countries
                    case "[countries]" -> {
                        while (d_reader.hasNextLine()) {
                            l_line = d_reader.nextLine();
                            if (!l_line.isEmpty()) {
                                String[] l_countries = l_line.split(" ");
                                ++l_countryCtn;
                                d_countriesMap.put(l_countryCtn, l_countries[1]);
                                d_gameMap.addCountry(Integer.parseInt(l_countries[0]), Integer.parseInt(l_countries[2]));
                            } else {
                                break;
                            }
                        }
                    }

//				Read boundries
                    case "[borders]" -> {
                        while (d_reader.hasNextLine()) {
                            l_line = d_reader.nextLine();
                            if (!l_line.isEmpty()) {
                                String[] l_borders = l_line.split(" ");
                                int l_countryId = Integer.parseInt(l_borders[0]);
                                int l_neighborId;
                                for (int i = 1; i < l_borders.length; i++) {
                                    l_neighborId = Integer.parseInt(l_borders[i]);
                                    d_gameMap.addNeighbor(l_countryId, l_neighborId);
                                }
                            } else {
                                break;
                            }

                        }
                    }
                }
            }
            d_reader.close();
            return true;
        } catch (FileNotFoundException p_e) {
            return false;
        }

    }

    /**
     * method to get continents ids
     *
     * @return Set of continents ids read
     */
    public Set<Integer> getContinentIds() {
        return d_gameMap.getContinents().keySet();
    }

    /**
     * method to get countries ids
     *
     * @return Set of countries ids read
     */
    public Set<Integer> getCountriesIds() {
        return d_gameMap.getCountries().keySet();
    }
}