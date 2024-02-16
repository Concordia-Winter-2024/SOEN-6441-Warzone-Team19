package entities.mapops;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import entities.*;

/**
 * Class to write into map file
 *
 */
public class WriteMap {
    private GameMap d_gameMap;
    private LinkedHashMap<Integer, Integer> d_continentsMap;
    private LinkedHashMap<Integer, Integer> d_countriesMap;
    private BufferedWriter d_bw;

    /**
     * Constructor
     * @param p_gameMap GameMap object
     */
    public WriteMap(GameMap p_gameMap) {
        d_gameMap = p_gameMap;
        d_continentsMap = new LinkedHashMap<>();
        d_countriesMap = new LinkedHashMap<>();
    }

    /**
     * method that opens a file and writes the data to that file
     * @param p_filePath to .map file
     * @return true if map saved successfully else false
     */
    public boolean writeFullMap(String p_filePath) {
        int l_countryCount = 0, l_continentCount = 0;
        try {
            FileWriter l_fw = new FileWriter(
                    Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps/" + p_filePath).toString());
            d_bw = new BufferedWriter(l_fw);

//			 Writing Continents
            HashMap<Integer, Continent> l_continents = new HashMap<>();
            l_continents = d_gameMap.getContinents();
            d_bw.write("[continents]");
            d_bw.newLine();
            for (int p_continents : l_continents.keySet()) {
                d_continentsMap.put(p_continents, ++l_continentCount);
                d_bw.write(p_continents + " " + l_continents.get(p_continents).getControlValue());
                d_bw.newLine();
            }

//			 Writing countries
            d_bw.write("\n");
            d_bw.write("[countries]");
            d_bw.newLine();
            for (int p_continents : d_continentsMap.keySet()) {
                Set<Integer> l_countriesId = l_continents.get(p_continents).getCountriesIds();
                for (int p_countriesId : l_countriesId) {
                    d_countriesMap.put(p_countriesId, ++l_countryCount);
                    d_bw.write(l_countryCount + " " + p_countriesId + " " + d_continentsMap.get(p_continents));
                    d_bw.newLine();
                }
            }

//			 Writing borders
            HashMap<Integer, Country> l_countries = new HashMap<>();
            l_countries = d_gameMap.getCountries();
            d_bw.write("\n");
            d_bw.write("[borders]");
            d_bw.newLine();
            for (int p_countries : d_countriesMap.keySet()) {
                Set<Integer> l_neighborIds = l_countries.get(p_countries).getNeighborIds();
                StringBuilder l_sb = new StringBuilder("");
                l_sb.append(d_countriesMap.get(p_countries).toString() + " ");

                for (int p_neighborIds : l_neighborIds) {
                    l_sb.append(d_countriesMap.get(p_neighborIds).toString() + " ");
                }
                d_bw.write(l_sb.toString());
                d_bw.newLine();
            }
            d_bw.close();
            l_fw.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
