
package Client;

import Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Oyun.RiskOyun;


public class Client {

    RiskOyun riskOyun;

    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;

    public Client(RiskOyun riskOyun){
        this.riskOyun = riskOyun;
        listenMe = new Listen(this);
    }
    
    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            System.out.println("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            //--
            listenMe.start();
            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Stop() {
           try {
               if (Client.socket != null) {
                   Client.listenMe.stop();
                   Client.socket.close();
                   Client.sOutput.flush();
                   Client.sOutput.close();
                   Client.sInput.close();
               }
           } catch (IOException ex) {
               Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
           }

    }

    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
}
class Listen extends Thread {

    Client client;

    public Listen(Client c){
        client=c;
    }

    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            
            try {
                //mesaj gelmesini bloking olarak dinyelen komut
                Message received = (Message) (Client.sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre yapılacak işlemi ayır.
                switch (received.type) {
                    case StartGame:
                        break;
                    case SideChoose:
                        client.riskOyun.oyunTahtasi.sira = (int) received.content;
                        break;
                    case Move:
                        //client.game.MoveServer((ArrayList<Object>) received.content,client.game);
                        break;
                    case Next:
                        client.riskOyun.oyunTahtasi.ReadMessageFromOpponent(received.content);
                        break;

                }
            } catch (IOException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        }

    }
}
