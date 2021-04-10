import java.awt.*;
import java.util.Set;

public class Ulke implements Comparable<Ulke>{ //kiyaslanabilir olmasi icin Comparable dan kalitim aliyorum

    Set<Ulke> komsuUlkeleri; //ulkenin komsularini burda tutuyorum

    public String ulkeAdi;

    public int xKordinati;//x kordinatindaki konumu
    public int yKordinati;//y kordinatindaki konumu
    public int genislik;//ulkenin genisligi
    public int yukseklik;//ulkenin yuksekligi
    public int askerSayisi;//ulkenin asker sayisi

    //harita uzerinden hangi ulkeye tiklanildigini bulmak icin seciliMi degiskeni olusturdum
    public boolean seciliMi;

    public Ulke(String ulkeAdi,int xKordinati,int yKordinati,int genislik,int yukseklik){
        this.ulkeAdi = ulkeAdi;
        this.xKordinati = xKordinati;
        this.yKordinati = yKordinati;
        this.genislik = genislik;
        this.yukseklik = yukseklik;
    }

    //ulkeyi seciyor
    public void chooseUlke(){
        this.seciliMi = true;
    }

    //ulke secimini kaldiriyor
    public void unchooseUlke(){
        this.seciliMi = false;
    }

    public void Ciz(Graphics g){
        if (seciliMi) {//eger seciliyse biraz daha koyu renge boyuyorum boylece kullanıcı hangisini sectigini gorebilir
            Color highlighted = g.getColor().darker();
            g.setColor(highlighted);
        }

        //ulkeyi ciziyorum
        g.fillRect(this.xKordinati,this.yKordinati, this.genislik, this.yukseklik);
        g.setColor(Color.BLACK);
        g.drawRect(this.xKordinati, this.yKordinati,this.genislik, this.yukseklik);

        //asker sayisini tam olarak orataya cizdirmek icin bu islemi yapiyorum
        //eger asker sayisi 10 dan kucukse 3 saga kaydir eger asker sayisi 10 dan buyukse 7 birim saga kaydir
        int offset = 3;
        if (this.askerSayisi >= 10) {
            offset = 7;
        }

        //ulkenin ortasina asker sayisini yazdiriyorum
        g.drawString("" + this.askerSayisi, this.xKordinati - offset + this.genislik / 2, this.yKordinati + 4 + this.yukseklik / 2);
    }

    public boolean sinirdaMi(Point mouse){
        int mouseX = (int) mouse.getX();
        int mouseY = (int) mouse.getY();

        //mouse tan gelen x y kordinati sinirin icersinde mi diye kontrol ediyorum
        if (mouseX < this.xKordinati + 1 || mouseX > this.xKordinati + this.genislik - 1) {
            return false;
        }
        if (mouseY < this.yKordinati + 1 || mouseY > this.yKordinati + this.yukseklik - 1) {
            return false;
        }
        return true;
    }

    //ulke adi parametre ile gonderilen ulkeyle uyusuyor mu kontrol ediyorum
    @Override
    public int compareTo(Ulke o) {
        return this.ulkeAdi.compareTo(o.ulkeAdi);
    }
}
