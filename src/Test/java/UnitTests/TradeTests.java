package UnitTests;

import CAB302.Client.Client;
import CAB302.Client.ClientSettings;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Common.ServerPackages.RuntimeSettings;
import CAB302.Server.Server;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TradeTests {
    /**
     * Pre-Test class declaration
     */
    private static Client client;
    public static AssetType type;
    public static OrganisationalUnit OU;
    public static Trade trader;
    public static Asset asset;

    public static OrganisationalUnitTests org;

    public static AssetTypeTests assetTypeTests;

    public static AssetTests assetTests;


    @BeforeAll
    public static void before() throws IOException {
        ClientSettings clientSettings = new ClientSettings();

        Server server = new Server(RuntimeSettings.Port);

        server.startServer();

        System.out.println("Started Server");
        client = new Client();

        OrganisationalUnit ouTemp  = new OrganisationalUnit();
        ouTemp.setUnitName("Temp OU");
        ouTemp.setAvailableCredit(5000);

        PayloadRequest request = new PayloadRequest();
        request.setRequestPayloadType(RequestPayloadType.Create);
        request.setPayloadObject(ouTemp);

        PayloadResponse response = new Client().SendRequest(request);

        OU = (OrganisationalUnit)response.getPayloadObject();

        AssetType typeTemp = new AssetType();
        typeTemp.setName("Temp Asset Type");
        typeTemp.setDescription("Temp Asset Type");

        request = new PayloadRequest();
        request.setRequestPayloadType(RequestPayloadType.Create);
        request.setPayloadObject(typeTemp);

        response = new Client().SendRequest(request);

        type = (AssetType)response.getPayloadObject();

        Asset assetTemp = new Asset();
        assetTemp.setOrganisationalUnit(OU);
        assetTemp.setQuantity(1000);
        assetTemp.setAssetType(type);

        request = new PayloadRequest();
        request.setRequestPayloadType(RequestPayloadType.Create);
        request.setPayloadObject(assetTemp);

        response = new Client().SendRequest(request);

        asset = (Asset)response.getPayloadObject();
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */
    @Test
    @Order(1)
    public void createTrade() {
        Client client = new Client();

        Trade trade = new Trade();
        trade.setAssetType(type);
        trade.setOrganisationalUnit(OU);
        trade.setQuantity(10);
        trade.setPrice(10);
        trade.setTransactionType(TradeTransactionType.Buying);
        trade.setStatus(TradeStatus.InMarket);

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(trade);
        request.setRequestPayloadType(RequestPayloadType.Buy);

        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());
        trader = (Trade) response.getPayloadObject();
    }

    @Test
    @Order(2)
    public void updateTrade() {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(trader);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        trader = (Trade)response.getPayloadObject();

        int expected = 10;
        int actual = trader.getQuantity();

        Assert.assertEquals(expected,actual);

        trader.setQuantity(20);

        request = new PayloadRequest();
        request.setPayloadObject(trader);
        request.setRequestPayloadType(RequestPayloadType.Update);

        response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        trader = (Trade)response.getPayloadObject();

        expected = 20;
        actual = trader.getQuantity();
        Assert.assertEquals(expected, actual);
    }
    @Test
    @Order(3)
    public void listTrades() {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(trader);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        List<Trade> trades = (List<Trade>)(List<?>)response.getPayloadObject();

        Assert.assertNotNull(trades);

        trader = trades.stream().filter(x -> x.getQuantity() == 20).findFirst().orElse(null);

        Assert.assertNotNull(trader);
    }

    @Test
    @Order(4)
    public void deleteTrade() {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(trader);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        trader = (Trade) response.getPayloadObject();

        request = new PayloadRequest();
        request.setPayloadObject(trader);
        request.setRequestPayloadType(RequestPayloadType.Delete);

        response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNull(response.getPayloadObject());
    }


    @Test
    public void testCreation() {
        Trade testTrade = new Trade();
        TradeStatus status = TradeStatus.InMarket;
        OrganisationalUnit org = OU;
        AssetType assetType = type;
        int price = 50;
        TradeTransactionType transactionTypeTest = TradeTransactionType.Buying;
        Timestamp time = Timestamp.from(Instant.now());

        testTrade.setStatus(status);
        testTrade.setOrganisationalUnit(org);
        testTrade.setAssetType(assetType);
        testTrade.setPrice(price);
        testTrade.setTransactionType(transactionTypeTest);
        testTrade.setCreatedDate(time);

        Assert.assertEquals(status, testTrade.getStatus());
        Assert.assertEquals(org, testTrade.getOrganisationalUnit());
        Assert.assertEquals(assetType, testTrade.getAssetType());
        Assert.assertEquals(price, testTrade.getPrice());
        Assert.assertEquals(transactionTypeTest, testTrade.getTransactionType());
        Assert.assertEquals(time, testTrade.getCreatedDate());
    }

    @Test
    public void errorTrade() {

    }
    //TODO: coverage of the catch(ex)
    @Test
    public void catchExceptions() {

    }
}
