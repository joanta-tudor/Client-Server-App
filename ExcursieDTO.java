package dto;

import javafx.collections.ObservableList;
import model.Excursie;

import java.io.Serializable;
import java.util.List;

public class ExcursieDTO implements Serializable {
    List<Excursie> exc;
    ObservableList<Excursie> exc1;

    public ExcursieDTO(List<Excursie> exc)
    {
        this.exc = exc;
    }

    public List<Excursie> getExc()
    {
        return exc;
    }

    public ObservableList<Excursie> getExc1() {return  exc1;}
}
