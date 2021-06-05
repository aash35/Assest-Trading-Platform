package CAB302.Client;

import CAB302.Client.Helper.Toast;
import CAB302.Common.*;
import CAB302.Common.Enums.*;
import CAB302.Common.Helpers.HibernateUtil;
import com.fasterxml.jackson.core.util.RequestPayload;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class initialises the client side GUI.
 */
public class GUI extends JFrame {

    /**
     * Method creates a new GUI frame and displays the login page.
     * @throws Exception
     */
    public GUI() throws Exception {

        ClientSettings clientSettings = new ClientSettings();

        Logger log = Logger.getLogger("org.hibernate");
        log.setLevel(Level.SEVERE);

        /**
         * Nested class allows the creation of notification toasts on separate threads from the main GUI.
         */
        class NotificationThread extends Thread {

            private JFrame frame;

            /**
             * Construct a new notification thread connected to a given frame.
             * @param frame The frame the notification toast will be displayed in.
             */
            public NotificationThread(JFrame frame) {
                this.frame = frame;
            }

            /**
             * Initialises an infinite loop to allow the notification thread to respond to user inputs
             * and display a toast notification when needed.
             */
            @Override
            public void run() {
                while (true) {

                    if (RuntimeSettings.CurrentUser != null) {
                        PayloadRequest request = new PayloadRequest();

                        request.setRequestPayloadType(RequestPayloadType.Notification);
                        request.setPayloadObject(RuntimeSettings.CurrentUser);

                        PayloadResponse response = null;

                        try {
                            response = new Client().SendRequest(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (response != null && response.getPayloadObject() != null) {

                            ArrayList<String> notifications = (ArrayList<String>)response.getPayloadObject();

                            for (String notification : notifications) {
                                Toast t;
                                t = new Toast(notification, this.frame);
                                t.showtoast();
                            }
                        }
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        NotificationThread notificationThread = new NotificationThread(this);

        notificationThread.start();

        //Sets the Look and Feel to Nimbus or SystemLookAndFeel if nimbus isn't found
        try {
            boolean nimbus = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel((info.getClassName()));
                    nimbus = true;
                    break;
                }
            }
            if (!nimbus) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (UnsupportedLookAndFeelException |
                ClassNotFoundException |
                IllegalAccessException |
                InstantiationException ignored) {
        }

        RuntimeSettings.Session = HibernateUtil.getHibernateSession();

        this.setTitle("Organisation Store");

        setLayout(new BorderLayout());
        Container container = getContentPane();

        ImageIcon img = new ImageIcon("src\\Main\\graphics\\blueBorder.png");
        setIconImage(img.getImage());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(800, 600));

        setMinimumSize(new Dimension(800, 600));

        //Panel to hold the button
        JPanel buttonPanel = new JPanel();
        //Button
        JButton button = new JButton("Button");
        buttonPanel.add(button);
        button.addActionListener(e -> {
            try {
                System.out.println(3 / 0);
            } catch (Exception x) {
                JOptionPane.showMessageDialog(null, x.getMessage());
            }
        });

        Login loginPanel = new Login(this);
        container.add(loginPanel, BorderLayout.CENTER);

        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }



}
