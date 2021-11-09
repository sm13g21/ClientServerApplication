import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Below is the client class which deals with all methods regarding the client application
 */
public class Client {

  private Scanner scan = new Scanner(System.in);
  private PrintWriter output;
  private BufferedReader input;
  private Socket socket;


  public String getIPaddress() { // gets ip address
    System.out.println("Whats the IP address of the Server you wish to connect to?");
    String ipAddress = scan.nextLine();
    return ipAddress;
  }

  public int getPort() { // gets port
    System.out.println("Please enter the port the server is running on");
    int ipAddress = scan.nextInt();
    return ipAddress;
  }

  /**
   * This method acts as a first time setup by connecting the server and initilising variables. It
   * also sets the listner thred off.
   *
   * @param ipAddress
   * @param port
   * @return
   */
  public Socket connectToServer(String ipAddress, int port) {
    InetAddress address;
    try {
      address = InetAddress.getByName(getIPaddress());
      socket = new Socket(address, port);
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output = new PrintWriter(socket.getOutputStream(), true);
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(
          new TimerTask() {
            public void run() {
              listner();
            }
          }
          , 0, 1000);
      return socket;
    } catch (UnknownHostException e) {
      e.printStackTrace();
      System.out.println("Could not get IP");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void sendMessages() {
    System.out.println("Please type to send messages!");
    while (true) {

      output.println(scan.nextLine());

    }
  }

  public void listner() {
    String message = "";
    try {
      message = input.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!message.equals("")) {
      System.out.println(message);
    }

  }


}
