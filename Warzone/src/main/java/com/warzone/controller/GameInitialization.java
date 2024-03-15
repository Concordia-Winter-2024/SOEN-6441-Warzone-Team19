package com.warzone.controller;

import java.util.Scanner;

import com.warzone.controller.state.Phase;
import com.warzone.controller.state.edit.PreEdit;

/**
 * Main class from where game starts, user commands are taken from here.
 */
public class GameInitialization {
    Scanner d_scanner = new Scanner(System.in);

    private static final String WELCOME_MESSAGE = "=====> Welcome to Warzone <=====";
    private static final String THANK_YOU_MESSAGE = "😃 Thank you for playing 😃";
    private static final String EXIT_COMMAND = "exit";

    public GameEngine d_gameEngine;

    /**
     * Constructor method to the class that sets the phase to the PreEdit phase to
     * start the initial process of the game
     */
    public GameInitialization() {
        d_gameEngine = new GameEngine();
        d_gameEngine.setPhase(new PreEdit(d_gameEngine));
    }

    /**
     * Method to manually set the phase of the game depending upon the user
     *
     * @param p_phase the phase that has to be set for next steps in the game
     */
    public void setPhase(Phase p_phase) {
        d_gameEngine.setPhase(p_phase);
    }

    /**
     * Method the takes in the command provided by user at the console for further
     * processing
     *
     * @return the result of the execution of the command provided after being
     *         splitted
     */
    public String getCommand() {
        String l_userCommand;
        d_gameEngine.setUserCommand(this);
        System.out.print("\033[1;34m"+" * "+"\033[0m");
        l_userCommand = d_scanner.nextLine();
        String[] l_splittedCommandString = l_userCommand.split(" ");
        if (EXIT_COMMAND.equals(l_splittedCommandString[0])) {
            return EXIT_COMMAND;
        }
        return d_gameEngine.executeCommand(l_splittedCommandString);
    }

    /**
     * Method that launches the game
     */
    public void start() {
        System.out.println("\033[1;93m"+WELCOME_MESSAGE+"\033[0m");
        new GameEngine().d_logEntryBuffer.setString("Game Started");
        GameInitialization l_gameInitialization = new GameInitialization();
        while (true) {
            String l_commandOpt = l_gameInitialization.getCommand();
            if ("exit".equals(l_commandOpt)) {
                break;
            }
            System.out.println(l_commandOpt);
        }

        System.out.println("\033[1;93m"+THANK_YOU_MESSAGE+"\033[0m");
        l_gameInitialization.d_scanner.close();
    }

    /**
     * Main method to start the game
     *
     * @param args argument to main
     */
    public static void main(String[] args) {
        new GameInitialization().start();
    }
}
