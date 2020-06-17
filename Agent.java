package model;

import java.io.Serializable;

public class Agent implements Serializable {
    private Integer id;
    private String parola;

    public Agent(Integer id, String parola) {
        this.id = id;
        this.parola = parola;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String toString()
    {
        return id+" "+parola;
    }

}
