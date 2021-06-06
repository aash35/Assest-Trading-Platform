package CAB302.Client;


import CAB302.Client.Helper.Toast;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.SHA256HashHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Class constructs and displays the account screen for the logged in user.
 */
public class MyAccount extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel name;
    JLabel role;
    JLabel organisationUnit;
    JLabel organisationUnitCredits;
    JButton changePassword;
    JButton submit;

    JPanel toastPanel = new JPanel();

    JLabel passwordLabel;
    JLabel repeatPasswordLabel;
    JPasswordField password = new JPasswordField(10);
    JPasswordField repeatPassword = new JPasswordField(10);

    User selectedUser;

    /**
     * Construct the account page for the logged in user.
     * @param user the currently logged in user.
     * @param panel the container for the account page.
     */
    public MyAccount(User user, JPanel panel) {
        selectedUser = user;
        name = new JLabel("Name: ");
        role = new JLabel("Role: ");
        organisationUnit = new JLabel("Organisation: ");
        organisationUnitCredits = new JLabel("Organisations Credit Balance: ");
        changePassword = new JButton("Change Password");

        passwordLabel = new JLabel("Password: ");
        repeatPasswordLabel = new JLabel("Repeat Password: ");
        submit = new JButton("Submit");


        JLabel username = new JLabel(user.getUsername());
        JLabel userRole = new JLabel("" + user.getAccountRoleType());
        JLabel userOrg = new JLabel(user.getOrganisationalUnit().getUnitName());
        JLabel userOrgCredit = new JLabel("" + user.getOrganisationalUnit().getAvailableCredit());
        name.setFont(name.getFont().deriveFont(Font.BOLD));
        role.setFont(role.getFont().deriveFont(Font.BOLD));
        organisationUnit.setFont(organisationUnit.getFont().deriveFont(Font.BOLD));
        organisationUnitCredits.setFont(organisationUnitCredits.getFont().deriveFont(Font.BOLD));


        setLayout(new GridBagLayout());

        JPanel detailsOne = new JPanel();
        detailsOne.add(name);
        detailsOne.add(username);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(detailsOne, gbc);


        JPanel detailsTwo = new JPanel();
        detailsTwo.add(role);
        detailsTwo.add(userRole);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(detailsTwo, gbc);

        JPanel detailsThree = new JPanel();
        detailsThree.add(organisationUnit);
        detailsThree.add(userOrg);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(detailsThree, gbc);

        JPanel detailsFour = new JPanel();
        detailsFour.add(organisationUnitCredits);
        detailsFour.add(userOrgCredit);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(detailsFour, gbc);

        gbc.insets = new Insets(30, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(changePassword, gbc);


        changePassword.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(changePassword);
                        JLabel errorMessage = new JLabel("");
                        errorMessage.setForeground(Color.red);

                        gbc.anchor = GridBagConstraints.LINE_END;
                        gbc.gridwidth = 1;
                        gbc.gridx = 0;
                        gbc.gridy = 4;
                        add(passwordLabel, gbc);
                        gbc.gridwidth = 2;
                        gbc.gridx = 1;
                        gbc.gridy = 4;
                        add(password, gbc);

                        gbc.gridwidth = 1;
                        gbc.gridx = 0;
                        gbc.gridy = 5;
                        add(repeatPasswordLabel, gbc);
                        gbc.gridwidth = 2;
                        gbc.gridx = 1;
                        gbc.gridy = 5;
                        add(repeatPassword, gbc);

                        gbc.anchor = GridBagConstraints.CENTER;
                        gbc.gridwidth = 3;
                        gbc.gridx = 0;
                        gbc.gridy = 6;
                        submit.setEnabled(false);
                        add(submit, gbc);

                        gbc.gridwidth = 3;
                        gbc.gridx = 0;
                        gbc.gridy = 7;
                        add(errorMessage, gbc);


                        gbc.fill = GridBagConstraints.BOTH;
                        gbc.anchor = GridBagConstraints.PAGE_END;
                        gbc.gridwidth = 3;
                        gbc.gridx = 1;
                        gbc.gridy = 8;
                        add(toastPanel, gbc);

                        password.addKeyListener(new KeyListener() {
                            @Override
                            public void keyTyped(KeyEvent e) {
                                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                                    repeatPassword.grabFocus();
                                }
                            }

                            @Override
                            public void keyPressed(KeyEvent e) {

                            }

                            @Override
                            public void keyReleased(KeyEvent e) {

                            }
                        });

                        repeatPassword.addKeyListener(new KeyListener() {
                            @Override
                            public void keyTyped(KeyEvent e) {

                                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                                    submit.doClick();
                                }
                            }

                            @Override
                            public void keyPressed(KeyEvent e) {

                            }

                            @Override
                            public void keyReleased(KeyEvent e) {
                                char[] a = repeatPassword.getPassword();
                                char[] b = password.getPassword();
                                String aString = new String(a);
                                String bString = new String(b);
                                if(!aString.equals(bString) & errorMessage.getText().length() == 0){
                                    errorMessage.setText("Passwords do not match");
                                    submit.setEnabled(false);
                                    revalidate();
                                    repaint();
                                }
                                else if(aString.equals(bString)){
                                    submit.setEnabled(true);
                                    errorMessage.setText("");
                                    revalidate();
                                    repaint();
                                }
                            }
                        });

                        revalidate();
                        repaint();
                    }
                });
        submit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        char[] charArray = password.getPassword();

                        password.setText("");
                        repeatPassword.setText("");

                        String passwordSend = new String(charArray);

                        String hashedPassword = SHA256HashHelper.generateHashedString(passwordSend);

                        selectedUser.setHashedPassword(hashedPassword);

                        PayloadRequest request = new PayloadRequest();

                        request.setPayloadObject(selectedUser);
                        request.setRequestPayloadType(RequestPayloadType.Update);

                        PayloadResponse response = null;
                        try {
                            response = new Client().SendRequest(request);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        selectedUser = (CAB302.Common.User)response.getPayloadObject();
                        Toast t;
                        if (selectedUser == null) {
                            t = new Toast("Update Failed", panel);
                        }
                        else {

                            t = new Toast("Update Complete", panel);
                        }
                        t.showtoast();
                    }
                });





    }
}
