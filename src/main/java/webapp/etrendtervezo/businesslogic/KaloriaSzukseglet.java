package webapp.etrendtervezo.businesslogic;

import webapp.etrendtervezo.dto.FelhasznaloiAdatokDTO;

public class KaloriaSzukseglet {

    private FelhasznaloiAdatokDTO felhasznaloiAdatokDTO;

    public FelhasznaloiAdatokDTO getFelhasznaloiAdatokDTO() {
        return felhasznaloiAdatokDTO;
    }

    public void setFelhasznaloiAdatokDTO(FelhasznaloiAdatokDTO felhasznaloiAdatokDTO) {
        this.felhasznaloiAdatokDTO = felhasznaloiAdatokDTO;
    }

    public KaloriaSzukseglet(FelhasznaloiAdatokDTO felhasznaloiAdatokDTO) {
        this.felhasznaloiAdatokDTO = felhasznaloiAdatokDTO;
    }

    public double kalkulalas() {
        int nemi_faktor;
        double aktivitasi_faktor;

        if (felhasznaloiAdatokDTO.getNem().equals("ferfi")) nemi_faktor = 5;
        else nemi_faktor = -161;

        if(felhasznaloiAdatokDTO.getAktivitasi_szint().equals("ulo")) aktivitasi_faktor = 1.2;
        else if(felhasznaloiAdatokDTO.getAktivitasi_szint().equals("enyhe")) aktivitasi_faktor = 1.375;
        else if(felhasznaloiAdatokDTO.getAktivitasi_szint().equals("kozepes")) aktivitasi_faktor = 1.55;
        else if(felhasznaloiAdatokDTO.getAktivitasi_szint().equals("nagy")) aktivitasi_faktor = 1.725;
        else aktivitasi_faktor = 1.9;

        double kaloriaszukseglet = aktivitasi_faktor * ((10 * felhasznaloiAdatokDTO.getTomeg()) +
                                        6.25 * felhasznaloiAdatokDTO.getMagassag() +
                                        nemi_faktor - 5 * felhasznaloiAdatokDTO.getEletkor()) +
                                        felhasznaloiAdatokDTO.getHavi_valtozas() / 4 / 7 * 7000;

        return kaloriaszukseglet;
    }



}
