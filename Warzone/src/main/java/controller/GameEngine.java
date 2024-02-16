package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import entities.Continent;
import entities.Country;
import entities.GameMap;
import entities.Player;
import entities.orders.Orders;
import entities.orders.ShowMap;

/**
 *
 * Main file to start game
 *
 */
public class GameEngine {

    GameMap d_gameMap = new GameMap();
    private boolean d_isLoadMap = false;
    private boolean d_isEditMap = false;
    private boolean d_isGamePhase = false;
    private HashMap<String, Player> d_players = new HashMap<>();
    private ArrayList<String> d_playerName = new ArrayList<>();
    int d_currentPlayer = 0;

    /**
     * method to edit map, it creates new file when specified file name does not
     * exists else loads existing map file.
     *
     * @param p_fileName Name of file
     * @return String which states completion of the operation
     */
    public String editMap(String p_fileName) {
        String l_result;
        if (!d_isEditMap && !d_isLoadMap) {
            this.loadMap(p_fileName, true);
            if (!Files.exists(Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps/" + p_fileName))) {
                try {
                    Files.createDirectories(Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps"));
                    Files.createFile(Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps/" + p_fileName));
                } catch (IOException p_e) {
                    p_e.printStackTrace();
                }
            }
            l_result = String.format("Map \"%s\" ready for edit", p_fileName);
            d_isEditMap = true;
            d_isLoadMap = false;
        } else {
            l_result = String.format("Edit map not available");
        }
        return l_result;
    }

    /**
     * used to load map in game phase
     *
     * @param p_fileName Name of the file
     * @return loaded map(responses positive or negative)
     */
    public String loadMap(String p_fileName) {
        return loadMap(p_fileName, false);
    }

    /**
     * method to load a map
     *
     * @param p_fileName Name of file
     * @param p_isEdit   true if loadmap is called in edit phase
     * @return loaded map(responses positive or negative)
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
                Boolean l_validateResult = d_gameMap.getValidateStatus();
                if (!l_validateResult) {
                    l_result = l_validMsg;
                    d_gameMap = new GameMap();
                    return l_result;
                }
            }
            d_isLoadMap = true;
        } else {
            l_result = String.format("Cannot be loaded map when map is edited");
        }
        return l_result;
    }

    /**
     * method to edit continents
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether continents are added or removed
     */
    public String editContinent(String[] p_commandSplitted) {
        String l_result;
        if (d_isEditMap && !d_isLoadMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = d_gameMap.addContinent(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            } else {
                l_result = d_gameMap.removeContinent(Integer.parseInt(p_commandSplitted[1]));
            }
        } else {
            l_result = String.format("Map can only be edited when file is open in edit phase");
        }
        return l_result;
    }

    /**
     * method to edit countries
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether countries are added or removed with respect to
     *         the continents they are a part of
     */
    public String editCountry(String[] p_commandSplitted) {
        String l_result;
        if (d_isEditMap && !d_isLoadMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = d_gameMap.addCountry(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            } else {
                l_result = d_gameMap.removeCountry(Integer.parseInt(p_commandSplitted[1]));
            }
        } else {
            l_result = String.format("Map can only be edited when file is open in edit phase");
        }
        return l_result;
    }

    /**
     * method to edit neighbors of a specific country
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether neighbors countries to a given country are
     *         added or removed
     */
    public String editNeighbor(String[] p_commandSplitted) {
        String l_result;
        if (d_isEditMap && !d_isLoadMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = d_gameMap.addNeighbor(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            } else {
                l_result = d_gameMap.removeNeighbor(Integer.parseInt(p_commandSplitted[1]),
                        Integer.parseInt(p_commandSplitted[2]));
            }
        } else {
            l_result = String.format("Map can only be edited when file is open in edit phase");
        }
        return l_result;
    }

    /**
     * method to save a map, it creates new file with specified file name.
     *
     * @param p_fileName filename of map to be saved
     * @return l_result shows whether map is saved or not
     */
    public String saveMap(String p_fileName) {
        String l_result;
        if (d_isEditMap && !d_isLoadMap) {
            String l_validMessage = d_gameMap.validateMap();
            Boolean l_validationResult = d_gameMap.getValidateStatus();
            if (!l_validationResult) {
                l_result = l_validMessage;
                return l_result;
            }

            l_result = d_gameMap.saveMap(p_fileName);
            d_isLoadMap = false;
            d_isEditMap = false;
        } else {
            l_result = String.format("Cannot save map");
        }
        return l_result;
    }

    /**
     * method to add players to the game
     *
     * @param p_commandSplitted splitted commands
     * @return l_result shows whether players are added or removed
     */
    public String gamePlayer(String[] p_commandSplitted) {
        String l_result;
        if (!d_isEditMap && d_isLoadMap) {
            if (p_commandSplitted[0].equals("-add")) {
                l_result = addPlayer(p_commandSplitted[1]);
            } else {
                l_result = removePlayer(p_commandSplitted[1]);
            }
        } else {
            l_result = String.format("Players cannot be added/removed in this phase");
        }
        return l_result;
    }

    /**
     * method to add a player to the game
     *
     * @param p_playerName name of the player
     * @return Positive response if player is added successfully
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
     * method to remove a player from the game
     *
     * @param p_playerName name of the player
     * @return Positive response if player is removed successfully
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
     * method to get a map
     *
     * @return d_gameMap Object of GameMap class
     */
    public GameMap getGameMap() {
        return d_gameMap;
    }

    /**
     * method to validate a map
     *
     * @return l_result Result of map validation
     */
    public String validateMap() {
        String l_result;
        if (this.d_gameMap != null) {
            l_result = d_gameMap.validateMap();
        } else {
            l_result = String.format("Cannot validate map");
        }
        return l_result;
    }

    /**
     * method to loop over players and in each subsequent loop player deploys
     * reinforcements to one's owned countries
     */
    public void deployPhase() {
        int l_currentPlayer = 0;
        Orders l_playerOrders = null;
        HashSet<String> l_playersCompleted = new HashSet<>();
        System.out.println("\nDeploy phase entered");
        System.out.println(org.apache.commons.lang3.StringUtils.repeat("-", 20));
        while (l_playersCompleted.size() < d_playerName.size()) {
            if (d_players.get(d_playerName.get(l_currentPlayer)).getNumberOfArmies() > 0) {
                System.out.println("Player " + d_playerName.get(l_currentPlayer) + "'s turn");
                System.out.println("Number of armies left: "
                        + d_players.get(d_playerName.get(l_currentPlayer)).getNumberOfArmies());
                d_players.get(d_playerName.get(l_currentPlayer)).issueOrder();
                l_playerOrders = d_players.get(d_playerName.get(l_currentPlayer)).nextOrder();
                System.out.println(l_playerOrders.executeOrder(this));
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
     * method to get the hash map of player names corresponding to their objects
     *
     * @return d_players HashMap of players
     */
    public HashMap<String, Player> getPlayers() {
        return d_players;
    }

    /**
     * method to assign the countries randomly to all the players at the start of
     * the game
     *
     * @return returns the message to the caller
     */
    public String assignCountries() {
        if (d_players.size() < 2) {
            return "There must be at least two player";
        }
        HashMap<Integer, Country> l_countries = d_gameMap.getCountries();
        List<Country> l_countryObjects = new ArrayList<Country>();
        l_countryObjects.addAll(l_countries.values());
        Random l_random = new Random();
        while (true) {
            for (Player p_player : d_players.values()) {
                if (l_countryObjects.size() == 0) {
                    break;
                }
                int l_idOfCountry = l_random.nextInt(l_countryObjects.size());
                p_player.addCountry(l_countryObjects.get(l_idOfCountry));
                l_countryObjects.get(l_idOfCountry).setPlayer(p_player);
                l_countryObjects.remove(l_countryObjects.get(l_idOfCountry));
            }
            if (l_countryObjects.size() == 0) {
                break;
            }
        }

        System.out.print("Countries Assigned\n");
        checkContinentOwnership();
        assignArmies();
        d_isGamePhase = true;
        deployPhase();
        return "Deployment done";
    }

    /**
     * method to check if any player acquired any continent, if any then add the
     * continent to the continents list acquired by player
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
     * method to assign armies to players
     */
    public void assignArmies() {
        for (Player l_player : d_players.values()) {
            l_player.setNumberOfArmies();
        }
    }

    /**
     * method to show map to player, map can be shown in edit phase and game phase
     *
     * @return map in string format
     */
    public String showMap() {
        if (!d_isGamePhase) {
            return d_gameMap.showMapEdit();
        }
        return d_gameMap.showMapPlay();
    }
}
