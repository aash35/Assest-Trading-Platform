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
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNull;

public class AssetTests {

    private static Client client;
    /**
     * Pre-Test class declaration
     */
    public static AssetType type;
    public static OrganisationalUnit OU;
    public static Asset asset;

    @Before
    public static void beforeClient() {
        client = new Client();

        OrganisationalUnitTests org = new OrganisationalUnitTests();
        org.createOrganisationalUnit();
        OU = org.OU;
    }

    @AfterAll
    public static void afterAll() {
        OrganisationalUnitTests org = new OrganisationalUnitTests();
        org.deleteOrganisationalUnit();
    }


    @BeforeAll
    public static void before() {
        Server server = new Server(8080);

        server.startServer();

        System.out.println("Started Server");
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */

    @Test
    public void createAsset() {

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new Asset());
        request.setRequestPayloadType(RequestPayloadType.Create);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.getPayloadObject();

        asset = (CAB302.Common.Asset)response.getPayloadObject();

        Assert.assertNotNull(asset);
    }
    @Test
    @Order(3)
    public void getAsset() {
        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new Asset());
        request.setRequestPayloadType(RequestPayloadType.Get);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.getPayloadObject();
        Assert.assertNotNull(response);
    }

    @Test
    @Order(2)
    public void updateAsset() {

    }
    @Test
    @Order(3)
    public void listAsset() {

    }
    @Test
    public void deleteAsset() {

    }

    @Test
    public void createAssetWithoutAssetType() {

    }
    @Test
    public void createAssetWithoutName() {

    }
    @Test
    public void createAssetWithoutOrganisation() {

    }
    @Test
    public void createAssetWithoutQuantity() {

    }
}
