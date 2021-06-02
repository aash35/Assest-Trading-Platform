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
        frame.revalidate();
    }

    public static void store(JPanel panel) {
        panel.removeAll();
        panel.add(new Store());
        panel.revalidate();
        panel.repaint();
    }

    public static void myAccount(JPanel panel) {
        panel.removeAll();
        panel.add(new MyAccount());

        panel.revalidate();
        panel.repaint();
    }

    public static void organisationalUnit(JPanel panel) {
        panel.removeAll();
        panel.add(new OrganisationalUnit());

        panel.revalidate();
        panel.repaint();

    }

    public static void administation(JPanel panel) {
        panel.removeAll();
        panel.add(new Administration(panel));
        panel.revalidate();
        panel.repaint();
    }

    public static void assetType(JPanel panel) {
        panel.removeAll();
        panel.add(new AssetType());
        panel.revalidate();
        panel.repaint();
    }
}
