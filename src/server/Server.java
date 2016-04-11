package server;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
  // Creates a new ServerSocket called servSoc
  private ServerSocket servSoc;
  private List<NewConnection> connectedServer;

  //Constructor
  public Server(Integer portNum) {

    // Sets up the server on the class desired port (1518). We then print out the server status.
    try {
      servSoc = new ServerSocket(portNum);
      connectedServer = new ArrayList<NewConnection>();
      servInfo();

    } catch (IOException e) {
      System.out.println("Sorry!! Server would not run on " + String.valueOf(portNum));
      System.exit(-10);
    }

    //Create the log file called "output.txt"
	try {
		FileWriter file = new FileWriter("output.txt");
	}
	 catch (IOException e) { }
    
    // Check to see if there are connections on the port. 
    boolean connectionCheck = false;
    while(!connectionCheck) {

      // Wait for connections and thread if successful connection made.
      try {
        Socket socket = servSoc.accept();
        if(socket.isConnected()) {
          NewConnection NewConnection = new NewConnection(servSoc, socket);
          NewConnection.start();
          connectedServer.add(NewConnection);
        }

      } catch (Exception e) {
        System.out.println("Sorry, but the connection failed on: " + String.valueOf(portNum));
        System.exit(-10);
      }
    }

  }

  //Gets server information
  private void servInfo() {
    String check = "Server is now running on IP " + String.valueOf(servSoc.getInetAddress().getHostName()) + " on port " + String.valueOf(servSoc.getLocalPort());
    System.out.println(check);
  }

  //Main
  public static void main(String[] args) {
    Server server = new Server(1518);
  }

}