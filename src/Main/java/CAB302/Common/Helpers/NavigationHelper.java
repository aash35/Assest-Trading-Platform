package CAB302.Common.Helpers;

import CAB302.Client.*;
import CAB302.Client.MainLayout.MainLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * Class contains method that handle the GUI navigation between pages.
 */
public class NavigationHelper {
    /**
     * Method called when the application user presses the logout button. Returns the user to the login screen.
     * @param frame the GUI container frame.
     */
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

        int sidePanelWidth = 0;

        try {
            Thread.sleep(200);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

            width = (int)size.getWidth();

            sidePanelWidth = mainLayout.westPanel.getWidth();
        }
        else {
            mainLayout.invalidate();

            mainLayout.repaint();

            width = mainLayout.getWidth();

            sidePanelWidth = mainLayout.westPanel.getWidth();
        }

        JScrollPane scrollPane = mainLayout.centerPanel.ouFrame.scrollPane;


        scrollPane.setPreferredSize(new Dimension(width - sidePanelWidth, 110));

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

    /**
     * Used to change the GUI content panel when navigating the GUI.
     * @param panel the GUI content panel.
     * @param changeTo the content panel that is being navigated to.
     */
    public static void changePanel(JPanel panel, JPanel changeTo){
        panel.removeAll();
        panel.add(changeTo);
        panel.revalidate();
        panel.repaint();
    }
}
