package CAB302.Common;

import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Asset")
public class Asset extends BaseClass {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    private List<Trade> trades = new ArrayList<Trade>();

    public Asset() { }
}
