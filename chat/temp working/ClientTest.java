import javax.swing.JFrame;

public class ClientTest
{
    public static void main(String[] args)
    {
       Clientc ravi;
       ravi = new Clientc("127.0.0.1");
  // change this ip address to ip adress of server
        ravi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ravi.startRunning();

     }

 }
