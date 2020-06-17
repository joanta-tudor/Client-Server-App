package dto;

import javafx.collections.ObservableList;
import model.Excursie;

import java.io.Serializable;
import java.util.List;

public class CautaDTO implements Serializable {
    List<Excursie> l;

    public CautaDTO(List<Excursie> l) {
        this.l = l;
    }

    public List<Excursie> getL() {
        return l;
    }

    public void setL(List<Excursie> l) {
        this.l = l;
    }
}
