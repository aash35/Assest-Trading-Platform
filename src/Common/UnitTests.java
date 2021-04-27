package Common;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class UnitTests {

    /*
    Test 0: Construct a new organisation unit, asset and user objects. In complete program, these will
    be constructed from database information.
     */
    @BeforeEach @Test
    public void newOrganisationalUnit() {
        String OUname = "New Unit";
        int budget = 100;
         // Organisational Units should be constructed with a name and budget.
        OrginisationalUnit OU = new OrginisationalUnit(name, budget);

        String assetName = "New Asset";

        Asset asset = new Asset(assetName);

        String userID = "john.smith@wherever.com";
        String userFirstName = "John";
        String userLastName = "Smith";

        User johnSmith = new User(userID, userFirstName, userLastName, OU);
    }


}
