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
public class AssetTests {
    /**
     * Pre-Test class declaration
     */
    public static Asset asset;

    public static OrganisationalUnitTests org;

    public static AssetTypeTests assetTypeTest;


    @AfterAll
    public static void afterAll() {

        org.updateOrganisationalUnit();
        org.deleteOrganisationalUnit();

        assetTypeTest.updateAssetType();
        assetTypeTest.deleteAssetType();
    }

    @BeforeAll
    public static void before() {
        ClientSettings clientSettings = new ClientSettings();

        Server server = new Server(RuntimeSettings.Port);

        server.startServer();

        System.out.println("Started Server");

        org = new OrganisationalUnitTests();
        org.createOrganisationalUnit();

        assetTypeTest = new AssetTypeTests();
        assetTypeTest.createAssetType();
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */

    @Test
    @Order(1)
    public void createAsset() {

        Client client = new Client();

        asset = new Asset();

        if (org == null) {
            org = new OrganisationalUnitTests();
            org.createOrganisationalUnit();
        }

        if (assetTypeTest == null) {
            assetTypeTest = new AssetTypeTests();
            assetTypeTest.createAssetType();
        }

        PayloadRequest request = new PayloadRequest();
        asset.setQuantity(1);
        asset.setOrganisationalUnit(org.OU);
        asset.setAssetType(assetTypeTest.type);

        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.Create);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.getPayloadObject();

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        asset = (CAB302.Common.Asset)response.getPayloadObject();

        Assert.assertNotNull(asset.id);
    }

    @Test
    @Order(2)
    public void getAsset() {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        Asset asset = new Asset();
        asset.setOrganisationalUnit(org.OU);
        asset.setAssetType(assetTypeTest.type);

        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.Get);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        asset = (CAB302.Common.Asset)response.getPayloadObject();

        Assert.assertNotNull(asset.id);
    }

    @Test
    @Order(3)
    public void updateAsset() {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        Asset asset = new Asset();
        asset.setOrganisationalUnit(org.OU);
        asset.setAssetType(assetTypeTest.type);

        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.Get);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        asset = (Asset)response.getPayloadObject();

        request = new PayloadRequest();
        asset.setQuantity(50);
        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.Update);

        response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.getPayloadObject();

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        asset = (CAB302.Common.Asset)response.getPayloadObject();

        Assert.assertEquals(asset.getQuantity(), 50);
    }

    @Test
    @Order(4)
    public void listAsset() {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.List);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.getPayloadObject();
        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        List<Asset> types = (List<Asset>)(List<?>)response.getPayloadObject();

        asset = types.stream().filter(x -> x.getQuantity() == 50).findFirst().orElse(null);

        Assert.assertNotNull(asset);
        Assert.assertNotNull(asset.id);

    }
    @Test
    @Order(5)
    public void deleteAsset() {
        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.Delete);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(response);

        Assert.assertNull(response.getPayloadObject());
    }


    @Test
    public void stopServerTest() {
        ClientSettings clientSettings = new ClientSettings();

        Server server = new Server(RuntimeSettings.Port);

        server.startServer();

        System.out.println("Started Server");

        server.stopServer();
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
