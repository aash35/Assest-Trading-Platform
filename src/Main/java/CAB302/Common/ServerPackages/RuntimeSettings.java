package CAB302.Common.ServerPackages;

import CAB302.Common.User;
import CAB302.Server.Notification;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains the global runtime settings for the program.
 */
public class RuntimeSettings {
    public static User CurrentUser;

    public static org.hibernate.Session Session;

    public static String IP;

    public static Integer Port;

    public static List<Notification> notifications = new ArrayList<Notification>();
}
