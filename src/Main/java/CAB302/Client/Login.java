package CAB302.Client;

import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.Helpers.SHA256HashHelper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;

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

        // First Column
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        // Second Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    passwordField.grabFocus();
                }

                //need to remove on the way out
                if (e.getKeyChar()==KeyEvent.VK_ESCAPE) {

                    User adminUser = new User();

                    adminUser.setUsername("admin");
                    adminUser.setAccountRoleType(AccountTypeRole.Administrator);

                    CAB302.Common.OrganisationalUnit adminUserOrg = new CAB302.Common.OrganisationalUnit();
                    adminUserOrg.setUnitName("Potatos");
                    adminUserOrg.setAvailableCredit(50000);

                    adminUser.setOrganisationalUnit(adminUserOrg);
                    RuntimeSettings.CurrentUser = adminUser;

                    NavigationHelper.mainMenu(frame);
                }
                //need to remove on the way out
                if ((int)e.getKeyChar()==96) {

                    User user = new User();

                    user.setUsername("user");
                    user.setAccountRoleType(AccountTypeRole.Standard);

                    CAB302.Common.OrganisationalUnit userOrg = new CAB302.Common.OrganisationalUnit();
                    userOrg.setUnitName("Potatos Farmers");
                    userOrg.setAvailableCredit(5000);

                    user.setOrganisationalUnit(userOrg);
                    RuntimeSettings.CurrentUser = user;

                    NavigationHelper.mainMenu(frame);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    loginButton.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

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

                        PayloadRequest request = new PayloadRequest();

                        request.setPayloadObject(user);
                        request.setRequestPayloadType(RequestPayloadType.Get);

                        PayloadResponse response = null;
                        try {
                            response = new Client().SendRequest(request);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        user = (CAB302.Common.User)response.getPayloadObject();

                        if (user != null) {
                            RuntimeSettings.CurrentUser = user;

                            NavigationHelper.mainMenu(frame);
                        }
                    }
                });

        // Last Row
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 5;
        gbc.insets = new Insets(20,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton, gbc);


    }
}
