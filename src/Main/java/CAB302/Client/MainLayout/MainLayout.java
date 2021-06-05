package CAB302.Client.MainLayout;

import CAB302.Common.RuntimeSettings;
import CAB302.Common.User;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;

/**
 * Class creates the layout of the application GUI, consisting of a navigation sidebar, a header area and
 * the central display area.
 */
public class MainLayout extends JPanel {
    JPanel northPanel;
    public MainCentre centerPanel;
    public JPanel westPanel;

    /**
     * Constructs the layout of the GUI system. Calls the constructors for the MainNorth, MainCentre and
     * MainWest classes.
     * @param frame gets passed to the MainNorth class constructor.
     */
    public MainLayout(JFrame frame) {

        User user = RuntimeSettings.CurrentUser;
        northPanel = new MainNorth(user, frame);
        centerPanel = new MainCentre(user);
        westPanel = new MainWest(user, centerPanel);

        setLayout(new BorderLayout());

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
    }
}
