package CAB302.Common.Helpers;

import CAB302.Client.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationHelper {
    public static void logout(JFrame frame) {
        frame.setContentPane(new Login(frame));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        fileMenu.add("Login");

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.revalidate();
    }

    public static void mainMenu(JFrame frame) {
        frame.setContentPane(new MainMenu(frame));

        NavigationHelper.generateMenuBar(frame);
    }

    public static void store(JFrame frame) {
        frame.setContentPane(new Store(frame));

        NavigationHelper.generateMenuBar(frame);
    }

    public static void myAccount(JFrame frame) {
        frame.setContentPane(new MyAccount(frame));

        NavigationHelper.generateMenuBar(frame);
    }

    public static void organisationalUnit(JFrame frame) {
        frame.setContentPane(new OrganisationalUnit(frame));

        NavigationHelper.generateMenuBar(frame);
    }

    public static void administation(JFrame frame) {
        frame.setContentPane(new Administration(frame));

        NavigationHelper.generateMenuBar(frame);
    }

    public static void assetType(JFrame frame) {
        frame.setContentPane(new AssetType(frame));

        NavigationHelper.generateMenuBar(frame);
    }

    public static void generateMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");

        JMenuItem menuItem = new JMenuItem("Main Menu");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.mainMenu(frame);
            }
        });

        fileMenu.add(menuItem);

        JMenuItem storeItem = new JMenuItem("Store");
        storeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.store(frame);
            }
        });

        fileMenu.add(storeItem);

        JMenuItem myAccountItem = new JMenuItem("My Account");
        myAccountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.myAccount(frame);
            }
        });

        fileMenu.add(myAccountItem);

        JMenuItem ouItem = new JMenuItem("Organisational Unit");
        ouItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.organisationalUnit(frame);
            }
        });

        fileMenu.add(ouItem);

        JMenuItem administrationItem = new JMenuItem("Administration");
        administrationItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.administation(frame);
            }
        });

        fileMenu.add(administrationItem);

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.logout(frame);
            }
        });

        fileMenu.add(logoutItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.revalidate();
    }
}
