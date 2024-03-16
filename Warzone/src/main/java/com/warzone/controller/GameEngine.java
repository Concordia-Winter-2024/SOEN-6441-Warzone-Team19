package com.warzone.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


import com.warzone.controller.state.Phase;
import com.warzone.elements.GameMap;
import com.warzone.elements.Player;

/**
 * Main file class to process command and to start game
 */
public class GameEngine {
    private Phase d_phase;
    private GameMap d_gameMap = new GameMap();
    private Queue<Player> d_playersOrder = new LinkedList<>();
    public HashMap<String, Player> d_players = new HashMap<>();
    public ArrayList<String> d_playerName = new ArrayList<>();
    public GameInitialization d_gameInitialization;
    public LogEntryBuffer d_logEntryBuffer;
    private LogWriter d_logWriter;
    public Player d_neutralPlayer;
    public Random d_random;

    /**
     * Constructor to creates a neutral player also initializes LogEntryBuffer
     */
    public GameEngine() {
        d_logEntryBuffer = new LogEntryBuffer();
        d_logWriter = new LogWriter(d_logEntryBuffer);
        d_neutralPlayer = new Player("neutralPlayer#1");
        d_random = new Random();
    }

    /**
     * Main method for execution method for commands
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for processing
     * @return the result of the command to be executed
     */
    public String executeCommand(String[] p_splittedCommand) {

        return switch (p_splittedCommand[0]) {
            case "loadmap" -> loadMap(p_splittedCommand);
            case "editcontinent" -> editContinent(p_splittedCommand);
            case "editcountry" -> editCountry(p_splittedCommand);
            case "editneighbor" -> editNeighbor(p_splittedCommand);
            case "editmap" -> editMap(p_splittedCommand);
            case "savemap" -> saveMap(p_splittedCommand);
            case "gameplayer" -> gamePlayer(p_splittedCommand);
            case "assigncountries" -> assignCountries(p_splittedCommand);
            case "validatemap" -> validateMap(p_splittedCommand);
            case "showmap" -> showmap();
            case "deploy" -> deploy(p_splittedCommand);
            case "negotiate" -> diplomacy(p_splittedCommand);
            case "advance" -> advance(p_splittedCommand);
            case "airlift" -> airlift(p_splittedCommand);
            case "bomb" -> bomb(p_splittedCommand);
            case "blockade" -> blockade(p_splittedCommand);
            default -> "Command not found";
        };
    }

    /**
     * This method is used to validate the command
     *
     * @param command the command that has been splitted into multiple parts for further processing
     *             and validation
     * @throws IllegalArgumentException if the command is invalid
     */

    private void validateCommand(String[] command) {
        if (command.length < 2) {
            throw new IllegalArgumentException("Please enter valid command");
        }
        if (command[1].split("\\.").length <= 1) {
            throw new IllegalArgumentException("File extension should be " + ".map");
        }
        if (!"map".equals(command[1].split("\\.")[1])) {
            throw new IllegalArgumentException("File extension should be " + ".map");
        }
    }

    /**
     * This method is used to get the order size of the player
     *
     * @return an integer representing the size of the orders, the players has
     */
    public int getPlayersOrderSize() {
        return d_playersOrder.size();
    }

    /**
     * This method is used to add order to the order queue of the player
     *
     * @param p_player represents the player whose order is to be added to the queue
     */
    public void addPlayerOrder(Player p_player) {
        d_playersOrder.add(p_player);
    }

    /**
     * This method is used to remove an order from the order queue of the player
     *
     * @return removal of order from the queue of the player
     */
    public Player getPlayerOrder() {
        return d_playersOrder.remove();
    }

    /**
     * This method is used to obtain the phase in which we are present in the game
     *
     * @return the phase we currently are in the game
     */
    public Phase getPhase() {
        return d_phase;
    }

    /**
     * This method is used to return the current state/situation of the game map
     *
     * @return the current game map
     */
    public GameMap getGameMap() {
        return d_gameMap;
    }

    /**
     * This method is used set the game map according to the parameter provided
     *
     * @param p_gameMap the game map state we want to set
     */
    public void setGameMap(GameMap p_gameMap) {
        d_gameMap = p_gameMap;
    }

