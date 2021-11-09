import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class captureMessages is a thread that is run with every new client that connects. It
 * uses the unique socket for each client and listens for any messages the client sends to the server,
 * relaying the message back to the central thread.
 */
public class captureMessages extends Thread {

  private String message = "";
  private BufferedReader input;
  private PrintWriter output;
  private Socket cSocket;
  private int numOfClients;
  private ArrayList<Socket> socketHolder = new ArrayList<Socket>();


  captureMessages(Socket socket, int Counter, PrintWriter printWriter, BufferedReader bufRead) { // constructor for CaptureMessages class
    cSocket = socket;
    numOfClients = Counter;
    output = printWriter;
    input = bufRead;

  }

  public void run() { // This is the method that runs as a thread
    while (true) {
      try {

        message = input.readLine();
        if (message != "") {
          System.out.println(message);
          Server.Relay.setMessage(message);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
}