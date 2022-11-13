package webapp.etrendtervezo.businesslogic;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class EtkezesEasyScoreCalculator implements EasyScoreCalculator<EtkezesSolution, HardSoftScore> {

    public boolean teszt () {System.out.println("fut a score"); return false;}

    @Override
    public HardSoftScore calculateScore(EtkezesSolution etkezesSolution) {

        EtkezesTervezet etkezesTervezet = etkezesSolution.getEtkezesTervezet();
        int hardScore = 0;



        if ( teszt() ||
                etkezesTervezet.feherjetartalom() < etkezesTervezet.feherjemin() ||
            etkezesTervezet.feherjetartalom() > etkezesTervezet.feherjemax() ||

                etkezesTervezet.zsirtartalom() < etkezesTervezet.zsirmin() ||
                etkezesTervezet.zsirtartalom() > etkezesTervezet.zsirmax() ||

                etkezesTervezet.szenhidrattartalom() < etkezesTervezet.szenhidratmin() ||
                etkezesTervezet.szenhidrattartalom() > etkezesTervezet.szenhidratmax() ||

                etkezesTervezet.kaloriatartalom() < etkezesTervezet.getKaloriacel()*0.95 ||
                etkezesTervezet.kaloriatartalom() > etkezesTervezet.getKaloriacel()*1.05
            )
            hardScore--;

        int softScore = 0;
        return HardSoftScore.of(hardScore, softScore);
    }
}

