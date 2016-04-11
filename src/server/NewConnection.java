package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class NewConnection extends Thread {
	
    // Writers
    FileWriter file = null;
    BufferedWriter writer = null;
	
    // Connections
    private static List<NewConnection> connectedUsers = new LinkedList<NewConnection>();

    // Socket info
    private Socket clientSide;
    private ServerSocket serverSide;
  
    // Name of User
    String username;

    // Stream information
    private PrintStream printSocket = null;
    private BufferedReader socketBufferedReader = null;

    // Constructor
    public NewConnection(ServerSocket server, Socket client) {
	super("NewConnection");
	connectedUsers.add(this);
	this.serverSide = server;
	this.clientSide = client;
    
	// room = new Chatroom();
    }

    // Get the connections 
    public void run() {
	System.err.println("DEBUG: NewConnection, new connection started.");
      
	// Get the different streams to read and write
	try {
	    printSocket = new PrintStream(clientSide.getOutputStream());
	    socketBufferedReader = new BufferedReader(new InputStreamReader(clientSide.getInputStream()));
	} catch (IOException e) {
	}
    
	// Holder variable.
	String inputLine;

	username = null;  // DUNCAN: Communication not allowed until name is set
    
	//This is where the connection writes and sends through the socket
	try {
	    System.err.println("DEBUG: NewConnection, reading in from the socket.");
	    while ((inputLine = socketBufferedReader.readLine()) != null) { 
		System.err.println("DEBUG: NewConnection, received message: " + inputLine);
		if(inputLine.contains("/q") == true) {
		    alertAllUsers(" ","has disconnected");
		    this.interrupt();
		    connectedUsers.remove(this);
		    break;
		}
		else if (inputLine.startsWith("/join") == true) {
		    if (username == null) {
			// Client has joined 
			username = inputLine.substring(6);
			alertAllUsers(" ", "has joined.");
		    } else {
			System.err.println("NewConnection: Can't (re)join.  Username already registered.");
			System.err.println("             : " + inputLine);
		    }
		}
		else { 
		    try {
			file = new FileWriter("output.txt", true); //This is where it writes out to the log
			writer = new BufferedWriter(file);
			writer.write(username + ":" + inputLine);
			writer.newLine();
			writer.close();
		    }
		    catch (IOException e) { }
    				 
		    //Send to the users
		    alertAllUsers(": ", inputLine);
		}
	    }
	} catch (IOException e) { }
    }

    // Tells all users who sent the specific message.
    public void alertUsers(String userID, String hasConnected, String message) {
	this.printSocket.println(userID + hasConnected + message);
    }

    // Sends actual message for all the users to see.
    public void alertAllUsers(String hasConnected, String message) {
	// No message allowed to be sent until username has been registered
	if (username == null) {
	    System.err.println("NewConnection: Can't transmit.  Username not registered.");
	    System.err.println("             : " + message);
	    return;
	}

	System.err.println("DEBUG: Transmitting to all users: " + username + hasConnected + message);
	for(NewConnection ct : connectedUsers) {
	    ct.alertUsers(username, hasConnected, message);
	}
    }

}
