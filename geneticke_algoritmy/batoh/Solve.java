package geneticke_algoritmy.batoh;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by pepe on 31.3.2017.
 */
class Solve {
    Predmet[] predmety;
    Batoh[] batohy;
    Random rn;

    public Solve(Predmet[] predmety) {
        this.predmety = predmety;
        Arrays.sort(this.predmety, Predmet.PomerComparator);
        batohy = new Batoh[100];
        rn = new Random();
        pocatecniPopulace();
    }

    private void pocatecniPopulace() {
        batohy[0] = new Batoh();
        for (int i = 0; i < predmety.length; i++) {
            batohy[0].addPredmet(predmety[i]);  //greedy
        }
        batohy[0].ohodnot();
        for (int i = 1; i < batohy.length; i++) {
            batohy[i] = new Batoh();
            for (int j = 0; j < 2*predmety.length; j++) {
                batohy[i].addPredmet(predmety[rn.nextInt(predmety.length)]);  //random
            }
            batohy[i].ohodnot();
        }
        Arrays.sort(batohy);
    }

    public Batoh findResult() {
        Batoh b = batohy[0];

        for (int i = 0; i < 2000; i++) {
            batohy[batohy.length - 1] = b.getMutace();
            batohy[batohy.length - 2] = b.getMutace();
            batohy[batohy.length - 3] = b.getMutace();

            Arrays.sort(batohy);
            b = batohy[rn.nextInt(batohy.length/4 +1)];
        }

        return batohy[0];
    }

}
