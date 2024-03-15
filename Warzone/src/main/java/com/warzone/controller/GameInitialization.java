package com.warzone.controller;

import java.util.Arrays;
import java.util.Scanner;

import com.warzone.controller.state.edit.PreEdit;
import com.warzone.controller.state.Phase;

/**
 * This is the Main class from where game starts.
 *
 */
public class  GameInitialization {
    Scanner d_scanner = new Scanner(System.in);
    public GameEngine d_gameEngine;


    /**
     * constructor method to the class that sets the phase to the PreEdit phase to
     * start the initial process of the game
     */
    public GameInitialization() {
        d_gameEngine = new GameEngine();
        d_gameEngine.setPhase(new PreEdit(d_gameEngine));
    }

    /**
     * This method is used to manually set the phase of the game depending upon the user
     *
     * @param p_phase the phase that has to be set for next steps in the game
     */
    public void setPhase(Phase p_phase) {
        d_gameEngine.setPhase(p_phase);
    }

    /**
     * Method to get user command
     *
     * @return l_splittedCommandString array containing command that is split using " ".
     */
    public String getCommand() {
        String l_userCommand;
        d_gameEngine.setUserCommand(this);
        System.out.print("\033[1;34m"+" * "+"\033[0m");
        l_userCommand = d_scanner.nextLine();
        String[] l_splittedCommandString = l_userCommand.split(" ");
        if ("exit".equals(l_splittedCommandString[0])) {
            return "exit()";
        }
        return d_gameEngine.executeCommand(l_splittedCommandString);
    }


    /**
     * This method is used to launch the game
     */
    public void start() {
        System.out.println("\033[1;93m"+"=====> Welcome to Warzone <====="+"\033[0m");
        new GameEngine().d_logEntryBuffer.setString("Game Started");
        GameInitialization l_gameInitialization = new GameInitialization();
        while (true) {
            String l_commandOpt = (l_gameInitialization.getCommand());
            if ("exit()".equals(l_commandOpt)) {
                break;
            }
            System.out.println(l_commandOpt);
        }
        System.out.print("\nThank you for playing Warzone :)");
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
