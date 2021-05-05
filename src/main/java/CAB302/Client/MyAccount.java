package CAB302.Client;

import javax.swing.*;
import java.awt.*;

public class MyAccount extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    public MyAccount(JFrame frame) {

        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

    }
}
