package CAB302.Client;

import CAB302.Client.Admin.AssetType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyAccount extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel name;
    JLabel role;
    JLabel organisationUnit;
    JLabel organisationUnitCredits;
    JButton changePassword;
    JButton submit;

    JLabel passwordLabel;
    JLabel repeatPasswordLabel;
    JPasswordField password = new JPasswordField();
    JPasswordField repeatPassword = new JPasswordField();


    public MyAccount(User user) {
        name = new JLabel("Name: ");
        role = new JLabel("Role: ");
        organisationUnit = new JLabel("Organisation: ");
        organisationUnitCredits = new JLabel("Organisations Credit Balance: ");
        changePassword = new JButton("Change Password");

        passwordLabel = new JLabel("Password: ");
        repeatPasswordLabel = new JLabel("Repeat Password: ");
        submit = new JButton("Submit");


        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;


        // First Column
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(name, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(role, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(organisationUnit, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(organisationUnitCredits, gbc);



        changePassword.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(changePassword);

                    }
                });



    }
}
