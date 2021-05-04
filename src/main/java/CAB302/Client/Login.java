package CAB302.Client;

import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Helpers.HibernateUtil;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.OrganisationalUnit;
import CAB302.Common.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Login extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel usernameLabel = new JLabel("Username:");
    JTextField usernameField = new JTextField(10);

    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(10);

    JButton loginButton = new JButton("Login");

    public Login(JFrame frame){
        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        //// First Column
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        //// Second Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        loginButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        char[] charArray = passwordField.getPassword();

                        String username = usernameField.getText();
                        String password = new String(charArray);

                        String hashedPassword = SHA256HashHelper.generateHashedString(password);

                        User user = new User();

                        user.setUsername(username);
                        user.setHashedPassword(hashedPassword);

                        boolean isValid = false;

                        try {
                            isValid = user.isValid();
                        } catch (Exception exception) {
                        }

                        if (!isValid) {
                            User adminUser = new User();

                            adminUser.setUsername("admin");

                            String hashedAdminPassword = SHA256HashHelper.generateHashedString("admin");

                            adminUser.setHashedPassword(hashedAdminPassword);

                            boolean isAdminValid = false;

                            try {
                                isAdminValid = adminUser.isValid();
                            } catch (Exception exception) {
                            }

                            if (!isAdminValid) {

                                Session session = HibernateUtil.getHibernateSession();
                                session.beginTransaction();

                                adminUser.setAccountRoleType(AccountTypeRole.Administrator);

                                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                                CriteriaQuery<OrganisationalUnit> criteria = criteriaBuilder.createQuery(OrganisationalUnit.class);
                                Root<OrganisationalUnit> root = criteria.from(OrganisationalUnit.class);

                                criteria.select(root).where(criteriaBuilder.equal(root.get("unitName"), "Administrators"));

                                Query query = session.createQuery(criteria);

                                boolean isOUValid = false;

                                OrganisationalUnit ou = null;

                                try {
                                    ou = (OrganisationalUnit)query.getSingleResult();
                                } catch (Exception exception) {
                                }

                                if (ou == null) {
                                    ou.setAvailableCredit(100);
                                    ou.setUnitName("Administrators");

                                    session.save(ou);
                                }

                                adminUser.setOrganisationalUnit(ou);

                                session.save(adminUser);

                                session.getTransaction().commit();

                                session.close();
                            }
                        }
                        else {

                            frame.setContentPane(new Store());

                            JMenuBar menuBar = new JMenuBar();
                            JMenu fileMenu = new JMenu("Menu");
                            fileMenu.add("Store");
                            fileMenu.add("Logout");

                            menuBar.add(fileMenu);
                            frame.setJMenuBar(menuBar);

                            frame.revalidate();
                        }
                    }
                });

        //// Last Row
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 5;
        gbc.insets = new Insets(20,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton, gbc);


    }
}
