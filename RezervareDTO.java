package dto;

import model.Agent;
import model.Excursie;

import java.io.Serializable;

public class RezervareDTO implements Serializable {
    Integer id;
    String nume;
    String telefon;
    Integer bilet;
    Excursie ex;
    Agent ag;

    public RezervareDTO(Integer id, String nume, String telefon, Integer bilet, Excursie ex, Agent ag) {
        this.id = id;
        this.nume = nume;
        this.telefon = telefon;
        this.bilet = bilet;
        this.ex = ex;
        this.ag = ag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Integer getBilet() {
        return bilet;
    }

    public void setBilet(Integer bilet) {
        this.bilet = bilet;
    }

    public Excursie getEx() {
        return ex;
    }

    public void setEx(Excursie ex) {
        this.ex = ex;
    }

    public Agent getAg() {
        return ag;
    }

    public void setAg(Agent ag) {
        this.ag = ag;
    }
}
