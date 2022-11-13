package webapp.etrendtervezo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Recept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String receptnev;
    private String kategoria;
    private String alapanyag;
    private double arany;

    public Recept() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceptnev() {
        return receptnev;
    }

    public void setReceptnev(String receptnev) {
        this.receptnev = receptnev;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getAlapanyag() {
        return alapanyag;
    }

    public void setAlapanyag(String alapanyag) {
        this.alapanyag = alapanyag;
    }

    public double getArany() {
        return arany;
    }

    public void setArany(double arany) {
        this.arany = arany;
    }
}
