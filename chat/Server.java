import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame
{
   private JTextField userText;  //where we type the msg
   private JTextArea chatWindow;  //where conversation is displayed
   private ObjectOutputStream output;
 //there are two main streams bw 2 users.. ouput and input
   private ObjectInputStream input;
   private ServerSocket server;
   private Socket connection;

//socket is a connection b/w two computers.

   //constructor
   public Server()
   {
       super("Necros Messenger(S)");
       userText = new JTextField();
       userText.setEditable(false);
       userText.addActionListener(
             new ActionListener()
             {
                 public void actionPerformed(ActionEvent event)
                 {
                      sendMessage(event.getActionCommand());
// event.getActionCommand()  returns the command string associated with this action.
                      userText.setText("fbxxxxxxxxxx");

                  }

              }


        );
       add(userText, BorderLayout.NORTH);
       chatWindow = new JTextArea();
       add(new JScrollPane(chatWindow));
       setSize(300,150);
       setVisible(true);


    }

    //set up and run the server
    public void startRunning()
    {
       try
       {
          server = new ServerSocket(2222, 100);
//here 3128 is port number(where do you want to connect) & 100 is backlog number i.e. how many peoples can wait to access this messenger
          while(true)
          {
              try
              {
                 //connect and have conversation
                 waitForConnection();
                 setupStreams();
                 whileChatting();

               }catch(EOFException eofException)
               {
                   showMessage("\n Server ended the connection! ");
               }
               finally
               {
                   closeCrap();
                }
          }

        }catch(IOException ioException)
         {
            ioException.printStackTrace();
          }

     }

   //wait for Connection, then display connection information
    private void waitForConnection() throws IOException
    {
         showMessage("Waiting for someone to connect...\n");
         connection = server.accept();
         showMessage("Now connected to "+connection.getInetAddress().getHostName());

     }

     //get streams to send and receive data
     private void setupStreams() throws IOException
     {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup! \n");


      }

      //during the chat conversion
      private void whileChatting() throws IOException
      {
         String message = "You are now connected! ";
         sendMessage(message);
        ableToType(true);
         do{
             try
             {
               message = (String) input.readObject();
               showMessage("\n" + message);

              }catch(ClassNotFoundException classNotFoundException)
               {
                   showMessage("\n idk wtf that user send!");
                }

           }while(!message.equals("CLIENT - END")); //not understood

       }

      //close streams and sockets after you are done chatting
       private void closeCrap()
       {
            showMessage("\n Closing connections. . .\n");
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

       //send a message to client
       private void sendMessage(String message)
      {
       try{
             output.writeObject("SERVER - "+message);
             output.flush();
             showMessage("\nSERVER -"+ message);


           }catch(IOException ioException)
           {
                chatWindow.append("\n ERROR: I CANT SEND THAT MESSAGE");

            }
        }
       //updates chatWindow
       private void showMessage(final String text)
       {
        //since we dont want to create a new GUI everytime, we just want to update the GUI by adding next message
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

     //let the type stuff into their box
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
