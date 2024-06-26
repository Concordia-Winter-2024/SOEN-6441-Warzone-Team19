package com.warzone.controller;

import com.warzone.controller.state.Phase;
import com.warzone.controller.state.edit.EditPhase;
import com.warzone.controller.state.gamephase.gamesetup.PreLoad;
import com.warzone.elements.GameMap;
import com.warzone.elements.Player;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * Game Engine class is the starting point of the game where commands are
 * processed and extracted to support the functionalities provided by the
 * commands.
 */
public class GameEngine {
    private Phase d_phase;
    private GameMap d_gameMap = new GameMap();
    public Queue<Player> d_playersOrder = new LinkedList<>();
    public HashMap<String, Player> d_players = new HashMap<>();
    public ArrayList<String> d_playerName = new ArrayList<>();
    public GameInitialization d_gameInitialization;
    public LogEntryBuffer d_logEntryBuffer;
    private LogWriter d_logWriter;
    public Player d_neutralPlayer;
    /**
     * This is the random object which
     * can be used by all the class.
     */
    public Random d_random;
    private int d_turnNumber = 0;
    private int d_maxTurn = 500;

    /**
     * Constructor method to initialize the game engine
     */
    public GameEngine() {
        d_logEntryBuffer = new LogEntryBuffer();
        d_logWriter = new LogWriter(d_logEntryBuffer);
        d_neutralPlayer = new Player("neutralPlayer#1");
        d_random = new Random();
    }

    /**
     * Enumeration for the command types
     */
    public enum CommandType {
        ADVANCE, AIRLIFT, ASSIGNCOUNTRIES, BLOCKADE, BOMB,
        DEPLOY, EDITCONTINENT, EDITCOUNTRY, EDITMAP, EDITNEIGHBOR,
        GAMEPLAYER, LOADMAP, NEGOTIATE, SAVEMAP, SHOWMAP, VALIDATEMAP,

        SAVEGAME, LOADGAME, TOURNAMENT

    }

