package UnitTests2;

import CAB302.Client.Admin.NewAssetType;
import CAB302.Client.Admin.NewOrganisationalUnit;
import CAB302.Client.Client;
import CAB302.Client.ClientSettings;
import CAB302.Common.Asset;
import CAB302.Common.AssetType;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.OrganisationalUnit;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Common.ServerPackages.RuntimeSettings;
import CAB302.Common.User;
import CAB302.Server.Server;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganisationalUnitTestsV2 {
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
    public void createOrganisationalUnit() {
        Client client = new Client();

        String testName = "TrialOU";
        PayloadResponse response = createOU(testName);

        Assert.assertNotNull(response);

        Assert.assertNotNull(response.getPayloadObject());

        //Delete from database
        OrganisationalUnit ou = (OrganisationalUnit) response.getPayloadObject();
        deleteOU(ou);
    }

    @Test
    @Order(2)
    public void getOrganisationalUnit() {
        Client client = new Client();

        //CreateOU
        String testName = "TrialOU";
        PayloadResponse response = createOU(testName);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPayloadObject());

        //Get the asset
        OrganisationalUnit type = new OrganisationalUnit();
        type.setUnitName("testName");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OrganisationalUnit ou = (OrganisationalUnit) payloadResponse.getPayloadObject();
        Assert.assertEquals(testName, ou.getUnitName());

        //Delete from database
        deleteOU(ou);
    }

    @Test
    @Order(3)
    public void updateOrganisationalUnit() {
        Client client = new Client();

        //CreateOU
        String testName = "TrialOU";
        PayloadResponse response = createOU(testName);
        Assert.assertNotNull(response);

        //GetOU
        OrganisationalUnit ou = getOU(testName);
        Assert.assertEquals(testName, ou.getUnitName());

        //UpdateOU
        ou.setUnitName("TrialOU - Updated");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(ou);
        request.setRequestPayloadType(RequestPayloadType.Update);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        OrganisationalUnit ouUpdated = (OrganisationalUnit)payloadResponse.getPayloadObject();

        Assert.assertEquals(ouUpdated.getUnitName(), "TrialOU - Updated");

        //Delete from database
        deleteOU(ou);

    }

    @Test
    @Order(4)
    public void listOrganisationalUnit() {
        Client client = new Client();

        //CreateOU
        String testName = "TrialOU";
        PayloadResponse response = createOU(testName);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPayloadObject());

        //Get List
        OrganisationalUnit type = new OrganisationalUnit();
        type.setUnitName("testName");

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

        List<OrganisationalUnit> types = (List<OrganisationalUnit>)(List<?>)payloadResponse.getPayloadObject();

        OrganisationalUnit ou = types.stream().filter(x -> x.getUnitName().equals(testName)).findFirst().orElse(null);

        Assert.assertNotNull(ou);

    }

    @Test
    @Order(5)
    public void deleteOrganisationalUnit() {
        Client client = new Client();

        //CreateOU
        String testName = "TrialOU";
        PayloadResponse response = createOU(testName);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPayloadObject());

        //GetOU
        OrganisationalUnit ou = getOU(testName);
        Assert.assertEquals(testName, ou.getUnitName());

        //Delete Asset
        PayloadResponse payloadResponse = deleteOU(ou);

        Assert.assertNotNull(payloadResponse);

        Assert.assertNull(payloadResponse.getPayloadObject());
    }

    @Test
    @Order(6)
    public void createOUEmptyName() {
        JPanel testpanel = new JPanel();
        NewOrganisationalUnit test = new NewOrganisationalUnit(testpanel);
        String testName = "";
        boolean enableToast = false;

        PayloadResponse response = test.createOU(testName, enableToast);

        Assert.assertNull(response);
    }

    @Test
    @Order(9)
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
    private PayloadResponse createOU(String name){
        JPanel testpanel = new JPanel();
        NewOrganisationalUnit test = new NewOrganisationalUnit(testpanel);
        boolean enableToast = false;

        PayloadResponse response = test.createOU(name, enableToast);

        return response;
    }
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