import java.util.*;

/**
 * Class Selecta is a 'specalisation' class which will chose which program is run, server or client.
 * It will then run the respective classes depending on what the user enters
 */
public class Selecta {

  private static Scanner scan = new Scanner(System.in);

  public static void main(String args[]) {
    System.out.println("Enter S for server or C for Client");
    String specialisation = scan.nextLine();
    if (specialisation.equals("S")) {
      Selecta.runServer();
    } else {
      Selecta.runClient();
    }


  }

  private static void runServer() {
    Server server = new Server();
    server.setup(server.getPort());


  }

  private static void runClient() {

    Client client = new Client();
    client.connectToServer(client.getIPaddress(), client.getPort());
    client.sendMessages();
  }


}
