package CAB302.Common;

import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Trade")
public class Trade extends BaseObject implements iGet, iList {

    @Column(name = "transactionTypeID")
    @Enumerated(EnumType.ORDINAL)
    private TradeTransactionType transactionType;

    @Column(name = "transactionTypeID")
    public TradeTransactionType getTransactionType() { return this.transactionType; }
    public void setTransactionType(TradeTransactionType transactionType) { this.transactionType = transactionType; }

    private int quantity;

    @Column(name = "quantity")
    public int getQuantity() { return this.quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    private int price;

    @Column(name = "price")
    public int getPrice() { return this.price; }
    public void setPrice(int price) { this.price = price; }

    @Column(name = "createdDate")
    private Timestamp createdDate;

    @Column(name = "createdDate")
    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }

    @Column(name = "statusID")
    private TradeStatus status;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "statusID")
    public TradeStatus getStatus() { return this.status; }
    public void setStatus(TradeStatus status) { this.status = status; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organisationalUnitID")
    private OrganisationalUnit organisationalUnit;

    @Column(name = "organisationalUnitID")
    public OrganisationalUnit getOrganisationalUnit() { return this.organisationalUnit; }
    public void setOrganisationalUnit(OrganisationalUnit organisationalUnit) { this.organisationalUnit = organisationalUnit; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assetTypeID")
    private AssetType assetType;

    @Column(name = "assetTypeID")
    public AssetType getAssetType() { return assetType; }
    public void setAssetType(AssetType assetType) { this.assetType = assetType; }

    public Trade() { }

    public BaseObject get() {

        Session session = RuntimeSettings.Session;

        HibernateUtil.openOrGetTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trade> criteria = criteriaBuilder.createQuery(Trade.class);
        Root<Trade> root = criteria.from(Trade.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("assetType"), this.getAssetType()));

        Query query = session.createQuery(criteria);

        BaseObject trade = null;

        try {
            trade = (BaseObject)query.getSingleResult();
        }
        catch (Exception ex) {

        }

        return trade;
    }

    public List<BaseObject> list() {

        Session session = RuntimeSettings.Session;

        HibernateUtil.openOrGetTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trade> criteria = criteriaBuilder.createQuery(Trade.class);
        Root<Trade> root = criteria.from(Trade.class);

        criteria.select(root).where(
                this.getAssetType() != null ? criteriaBuilder.equal(root.get("assetType"), this.getAssetType()) : criteriaBuilder.and(),
                this.getTransactionType() != null ? criteriaBuilder.equal(root.get("transactionType"), this.getTransactionType()) : criteriaBuilder.and(),
                this.getStatus() != null ? criteriaBuilder.equal(root.get("status"), this.getStatus()) : criteriaBuilder.and(),
                this.getOrganisationalUnit() != null ? criteriaBuilder.equal(root.get("organisationalUnit"), this.getOrganisationalUnit()) : criteriaBuilder.and()
        );

        Query query = session.createQuery(criteria);

        List<BaseObject> trades = null;

        try {
            trades = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) {

        }

        return trades;
    }
}
