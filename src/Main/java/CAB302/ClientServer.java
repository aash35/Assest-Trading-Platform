package CAB302;

/**
 * Class used to run the complete application. Initialises the client and server threads.
 */
public class ClientServer {

    /**
     * Method runs the application, starting the client and server sides on separate threads.
     * @param args command line arguments, unused by the application.
     */
    public static void main(String[] args) {

        Thread serverThread = new Thread() {
            public void run() {
                CAB302.Server.Main.main(args);
            }
        };

        serverThread.start();

        Thread clientThread = new Thread() {
            public void run() {
                CAB302.Client.Main.main(args);
            }
        };

        clientThread.start();
    }
}
