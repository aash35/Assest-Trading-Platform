package Common;
import Common.Enums.AccountTypeRole;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    /*
    Test 0: Construct a new organisation unit, asset and user objects. In complete program, these will
    be constructed from database information.
     */
    @BeforeEach @Test
    public void newOrganisationalUnit() {
        // Organisational Units should be constructed with a name, budget and a key-value organised list
        // of the unit's available assets.
        String unitName = "New Unit";
        int availableCredits = 100;
        HashMap<Asset, Integer> availableAssets = new HashMap<>();
        OrganisationalUnit OU = new OrganisationalUnit(unitName, availableCredits, availableAssets);

        // Constuctor for an Asset object, to be added to the organisational unit's HashMap of available assets
        int id = 1;
        String assetTypeName = "Asset type";
        String description = "Placeholder asset type";
        AssetType assetType = new AssetType(id, assetTypeName, description);

        int quantity = 100;
        Asset asset = new Asset(assetType, quantity, OU);

        OU.addAsset(asset);

        String username = "john.smith@wherever.com";
        String hashedPassword = hashString("password");
        AccountTypeRole accountTypeRole = AccountTypeRole.Standard;

        User johnSmith = new User(username, hashedPassword, accountTypeRole, OU);
    }

    /*
    Test 1: Construct a new legal buy type Trade object.
     */
    @Test
    public void newLegalBuy() throws IllegalTradeException {
        String tradeType = "buy";
        int numOfAssets = 10;
        int pricePerUnit = 5;
        LocalDate creationDate = LocalDate.now();

        // Trade object should require the type of trade, the organisational unit submitting the trade,
        // the asset being traded and the quantity and price per unit of the asset.
        Trade buyTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit, creationDate);
    }

    /*
    Test 2: Construct a new legal sell type Trade object.
     */
    @Test
    public void newLegalSell() throws IllegalTradeException {
        String tradeType = "sell";
        int numOfAssets = 10;
        int pricePerUnit = 5;
        LocalDate creationDate = LocalDate.now();

        Trade sellTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit, creationDate);
    }

    /*
    Test 3: Construct an illegal buy type Trade and ensure it throws an exception.
     */
    @Test
    public void newIllegalBuy() {
        String tradeType = "buy";
        int numOfAssets = 20;
        int pricePerUnit = 10;
        LocalDate creationDate = LocalDate.now();

        // This trade requires more credits than the organisational unit has access to.
        assertThrows(IllegalTradeException.class, () -> {
            Trade illegalBuyTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit, creationDate);
        });
    }

    /*
    Test 4: Construct an illegal sell type Trade and ensure it throws an exception.
     */
    @Test
    public void newIllegalSell() {
        String tradeType = "sell";
        int numOfAssets = 60;
        int pricePerUnit = 5;
        LocalDate creationDate = LocalDate.now();

        // This trade requires more assets than the organisation unit has available to them.
        assertThrows(IllegalTradeException.class, () -> {
           Trade illegalSellTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit, creationDate);
        });
    }
}
