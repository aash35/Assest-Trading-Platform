package CAB302.Client;

import javax.swing.*;
import java.awt.*;

public class OrganisationalUnit extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel OULabel = new JLabel("Orginisation Unit");
    public OrganisationalUnit() {

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

        OULabel.setFont(new Font(OULabel.getFont().getFontName(), Font.PLAIN, 42));

        add(OULabel, gbc);

    }
}
