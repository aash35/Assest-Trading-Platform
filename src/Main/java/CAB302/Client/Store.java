package CAB302.Client;

import javax.swing.*;
import java.awt.*;

public class Store extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel storeLabel = new JLabel("Store");

    public Store() {
        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        // First Column
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        storeLabel.setFont(new Font(storeLabel.getFont().getFontName(), Font.PLAIN, 42));

        add(storeLabel, gbc);
    }
}
