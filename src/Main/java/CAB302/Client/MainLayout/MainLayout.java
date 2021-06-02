package CAB302.Client.MainLayout;

import javax.swing.*;
import java.awt.*;

public class MainLayout extends JPanel {
    JPanel northPanel = new MainNorth();
    JPanel centerPanel = new MainCentre();
    JPanel westPanel;

    public MainLayout(JFrame frame) {
        setBackground(new Color(243, 244, 246));
        westPanel =new MainWest(centerPanel, frame);
        setLayout(new BorderLayout());

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
    }
}
