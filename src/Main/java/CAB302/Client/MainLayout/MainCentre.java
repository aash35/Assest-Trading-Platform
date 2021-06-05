package CAB302.Client.MainLayout;

import CAB302.Client.Organisation.OrganisationalUnit;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;

/**
 * Class creates the central panel of the application GUI.
 */
public class MainCentre extends JPanel {

    public OrganisationalUnit ouFrame;

    /**
     * Constructs the panel to be used as the centre panel of the application GUI.
     * @param user
     */
    public MainCentre(User user) {
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        //setBackground(new LightBlue());

        ouFrame = new OrganisationalUnit(user);

        add(ouFrame);
    }
}
