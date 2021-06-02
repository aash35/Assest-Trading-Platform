package CAB302.Client;

import javax.swing.*;
import java.awt.*;

public class MyAccount extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel AccountLabel = new JLabel("Account");
    public MyAccount() {

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

        AccountLabel.setFont(new Font(AccountLabel.getFont().getFontName(), Font.PLAIN, 42));

        add(AccountLabel, gbc);

    }
}
