package UnitTests2;

import CAB302.Client.Admin.NewAssetType;
import CAB302.Client.Client;
import CAB302.Client.ClientSettings;
import CAB302.Client.Helper.Toast;
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

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssetTypeTestV2 {
    /**
     * Pre-Test class declaration
     */

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

        //remove newly created asset type
        if (payloadResponse != null)
        {
            AssetType type = (AssetType) payloadResponse.getPayloadObject();
            deleteAssetType(type);
        }
    }

    @Test
    @Order(2)
    public void getAssetType() {
        Client client = new Client();
        //Create Asset
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);
        String testName = "TestAsset";
        String testDesc = "test";
        boolean enableToast = false;
        PayloadResponse response = test.createAssetType(testName, testDesc, enableToast);
        Assert.assertNotNull(response);

        //Get the asset
        AssetType type = new AssetType();
        type.setName("TestAsset");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AssetType assetType = (AssetType) payloadResponse.getPayloadObject();
        Assert.assertEquals(testName, assetType.getName());

        //remove newly created asset type
        if (payloadResponse != null)
        {
            deleteAssetType(assetType);
        }
    }

    @Test
    @Order(3)
    public void updateAssetType() {
        Client client = new Client();

        //Create Asset
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);
        String testName = "TestAsset";
        String testDesc = "test";
        boolean enableToast = false;
        PayloadResponse response = test.createAssetType(testName, testDesc, enableToast);
        Assert.assertNotNull(response);

        //Get the asset
        AssetType assetType = getAssetType(testName, testDesc);
        Assert.assertEquals(testName, assetType.getName());

        //Update the asset
        assetType.setName("TestAsset - Updated");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(assetType);
        request.setRequestPayloadType(RequestPayloadType.Update);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AssetType assetTypeUpdated = (AssetType)payloadResponse.getPayloadObject();
        Assert.assertEquals(assetTypeUpdated.getName(), "TestAsset - Updated");

        //remove newly created asset type
        if (payloadResponse != null)
        {
            deleteAssetType(assetTypeUpdated);
        }
    }

    @Test
    @Order(4)
    public void listAssetType() {
        Client client = new Client();

        //Create Asset
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);
        String testName = "TestAsset";
        String testDesc = "test";
        boolean enableToast = false;
        PayloadResponse response = test.createAssetType(testName, testDesc, enableToast);
        Assert.assertNotNull(response);

        //Get List
        AssetType type = new AssetType();
        type.setName(testName);

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

        type = types.stream().filter(x -> x.getName().equals(testName)).findFirst().orElse(null);

        Assert.assertNotNull(type);

        //remove newly created asset type
        if (payloadResponse != null)
        {
            AssetType assetType = getAssetType(testName, testDesc);
            deleteAssetType(assetType);
        }
    }

    @Test
    @Order(5)
    public void deleteAssetType() {
        Client client = new Client();

        //Create Asset
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);
        String testName = "TestAsset";
        String testDesc = "test";
        boolean enableToast = false;
        PayloadResponse response = test.createAssetType(testName, testDesc, enableToast);
        Assert.assertNotNull(response);

        //Get the asset
        AssetType assetType = getAssetType(testName, testDesc);
        Assert.assertEquals(testName, assetType.getName());

        //Delete Asset
        PayloadResponse payloadResponse = deleteAssetType(assetType);

        Assert.assertNotNull(payloadResponse);

        Assert.assertNull(payloadResponse.getPayloadObject());
    }

    @Test
    @Order(6)
    public void createAssetTypeEmptyNameAndDescription() {
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);
        String testName = "";
        String testDesc = "";
        boolean enableToast = false;

        PayloadResponse response = test.createAssetType(testName, testDesc, enableToast);

        Assert.assertNull(response);
    }

    @Test
    @Order(7)
    public void createAssetTypeEmptyNameOnly() {
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);
        String testName = "";
        String testDesc = "asdasd";
        boolean enableToast = false;

        PayloadResponse response = test.createAssetType(testName, testDesc, enableToast);

        Assert.assertNull(response);
    }

    @Test
    @Order(8)
    public void createAssetTypeEmptyDescOnly() {
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);
        String testName = "asdasd";
        String testDesc = "";
        boolean enableToast = false;

        PayloadResponse response = test.createAssetType(testName, testDesc, enableToast);

        Assert.assertNull(response);
    }

    @Test
    @Order(9)
    public void createDuplicateAssetType() {
        JPanel testpanel = new JPanel();
        NewAssetType test = new NewAssetType(testpanel);

        //Create asset
        String testName1 = "asdasd";
        String testDesc1 = "asdasd";
        boolean enableToast = false;

        PayloadResponse response1 = test.createAssetType(testName1, testDesc1, enableToast);

        Assert.assertNotNull(response1);

        //Test if an asset of the same name can be created
        String testName2 = "asdasd";
        String testDesc2 = "asda";

        PayloadResponse response2 = test.createAssetType(testName2, testDesc2, enableToast);

        Assert.assertNull(response2);

        //cleanup database
        if(response1 != null)
        {
            AssetType assetType = getAssetType(testName1, testDesc1);
            deleteAssetType(assetType);
        }
    }


    ///////////////////////////////HELPER FUNCTIONS TO CLEAN DATABASE//////////////////////
    private AssetType getAssetType (String name, String description){

        CAB302.Common.AssetType type = new CAB302.Common.AssetType();

        type.setName(name);
        type.setDescription(description);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse response = null;
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return (AssetType)response.getPayloadObject();
    }

    private PayloadResponse deleteAssetType (AssetType assetType){

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(assetType);
        //an organisation asset can only be deleted if a trade related to that orgAsset does not exist
        request.setRequestPayloadType(RequestPayloadType.Delete);

        PayloadResponse response = null;
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return response;
    }
}
