package webapp.etrendtervezo.dto;



public class ReceptDTO {


    private String receptnev;
    private String kategoria;
    private String alapanyag;
    private double arany;

    public ReceptDTO() {
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
