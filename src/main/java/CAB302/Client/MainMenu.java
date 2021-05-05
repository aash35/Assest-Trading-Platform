package CAB302.Client;

import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.OrganisationalUnit;
import CAB302.Common.User;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenu extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel mainMenuLabel = new JLabel("Main Menu");

    JButton myAccountButton = new JButton("My Account");

    JButton storeButton = new JButton("Store");

    JButton ouButton = new JButton("Organisational Unit");

    JButton logoutButton = new JButton("Logout");

    public MainMenu(JFrame frame) {
        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        // First Column
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 1;
        gbc.gridy = 0;

        mainMenuLabel.setFont(new Font(mainMenuLabel.getFont().getFontName(), Font.PLAIN, 42));

        add(mainMenuLabel, gbc);

        // Second Column
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(myAccountButton, gbc);

        myAccountButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.myAccount(frame);
                    }
                });

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(storeButton, gbc);

        storeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.store(frame);
                    }
                });

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(ouButton, gbc);

        ouButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.organisationalUnit(frame);
                    }
                });

        // Last Row
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(logoutButton, gbc);

        logoutButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.logout(frame);
                    }
                });
    }
}
