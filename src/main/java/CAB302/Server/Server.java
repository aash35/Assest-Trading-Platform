package CAB302.Server;

import CAB302.Common.JsonPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Server extends Thread {

    private ServerSocket serverSocket;

    private int port;

    private boolean running = false;

    public Server(int port)
    {
        this.port = port;
    }

    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket(port);
            this.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stopServer()
    {
        running = false;
        this.interrupt();
    }

    @Override
    public void run()
    {
        running = true;

        while(running)
        {
            try
            {
                System.out.println("Listening for a connection");

                Socket socket = serverSocket.accept();

                RequestHandler requestHandler = new RequestHandler( socket );
                requestHandler.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class RequestHandler extends Thread {

    private Socket socket;

    RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Received a connection");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            out.flush();

            String data = in.readLine();

            processData(data);

            while (data != null && data.length() > 0) {
                data = in.readLine();

                processData(data);
            }

            // Close our connection
            in.close();
            out.close();
            socket.close();

            System.out.println("Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void processData(String data) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        JsonPayload jsonPayload = mapper.readValue(data, JsonPayload.class);

        //close the connection if we don't like the result or the checksum is empty
        if (jsonPayload == null || jsonPayload.getChecksum() == null || jsonPayload.getChecksum().length() == 0) {
            socket.close();
        }

        //need to add validation of the checksum

        switch (jsonPayload.getJsonPayloadType()) {
            case Buy:
                break;
            case Create:
                break;
            case Delete:
                break;
            case Update:
                break;
        }
    }
}
