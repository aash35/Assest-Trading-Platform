package CAB302.Common;

import CAB302.Server.Notification;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class RuntimeSettings {
    public static User CurrentUser;

    public static org.hibernate.Session Session;

    public static String IP;

    public static Integer Port;

    public static List<Notification> notifications = new ArrayList<Notification>();
}
