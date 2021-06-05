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
    private JPanel focusPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JPanel innerPanel;
    JLabel OUnameLabel = new JLabel("Organisational Unit Name: ");
    JTextField OUnameField = new JTextField(20);
    JButton confirmBtn = new JButton("Confirm");

    /**
     * Constructs the application page to create a new organisational unit.
     * @param panel the container for the page.
     */
    public NewOrganisationalUnit(JPanel panel){
        //GUI stuff
        focusPanel = panel;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(630,500));
        add(mainPanel);

        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());

        JLabel title = new JLabel("Create New Organisational Unit");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28));
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        innerPanel = new JPanel();
        mainPanel.add(innerPanel, BorderLayout.CENTER);

        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        innerPanel.add(OUnameLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        innerPanel.add(OUnameField, gbc);

        //Middle
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        innerPanel.add(confirmBtn, gbc);

        confirmBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = OUnameField.getText();
                        if (name.length() > 0)
                        {
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
                        else
                        {
                            Toast t;
                            t = new Toast("Please enter a value in the field", focusPanel);
                            t.showtoast();
                            OUnameField.setText("");
                        }
                    }
                });
    }
}
