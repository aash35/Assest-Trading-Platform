package CAB302.Client.Admin;

import CAB302.Common.Helpers.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administration extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JButton createOrganisationalUnitBtn = new JButton("Create New Organisational Unit");
    JButton editOrganisationalUnitsBtn = new JButton("Edit Organisational Units");
    JButton createAssetTypeBtn = new JButton("Create Asset Types");
    JButton createUserBtn = new JButton("Create New Users");
    JButton mainMenuButton = new JButton("Main Menu");

    public Administration(JPanel panel) {

        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        //Create Organisational Unit
        gbc.gridx = 1;
        gbc.gridy = 2;
        createOrganisationalUnitBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new NewOrganisationalUnit());
                    }
                });
        add(createOrganisationalUnitBtn, gbc);

        //Edit Organisational Unit
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(editOrganisationalUnitsBtn, gbc);
        editOrganisationalUnitsBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new EditOrganisationalUnit());
                    }
                });

        //Create Asset
        gbc.gridx = 3;
        gbc.gridy = 2;
        createAssetTypeBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new NewAssetType());
                    }
                });
        add(createAssetTypeBtn, gbc);

        //Create User
        gbc.gridx = 4;
        gbc.gridy = 2;
        createUserBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new NewUser());
                    }
                });
        add(createUserBtn, gbc);

        /*gbc.gridx = 2;
        gbc.gridy = 2;
        mainMenuButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //NavigationHelper.mainMenu(frame);
                    }
                });
        add(mainMenuButton, gbc);*/

    }
}
