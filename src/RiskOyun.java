import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RiskOyun implements Runnable {

    public void run() {

        JFrame frame = new JFrame("Risk_Oyunu");

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        oyunuBaslat(frame);
    }

    public static void oyunuBaslat(JFrame frame) {

        JPanel turnPanel = new JPanel();
        JLabel turnInfo = new JLabel();
        turnPanel.add(turnInfo);

        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(120, 0));

        OyunTahtasi oyunTahtasi = new OyunTahtasi(turnInfo);
        frame.add(oyunTahtasi, BorderLayout.CENTER);

        JButton next = new JButton("Ilerle");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oyunTahtasi.durum();
            }
        });
        statusPanel.add(next);

        frame.add(statusPanel, BorderLayout.WEST);
        frame.add(turnPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RiskOyun());
    }

}
