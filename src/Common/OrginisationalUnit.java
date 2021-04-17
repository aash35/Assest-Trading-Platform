package Common;

import java.util.HashMap;

public class OrginisationalUnit {

    private String unitName;
    private HashMap<Asset, Integer> availableAssets;
    private int availableCredits;

    public OrginisationalUnit(){
        unitName = "Keepers of Potatos";
        availableCredits = 10;
    }
}
