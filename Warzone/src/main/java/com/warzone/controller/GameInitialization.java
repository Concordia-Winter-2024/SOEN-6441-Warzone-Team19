package com.warzone.controller;

import java.util.Scanner;

/**
 * Main class from where game starts, user commands are taken from here.
 *
 */
public class GameInitialization {
    Scanner l_scanner = new Scanner(System.in);

    /**
     * Get commands from user
     *
     * @return l_splittedCommandString array containing command that is split using " ".
     */
    public String[] getCommand() {
        String l_userCommand;
        System.out.print("\033[1;34m" + " * " + "\033[0m");
        l_userCommand = l_scanner.nextLine();
        return l_userCommand.split(" ");
    }

    /**
     * Main method to start the game
     *
     * @param args argument to main
     */
    public static void main(String[] args) {
        Commands l_commands = new Commands();
        GameInitialization l_gameInitialization = new GameInitialization();

        System.out.println("\033[1;93m" + "=====> Welcome to Warzone <=====" + "\033[0m");
        while (true) {
            String[] l_splittedCommandString = l_gameInitialization.getCommand();
            if (l_splittedCommandString[0].equals("exit")) {
                break;
            }
            System.out.println(l_commands.executeCommand(l_splittedCommandString));
        }
        System.out.print("\033[1;93m" + "\n😃 Thank you for playing 😃 " + "\033[0m");
        l_gameInitialization.l_scanner.close();
    }

    /**
     * Interface for the Observer, which specifies the implementation of the update function in all views.
     */

    public interface Observer {

        /**
         * method to be implemented that responds to  notification generally by
         * querying the model object and presenting its most recent state
         *
         * @param p_observableState: The object that the subject passes through (observable)
         *                         Though it's not always the case,
         *                         this object is frequently the subject.
         */
        public void update(Observable p_observableState);
    }


    public class Observable {
        private List<Observer> d_observers = new ArrayList<Observer>();

        /**
         * Method to attach a view to the model.
         *
         * @param p_observer: view that has to be added to the list of observers that are to be notified.
         */
        public void attach(Observer p_observer) {
            this.d_observers.add(p_observer);
        }

        /**
         * Method to detach a view from the model.
         *
         * @param p_observer: view that has o be removed from the list of observers.
         */
        public void detach(Observer p_observer) {
            if (!d_observers.isEmpty()) {
                d_observers.remove(p_observer);
            }
        }

        /**
         * It notifies all the views attached to the model.
         *
         * @param p_observable: object that contains the information to be observed.
         */
        public void notifyObservers(Observable p_observable) {
            for (Observer l_observer : d_observers) {
                l_observer.update(p_observable);
            }
        }
    }

}


