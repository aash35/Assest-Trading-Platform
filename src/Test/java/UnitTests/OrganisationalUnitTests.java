package UnitTests;

import CAB302.Client.Admin.NewAssetType;
import CAB302.Client.Admin.NewOrganisationalUnit;
import CAB302.Client.Client;
import CAB302.Client.ClientSettings;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Common.ServerPackages.RuntimeSettings;
import CAB302.Server.Server;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganisationalUnitTests {
    /**
     * Pre-Test class declaration
     */
    public OrganisationalUnit OU;
    User user;

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
    public void createOrganisationalUnit() {

        Client client = new Client();

        OrganisationalUnit type = new OrganisationalUnit();
        type.setUnitName("Unit Test Organisational Unit");
        type.setAvailableCredit(5000);

        PayloadRequest request = new PayloadRequest();
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

        this.OU = (OrganisationalUnit) payloadResponse.getPayloadObject();
    }

    @Test
    @Order(2)
    public void getOrganisationalUnit() {

        Client client = new Client();

        PayloadRequest request = new PayloadRequest();

        OrganisationalUnit ou = new OrganisationalUnit();
        ou.setUnitName("Unit Test Organisational Unit");

        request.setPayloadObject(ou);
        request.setRequestPayloadType(RequestPayloadType.Get);
        PayloadResponse response = null;

        try {
            response = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        OU = (CAB302.Common.OrganisationalUnit)response.getPayloadObject();

        Assert.assertNotNull(OU.id);
    }

    @Test
    @Order(3)
    public void updateOrganisationalUnit() {
        Client client = new Client();

        OrganisationalUnit type = new OrganisationalUnit();
        type.setUnitName("Unit Test Organisational Unit");

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

        type = (OrganisationalUnit)payloadResponse.getPayloadObject();

        Assert.assertNotNull(type.id);

        type.setUnitName("Unit Test Organisational Unit - Updated");

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

        type = (OrganisationalUnit)payloadResponse.getPayloadObject();

        Assert.assertEquals(type.getUnitName(), "Unit Test Organisational Unit - Updated");

    }

    @Test
    @Order(4)
    public void listOrganisationalUnit() {
        Client client = new Client();

        OrganisationalUnit type = new OrganisationalUnit();
        type.setUnitName("Unit Test Organisational Unit - Updated");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        payloadResponse.getPayloadObject();

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        List<OrganisationalUnit> types = (List<OrganisationalUnit>)(List<?>)payloadResponse.getPayloadObject();

        OU = types.stream().filter(x -> x.getUnitName().equals("Unit Test Organisational Unit - Updated")).findFirst().orElse(null);

        Assert.assertNotNull(payloadResponse);
        Assert.assertNotNull(OU.id);

    }

    @Test
    @Order(5)
    public void listOrganisationalUnitWithoutUnitName() {
        Client client = new Client();

        OrganisationalUnit type = new OrganisationalUnit();

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        payloadResponse.getPayloadObject();

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        List<OrganisationalUnit> types = (List<OrganisationalUnit>)(List<?>)payloadResponse.getPayloadObject();

        OU = types.stream().filter(x -> x.getUnitName().equals("Unit Test Organisational Unit - Updated")).findFirst().orElse(null);

        Assert.assertNotNull(payloadResponse);
        Assert.assertNotNull(OU.id);

    }

    @Test
    @Order(6)
    public void deleteOrganisationalUnit() {
        Client client = new Client();

        OrganisationalUnit type = new OrganisationalUnit();
        type.setUnitName("Unit Test Organisational Unit - Updated");

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

        type = (OrganisationalUnit)payloadResponse.getPayloadObject();

        request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Delete);

        payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNull(payloadResponse.getPayloadObject());
    }

    @Test
    @Order(7)
    public void createOUEmptyName() {
        JPanel testpanel = new JPanel();
        NewOrganisationalUnit test = new NewOrganisationalUnit(testpanel);
        String testName = "";
        boolean enableToast = false;

        PayloadResponse response = test.createOU(testName, enableToast);

        Assert.assertNull(response);
    }

    @Test
    @Order(8)
    public void createDuplicateOU() {
        JPanel testpanel = new JPanel();
        NewOrganisationalUnit test = new NewOrganisationalUnit(testpanel);

        //Create Ou
        String testName1 = "Test1";
        boolean enableToast = false;

        PayloadResponse response1 = test.createOU(testName1, enableToast);

        Assert.assertNotNull(response1);

        //Test if an asset of the same name can be created
        String testName2 = "Test1";

        PayloadResponse response2 = test.createOU(testName2, enableToast);

        Assert.assertNull(response2);

        //cleanup database
        if(response1 != null)
        {
            OrganisationalUnit ou = getOU(testName1);
            deleteOU(ou);
        }
    }
    @Test
    public void testCreation() {
        OrganisationalUnit testOrganisation = new OrganisationalUnit();
        String unitName = "Test Org";

        AssetType assetType = new AssetType();
        assetType.setName("test");

        Asset asset = new Asset();
        asset.setAssetType(assetType);
        asset.setQuantity(50);
        List<Asset> assetsList = new ArrayList<Asset>();
        assetsList.add(asset);

        int credits = 5000;

        Trade trade = new Trade();
        trade.setAssetType(assetType);
        trade.setPrice(50);
        trade.setStatus(TradeStatus.InMarket);
        List<Trade> tradeList = new ArrayList<Trade>();
        tradeList.add(trade);

        User userOne = new User();
        userOne.setUsername("TestUser");;
        userOne.setOrganisationalUnit(testOrganisation);
        List<User> userList = new ArrayList<User>();
        userList.add(userOne);



        testOrganisation.setAssets(assetsList);
        testOrganisation.setAvailableCredit(credits);
        testOrganisation.setTrades(tradeList);
        testOrganisation.setUsers(userList);
        testOrganisation.setUnitName(unitName);

        Assert.assertEquals(assetsList.get(0), testOrganisation.getAssets().get(0));
        Assert.assertEquals(credits, testOrganisation.getAvailableCredit());
        Assert.assertEquals(tradeList.get(0), testOrganisation.getTrades().get(0));
        Assert.assertEquals(userList.get(0), testOrganisation.getUsers().get(0));
        Assert.assertEquals(unitName, testOrganisation.getUnitName());
    }

/*
    @Test
    @Order(10)
    public void editOUCredits() {
        JPanel testpanel = new JPanel();
        NewOrganisationalUnit test = new NewOrganisationalUnit(testpanel);

        //Create Ou
        String testName1 = "Test1";
        boolean enableToast = false;

        PayloadResponse response1 = test.createOU(testName1, enableToast);

        Assert.assertNotNull(response1);



        //cleanup database
        if(response1 != null)
        {
            OrganisationalUnit ou = getOU(testName1);
            deleteOU(ou);
        }
    }*/


    ///////////////////////////////HELPER FUNCTIONS TO CLEAN DATABASE//////////////////////
    private OrganisationalUnit getOU (String name){

        OrganisationalUnit type = new OrganisationalUnit();

        type.setUnitName(name);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse response = null;
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return (OrganisationalUnit)response.getPayloadObject();
    }

    private PayloadResponse deleteOU (OrganisationalUnit ou){

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(ou);
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
