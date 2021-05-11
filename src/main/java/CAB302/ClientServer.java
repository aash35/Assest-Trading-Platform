package CAB302;

public class ClientServer {
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
