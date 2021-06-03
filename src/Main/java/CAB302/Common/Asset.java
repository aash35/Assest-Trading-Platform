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
    @JoinColumn(name = "createdByUserID")
    private User createdByUser;

    @Column(name = "createdByUserID")
    public User getCreatedByUser() {
        return this.createdByUser;
    }
    public void setCreatedByUserID(User createdByUserID) {
        this.createdByUser = createdByUserID;
    }



    public Asset() { }

    public BaseObject get() {
        Session session = HibernateUtil.getHibernateSession();

        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Asset> criteria = criteriaBuilder.createQuery(Asset.class);
        Root<Asset> root = criteria.from(Asset.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("assetType"), this.getAssetType()));

        Query query = session.createQuery(criteria);

        BaseObject asset = null;

        try {
            asset = (BaseObject)query.getSingleResult();
        }
        catch (Exception ex) {

        }

        session.close();

        return asset;
    }

    public List<BaseObject> list() {
        Session session = HibernateUtil.getHibernateSession();

        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Asset> criteria = criteriaBuilder.createQuery(Asset.class);
        Root<Asset> root = criteria.from(Asset.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("assetType"), this.getAssetType()));

        Query query = session.createQuery(criteria);

        List<BaseObject> assets = null;

        try {
            assets = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) {

        }

        session.close();

        return assets;
    }
}
