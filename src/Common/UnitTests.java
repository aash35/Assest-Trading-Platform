package Common;
import org.junit.jupiter.api.*;

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
        String assetName = "New Asset";

        Asset asset = new Asset(assetName);

        String OUname = "New Unit";
        int budget = 100;
        HashMap<Asset, Integer> availableAssets = new HashMap<>();
        availableAssets.put(asset, 50);
         // Organisational Units should be constructed with a name, budget and a key-value organised list
        // of the unit's available assets.
        OrganisationalUnit OU = new OrganisationalUnit(name, budget, availableAssets);

        String userID = "john.smith@wherever.com";
        String userFirstName = "John";
        String userLastName = "Smith";

        User johnSmith = new User(userID, userFirstName, userLastName, OU);
    }

    /*
    Test 1: Construct a new legal buy type Trade object.
     */
    @Test
    public void newLegalBuy() throws IllegalTradeException {
        String tradeType = "buy";
        int numOfAssets = 10;
        int pricePerUnit = 5;

        // Trade object should require the type of trade, the organisational unit submitting the trade,
        // the asset being traded and the quantity and price per unit of the asset.
        Trade buyTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit);
    }

    /*
    Test 2: Construct a new legal sell type Trade object.
     */
    @Test
    public void newLegalSell() throws IllegalTradeException {
        String tradeType = "sell";
        int numOfAssets = 10;
        int pricePerUnit = 5;

        Trade sellTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit);
    }

    /*
    Test 3: Construct an illegal buy type Trade and ensure it throws an exception.
     */
    @Test
    public void newIllegalBuy() {
        String tradeType = "buy";
        int numOfAssets = 20;
        int pricePerUnit = 10;

        // This trade requires more credits than the organisational unit has access to.
        assertThrows(IllegalTradeException.class, () -> {
            Trade illegalBuyTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit);
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

        // This trade requires more assets than the organisation unit has available to them.
        assertThrows(IllegalTradeException.class, () -> {
           Trade illegalSellTrade = new Trade(tradeType, OU, asset, numOfAssets, pricePerUnit);
        });
    }
}
