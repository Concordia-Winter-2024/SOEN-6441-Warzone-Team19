import java.util.Arrays;
import java.util.Set;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import entities.GameMap;

public class ReadMap{
    private GameMap d_gameMap;
	private HashMap<String, Integer> d_continentsMap;
	private HashMap<Integer, String> d_countriesMap;
	Scanner d_reader;

    public ReadMap(GameMap p_gameMap) {
		d_gameMap = p_gameMap;
		d_continentsMap = new HashMap<>();
		d_countriesMap = new HashMap<>();
	}

    public boolean readFullMap(String p_mapFilePath) {
		File l_mapFile = new File(
				Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps/" + p_mapFilePath).toString());
		String l_lineData, l_valueString;
		int l_countryCtn = 0, l_continentCtn = 0;

		try {
			d_reader = new Scanner(l_mapFile);
			while (d_reader.hasNextLine()) {
				l_valueString = d_reader.nextLine();

//				Read continents
				if (l_valueString.equals("[continents]")) {
					while (d_reader.hasNextLine()) {
						l_lineData = d_reader.nextLine();
						if (l_lineData.length() > 0) {
							String[] l_continents = l_lineData.split(" ");
							++l_continentCtn;
							d_continentsMap.put(l_continents[0], l_continentCtn);
							d_gameMap.addContinent(l_continentCtn, Integer.parseInt(l_continents[1]));
						} else {
							break;
						}
					}
				}

//				Read countries
				else if (l_valueString.equals("[countries]")) {
					while (d_reader.hasNextLine()) {
						l_lineData = d_reader.nextLine();
						if (l_lineData.length() > 0) {
							String[] l_countries = l_lineData.split(" ");
							System.out.println(Arrays.toString(l_countries));
							++l_countryCtn;
							d_countriesMap.put(l_countryCtn, l_countries[1]);
							d_gameMap.addCountry(Integer.parseInt(l_countries[0]), Integer.parseInt(l_countries[2]));
						} else {
							break;
						}
					}

				}

//				Read boundries
				else if (l_valueString.equals("[borders]")) {
					while (d_reader.hasNextLine()) {
						l_lineData = d_reader.nextLine();
						if (l_lineData.length() > 0) {
							String[] l_borders = l_lineData.split(" ");
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
			d_reader.close();
			return true;
		} catch (FileNotFoundException p_e) {
			return false;
		}

	}

    public Set<Integer> getContinentIds() {
		return d_gameMap.getContinents().keySet();
	}

    public Set<Integer> getCountriesIds() {
		return d_gameMap.getCountries().keySet();
	}

    public GameMap getGameMap() {
		return d_gameMap;
	}

}