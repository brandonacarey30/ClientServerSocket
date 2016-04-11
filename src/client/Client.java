package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

// import server.Chatroom;  // DUNCAN: Moved to client package.

public class Client {

  // Socket for the server
  private Socket servSoc;

  //Reads input from the server
  private InputOutput readsInput;

  //Printwriter to write out
  private PrintWriter out;
    
  //Buffered reader to read in
  private BufferedReader in;
    
  //The ip of the server
  public static String ipNum;

  //The chatroom of this instance
  private Chatroom c;

  //Constructor
  public Client(String serverName, int portNumber) {

    // Attempt to connect to the server given the args command line prompt of the specific IP address.
    try {
      servSoc = new Socket(ipNum, 1518);

      // Have the Chatroom store reference to the Client - so it can use it.
      c = new Chatroom(this);
      
      //Create an input/output thread
      readsInput = new InputOutput(servSoc.getInputStream(), System.out, c);
      readsInput.start();

      //Printwriter writes to output stram
      out = new PrintWriter(servSoc.getOutputStream(), true);

      // Keeps the client runnning until its killed
      boolean socketConnectionStatus = false;
      while(!socketConnectionStatus);

      // Closes the socket once the connection status is set to ended
      servSoc.close();

    } catch (UnknownHostException e) {
      System.err.println("We are sorry, we can't find the server.");
      System.exit(1);

    } catch (IOException e) {
      System.err.println("Couldn't get I/O from the server.");
      System.exit(1);
    }

  }

  // Send a message to the Server via the Socket
  public void sendMessage(String message) {
	System.err.println("DEBUG: sendMessage: " + message);
	if (out != null)
	    out.println(message);
	else
	    System.err.println("Error (Client): No Outputstream to send message.");
    }
    
  //Main
  public static void main(String[] args) {
    ipNum = args[0];
    Client clientApplication = new Client("server", 1518);
  }

}
