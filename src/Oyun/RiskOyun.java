package Oyun;

import Client.Client;
import Message.Message;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RiskOyun implements Runnable {

    public OyunTahtasi oyunTahtasi = null;

    public void run() {

        JFrame frame = new JFrame("Risk_Oyunu");

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        oyunuBaslat(frame);
    }

    public void oyunuBaslat(JFrame frame) {

        RiskOyun riskOyun = new RiskOyun();

        JPanel turnPanel = new JPanel();
        JLabel turnInfo = new JLabel();
        turnPanel.add(turnInfo);

        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(120, 0));

        oyunTahtasi = new OyunTahtasi(turnInfo);
        frame.add(oyunTahtasi, BorderLayout.CENTER);

        JButton btnIlerle = new JButton("Ilerle");

        btnIlerle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oyunTahtasi.durum();
            }
        });
        statusPanel.add(btnIlerle);

        frame.add(btnIlerle, BorderLayout.EAST);
        frame.add(turnPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        Client c = new Client(riskOyun);
        c.Start("127.0.0.1",5000);

        Message m = new Message(Message.Message_Type.SideChoose);
        m.content =  oyunTahtasi.sira;
        c.Send(m);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RiskOyun());
    }

}
