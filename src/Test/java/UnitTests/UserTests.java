package UnitTests;

import CAB302.Client.Admin.Administration;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTests {
    /**
     * Pre-Test class declaration
     */
    private static Client client;
    public static AssetType type;
    public static OrganisationalUnit OU;
    public static User user;
    public static Asset asset;

    @BeforeAll
    public static void before() {
        Server server = new Server(8080);

        server.startServer();

        System.out.println("Started Server");

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

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */
    @BeforeEach
    @Test
    public void createUser() {
        Client client = new Client();

        User type = new User();
        type.setUsername("Unit Test User");
        String password = "test";
        String hashedPass = SHA256HashHelper.generateHashedString(password);
        type.setHashedPassword(hashedPass);
        type.setOrganisationalUnit(OU);
        type.setAccountRoleType(AccountTypeRole.Administrator);


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

        type = types.stream().filter(x -> x.getUsername() == "Unit Test User - Updated").findFirst().orElse(null);

        Assert.assertNotNull(type);

    }

    @Test
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
}
