package dto;

import java.io.Serializable;

public class IntervalDTO implements Serializable {
    String nume;
    Integer interval1;
    Integer interval2;

    public IntervalDTO(String nume, Integer interval1, Integer interval2) {
        this.nume = nume;
        this.interval1 = interval1;
        this.interval2 = interval2;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setInterval1(Integer interval1) {
        this.interval1 = interval1;
    }

    public void setInterval2(Integer interval2) {
        this.interval2 = interval2;
    }

    public String getNume() {
        return nume;
    }

    public Integer getInterval1() {
        return interval1;
    }

    public Integer getInterval2() {
        return interval2;
    }
}
