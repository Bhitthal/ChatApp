import javax.swing.JFrame;

public class ClientTest
{

    public static void main(String[] args)
    {
       Clientc cc;
       cc = new Clientc("127.0.0.1");


        //cc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cc.startRunning();

     }

 }
