package CAB302.Client.Admin;

import CAB302.Client.Client;
import CAB302.Common.Enums.JsonPayloadType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.JsonPayloadRequest;
import CAB302.Common.JsonPayloadResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NewOrganisationalUnit extends JPanel {
    JLabel messageStackLabel = new JLabel("");
    JLabel OUnameLabel = new JLabel("Enter Organisational Unit Name: ");
    JTextField OUnameField = new JTextField(20);
    JButton confirmBtn = new JButton("Confirm");

    public NewOrganisationalUnit(){

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(OUnameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(OUnameField, gbc);

        confirmBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = OUnameField.getText();

                        CAB302.Common.OrganisationalUnit type = new CAB302.Common.OrganisationalUnit();

                        type.setUnitName(name);

                        JsonPayloadRequest request = new JsonPayloadRequest();

                        request.setPayloadObject(type);
                        request.setJsonPayloadType(JsonPayloadType.Get);

                        JsonPayloadResponse response = new Client().SendRequest(request);

                        CAB302.Common.OrganisationalUnit ouName = (CAB302.Common.OrganisationalUnit)response.getPayloadObject();

                        if (ouName == null) {

                            request.setJsonPayloadType(JsonPayloadType.Create);
                            response = new Client().SendRequest(request);

                            OUnameField.setText("");

                            messageStackLabel.setText("Successfully saved");

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 2;
                            add(messageStackLabel, gbc);
                            remove(confirmBtn);

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 3;
                            add(confirmBtn, gbc);
                        }
                        else {
                            messageStackLabel.setText(String.format("Organisational Unit (%s) already exists", name));

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 2;
                            add(messageStackLabel, gbc);
                            remove(confirmBtn);

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 3;
                            add(confirmBtn, gbc);
                        }
                    }
                });

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(confirmBtn, gbc);
    }
}
