package webapp.etrendtervezo.businesslogic;



import org.optaplanner.core.api.domain.solution.PlanningEntityProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PlanningSolution
public class EtkezesSolution {


   
    @PlanningEntityProperty
    private EtkezesTervezet etkezesTervezet;

    @PlanningScore
    private HardSoftScore score;

    @ValueRangeProvider(id = "aranyRange")
    @ProblemFactProperty
    List<List<Double>> aranyLista = new LinkedList<>();



    public EtkezesSolution() {
    }

    public EtkezesSolution(EtkezesTervezet etkezesTervezet) {
        this.etkezesTervezet = etkezesTervezet;

        // az egyes alapanyagok lehetséges mennyiségeit feltöltöm egy tömbbe, eredeti +/-40%, lépték 2%

        double aranyRangeTabla [][] = new double[etkezesTervezet.getArany().size()][41];

        for (int i = 0; i<etkezesTervezet.getArany().size(); i++ ){

           // System.out.println("limithez igazított arany: " +  etkezesTervezet.getArany().get(i));

            for (double k = 0; k < 81; k+=2 ) {

                double szorzo = 0.6+k/100;
                aranyRangeTabla[i][(int) (k/2)] = szorzo * etkezesTervezet.getArany().get(i);
                //TESZT
                //System.out.print(szorzo + "*" + etkezesTervezet.getArany().get(i)+ "=" + aranyRangeTabla[i][(int) (k/2)] +", ");

            }
           // System.out.println("következő külső c");
        }

      /*  for (int i=0; i<etkezesTervezet.getArany().size(); i++){

            for(int j=0; j<41; j++){

                System.out.print(Math.round(aranyRangeTabla[i][j]) + " " );
            }

            System.out.println(" következő alapanyag");}*/

        // az egyes alapanyagok lehetséges mennyiségeit az összes kombinációban elállítom
        List<List<Double>> aranyLista = new LinkedList<>();


        for (int i = 0; i < etkezesTervezet.getArany().size(); i++){

            List<Double> sor = new LinkedList<>();

            for (int j = 0; j < 41; j++){

                BigDecimal bd = new BigDecimal(aranyRangeTabla[i][j]).setScale(2, RoundingMode.HALF_UP);
                double newNum = bd.doubleValue();
                sor.add(j, newNum);
                //TESZTELÉSHEZ
                System.out.print(sor.get(j) + ", ");
            }
            System.out.println("következő kombináció");
            aranyLista.add(i, sor);
            System.out.println("aranylista:" + aranyLista);
            //TESZTELÉSHEZ


        }

        this.aranyLista = CartesianProductUtil.cartesianProduct(aranyLista);
            System.out.println("kartezsi: " + this.aranyLista);

    }





    public EtkezesTervezet getEtkezesTervezet() {
        return etkezesTervezet;
    }

    public void setEtkezesTervezet(EtkezesTervezet etkezesTervezet) {
        this.etkezesTervezet = etkezesTervezet;
    }



}
