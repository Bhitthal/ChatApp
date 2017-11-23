import javax.swing.JFrame;

public class ServerTest
{
   public static void main(String[] args)
   {
       Server sachan = new Server();
       sachan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       sachan.startRunning();
    }


}
