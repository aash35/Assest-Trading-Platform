package CAB302.Common;

import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Helpers.HibernateUtil;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.*;

import javax.persistence.*;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import CAB302.Common.Interfaces.*;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdByUser")
    private List<Trade> trades = new ArrayList<Trade>();

    public List<Trade> getTrades() { return this.trades; }
    public void setTrades(List<Trade> trades) { this.trades = trades; }

    public User() { }

    public BaseObject get() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(root).where(
                criteriaBuilder.equal(root.get("username"), this.getUsername()),
                criteriaBuilder.equal(root.get("hashedPassword"), this.getHashedPassword())
        );

        Query query = session.createQuery(criteria);

        User user = null;

        try {
            user = (User) query.getSingleResult();
        }
        catch (Exception ex) { }

        return user;
    }

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
