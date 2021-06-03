package CAB302.Client.MainLayout;

import CAB302.Client.OrganisationalUnit;
import CAB302.Common.Colors.Grey;
import CAB302.Common.Colors.LightBlue;
import CAB302.Common.Colors.Purple;
import CAB302.Common.Helpers.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainCentre extends JPanel {
    public MainCentre() {
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        //setBackground(new LightBlue());
        add(new OrganisationalUnit());
    }
}
