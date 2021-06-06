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
        createGUI(panel);

        confirmBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = OUnameField.getText();
                        boolean enableToast = true;
                        createOU(name, enableToast);
                    }
                });
    }

    /**
     * Creates a new Organisational Unit based on the selection of the admin
     * @param name The name of the orgUnit to be created.
     * @return a response on if the method was successful or not.
     */
    public PayloadResponse createOU(String name, boolean enableToast)
    {
        PayloadResponse response = null;
        //Checks if the field that was entered is empty
        if (name.length() > 0)
        {
            CAB302.Common.OrganisationalUnit type = new CAB302.Common.OrganisationalUnit();

            type.setUnitName(name);

            PayloadRequest request = new PayloadRequest();

            request.setPayloadObject(type);
            request.setRequestPayloadType(RequestPayloadType.Get);

            response = null;
            try {
                response = new Client().SendRequest(request);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            OrganisationalUnit ou = (OrganisationalUnit)response.getPayloadObject();
            //checks if the orgUnit searched exists in the database
            if (ou == null) {
                request.setRequestPayloadType(RequestPayloadType.Create);
                try {
                    response = new Client().SendRequest(request);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                if (response != null){
                    if (enableToast)
                    {
                        Toast t;
                        t = new Toast("New Organisational Unit Added", focusPanel);
                        t.showtoast();
                    }
                    NavigationHelper.changePanel(focusPanel, new Administration(focusPanel));
                }
            }
            else {
                if (enableToast)
                {
                    Toast t;
                    t = new Toast("Organisational Unit already exists", focusPanel);
                    t.showtoast();
                }
                OUnameField.setText("");
                response = null;
            }
        }
        else
        {
            if (enableToast)
            {
                Toast t;
                t = new Toast("Please enter a value in the field", focusPanel);
                t.showtoast();
            }
            OUnameField.setText("");
        }
        return response;
    }

    /**
     * Constructs the application page to create a new organisational unit.
     * @param panel the container for the page.
     */
    private void createGUI(JPanel panel)
    {
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

        //Left column
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        innerPanel.add(OUnameLabel, gbc);

        // Right Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        innerPanel.add(OUnameField, gbc);

        //Middle Bottom
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        innerPanel.add(confirmBtn, gbc);
    }
}
