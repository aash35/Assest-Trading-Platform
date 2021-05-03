package Client;

import Common.Asset;
import Common.*;
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
import java.awt.image.BufferedImage;

public class GUI extends JFrame {
    public GUI() throws Exception {
        SessionFactory sessionFactory = setUp();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);

        cr.select(root).where(cb.equal(root.get("username"), "admin"));

        Query<User> query = session.createQuery(cr);

        User admin = query.getSingleResult();

        if (admin == null) {
            admin.setUsername("admin");
        }

        session.close();


        /*//Sets the Look and Feel to Nimbus or SystemLookAndFeel if nimbus isn't found
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

        setLayout(new BorderLayout());
        Container container = getContentPane();

        //removes the default icon
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(800, 600));


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Change");
        fileMenu.add("Login");
        fileMenu.add("Store");

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

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

        Login loginPanel = new Login();
        container.add(loginPanel, BorderLayout.CENTER);

        pack();

        setLocationRelativeTo(null);

        setVisible(true);*/
    }

    private SessionFactory setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }
}
