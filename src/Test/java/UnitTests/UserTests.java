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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTests {
    /**
     * Pre-Test class declaration
     */
    AssetType type;
    OrganisationalUnit OU;
    User user;
    Asset asset;

    @BeforeAll
    public static void before() {
        Server server = new Server(8080);

        server.startServer();

        System.out.println("Started Server");
    }

    /**
     * Test 0: Construct objects for AssetType, OrganisationalUnit, User and Asset classes.
     */
    @BeforeEach
    @Test
    public void createUser() {

    }
    @Test
    public void updateUser() {

    }
    @Test
    public void deleteUser() {

    }
    @Test
    public void listUser() {

    }

    //need to make all the error cases
    @Test
    public void createDuplicateUser() {

    }
}
