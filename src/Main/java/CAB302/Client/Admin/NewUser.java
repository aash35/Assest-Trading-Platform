package CAB302.Client.Admin;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.AccountTypeRole;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.SHA256HashHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import static CAB302.Common.Helpers.SHA256HashHelper.generateHashedString;

/**
 * Class creates the new user page of the application GUI.
 */
public class NewUser extends JPanel {
    private JLabel messageStackLabel = new JLabel("");

    private JLabel enterUserLabel = new JLabel("Enter Username: ");
    private JTextField enterUserField = new JTextField(20);

    private JLabel enterPassLabel = new JLabel("Enter Password: ");
    private JTextField enterPassField = new JTextField(20);

    private JLabel accountTypeLabel = new JLabel("Account Type: ");
    private JComboBox accountTypeCB;

    private JLabel ouNameLabel = new JLabel("Organisational Unit: ");
    private JComboBox ouCB;

    private List<OrganisationalUnit> ouList;

    private JButton confirmBtn = new JButton("Confirm");

    /**
     * Constructs the application page to create a new user.
     */
    public NewUser(){

        AccountTypeRole[] accountType = AccountTypeRole.values();
        accountTypeCB = new JComboBox(accountType);

        //Get list of all OrgUnits and add to combobox
        try {
            getOUList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] organisationalUnits = new String[ouList.size()];
        for (int i = 0; i < ouList.size(); i++)
        {
            organisationalUnits[i] = ouList.get(i).getUnitName();
        }
        ouCB = new JComboBox(organisationalUnits);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Left Column
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(enterUserLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(enterPassLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(ouNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(accountTypeLabel, gbc);

        //Right Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(enterUserField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(enterPassField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(ouCB, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(accountTypeCB, gbc);

        confirmBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = enterUserField.getText();
                        String password = SHA256HashHelper.generateHashedString(enterPassField.getText());
                        OrganisationalUnit oUnit = ouList.get(ouCB.getSelectedIndex());
                        AccountTypeRole accountType = (AccountTypeRole) accountTypeCB.getSelectedItem();

                        User type = new User();

                        type.setUsername(username);
                        type.setHashedPassword(password);
                        type.setOrganisationalUnit(oUnit);
                        type.setAccountRoleType(accountType);

                        PayloadRequest request = new PayloadRequest();

                        request.setPayloadObject(type);

                        request.setRequestPayloadType(RequestPayloadType.Get);

                        PayloadResponse response = null;
                        try {
                            response = new Client().SendRequest(request);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        User user = (User)response.getPayloadObject();

                        if (user == null) {
                            request.setRequestPayloadType(RequestPayloadType.Create);
                            try {
                                response = new Client().SendRequest(request);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }

                            enterUserField.setText("");
                            enterPassField.setText("");

                            messageStackLabel.setText("Successfully saved");

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 5;
                            add(messageStackLabel, gbc);
                            remove(confirmBtn);

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 6;
                            add(confirmBtn, gbc);
                        }
                        else {
                            messageStackLabel.setText(String.format("Username (%s) already exists", username));

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 5;
                            add(messageStackLabel, gbc);
                            remove(confirmBtn);

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 6;
                            add(confirmBtn, gbc);
                        }
                    }
                });
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(confirmBtn, gbc);
    }

    private void getOUList() throws IOException {

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new OrganisationalUnit());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        ouList = (List<OrganisationalUnit>)(List<?>)response.getPayloadObject();
    }
}
