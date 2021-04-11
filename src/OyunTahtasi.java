import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OyunTahtasi extends JPanel {

    public static List<Set<Ulke>> kitalar;
    public static final int[] kitaBonuslari = {5, 2, 5, 3, 7, 2};
    public static Ulke[] ulkeler;

    public static final int OYUNTAHTASI_GENISLIK = 900;
    public static final int OYUNTAHTASI_YUKSEKLIK = 600;

    public static final Color[] ulkeRenkleri = {Color.CYAN, Color.MAGENTA};

    public static int sira = 0;
    public static int troopsToPlace;
    public static Ulke seciliUlke;
    public static Ulke seciliIkinciUlke;
    public final JLabel siraBilgisi;
    public final JLabel[] kartBilgisi;
    public final Zar zarBilgisi;

    public OyunTahtasi(final JLabel turnInfo, final JLabel[] kartBilgisi, Zar zarBilgisi, int numPlayers) {
        this.siraBilgisi = turnInfo;
        this.kartBilgisi = kartBilgisi;
        this.zarBilgisi = zarBilgisi;

        initializeUlkeler();
        initializeKitalar();

    }

    //kitalari initialize ediyorum ulkeleri kitalara gore olusturup ekledigim icin siraya gore kitalara ekliyorum
    private void initializeKitalar() {

        kitalar = new ArrayList<Set<Ulke>>();
        //6 tane kitayi olusturuyorum <Not Antartikada ulke olmadigi icin kita olarak onu eklemedim>
        for (int i = 0; i < 6; i++) {
            kitalar.add(i, new TreeSet<Ulke>());
        }

        // Kuzey amerika kıtasına ulkeleri ekliyorum
        Set<Ulke> thisContinent = kitalar.get(0);
        for (int i = 0; i < 9; i++) {
            thisContinent.add(ulkeler[i]);
        }

        // Guney amerika kıtasına ulkeleri ekliyorum
        thisContinent = kitalar.get(1);
        for (int i = 9; i < 13; i++) {
            thisContinent.add(ulkeler[i]);
        }

        // Avrupa kitasina ulkeleri ekliyorum
        thisContinent = kitalar.get(2);
        for (int i = 13; i < 20; i++) {
            thisContinent.add(ulkeler[i]);
        }

        // Afrika kitasina ulkeleri ekliyorum
        thisContinent = kitalar.get(3);
        for (int i = 20; i < 26; i++) {
            thisContinent.add(ulkeler[i]);
        }

        // Asya kitasina ulkeleri ekliyorum
        thisContinent = kitalar.get(4);
        for (int i = 26; i < 38; i++) {
            thisContinent.add(ulkeler[i]);
        }

        // Avustralya kitasina ulkeleri ekliyorum
        thisContinent = kitalar.get(5);
        for (int i = 38; i < 42; i++) {
            thisContinent.add(ulkeler[i]);
        }
    }

    //ulkeleri initialize ediyorum ulkeleri kita sirasina gore ekliyorum
    public void initializeUlkeler() {

        ulkeler = new Ulke[42];
        ulkeler[0] = new Ulke("Alaska", 30, 30, 80, 60);
        ulkeler[1] = new Ulke("Alberta", 110, 80, 80, 50);
        ulkeler[2] = new Ulke("Central America", 140, 210, 65, 60);
        ulkeler[3] = new Ulke("Eastern United States", 200, 130, 50, 80);
        ulkeler[4] = new Ulke("Greenland", 280, 20, 80, 60);
        ulkeler[5] = new Ulke("Northwest Territory", 110, 30, 120, 50);
        ulkeler[6] = new Ulke("Ontario", 190, 80, 60, 50);
        ulkeler[7] = new Ulke("Quebec", 250, 90, 50, 70);
        ulkeler[8] = new Ulke("Western United States", 110, 130, 90, 80);
        ulkeler[9] = new Ulke("Venezuela", 200, 270, 70, 50);
        ulkeler[10] = new Ulke("Brazil", 230, 320, 90, 120);
        ulkeler[11] = new Ulke("Peru", 190, 320, 40, 120);
        ulkeler[12] = new Ulke("Argentina", 200, 440, 70, 100);
        ulkeler[13] = new Ulke("Great Britain", 400, 110, 20, 40);
        ulkeler[14] = new Ulke("Iceland", 370, 90, 20, 20);
        ulkeler[15] = new Ulke("Northern Europe", 440, 140, 60, 40);
        ulkeler[16] = new Ulke("Scandinavia", 450, 20, 50, 100);
        ulkeler[17] = new Ulke("Ukraine", 500, 70, 100, 110);
        ulkeler[18] = new Ulke("Southern Europe", 440, 180, 80, 40);
        ulkeler[19] = new Ulke("Western Europe", 380, 160, 60, 40);
        ulkeler[20] = new Ulke("Madagascar", 540, 400, 20, 40);
        ulkeler[21] = new Ulke("Egypt", 450, 240, 70, 40);
        ulkeler[22] = new Ulke("North Africa", 360, 240, 90, 90);
        ulkeler[23] = new Ulke("East Africa", 450, 280, 80, 110);
        ulkeler[24] = new Ulke("Congo", 390, 330, 60, 60);
        ulkeler[25] = new Ulke("South Africa", 420, 390, 80, 110);
        ulkeler[26] = new Ulke("Middle East", 520, 180, 110, 70);
        ulkeler[27] = new Ulke("Afghanistan", 600, 110, 70, 70);
        ulkeler[28] = new Ulke("Ural", 600, 55, 80, 55);
        ulkeler[29] = new Ulke("India", 630, 180, 60, 100);
        ulkeler[30] = new Ulke("China", 670, 110, 90, 70);
        ulkeler[31] = new Ulke("Siberia", 680, 35, 30, 75);
        ulkeler[32] = new Ulke("Siam", 690, 180, 40, 70);
        ulkeler[33] = new Ulke("Mongolia", 710, 80, 60, 30);
        ulkeler[34] = new Ulke("Irkutsk", 710, 55, 60, 25);
        ulkeler[35] = new Ulke("Yakutsk", 710, 30, 60, 25);
        ulkeler[36] = new Ulke("Kamchatka", 770, 30, 30, 70);
        ulkeler[37] = new Ulke("Japan", 810, 80, 20, 40);
        ulkeler[38] = new Ulke("Indonesia", 730, 300, 30, 30);
        ulkeler[39] = new Ulke("Western Australia", 740, 360, 60, 70);
        ulkeler[40] = new Ulke("Eastern Australia", 800, 360, 50, 70);
        ulkeler[41] = new Ulke("New Guinea", 800, 310, 40, 25);
        ulkeler[0].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[0].komsuUlkeleri.add(ulkeler[1]);
        ulkeler[0].komsuUlkeleri.add(ulkeler[5]);
        ulkeler[0].komsuUlkeleri.add(ulkeler[36]);
        ulkeler[1].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[1].komsuUlkeleri.add(ulkeler[5]);
        ulkeler[1].komsuUlkeleri.add(ulkeler[6]);
        ulkeler[1].komsuUlkeleri.add(ulkeler[8]);
        ulkeler[2].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[2].komsuUlkeleri.add(ulkeler[3]);
        ulkeler[2].komsuUlkeleri.add(ulkeler[8]);
        ulkeler[2].komsuUlkeleri.add(ulkeler[9]);
        ulkeler[3].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[3].komsuUlkeleri.add(ulkeler[6]);
        ulkeler[3].komsuUlkeleri.add(ulkeler[7]);
        ulkeler[3].komsuUlkeleri.add(ulkeler[8]);
        ulkeler[4].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[4].komsuUlkeleri.add(ulkeler[5]);
        ulkeler[4].komsuUlkeleri.add(ulkeler[6]);
        ulkeler[4].komsuUlkeleri.add(ulkeler[7]);
        ulkeler[4].komsuUlkeleri.add(ulkeler[14]);
        ulkeler[5].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[5].komsuUlkeleri.add(ulkeler[6]);
        ulkeler[6].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[6].komsuUlkeleri.add(ulkeler[7]);
        ulkeler[6].komsuUlkeleri.add(ulkeler[8]);
        ulkeler[7].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[8].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[9].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[9].komsuUlkeleri.add(ulkeler[10]);
        ulkeler[9].komsuUlkeleri.add(ulkeler[11]);
        ulkeler[10].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[10].komsuUlkeleri.add(ulkeler[11]);
        ulkeler[10].komsuUlkeleri.add(ulkeler[12]);
        ulkeler[10].komsuUlkeleri.add(ulkeler[22]);
        ulkeler[11].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[11].komsuUlkeleri.add(ulkeler[12]);
        ulkeler[12].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[13].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[13].komsuUlkeleri.add(ulkeler[14]);
        ulkeler[13].komsuUlkeleri.add(ulkeler[15]);
        ulkeler[13].komsuUlkeleri.add(ulkeler[16]);
        ulkeler[13].komsuUlkeleri.add(ulkeler[19]);
        ulkeler[14].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[15].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[15].komsuUlkeleri.add(ulkeler[16]);
        ulkeler[15].komsuUlkeleri.add(ulkeler[17]);
        ulkeler[15].komsuUlkeleri.add(ulkeler[18]);
        ulkeler[15].komsuUlkeleri.add(ulkeler[19]);
        ulkeler[16].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[16].komsuUlkeleri.add(ulkeler[17]);
        ulkeler[17].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[17].komsuUlkeleri.add(ulkeler[18]);
        ulkeler[17].komsuUlkeleri.add(ulkeler[26]);
        ulkeler[17].komsuUlkeleri.add(ulkeler[27]);
        ulkeler[17].komsuUlkeleri.add(ulkeler[28]);
        ulkeler[18].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[18].komsuUlkeleri.add(ulkeler[19]);
        ulkeler[18].komsuUlkeleri.add(ulkeler[21]);
        ulkeler[18].komsuUlkeleri.add(ulkeler[22]);
        ulkeler[18].komsuUlkeleri.add(ulkeler[26]);
        ulkeler[19].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[19].komsuUlkeleri.add(ulkeler[22]);
        ulkeler[20].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[20].komsuUlkeleri.add(ulkeler[23]);
        ulkeler[20].komsuUlkeleri.add(ulkeler[25]);
        ulkeler[21].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[21].komsuUlkeleri.add(ulkeler[22]);
        ulkeler[21].komsuUlkeleri.add(ulkeler[23]);
        ulkeler[21].komsuUlkeleri.add(ulkeler[26]);
        ulkeler[22].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[22].komsuUlkeleri.add(ulkeler[23]);
        ulkeler[22].komsuUlkeleri.add(ulkeler[24]);
        ulkeler[23].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[23].komsuUlkeleri.add(ulkeler[24]);
        ulkeler[23].komsuUlkeleri.add(ulkeler[25]);
        ulkeler[23].komsuUlkeleri.add(ulkeler[26]);
        ulkeler[24].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[24].komsuUlkeleri.add(ulkeler[25]);
        ulkeler[25].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[26].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[26].komsuUlkeleri.add(ulkeler[27]);
        ulkeler[26].komsuUlkeleri.add(ulkeler[29]);
        ulkeler[27].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[27].komsuUlkeleri.add(ulkeler[28]);
        ulkeler[27].komsuUlkeleri.add(ulkeler[29]);
        ulkeler[27].komsuUlkeleri.add(ulkeler[30]);
        ulkeler[28].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[28].komsuUlkeleri.add(ulkeler[30]);
        ulkeler[28].komsuUlkeleri.add(ulkeler[31]);
        ulkeler[29].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[29].komsuUlkeleri.add(ulkeler[30]);
        ulkeler[29].komsuUlkeleri.add(ulkeler[32]);
        ulkeler[30].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[30].komsuUlkeleri.add(ulkeler[31]);
        ulkeler[30].komsuUlkeleri.add(ulkeler[32]);
        ulkeler[30].komsuUlkeleri.add(ulkeler[33]);
        ulkeler[31].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[31].komsuUlkeleri.add(ulkeler[33]);
        ulkeler[31].komsuUlkeleri.add(ulkeler[34]);
        ulkeler[31].komsuUlkeleri.add(ulkeler[35]);
        ulkeler[32].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[32].komsuUlkeleri.add(ulkeler[38]);
        ulkeler[33].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[33].komsuUlkeleri.add(ulkeler[34]);
        ulkeler[33].komsuUlkeleri.add(ulkeler[36]);
        ulkeler[33].komsuUlkeleri.add(ulkeler[37]);
        ulkeler[34].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[34].komsuUlkeleri.add(ulkeler[35]);
        ulkeler[34].komsuUlkeleri.add(ulkeler[36]);
        ulkeler[35].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[35].komsuUlkeleri.add(ulkeler[36]);
        ulkeler[36].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[36].komsuUlkeleri.add(ulkeler[37]);
        ulkeler[37].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[38].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[38].komsuUlkeleri.add(ulkeler[39]);
        ulkeler[38].komsuUlkeleri.add(ulkeler[41]);
        ulkeler[39].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[39].komsuUlkeleri.add(ulkeler[40]);
        ulkeler[39].komsuUlkeleri.add(ulkeler[41]);
        ulkeler[40].komsuUlkeleri = new TreeSet<Ulke>();
        ulkeler[40].komsuUlkeleri.add(ulkeler[41]);
        ulkeler[41].komsuUlkeleri = new TreeSet<Ulke>();
        for (int i = 0; i < ulkeler.length; i++) {
            for (Ulke c : ulkeler[i].komsuUlkeleri) {
                c.komsuUlkeleri.add(ulkeler[i]);
            }
        }

    }

    //cizgileri ciziyorum
    private void drawLines(Graphics g) {
        g.drawLine(0, 60, 30, 60);
        g.drawLine(800, 60, 900, 60);
        g.drawLine(230, 55, 280, 50);
        g.drawLine(250, 80, 280, 50);
        g.drawLine(300, 90, 310, 80);
        g.drawLine(360, 80, 370, 90);
        g.drawLine(390, 95, 410, 110);
        g.drawLine(410, 150, 410, 160);
        g.drawLine(410, 110, 450, 70);
        g.drawLine(420, 130, 440, 140);
        g.drawLine(475, 120, 470, 140);
        g.drawLine(480, 220, 485, 240);
        g.drawLine(440, 220, 405, 240);
        g.drawLine(410, 200, 405, 240);
        g.drawLine(530, 280, 575, 250);
        g.drawLine(530, 390, 540, 400);
        g.drawLine(500, 445, 540, 420);
        g.drawLine(320, 320, 360, 285);
        g.drawLine(810, 120, 770, 110);
        g.drawLine(810, 80, 800, 65);
        g.drawLine(710, 250, 745, 300);
        g.drawLine(760, 315, 800, 323);
        g.drawLine(745, 330, 770, 360);
        g.drawLine(820, 335, 825, 360);
        g.drawLine(770, 360, 800, 335);
    }

}
