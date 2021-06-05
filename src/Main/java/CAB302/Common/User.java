package CAB302.Common;

import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.ServerPackages.BaseObject;
import CAB302.Common.ServerPackages.RuntimeSettings;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import CAB302.Common.Interfaces.*;

/**
 * Class stores a user, with a username, a hashed password, an account role type, and an organisational unit the user
 * belongs to.
 */
@Entity
@Table(name = "User")
public class User extends BaseObject implements iGet, iList {

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "username", unique = true)
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    @Column(name = "password")
    private String hashedPassword;

    @Column(name = "password")
    public String getHashedPassword() { return this.hashedPassword; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    @Column(name = "accountRoleTypeID")
    @Enumerated(EnumType.ORDINAL)
    private AccountTypeRole accountRoleType;

    @Column(name = "accountRoleTypeID")
    public AccountTypeRole getAccountRoleType() { return this.accountRoleType; }
    public void setAccountRoleType(AccountTypeRole accountRoleType) { this.accountRoleType = accountRoleType; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organisationalUnitID")
    private OrganisationalUnit organisationalUnit;

    @Column(name = "organisationalUnitID")
    public OrganisationalUnit getOrganisationalUnit() { return this.organisationalUnit; }
    public void setOrganisationalUnit(OrganisationalUnit organisationalUnit) { this.organisationalUnit = organisationalUnit; }

    /**
     * Construct an empty user object.
     */
    public User() { }

    /**
     * Used by the server side of the application to retrieve an object from the database. The database will select
     * where the usernames and hashed passwords match.
     * @return an object from the database that matches the select criteria, or null if none exists.
     */
    public BaseObject get() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        if (hashedPassword == null) {
            criteria.select(root).where(
                    criteriaBuilder.equal(root.get("username"), this.getUsername())
            );
        }
        else {
            criteria.select(root).where(
                    criteriaBuilder.equal(root.get("username"), this.getUsername()),
                    criteriaBuilder.equal(root.get("hashedPassword"), this.getHashedPassword())
            );
        }

        Query query = session.createQuery(criteria);

        User user = null;

        try {
            user = (User) query.getSingleResult();
        }
        catch (Exception ex) { }

        return user;
    }

    /**
     * Used by the server side of the application to retrieve a list of objects from the database. If the instance's
     * hashed password is not null, the database will select where the usernames and hashed passwords match. If the
     * instance's hashed password is null, the database will select where the usernames match.
     * @return a list of object matching the search criteria, or null if none exists.
     */
    public List<BaseObject> list() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        if (hashedPassword == null) {
            criteria.select(root).where(
                    criteriaBuilder.equal(root.get("username"), this.getUsername())
            );
        }
        else {
            criteria.select(root).where(
                    criteriaBuilder.equal(root.get("username"), this.getUsername()),
                    criteriaBuilder.equal(root.get("hashedPassword"), this.getHashedPassword())
            );
        }

        Query query = session.createQuery(criteria);

        List<BaseObject> users = null;

        try {
            users = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) { }

        return users;
    }
}
