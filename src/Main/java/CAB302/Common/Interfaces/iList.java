package CAB302.Common.Interfaces;

import CAB302.Common.BaseObject;

import java.util.List;

/**
 * Interface implemented by the Common classes, contains the list() method.
 */
public interface iList {

    /**
     * Communicates with database and returns a list of BaseObject that matches criteria determined by the
     * implementing class.
     * @return a list of objects from the database that match the query criteria.
     */
    public List<BaseObject> list();
}
