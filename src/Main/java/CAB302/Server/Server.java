package CAB302.Server;

import CAB302.Common.*;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import com.google.gson.Gson;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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

                TradeProcessor tradeProcessor = new TradeProcessor();
                tradeProcessor.start();

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

class TradeProcessor extends Thread {

    @Override
    public void run() {

        Session session = HibernateUtil.getHibernateSession();

        List<Asset> assets = (List<Asset>)(List<?>)new Asset().list();

        List<Trade> trades = (List<Trade>)(List<?>)new Asset().list();



        while(true) {
            System.out.println("Attempting to process trades");

            try {
                sleep(5000);
            } catch (InterruptedException e) {
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
        var object = jsonPayload.getPayloadObject();

        switch (jsonPayload.getJsonPayloadType()) {
            case Buy:

                break;

            case Sell:

                break;

            case Get:
                if (object instanceof iGet)
                {
                    var result = ((iGet)object).get();

                    JsonPayloadResponse response = new JsonPayloadResponse();

                    response.setPayloadObject(result);

                    String jsonString = response.getJsonString();

                    return jsonString;
                }

                break;

            case List:
                if (object instanceof iList)
                {
                    var result = ((iList)object).list();

                    JsonPayloadResponse response = new JsonPayloadResponse();

                    response.setPayloadObject(result);

                    String jsonString = response.getJsonString();

                    return null;
                }
                break;

            case Create:

                Session session = HibernateUtil.getHibernateSession();

                session.beginTransaction();

                session.save(object);

                session.getTransaction().commit();

                session.close();

                break;

            case Update:
                session = HibernateUtil.getHibernateSession();

                session.beginTransaction();

                session.update(object);

                session.getTransaction().commit();

                session.close();
                break;

            case Delete:
                session = HibernateUtil.getHibernateSession();

                session.beginTransaction();

                session.remove(object);

                session.getTransaction().commit();

                session.close();
                break;
        }

        return null;
    }
}
