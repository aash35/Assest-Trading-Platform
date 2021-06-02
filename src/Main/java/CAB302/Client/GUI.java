package CAB302.Client;

import CAB302.Common.*;
import CAB302.Common.Enums.*;
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

public class GUI extends JFrame {

    public GUI() throws Exception {

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
        this.setTitle("Organisation Store");

        setLayout(new BorderLayout());
        Container container = getContentPane();

        ImageIcon img = new ImageIcon("src\\Main\\graphics\\blueBorder.png");
        setIconImage(img.getImage());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(800, 600));

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
