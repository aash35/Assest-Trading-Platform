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
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField(10);

    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField(10);

    JButton confirmButton = new JButton("Confirm");
    private JPanel focusPanel;

    /**
     * Constructs the application page to create a new asset type.
     */
    public NewAssetType(JPanel panel) {
        focusPanel = panel;

        setLayout(new GridBagLayout());
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        // First Column
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(descriptionLabel, gbc);

        // Second Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField, gbc);

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

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(descriptionField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(confirmButton, gbc);
        confirmButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String description = descriptionField.getText();

                        CAB302.Common.AssetType type = new CAB302.Common.AssetType();

                        type.setName(name);
                        type.setDescription(description);

                        PayloadRequest request = new PayloadRequest();

                        request.setPayloadObject(type);
                        request.setRequestPayloadType(RequestPayloadType.Get);

                        PayloadResponse response = null;
                        try {
                            response = new Client().SendRequest(request);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        CAB302.Common.AssetType assetType = (CAB302.Common.AssetType)response.getPayloadObject();

                        if (assetType == null) {
                            request.setRequestPayloadType(RequestPayloadType.Create);
                            try {
                                response = new Client().SendRequest(request);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            if (response != null){
                                Toast t;
                                t = new Toast("New Asset Type Added", focusPanel);
                                t.showtoast();
                                NavigationHelper.changePanel(focusPanel, new Administration(focusPanel));
                            }

                        }
                        else {
                            Toast t;
                            t = new Toast("AssetType already exists", focusPanel);
                            t.showtoast();
                            nameField.setText("");
                            descriptionField.setText("");
                        }
                    }
                });
    }
}
