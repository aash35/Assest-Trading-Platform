package Common;

import Common.Enums.AccountTypeRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
public class User extends BaseClass {

    private String username;

    @Column(name = "username")
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    private String hashedPassword;

    @Column(name = "password")
    public String getHashedPassword() { return this.hashedPassword; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    private AccountTypeRole accountRoleType;

    @Column(name = "accountRoleTypeID")
    @Enumerated(EnumType.ORDINAL)
    public AccountTypeRole getAccountRoleType() { return this.accountRoleType; }
    public void setAccountRoleType(AccountTypeRole accountRoleType) { this.accountRoleType = accountRoleType; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organisationalUnitID")
    private OrganisationalUnit organisationalUnit;

    @Column(name = "organisationalUnitID")
    public OrganisationalUnit getOrganisationalUnit() { return this.organisationalUnit; }
    public void setOrganisationalUnit(OrganisationalUnit organisationalUnit) { this.organisationalUnit = organisationalUnit; }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdByUser")
    private List<Asset> assets = new ArrayList<Asset>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdByUser")
    private List<Trade> trades = new ArrayList<Trade>();

    public User() { }
}
