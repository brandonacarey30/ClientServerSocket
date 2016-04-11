
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class InputOutput extends Thread {
	
  // Input Stream
  private InputStream input;

  // Output Stream 
  private OutputStream output;
  
  //Chatroom instance
  private Chatroom room;

  //Constructor
  public InputOutput(InputStream i, OutputStream o, Chatroom c) throws IOException {
    this.input = i;
    this.output = o;
    room = c;
  }

  ///Writes the input stream to the output
  public void run() {

    // Reader for the input 
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));

    // Writer for the output 
    PrintWriter outputWriter = new PrintWriter(output, true);
    
    // Buffer the line from the reader and write it to the output stream.
    try {
      String line;
      while ((line = inputReader.readLine()) != null) {
        outputWriter.println(line);
        room.updatePanel(line);
      }

      //Close the reader and writer
      inputReader.close();
      outputWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}