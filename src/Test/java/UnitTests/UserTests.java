package UnitTests;

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
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {
    /**
     * Pre-Test class declaration
     */
    public static Client client;
    public static AssetType type;
    public static OrganisationalUnit OU;
    public static User user;
    public static Asset asset;

    public static OrganisationalUnitTests org;

    @BeforeAll
    public static void before() {
        ClientSettings clientSettings = new ClientSettings();

        Server server = new Server(RuntimeSettings.Port);

        server.startServer();

        System.out.println("Started Server");

        client = new Client();

        org = new OrganisationalUnitTests();
        org.createOrganisationalUnit();
        OU = org.OU;
    }


    @AfterAll
    public static void afterAll() {
        org.updateOrganisationalUnit();
        org.deleteOrganisationalUnit();
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */
    @Test
    @Order(1)
    public void createUser() {
        Client client = new Client();

        User type = new User();
        type.setUsername("Unit Test User");
        String password = "test";
        String hashedPass = SHA256HashHelper.generateHashedString(password);
        type.setHashedPassword(hashedPass);
        type.setOrganisationalUnit(OU);
        type.setAccountRoleType(AccountTypeRole.Standard);


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

    }

    @Test
    @Order(2)
    public void getUser() {
        PayloadRequest request = new PayloadRequest();

        User user = new User();
        user.setUsername("Unit Test User");

        request.setPayloadObject(user);
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
    public void getUserWithPass() {
        PayloadRequest request = new PayloadRequest();

        User user = new User();
        user.setUsername("Unit Test User");
        String password = "test";
        String hashedPass = SHA256HashHelper.generateHashedString(password);
        user.setHashedPassword(hashedPass);

        request.setPayloadObject(user);
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
    @Order(4)
    public void updateUser() {
        Client client = new Client();

        User type = new User();
        type.setUsername("Unit Test User");

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

        type = (User)payloadResponse.getPayloadObject();

        Assert.assertNotNull(type.id);

        type.setUsername("Unit Test User - Updated");

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

        type = (User)payloadResponse.getPayloadObject();

        Assert.assertEquals(type.getUsername(), "Unit Test User - Updated");

    }

    @Test
    @Order(5)
    public void listUser() {
        Client client = new Client();

        User type = new User();
        type.setUsername("Unit Test User - Updated");

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

        List<User> types = (List<User>)(List<?>)payloadResponse.getPayloadObject();

        Assert.assertNotNull(types);

        type = types.stream().filter(x -> x.getUsername().equals("Unit Test User - Updated")).findFirst().orElse(null);

        Assert.assertNotNull(type);

    }

    @Test
    @Order(6)
    public void listUserWithPass() {
        Client client = new Client();

        User type = new User();
        type.setUsername("Unit Test User - Updated");
        String password = "test";
        String hashedPass = SHA256HashHelper.generateHashedString(password);
        type.setHashedPassword(hashedPass);

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

        List<User> types = (List<User>)(List<?>)payloadResponse.getPayloadObject();

        Assert.assertNotNull(types);

        type = types.stream().filter(x -> x.getUsername().equals("Unit Test User - Updated")).findFirst().orElse(null);

        Assert.assertNotNull(type);

    }

    @Test
    @Order(7)
    public void deleteUser() {
        Client client = new Client();

        User type = new User();
        type.setUsername("Unit Test User - Updated");

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

        type = (User)payloadResponse.getPayloadObject();

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

    //need to make all the error cases
    @Test
    public void createDuplicateUser() {

    }

    @Test
    public void testCreation() {
        User testUser = new User();
        String username = "Hello";
        String password = "test";
        String hashedPass = SHA256HashHelper.generateHashedString(password);
        AccountTypeRole userRole = AccountTypeRole.Administrator;

        testUser.setUsername(username);
        testUser.setOrganisationalUnit(OU);
        testUser.setHashedPassword(hashedPass);
        testUser.setAccountRoleType(userRole);

        Assert.assertEquals(username, testUser.getUsername());
        Assert.assertEquals(OU, testUser.getOrganisationalUnit());
        Assert.assertEquals(hashedPass, testUser.getHashedPassword());
        Assert.assertEquals(userRole, testUser.getAccountRoleType());
    }


}
