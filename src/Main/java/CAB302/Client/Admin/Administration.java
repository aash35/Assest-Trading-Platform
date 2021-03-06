package CAB302.Client.Admin;

import CAB302.Common.Helpers.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class creates the administration page of the application GUI.
 */
public class Administration extends JPanel {
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JPanel innerPanel;

    private GridBagConstraints gbc = new GridBagConstraints();

    private JButton createOrganisationalUnitBtn = new JButton("Create New Organisational Unit");
    private JButton editOrganisationalUnitsBtn = new JButton("Edit Organisational Units");
    private JButton createAssetTypeBtn = new JButton("Create New Asset Types");
    private JButton createUserBtn = new JButton("Create New Users");
    private JPanel focusPanel;
    /**
     * Constructs the application administration page.
     * @param panel the container of the panel, passed to the NavigationHelper.changePanel method.
     */
    public Administration(JPanel panel) {
        createGUI(panel);

        createOrganisationalUnitBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new NewOrganisationalUnit(focusPanel));
                    }
                });

        editOrganisationalUnitsBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new EditOrganisationalUnit(focusPanel));
                    }
                });

        createAssetTypeBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new NewAssetType(focusPanel));
                    }
                });

        createUserBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NavigationHelper.changePanel(panel, new NewUser(focusPanel));
                    }
                });
    }

    /**
     * Constructs the admin page.
     * @param panel the container for the page.
     */
    private void createGUI(JPanel panel){
        focusPanel = panel;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(630,500));
        add(mainPanel);

        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());

        JLabel title = new JLabel("Admin");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28));
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        innerPanel = new JPanel();
        int gap =  50;
        innerPanel.setLayout(new GridLayout(2,2, gap, gap));
        mainPanel.add(innerPanel, BorderLayout.CENTER);

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        //Create Organisational Unit
        gbc.gridx = 0;
        gbc.gridy = 2;
        innerPanel.add(createOrganisationalUnitBtn, gbc);

        //Edit Organisational Unit
        gbc.gridx = 1;
        gbc.gridy = 2;
        innerPanel.add(editOrganisationalUnitsBtn, gbc);

        //Create Asset
        gbc.gridx = 2;
        gbc.gridy = 2;
        innerPanel.add(createAssetTypeBtn, gbc);

        //Create User
        gbc.gridx = 3;
        gbc.gridy = 2;
        innerPanel.add(createUserBtn, gbc);
    }
}
