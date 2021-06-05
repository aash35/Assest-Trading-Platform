package CAB302.Common;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Superclass inherited by all Common package classes. Gives each object an ID for the database to use
 * use as a primary key.
 */
@MappedSuperclass
public class BaseObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    /**
     * Construct an empty base object.
     */
    public BaseObject() { }
}
