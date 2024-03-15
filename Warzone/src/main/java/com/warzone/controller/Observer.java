package com.warzone.controller;
import java.util.List;
import java.util.ArrayList;
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