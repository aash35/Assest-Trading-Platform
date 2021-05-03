package Client;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel usernameLabel = new JLabel("Username:");
    JTextField usernameField = new JTextField(10);
    JLabel passwordLabel = new JLabel("Password:");
    JTextField passwordField = new JTextField(10);
    JButton loginButton = new JButton("Login");

    public Login(){
        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        //// First Column
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        //// Second Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);


        //// Last Row
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 5;
        gbc.insets = new Insets(20,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton, gbc);

    }
}
