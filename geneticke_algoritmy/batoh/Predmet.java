package geneticke_algoritmy.batoh;

import java.util.Comparator;

/**
 * Created by pepe on 31.3.2017.
 */
class Predmet implements Comparable<Predmet> {

    int cislo, vaha, cena;
    double pomer;

    public Predmet(int cislo, int vaha, int cena) {
        this.cislo = cislo;
        this.vaha = vaha;
        this.cena = cena;
        this.pomer = (double)cena / vaha;
    }

    @Override
    public String toString() {
        return "Predmet{" +
                "cislo=" + cislo +
                ", vaha=" + vaha +
                ", cena=" + cena +
                ", pomer=" + pomer +
                '}';
    }

    @Override
    public int compareTo(Predmet o) {
        return cislo - o.cislo;
    }

    public static Comparator<Predmet> PomerComparator
            = new Comparator<Predmet>() {

        @Override
        public int compare(Predmet o1, Predmet o2) {
            if (o1.pomer < o2.pomer) {
                return 1;
            }
            else if (o1.pomer > o2.pomer){
                return -1;
            }

            return 0;
        }

    };
}
