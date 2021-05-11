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

@Entity
@Table(name = "AssetType")
public class AssetType extends BaseObject implements iGet {

    private String name;

    @Column(name = "name")
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    private String description;

    @Column(name = "description")
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assetType")
    private List<Asset> assets = new ArrayList<Asset>();
    public List<Asset> getAssets() { return this.assets; }
    public void setAssets(List<Asset> assets) { this.assets = assets; }

    public AssetType() { }

    public BaseObject get() {
        Session session = HibernateUtil.getHibernateSession();

        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<AssetType> criteria = criteriaBuilder.createQuery(AssetType.class);
        Root<AssetType> root = criteria.from(AssetType.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("name"), this.getName()));

        Query query = session.createQuery(criteria);

        AssetType assetType = null;

        try {
            assetType = (AssetType)query.getSingleResult();
        }
        catch (Exception ex) { }

        session.close();

        return assetType;
    }
}
