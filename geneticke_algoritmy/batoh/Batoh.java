package geneticke_algoritmy.batoh;


import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by pepe on 31.3.2017.
 */
class Batoh implements Comparable<Batoh> {

    int hodnoceni;
    Set<Predmet> obsah;
    int obsazeno;

    public Batoh() {
        this.obsazeno = 0;
        this.hodnoceni = 0;
        this.obsah = new ConcurrentSkipListSet<>();
    }

    public void addPredmet(Predmet p) {
        if(obsazeno+p.vaha <= Main.KAPACITA && !obsah.contains(p)) {
            obsah.add(p);
            obsazeno += p.vaha;
        }
    }

    public void ohodnot() {
        hodnoceni = obsah.stream().mapToInt(i -> i.cena).sum();
    }

    public Batoh getMutace() {
        Random rn = new Random();
        Batoh n = new Batoh();
        for (Predmet p : obsah) {
            if (rn.nextBoolean()){
                n.addPredmet(p);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Main.predmety.length; j++) {
                if (rn.nextBoolean()){
                    n.addPredmet(Main.predmety[j]);
                }
            }
        }

        n.ohodnot();
        return n;
    }

    @Override
    public int compareTo(Batoh o) {
        return o.hodnoceni - hodnoceni;
    }

    @Override
    public String toString() {
        return "Batoh{" +
                "cena=" + hodnoceni +
                ", obsazeno=" + obsazeno +
                '}';
    }
}
