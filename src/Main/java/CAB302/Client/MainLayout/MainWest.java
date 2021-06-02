package CAB302.Client.MainLayout;

import CAB302.Common.Helpers.NavigationHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWest extends JPanel {
    JLabel mainMenuLabel = new JLabel("Main Menu");

    JButton myAccountButton = new JButton("My Account");

    JButton storeButton = new JButton("Store");

    JButton ouButton = new JButton("Organisational Unit");

    JButton adminButton = new JButton("Administration");

    JButton logoutButton = new JButton("Logout");

    public MainWest(JPanel panel, JFrame contentFrame) {
        add(myAccountButton);
        myAccountButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.myAccount(panel);
                    }
                });

        add(storeButton);
        storeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.store(panel);
                    }
                });

       add(ouButton);
        ouButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.organisationalUnit(panel);
                    }
                });

        add(adminButton);

        adminButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.administation(panel);
                    }
                });


        add(logoutButton);

        logoutButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.logout(contentFrame);
                    }
                });
    }
}
