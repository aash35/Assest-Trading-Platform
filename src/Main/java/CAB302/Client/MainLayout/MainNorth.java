package CAB302.Client.MainLayout;

import CAB302.Common.Colors.Grey;
import CAB302.Common.Colors.LightBlue;
import CAB302.Common.Colors.Purple;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.RuntimeSettings;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainNorth extends JPanel {
    JLabel name;
    JLabel credits;
    JButton logoutButton;
    GridBagConstraints gbc = new GridBagConstraints();

    public MainNorth(User user, JFrame contentFrame) {
        setLayout(new GridBagLayout());
        setBackground(new Grey());
        setBorder(BorderFactory.createMatteBorder(2,2,0,2, Color.BLACK));

        name = new JLabel(user.getUsername() +": " + user.getOrganisationalUnit() == null ? "" : user.getOrganisationalUnit().getUnitName());
        name.setFont(name.getFont().deriveFont(Font.BOLD));

        credits = new JLabel(user.getOrganisationalUnit().getAvailableCredit() +" credits");
        credits.setFont(credits.getFont().deriveFont(Font.BOLD));

        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 0);
        add(name, gbc);


        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(credits, gbc);

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
