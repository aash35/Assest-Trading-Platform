package CAB302.Common;

import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Trade")
public class Trade extends BaseClass {

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
}
