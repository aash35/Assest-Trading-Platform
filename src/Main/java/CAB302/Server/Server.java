package CAB302.Server;

import CAB302.Common.*;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
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
            /*System.out.println("Attempting to process trades");

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

                    session.refresh(buyTrade);

                    *//*List<Trade> availableSellTrades = sellTrades.stream().filter(x -> x.getAsset().id == x.getAsset().id).collect(Collectors.toList());

                    if (availableSellTrades != null) {

                        int quantityLeftToBuy = buyTrade.getQuantity();

                        sellTradeFinish:
                        for (Trade availableSellTrade : availableSellTrades) {

                            session.refresh(availableSellTrade);

                            if (availableSellTrade.getPrice() <= buyTrade.getPrice()) {
                                int quantityToBuy = buyTrade.getQuantity();

                                if (buyTrade.getQuantity() > availableSellTrade.getQuantity()) {
                                    quantityToBuy = availableSellTrade.getQuantity();
                                }

                                Asset asset = new Asset();
                                asset.setAssetType(availableSellTrade.getAssetType());
                                asset.setQuantity(quantityToBuy);
                                asset.setCreatedByUserID(buyTrade.getCreatedByUser());

                                Trade trade = new Trade();
                                trade.setQuantity(quantityToBuy);
                                trade.setAssetType(buyTrade.getAssetType());
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
                    }*//*
                }*/

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

            InputStream inputStream = socket.getInputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            PayloadRequest request = (PayloadRequest)objectInputStream.readObject();

            PayloadResponse response = processData(request);

            OutputStream outputStream = socket.getOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(response);

            socket.close();

            System.out.println("Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected PayloadResponse processData(PayloadRequest requestPayload) throws IOException {

        //close the connection if we don't like the result or the checksum is empty
        if (requestPayload == null || requestPayload.getChecksum() == null || requestPayload.getChecksum().length() == 0) {
            socket.close();
            return null;
        }

        //need to add validation of the checksum
        var object = requestPayload.getPayloadObject();

        switch (requestPayload.getRequestPayloadType()) {
            case Buy:

                Trade buyTrade = (Trade)object;

                if (buyTrade.getOrganisationalUnit().getAvailableCredit() >= buyTrade.getPrice()) {
                    Session session = RuntimeSettings.Session;

                    try {
                        Transaction transaction = session.getTransaction();

                        if (transaction.getStatus() == TransactionStatus.NOT_ACTIVE) {
                            session.beginTransaction();
                        }
                    }
                    catch (Exception ex) {
                        session.beginTransaction();
                    }

                    session.save(object);

                    session.getTransaction().commit();

                    buyTrade = (Trade)buyTrade.get();

                    PayloadResponse response = new PayloadResponse();

                    response.setPayloadObject(buyTrade);

                    return response;
                }
                else {
                    return null;
                }

            case Sell:

                Trade sellTrade = (Trade)object;

                int assetTypeId = sellTrade.getAssetType().id;

                Asset assetOfType = sellTrade.getCreatedByUser().getAssets().stream().filter(asset -> asset.getAssetType().id == assetTypeId).findFirst().orElse(null);

                if (assetOfType.getQuantity() >= sellTrade.getQuantity()) {
                    Session session = RuntimeSettings.Session;

                    try {
                        Transaction transaction = session.getTransaction();

                        if (transaction.getStatus() == TransactionStatus.NOT_ACTIVE) {
                            session.beginTransaction();
                        }
                    }
                    catch (Exception ex) {
                        session.beginTransaction();
                    }

                    session.save(object);

                    session.getTransaction().commit();

                    sellTrade = (Trade)sellTrade.get();

                    PayloadResponse response = new PayloadResponse();

                    response.setPayloadObject(sellTrade);

                    return response;
                }
                else {
                    return null;
                }

            case Get:
                if (object instanceof iGet)
                {
                    var result = ((iGet)object).get();

                    PayloadResponse response = new PayloadResponse();

                    response.setPayloadObject(result);

                    return response;
                }

                break;

            case List:
                if (object instanceof iList)
                {
                    var result = ((iList)object).list();

                    PayloadResponse response = new PayloadResponse();

                    response.setPayloadObject(result);

                    return response;
                }
                break;

            case Create:

                Session session = RuntimeSettings.Session;

                try {
                    Transaction transaction = session.getTransaction();

                    if (transaction.getStatus() == TransactionStatus.NOT_ACTIVE) {
                        session.beginTransaction();
                    }
                }
                catch (Exception ex) {
                    session.beginTransaction();
                }

                session.save(object);

                session.getTransaction().commit();

                break;

            case Update:
                session = RuntimeSettings.Session;

                try {
                    Transaction transaction = session.getTransaction();

                    if (transaction.getStatus() == TransactionStatus.NOT_ACTIVE) {
                        session.beginTransaction();
                    }
                }
                catch (Exception ex) {
                    session.beginTransaction();
                }
                session.flush();
                session.clear();
                session.update(object);

                session.getTransaction().commit();
                session.refresh(object);

                PayloadResponse response = new PayloadResponse();

                response.setPayloadObject(object);

                return response;

            case Delete:
                session = RuntimeSettings.Session;

                try {
                    Transaction transaction = session.getTransaction();

                    if (transaction.getStatus() == TransactionStatus.NOT_ACTIVE) {
                        session.beginTransaction();
                    }
                }
                catch (Exception ex) {
                    session.beginTransaction();
                }

                session.remove(object);

                session.getTransaction().commit();

                break;
        }

        return null;
    }
}
