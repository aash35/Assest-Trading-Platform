package CAB302.Client.Admin;

import CAB302.Client.Client;
import CAB302.Client.Helper.Toast;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.OrganisationalUnit;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Class creates the new organisational unit page of the application GUI.
 */
public class NewOrganisationalUnit extends JPanel {
    JLabel OUnameLabel = new JLabel("Enter Organisational Unit Name: ");
    JTextField OUnameField = new JTextField(20);
    JButton confirmBtn = new JButton("Confirm");
    private JPanel focusPanel;

    /**
     * Constructs the application page to create a new organisational unit.
     */
    public NewOrganisationalUnit(JPanel panel){
        focusPanel = panel;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(OUnameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(OUnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(confirmBtn, gbc);
        confirmBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = OUnameField.getText();

                        CAB302.Common.OrganisationalUnit type = new CAB302.Common.OrganisationalUnit();

                        type.setUnitName(name);

                        PayloadRequest request = new PayloadRequest();

                        request.setPayloadObject(type);
                        request.setRequestPayloadType(RequestPayloadType.Get);

                        PayloadResponse response = null;
                        try {
                            response = new Client().SendRequest(request);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        OrganisationalUnit ou = (OrganisationalUnit)response.getPayloadObject();

                        if (ou == null) {
                            request.setRequestPayloadType(RequestPayloadType.Create);
                            try {
                                response = new Client().SendRequest(request);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }

                            if (response != null){
                                Toast t;
                                t = new Toast("New Organisational Unit Added", focusPanel);
                                t.showtoast();
                                NavigationHelper.changePanel(focusPanel, new Administration(focusPanel));
                            }
                        }
                        else {
                            Toast t;
                            t = new Toast("Organisational Unit already exists", focusPanel);
                            t.showtoast();
                            OUnameField.setText("");
                        }
                    }
                });
    }
}
