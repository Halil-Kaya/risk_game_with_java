import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Zar extends JPanel {


    ZarPaun[] zarPaunArr = new ZarPaun[5];//zarin puanlarini tutan array
    BufferedImage[] beyazZarImages = new BufferedImage[7];//beyaz zarin resimlerini tutan array
    BufferedImage[] kirmiziZarImages = new BufferedImage[7];//kirmizi zarin resimlerini tutan array

    public Zar() {
        //img klosorunden beyaz ve kirmizi zarin resimlerini aliyorum
        try {
            for (int i = 0; i < beyazZarImages.length; i++) {
                String filename = "Zar" + i + ".png";
                beyazZarImages[i] = ImageIO.read(new File("img/beyaz" + filename));
                kirmiziZarImages[i] = ImageIO.read(new File("images/kirmizi" + filename));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(new GridLayout(3, 2, 5, 5));
        zarPaunArr[0] = new ZarPaun(true);
        zarPaunArr[1] = new ZarPaun(false);
        zarPaunArr[2] = new ZarPaun(true);
        zarPaunArr[3] = new ZarPaun(false);
        zarPaunArr[4] = new ZarPaun(true);

        for (int i = 0; i < zarPaunArr.length; i++) {
            this.add(zarPaunArr[i]);
        }

    }

    public class ZarPaun extends JComponent {
        private int puan;
        private boolean kirmiziMi;

        public ZarPaun(boolean kirmiziMi) {
            this.kirmiziMi = kirmiziMi;
        }

        public void update(int x) {
            puan = x;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (kirmiziMi) {//zarin rengine gore zari ciziyorum
                g.drawImage(kirmiziZarImages[puan], 0, 0, null);
            } else {
                g.drawImage(beyazZarImages[puan], 0, 0, null);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 50);
        }
    }

}
