package CAB302.Client.MainLayout;

import CAB302.Common.Colors.Grey;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.ServerPackages.RuntimeSettings;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class creates the north panel of the application GUI.
 */
public class MainNorth extends JPanel {
    JLabel name;
    //JLabel credits;
    JButton logoutButton;
    GridBagConstraints gbc = new GridBagConstraints();

    /**
     * Constructs the panel to be used as the north panel of the GUI, which displays the organisational unit
     * of the logged in user, the credits available to the unit and the logout button.
     * @param user the currently logged in user
     * @param contentFrame is passed to the NavigationHelper.logout method.
     */
    public MainNorth(User user, JFrame contentFrame) {
        setLayout(new GridBagLayout());
        setBackground(new Grey());
        setBorder(BorderFactory.createMatteBorder(2,2,0,2, Color.BLACK));

        name = new JLabel(user.getUsername() +": " + user.getOrganisationalUnit().getUnitName());
        name.setFont(name.getFont().deriveFont(Font.BOLD));

        //credits = new JLabel(user.getOrganisationalUnit().getAvailableCredit() +" credits");
        //credits.setFont(credits.getFont().deriveFont(Font.BOLD));

        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 0);
        add(name, gbc);


        /*gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(credits, gbc);*/

        gbc.weightx = 0.01;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 0);
        Icon icon = new ImageIcon("src\\Main\\graphics\\logout.png");
        logoutButton = new JButton(icon);
        add(logoutButton, gbc);

        logoutButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        RuntimeSettings.CurrentUser = null;
                        NavigationHelper.logout(contentFrame);
                    }
                });


    }
}
