package CAB302.Server;

import CAB302.Common.*;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
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
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            RuntimeSettings.Session = HibernateUtil.getHibernateSession();

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

        Session session = RuntimeSettings.Session;

        while(true) {
            System.out.println("Attempting to process trades");

            Trade buyTradeSelection = new Trade();
            buyTradeSelection.setTransactionType(TradeTransactionType.Buying);
            buyTradeSelection.setStatus(TradeStatus.InMarket);

            Trade sellTradeSelection = new Trade();
            sellTradeSelection.setTransactionType(TradeTransactionType.Selling);
            sellTradeSelection.setStatus(TradeStatus.InMarket);

            List<Trade> buyTrades = (List<Trade>)(List<?>)buyTradeSelection.list();
            List<Trade> sellTrades = (List<Trade>)(List<?>)sellTradeSelection.list();

            buyTrades.sort(Comparator.comparingDouble(Trade::getPrice));

                for (Trade buyTrade : buyTrades) {
                    List<Trade> availableSellTrades = sellTrades.stream().filter(x -> x.getAsset().id == x.getAsset().id).collect(Collectors.toList());

                    if (availableSellTrades != null) {

                        int quantityLeftToBuy = buyTrade.getQuantity();

                        sellTradeFinish:
                        for (Trade availableSellTrade : availableSellTrades) {
                            if (availableSellTrade.getPrice() <= buyTrade.getPrice()) {
                                int quantityToBuy = buyTrade.getQuantity();

                                if (buyTrade.getQuantity() > availableSellTrade.getQuantity()) {
                                    quantityToBuy = availableSellTrade.getQuantity();
                                }

                                Asset asset = new Asset();
                                asset.setAssetType(availableSellTrade.getAsset().getAssetType());
                                asset.setQuantity(quantityToBuy);
                                asset.setCreatedByUserID(buyTrade.getCreatedByUser());

                                Trade trade = new Trade();
                                trade.setQuantity(quantityToBuy);
                                trade.setAsset(asset);
                                trade.setPrice(buyTrade.getPrice());
                                trade.setStatus(TradeStatus.Filled);
                                trade.setCreatedDate(new Timestamp(System.currentTimeMillis()));

                                if ((availableSellTrade.getQuantity() - quantityToBuy) == 0) {
                                    availableSellTrade.setStatus(TradeStatus.Filled);
                                }

                                availableSellTrade.setQuantity(availableSellTrade.getQuantity() - quantityToBuy);

                                quantityLeftToBuy -= quantityToBuy;

                                if (quantityLeftToBuy == 0) {
                                    buyTrade.setStatus(TradeStatus.Filled);
                                }

                                session.save(trade);

                                session.save(asset);

                                session.update(availableSellTrade);

                                session.update(buyTrade);

                                session.getTransaction().commit();

                                if (quantityLeftToBuy == 0) {
                                    break sellTradeFinish;
                                }
                            }
                        }
                    }
                }

            try {
                sleep(2000);
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

                Session session = RuntimeSettings.Session;

                session.save(object);

                session.getTransaction().commit();

                break;

            case Update:
                session = RuntimeSettings.Session;

                session.update(object);

                session.getTransaction().commit();

                break;

            case Delete:
                session = RuntimeSettings.Session;

                session.remove(object);

                session.getTransaction().commit();

                break;
        }

        return null;
    }
}
