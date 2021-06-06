package UnitTests;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Server.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNull;

public class AssetTests {

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
    @Test
    public void createAsset() {

    }

    @Test
    public void updateAsset() {

    }
    @Test
    public void deleteAsset() {

        createAsset();
    }
    @Test
    public void listAsset() {

    }
    @Test
    public void createAssetWithoutAssetType() {

    }
    @Test
    public void createAssetWithoutOrganisation() {

    }
    @Test
    public void createAssetWithoutQuantity() {

    }
}
