package services;

import model.Agent;
import model.Excursie;

import java.io.Serializable;
import java.rmi.Remote;

public interface RezervareObserver extends Remote, Serializable {
    void rezervareUpdate(Integer id, String nume, String telefon, Integer bilet, Excursie ex, Agent ag) throws Exception;
}
