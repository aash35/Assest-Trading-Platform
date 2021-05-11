package CAB302.Server;

import CAB302.Common.BaseObject;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.JsonPayloadRequest;
import CAB302.Common.Interfaces.*;
import CAB302.Common.JsonPayloadResponse;
import com.google.gson.Gson;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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

            String result = processData(data);

            out.println(result);

            out.flush();

            data = null;

            // Close our connection
            in.close();
            out.close();
            socket.close();

            System.out.println("Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String processData(String data) throws IOException {

        Gson g = new Gson();

        JsonPayloadRequest jsonPayload = null;

        try {
            jsonPayload = g.fromJson(data, JsonPayloadRequest.class);
        } catch (Exception ex) { }

        //close the connection if we don't like the result or the checksum is empty
        if (jsonPayload == null || jsonPayload.getChecksum() == null || jsonPayload.getChecksum().length() == 0) {
            socket.close();
            return null;
        }

        //need to add validation of the checksum

        switch (jsonPayload.getJsonPayloadType()) {
            case Buy:
                break;

            case Sell:
                break;

            case Get:
                var objectGet = jsonPayload.getPayloadObject();

                if (objectGet instanceof iGet)
                {
                    var result = ((iGet)objectGet).get();

                    JsonPayloadResponse response = new JsonPayloadResponse();

                    response.setPayloadObject(result);

                    String jsonString = response.getJsonString();

                    return jsonString;
                }

                break;

            case List:
                break;

            case Create:
                var objectCreate = jsonPayload.getPayloadObject();

                Session session = HibernateUtil.getHibernateSession();

                session.beginTransaction();

                session.save(objectCreate);

                session.getTransaction().commit();

                session.close();

                break;

            case Update:
                break;

            case Delete:
                break;
        }

        return null;
    }
}
