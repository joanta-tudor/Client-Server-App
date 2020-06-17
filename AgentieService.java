package services;

import javafx.collections.ObservableList;
import model.Agent;
import model.Excursie;
import model.Rezervare;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public interface AgentieService extends Remote, Serializable {
    public Iterable<Rezervare> getAll() throws Exception;

    public Iterable<Excursie> getAllE() throws Exception;

    public List<Excursie> cauta(String nume, Integer intre1, Integer intre2) throws Exception;

    public void adauga (Integer id, String nume, String telefon, Integer bilet, Excursie ex, Agent ag) throws Exception;

    public boolean login(Integer id, String pass, RezervareObserver obs) throws Exception;
}
