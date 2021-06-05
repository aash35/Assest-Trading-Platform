package CAB302.Common;

import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * This class stores an asset type, with a name and description.
 */
@Entity
@Table(name = "AssetType")
public class AssetType extends BaseObject implements iGet, iList {

    private String name;

    @Column(name = "name")
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    private String description;

    @Column(name = "description")
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "assetType")
    private List<Asset> assets = new ArrayList<Asset>();

    public List<Asset> getAssets() { return this.assets; }
    public void setAssets(List<Asset> assets) { this.assets = assets; }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assetType")
    private List<Trade> trades = new ArrayList<Trade>();

    public List<Trade> getTrades() { return this.trades; }
    public void setTrades(List<Trade> trades) { this.trades = trades; }

    /**
     * Construct and empty asset type object.
     */
    public AssetType() { }

    /**
     * Used by the server side of the application to retrieve an object from the database. If the instance's name
     * is not null, the database will select where the names match.
     * @return an object from the database that matches the select criteria, or null if none exists.
     */
    public BaseObject get() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<AssetType> criteria = criteriaBuilder.createQuery(AssetType.class);
        Root<AssetType> root = criteria.from(AssetType.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("name"), this.getName()));

        Query query = session.createQuery(criteria);

        AssetType assetType = null;

        try {
            assetType = (AssetType)query.getSingleResult();
        }
        catch (Exception ex) {

        }

        return assetType;
    }

    /**
     * Used by the server side of the application to retrieve a list of objects from the database.If the instance's
     * name is not null, the database will select where the names match and, if the instance's name is null, the
     * database will select all objects in the table.
     * @return a list of object matching the search criteria, or null if none exists.
     */
    public List<BaseObject> list() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<AssetType> criteria = criteriaBuilder.createQuery(AssetType.class);
        Root<AssetType> root = criteria.from(AssetType.class);

        if (this.getName() == null){
            criteria.select(root);
        }
        else{
            criteria.select(root).where(criteriaBuilder.equal(root.get("name"), this.getName()));
        }

        Query query = session.createQuery(criteria);

        List<BaseObject> assetTypes = null;

        try {
            assetTypes = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) {

        }

        return assetTypes;
    }
}
