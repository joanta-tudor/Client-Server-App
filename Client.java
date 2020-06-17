package model;

public class Client {
    private Integer id;
    private String nume;
    private String telefon;
    private Integer bilete;

    public Client(Integer id, String nume, String telefon, Integer bilete) {
        this.id = id;
        this.nume = nume;
        this.telefon = telefon;
        this.bilete = bilete;
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

    public Integer getBilete() {
        return bilete;
    }

    public void setBilete(Integer bilete) {
        this.bilete = bilete;
    }

    public String toString()
    {
        return id+" "+nume+" "+telefon+" "+bilete;
    }
}
