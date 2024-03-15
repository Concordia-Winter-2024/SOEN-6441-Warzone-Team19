package com.warzone.controller;

/**
 * The <code>LogEntryBuffer</code> class inherits Observable class whose function is to get
 * the outputs that are received after executing commands
 */
public class LogEntryBuffer extends Observable {
    private String d_value;

    /**
     * This method is used to extracts the string of output
     *
     * @return output string of the executed command
     */
    public String getString() {
        return d_value;
    }

    /**
     * This method is used to sets the string of output to notify other observers regarding the change
     *
     * @param p_value the string of output that is used to notify the observers
     */
    public void setString(String p_value) {
        d_value = p_value;
        notifyObservers(this);
    }
}