    /**
     * This method is used to set the phases of the game like editing phase, loading phase,
     * gamesetup and gameplay phase
     *
     * @param p_phase the name of the phase to be set
     */
    public void setPhase(Phase p_phase) {
        d_phase = p_phase;
    }

    /**
     * This method is used for deployment of armies to countries
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the deploy command
     */
    public String deploy(String[] p_splittedCommand) {
        return d_phase.deploy(p_splittedCommand);
    }

    /**
     * This method is used to  advance of armies from player country to enemy country or
     * own country
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the advance command
     */
    public String advance(String[] p_splittedCommand) {
        return d_phase.deploy(p_splittedCommand);
    }

    /**
     * This method is used to  airlift of armies to player's own countries
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the airlift command
     */
    public String airlift(String[] p_splittedCommand) {
        return d_phase.deploy(p_splittedCommand);
    }

    /**
     * This method is used to  bombing of the country of enemy player
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the bomb command
     */
    public String bomb(String[] p_splittedCommand) {
        return d_phase.deploy(p_splittedCommand);
    }

    /**
     * This method is used to  blockade card that blocks player own country
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the blockade command
     */
    public String blockade(String[] p_splittedCommand) {
        return d_phase.deploy(p_splittedCommand);
    }

    /**
     * This method is used to establish diplomacy among players
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the diplomacy command
     */
    public String diplomacy(String[] p_splittedCommand) {
        return d_phase.deploy(p_splittedCommand);
    }

