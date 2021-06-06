package CAB302.Common;

import CAB302.Common.Interfaces.*;
import CAB302.Common.ServerPackages.BaseObject;
import CAB302.Common.ServerPackages.RuntimeSettings;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Class stores a organisational unit, with a name and quantity of available credits.
 */
@Entity
@Table(name = "OrganisationalUnit")
public class OrganisationalUnit extends BaseObject implements iGet, iList {

    @Column(name = "name")
    private String unitName;

    @Column(name = "name")
    public String getUnitName() { return this.unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }

    @Column(name = "credit")
    private int availableCredit;

    @Column(name = "credit")
    public int getAvailableCredit() { return this.availableCredit; }
    public void setAvailableCredit(int availableCredit) { this.availableCredit = availableCredit; }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organisationalUnit")
    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() { return this.users; }
    public void setUsers(List<User> users) { this.users = users; }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organisationalUnit")
    private List<Asset> assets = new ArrayList<Asset>();

    public List<Asset> getAssets() { return this.assets; }
    public void setAssets(List<Asset> assets) { this.assets = assets; }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organisationalUnit")
    private List<Trade> trades = new ArrayList<Trade>();

    public List<Trade> getTrades() { return this.trades; }
    public void setTrades(List<Trade> trades) { this.trades = trades; }

    /**
     * Constructs an empty organisational unit object.
     */
    public OrganisationalUnit() { }

    /**
     * Used by the server side of the application to retrieve an object from the database. The database will select
     * where the asset types match.
     * @return an object from the database that matches the select criteria, or null if none exists.
     */
    public BaseObject get() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrganisationalUnit> criteria = criteriaBuilder.createQuery(OrganisationalUnit.class);
        Root<OrganisationalUnit> root = criteria.from(OrganisationalUnit.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("unitName"), this.getUnitName()));

        Query query = session.createQuery(criteria);

        BaseObject ou = null;

        try {
            ou = (BaseObject)query.getSingleResult();
        }
        catch (Exception ex) {

        }

        return ou;
    }

    /**
     * Used by the server side of the application to retrieve a list of objects from the database. If the instance's
     * name is not null, the database will select where the names match. If the instance's name is null, the database
     * will select all objects in the table.
     * @return a list of object matching the search criteria, or null if none exists.
     */
    public List<BaseObject> list() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrganisationalUnit> criteria = criteriaBuilder.createQuery(OrganisationalUnit.class);
        Root<OrganisationalUnit> root = criteria.from(OrganisationalUnit.class);

        if (this.getUnitName() == null)
        {
            criteria.select(root);
        }
        else
        {
            criteria.select(root).where(criteriaBuilder.equal(root.get("unitName"), this.getUnitName()));
        }

        Query query = session.createQuery(criteria);

        List<BaseObject> ous = null;

        try {
            ous = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) {

        }

        return ous;
    }
}
