package UnitTests;
import CAB302.Client.Client;
import CAB302.Common.AssetType;
import CAB302.Common.Enums.JsonPayloadType;
import CAB302.Common.JsonPayloadRequest;
import CAB302.Common.JsonPayloadResponse;
import CAB302.Common.OrganisationalUnit;
import org.junit.Assert;
import org.junit.Test;
import junit.framework.*;

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

public class UnitTests extends TestCase {

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
    public void newLegalBuy() {
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


    //Test 2: Construct a new legal sell type Trade object.

    @Test
    public void newLegalSell() {

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