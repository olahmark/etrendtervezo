package webapp.etrendtervezo.businesslogic;


import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import webapp.etrendtervezo.dto.AlapanyagDTO;
import webapp.etrendtervezo.dto.ReceptDTO;
import webapp.etrendtervezo.service.AlapanyagService;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@PlanningEntity
public class EtkezesTervezet {

    private AlapanyagService alapanyagService;

    @PlanningId
    private int id;
    private String kategoria;
    private String receptnev;
    private List<String> alapanyag;
    private List<AlapanyagDTO> alapanyagadatok;
    private double kaloriacel;
    private double havi_valtozas;

    @PlanningVariable (valueRangeProviderRefs = {"aranyRange"})
    private List<Double> arany;


    public EtkezesTervezet() {

    }

    public EtkezesTervezet(AlapanyagService alapanyagService, List<ReceptDTO> recept, KaloriaSzukseglet kaloriaSzukseglet) {

        this.id = 1;
        this.havi_valtozas = kaloriaSzukseglet.getFelhasznaloiAdatokDTO().getHavi_valtozas();
        double napikaloria = kaloriaSzukseglet.kalkulalas();
        this.alapanyagService = alapanyagService;
        this.receptnev = recept.get(1).getReceptnev();
        this.kategoria = recept.get(1).getKategoria();
        // alapanyaglista, arány és alapanyagadatok betöltése
        List<String> alapanyag = new LinkedList<String>();
        List<Double> arany = new LinkedList<Double>();
        List<AlapanyagDTO> alapanyagadatok = new LinkedList<AlapanyagDTO>();

        for (int i = 0; i < recept.size(); i++) {
            alapanyag.add(i, recept.get(i).getAlapanyag());
            arany.add(i, recept.get(i).getArany());
            alapanyagadatok.add(i, alapanyagService.findByAlapanyagnev(alapanyag.get(i)));
        }
        this.alapanyag = alapanyag;
        this.arany = arany;
        this.alapanyagadatok = alapanyagadatok;

        // a kalóriacél megállapítása az alapján, h mikor van az étkezés
        if (this.kategoria.equals("reggeli"))
            this.kaloriacel = napikaloria * 0.33;

        else if (this.kategoria.equals("ebed"))
            this.kaloriacel = napikaloria * 0.43;

        else this.kaloriacel = napikaloria * 0.23;

        // az adag mennyiségének beállítása a kalóriacél függvényében

        double adagszorzo = kaloriacel/kaloriatartalom();

        System.out.println("kaloriacel:" + String.format("%.2f", kaloriacel));
        System.out.println("kaloriatartalom: " + String.format("%.2f", kaloriatartalom()));
        System.out.println("adagszorzo: " + String.format("%.2f", adagszorzo));


        for (int i = 0; i < this.arany.size(); i++){

            System.out.println("eredeti arany: " + this.arany.get(i));

            this.arany.set(i, this.arany.get(i) * adagszorzo);

          //  System.out.println("adag beállítása a konstruktorban: " + String.format("%.2f", this.arany.get(i)));
        }

    }
        // teszteléshez
    public void nyomtat() {

        System.out.println(receptnev);
        System.out.println(kategoria);
        for (int i = 0; i < alapanyag.size(); i++) {
            System.out.println(alapanyag.get(i) + " " + arany.get(i) + " feherje: " + alapanyagadatok.get(i).getFeherje()
                    + "zsir: " + alapanyagadatok.get(i).getZsir()
                    + "szenhidrat: " + alapanyagadatok.get(i).getSzenhidrat());
            System.out.println(kaloriacel);
        }
        System.out.println("feherjetartalom:" + this.feherjetartalom());
        System.out.println("feherjemin: " + this.feherjemin());
        System.out.println("feherjemax: " + this.feherjemax());
        System.out.println("zsirtartalom:" + this.zsirtartalom());
        System.out.println("zsirmin: " + this.zsirmin());
        System.out.println("zsirmax: " + this.zsirmax());
        System.out.println("chtartalom:" + this.szenhidrattartalom());
        System.out.println("chmin: " + this.szenhidratmin());
        System.out.println("chmax: " + this.szenhidratmax());
        System.out.println("kaloriacel: " + this.kaloriacel);
        System.out.println("kaloriatartalom: " + this.kaloriatartalom());
    }

