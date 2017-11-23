import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//class chat extends Thread
//{
	//ClientT ca;
	//	public chat(ClientT o)
		//{
		//	ca=o;
	//}//

//}

class ClientT extends JFrame  implements ActionListener
{
      JTextField userText,t1;
      JTextArea chatWindow;
      ObjectOutputStream output;
      ObjectInputStream input;
      String message = "";
      String serverIP;
      Socket connection;
      JButton b1,b2;
	  JLabel lb1,lb2;
	  InetAddress adr;
	  JPanel p;
	  JScrollPane jsp;

     //constructor
     public ClientT ()
     {

         super("Chit Chat");
         //ObjectInputStream input;
		 setSize(300,400);
		 setVisible(true);
         serverIP = "127.0.0.1";
         lb1=new JLabel("   IP Address");
		 lb2=new JLabel("Message");
		 t1=new JTextField(20);
		 userText=new JTextField(20);
		 b1= new JButton("  Connect  ");
		 b2= new JButton("  Send  ");
		 chatWindow = new JTextArea();
		 p=new JPanel();
		 p.setLayout(new GridLayout(2,3));
		 p.add(lb1);
		 p.add(t1);
		 p.add(b1);
		 p.add(lb2);
		 p.add(userText);
		 p.add(b2);
         userText.setEditable(false);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         jsp=new JScrollPane(chatWindow,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 setLayout(new BorderLayout());
		 add(p,BorderLayout.NORTH);
		 add(jsp,BorderLayout.CENTER);
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


             //add(new JScrollPane(chatWindow), BorderLayout.CENTER);

				try
						{

							//cr=new cha(this);
							//cr.start();
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(null,ex.getMessage());
		}

      }

      public ClientT (InetAddress adr1)
      {

	 	 super("Chit Chat");
         //ObjectInputStream input;
		 setSize(300,400);
		 setVisible(true);
         serverIP = "127.0.0.1";
         lb1=new JLabel("   IP Address");
		 lb2=new JLabel("Message");
		 t1=new JTextField(20);
		 userText=new JTextField(20);
		 b1= new JButton("  Connect  ");
		 b2= new JButton("  Send  ");
		 chatWindow = new JTextArea();
		 p=new JPanel();
		 p.setLayout(new GridLayout(2,3));
		 p.add(lb1);
		 p.add(t1);
		 p.add(b1);
		 p.add(lb2);
		 p.add(userText);
		 p.add(b2);
         userText.setEditable(true);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         jsp=new JScrollPane(chatWindow,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 setLayout(new BorderLayout());
		 add(p,BorderLayout.NORTH);
		 add(jsp,BorderLayout.CENTER);
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


             //add(new JScrollPane(chatWindow), BorderLayout.CENTER);

				try
						{

							//cr=new cha(this);
							//cr.start();
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(null,ex.getMessage());
		}



		this.startRunning();

	  }



      public void actionPerformed(ActionEvent ae)
	  	{
	  		if(ae.getSource()==b1)
	  		{
				try
				{

	  				adr=InetAddress.getByName(t1.getText());
				}
				catch(UnknownHostException e)
				{
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
	  				//serverIP = t1.getText();
	  				//startRunning();
	  				 ClientT cc;
			 cc = new ClientT(adr);
	         //cc.startRunning();
	  				//showMessage("1231");


	  		}
	  		if(ae.getSource()==b2)
	  		{
	  			try
	  			{
	  				sendMessage(userText.getText());

	  			}
	  			catch(Exception ex)
	  			{
	  				JOptionPane.showMessageDialog(null,ex.getMessage());
	  			}
	  		}
	}

		//connect to server
			     public void startRunning()
			     //public void run()
			     {showMessage("1231");

			         try
			         {
						showMessage("1231      ");
			           connectToServer();
			           setupStreams();
			           whileChatting();

			         }
			         catch(EOFException eofException)
			          {
			               showMessage("\n Client terminated connection");
			           }
			           catch(IOException ioException)
			           {
			               ioException.printStackTrace();
			            }finally
			            {
			               //closeCrap();
			            }
			      }

			// connect to server

			     public void connectToServer() throws IOException
			     {
			          showMessage("Attempting connection...\n");
			          connection = new Socket(InetAddress.getByName(serverIP),2222);
			          showMessage("Connected to :"+connection.getInetAddress().getHostName());


			     }

			//setup streams to send and receive messages
			      public void setupStreams() throws IOException
			      {
			          output = new ObjectOutputStream(connection.getOutputStream());
			          output.flush();
			          input = new ObjectInputStream(connection.getInputStream());
			          showMessage("\n Streams setup ho gayi hai ;) \n");

			       }

			  //while chatting with server
			   public void whileChatting() throws IOException
			    {
			        //ableToType(true);
			        do
			        {
			           //try
			            {
			              message = (String) input.readLine();
			              //if(message!="null")
			               showMessage("\n"+ message);

			             }
			           //  catch(ClassNotFoundException classNotfoundException)
			              {
			           //      showMessage("\n I dont know that object type");

			              }



			        }while(true);
			        //while(!message.equals("S - END"));

			     }
			   //close the streams and sockets
			     public void closeCrap()
			     {
			   /*    showMessage("\n closing crap down...");
			       ableToType(false);
			       try
			      {
			       //  output.close();
			       //  input.close();
			       //  connection.close();


			       }
			       catch(IOException ioException)
			        {
			           ioException.printStackTrace();
			        }*/

			      }

			  //send messages to the server
			      public void sendMessage(String message)
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
			   public void ableToType(final boolean tof)
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
	        cc1 = new ClientT();
	         //cc.startRunning();

	      }





}
