package UnitTests;

import CAB302.Client.Client;
import CAB302.Common.Asset;
import CAB302.Common.AssetType;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.OrganisationalUnit;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Common.User;
import CAB302.Server.Server;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;

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

        AssetTests assetTests = new AssetTests();
        assetTests.createAsset();
        asset = assetTests.asset;
        OU = assetTests.OU;

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
    public void createTrade() {

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
