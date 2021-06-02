package CAB302.Common;

import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Interfaces.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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

    public OrganisationalUnit() { }

    public BaseObject get() {
        Session session = HibernateUtil.getHibernateSession();

        session.beginTransaction();

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

        session.close();

        return ou;
    }

    public List<BaseObject> list() {
        Session session = HibernateUtil.getHibernateSession();

        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrganisationalUnit> criteria = criteriaBuilder.createQuery(OrganisationalUnit.class);
        Root<OrganisationalUnit> root = criteria.from(OrganisationalUnit.class);

        criteria.select(root).where(criteriaBuilder.equal(root.get("unitName"), this.getUnitName()));

        Query query = session.createQuery(criteria);

        List<BaseObject> ous = null;

        try {
            ous = (List<BaseObject>)query.getResultList();
        }
        catch (Exception ex) {

        }

        session.close();

        return ous;
    }
}