    /**
     * Main method for execution method for commands
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for processing
     * @return the result of the command to be executed
     */
    public String executeCommand(String[] p_splittedCommand) {
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(p_splittedCommand[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Command not found";
        }

        return switch (commandType) {
            case LOADMAP -> loadMap(p_splittedCommand);
            case EDITCONTINENT -> editContinent(p_splittedCommand);
            case EDITCOUNTRY -> editCountry(p_splittedCommand);
            case EDITNEIGHBOR -> editNeighbor(p_splittedCommand);
            case EDITMAP -> editMap(p_splittedCommand);
            case SAVEMAP -> saveMap(p_splittedCommand);
            case GAMEPLAYER -> gamePlayer(p_splittedCommand);
            case ASSIGNCOUNTRIES -> assignCountries(p_splittedCommand);
            case VALIDATEMAP -> validateMap(p_splittedCommand);
            case SHOWMAP -> showmap();
            case DEPLOY -> deploy(p_splittedCommand);
            case NEGOTIATE -> diplomacy(p_splittedCommand);
            case ADVANCE -> advance(p_splittedCommand);
            case AIRLIFT -> airlift(p_splittedCommand);
            case BOMB -> bomb(p_splittedCommand);
            case BLOCKADE -> blockade(p_splittedCommand);
            case SAVEGAME -> saveGame(p_splittedCommand);
            case LOADGAME -> loadGame(p_splittedCommand);
            case TOURNAMENT -> tournament(p_splittedCommand);
        };
    }

    /**
     * This method is used to validate the command
     *
     * @param command the command that has been splitted into multiple parts for further processing
     *                and validation
     * @throws IllegalArgumentException if the command is invalid
     */

    private void validateCommand(String[] command) {
        if (command.length < 2)
            throw new IllegalArgumentException("Please enter valid command");
        if (command[1].split("\\.").length <= 1)
            throw new IllegalArgumentException("File extension should be " + ".map");
        if (!"map".equals(command[1].split("\\.")[1]))
            throw new IllegalArgumentException("File extension should be " + ".map");
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
        String l_result = "";
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
                    if (!"".equals(l_result)) {
                        l_result += "\n";
                    }
                    l_result += d_phase.gamePlayer(l_commandParts);
                    l_i = l_i + 2;
                } else if ("-remove".equals(p_splittedCommand[l_i])) {
                    l_commandParts = new String[2];
                    l_commandParts[0] = p_splittedCommand[l_i];
                    l_commandParts[1] = p_splittedCommand[l_i + 1];
                    if (!"".equals(l_result)) {
                        l_result += "\n";
                    }
                    l_result += d_phase.gamePlayer(l_commandParts);
                    l_i = l_i + 2;
                } else {
                    if (!"".equals(l_result)) {
                        l_result += "\n";
                    }
                    l_result += "Command needs to have -add or -remove.";
                    l_i++;
                }
            }
        } catch (Exception p_e) {
            System.out.println("Valid command not entered.");
        }
        return l_result;
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
        String l_result = "";
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
                    if (!"".equals(l_result)) {
                        l_result += "\n";
                    }
                    l_result += d_phase.editContinent(l_commandParts);
                    l_i = l_i + 3;
                } else if ("-remove".equals(p_splittedCommand[l_i])) {
                    l_commandParts = new String[2];
                    l_commandParts[0] = p_splittedCommand[l_i];
                    l_commandParts[1] = p_splittedCommand[l_i + 1];
                    if (!"".equals(l_result)) {
                        l_result += "\n";
                    }
                    l_result += d_phase.editContinent(l_commandParts);
                    l_i = l_i + 2;
                } else {
                    if (!"".equals(l_result)) {
                        l_result += "\n";
                    }
                    l_result += "Command needs to have -add or -remove.";
                    l_i++;
                }
            }
        } catch (Exception p_e) {
            System.out.println("valid command not entered.");
        }
        return l_result;
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
        String l_result = "";
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
                if (!"".equals(l_result)) {
                    l_result += "\n";
                }
                l_result += d_phase.editCountry(l_commandParts);
                l_i = l_i + 3;
            } else if ("-remove".equals(p_splittedCommand[l_i])) {
                l_commandParts = new String[2];
                l_commandParts[0] = p_splittedCommand[l_i];
                l_commandParts[1] = p_splittedCommand[l_i + 1];
                if (!"".equals(l_result)) {
                    l_result += "\n";
                }
                l_result += d_phase.editCountry(l_commandParts);
                l_i = l_i + 2;
            } else {
                if (!"".equals(l_result)) {
                    l_result += "\n";
                }
                l_result += "Command needs to have -add or -remove.";
                l_i++;
            }
        }
        return l_result;
    }

    /**
     * This method is used to edit the neighbors of the countries
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of the editNeighbor command depending upon the syntax of
     * command provided
     */
    public String editNeighbor(String[] p_splittedCommand) {
        String[] l_commandParts;
        String l_result = "";
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
            if ("-add".equals(p_splittedCommand[l_validAddRemovePlacement])) {
                l_validAddRemovePlacement += 3;
            } else {
                l_validAddRemovePlacement += 3;
            }
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
                if (!"".equals(l_result)) {
                    l_result += "\n";
                }
                l_result += d_phase.editNeighbor(l_commandParts);
                l_i = l_i + 3;
            } else if ("-remove".equals(p_splittedCommand[l_i])) {
                l_commandParts = new String[3];
                l_commandParts[0] = p_splittedCommand[l_i];
                l_commandParts[1] = p_splittedCommand[l_i + 1];
                l_commandParts[2] = p_splittedCommand[l_i + 2];
                if (!"".equals(l_result)) {
                    l_result += "\n";
                }
                l_result += d_phase.editNeighbor(l_commandParts);
                l_i = l_i + 3;
            } else {
                if (!"".equals(l_result)) {
                    l_result += "\n";
                }
                l_result += "Command needs to have -add or -remove.";
                l_i++;
            }
        }
        return l_result;
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
        if (d_phase instanceof EditPhase) {
            setPhase(new PreLoad(this));
        }
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
            return String.format("Invalid Command");
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
        } catch (NumberFormatException p_e) {
            return false;
        } catch (Exception p_e) {
            return false;
        }
    }

    /**
     * Method to return the maximum turns in a game. Game is draw when maximum.
     * turns reached.
     *
     * @return maximum turns in game
     */
    public int getMaxTurns() {
        return d_maxTurn;
    }

    /**
     * Method to return current turn in game play.
     *
     * @return return current turn number
     */
    public int getCurrentTurn() {
        return d_turnNumber;
    }

    /**
     * Method to increment the value of current turn, incremented when one
     * execution phase is completed.
     */
    public void incrementTurn() {
        ++d_turnNumber;
    }

    /**
     * Method to allow loading the game from the directory
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of executing the loadgame command
     */
    public String loadGame(String[] p_splittedCommand) {
        if (d_phase instanceof EditPhase) {
            setPhase(new PreLoad(this));
        }
        if (p_splittedCommand.length != 2) {
            return "Please enter valid command. Valid command is : \"loadgame filename.game\"";
        }
        if (p_splittedCommand[1].split("\\.").length <= 1) {
            return "File extension should be .map";
        }
        if (!"game".equals(p_splittedCommand[1].split("\\.")[1])) {
            return "File extension should be .game";
        }
        return d_phase.loadGame(p_splittedCommand[1]);
    }

    /**
     * Function to allow saving of game to the directory
     *
     * @param p_splittedCommand the command that has been splitted into multiple
     *                          parts for further processing
     * @return the result of executing the savegame command
     */
    public String saveGame(String[] p_splittedCommand) {
        if (p_splittedCommand.length != 2) {
            return "Please enter valid command. Valid command is : \"savegame filename.game\"";
        }
        if (p_splittedCommand[1].split("\\.").length <= 1) {
            return "File extension should be .game";
        }
        if (!"game".equals(p_splittedCommand[1].split("\\.")[1])) {
            return "File extension should be .game";
        }
        return d_phase.saveGame(p_splittedCommand[1]);
    }

    /**
     * Method to set maximum turns per game.
     *
     * @param p_maxTurn number of turns.
     */
    public void setMaxTurns(int p_maxTurn) {
        d_maxTurn = p_maxTurn;
    }

    /**
     * Method to start tournament, user provides list of maps, players strategies,
     * number of games per map and the maximum turns per game.
     *
     * @param p_splittedCommand splitted command that contains list of maps, players
     *                          strategies, number of games per map and the maximum
     *                          turns per game.
     * @return String which contains winners of each games played on each maps.
     */
    public String tournament(String[] p_splittedCommand) {
        try {

            ArrayList<String> l_playerStrategy = new ArrayList<>(
                    Arrays.asList("Random", "Aggressive", "Benevolent", "Cheater"));
            int l_indexM = 0;
            int l_indexP = 0;
            int l_indexG = 0;
            int l_indexD = 0;
            String l_tournamentCommand = "tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns";

            // getting index of all attributes -M -P -G -D from the command
            for (int l_index = 0; l_index < p_splittedCommand.length; l_index++) {
                if (p_splittedCommand[l_index].equals("-M")) {
                    l_indexM = l_index;
                } else if (p_splittedCommand[l_index].equals("-P")) {
                    l_indexP = l_index;
                } else if (p_splittedCommand[l_index].equals("-G")) {
                    l_indexG = l_index;
                } else if (p_splittedCommand[l_index].equals("-D")) {
                    l_indexD = l_index;
                }
            }

            // missing attributes in command.
            if (l_indexM == 0 || l_indexP == 0 || l_indexG == 0 || l_indexD == 0) {
                String l_temp = "Incomplete Tournament Command found.\nYou must supply with all the attributes \"-M -P -G -D\" in this sequence with their respective arguments.";
                l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                return l_temp;
            }

            // check sequence of -M -P -G -D
            if ((l_indexM >= l_indexP) || (l_indexP >= l_indexG) || (l_indexG >= l_indexD)) {
                String l_temp = "Sequence of attributes not maintained in command or arguments missing for any attribute -M -P -G -D.";
                l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                return l_temp;
            }

            // map files inclusion
            if (l_indexP - l_indexM < 2) {
                String l_temp = "There should be atleast one Map file inserted in command.\n";
                l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                return l_temp;
            } else {
                for (int l_index = l_indexM + 1; l_index < l_indexP; l_index++) {
                    if ((p_splittedCommand[l_index].split("\\.").length <= 1)) {
                        return "Please add file extension .map to the map files.";
                    } else if (!"map".equals(p_splittedCommand[l_index].split("\\.")[1])) {
                        return "After -M, The map file(s) extension should be .map";
                    } else if ("".equals(p_splittedCommand[l_index].split("\\.")[0])) {
                        return "After -M, File name missing for the Map files(.map)";
                    } else {
                        // check if the file is present or not
                        File l_file = new File(
                                Paths.get(Paths.get("").toAbsolutePath().toString() + "/maps/" + p_splittedCommand[l_index])
                                        .toString());
                        if (!l_file.exists()) {
                            return "Map file : \"" + p_splittedCommand[l_index] + "\" does not exist.";
                        }
                    }
                }
            }

            // Player Strategy checking
            if (l_indexG - l_indexP < 3) {
                String l_temp = "There should be atleast Two Players Strategy inserted in the command.";
                l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                return l_temp;
            } else {
                HashSet<String> l_tempPlayerStrategies = new HashSet<String>();
                for (int l_index = l_indexP + 1; l_index < l_indexG; l_index++) {
                    if (l_tempPlayerStrategies.contains(p_splittedCommand[l_index])) {
                        return "Duplicate players not allowed.";
                    } else {
                        if (!l_playerStrategy.contains(p_splittedCommand[l_index])) {
                            return "Strategies allowed for Player are :\" Aggressive, Benevolent, Cheater and Random\".";
                        }
                        l_tempPlayerStrategies.add(p_splittedCommand[l_index]);
                    }
                }
                if (l_tempPlayerStrategies.size() < 2) {
                    String l_temp = "There should be atleast Two Players Strategy inserted in the command.\n";
                    l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                    return l_temp;
                }
            }

            // number of games attribute
            if (l_indexD - l_indexG != 2) {
                String l_temp = "You should insert exactly One argument after -G which signifies number of games to be played in the tournament.";
                l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                return l_temp;
            } else {
                if (!isNumeric(p_splittedCommand[l_indexG + 1])) {
                    String l_temp = "After -G, you can only use integer to represent the Number of Games to be Played.";
                    l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                    return l_temp;
                }
            }

            // max number of turns
            if ((p_splittedCommand.length - 2) != l_indexD) {
                String l_temp = "You should insert exactly One argument after -D which signifies maximum number of turns allowed per game.\nAfter that decision/winner must be declared.";
                l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                return l_temp;
            } else {
                if (!isNumeric(p_splittedCommand[l_indexD + 1])) {
                    String l_temp = "After -D, you can not use integer to represent the maximum number of turns allowed per game to be played.";
                    l_temp += "\nCorrect command is :\"" + l_tournamentCommand + "\"";
                    return l_temp;
                } else {
                    if (Integer.parseInt(p_splittedCommand[l_indexD + 1]) > 900) {
                        return "Please enter Less number of turns per game to avoid Memory Stack Overflow.";
                    }
                }
            }

            int l_i = 2;
            ArrayList<String> l_maps = new ArrayList<>();
            ArrayList<String> l_players = new ArrayList<>();
            int l_numGames;
            int l_numTurns;
            while (!p_splittedCommand[l_i].equals("-P")) {
                l_maps.add(p_splittedCommand[l_i]);
                ++l_i;
            }
            ++l_i;
            while (!p_splittedCommand[l_i].equals("-G")) {
                if (!l_playerStrategy.contains(p_splittedCommand[l_i])) {
                    return "Only strategies Random, Aggressive, Benevolent and Cheater are allowed.";
                }
                l_players.add(p_splittedCommand[l_i]);
                ++l_i;
            }
            if (l_players.size() > new HashSet<String>(l_players).size()) {
                return "Duplicate players not permitted.";
            }
            ++l_i;
            l_numGames = Integer.parseInt(p_splittedCommand[l_i]);
            ++l_i;
            ++l_i;
            l_numTurns = Integer.parseInt(p_splittedCommand[l_i]);
            setPhase(new PreLoad(this));
            return d_phase.tournament(l_maps, l_players, l_numGames, l_numTurns);
        } catch (Exception p_e) {
            return "Command invalid, check for special characters.";
        }
    }

}
