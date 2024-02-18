package com.warzone.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.warzone.elements.Continent;
import com.warzone.elements.Country;
import com.warzone.elements.GameMap;
import com.warzone.elements.Player;
import com.warzone.elements.orders.Orders;
import com.warzone.elements.orders.ShowMap;

/**
 *
 * Main file to start game
 *
 */
public class GameEngine {

    GameMap d_gameMap = new GameMap();
    private boolean d_isLoadedMap = false;
    boolean d_isEditMap = false;
    private boolean d_isGamePhase = false;
    private HashMap<String, Player> d_players = new HashMap<>();
    private ArrayList<String> d_playerName = new ArrayList<>();
    int d_currentPlayer = 0;

    /**
     * This method is to edit map. If the specified map file does not exist, it will create a new map file.
     * If the map file exists, it will load the map file and set the edit phase to true.
     *
     * @param p_fileName Name of file
     * @return String which states completion of the operation
     */
    public String editMap(String p_fileName) {
        String l_result;
        if (!d_isEditMap && !d_isLoadedMap) {
            this.loadMap(p_fileName, true);
            if (!Files.exists(Paths.get(Paths.get("").toAbsolutePath() + "/maps/" + p_fileName))) {
                try {
                    Files.createDirectories(Paths.get(Paths.get("").toAbsolutePath() + "/maps"));
                    Files.createFile(Paths.get(Paths.get("").toAbsolutePath() + "/maps/" + p_fileName));
                } catch (IOException p_e) {
                    p_e.printStackTrace();
                }
            }
            l_result = String.format("Map \"%s\" ready for edit", p_fileName);
            d_isEditMap = true;
            d_isLoadedMap = false;
        } else {
            l_result = "Edit map not available";
        }
        return l_result;
    }

    /**
     * This method is used to load a map.
     *
     * @param p_fileName Name of the file
     * @return loaded map(responses true or false)
     */
    public String loadMap(String p_fileName) {
        return loadMap(p_fileName, false);
    }

    /**
     * Load map method to load a map
     *
     * @param p_fileName Name of file
     * @param p_isEdit   true if loadmap is called in edit phase
     * @return loaded map(responses true or false)
     */
    public String loadMap(String p_fileName, boolean p_isEdit) {
        String l_result;
        if (!d_isEditMap) {
            l_result = d_gameMap.loadMap(p_fileName);
            if (l_result.equals(String.format("Map \"%s\" cannot be loaded", p_fileName))) {
                return l_result;
            }
            if (!p_isEdit) {
                String l_validMsg = d_gameMap.validateMap();
                boolean l_validateResult = d_gameMap.getValidateStatus();
                if (!l_validateResult) {
                    l_result = l_validMsg;
                    d_gameMap = new GameMap();
                    return l_result;
                }
            }
            d_isLoadedMap = true;
        } else {
            l_result = "Cannot be loaded map when map is edited";
        }
        return l_result;
    }

    /**
     * This method is used to edit the continents
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether continents are added or removed
     */
    public String editContinent(String[] p_commandSplitted) {
        String l_result;
        if (d_isEditMap && !d_isLoadedMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = d_gameMap.addContinent(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            } else {
                l_result = d_gameMap.removeContinent(Integer.parseInt(p_commandSplitted[1]));
            }
        } else {
            l_result = "Map can only be edited when file is open in edit phase";
        }
        return l_result;
    }

    /**
     * This method is used to edit the countries
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether countries are added or removed from the map
     */
    public String editCountry(String[] p_commandSplitted) {
        String l_result;
        if (d_isEditMap && !d_isLoadedMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = d_gameMap.addCountry(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            } else {
                l_result = d_gameMap.removeCountry(Integer.parseInt(p_commandSplitted[1]));
            }
        } else {
            l_result = "Map can only be edited when file is open in edit phase";
        }
        return l_result;
    }

    /**
     * This method is used to edit the neighbors of the countries
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether neighbors are added or removed from the map
     */
    public String editNeighbor(String[] p_commandSplitted) {
        String l_result;
        if (d_isEditMap && !d_isLoadedMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = d_gameMap.addNeighbor(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            } else {
                l_result = d_gameMap.removeNeighbor(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            }
        } else {
            l_result = "Map can only be edited when file is open in edit phase";
        }
        return l_result;
    }

    /**
     * This method is used to save the map with the specified file name
     *
     * @param p_fileName filename of map to be saved
     * @return l_result shows whether map is saved or not
     */
    public String saveMap(String p_fileName) {
        String l_result;
        if (d_isEditMap && !d_isLoadedMap) {
            String l_validMsg = d_gameMap.validateMap();
            boolean l_validateResult = d_gameMap.getValidateStatus();
            if (!l_validateResult) {
                l_result = l_validMsg;
                return l_result;
            }

            l_result = d_gameMap.saveMap(p_fileName);
            d_isLoadedMap = false;
            d_isEditMap = false;
        } else {
            l_result = "Cannot save map";
        }
        return l_result;
    }

    /**
     * This method is used to show the map, it will show the map in edit phase and play phase
     *
     * @return map in string format
     */
    public String showMap() {
        if (!d_isGamePhase) {
            return d_gameMap.showMapEdit();
        }
        return d_gameMap.showMapPlay();
    }

    /**
     * This method is to add or remove players from the .
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether players are added or removed
     */
    public String gamePlayer(String[] p_commandSplitted) {
        String l_result;
        if (!d_isEditMap && d_isLoadedMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = addPlayer(p_commandSplitted[1]);
            } else {
                l_result = removePlayer(p_commandSplitted[1]);
            }
        } else {
            l_result = "Players cannot be added/removed in this phase";
        }
        return l_result;
    }

