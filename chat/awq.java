
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ClientT extends JFrame //implements ActionListener
{
     private JTextField userText,ip;
     private JTextArea chatWindow;
     private JButton b1,b2;
	 private JLabel lb1,lb2;
	 private JPanel p;
	 private JScrollPane jsp;

     private ObjectOutputStream output;
     private ObjectInputStream input;
     private String message = "";
     private String serverIP;
     private Socket connection;

     //constructor
     public ClientT (String host)
     {
         super("Client");
         serverIP = host;
         userText =new JTextField();
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
            chatWindow = new JTextArea();
            add(new JScrollPane(chatWindow), BorderLayout.CENTER);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(300,150);
            setVisible(true);


      }
  /*    public ClientT ()
	       {

	           super("Chit Chat");
			   setSize(300,400);
			   setVisible(true);
			   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			   		lb1=new JLabel("   IP Address");
			   		lb2=new JLabel("Message");
			   		ip=new JTextField(20);
			   		userText=new JTextField(20);
			   		b1= new JButton("  Connect  ");
			   		b2= new JButton("  Send  ");
			   		chatWindow = new JTextArea();
			   		p=new JPanel();

			   		p.setLayout(new GridLayout(2,3));
			   		p.add(lb1);
			   		p.add(ip);
			   		p.add(b1);
			   		p.add(lb2);
			   		p.add(userText);
			   		p.add(b2);
			   		jsp=new JScrollPane(chatWindow,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			   		setLayout(new BorderLayout());
			   		add(p,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);

	          serverIP = "127.0.0.1";
	           userText =new JTextField();
	           userText.setEditable(false);
	           b1.addActionListener(this);
		       b2.addActionListener(this);
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


				//ClientT cc1;
                //cc1 = new ClientT("127.0.0.1");
                //System.out.println("213gj");
                this.startRunning();
                //System.out.println("213");
	        }

    	public void actionPerformed(ActionEvent ae)
		{
			if(ae.getSource()==b1)
			{
				//serverIP = userText.getText();
				//this.startRunning();
			}
			if(ae.getSource()==b2)
			{
				sendMessage(ae.getActionCommand());
	            userText.setText("");
			}
	}*/
     //connect to server
     public void startRunning()
     {
		 System.out.println("213");
         try
         {System.out.println("1");
           connectToServer();System.out.println("2");
           setupStreams();System.out.println("3");
           whileChatting();System.out.println("4");

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

     private void connectToServer() throws IOException
     {
          showMessage("Attempting connection...\n");
          connection = new Socket(InetAddress.getByName(serverIP),2222);
          showMessage("Connected to :"+connection.getInetAddress().getHostName());


     }

//setup streams to send and receive messages
      private void setupStreams() throws IOException
      {
		  System.out.println("44");
          output = new ObjectOutputStream(connection.getOutputStream());System.out.println("45");
          output.flush();System.out.println("46");
          input = new ObjectInputStream(connection.getInputStream());System.out.println("47");
          showMessage("\n Streams setup ho gayi hai ;) \n");System.out.println("48");

       }

  //while chatting with server
   private void whileChatting() throws IOException
    {
        //ableToType(true);
        do
        {
           // try
            {
              message = (String) input.readLine();
               showMessage("\n"+ message);

             }//catch(ClassNotFoundException classNotfoundException)
              {
              //   showMessage("\n I dont know that object type");

              }



        }while(!message.equals("S - END"));

     }
   //close the streams and sockets
     private void closeCrap()
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
      private void sendMessage(String message)
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
    public void showMessage(final String m)
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
   private void ableToType(final boolean tof)
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


     public static void main(String[] args)
	 {
         ClientT cc1;
         //cc1 = new ClientT();
         cc1 = new ClientT("127.0.0.1");
	     cc1.startRunning();

	 }


}
