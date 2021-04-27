package Common;

import java.util.List;

public class OrganisationalUnit {

    public int id;
    public String name;
    public List<Asset> availableAssets;
    public int credits;
    public List<User> users;
    public List<Trade> trades;

    public OrganisationalUnit() { }
}
