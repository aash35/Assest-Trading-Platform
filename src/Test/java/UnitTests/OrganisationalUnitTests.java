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
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganisationalUnitTests {
    /**
     * Pre-Test class declaration
     */
    public static OrganisationalUnit OU;
    User user;

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
    @Order(1)
    public void createOrganisationalUnit() {
        Client client = new Client();

        OrganisationalUnit type = new OrganisationalUnit();
        type.setUnitName("Unit Test Organisational Unit");

        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Create);

        PayloadResponse payloadResponse = null;

        try {
            payloadResponse = client.SendRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OU = type;
        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

    }
    @Test
    @Order(2)
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
    @Order(3)
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

        Assert.assertNotNull(payloadResponse);

        Assert.assertNotNull(payloadResponse.getPayloadObject());

        List<OrganisationalUnit> types = (List<OrganisationalUnit>)(List<?>)payloadResponse.getPayloadObject();

        Assert.assertNotNull(types);

        type = types.stream().filter(x -> x.getUnitName() == "Unit Test Organisational Unit - Updated").findFirst().orElse(null);

        Assert.assertNotNull(type);

    }

    @Test
    @Order(4)
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
}