    /**
     * This method is used to add a player to the game
     *
     * @param p_playerName name of the player
     * @return Positive true if player is added successfully
     */
    public String addPlayer(String p_playerName) {
        if (d_players.containsKey(p_playerName)) {
            return String.format("Player \"%s\" already present in game", p_playerName);
        }
        d_players.put(p_playerName, new Player(p_playerName));
        d_playerName.add(p_playerName);
        return String.format("Player \"%s\" added to game", p_playerName);
    }

    /**
     * This method is used to remove a player from the game
     *
     * @param p_playerName name of the player
     * @return Positive true if player is removed successfully
     */
    public String removePlayer(String p_playerName) {
        if (!d_players.containsKey(p_playerName)) {
            return String.format("Player \"%s\" not present in game", p_playerName);
        }
        d_players.remove(p_playerName);
        d_playerName.remove(p_playerName);
        return String.format("Player \"%s\" removed from game", p_playerName);
    }

    /**
     * Get the map object
     *
     * @return d_gameMap Object of GameMap class
     */
    public GameMap getGameMap() {
        return d_gameMap;
    }

    /**
     * Validate map method to validate a map
     *
     * @return l_result Result of map validation
     */
    public String validateMap() {
        String l_result;
        if (this.d_gameMap != null) {
            l_result = d_gameMap.validateMap();
        } else {
            l_result = "Cannot validate map";
        }
        return l_result;
    }

    /**
     * This method to loop over players and in each subsequent loop player deploys
     * armies to one's owned countries
     */
    public void deployPhase() {
        int l_currentPlayer = 0;
        Orders l_playerOrders = null;
        HashSet<String> l_playersCompleted = new HashSet<>();
        System.out.println("\nDeploy phase entered");
        String dash = "-";
        System.out.println(dash.repeat(20));
        while (l_playersCompleted.size() < d_playerName.size()) {
            if (d_players.get(d_playerName.get(l_currentPlayer)).getNumberOfArmies() > 0) {
                System.out.println("Player " + d_playerName.get(l_currentPlayer) + "'s turn");
                System.out.println("Number of armies left: "
                        + d_players.get(d_playerName.get(l_currentPlayer)).getNumberOfArmies());
                d_players.get(d_playerName.get(l_currentPlayer)).issue_order();
                l_playerOrders = d_players.get(d_playerName.get(l_currentPlayer)).next_order();
                System.out.println(l_playerOrders.execute(this));
            } else {
                l_playersCompleted.add(d_playerName.get(l_currentPlayer));
            }

            if (!(l_playerOrders instanceof ShowMap)) {
                ++l_currentPlayer;
            }
            if (l_currentPlayer == d_playerName.size()) {
                l_currentPlayer = 0;
            }
        }
    }

    /**
     * This method to get the hash map of player names corresponding to their objects
     *
     * @return d_players HashMap of players
     */
    public HashMap<String, Player> getPlayers() {
        return d_players;
    }

    /**
     * This method is used assign countries to the players
     *
     * @return returns the message to the caller
     */
    public String assignCountries() {
        if (d_players.size() < 2) {
            return "There must be at least two player";
        }
        HashMap<Integer, Country> l_countries = d_gameMap.getCountries();
        List<Country> l_countryObjects = new ArrayList<>(l_countries.values());
        Random l_random = new Random();
        do {
            for (Player p_player : d_players.values()) {
                if (l_countryObjects.isEmpty()) {
                    break;
                }
                int l_idOfCountry = l_random.nextInt(l_countryObjects.size());
                p_player.addCountry(l_countryObjects.get(l_idOfCountry));
                l_countryObjects.get(l_idOfCountry).setPlayer(p_player);
                l_countryObjects.remove(l_countryObjects.get(l_idOfCountry));
            }
        } while (!l_countryObjects.isEmpty());

        System.out.print("Countries Assigned\n");
        checkContinentOwnership();
        assignArmies();
        d_isGamePhase = true;
        deployPhase();
        return "Deployment done";
    }

    /**
     * This method is used to check the ownership of the continents, if a player owns
     * a continent, player will get the control value/armies of that continent
     */
    public void checkContinentOwnership() {
        for (Player l_player : d_players.values()) {
            for (Continent l_continent : d_gameMap.getContinents().values()) {
                if (l_player.checkContinent(l_continent)) {
                    l_player.addContinent(l_continent);
                }
            }
        }
    }

    /**
     * This method is used to assign armies to the players
     */
    public void assignArmies() {
        for (Player l_player : d_players.values()) {
            l_player.setNumberOfArmies();
        }
    }
}
