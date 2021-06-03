package CAB302.Common.Helpers;

import CAB302.Client.*;
import CAB302.Client.Admin.AssetType;
import CAB302.Client.MainLayout.MainLayout;
import CAB302.Client.Store.BuySellAsset;

import javax.swing.*;

public class NavigationHelper {
    public static void logout(JFrame frame) {
        frame.setContentPane(new Login(frame));

        frame.revalidate();
    }

    public static void mainMenu(JFrame frame) {
        frame.setContentPane(new MainLayout(frame));
        frame.revalidate();
    }
    public static void changePanel(JPanel panel, JPanel changeTo){
        panel.removeAll();
        panel.add(changeTo);
        panel.revalidate();
        panel.repaint();
    }
}
