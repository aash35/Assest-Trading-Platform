package CAB302.Common;

import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Trade")
public class Trade extends BaseObject implements iGet, iList {

    private TradeTransactionType transactionType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transactionTypeID")
    public TradeTransactionType getTransactionType() { return this.transactionType; }
    public void setTransactionType(TradeTransactionType transactionType) { this.transactionType = transactionType; }

    private int quantity;

    @Column(name = "quantity")
    public int getQuantity() { return this.quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    private double price;

    @Column(name = "price")
    public double getPrice() { return this.price; }
    public void setPrice(double price) { this.price = price; }

    private Timestamp createdDate;

    @Column(name = "createdDate")
    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }

    private TradeStatus status;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "statusID")
    public TradeStatus getStatus() { return this.status; }
    public void setStatus(TradeStatus status) { this.status = status; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdByUserID")
    private User createdByUser;

    @Column(name = "createdByUserID")
    public User getCreatedByUser() { return this.createdByUser; }
    public void setCreatedByUser(User createdByUser) { this.createdByUser = createdByUser; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assetID")
    private Asset asset;

    @Column(name = "assetID")
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }

    public Trade() { }

    public BaseObject get() {
        Session session = RuntimeSettings.Session;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trade> criteria = criteriaBuilder.createQuery(Trade.class);
        Root<Trade> root = criteria.from(Trade.class);

        criteria.select(root);

        if (this.getAsset() != null) {
            criteria.where(criteriaBuilder.equal(root.get("asset"), this.getAsset()));
        }

        if (this.getStatus() != null) {
            criteria.where(criteriaBuilder.equal(root.get("status"), this.getStatus()));
        }

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

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trade> criteria = criteriaBuilder.createQuery(Trade.class);
        Root<Trade> root = criteria.from(Trade.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("asset"), this.getAsset()));

        Query query = session.createQuery(criteria);

        List<BaseObject> trades = null;

        try {
            trades = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) {

        }

        session.flush();

        return trades;
    }
}
