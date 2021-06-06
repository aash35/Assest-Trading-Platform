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
    public static OrganisationalUnit OU;
    public static Asset asset;

    public static AssetType assetType;


    @AfterAll
    public static void afterAll() {
        OrganisationalUnitTests org = new OrganisationalUnitTests();
        org.deleteOrganisationalUnit();

        AssetTypeTests assetTypeTest = new AssetTypeTests();
        assetTypeTest.deleteAssetType();
    }


    @BeforeAll
    public static void before() {
        client = new Client();

        Server server = new Server(8080);

        server.startServer();

        System.out.println("Started Server");

        OrganisationalUnitTests org = new OrganisationalUnitTests();
        org.createOrganisationalUnit();
        OU = org.OU;

        AssetTypeTests assetTypeTest = new AssetTypeTests();
        assetTypeTest.createAssetType();
        assetType = assetTypeTest.type;
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */

    @Test
    @Order(1)
    public void createAsset() {

        PayloadRequest request = new PayloadRequest();
        asset.setQuantity(1);
        asset.setOrganisationalUnit(OU);
        asset.setAssetType(assetType);

        request.setPayloadObject(asset);
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





        AssetType type = new AssetType();
        type.setName("Unit Test Asset Type");
        type.setDescription("Test Description");

        PayloadRequest request1 = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Create);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());


    }

    @Test
    @Order(2)
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
    @Order(3)
    public void updateAsset() {

    }
    @Test
    @Order(4)
    public void listAsset() {

    }
    @Test
    @Order(5)
    public void deleteAsset() {

    }

    @Test
    @Order(6)
    public void createAssetWithoutAssetType() {

    }
    @Test
    @Order(7)
    public void createAssetWithoutName() {

    }
    @Test
    @Order(8)
    public void createAssetWithoutOrganisation() {

    }
    @Test
    @Order(9)
    public void createAssetWithoutQuantity() {

    }
}
