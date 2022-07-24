package com.xiaoxu.principle.observer;

public interface Subject {
    void registerObserver(Observer observer);

    void remove(Observer observer);

    void notifyObservers();
}
