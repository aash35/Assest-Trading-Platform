package CAB302.Server;

import CAB302.Common.RuntimeSettings;

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

        ServerSettings settings = new ServerSettings();

        Server server = new Server(RuntimeSettings.Port);

        System.out.println("Starting up server");

        server.startServer();

        System.out.println("Server running");
    }
}
