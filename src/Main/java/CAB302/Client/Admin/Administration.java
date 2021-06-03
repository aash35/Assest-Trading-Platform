package CAB302.Client.Admin;

import CAB302.Common.Helpers.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administration extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JButton createAssetTypeButton = new JButton("Create Asset Types");

    JButton mainMenuButton = new JButton("Main Menu");

    public Administration(JPanel panel) {

        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 1;
        gbc.gridy = 2;

        createAssetTypeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new AssetType());
                    }
                });

        add(createAssetTypeButton, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 2;
        gbc.gridy = 2;

        mainMenuButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //NavigationHelper.mainMenu(frame);
                    }
                });

        add(mainMenuButton, gbc);

    }
}
