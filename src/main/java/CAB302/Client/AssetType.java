package CAB302.Client;

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

public class AssetType extends JPanel{
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel messageStackLabel = new JLabel("");

    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField(10);

    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField(10);

    JButton saveButton = new JButton("Save");

    public AssetType(JFrame frame) {

        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
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
        add(descriptionField, gbc);

        saveButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String description = descriptionField.getText();

                        CAB302.Common.AssetType type = new CAB302.Common.AssetType();

                        type.setName(name);
                        type.setDescription(description);

                        JsonPayloadRequest request = new JsonPayloadRequest();

                        request.setPayloadObject(type);
                        request.setJsonPayloadType(JsonPayloadType.Get);

                        JsonPayloadResponse response = new Client().SendRequest(request);

                        CAB302.Common.AssetType assetType = (CAB302.Common.AssetType)response.getPayloadObject();

                        if (assetType == null) {

                            request.setJsonPayloadType(JsonPayloadType.Create);
                            response = new Client().SendRequest(request);

                            nameField.setText("");
                            descriptionField.setText("");

                            messageStackLabel.setText("Successfully saved");

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 2;
                            add(messageStackLabel, gbc);
                            remove(saveButton);

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 3;
                            add(saveButton, gbc);
                        }
                        else {
                            messageStackLabel.setText(String.format("Asset Type (%s) already exists", name));

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 2;
                            add(messageStackLabel, gbc);
                            remove(saveButton);

                            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                            gbc.weighty = 5;
                            gbc.insets = new Insets(20,0,0,0);
                            gbc.gridx = 1;
                            gbc.gridy = 3;
                            add(saveButton, gbc);
                        }
                    }
                });

        // Last Row
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 5;
        gbc.insets = new Insets(20,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(saveButton, gbc);

    }
}
