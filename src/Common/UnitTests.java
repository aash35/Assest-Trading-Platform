package Common;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class UnitTests {

    /*
    Test 0: Construct a new organisation unit, with a name and current budget
     */
    @BeforeEach @Test
    public void newOrganisationalUnit() {
        String name = "New Unit";
        int budget = 100;

        OrginisationalUnit OU = new OrginisationalUnit(name, budget);
    }
}
