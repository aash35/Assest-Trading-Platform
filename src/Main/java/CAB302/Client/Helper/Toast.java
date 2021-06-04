package CAB302.Client.Helper;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Modified code from https://www.geeksforgeeks.org/java-swing-creating-toast-message/
 */
public class Toast extends JFrame {
    //String of toast
    String s;

    // JWindow
    JWindow w;
    public Toast(String s, JFrame frame)
    {
        w = new JWindow();

        // make the background transparent
        w.setBackground(new Color(0, 0, 0, 0));

        // create a panel
        JPanel p = new JPanel() {
            public void paintComponent(Graphics g)
            {
                int wid = g.getFontMetrics().stringWidth(s);
                int hei = g.getFontMetrics().getHeight();

                // draw the boundary of the toast and fill it
                g.setColor(Color.black);
                g.fillRect(10, 10, wid + 30, hei + 10);
                g.setColor(Color.black);
                g.drawRect(10, 10, wid + 30, hei + 10);

                // set the color of text
                g.setColor(new Color(255, 255, 255, 240));
                g.drawString(s, 25, 27);
                int t = 250;

                // draw the shadow of the toast
                for (int i = 0; i < 4; i++) {
                    t -= 60;
                    g.setColor(new Color(0, 0, 0, t));
                    g.drawRect(10 - i, 10 - i, wid + 30 + i * 2,
                            hei + 10 + i * 2);
                }
            }
        };
        int width = 300;
        int height = 100;
        w.add(p);
        w.setSize(width, height);

        Rectangle bounds = frame.getBounds();
        Point panelPoint = frame.getLocationOnScreen();

        int x = panelPoint.x + bounds.width-(width/2);
        int y = panelPoint.y + bounds.height-(height/2);
        w.setLocation(x, y);
    }
    public Toast(String s, JPanel panel)
    {
        panel = panel;
        w = new JWindow();

        // make the background transparent
        w.setBackground(new Color(0, 0, 0, 0));

        // create a panel
        JPanel p = new JPanel() {
            public void paintComponent(Graphics g)
            {
                int wid = g.getFontMetrics().stringWidth(s);
                int hei = g.getFontMetrics().getHeight();

                // draw the boundary of the toast and fill it
                g.setColor(Color.black);
                g.fillRect(10, 10, wid + 30, hei + 10);
                g.setColor(Color.black);
                g.drawRect(10, 10, wid + 30, hei + 10);

                // set the color of text
                g.setColor(new Color(255, 255, 255, 240));
                g.drawString(s, 25, 27);
                int t = 250;

                // draw the shadow of the toast
                for (int i = 0; i < 4; i++) {
                    t -= 60;
                    g.setColor(new Color(0, 0, 0, t));
                    g.drawRect(10 - i, 10 - i, wid + 30 + i * 2,
                            hei + 10 + i * 2);
                }
            }
        };
        int width = 300;
        int height = 100;
        w.add(p);
        w.setSize(width, height);

        Rectangle bounds = panel.getBounds();
        Point panelPoint = panel.getLocationOnScreen();

        int x = panelPoint.x + bounds.width-(width/2);
        int y = panelPoint.y + bounds.height-(height/2);
        w.setLocation(x, y);
    }

    // function to pop up the toast
    public void showtoast()
    {
        try {
            w.setOpacity(1);
            w.setVisible(true);

            // wait for some time
            Thread.sleep(2000);

            // make the message disappear  slowly
            for (double d = 1.0; d > 0.2; d -= 0.1) {
                Thread.sleep(100);
                w.setOpacity((float)d);
            }

            // set the visibility to false
            w.setVisible(false);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
