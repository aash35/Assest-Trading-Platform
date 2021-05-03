package CAB302.Common;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public class BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public BaseClass() { }
}
