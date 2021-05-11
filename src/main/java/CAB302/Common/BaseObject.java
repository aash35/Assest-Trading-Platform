package CAB302.Common;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public class BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public BaseObject() { }
}
