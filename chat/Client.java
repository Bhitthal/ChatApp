//Created by:
//Amandeep Sachan


import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Client extends JFrame
{
     private JTextField userText;
     private JTextArea chatWindow;
     private ObjectOutputStream output;
     private ObjectInputStream input;
     private String message = "";
     private String serverIP;
     private Socket connection;
	 private PrintStream os = null;
     //constructor
     public Client(String host)
     {
         super("client");
         serverIP = host;
         userText =new JTextField();
         userText.setEditable(false);
      //   userText.addActionListener(
       //       new ActionListener()
       //        {
       //            public void actionPerformed(ActionEvent event)
        //           {
       //                sendMessage(event.getActionCommand());
          //             userText.setText("");
///
          //          }
//
      //          }

     //        );
     userText.addActionListener(this);
            add(userText, BorderLayout.NORTH);
             chatWindow = new JTextArea();
             add(new JScrollPane(chatWindow), BorderLayout.CENTER);
            setSize(300,150);
            setVisible(true);


      }

     //connect to server
     public void startRunning()
     {
         try
         {
			 System.out.println("0");
           connectToServer();
           System.out.println("1");
           whileChatting();
           System.out.println("2");

         }catch(EOFException eofException)
          {
               showMessage("\n Client terminated connection");
           }catch(IOException ioException)
           {
               ioException.printStackTrace();
            }//finally
            {
           //    closeCrap();
            }
      }

// connect to server

     private void connectToServer() throws IOException
     {
          showMessage("Attempting connection...\n");
          connection = new Socket(InetAddress.getByName(serverIP),2222);
          showMessage("Connected to :"+connection.getInetAddress().getHostName());


		  output = new ObjectOutputStream(connection.getOutputStream());
		  //os = new PrintStream(connection.getOutputStream());
		  output.flush();
		  input = new ObjectInputStream(connection.getInputStream());
		  showMessage("\n Streams setup ho gayi hai ;) \n");


     }


  //while chatting with server
   private void whileChatting() throws IOException
    {
        ableToType(true);
        do
        {
            try
            {
              message = (String) input.readObject();
              showMessage("\n"+ message);

             }catch(ClassNotFoundException classNotfoundException)
              {
                 showMessage("\n I dont know that object type");

              }



        }while(!message.equals("SERVER - END"));

     }
   //close the streams and sockets
     private void closeCrap()
     {
       showMessage("\n closing crap down...");
       ableToType(true);
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
          output.writeObject("CLIENT - "+message);
          output.flush();
          showMessage("\nCLIENT - "+message);

          }catch(IOException ioException)
          {
              chatWindow.append("\n something messed up sending message hoss!");
          }

       }
// change/update chat window
    private void showMessage(final String m)
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
}
