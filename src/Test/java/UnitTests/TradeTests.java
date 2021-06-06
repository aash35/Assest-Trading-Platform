package UnitTests;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Server.Server;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TradeTests {
    /**
     * Pre-Test class declaration
     */
    private static Client client;
    public static AssetType type;
    public static OrganisationalUnit OU;
    public static User user;
    public static Asset asset;

    @Before
    public void preTestConstruction() {
        client = new Client();

        OrganisationalUnitTests ouTests = new OrganisationalUnitTests();
        ouTests.createOrganisationalUnit();
        OU = ouTests.OU;

        AssetTypeTests assetTypeTests = new AssetTypeTests();
        assetTypeTests.createAssetType();
        type = assetTypeTests.type;
    }

    @BeforeAll
    public static void before() {
        Server server = new Server(8080);

        server.startServer();

        System.out.println("Started Server");
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
    }

    @Test
    public void updateTrade() {

    }
    @Test
    public void deleteTrade() {

    }
    @Test
    public void listTrades() {

    }

    //need to make all the error cases
    @Test
    public void errorTrade() {

    }
}
