package CAB302.Common.Helpers;

import CAB302.Client.*;
import CAB302.Client.MainLayout.MainLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationHelper {
    public static void logout(JFrame frame) {
        frame.setContentPane(new Login(frame));

        frame.revalidate();
    }

    public static void mainMenu(JFrame frame) {
        frame.setContentPane(new MainLayout(frame));

        NavigationHelper.generateMenuBar(frame);
    }

    public static void store(JPanel frame) {
        frame.removeAll();
        frame.add(new Store());
        frame.revalidate();
        frame.repaint();

    }

    public static void myAccount(JPanel frame) {
        frame.removeAll();
        frame.add(new MyAccount());

        frame.revalidate();
        frame.repaint();
    }

    public static void organisationalUnit(JPanel frame) {
        frame.removeAll();
        frame.add(new OrganisationalUnit());

        frame.revalidate();
        frame.repaint();

    }

    public static void administation(JPanel frame) {
        frame.removeAll();
        frame.add(new Administration(frame));

        frame.revalidate();
        frame.repaint();

    }

    public static void assetType(JPanel frame) {
        frame.removeAll();
        frame.add(new AssetType());

        frame.revalidate();
        frame.repaint();
    }

    public static void generateMenuBar(JFrame frame) {

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.logout(frame);
            }
        });

        frame.revalidate();
    }
}
