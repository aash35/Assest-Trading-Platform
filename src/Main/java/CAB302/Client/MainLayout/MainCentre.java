package CAB302.Client.MainLayout;

import CAB302.Client.Organisation.OrganisationalUnit;
import CAB302.Common.User;

import javax.swing.*;
import java.awt.*;

public class MainCentre extends JPanel {

    public OrganisationalUnit ouFrame;

    public MainCentre(User user) {
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        //setBackground(new LightBlue());

        ouFrame = new OrganisationalUnit(user, this);

        add(ouFrame);
    }
}
