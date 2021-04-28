package Oyun;

import Client.Client;
import Message.Message;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RiskOyun implements Runnable {

    public OyunTahtasi oyunTahtasi;
    Client c;

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
        riskOyun.c= new Client(riskOyun);

        JPanel turnPanel = new JPanel();
        JLabel turnInfo = new JLabel();
        turnPanel.add(turnInfo);

        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(120, 0));

        riskOyun.oyunTahtasi = new OyunTahtasi(turnInfo,c);
        frame.add(riskOyun.oyunTahtasi, BorderLayout.CENTER);

        JButton btnIlerle = new JButton("Ilerle");

        btnIlerle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                riskOyun.oyunTahtasi.durum();
            }
        });
        statusPanel.add(btnIlerle);

        frame.add(btnIlerle, BorderLayout.EAST);
        frame.add(turnPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);


        riskOyun.c.Start("127.0.0.1",5000);

        Message m = new Message(Message.Message_Type.SideChoose);
        m.content =  riskOyun.oyunTahtasi.sira;
        riskOyun.c.Send(m);



    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RiskOyun());
    }

}
