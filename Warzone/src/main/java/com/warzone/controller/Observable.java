package com.warzone.controller;

import java.util.List;
import java.util.ArrayList;

/**
 *  Observable class observers and observables (subject). It also implements the notification
 * mechanism that the observable will trigger when its state changes.
 */
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