package Oyun;

import java.util.Set;
import java.util.TreeSet;

public class Oyuncu {

    Set<Ulke> oyuncununUlkeleri;
    boolean kaybettiMi;

    public Oyuncu() {
        oyuncununUlkeleri = new TreeSet<Ulke>();
    }

}
