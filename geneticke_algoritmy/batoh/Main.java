package geneticke_algoritmy.batoh;


import java.util.Arrays;


public class Main {

    public static final int KAPACITA = 20;

    public static Predmet[] predmety = new Predmet[]{
            new Predmet(1,10,5),
            new Predmet(2,8,11),
            new Predmet(3,4,7),
            new Predmet(4,18,14),
            new Predmet(5,5,3),
            new Predmet(6,17,10)
    };


    public static void main(String[] args) {


        Solve s = new Solve(predmety);

        System.out.println(Arrays.toString(s.predmety));

        Batoh res = s.findResult();
        System.out.println(res);
        System.out.println(res.obsah);
    }
}
