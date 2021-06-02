package CAB302.Client.MainLayout;

import CAB302.Common.Colors.Grey;
import CAB302.Common.Colors.LightBlue;
import CAB302.Common.Colors.Purple;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.RuntimeSettings;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWest extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JButton myAccountButton = new JButton("My Account");

    JButton storeButton = new JButton("Store");

    JButton ouButton = new JButton("Organisational Unit");

    JButton adminButton = new JButton("Administration");


    public MainWest(User user, JPanel panel) {
        setLayout(new GridBagLayout());
        setBackground(new Grey());
        setBorder(BorderFactory.createMatteBorder(2,2,2,0, Color.BLACK));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 2;

        add(myAccountButton, gbc);
        myAccountButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.myAccount(panel);
                    }
                });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(storeButton, gbc);
        storeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.store(panel);
                    }
                });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(ouButton, gbc);
        ouButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.organisationalUnit(panel);
                    }
                });

        if(user.getAccountRoleType() != AccountTypeRole.Standard){
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(adminButton, gbc);

            adminButton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            NavigationHelper.administation(panel);
                        }
                    });

        }
    }
}
