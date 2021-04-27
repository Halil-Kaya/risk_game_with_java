package Oyun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OyunTahtasi extends JPanel {


    public static List<Set<Ulke>> kitalar;//kitalari tutuyorum kitalarinin icinde ulkeler var
    public static Ulke[] ulkeler;//ulkeleri tutuyorum

    //genislik ve yukseklik bilgileri
    public static int OYUNTAHTASI_GENISLIK = 900;
    public static int OYUNTAHTASI_YUKSEKLIK = 600;

    //ulkenin renkleri
    public static Color[] ulkeRenkleri = {Color.CYAN, Color.MAGENTA};

    public static int sira = 0;//siranin kimde oldugunu takip etmek icin bu degiskeni kullaniyorum
    public static int yerlestirilecekAskerSayisi;//yerlestirilecek asker sayisini bu degiskene atiyorum
    public static Ulke seciliUlke;//secili ulkeyi bu degiskene atiyorum
    public static Ulke seciliIkinciUlke;//secili ikinci ulkeyi bu degiskene atiyorum
    public JLabel siraBilgisi;//oyuncuya durum hakkinda bilgi veriyor

    public Oyuncu[] oyuncular;//oyunculari tutuyorum

    //oyun esnasinda bir cok durum var bu durumlari enum olarak tutuyorum boylece oyun sirasinda hangi durumda oldugunu bilebilcem
    public enum Durum {
        BaslangicDurumu, AskerYerlestirmeDurumu, SaldiriyiYapacakUlkeSecmeDurumu, SaldirmaDurumu,
        SaldiriyaDevamEtmeDurumu, FetihSonrasiDurumu, TransferYapacakUlkeSecmeDurumu, TransferDurumu,
        TransfereDevamEtmeDurumu, OyunuBitirmeDurumu;
    }

    //ilk durum baslangic durumu
    public Durum durum = Durum.BaslangicDurumu;

    public OyunTahtasi(JLabel siraBilgisi) {
        this.siraBilgisi = siraBilgisi;

        //oyunculari olusturuyorum
        this.oyuncular = new Oyuncu[2];
        this.oyuncular[0] = new Oyuncu();
        this.oyuncular[1] = new Oyuncu();

        //ulkeleri olusturuyorum
        initializeUlkeler();
        //kitalari olusturuyorum
        initializeKitalar();

        //oyunculara ulkeleri dagitiyorum
        oyuncularaUlkeDagit();

        //yerlestirilecek asker sayisini hesaplayip yerlestirilecekAskerSayisi degiskinene atar
        yerlestirilecekAskerSayisiniHesapla();

        this.siraBilgisi.setText(ekranaBilgiBastir());

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();

                if(durum == Durum.BaslangicDurumu || durum == Durum.AskerYerlestirmeDurumu){
                    //duruma gore asker yerlestirtiyorum
                    askerYerlestir(mouse);

                }else if(durum == Durum.SaldiriyiYapacakUlkeSecmeDurumu || durum == Durum.TransferYapacakUlkeSecmeDurumu){
                    //saldirma durumundaysa saldiri yapacak olan ulkeyi sectirtiyorum burda oyuncu sadece kendi 1 askerden fazla olan ulkesini secebilir
                    sahipOlunanUlkeyiSec(mouse);

                }else if(durum == Durum.SaldirmaDurumu){
                    //saldirma durumundaysa saldiri yapabilecek dusman ulkeyi sectiriyorum sadece sinir komsulugu varsa saldirabilir
                    dusmanUlkeSec(mouse);

                }else if(durum == Durum.SaldiriyaDevamEtmeDurumu){
                    //saldirmaya devam etsin durumundaysa eger hala asker varsa saldiriya devam ediyor
                    saldirmayaDevamEt(mouse);


                }else if(durum == Durum.FetihSonrasiDurumu){
                    //saldiri basarili ise asker yerlestirme durumuna geciyorum yeni feth edilen ulkeye kac asker yerlestirilsin
                    yeniUlkeyeAskerYerlestir(mouse);


                }else if(durum == Durum.TransferDurumu){
                    //asker transfer etmemi sagliyor
                    askerTransferEdilecekUlkeyiSec(mouse);


                }else if(durum == Durum.TransfereDevamEtmeDurumu){
                    //asker transfer etmeye devam ediyorum
                    if (seciliIkinciUlke.sinirdaMi(mouse)) {
                        askerTransferEt();
                    }
                }

                //oyuncuya durum hakkinda bilgi veriyorum
                OyunTahtasi.this.siraBilgisi.setText(ekranaBilgiBastir());
                //tahtayi yeniden ciziyorum
                repaint();
            }

        });

    }

    //kitalari initialize ediyorum ulkeleri kitalara gore olusturup ekledigim icin siraya gore kitalara ekliyorum
    public void initializeKitalar() {

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

    //oyuncu nelere gidebilir onu gostermek icin cizgiler ciziyorum
    public void drawLines(Graphics g) {
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

    //oyunculara ulke dagitiyorum
    public void oyuncularaUlkeDagit() {
        for (int i = 0; i < ulkeler.length; i++) {
            if(i%2 == 0){
                oyuncular[0].oyuncununUlkeleri.add(ulkeler[i]);
            }else{
                oyuncular[1].oyuncununUlkeleri.add(ulkeler[i]);
            }
        }
    }

    //eger oyunculardan biri kaybederse durumu guncelleyip oyunu bitiriyorum
    public void oyunBittimiKontrolEt() {
        //oyunculardan biri kaybetti mi diye kontrol eidyorum
        if(oyuncular[0].kaybettiMi || oyuncular[1].kaybettiMi){
            //durumu guncelliyorum
            durum = Durum.OyunuBitirmeDurumu;
            //oyuncuya durum hakkinda bilgi veriyorum
            siraBilgisi.setText(ekranaBilgiBastir());
            //oyun tahtasini yeniden ciziyorum
            repaint();
        }
    }

    //asker yerlestiriyorum
    public void askerYerlestir(Point mouse) {
        //mouse un tikladigi ulkeye oyuncunun hakkı oldugu kadar asker yerlestiriyorum
        for (Ulke c : oyuncular[sira].oyuncununUlkeleri) {
            if (c.sinirdaMi(mouse)) {
                c.askerSayisi++;
                yerlestirilecekAskerSayisi--;
            }
        }

        //yerlestirilecek asker sayisi bittiyse durumu degistiriyorum
        if (yerlestirilecekAskerSayisi == 0) {
            if (durum == Durum.BaslangicDurumu){
                sira++;//artik sira diger oyuncuya gecti
                if (sira == oyuncular.length) {
                    sira = 0;
                    yerlestirilecekBirlikleriGuncelle();//birlikleri guncelliyorum
                    sonrakiDurum();//sonraki duruma geciyorum
                } else {
                    yerlestirilecekAskerSayisiniHesapla();//oyuncu kac tane asker yerlestirebilir hesapliyorum
                }
            } else {
                sonrakiDurum();
            }
        }
    }

    //oyuncunun kac tane asker yerlestirebilir onu hesapliyorum
    public void yerlestirilecekAskerSayisiniHesapla() {
        yerlestirilecekAskerSayisi = 40 - oyuncular[sira].oyuncununUlkeleri.size() - (oyuncular.length - 2) * 5;
    }

    //yerlestirilecek asker sayisini guncelliyorum
    public void yerlestirilecekBirlikleriGuncelle() {
        yerlestirilecekAskerSayisi = Math.max(3, oyuncular[sira].oyuncununUlkeleri.size() / 3);
    }

    //sahip olunan ulkeyi sectirtiyorum eger secilen ulke sahip olunan ulkeyse sonraki duruma gecirtiyorum
    public void sahipOlunanUlkeyiSec(Point mouse) {
        for (Ulke c : oyuncular[sira].oyuncununUlkeleri) {
            if (c.sinirdaMi(mouse) && c.askerSayisi > 1) {//mouse oyuncunun ulkesi olucak ayrica asker sayisi 1 den fazla olucak
                seciliUlke = c;//secili ulkeyi degiskene atiyorum
                sonrakiDurum();
            }
        }
    }

    //dusman ulkeyi sectiriyorum
    public void dusmanUlkeSec(Point mouse) {
        //oyuncu saldiriyi yapacak olan ulkeyi secerse durumu degistirip oyuncunun tekrar saldiriyi yapacak ulkeyi secmesini sagliyorum
        if (seciliUlke.sinirdaMi(mouse)) {
            seciliUlke = null;//oyuncu yeni ulke secmeli o yuzden degiskeni null a esitliyorum
            durum = Durum.SaldiriyiYapacakUlkeSecmeDurumu;//durumu guncelliyorum
            return;
        }

        //secili ulkenin komsu ulkelerini geziyorum
        for (Ulke c : seciliUlke.komsuUlkeleri) {
            //saldiri yapacagi ulkeyi seciyorum sinirda mi ve oyuncunun kendi ulkesi degil mi diye
            if (c.sinirdaMi(mouse) && !oyuncular[sira].oyuncununUlkeleri.contains(c)) {
                seciliIkinciUlke = c;
                saldir(seciliUlke, seciliIkinciUlke);//saldiriyorum
                saldiriyiKontrolEt();//saldiriyi kontrol ediyorum
                //eger saldirma ve kontrol etme kisminda durum degistiyse sonraki duruma geciyorum
                if (durum == Durum.SaldirmaDurumu) {
                    sonrakiDurum();
                }
            }
        }
    }

    //gonderilen diziyi buyukten kucuke dogru siraliyorum
    public void diziyiSirala(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] > arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }

    public void saldir(Ulke saldiriYapan, Ulke savunmaYapan) {
        int[] saldirmaOrani = new int[3];
        int[] savunmaOrani = new int[2];

        //saldirma oranini hesapliyorum
        for (int i = 0; i < Math.min(saldirmaOrani.length, saldiriYapan.askerSayisi - 1); i++) {
            saldirmaOrani[i] = (int) Math.ceil(6 * Math.random());
        }

        //savunma oranini hesapliyorum
        for (int i = 0; i < Math.min(savunmaOrani.length, savunmaYapan.askerSayisi); i++) {
            savunmaOrani[i] = (int) Math.ceil(6 * Math.random());
        }
        //savunma ve saldirima oranini dizilerini buyukten kucuge siraliyorum
        diziyiSirala(saldirmaOrani);
        diziyiSirala(savunmaOrani);

        //eger saldirma oraninin en buyuk degeri savunma degerinden buyukse saldiri yapanin saldirisi basarili degilse savunma yapanin savunmasi basarili
        if (saldirmaOrani[0] > savunmaOrani[0]) {
            savunmaYapan.askerSayisi--;
        } else {
            saldiriYapan.askerSayisi--;
        }

        //ikinci orani kontrol ediyorum eger yine saldirma orani yuksekse saldiri gerceklesiyor
        if (saldirmaOrani[1] != 0 && savunmaOrani[1] != 0) {
            if (saldirmaOrani[1] > savunmaOrani[1]) {
                savunmaYapan.askerSayisi--;
            } else {
                saldiriYapan.askerSayisi--;
            }
        }

    }
    //saldiriyi kontrol ediyorum
    public void saldiriyiKontrolEt() {
        if (seciliUlke.askerSayisi == 1) {//eger saldiran ulkenin askeri bittiyse saldiriyi bitirip durumu degistiriyorum
            seciliUlke = null;
            seciliIkinciUlke = null;
            durum = Durum.SaldiriyiYapacakUlkeSecmeDurumu;
            return;
        }//eger saldirilan ulkenin asker sayisi bittiyse durumu guncelleyip feth etme islemini baslatiyorum
        if (seciliIkinciUlke.askerSayisi == 0) {
            durum = Durum.FetihSonrasiDurumu;
            fethEt();
        }
    }

    //saldirmaya devam ettiriyorum
    public void saldirmayaDevamEt(Point mouse) {
        //secili ulke sinirda mi diye kontrol ediyorum eger sinirda degilse durumu degistirip degiskenleri null yapiyorum
        if (seciliUlke.sinirdaMi(mouse)) {
            seciliUlke = null;
            seciliIkinciUlke = null;
            durum = Durum.SaldiriyiYapacakUlkeSecmeDurumu;
            return;
        }

        //saldirip saldiriyi kontrol ediyorum
        if (seciliIkinciUlke.sinirdaMi(mouse)) {
            saldir(seciliUlke, seciliIkinciUlke);
            saldiriyiKontrolEt();
        }

    }
    //sonraki oyuncuya geciyorum
    public void sonrakiOyuncu() {
        seciliUlke = null;
        seciliIkinciUlke = null;
        sira = (sira + 1) % 2;//sira atliyorum
        durum = Durum.AskerYerlestirmeDurumu;//durumu guncelliyorum
        yerlestirilecekBirlikleriGuncelle();//asker sayisini guncelliyorum
    }

    //o anki duruma gore sonraki duruma geciyorum
    public void sonrakiDurum() {

        if(durum == Durum.BaslangicDurumu){

            durum = Durum.AskerYerlestirmeDurumu;

        }else if(durum == Durum.AskerYerlestirmeDurumu){

            durum = Durum.SaldiriyiYapacakUlkeSecmeDurumu;

        }else if(durum == Durum.SaldiriyiYapacakUlkeSecmeDurumu){

            durum = Durum.SaldirmaDurumu;

        }else if(durum == Durum.SaldirmaDurumu){

            durum = Durum.SaldiriyaDevamEtmeDurumu;

        }else if(durum == Durum.SaldiriyaDevamEtmeDurumu){

            durum = Durum.FetihSonrasiDurumu;

        }else if(durum == Durum.FetihSonrasiDurumu){

            durum = Durum.SaldiriyiYapacakUlkeSecmeDurumu;

        }else if(durum == Durum.TransferYapacakUlkeSecmeDurumu){

            durum = Durum.TransferDurumu;

        }else if(durum == Durum.TransferDurumu){

            durum = Durum.TransfereDevamEtmeDurumu;

        }else if(durum == Durum.TransfereDevamEtmeDurumu){

            sonrakiOyuncu();

        }

    }

    //feth etme islemini yapiyorum
    public void fethEt() {
        Oyuncu dusman = oyuncular[(sira + 1) % 2]; //dusmani buluyorum

        //dusmandan ulkeyi cikartiyorum
        dusman.oyuncununUlkeleri.remove(seciliIkinciUlke);
        //oyuncuya ulkeyi ekliyorum
        oyuncular[sira].oyuncununUlkeleri.add(seciliIkinciUlke);

        //eger dusmanda ulke kalmadiysa oyuncuyu kaybettiriyorum
        if (dusman.oyuncununUlkeleri.isEmpty()) {
            dusman.kaybettiMi = true;
        }
        //oyun bitti mi diye kontrol ediyorum
        oyunBittimiKontrolEt();
        //artik askeri dagitmasi lazim
        //feth edilmis olan ulkenin asker sayisini 1 yapiyorum
        seciliIkinciUlke.askerSayisi = 1;
        yerlestirilecekAskerSayisi = seciliUlke.askerSayisi - 2;//yerlestirilmesi gereken asker sayisini aliyorum
        seciliUlke.askerSayisi = 1;//secili ulkenin asker sayisini 1 yapyırum
        //yerlestirilecek asker kalmamissa sonraki duruma geciyorum
        if (yerlestirilecekAskerSayisi == 0) {
            seciliUlke = null;
            seciliIkinciUlke = null;
            sonrakiDurum();
        }
    }

    //yeni asker yerlestiriyorum
    public void yeniUlkeyeAskerYerlestir(Point mouse) {
        if (seciliUlke.sinirdaMi(mouse)) {
            yerlestirilecekAskerSayisi--;
            seciliUlke.askerSayisi++;
        }
        if (seciliIkinciUlke.sinirdaMi(mouse)) {
            yerlestirilecekAskerSayisi--;
            seciliIkinciUlke.askerSayisi++;
        }
        //yerlestirilecek asker sayisi kalmamissa sonraki duruma geciyorum
        if (yerlestirilecekAskerSayisi == 0) {
            seciliUlke = null;
            seciliIkinciUlke = null;
            sonrakiDurum();
        }
    }

    //asker transfer yapacak ulkeyi seciyorum
    public void askerTransferEdilecekUlkeyiSec(Point mouse) {
        //eger transfer yapacak ulkeyi bir daha secerse durumu degistiriyorum
        if (seciliUlke.sinirdaMi(mouse)) {
            seciliUlke = null;
            durum = Durum.TransferYapacakUlkeSecmeDurumu;
            return;
        }
        //komsu ulkelerini kontrol ediyorum
        for (Ulke c : seciliUlke.komsuUlkeleri) {
            if (c.sinirdaMi(mouse) && oyuncular[sira].oyuncununUlkeleri.contains(c)) {//sinirda ve secili ulke oyuncunun ulkesi ise isleme devam ediyorum
                seciliIkinciUlke = c;
                askerTransferEt();
                sonrakiDurum();
            }
        }
    }

    //asker transferi yapiyorum
    public void askerTransferEt(){
        seciliUlke.askerSayisi--;
        seciliIkinciUlke.askerSayisi++;
        if (seciliUlke.askerSayisi == 1) {//eger asker sayisi 1 tane kalmissa sonraki duruma geciyorum
            sonrakiDurum();
        }
    }


    //hangi durumda oldugumu oyuncuya bildirmem gerek o yuzden alt kisma duruma gore bilgi bastiriyorum
    public String ekranaBilgiBastir() {
        String bilgiMesaji = "";

        if(durum == Durum.BaslangicDurumu || durum == Durum.AskerYerlestirmeDurumu){

            bilgiMesaji = "Asker yerlestir: kalan " + yerlestirilecekAskerSayisi;

        }else if(durum == Durum.SaldiriyiYapacakUlkeSecmeDurumu){

            bilgiMesaji = "Saldiri yapacak ulkeyi sec";

        }else if(durum == Durum.SaldirmaDurumu){

            bilgiMesaji = "Saldiracagin ulkeyi sec: ";

        }else if(durum == Durum.SaldiriyaDevamEtmeDurumu){

            bilgiMesaji = "Saldiriya devam etsin mi? ";

        }else if(durum == Durum.FetihSonrasiDurumu){

            bilgiMesaji = "Oyun.Ulke Fethedildi Asker sayisini paylastir: " + yerlestirilecekAskerSayisi + " tane asker kaldi";

        }else if(durum == Durum.TransferYapacakUlkeSecmeDurumu){

            bilgiMesaji = "Asker tasiyacagin ulkeyi sec";

        }else if(durum == Durum.TransferDurumu){

            bilgiMesaji = "Asker tasiyacagin ikinci ulkeyi sec";

        }else if(durum == Durum.TransfereDevamEtmeDurumu){

            bilgiMesaji = "Asker tasimaya devam ediliyor";

        }else if(durum == Durum.OyunuBitirmeDurumu){

            bilgiMesaji = "Tebrikler kazandin";

        }

        bilgiMesaji += " <Oyun.Oyuncu: "+ sira + ">";

        return bilgiMesaji;
    }

    //ileri butonuna tiklanirsa sonraki duruma geciyorum
    public void durum() {

        if(durum == Durum.SaldiriyiYapacakUlkeSecmeDurumu){

            durum = Durum.TransferYapacakUlkeSecmeDurumu;
            seciliUlke = null;

        }else if(durum == Durum.SaldirmaDurumu){

            durum = Durum.TransferYapacakUlkeSecmeDurumu;
            seciliUlke = null;
            seciliIkinciUlke = null;

        }else if(durum == Durum.SaldiriyaDevamEtmeDurumu){

            durum = Durum.SaldiriyiYapacakUlkeSecmeDurumu;
            seciliUlke = null;
            seciliIkinciUlke = null;

        }else if(durum == Durum.TransferYapacakUlkeSecmeDurumu || durum == Durum.TransferDurumu || durum == Durum.TransfereDevamEtmeDurumu){

            sonrakiOyuncu();

        }

        siraBilgisi.setText(ekranaBilgiBastir());
        repaint();
    }


    //tahtayi olusturuyorum
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(200, 255, 230));
        g.fillRect(0, 0, OYUNTAHTASI_GENISLIK, OYUNTAHTASI_YUKSEKLIK);
        for (int i = 0; i < oyuncular.length; i++) {
            Oyuncu oyuncu = oyuncular[i];
            for(Ulke ulke : oyuncu.oyuncununUlkeleri) {
                g.setColor(ulkeRenkleri[i]);
                if (ulke == seciliUlke || ulke == seciliIkinciUlke) {
                    ulke.chooseUlke();
                } else {
                    ulke.unchooseUlke();
                }
                ulke.Ciz(g);
            }
        }
        drawLines(g);
    }

    //tahtanin boyutunu ayarliyorum
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(OYUNTAHTASI_GENISLIK, OYUNTAHTASI_YUKSEKLIK);
    }
}
