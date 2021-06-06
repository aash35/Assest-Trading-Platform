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



    @BeforeAll
    public static void before() {
        ClientSettings clientSettings = new ClientSettings();

        Server server = new Server(RuntimeSettings.Port);

        server.startServer();

        System.out.println("Started Server");
        client = new Client();

        OrganisationalUnitTests ouTests = new OrganisationalUnitTests();
        ouTests.createOrganisationalUnit();
        OU = ouTests.OU;

        AssetTypeTests assetTypeTests = new AssetTypeTests();
        assetTypeTests.createAssetType();
        type = assetTypeTests.type;
    }

    @AfterAll
    public static void cleanUp() {
        AssetTests assetTests = new AssetTests();
        assetTests.deleteAsset();

        AssetTypeTests assetTypeTests = new AssetTypeTests();
        assetTypeTests.deleteAssetType();
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

    //need to make all the error cases
    @Test
    public void errorTrade() {

    }
}
