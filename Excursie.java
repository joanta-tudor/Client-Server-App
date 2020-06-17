package model;

import java.io.Serializable;

public class Excursie implements Serializable {
    private Integer id;
    private String plecare;
    private double pret;
    private Integer locuri_disp;
    private String transport;
    private String nume;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Excursie(Integer id, String plecare, double pret, Integer locuri_disp, String transport, String nume) {
        this.id = id;
        this.plecare = plecare;
        this.pret = pret;
        this.locuri_disp = locuri_disp;
        this.transport = transport;
        this.nume=nume;
    }

    public String getPlecare() {
        return plecare;
    }

    public void setPlecare(String plecare) {
        this.plecare = plecare;
    }

    public Integer getLocuri_disp() {
        return locuri_disp;
    }

    public void setLocuri_disp(Integer locuri_disp) {
        this.locuri_disp = locuri_disp;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String toString()
    {
        return id+" "+plecare+" "+pret+" "+locuri_disp+" "+transport+" "+nume;
    }
}
