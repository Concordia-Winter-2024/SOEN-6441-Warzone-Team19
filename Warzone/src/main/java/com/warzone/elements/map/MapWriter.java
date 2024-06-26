package com.warzone.elements.map;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import com.warzone.elements.*;

/**
 * Class to write into map file
 *
 */
public class MapWriter {
    private GameMap d_gameMap;
    private LinkedHashMap<Integer, Integer> d_continentsMap;
    private LinkedHashMap<Integer, Integer> d_countriesMap;
    private BufferedWriter d_writer;

    /**
     * Constructor
     *
     * @param p_gameMap GameMap object
     */
    public MapWriter(GameMap p_gameMap) {
        d_gameMap = p_gameMap;
        d_continentsMap = new LinkedHashMap<>();
        d_countriesMap = new LinkedHashMap<>();
    }

    /**
     * method that opens a file and writes the data to that file
     *
     * @param p_filePath to .map file
     * @return true if map saved successfully else false
     *
     */
    public boolean writeFullMap(String p_filePath) {
        int l_countryCtn = 0, l_continentCtn = 0;
        try {
            FileWriter l_fw = new FileWriter(
                    Paths.get(Paths.get("").toAbsolutePath() + "/maps/" + p_filePath).toString());
            d_writer = new BufferedWriter(l_fw);

//			 Writing Continents
            HashMap<Integer, Continent> l_continents;
            l_continents = d_gameMap.getContinents();
            d_writer.write("[continents]");
            d_writer.newLine();
            for (int p_continents : l_continents.keySet()) {
                d_continentsMap.put(p_continents, ++l_continentCtn);
                d_writer.write(p_continents + " " + l_continents.get(p_continents).getControlValue());
                d_writer.newLine();
            }

//			 Writing countries
            d_writer.write("\n");
            d_writer.write("[countries]");
            d_writer.newLine();
            for (int p_continents : d_continentsMap.keySet()) {
                Set<Integer> l_countriesId = l_continents.get(p_continents).getCountriesIds();
                for (int p_countriesId : l_countriesId) {
                    d_countriesMap.put(p_countriesId, ++l_countryCtn);
                    d_writer.write(l_countryCtn + " " + p_countriesId + " " + d_continentsMap.get(p_continents));
                    d_writer.newLine();
                }
            }

//			 Writing borders
            HashMap<Integer, Country> l_countries;
            l_countries = d_gameMap.getCountries();
            d_writer.write("\n");
            d_writer.write("[borders]");
            d_writer.newLine();
            for (int p_countries : d_countriesMap.keySet()) {
                Set<Integer> l_neighborIds = l_countries.get(p_countries).getNeighborIds();
                StringBuilder l_sb = new StringBuilder();
                l_sb.append(d_countriesMap.get(p_countries).toString()).append(" ");

                for (int p_neighborIds : l_neighborIds) {
                    l_sb.append(d_countriesMap.get(p_neighborIds).toString()).append(" ");
                }
                d_writer.write(l_sb.toString());
                d_writer.newLine();
            }
            d_writer.close();
            l_fw.close();
            return true;
        } catch (Exception p_e) {
            System.out.println("Exception " + p_e.getMessage());
            p_e.printStackTrace();
            return false;
        }
    }
}
