package CAB302.Server;

import CAB302.Common.ServerPackages.RuntimeSettings;

import java.io.Console;

/**
 * Class used to run the server side of the application.
 */
public class Main {

    /**
     * Method initialises a new server.
     * @param args
     */
    public static void main( String[] args )
    {
        Console console = System.console();

        ServerSettings settings = new ServerSettings();

        Server server = new Server(RuntimeSettings.Port);

        System.out.println("Starting up server");

        server.startServer();

        System.out.println("Server running");
    }
}
