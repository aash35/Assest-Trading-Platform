package CAB302.Server;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Console;

public class Main {
    public static void main( String[] args )
    {
        Console console = System.console();

        Server server = new Server(8080);

        System.out.println("Starting up server");

        server.startServer();

        System.out.println("Server running");



        server.stopServer();
    }
}
