package controller;

import java.util.Scanner;

/**
 * Main class from where game starts, user commands are taken from here..
 *
 */
public class UserCommand {
    Scanner l_scanner = new Scanner(System.in);

    /**
     * Get commands from user
     *
     * @return l_splittedCommandString array containing command that is split using " ".
     */
    public String[] getCommand() {
        String l_userCommand;
        System.out.print("\033[1;34m"+" * "+"\033[0m");
        l_userCommand = l_scanner.nextLine();
        String[] l_splittedCommandString = l_userCommand.split(" ");
        return l_splittedCommandString;
    }

    /**
     * Main method to start the game
     *
     * @param args argument to main
     */
    public static void main(String[] args) {
        Commands l_commands = new Commands();
        UserCommand l_userCommand = new UserCommand();

        System.out.println("\033[1;93m"+"=====> Welcome to Warzone <====="+"\033[0m");
        while (true) {
            String[] l_splittedCommandString = l_userCommand.getCommand();
            if (l_splittedCommandString[0].equals("exit()")) {
                break;
            }
            System.out.println(l_commands.executeCommand(l_splittedCommandString));
        }
        System.out.print("\033[1;93m"+"\n😃 Thank you for playing 😃 "+"\033[0m");
        l_userCommand.l_scanner.close();
    }
}
