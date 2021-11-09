import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.*; // Import javas networking library
import java.io.*; // Import javas input output librarys
import java.net.ServerSocket;

/**
 * @author sgmud
 *
 * Below is the main server class which contains the methods regarding the server application
 */


public class Server {

  private Scanner scan = new Scanner(System.in);
  public int indexer;
  public static ArrayList<PrintWriter> printWriteHolder = new ArrayList<PrintWriter>();
  public static ArrayList<String> nameHolder = new ArrayList<String>();

  private String message;


  private int Counter = 0;

  public int getPort() {
    System.out.println("What port do you want to run your server on?");
    int ipAddress = scan.nextInt();
    return ipAddress;
  }

  /**
   * This method acts as a first time setup by creating the server socket and LIstening for connections. It
   * also sets a listener thread off and a socket thread. There is only one listener thread that is centralised
   * whereas there are multiple socket threads for each client
   *
   * @param ipAddress
   *
   * @return
   */
  public void setup(int ipAddress) {
    try {
      System.out.println("Server Starting...");
      int Counter = 0;
      indexer = 0;
      ServerSocket sSocket = new ServerSocket(
          ipAddress); // Takes the port the user enters and starts a server socket with it
      Relay relay = new Relay(printWriteHolder, nameHolder, indexer);
      relay.start();

      while (true) {

        Counter++;
        Socket cSocket = sSocket.accept(); // When A client trys to connect to the server a socket is made
        BufferedReader input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
        PrintWriter output = new PrintWriter(cSocket.getOutputStream(), true);
        printWriteHolder.add(indexer, output);
        relay.relayUpdate(printWriteHolder, nameHolder);

        indexer++;

        System.out.println("Cleint Number:" + Counter + " Connected");
        captureMessages MsgCap = new captureMessages(cSocket, Counter, output, input);
        MsgCap.start();



      }


    } catch (IOException e) { // Catches the error as variable e
      System.out.println("ERROR");
      e.printStackTrace();// Prints error Location in code if there is an error

    }

  }

  /**
   * Class Relay is a thread that is called when server is set up, it listens for any messages coming in from the sockets,
   * Then sends these  messages to all clients connected as well as outputting the messages to the server console its self.
   *
   */
  public class Relay extends Thread {

    private ArrayList<PrintWriter> pHold;
    private ArrayList<String> nHold = new ArrayList<String>();
    public static String sMessage;
    private static int arraySize;



    public static void setMessage(String smessage){
      System.out.println(sMessage); // outputs prior message
      sMessage = smessage;
      System.out.println(sMessage); // outputs the message

    }

    public Relay(ArrayList printHolder, ArrayList nameHolder, int arraySize) {
      pHold = printHolder;
      nHold = nameHolder;

    }

    public void relayUpdate(ArrayList printHolder, ArrayList nameHolder) {
      pHold = printHolder;
      nHold = nameHolder;
      arraySize++;
    }

    public void run() {

      int loopWork = 0;
      int incr = 0;
      while (true) {

        System.out.print("");

        String message = this.sMessage;

        int arrayS = arraySize;



        if(message != null ) {

          for (int i = 0; i < arrayS; i++) {

            pHold.get(i).println(message);
          }


        }else{
          loopWork++;
        }
        this.sMessage = null;

      }
    }


  }

}












