package webapp.etrendtervezo.dto;

import javax.persistence.ElementCollection;
import java.util.Set;

public class AlapanyagDTO {

    private String alapanyagnev;
    private int bazismennyiseg;
    private double feherje;
    private double zsir;
    private double szenhidrat;
    private Set<String> kizarook;

    public AlapanyagDTO() {
    }

    public String getAlapanyagnev() {
        return alapanyagnev;
    }

    public void setAlapanyagnev(String alapanyagnev) {
        this.alapanyagnev = alapanyagnev;
    }

    public int getBazismennyiseg() {
        return bazismennyiseg;
    }

    public void setBazismennyiseg(int bazismennyiseg) {
        this.bazismennyiseg = bazismennyiseg;
    }

    public double getFeherje() {
        return feherje;
    }

    public void setFeherje(double feherje) {
        this.feherje = feherje;
    }

    public double getZsir() {
        return zsir;
    }

    public void setZsir(double zsir) {
        this.zsir = zsir;
    }

    public double getSzenhidrat() {
        return szenhidrat;
    }

    public void setSzenhidrat(double szenhidrat) {
        this.szenhidrat = szenhidrat;
    }

    public Set<String> getKizarook() {
        return kizarook;
    }

    public void setKizarook(Set<String> kizarook) {
        this.kizarook = kizarook;
    }
}
