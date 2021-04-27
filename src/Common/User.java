package Common;

import Common.Enums.AccountTypeRole;

public class User extends BaseClass {

    public String username;
    public String hashedPassword;
    public AccountTypeRole assetTypeRole;
    public OrganisationalUnit organisationalUnit;

    public User() { }
}
