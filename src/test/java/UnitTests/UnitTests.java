package UnitTests;
import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.JsonPayloadType;

import static org.junit.jupiter.api.Assertions.*;

import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.SHA256HashHelper;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;

public class UnitTests {

    /**
     * Pre-Test class declaration
     */
    AssetType type;
    OrganisationalUnit OU;
    User user;
    Asset asset;

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */
    @BeforeEach @Test
    public void tradeTestData() {
        type = new AssetType();
        type.setName("Potatoes");

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
    public void newAssetTypeTest() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(type);

        request.setJsonPayloadType(JsonPayloadType.Create);

        JsonPayloadResponse response = client.SendRequest(request);

        assertNull(response.getPayloadObject());
    }

    /**
     * Test 2: Construct a new Organisational Unit object
     */
    @Test
    public void newOrganisationalUnit() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(OU);
        request.setJsonPayloadType(JsonPayloadType.Create);

        JsonPayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }


    /**
     * Test 3: Construct a new User object
     */
    @Test
    public void newUser() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(user);
        request.setJsonPayloadType(JsonPayloadType.Create);

        JsonPayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 4: Construct a new Asset object
     */
    @Test
    public void newAsset() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(asset);
        request.setJsonPayloadType(JsonPayloadType.Create);

        JsonPayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 5: Construct a new legal buy type trade
     */
    @Test
    public void newLegalBuy() {
        Trade buyTrade = new Trade();

        buyTrade.setAsset(asset);
        buyTrade.setQuantity(10);
        buyTrade.setPrice(5);
        buyTrade.setCreatedByUser(user);
        buyTrade.setCreatedDate(Timestamp.from(Instant.now()));
        buyTrade.setTransactionType(TradeTransactionType.Buying);
        buyTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(buyTrade);
        request.setJsonPayloadType(JsonPayloadType.Buy);

        JsonPayloadResponse response = client.SendRequest(request);
        // I assume we'd still be expecting a null response from this one. - Chris
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 6: Construct a new legal sell type trade.
     */
    @Test
    public void newLegalSell() {
        Trade sellTrade = new Trade();

        sellTrade.setAsset(asset);
        sellTrade.setQuantity(10);
        sellTrade.setPrice(10);
        sellTrade.setCreatedByUser(user);
        sellTrade.setCreatedDate(Timestamp.from(Instant.now()));
        sellTrade.setTransactionType(TradeTransactionType.Selling);
        sellTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(sellTrade);
        request.setJsonPayloadType(JsonPayloadType.Sell);

        JsonPayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }

    /**
     * Test 7: Construct an illegal buy type trade, ensure that processing this trade returns an error.
     */
    @Test
    public void newIllegalBuy() {
        Trade buyTrade = new Trade();

        buyTrade.setAsset(asset);
        buyTrade.setQuantity(10);
        buyTrade.setPrice(12);
        buyTrade.setCreatedByUser(user);
        buyTrade.setCreatedDate(Timestamp.from(Instant.now()));
        buyTrade.setTransactionType(TradeTransactionType.Buying);
        buyTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(buyTrade);
        request.setJsonPayloadType(JsonPayloadType.Buy);

        assertThrows(IllegalTradeException.class, () ->{
            JsonPayloadResponse response = client.SendRequest(request);
        });
    }

    /**
     * Test 8: Construct an illegal buy type trade, ensure that processing this trade returns an error.
     */
    @Test
    public void newIllegalSell() {
        Trade sellTrade = new Trade();

        sellTrade.setAsset(asset);
        sellTrade.setQuantity(60);
        sellTrade.setPrice(10);
        sellTrade.setCreatedByUser(user);
        sellTrade.setCreatedDate(Timestamp.from(Instant.now()));
        sellTrade.setTransactionType(TradeTransactionType.Selling);
        sellTrade.setStatus(TradeStatus.InMarket);

        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(sellTrade);
        request.setJsonPayloadType(JsonPayloadType.Sell);

        assertThrows(IllegalTradeException.class, () -> {
            JsonPayloadResponse response = client.SendRequest(request);
        });
    }

    /**
     * Test 9: Retrieve a list of current users
     */
    @Test
    public void listCurrentUsers() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(new User());
        request.setJsonPayloadType(JsonPayloadType.List);

        JsonPayloadResponse response = client.SendRequest(request);

        // Not too sure how to verify the result. - Chris
        response.getPayloadObject();
    }

    /**
     * Test 10: Retrieve a list of current organisational units
     */
    @Test
    public void listCurrentOrganisationalUnits() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(new OrganisationalUnit());
        request.setJsonPayloadType(JsonPayloadType.List);

        JsonPayloadResponse response = client.SendRequest(request);

        response.getPayloadObject();
    }

    /**
     * Test 11: Retrieve a list of current asset types
     */
    @Test
    public void listCurrentAssetTypes() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(new AssetType());
        request.setJsonPayloadType(JsonPayloadType.List);

        JsonPayloadResponse response = client.SendRequest(request);

        response.getPayloadObject();
    }

    /**
     * Test 12: Retrieve a list of current assets
     */
    @Test
    public void listCurrentAssets() {
        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(new Asset());
        request.setJsonPayloadType(JsonPayloadType.List);

        JsonPayloadResponse response = client.SendRequest(request);

        response.getPayloadObject();
    }
}