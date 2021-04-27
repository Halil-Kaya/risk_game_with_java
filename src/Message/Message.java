
package Message;

public class Message implements java.io.Serializable {
    public static enum Message_Type {None, Name, Disconnect,OpponentConnected, Text, Move, StartGame, SideChoose}
    
    public Message_Type type;
    public Object content;
    public Message(Message_Type t)
    {
        this.type=t;
    }

}
