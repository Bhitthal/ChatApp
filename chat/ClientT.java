
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ClientT extends JFrame implements ActionListener
{
     private static JTextField userText;
     public static JTextArea chatWindow;
     private static ObjectOutputStream output;
     private static ObjectInputStream input;
     private static String message = "";
     private static String serverIP;
     private static Socket connection;
     private static JButton b1;

     //constructor
     public ClientT (String host)
     {
         super("chit chat");
         serverIP = host;
         userText =new JTextField();
         b1 = new JButton();
         userText.setEditable(false);
         userText.addActionListener(
              new ActionListener()
               {
                   public void actionPerformed(ActionEvent event)
                   {
                       sendMessage(event.getActionCommand());
                       userText.setText("");

                    }

                }

             );
            add(userText, BorderLayout.NORTH);
            add(b1, BorderLayout.SOUTH);
            b1.addActionListener(this);
             chatWindow = new JTextArea();
             add(new JScrollPane(chatWindow), BorderLayout.CENTER);
            setSize(300,150);
            setVisible(true);
			//startRunning();

      }

		public ClientT ()
		     {
		         super("Client123");
		         serverIP = "127.0.0.1";
		         userText =new JTextField();
		         b1 = new JButton();
		         userText.setEditable(false);
		         userText.addActionListener(
		              new ActionListener()
		               {
		                   public void actionPerformed(ActionEvent event)
		                   {
		                       sendMessage(event.getActionCommand());
		                       userText.setText("");

		                    }

		                }

		             );
		            add(userText, BorderLayout.NORTH);
		            add(b1, BorderLayout.SOUTH);
		            b1.addActionListener(this);
		             chatWindow = new JTextArea();
		             add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		            setSize(300,150);
		            setVisible(true);
					//startRunning();

      }
      public  void actionPerformed(ActionEvent ae)

	  {
		if(ae.getSource()==b1)
		{
			//startRunning();
			Clientc cr=new Clientc("127.0.0.1");
			cr.startRunning();
		}
	  }

     //connect to server
     public static void startRunning()
     {
         try
         {
           connectToServer();
           setupStreams();
           whileChatting();

         }catch(EOFException eofException)
          {
               showMessage("\n Client terminated connection");
           }catch(IOException ioException)
           {
               ioException.printStackTrace();
            }finally
            {
               closeCrap();
            }
      }

// connect to server

     public static void connectToServer() throws IOException
     {
          showMessage("Attempting connection...\n");
          connection = new Socket(InetAddress.getByName(serverIP),2222);
          showMessage("Connected to :"+connection.getInetAddress().getHostName());


     }

//setup streams to send and receive messages
      public static void setupStreams() throws IOException
      {
          output = new ObjectOutputStream(connection.getOutputStream());
          output.flush();
          input = new ObjectInputStream(connection.getInputStream());
          showMessage("\n Streams setup ho gayi hai ;) \n");

       }

  //while chatting with server
   public static void whileChatting() throws IOException
    {
        ableToType(true);
        do
        {
            try
            {
              message = (String) input.readObject();
              showMessage("\n"+ message);

             }

        	catch(ClassNotFoundException classNotfoundException)
              {
                showMessage("\n I dont know that object type");

              }



        }while(!message.equals("S - END"));

     }
   //close the streams and sockets
     public static void closeCrap()
     {
       showMessage("\n closing crap down...");
       ableToType(false);
       try
      {
         output.close();
         input.close();
         connection.close();


       }catch(IOException ioException)
        {
           ioException.printStackTrace();
        }

      }

  //send messages to the server
      public static void sendMessage(String message)
      {
         try
         {
          output.writeObject("C - "+message);
          output.flush();
          showMessage("\nC - "+message);

          }catch(IOException ioException)
          {
              chatWindow.append("\n something messed up sending message hoss!");
          }

       }
// change/update chat window
    public static void showMessage(final String m)
    {
//since we dont want to create a new GUI everytime, we just want to update the GUI by adding next message
         SwingUtilities.invokeLater(
             new Runnable()
             {
               public void run()
               {
                   chatWindow.append(m);
                 }

             }
          );

     }




// gives user permission to type crap into the text box
   private static void ableToType(final boolean tof)
    {
        SwingUtilities.invokeLater(
             new Runnable()
             {
               public void run()
               {
                   userText.setEditable(tof);
                 }

             }
          );

     }

     public static void main(String [] str)
     {

		 ClientT cc;
		 cc = new ClientT();
		 //startRunning();
	 }
}


























class chatread extends Thread
{
	ClientT ca;
	private ObjectOutputStream output;
	     private ObjectInputStream input;
	     private String message = "";
	     private String serverIP;
	     private Socket connection;

	public chatread(ClientT o)
	{
		ca=o;
	}
	public void run()
	{

		     {
		         try
		         {
		           ca.connectToServer();
		           ca.setupStreams();
		           ca.whileChatting();

		         }catch(EOFException eofException)
		          {
		               ca.showMessage("\n Client terminated connection");
		           }catch(IOException ioException)
		           {
		               ioException.printStackTrace();
		            }finally
		            {
		               ca.closeCrap();
		            }
		      }
	}

		/*// connect to server

		     private   void connectToServer() throws IOException
		     {
		          showMessage("Attempting connection...\n");
		          connection = new Socket(InetAddress.getByName(serverIP),2222);
		          showMessage("Connected to :"+connection.getInetAddress().getHostName());


		     }

		//setup streams to send and receive messages
		      private    void setupStreams() throws IOException
		      {
		          output = new ObjectOutputStream(connection.getOutputStream());
		          output.flush();
		          input = new ObjectInputStream(connection.getInputStream());
		          showMessage("\n Streams setup ho gayi hai ;) \n");

		       }

		  //while chatting with server
		   private   void whileChatting() throws IOException
		    {
		        //ableToType(true);
		        do
		        {
		            try
		            {
		              message = (String) input.readObject();
		              showMessage("\n"+ message);

		             }

		        	catch(ClassNotFoundException classNotfoundException)
		              {
		                 showMessage("\n I dont know that object type");

		              }



		        }while(!message.equals("S - END"));

		     }
		   //close the streams and sockets
		     private   void closeCrap()
		     {
		       showMessage("\n closing crap down...");
		       //ableToType(false);
		       try
		      {
		         output.close();
		         input.close();
		         connection.close();


		       }catch(IOException ioException)
		        {
		           ioException.printStackTrace();
		        }

		      }

		  //send messages to the server
		      private   void sendMessage(String message)
		      {
		         try
		         {
		          output.writeObject("C - "+message);
		          output.flush();
		          showMessage("\nC - "+message);

		          }catch(IOException ioException)
		          {
		              ca.chatWindow.append("\n something messed up sending message hoss!");
		          }

		       }
		// change/update chat window
		    public   void showMessage(final String m)
		    {
		//since we dont want to create a new GUI everytime, we just want to update the GUI by adding next message
		         SwingUtilities.invokeLater(
		             new Runnable()
		             {
		               public void run()
		               {
		                   ca.chatWindow.append(m);
		                 }

		             }
		          );

		     }*/

}