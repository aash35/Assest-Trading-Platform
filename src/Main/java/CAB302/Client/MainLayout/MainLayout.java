package CAB302.Client.MainLayout;

import CAB302.Common.RuntimeSettings;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;

public class MainLayout extends JPanel {
    JPanel northPanel;
    public MainCentre centerPanel;
    JPanel westPanel;

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
