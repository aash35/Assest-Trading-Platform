package CAB302.Common;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "AssetType")
public class AssetType extends BaseClass {

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

    public AssetType() { }
}
