package UnitTests;
import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Exceptions.IllegalTradeException;

import static org.junit.jupiter.api.Assertions.*;

import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Server.Server;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;


public class UnitTests {

    /**
     * Pre-Test class declaration
     */
    AssetType type;
    OrganisationalUnit OU;
    User user;
    Asset asset;


    @BeforeAll
    public static void before() {
        Server server = new Server(8080);

        server.startServer();

        System.out.println("Started Server");
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */
    @BeforeEach @Test
    public void tradeTestData() {
        type = new AssetType();
        type.setName("Potatoes");
        type.setDescription("Many potatoes");

        OU = new OrganisationalUnit();
        OU.setUnitName("Unit A");
        OU.setAvailableCredit(100);

        user = new User();
        user.setUsername("User");
        user.setHashedPassword(SHA256HashHelper.generateHashedString("password"));
        user.setOrganisationalUnit(OU);
        user.setAccountRoleType(AccountTypeRole.Standard);

        asset = new Asset();
        asset.setAssetType(type);
        asset.setQuantity(50);
        asset.setCreatedByUserID(user);
    }

    /**
     * Test 1: Construct a new AssetType object
     */
    @Test
    public void newAssetTypeTest() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(type);

        request.setRequestPayloadType(RequestPayloadType.Create);

        PayloadResponse response = client.SendRequest(request);

        assertNull(response.getPayloadObject());
    }

    /**
     * Test 2: Construct a new Organisational Unit object
     */
    @Test
    public void newOrganisationalUnit() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(OU);
        request.setRequestPayloadType(RequestPayloadType.Create);

        PayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }


    /**
     * Test 3: Construct a new User object
     */
    @Test
    public void newUser() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(user);
        request.setRequestPayloadType(RequestPayloadType.Create);

        PayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 4: Construct a new Asset object
     */
    @Test
    public void newAsset() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.Create);

        PayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 5: Construct a new legal buy type trade
     */
    @Test
    public void newLegalBuy() throws IOException {
        Trade buyTrade = new Trade();

        buyTrade.setAssetType(type);
        buyTrade.setQuantity(10);
        buyTrade.setPrice(5);
        buyTrade.setCreatedByUser(user);
        buyTrade.setCreatedDate(Timestamp.from(Instant.now()));
        buyTrade.setTransactionType(TradeTransactionType.Buying);
        buyTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(buyTrade);
        request.setRequestPayloadType(RequestPayloadType.Buy);

        PayloadResponse response = client.SendRequest(request);
        // I assume we'd still be expecting a null response from this one. - Chris
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 6: Construct a new legal sell type trade.
     */
    @Test
    public void newLegalSell() throws IOException {
        Trade sellTrade = new Trade();

        sellTrade.setAssetType(type);
        sellTrade.setQuantity(10);
        sellTrade.setPrice(10);
        sellTrade.setCreatedByUser(user);
        sellTrade.setCreatedDate(Timestamp.from(Instant.now()));
        sellTrade.setTransactionType(TradeTransactionType.Selling);
        sellTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(sellTrade);
        request.setRequestPayloadType(RequestPayloadType.Sell);

        PayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 7: Construct an illegal buy type trade, ensure that processing this trade returns an error.
     */
    @Test
    public void newIllegalBuy() {
        Trade buyTrade = new Trade();

        buyTrade.setAssetType(type);
        buyTrade.setQuantity(10);
        buyTrade.setPrice(12);
        buyTrade.setCreatedByUser(user);
        buyTrade.setCreatedDate(Timestamp.from(Instant.now()));
        buyTrade.setTransactionType(TradeTransactionType.Buying);
        buyTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(buyTrade);
        request.setRequestPayloadType(RequestPayloadType.Buy);

        assertThrows(IllegalTradeException.class, () ->{
            PayloadResponse response = client.SendRequest(request);
        });
    }

    /**
     * Test 8: Construct an illegal buy type trade, ensure that processing this trade returns an error.
     */
    @Test
    public void newIllegalSell() {
        Trade sellTrade = new Trade();

        sellTrade.setAssetType(type);
        sellTrade.setQuantity(60);
        sellTrade.setPrice(10);
        sellTrade.setCreatedByUser(user);
        sellTrade.setCreatedDate(Timestamp.from(Instant.now()));
        sellTrade.setTransactionType(TradeTransactionType.Selling);
        sellTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(sellTrade);
        request.setRequestPayloadType(RequestPayloadType.Sell);

        assertThrows(IllegalTradeException.class, () -> {
            PayloadResponse response = client.SendRequest(request);
        });
    }

    /**
     * Test 9: Retrieve a list of current users
     */
    @Test
    public void listCurrentUsers() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new User());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = client.SendRequest(request);

        // Not too sure how to verify the result. - Chris
        response.getPayloadObject();
    }

    /**
     * Test 10: Retrieve a list of current organisational units
     */
    @Test
    public void listCurrentOrganisationalUnits() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new OrganisationalUnit());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = client.SendRequest(request);

        response.getPayloadObject();
    }

    /**
     * Test 11: Retrieve a list of current asset types
     */
    @Test
    public void listCurrentAssetTypes() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new AssetType());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = client.SendRequest(request);

        response.getPayloadObject();
    }

    /**
     * Test 12: Retrieve a list of current assets
     */
    @Test
    public void listCurrentAssets() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new Asset());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = client.SendRequest(request);

        response.getPayloadObject();
    }

    /**
     * Test 13: Retrieve a list of current trades
     */
    @Test
    public void listCurrentTrades() throws IOException {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new Trade());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = client.SendRequest(request);

        response.getPayloadObject();
    }
}