package CAB302.Common;

import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores an asset, with a quantity, asset type and the organisational unit that owns the asset.
 */
@Entity
@Table(name = "Asset")
public class Asset extends BaseObject implements iGet, iList {

    private int quantity;

    @Column(name = "quantity")
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assetTypeID")
    private AssetType assetType;

    @Column(name = "assetTypeID")
    public AssetType getAssetType() {
        return this.assetType;
    }
    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organisationalUnitID")
    private OrganisationalUnit organisationalUnit;

    @JoinColumn(name = "organisationalUnitID")
    public OrganisationalUnit getOrganisationalUnit() {
        return this.organisationalUnit;
    }
    public void setOrganisationalUnit(OrganisationalUnit organisationalUnit) { this.organisationalUnit = organisationalUnit; }

    /**
     * Construct an empty asset object.
     */
    public Asset() { }

    /**
     * Used by the server side of the application to retrieve an object from the database. If the instance's asset type
     * is not null, the database will select where the asset types match and, if the instance's organisation unit ID
     * isn't null, the database will select where the organisational unit IDs match.
     * @return an object from the database that matches the select criteria, or null if none exists.
     */
    public BaseObject get() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Asset> criteria = criteriaBuilder.createQuery(Asset.class);
        Root<Asset> root = criteria.from(Asset.class);

        criteria.select(root).where(
                this.getAssetType() != null ? criteriaBuilder.equal(root.get("assetType"), this.getAssetType()) : criteriaBuilder.and(),
                this.getOrganisationalUnit() != null ? criteriaBuilder.equal(root.get("organisationalUnit"), this.getOrganisationalUnit()) : criteriaBuilder.and()
        );

        Query query = session.createQuery(criteria);

        BaseObject asset = null;

        try {
            asset = (BaseObject)query.getSingleResult();
        }
        catch (Exception ex) {

        }

        return asset;
    }

    /**
     * Used by the server side of the application to retrieve a list of objects from the database.If the instance's
     * asset type is not null, the database will select where the asset types match and, if the instance's organisation
     * unit ID isn't null, the database will select where the organisational unit IDs match.
     * @return a list of object matching the search criteria, or null if none exists.
     */
    public List<BaseObject> list() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Asset> criteria = criteriaBuilder.createQuery(Asset.class);
        Root<Asset> root = criteria.from(Asset.class);

        criteria.select(root).where(
                this.getAssetType() != null ? criteriaBuilder.equal(root.get("assetType"), this.getAssetType()) : criteriaBuilder.and(),
                this.getOrganisationalUnit() != null ? criteriaBuilder.equal(root.get("organisationalUnit"), this.getOrganisationalUnit()) : criteriaBuilder.and()
        );

        Query query = session.createQuery(criteria);

        List<BaseObject> assets = null;

        try {
            assets = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) {

        }

        return assets;
    }
}
