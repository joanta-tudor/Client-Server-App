package dto;

import model.Rezervare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RezervDTO implements Serializable {
    List<Rezervare> r=new ArrayList<>();

    public RezervDTO(List<Rezervare> r) {
        this.r = r;
    }

    public List<Rezervare> getR() {
        return r;
    }

    public void setR(List<Rezervare> r) {
        this.r = r;
    }
}
