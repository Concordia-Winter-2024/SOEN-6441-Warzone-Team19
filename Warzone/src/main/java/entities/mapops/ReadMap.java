package entities.mapops;

import java.util.Set;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import entities.GameMap;

/**
 * Class to read map file
 *
 */
public class ReadMap {
    private GameMap d_gameMap;

    private HashMap<String, Integer> d_continentsMap;
    private HashMap<Integer, String> d_countriesMap;
    Scanner d_scanner;

    /**
     * ReadMap Constructor
     *
     * @param p_gameMap GameMap object
     */
    public ReadMap(GameMap p_gameMap) {
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
        String l_nextLine, l_dataString;
        int l_countryCount = 0, l_continentCount = 0;

        try {
            d_scanner = new Scanner(l_mapFile);
            while (d_scanner.hasNextLine()) {
                l_dataString = d_scanner.nextLine();

//				Read continents
                if (l_dataString.equals("[continents]")) {
                    while (d_scanner.hasNextLine()) {
                        l_nextLine = d_scanner.nextLine();
                        if (l_nextLine.length() > 0) {
                            String[] l_continents = l_nextLine.split(" ");
                            ++l_continentCount;
                            d_continentsMap.put(l_continents[0], l_continentCount);
                            d_gameMap.addContinent(l_continentCount, Integer.parseInt(l_continents[1]));
                        } else {
                            break;
                        }
                    }
                }

//				Read countries
                else if (l_dataString.equals("[countries]")) {
                    while (d_scanner.hasNextLine()) {
                        l_nextLine = d_scanner.nextLine();
                        if (l_nextLine.length() > 0) {
                            String[] l_countries = l_nextLine.split(" ");
                            ++l_countryCount;
                            d_countriesMap.put(l_countryCount, l_countries[1]);
                            d_gameMap.addCountry(Integer.parseInt(l_countries[0]), Integer.parseInt(l_countries[2]));
                        } else {
                            break;
                        }
                    }

                }

//				Read boundries
                else if (l_dataString.equals("[borders]")) {
                    while (d_scanner.hasNextLine()) {
                        l_nextLine = d_scanner.nextLine();
                        if (l_nextLine.length() > 0) {
                            String[] l_borders = l_nextLine.split(" ");
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
            d_scanner.close();
            return true;
        } catch (FileNotFoundException fnfe) {
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