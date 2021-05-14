package UnitTests;
import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.JsonPayloadType;

import static org.junit.jupiter.api.Assertions.*;

import CAB302.Common.Helpers.SHA256HashHelper;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;

public class UnitTests {

    //Test 1: Construct a new Asset Type
    @Test
    public void newAssetTypeTest() {
        AssetType type = new AssetType();
        type.setName("123");

        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(type);

        request.setJsonPayloadType(JsonPayloadType.Create);

        JsonPayloadResponse response = client.SendRequest(request);

        assertNull(response.getPayloadObject());
    }

    //Test 2: Construct a new Organisational Unit
    @Test
    public void newOrganisationalUnit() {
        OrganisationalUnit OU = new OrganisationalUnit();
        OU.setUnitName("Unit A");
        OU.setAvailableCredit(100);

        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(OU);
        request.setJsonPayloadType(JsonPayloadType.Create);

        JsonPayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }


    //Test 3: Construct a new User
    @Test
    public void newUser() {
        User user = new User();
        user.setUsername("User");
        user.setHashedPassword(SHA256HashHelper.generateHashedString("password"));
        OrganisationalUnit OU = new OrganisationalUnit(); OU.setUnitName("Unit");
        user.setOrganisationalUnit(OU);
        user.setAccountRoleType(AccountTypeRole.Standard);

        Client client = new Client();

        JsonPayloadRequest request = new JsonPayloadRequest();

        request.setPayloadObject(user);
        request.setJsonPayloadType(JsonPayloadType.Create);

        JsonPayloadResponse response = client.SendRequest(request);
        assertNull(response.getPayloadObject());
    }


    //Test 3: Construct an illegal buy type Trade and ensure it throws an exception.

    @Test
    public void newIllegalBuy() {

    }

    //Test 4: Construct an illegal sell type Trade and ensure it throws an exception.

    @Test
    public void newIllegalSell() {

    }
}