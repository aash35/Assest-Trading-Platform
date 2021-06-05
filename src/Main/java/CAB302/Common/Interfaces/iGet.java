package CAB302.Common.Interfaces;

import CAB302.Common.ServerPackages.BaseObject;

/**
 * Interface implemented by the Common classes, contains the get() method.
 */
public interface iGet {

    /**
     * Communicates with database and returns a BaseObject that matches criteria determined by the implementing
     * class.
     * @return an object from the database that matches the query criteria.
     */
    public BaseObject get();
}
