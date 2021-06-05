package CAB302.Server;

import CAB302.Common.*;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class acts as the server side thread of the application.
 */
public class Server extends Thread {

    private ServerSocket serverSocket;

    private int port;

    private boolean running = false;

    /**
     * Constructs a server object listening on a given port.
     * @param port the port the server will be active on.
     */
    public Server(int port)
    {
        this.port = port;
    }

    /**
     * Method opens a Hibernate session and opens a new server socket on the instance's port.
     */
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

    /**
     * Interrupts and stops the instance's processes.
     */
    public void stopServer()
    {
        running = false;
        this.interrupt();
    }

    /**
     * Waits for an incoming communication from the client side application, then constructs a request handler
     * when a new connection to the client is established.
     */
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
                //tradeProcessor.start();

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

/**
 * Class regularly checks the database for compatible trades.
 */
class TradeProcessor extends Thread {

    /**
     * Runs an infinite loop of checking the database for compatible trades at regular intervals.
     */
    @Override
    public void run() {

        RuntimeSettings.Session = HibernateUtil.getHibernateSession();

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

                session.refresh(buyTrade);

                List<Trade> availableSellTrades = sellTrades != null ? sellTrades.stream().filter(x -> x.getAssetType().id == buyTrade.getAssetType().id).collect(Collectors.toList()) : null;

                if (availableSellTrades != null) {

                    int quantityLeftToBuy = buyTrade.getQuantity();

                    sellTradeFinish:
                    for (Trade availableSellTrade : availableSellTrades) {

                        HibernateUtil.openOrGetTransaction();

                        session.refresh(availableSellTrade);

                        if (availableSellTrade.getPrice() <= buyTrade.getPrice()) {
                            int quantityToBuy = buyTrade.getQuantity();

                            if (buyTrade.getQuantity() > availableSellTrade.getQuantity()) {
                                quantityToBuy = availableSellTrade.getQuantity();
                            }

                            Asset asset = new Asset();
                            asset.setAssetType(availableSellTrade.getAssetType());

                            asset = (Asset)asset.get();

                            if (asset == null) {
                                asset.setAssetType(availableSellTrade.getAssetType());

                                session.save(asset);
                            }
                            else {
                                asset.setAssetType(availableSellTrade.getAssetType());

                                asset.setQuantity(asset.getQuantity() + quantityToBuy);
                                asset.setOrganisationalUnit(buyTrade.getOrganisationalUnit());

                                session.update(asset);
                            }


                            if ((availableSellTrade.getQuantity() - quantityToBuy) == 0) {
                                availableSellTrade.setStatus(TradeStatus.Filled);
                            }

                            Integer newQty = availableSellTrade.getQuantity() - quantityToBuy;

                            availableSellTrade.setQuantity(newQty);

                            quantityLeftToBuy -= quantityToBuy;

                            if (quantityLeftToBuy == 0) {
                                buyTrade.setStatus(TradeStatus.Filled);
                            }
                            else {
                                buyTrade.setQuantity(quantityLeftToBuy);

                                Trade trade = new Trade();
                                trade.setQuantity(quantityToBuy);
                                trade.setAssetType(buyTrade.getAssetType());
                                trade.setPrice(buyTrade.getPrice());
                                trade.setStatus(TradeStatus.Filled);
                                trade.setTransactionType(TradeTransactionType.Buying);
                                trade.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                                trade.setOrganisationalUnit(buyTrade.getOrganisationalUnit());

                                session.save(trade);
                            }

                            Integer creditsToAdd = quantityToBuy * buyTrade.getPrice();

                            OrganisationalUnit ou = availableSellTrade.getOrganisationalUnit();

                            Integer newCredit = ou.getAvailableCredit() + creditsToAdd;

                            ou.setAvailableCredit(newCredit);

                            session.update(availableSellTrade);

                            session.update(ou);

                            session.update(buyTrade);

                            session.getTransaction().commit();

                            System.out.println("Trade Processed!");

                            Notification notificationSell = new Notification();
                            notificationSell.setNotificationMessage("Sold " + availableSellTrade.getAssetType().getName());
                            notificationSell.setOuID(availableSellTrade.getOrganisationalUnit().id);

                            RuntimeSettings.notifications.add(notificationSell);

                            Notification notificationBuy = new Notification();
                            notificationBuy.setNotificationMessage("Bought " + availableSellTrade.getAssetType().getName());
                            notificationBuy.setOuID(availableSellTrade.getOrganisationalUnit().id);

                            RuntimeSettings.notifications.add(notificationBuy);

                            if (quantityLeftToBuy == 0) {
                                //break sellTradeFinish;
                            }
                        }
                    }
                }
            }

            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * Class is called when a new communication from the client is received by the server. Contains methods to
 * receive and process requests from the client.
 */
class RequestHandler extends Thread {

    private Socket socket;

    /**
     * Constructs a request handler for a given client communication.
     * @param socket the client connection.
     */
    RequestHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * The request handler will read in the client object stream then call the processData method, before
     * sending a response to the client and closing the connection.
     */
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

    /**
     * Method processes the request received from the client and interfaces with the database. The actions taken
     * are determined by the request contents.
     * @param requestPayload the request received from the client.
     * @return a response to be sent to the client.
     * @throws IOException
     */
    protected PayloadResponse processData(PayloadRequest requestPayload) throws IOException {

        //close the connection if we don't like the result or the checksum is empty
        if (requestPayload == null || requestPayload.getChecksum() == null || requestPayload.getChecksum().length() == 0) {
            socket.close();
            return null;
        }

        //need to add validation of the checksum
        var object = requestPayload.getPayloadObject();

        RuntimeSettings.Session.clear();

        switch (requestPayload.getRequestPayloadType()) {
            case Buy:
            case Sell:
                Trade buyTrade = (Trade)object;

                if (buyTrade.getOrganisationalUnit().getAvailableCredit() >= buyTrade.getPrice() * buyTrade.getQuantity()) {
                    Session session = RuntimeSettings.Session;

                    HibernateUtil.openOrGetTransaction();

                    session.save(object);

                    session.getTransaction().commit();

                    PayloadResponse response = new PayloadResponse();

                    response.setPayloadObject(buyTrade);

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

                HibernateUtil.openOrGetTransaction();

                session.save(object);

                session.getTransaction().commit();

                break;

            case Update:
                session = RuntimeSettings.Session;

                HibernateUtil.openOrGetTransaction();

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

                HibernateUtil.openOrGetTransaction();

                session.flush();
                session.clear();

                session.delete(object);

                session.getTransaction().commit();

                response = new PayloadResponse();

                response.setPayloadObject(object);

                return response;

            case Notification:

                if (object instanceof User) {
                    List<Notification> notifications = RuntimeSettings.notifications.stream().filter(x -> x.getOuID() == ((User)object).id).collect(Collectors.toList());

                    response = new PayloadResponse();

                    List<String> notificationMessages = new ArrayList<String>();

                    for (Notification notificationMessage : notifications) {
                        notificationMessages.add(notificationMessage.getNotificationMessage());

                        RuntimeSettings.notifications.remove(notificationMessage);
                    }

                    response.setPayloadObject(notificationMessages);

                    return response;
                }

                return null;
        }

        return null;
    }
}
