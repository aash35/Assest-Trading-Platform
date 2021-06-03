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
    JPasswordField password = new JPasswordField(10);
    JPasswordField repeatPassword = new JPasswordField(10);


    public MyAccount(User user) {
        name = new JLabel("Name: " + user.getUsername());
        role = new JLabel("Role: " + user.getAccountRoleType());
        organisationUnit = new JLabel("Organisation: " + user.getOrganisationalUnit().getUnitName());
        organisationUnitCredits = new JLabel("Organisations Credit Balance: " + user.getOrganisationalUnit().getAvailableCredit());
        changePassword = new JButton("Change Password");

        passwordLabel = new JLabel("Password: ");
        repeatPasswordLabel = new JLabel("Repeat Password: ");
        submit = new JButton("Submit");



        name.setFont(name.getFont().deriveFont(Font.BOLD));
        role.setFont(role.getFont().deriveFont(Font.BOLD));
        organisationUnit.setFont(organisationUnit.getFont().deriveFont(Font.BOLD));
        organisationUnitCredits.setFont(organisationUnitCredits.getFont().deriveFont(Font.BOLD));


        setLayout(new GridBagLayout());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(role, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(organisationUnit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(organisationUnitCredits, gbc);

        gbc.insets = new Insets(30, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(changePassword, gbc);

        changePassword.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(changePassword);

                        gbc.anchor = GridBagConstraints.LINE_END;
                        gbc.gridwidth = 1;
                        gbc.gridx = 0;
                        gbc.gridy = 4;
                        add(passwordLabel, gbc);
                        gbc.gridwidth = 2;
                        gbc.gridx = 1;
                        gbc.gridy = 4;
                        add(password, gbc);

                        gbc.gridwidth = 1;
                        gbc.gridx = 0;
                        gbc.gridy = 5;
                        add(repeatPasswordLabel, gbc);
                        gbc.gridwidth = 2;
                        gbc.gridx = 1;
                        gbc.gridy = 5;
                        add(repeatPassword, gbc);

                        gbc.anchor = GridBagConstraints.CENTER;
                        gbc.gridwidth = 3;
                        gbc.gridx = 0;
                        gbc.gridy = 6;
                        add(submit, gbc);


                        revalidate();
                        repaint();
                    }
                });



    }
}
