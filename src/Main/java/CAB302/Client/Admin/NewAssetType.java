package CAB302.Client.Admin;

import CAB302.Client.Client;
import CAB302.Client.Helper.Toast;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Class creates the new asset type page of the application GUI.
 */
public class NewAssetType extends JPanel{
    private JPanel focusPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JPanel innerPanel;

    GridBagConstraints gbc = new GridBagConstraints();

    JLabel nameLabel = new JLabel("Asset Type Name:");
    JTextField nameField = new JTextField(20);

    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField(20);

    JButton confirmButton = new JButton("Confirm");

    /**
     * Constructs the application page to create a new asset type.
     * @param panel the container for the page.
     */
    public NewAssetType(JPanel panel) {
        createGUI(panel);

        confirmButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String description = descriptionField.getText();
                        boolean enableToast = true;
                        createAssetType(name, description, enableToast);
                    }
                });
    }

    /**
     * Creates a new Asset Type based on the selection of the admin
     * @param name the name of the new asset type.
     * @param description a description of the new asset type.
     * @return a response on if the method was successful or not.
     */
    public PayloadResponse createAssetType(String name, String description, boolean enableToast)
    {
        PayloadResponse response = null;
        if (name.length() > 0 && description.length() > 0)
        {
            CAB302.Common.AssetType type = new CAB302.Common.AssetType();

            type.setName(name);
            type.setDescription(description);

            PayloadRequest request = new PayloadRequest();

            request.setPayloadObject(type);
            request.setRequestPayloadType(RequestPayloadType.Get);

            response = null;
            try {
                response = new Client().SendRequest(request);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            CAB302.Common.AssetType assetType = (CAB302.Common.AssetType)response.getPayloadObject();

            //checks if the asset type exists in the database
            if (assetType == null)
            {
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
                        t = new Toast("New Asset Type Added", focusPanel);
                        t.showtoast();
                    }
                    NavigationHelper.changePanel(focusPanel, new Administration(focusPanel));
                }
            }
            else
            {
                if (enableToast)
                {
                    Toast t;
                    t = new Toast("Asset Type already exists", focusPanel);
                    t.showtoast();
                }
                nameField.setText("");
                descriptionField.setText("");
            }
        }
        else
        {
            if (enableToast)
            {
                Toast t;
                t = new Toast("Please enter a value in all fields", focusPanel);
                t.showtoast();
            }
            nameField.setText("");
            descriptionField.setText("");
        }
        return response;
    }

    /**
     * Constructs the application page to create a new asset type.
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

        JLabel title = new JLabel("Create New Asset Type");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28));
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        innerPanel = new JPanel();
        mainPanel.add(innerPanel, BorderLayout.CENTER);

        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        // Left Column
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        innerPanel.add(nameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        innerPanel.add(descriptionLabel, gbc);

        // Right Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        innerPanel.add(nameField, gbc);

        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    descriptionField.grabFocus();
                }

                //need to remove on the way out
                if (e.getKeyChar()==KeyEvent.VK_ESCAPE) {
                    //NavigationHelper.mainMenu(frame);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        innerPanel.add(descriptionField, gbc);

        //Middle Bottom
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        innerPanel.add(confirmButton, gbc);
    }
}
