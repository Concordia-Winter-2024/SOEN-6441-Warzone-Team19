package com.warzone.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * The <code>LogWriter</code> class inherits Observer and whose function is to write the output to
 * the log file
 */
public class LogWriter implements Observer {

    FileWriter d_logFile;
    DateTimeFormatter d_dtf;
    LocalDateTime d_now;

    /**
     * Constructor for the class which attaches the object of LogEntryBuffer
     *
     * @param p_logEntry object of LogEntryBuffer
     */
    public LogWriter(LogEntryBuffer p_logEntry) {
        p_logEntry.attach(this);
        d_dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS z");
        d_now = LocalDateTime.now();
    }

    /**
     * Method to show the current timestamp and the state changes have taken
     * places during execution
     *
     * @param p_observableState the object of Observable that contains the current
     *                           state
     */
    @Override
    public void update(Observable p_observableState) {
        try (FileWriter logFile = new FileWriter(
                Paths.get(Paths.get("").toAbsolutePath().toString() + "/log/logfile.log").toString(), true)) {
            logFile.append(d_dtf.format(ZonedDateTime.now()) + "> " + ((LogEntryBuffer) p_observableState).getString() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to log file", e);
        }
    }
}
