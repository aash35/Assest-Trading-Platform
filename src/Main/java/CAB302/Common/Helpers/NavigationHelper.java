package CAB302.Common.Helpers;

import CAB302.Client.*;
import CAB302.Client.MainLayout.MainLayout;

import javax.swing.*;
import java.awt.*;

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
    public static void buySellOrder(JPanel panel, CAB302.Common.AssetType assetType) {
        panel.removeAll();
        panel.add(new BuySellAsset(assetType));
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
