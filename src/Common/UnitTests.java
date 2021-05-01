package Common;
import Common.Enums.AccountTypeRole;
import Common.Enums.TradeTransactionType;
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
        /**Excluded for Javadoc: OrganisationalUnit OU = new OrganisationalUnit(unitName, availableCredits, availableAssets);*/

        // Constuctor for an Asset object, to be added to the organisational unit's HashMap of available assets
        int id = 1;
        String assetTypeName = "Asset type";
        String description = "Placeholder asset type";
        /**Excluded for Javadoc:AssetType assetType = new AssetType(id, assetTypeName, description); */

        int quantity = 100;
        /**Excluded for Javadoc: Asset asset = new Asset(assetType, quantity, OU);*/

        /**Excluded for Javadoc: OU.addAsset(asset);*/

        String username = "john.smith@wherever.com";
        /**Excluded for Javadoc: String hashedPassword = hashString("password");*/
        AccountTypeRole accountTypeRole = AccountTypeRole.Standard;

        /**Excluded for Javadoc: User johnSmith = new User(username, hashedPassword, accountTypeRole, OU);*/
    }

    /*
    Test 1: Construct a new legal buy type Trade object.
     */
    /** Excluded for Javadoc: @Test
    public void newLegalBuy() throws IllegalTradeException {
        TradeTransactionType tradeType = TradeTransactionType.Buying;
        int quantity = 10;
        double pricePerUnit = 5;
        LocalDateTime creationDate = LocalDateTime.now();

        // Trade object should require the type of trade, the organisational unit submitting the trade,
        // the asset being traded and the quantity and price per unit of the asset.
        Trade buyTrade = new Trade(tradeType, OU, asset, quantity, pricePerUnit, creationDate);
    }*/

    /*
    Test 2: Construct a new legal sell type Trade object.
     */
    /** Excluded for Javadoc: @Test
    public void newLegalSell() throws IllegalTradeException {
        TradeTransactionType tradeType = TradeTransactionType.Selling;
        int quantity = 10;
        double pricePerUnit = 5;
        LocalDateTime creationDate = LocalDateTime.now();

        Trade sellTrade = new Trade(tradeType, OU, asset, quantity, pricePerUnit, creationDate);
    }*/

    /*
    Test 3: Construct an illegal buy type Trade and ensure it throws an exception.
     */
    @Test
    public void newIllegalBuy() {
        TradeTransactionType tradeType = TradeTransactionType.Buying;
        int quantity = 20;
        int pricePerUnit = 10;
        LocalDateTime creationDate = LocalDateTime.now();

        // This trade requires more credits than the organisational unit has access to.
        /** Excluded for Javadoc: assertThrows(IllegalTradeException.class, () -> {
            Trade illegalBuyTrade = new Trade(tradeType, OU, asset, quantity, pricePerUnit, creationDate);
        });*/
    }

    /*
    Test 4: Construct an illegal sell type Trade and ensure it throws an exception.
     */
    @Test
    public void newIllegalSell() {
        TradeTransactionType tradeType = TradeTransactionType.Selling;
        int quantity = 60;
        int pricePerUnit = 5;
        LocalDateTime creationDate = LocalDateTime.now();

        // This trade requires more assets than the organisation unit has available to them.
        /** Excluded for Javadoc: assertThrows(IllegalTradeException.class, () -> {
           Trade illegalSellTrade = new Trade(tradeType, OU, asset, quantity, pricePerUnit, creationDate);
        });*/
    }
}
