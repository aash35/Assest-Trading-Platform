package CAB302.Client.MainLayout;

import javax.swing.*;
import java.awt.*;

public class MainNorth extends JPanel {
    JLabel mainMenuLabel = new JLabel("Main Menu");

    public MainNorth() {
        mainMenuLabel.setFont(new Font(mainMenuLabel.getFont().getFontName(), Font.PLAIN, 42));
        add(mainMenuLabel);
    }
}
