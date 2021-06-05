package CAB302.Server;

/**
 * Class handles notifications passed to the runtime settings.
 */
public class Notification {
    private int ouID;

    public int getOuID() { return this.ouID; }
    public void setOuID(int ouID) {this.ouID = ouID;}

    private String notificationMessage;
    public String getNotificationMessage() { return this.notificationMessage; }
    public void setNotificationMessage(String notificationMessage) {this.notificationMessage = notificationMessage;}
}
