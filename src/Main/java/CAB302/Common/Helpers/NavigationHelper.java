package CAB302.Common.Helpers;

import CAB302.Client.*;
import CAB302.Client.MainLayout.MainCentre;
import CAB302.Client.MainLayout.MainLayout;
import CAB302.Client.Organisation.OrganisationalUnit;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;

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

            int height = mainLayout.getHeight();

            JScrollPane scrollPane = mainLayout.centerPanel.ouFrame.scrollPane;

            JScrollPane tradePane = mainLayout.centerPanel.ouFrame.currentTradesPanelOne;

            int topScrollPaneHeight = scrollPane.getHeight();

            int finalHeight = height - topScrollPaneHeight;

            scrollPane.setPreferredSize(new Dimension(width - 150, 110));

            tradePane.setPreferredSize(new Dimension(600, finalHeight - 150));

            scrollPane.invalidate();
            tradePane.invalidate();

            scrollPane.repaint();
            tradePane.repaint();
        }
    }

    public static void frameWindowStateChanged(WindowEvent e){

        GUI gui = (GUI)e.getComponent();

        var rootPane = gui.getRootPane();

        MainLayout mainLayout = (MainLayout)rootPane.getContentPane();

        int width = 0;
        int height = 0;

        int finalHeight = 0;

        int sidePanelWidth = 0;

        try {
            Thread.sleep(200);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        JScrollPane scrollPane = mainLayout.centerPanel.ouFrame.scrollPane;

        int topScrollPaneHeight = scrollPane.getHeight();

        if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            mainLayout.invalidate();

            mainLayout.repaint();

            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

            width = (int)size.getWidth();

            height = (int)size.getHeight();

            finalHeight = height - topScrollPaneHeight - 50;

            sidePanelWidth = mainLayout.westPanel.getWidth();
        }
        else {
            mainLayout.invalidate();

            mainLayout.repaint();

            width = e.getWindow().getWidth();

            height = e.getWindow().getHeight();

            finalHeight = height - topScrollPaneHeight;

            sidePanelWidth = mainLayout.westPanel.getWidth();
        }

        JScrollPane tradePane = mainLayout.centerPanel.ouFrame.currentTradesPanelOne;

        scrollPane.setPreferredSize(new Dimension(width - sidePanelWidth, 110));

        tradePane.setPreferredSize(new Dimension(600, finalHeight - 150));

        scrollPane.invalidate();
        tradePane.invalidate();

        scrollPane.repaint();
        tradePane.repaint();

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

<<<<<<< HEAD
=======
    /**
     * Used to change the GUI content panel when navigating the GUI.
     * @param panel the GUI content panel.
     * @param changeTo the content panel that is being navigated to.
     */
>>>>>>> JavaDocCommenting
    public static void changePanel(JPanel panel, JPanel changeTo){

        panel.removeAll();
        panel.add(changeTo);

        if (changeTo instanceof OrganisationalUnit) {

            int width = panel.getWidth();

            int height = panel.getHeight();

            OrganisationalUnit ouFrame = (OrganisationalUnit)changeTo;

            JScrollPane scrollPane = ouFrame.scrollPane;

            JScrollPane tradePane = ouFrame.currentTradesPanelOne;

            int topScrollPaneHeight = scrollPane.getHeight();

            int finalHeight = height - topScrollPaneHeight;

            scrollPane.setPreferredSize(new Dimension(width - 150, 110));

            tradePane.setPreferredSize(new Dimension(600, finalHeight - 150));

            scrollPane.invalidate();
            tradePane.invalidate();

            scrollPane.repaint();
            tradePane.repaint();
        }

        panel.revalidate();
        panel.repaint();
    }
}