    // mértékegység g
    public double feherjetartalom () {

        double sumfeherje = 0;
        for (int i = 0; i < alapanyag.size(); i++) {

           // System.out.println("fut a feherjetartalom: " + " arany: " + arany.get(i) +
             //       " alapanyagadatok: " + alapanyagadatok.get(i).getFeherje());

            sumfeherje += arany.get(i) / alapanyagadatok.get(i).getBazismennyiseg() * alapanyagadatok.get(i).getFeherje();
        } return sumfeherje;
    }
    // mértékegység g
    public double zsirtartalom () {

        double sumzsir = 0;
        for (int i = 0; i < alapanyag.size(); i++) {

            sumzsir += arany.get(i) / alapanyagadatok.get(i).getBazismennyiseg() * alapanyagadatok.get(i).getZsir();
        } return sumzsir;
    }
    // mértékegység g
    public double szenhidrattartalom () {

        double sumszenhidrat = 0;
        for (int i = 0; i < alapanyag.size(); i++) {

            sumszenhidrat += arany.get(i) / alapanyagadatok.get(i).getBazismennyiseg() * alapanyagadatok.get(i).getSzenhidrat();
        } return sumszenhidrat;
    }
    // mértékegység g
    public double feherjemin () {
        double feherjeminimum = 0;
        if (kategoria.equals("reggeli")){
            if(havi_valtozas<0)
                feherjeminimum = kaloriacel/33*30*0.4/4.1;
            else if (havi_valtozas==0)
                feherjeminimum = kaloriacel/33*30*0.25/4.1;
            else feherjeminimum = kaloriacel/33*30*0.25/4.1;
        }
        if (kategoria.equals("ebed")){
            if(havi_valtozas<0)
                feherjeminimum = kaloriacel/43*40*0.4/4.1;
            else if (havi_valtozas==0)
                feherjeminimum = kaloriacel/43*40*0.25/4.1;
            else feherjeminimum = kaloriacel/43*40*0.25/4.1;
        }

        if (kategoria.equals("vacsora")){
            if(havi_valtozas<0)
                feherjeminimum = kaloriacel/23*20*0.4/4.1;
            else if (havi_valtozas==0)
                feherjeminimum = kaloriacel/23*20*0.25/4.1;
            else feherjeminimum = kaloriacel/23*20*0.25/4.1;
        }

        return feherjeminimum;

    }
    // mértékegység g
    public double feherjemax () {
        double feherjemaximum = 0;
        if (kategoria.equals("reggeli")){
            if(havi_valtozas<0)
                feherjemaximum = kaloriacel/33*35*0.5/4.1;
            else if (havi_valtozas==0)
                feherjemaximum = kaloriacel/33*35*0.35/4.1;
            else feherjemaximum = kaloriacel/33*35*0.35/4.1;
        }
        if (kategoria.equals("ebed")){
            if(havi_valtozas<0)
                feherjemaximum = kaloriacel/43*45*0.5/4.1;
            else if (havi_valtozas==0)
                feherjemaximum = kaloriacel/43*45*0.35/4.1;
            else feherjemaximum = kaloriacel/43*45*0.35/4.1;
        }

        if (kategoria.equals("vacsora")){
            if(havi_valtozas<0)
                feherjemaximum = kaloriacel/23*25*0.5/4.1;
            else if (havi_valtozas==0)
                feherjemaximum = kaloriacel/23*25*0.35/4.1;
            else feherjemaximum = kaloriacel/23*25*0.35/4.1;
        }

        return feherjemaximum;

    }
    // mértékegység g
    public double zsirmin () {
        double zsirminimum = 0;
        if (kategoria.equals("reggeli")){
            if(havi_valtozas<0)
                zsirminimum = kaloriacel/33*30*0.3/9.3;
            else if (havi_valtozas==0)
                zsirminimum = kaloriacel/33*30*0.25/9.3;
            else zsirminimum = kaloriacel/33*30*0.15/9.3;
        }
        if (kategoria.equals("ebed")){
            if(havi_valtozas<0)
                zsirminimum = kaloriacel/43*40*0.3/9.3;
            else if (havi_valtozas==0)
                zsirminimum = kaloriacel/43*40*0.25/9.3;
            else zsirminimum = kaloriacel/43*40*0.15/9.3;
        }

        if (kategoria.equals("vacsora")){
            if(havi_valtozas<0)
                zsirminimum = kaloriacel/23*20*0.3/9.3;
            else if (havi_valtozas==0)
                zsirminimum = kaloriacel/23*20*0.25/9.3;
            else zsirminimum = kaloriacel/23*20*0.15/9.3;
        }

        return zsirminimum;

    }
    // mértékegység g
    public double zsirmax () {
        double zsirmaximum = 0;
        if (kategoria.equals("reggeli")){
            if(havi_valtozas<0)
                zsirmaximum = kaloriacel/33*30*0.4/9.3;
            else if (havi_valtozas==0)
                zsirmaximum = kaloriacel/33*30*0.35/9.3;
            else zsirmaximum = kaloriacel/33*30*0.25/9.3;
        }
        if (kategoria.equals("ebed")){
            if(havi_valtozas<0)
                zsirmaximum = kaloriacel/43*40*0.4/9.3;
            else if (havi_valtozas==0)
                zsirmaximum = kaloriacel/43*40*0.35/9.3;
            else zsirmaximum = kaloriacel/43*40*0.25/9.3;
        }

        if (kategoria.equals("vacsora")){
            if(havi_valtozas<0)
                zsirmaximum = kaloriacel/23*20*0.4/9.3;
            else if (havi_valtozas==0)
                zsirmaximum = kaloriacel/23*20*0.35/9.3;
            else zsirmaximum = kaloriacel/23*20*0.25/9.3;
        }
        return zsirmaximum;
    }
    // mértékegység g
    public double szenhidratmin () {
        double szenhidratminimum = 0;
        if (kategoria.equals("reggeli")){
            if(havi_valtozas<0)
                szenhidratminimum = kaloriacel/33*30*0.1/4.1;
            else if (havi_valtozas==0)
                szenhidratminimum = kaloriacel/33*30*0.3/4.1;
            else szenhidratminimum = kaloriacel/33*30*0.4/4.1;
        }
        if (kategoria.equals("ebed")){
            if(havi_valtozas<0)
                szenhidratminimum = kaloriacel/43*40*0.1/4.1;
            else if (havi_valtozas==0)
                szenhidratminimum = kaloriacel/43*40*0.3/4.1;
            else szenhidratminimum = kaloriacel/43*40*0.4/4.1;
        }

        if (kategoria.equals("vacsora")){
            if(havi_valtozas<0)
                szenhidratminimum = kaloriacel/23*20*0.1/4.1;
            else if (havi_valtozas==0)
                szenhidratminimum = kaloriacel/23*20*0.3/4.1;
            else szenhidratminimum = kaloriacel/23*20*0.4/4.1;
        }

        return szenhidratminimum;

    }
    // mértékegység g
    public double szenhidratmax () {
        double szenhidratmaxiumum = 0;
        if (kategoria.equals("reggeli")){
            if(havi_valtozas<0)
                szenhidratmaxiumum = kaloriacel/33*30*0.3/4.1;
            else if (havi_valtozas==0)
                szenhidratmaxiumum = kaloriacel/33*30*0.5/4.1;
            else szenhidratmaxiumum = kaloriacel/33*30*0.6/4.1;
        }
        if (kategoria.equals("ebed")){
            if(havi_valtozas<0)
                szenhidratmaxiumum = kaloriacel/43*40*0.3/4.1;
            else if (havi_valtozas==0)
                szenhidratmaxiumum = kaloriacel/43*40*0.5/4.1;
            else szenhidratmaxiumum = kaloriacel/43*40*0.6/4.1;
        }

        if (kategoria.equals("vacsora")){
            if(havi_valtozas<0)
                szenhidratmaxiumum = kaloriacel/23*20*0.3/4.1;
            else if (havi_valtozas==0)
                szenhidratmaxiumum = kaloriacel/23*20*0.5/4.1;
            else szenhidratmaxiumum = kaloriacel/23*20*0.6/4.1;
        }

        return szenhidratmaxiumum;

    }


    public double kaloriatartalom() {

        return feherjetartalom()*4.1 + szenhidrattartalom()*4.1 + zsirtartalom()*9.3;

    }


    public AlapanyagService getAlapanyagService() {
        return alapanyagService;
    }

    public void setAlapanyagService(AlapanyagService alapanyagService) {
        this.alapanyagService = alapanyagService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getReceptnev() {
        return receptnev;
    }

    public void setReceptnev(String receptnev) {
        this.receptnev = receptnev;
    }

    public List<String> getAlapanyag() {
        return alapanyag;
    }

    public void setAlapanyag(List<String> alapanyag) {
        this.alapanyag = alapanyag;
    }

    public List<AlapanyagDTO> getAlapanyagadatok() {
        return alapanyagadatok;
    }

    public void setAlapanyagadatok(List<AlapanyagDTO> alapanyagadatok) {
        this.alapanyagadatok = alapanyagadatok;
    }

    public double getKaloriacel() {
        return kaloriacel;
    }

    public void setKaloriacel(double kaloriacel) {
        this.kaloriacel = kaloriacel;
    }

    public List<Double> getArany() {
        return arany;
    }

    public void setArany(List<Double> arany) {
        this.arany = arany;
    }

}
