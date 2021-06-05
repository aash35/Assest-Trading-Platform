package CAB302.Common.Helpers;

import CAB302.Client.*;
import CAB302.Client.MainLayout.MainLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class NavigationHelper {
    public static void logout(JFrame frame) {
        frame.setContentPane(new Login(frame));

        frame.revalidate();
    }

    static class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {

            GUI gui = (GUI)e.getComponent();

            var rootPane = gui.getRootPane();

            MainLayout mainLayout = (MainLayout)rootPane.getContentPane();

            int width = mainLayout.getWidth();

            JScrollPane scrollPane = mainLayout.centerPanel.ouFrame.scrollPane;

            scrollPane.setPreferredSize(new Dimension(width - 150, 110));

            scrollPane.invalidate();

            scrollPane.repaint();
        }
    }

    public static void frameWindowStateChanged(WindowEvent e){



        GUI gui = (GUI)e.getComponent();

        var rootPane = gui.getRootPane();

        MainLayout mainLayout = (MainLayout)rootPane.getContentPane();


        int width = 0;

        if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

            width = (int)size.getWidth();
        }
        else {
            width = mainLayout.getWidth();
        }

        JScrollPane scrollPane = mainLayout.centerPanel.ouFrame.scrollPane;

        scrollPane.setPreferredSize(new Dimension(width - 150, 110));

        scrollPane.invalidate();

        scrollPane.repaint();
    }

    public static void mainMenu(JFrame frame) {
        frame.setContentPane(new MainLayout(frame));
        frame.revalidate();

        frame.addComponentListener(new ResizeListener());
        frame.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent arg0) {
                frameWindowStateChanged(arg0);
            }
        });
    }
    public static void changePanel(JPanel panel, JPanel changeTo){
        panel.removeAll();
        panel.add(changeTo);
        panel.revalidate();
        panel.repaint();
    }
}
