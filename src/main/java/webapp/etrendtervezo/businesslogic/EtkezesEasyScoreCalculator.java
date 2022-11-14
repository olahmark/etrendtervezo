package webapp.etrendtervezo.businesslogic;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class EtkezesEasyScoreCalculator implements EasyScoreCalculator<EtkezesSolution, HardSoftScore> {

    //int i =0;
    //private boolean teszt (int i) {System.out.println("fut a score" + i); return false; }

    @Override
    public HardSoftScore calculateScore(EtkezesSolution etkezesSolution) {

        EtkezesTervezet etkezesTervezet = etkezesSolution.getEtkezesTervezet();
        int hardScore = 0;
        int softScore = 0;

        if (etkezesTervezet.feherjetartalom() < etkezesTervezet.feherjemin())
            softScore--;

         if (etkezesTervezet.feherjetartalom() > etkezesTervezet.feherjemax())
            softScore--;

        if (etkezesTervezet.zsirtartalom() < etkezesTervezet.zsirmin())
            softScore--;

        if(etkezesTervezet.zsirtartalom() > etkezesTervezet.zsirmax())
            softScore--;

        if (etkezesTervezet.szenhidrattartalom() < etkezesTervezet.szenhidratmin())
            softScore--;

        if (etkezesTervezet.szenhidrattartalom() > etkezesTervezet.szenhidratmax())
            softScore--;

        if (etkezesTervezet.kaloriatartalom() < etkezesTervezet.getKaloriacel()*0.95)
            hardScore--;

        if( etkezesTervezet.kaloriatartalom() > etkezesTervezet.getKaloriacel()*1.05)
            hardScore--;

       // i++;

        return HardSoftScore.of(hardScore, softScore);
    }
}

