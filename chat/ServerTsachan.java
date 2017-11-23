import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
//brendon1234

class ServerTsachan extends JFrame implements ActionListener
{
	public static JTextField userText;
	public static JTextArea chatWindow;
	ObjectOutputStream output;
	ObjectInputStream input;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	public static final int maxClientsCount = 10;
    public static final clientThread[] threads = new clientThread[maxClientsCount];
	public ServerTsachan()
    {
		super("server");
		userText = new JTextField();
		userText.setEditable(true);
		/*userText.addActionListener(
		new ActionListener()
		{
		public void actionPerformed(ActionEvent event)
			{
			sendMessage(event.getActionCommand());
			userText.setText("");
		    }
		 }
		    );*/
		userText.addActionListener(this);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300,150);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public ServerTsachan(String s)






	{}





          public void actionPerformed(ActionEvent ae)
	{
               String str = ae.getActionCommand();
               showMessage("S: "+str+"\n");
               for (int i = 0; i < maxClientsCount; i++)
               {
				   System.out.println(i);
                    if (threads[i] != null)
                    {
                          threads[i].sendMessage(str);
                     }
               }

	        userText.setText("");
        }


    public static void showMessage(final String text)
          {
                SwingUtilities.invokeLater(
                    new Runnable()
                    {
                        public void run()
                        {
                            chatWindow.append(text);
                         }
                     }

                   );

         }

	/* public void sendMessage(String message)
	  		      {System.out.println("12");
	  		       try
	  		       {while(true){
					   for (int i = 0; i < maxClientsCount; i++)
					         {
					           if (threads[i] != null )
					           {
					             threads[i].output.writeObject("SERVER - "+message);
						          output.flush();
						          showMessage("\nSERVER -"+ message);

					             }
       }


	  		           }}
	  		           catch(IOException ioException)
	  		           {
	  		                chatWindow.append("\n ERROR: I CANT SEND THAT MESSAGE");

	  		            }
          }*/

   public static void main(String[] args)
   {


		ServerTsachan s = new ServerTsachan();

		   try
		   {
		        serverSocket = new ServerSocket(2222);
		   }
		   catch (IOException e)
		   {
		        System.out.println(e);
   		   }

   		    while (true)
   		    {
		         try
		         {
					showMessage("\n\nWaiting for someone to connect...\n\n");
		           clientSocket = serverSocket.accept();
		           showMessage("\nNow connected to "+clientSocket.getInetAddress().getHostName());
		           int i = 0;
		           for (i = 0; i < maxClientsCount; i++)
		           {
		             if (threads[i] == null)
		             {
		               (threads[i] = new clientThread(clientSocket, threads)).start();
		               break;
		             }
		           }
		           if (i == maxClientsCount)
		           {
					   showMessage("\nServer too busy. Try later.\n");
		             //PrintStream os = new PrintStream(clientSocket.getOutputStream());
		             //os.println("Server too busy. Try later.");
		             //os.close();
		             clientSocket.close();
		           }
		         }
		         catch (IOException e)
		         {
		           System.out.println(e);
		         }
		     }




   }




}




class clientThread extends Thread
{

  ObjectInputStream is = null;
  ObjectOutputStream output;
  PrintStream os = null;
  Socket clientSocket = null;
   clientThread[] threads;
   int maxClientsCount;
//ServerTsachan s = new ServerTsachan();

  public clientThread(Socket clientSocket, clientThread[] threads)
  {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
    //System.out.println("contrucor call success");
  }



public void showMessage(final String text)
          {
                SwingUtilities.invokeLater(
                    new Runnable()
                    {
                        public void run()
                        {
                            ServerTsachan.chatWindow.append(text);
                         }
                     }

                   );

         }

  public void sendMessage(String message)
  		      {
  		       try
  		       {
  		             output.writeObject("S - "+message);
  		             //output.flush();
  		              this.showMessage("\nSERVER -"+ message);


  		           }
  		           catch(IOException ioException)
  		           {
  		                ServerTsachan.chatWindow.append("\n ERROR: I CANT SEND THAT MESSAGE");

  		            }
          }




  public void run()
  {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;
	//System.out.println("run startes");
    try
    {
      /*
       * Create input and output streams for this client.
       */
      is = new ObjectInputStream(clientSocket.getInputStream());
      //os = new PrintStream(clientSocket.getOutputStream());
      output = new ObjectOutputStream(clientSocket.getOutputStream());
      //os.println("Enter your name.");
      //output.writeObject("Enter Your Name");
      sendMessage("\nEnter Your Name\n");
      String name = (String) is.readObject();   //s.userText.getText(); //is.readLine().trim();
      //os.println("Hello " + name
         // + " to our chat room.\nTo leave enter /quit in a new line");
         sendMessage("Hello " + name + " to our chat room.\nTo leave enter /END in a new line\n");
      this.showMessage("\n** A new user " + name + " entered the chat room !!! **\n");
      for (int i = 0; i < maxClientsCount; i++)
      {
        if (threads[i] != null && threads[i] != this )
        {
          //threads[i].os.println("*** A new user " + name
          //    + " entered the chat room !!! ***");
          threads[i].sendMessage("** A new user " + name + " entered the chat room !!! **\n");
          }
       }
       //System.out.println("1");
      while (true)
      {
		  //System.out.println("1");
        String line = (String) is.readObject();
        showMessage("\n"+name+ ": " + line);
        if (line.startsWith("C - /END"))
        {
          break;
        }
        //System.out.println("2");
        for (int i = 0; i < maxClientsCount; i++)
        {
          if (threads[i] != null && threads[i] != this)
          {
            //threads[i].os.println("<" + name + "&gr; " + line);
            threads[i].sendMessage("<" + name + ":: " + line);
          }
        }
      }
      //System.out.println("3");
      for (int i = 0; i < maxClientsCount; i++)
      {
        if (threads[i] != null && threads[i] != this)
        {
          //threads[i].os.println("*** The user " + name
          //    + " is leaving the chat room !!! ***");
              threads[i].sendMessage("*** The user " + name
              + " is leaving the chat room !!! ***\n");
        }
      }
       //System.out.println("4");
      //os.println("*** Bye " + name + " ***");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      for (int i = 0; i < maxClientsCount; i++)
      {
        if (threads[i] == this)
        {
          threads[i] = null;
        }
      }

      /*
       * Close the output stream, close the input stream, close the socket.
       */
      //is.close();
      //os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
    catch(ClassNotFoundException classNotFoundException)
               {
                   showMessage("\n idk wtf that user send!");
                }
  }
}

