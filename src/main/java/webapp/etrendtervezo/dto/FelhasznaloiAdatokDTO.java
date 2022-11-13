package webapp.etrendtervezo.dto;

public class FelhasznaloiAdatokDTO {

    private String nem;
    private Integer tomeg;
    private Integer magassag; //cm
    private Integer eletkor;
    private String aktivitasi_szint;
    private double havi_valtozas; //kg

    public String getNem() {
        return nem;
    }

    public void setNem(String nem) {
        this.nem = nem;
    }

    public String getAktivitasi_szint() {
        return aktivitasi_szint;
    }

    public void setAktivitasi_szint(String aktivitasi_szint) {
        this.aktivitasi_szint = aktivitasi_szint;
    }

    public Integer getTomeg() {
        return tomeg;
    }

    public void setTomeg(Integer tomeg) {
        this.tomeg = tomeg;
    }

    public Integer getMagassag() {
        return magassag;
    }

    public void setMagassag(Integer magassag) {
        this.magassag = magassag;
    }

    public Integer getEletkor() {
        return eletkor;
    }

    public void setEletkor(Integer eletkor) {
        this.eletkor = eletkor;
    }

    public double getHavi_valtozas() {
        return havi_valtozas;
    }

    public void setHavi_valtozas(double havi_valtozas) {
        this.havi_valtozas = havi_valtozas;
    }

    public FelhasznaloiAdatokDTO() {
    }
}
