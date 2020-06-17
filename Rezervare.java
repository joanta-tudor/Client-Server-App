package model;

import java.io.Serializable;

public class Rezervare implements Serializable {
    public Integer id;
    public String numeC;
    public String telefonC;
    public Integer bileteC;
    public Integer agent_id;
    public Integer excursie_id;


    public String getNumeC() {
        return numeC;
    }

    public void setNumeC(String numeC) {
        this.numeC = numeC;
    }

    public String getTelefonC() {
        return telefonC;
    }

    public void setTelefonC(String telefonC) {
        this.telefonC = telefonC;
    }

    public Integer getBileteC() {
        return bileteC;
    }

    public void setBileteC(Integer bileteC) {
        this.bileteC = bileteC;
    }

    public Rezervare(Integer id, String numeC, String telefonC, Integer bileteC, Integer agent_id, Integer excursie_id) {
        this.id = id;
        this.numeC=numeC;
        this.telefonC=telefonC;
        this.bileteC=bileteC;
        this.agent_id = agent_id;
        this.excursie_id = excursie_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Integer agent_id) {
        this.agent_id = agent_id;
    }

    public Integer getExcursie_id() {
        return excursie_id;
    }

    public void setExcursie_id(Integer excursie_id) {
        this.excursie_id = excursie_id;
    }

    public String toString()
    {
        return id+" "+numeC+" "+telefonC+" "+bileteC+" "+agent_id+" "+excursie_id;
    }
}