    /**
     * This method is basically used to edit map.
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the editmap command depending upon the syntax of
     * command provided
     * @throws IllegalArgumentException if the command is invalid
     */
    public String editMap(String[] p_splittedCommand) {
        try {
            validateCommand(p_splittedCommand);
            return d_phase.editMap(p_splittedCommand[1]);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * This method is used to add and remove player.
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the gameplayer command depending upon the syntax of
     * command provided
     */
    public String gamePlayer(String[] p_splittedCommand) {
        String[] l_commandParts;
        StringBuilder l_result = new StringBuilder();
        int l_i = 1;
        if (p_splittedCommand.length < 3) {
            return "Please enter valid command. Command is \"gameplayer -add playerName -remove playerName\".";
        }

        int l_addRemoveCount = 0;
        int l_argsPerCmd = 2;

        for (int l_index = 1; l_index < p_splittedCommand.length; l_index++) {
            if ("-add".equals(p_splittedCommand[l_index]) || "-remove".equals(p_splittedCommand[l_index])) {
                l_addRemoveCount++;
            }
        }

        if ((p_splittedCommand.length - 1) != (l_addRemoveCount * 2)) {
            return "Number of arguments does not match with the add and remove command. Command is: \"gameplayer -add playername -remove playername\".";
        }

        int l_validAddRemovePlacement = 1;
        while (l_validAddRemovePlacement < p_splittedCommand.length) {
            if (!"-add".equals(p_splittedCommand[l_validAddRemovePlacement])
                    && !"-remove".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                return "Misplacement of -add and -remove keyword w.r.t number of arguments. Command is \"gameplayer -add playername -remove playername\". Use -add and -remove as per your need.";
            }
            l_validAddRemovePlacement += l_argsPerCmd;
        }

        try {
            while (l_i < p_splittedCommand.length) {
                if ("-add".equals(p_splittedCommand[l_i])) {
                    l_commandParts = new String[3];
                    l_commandParts[0] = p_splittedCommand[l_i];
                    l_commandParts[1] = p_splittedCommand[l_i + 1];
                    if (!l_result.isEmpty()) {
                        l_result.append("\n");
                    }
                    l_result.append(d_phase.gamePlayer(l_commandParts));
                    l_i = l_i + 2;
                } else if ("-remove".equals(p_splittedCommand[l_i])) {
                    l_commandParts = new String[2];
                    l_commandParts[0] = p_splittedCommand[l_i];
                    l_commandParts[1] = p_splittedCommand[l_i + 1];
                    if (!l_result.isEmpty()) {
                        l_result.append("\n");
                    }
                    l_result.append(d_phase.gamePlayer(l_commandParts));
                    l_i = l_i + 2;
                } else {
                    if (!l_result.isEmpty()) {
                        l_result.append("\n");
                    }
                    l_result.append("Command needs to have -add or -remove.");
                    l_i++;
                }
            }
        } catch (Exception p_e) {
            System.out.println("Valid command not entered.");
        }
        return l_result.toString();
    }

    /**
     * This method is used to allow user to display the current state of the map
     *
     * @return the map with all the information of the current state
     */
    public String showmap() {
        return d_phase.showMap();
    }

    /**
     * This method is used to edit the continents
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the editContinent command depending upon the syntax of
     * command provided
     */
    public String editContinent(String[] p_splittedCommand) {
        String[] l_commandParts;
        StringBuilder l_result = new StringBuilder();
        int l_i = 1;
        if (p_splittedCommand.length < 3) {
            return "Please enter valid command. Command is: \"editcontinent -add continentId controlValue -remove continentId\"";
        }

        int l_addCount = 0;
        int l_removeCount = 0;

        for (int l_index = 1; l_index < p_splittedCommand.length; l_index++) {
            if ("-add".equals(p_splittedCommand[l_index])) {
                l_addCount++;
            }
            if ("-remove".equals(p_splittedCommand[l_index])) {
                l_removeCount++;
            }
        }

        if ((p_splittedCommand.length - 1) != ((l_addCount * 3) + (l_removeCount * 2))) {
            return "Number of arguments does not match with the add and remove command. Command is: \"editcontinent -add continentId controlValue -remove continentId\".";
        }

        int l_validAddRemovePlacement = 1;
        while (l_validAddRemovePlacement < p_splittedCommand.length) {
            if (!"-add".equals(p_splittedCommand[l_validAddRemovePlacement])
                    && !"-remove".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                return "Misplacement of -add and -remove keyword w.r.t number of arguments. Command is: \"editcontinent -add continentId controlValue -remove continentId\". Use -add and -remove as per your need.";
            }
            if ("-add".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                l_validAddRemovePlacement += 3;
            } else {
                l_validAddRemovePlacement += 2;
            }
        }

        l_validAddRemovePlacement = 1;
        while (l_validAddRemovePlacement < p_splittedCommand.length) {
            if ("-add".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                if (!(isNumeric(p_splittedCommand[l_validAddRemovePlacement + 1])
                        && isNumeric(p_splittedCommand[l_validAddRemovePlacement + 2]))) {
                    return "You can only use Integer to represent continentId and controlValue for adding continent. Add Continent command is \"editneighbor -add continentId controlValue\".";
                }
                l_validAddRemovePlacement += 3;
            } else {
                if (!isNumeric(p_splittedCommand[l_validAddRemovePlacement + 1])) {
                    return "You can only use Integer to represent continentId for removing continent. Remove Continent command is \"editneighbor -remove continentId\".";
                }
                l_validAddRemovePlacement += 2;
            }
        }

        try {
            while (l_i < p_splittedCommand.length) {
                if ("-add".equals(p_splittedCommand[l_i])) {
                    l_commandParts = new String[3];
                    l_commandParts[0] = p_splittedCommand[l_i];
                    l_commandParts[1] = p_splittedCommand[l_i + 1];
                    l_commandParts[2] = p_splittedCommand[l_i + 2];
                    if (!l_result.isEmpty()) {
                        l_result.append("\n");
                    }
                    l_result.append(d_phase.editContinent(l_commandParts));
                    l_i = l_i + 3;
                } else if ("-remove".equals(p_splittedCommand[l_i])) {
                    l_commandParts = new String[2];
                    l_commandParts[0] = p_splittedCommand[l_i];
                    l_commandParts[1] = p_splittedCommand[l_i + 1];
                    if (!l_result.isEmpty()) {
                        l_result.append("\n");
                    }
                    l_result.append(d_phase.editContinent(l_commandParts));
                    l_i = l_i + 2;
                } else {
                    if (!l_result.isEmpty()) {
                        l_result.append("\n");
                    }
                    l_result.append("Command needs to have -add or -remove.");
                    l_i++;
                }
            }
        } catch (Exception p_e) {
            System.out.println("valid command not entered.");
        }
        return l_result.toString();
    }

    /**
     * This method is used to edit  countries
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the editCountry command depending upon the syntax of
     * command provided
     */
    public String editCountry(String[] p_splittedCommand) {
        String[] l_commandParts;
        StringBuilder l_result = new StringBuilder();
        int l_i = 1;
        if (p_splittedCommand.length < 3) {
            return "Please enter valid command. Command is: \"editcountry -add countryId continentId -remove countryId\".";
        }

        int l_addCount = 0;
        int l_removeCount = 0;

        for (int l_index = 1; l_index < p_splittedCommand.length; l_index++) {
            if ("-add".equals(p_splittedCommand[l_index])) {
                l_addCount++;
            }
            if ("-remove".equals(p_splittedCommand[l_index])) {
                l_removeCount++;
            }
        }

        if ((p_splittedCommand.length - 1) != ((l_addCount * 3) + (l_removeCount * 2))) {
            return "Number of arguments does not match with the add and remove command. Command is: \"editcountry -add countryId continentId -remove countryId\".";
        }

        int l_validAddRemovePlacement = 1;
        while (l_validAddRemovePlacement < p_splittedCommand.length) {
            if (!"-add".equals(p_splittedCommand[l_validAddRemovePlacement])
                    && !"-remove".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                return "Misplacement of -add and -remove keyword w.r.t number of arguments. Command is \"editcountry -add countryId continentId -remove countryId\". Use -add and -remove as per your need.";
            }
            if ("-add".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                l_validAddRemovePlacement += 3;
            } else {
                l_validAddRemovePlacement += 2;
            }
        }

        l_validAddRemovePlacement = 1;
        while (l_validAddRemovePlacement < p_splittedCommand.length) {
            if ("-add".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                if (!(isNumeric(p_splittedCommand[l_validAddRemovePlacement + 1])
                        && isNumeric(p_splittedCommand[l_validAddRemovePlacement + 2]))) {
                    return "You can only use Integer to represent countryId and continentId for adding Country. Add Country command is \"editcountry -add countryId continentId\".";
                }
                l_validAddRemovePlacement += 3;
            } else {
                if (!isNumeric(p_splittedCommand[l_validAddRemovePlacement + 1])) {
                    return "You can only use Integer to represent countryId for removing Country. Remove Country command is \"editcountry -remove countryId\".";
                }
                l_validAddRemovePlacement += 2;
            }
        }

        while (l_i < p_splittedCommand.length) {
            if ("-add".equals(p_splittedCommand[l_i])) {
                l_commandParts = new String[3];
                l_commandParts[0] = p_splittedCommand[l_i];
                l_commandParts[1] = p_splittedCommand[l_i + 1];
                l_commandParts[2] = p_splittedCommand[l_i + 2];
                if (!l_result.isEmpty()) {
                    l_result.append("\n");
                }
                l_result.append(d_phase.editCountry(l_commandParts));
                l_i = l_i + 3;
            } else if ("-remove".equals(p_splittedCommand[l_i])) {
                l_commandParts = new String[2];
                l_commandParts[0] = p_splittedCommand[l_i];
                l_commandParts[1] = p_splittedCommand[l_i + 1];
                if (!l_result.isEmpty()) {
                    l_result.append("\n");
                }
                l_result.append(d_phase.editCountry(l_commandParts));
                l_i = l_i + 2;
            } else {
                if (!l_result.isEmpty()) {
                    l_result.append("\n");
                }
                l_result.append("Command needs to have -add or -remove.");
                l_i++;
            }
        }
        return l_result.toString();
    }

    /**
     * method is used to edit the neighbors of  countries
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the editNeighbor command depending upon the syntax of
     * command provided
     */
    public String editNeighbor(String[] p_splittedCommand) {
        String[] l_commandParts;
        StringBuilder l_result = new StringBuilder();
        int l_i = 1;
        if (p_splittedCommand.length < 4) {
            return "Please enter valid command. Command is \"editneighbor -add countryId neighborCountryId -remove countryId neighborCountryId\".";
        }

        int l_addCount = 0;
        int l_removeCount = 0;

        for (int l_index = 1; l_index < p_splittedCommand.length; l_index++) {
            if ("-add".equals(p_splittedCommand[l_index])) {
                l_addCount++;
            }
            if ("-remove".equals(p_splittedCommand[l_index])) {
                l_removeCount++;
            }
        }

        if ((p_splittedCommand.length - 1) != ((l_addCount * 3) + (l_removeCount * 3))) {
            return "Number of arguments does not match with the add and remove command. Command is \"editneighbor -add countryId neighborCountryId -remove countryId neighborCountryId\".";
        }

        int l_validAddRemovePlacement = 1;
        while (l_validAddRemovePlacement < p_splittedCommand.length) {
            if (!"-add".equals(p_splittedCommand[l_validAddRemovePlacement])
                    && !"-remove".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                return "Misplacement of -add and -remove keyword w.r.t number of arguments. Command is \"editneighbor -add countryId neighborCountryId -remove countryId neighborCountryId\". Use -add and -remove as per your need.";
            }
            l_validAddRemovePlacement += 3;
        }

        l_validAddRemovePlacement = 1;
        while (l_validAddRemovePlacement < p_splittedCommand.length) {
            if ("-add".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                if (!(isNumeric(p_splittedCommand[l_validAddRemovePlacement + 1])
                        && isNumeric(p_splittedCommand[l_validAddRemovePlacement + 2]))) {
                    return "You can only use Integer to represent continentId and neighborCountryId for adding Neighbor. Add Neighbor command is \"editneighbor -add countryId neighborCountryId\".";
                }
                l_validAddRemovePlacement += 3;
            } else {
                if (!(isNumeric(p_splittedCommand[l_validAddRemovePlacement + 1])
                        && isNumeric(p_splittedCommand[l_validAddRemovePlacement + 2]))) {
                    return "You can only use Integer to represent continentId and neighborCountryId for removing Neighbor. Remove Neighbor command is \"editneighbor -remove countryId neighborCountryId\".";
                }
                l_validAddRemovePlacement += 3;
            }
        }

        while (l_i < p_splittedCommand.length) {
            if ("-add".equals(p_splittedCommand[l_i])) {
                l_commandParts = new String[3];
                l_commandParts[0] = p_splittedCommand[l_i];
                l_commandParts[1] = p_splittedCommand[l_i + 1];
                l_commandParts[2] = p_splittedCommand[l_i + 2];
                if (!l_result.isEmpty()) {
                    l_result.append("\n");
                }
                l_result.append(d_phase.editNeighbor(l_commandParts));
                l_i = l_i + 3;
            } else if ("-remove".equals(p_splittedCommand[l_i])) {
                l_commandParts = new String[3];
                l_commandParts[0] = p_splittedCommand[l_i];
                l_commandParts[1] = p_splittedCommand[l_i + 1];
                l_commandParts[2] = p_splittedCommand[l_i + 2];
                if (!l_result.isEmpty()) {
                    l_result.append("\n");
                }
                l_result.append(d_phase.editNeighbor(l_commandParts));
                l_i = l_i + 3;
            } else {
                if (!l_result.isEmpty()) {
                    l_result.append("\n");
                }
                l_result.append("Command needs to have -add or -remove.");
                l_i++;
            }
        }
        return l_result.toString();
    }

    /**
     * Method to save the map with the specific file name
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of executing the saveMap command
     * @throws IllegalArgumentException if the command is invalid
     */
    public String saveMap(String[] p_splittedCommand) {
        try {
            validateCommand(p_splittedCommand);
            return d_phase.saveMap(p_splittedCommand[1]);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Validate map method to validate a map
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of map validation
     */
    public String validateMap(String[] p_splittedCommand) {
        if (p_splittedCommand.length > 1) {
            return "Please enter valid command";
        }
        return d_phase.validateMap();
    }

    /**
     * This method is used to load a map.
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of loading of map
     * @throws IllegalArgumentException if the command is invalid
     */
    public String loadMap(String[] p_splittedCommand) {
        try {
            validateCommand(p_splittedCommand);
            return d_phase.loadMap(p_splittedCommand[1]);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * This method is used assign countries to the players
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of assigning countries to the players
     */
    public String assignCountries(String[] p_splittedCommand) {
        if (p_splittedCommand.length > 1) {
            return "Invalid Command";
        }
        return d_phase.assignCountries();
    }

    /**
     * This method is used to process the entire command provided by the user that will be
     * splitted for further processing
     *
     * @param p_userCommand the entire line that acts as the command
     */
    public void setUserCommand(GameInitialization p_userCommand) {
        d_gameInitialization = p_userCommand;
    }

    /**
     * This method is used to check if a string can be converted to integer or
     * not.
     *
     * @param p_str represents the string to be casted to Integer value.
     * @return true if the string can be parsed to an Integer.
     */
    public static boolean isNumeric(String p_str) {
        try {
            Integer.parseInt(p_str);
            return true;
        } catch (Exception p_e) {
            return false;
        }
    }
}
