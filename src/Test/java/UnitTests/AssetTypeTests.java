package UnitTests;

import CAB302.Client.Client;
import CAB302.Client.ClientSettings;
import CAB302.Common.Asset;
import CAB302.Common.AssetType;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.OrganisationalUnit;
import CAB302.Common.ServerPackages.BaseObject;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Common.ServerPackages.RuntimeSettings;
import CAB302.Common.User;
import CAB302.Server.Server;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssetTypeTests {
    /**
     * Pre-Test class declaration
     */
    public static AssetType type;

    @BeforeAll
    public static void before() {
        ClientSettings clientSettings = new ClientSettings();

        Server server = new Server(RuntimeSettings.Port);

        server.startServer();

        System.out.println("Started Server");
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */
    @Test
    @Order(1)
    public void createAssetType() {
        Client client = new Client();

        AssetType typeOne = new AssetType();
        typeOne.setName("Unit Test Asset Type");
        typeOne.setDescription("Test Description");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(typeOne);
        request.setRequestPayloadType(RequestPayloadType.Create);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());
        type = (AssetType) payloadResponse.getPayloadObject();
    }

    @Test
    @Order(2)
    public void getAssetType() {
        Client client = new Client();

        AssetType type = new AssetType();
        type.setName("Unit Test Asset Type");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        Assert.assertNotNull(((BaseObject)payloadResponse.getPayloadObject()).id);
    }

    @Test
    @Order(3)
    public void updateAssetType() {
        Client client = new Client();

        AssetType type = new AssetType();
        type.setName("Unit Test Asset Type");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        type = (AssetType)payloadResponse.getPayloadObject();

        Assert.assertNotNull(type.id);

        type.setName("Unit Test Asset Type - Updated");

        request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Update);

        payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        type = (AssetType)payloadResponse.getPayloadObject();

        Assert.assertEquals(type.getName(), "Unit Test Asset Type - Updated");
    }

    @Test
    @Order(4)
    public void listAssetType() {
        Client client = new Client();

        AssetType type = new AssetType();
        type.setName("Unit Test Asset Type - Updated");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        List<AssetType> types = (List<AssetType>)(List<?>)payloadResponse.getPayloadObject();

        Assert.assertNotNull(types);

        type = types.stream().filter(x -> x.getName().equals("Unit Test Asset Type - Updated")).findFirst().orElse(null);

        Assert.assertNotNull(type);
    }

    @Test
    @Order(5)
    public void deleteAssetType() {
        Client client = new Client();

        AssetType type = new AssetType();
        type.setName("Unit Test Asset Type - Updated");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        type = (AssetType)payloadResponse.getPayloadObject();

        request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Delete);

        payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        type = null;
        Assert.assertNotNull(payloadResponse);

        Assert.assertNull(payloadResponse.getPayloadObject());

    }
}
