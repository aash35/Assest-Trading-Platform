package Common;

import java.util.HashMap;

public class OrganisationalUnit {

    private String unitName;
    private HashMap<Asset, Integer> availableAssets;
    private int availableCredits;

    public OrganisationalUnit(){
        unitName = "Keepers of Potatos";
        availableCredits = 10;
    }
}
