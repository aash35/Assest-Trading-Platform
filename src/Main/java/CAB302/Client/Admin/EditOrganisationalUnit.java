package CAB302.Client.Admin;

import CAB302.Client.Client;
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

public class EditOrganisationalUnit extends JPanel{
    JLabel assetNameLabel = new JLabel("Select Asset: ");
    JComboBox assetCB;

    JLabel ouNameLabel = new JLabel("Select Organisational Unit: ");
    JComboBox ouCB;

    JLabel changeAmtLabel = new JLabel("Change Amount To: ");
    JTextField changeAmtField = new JTextField(20);

    JButton confirmBtn = new JButton("Confirm");
    public EditOrganisationalUnit(){

        //Placeholder - NEED TO GET DATA FROM DATABASE//////////////////////
        String[] assetString = { "Credits", "PooPoo", "PeePee", "Water"};
        String[] ouString = { "Marketing", "Finance", "Accounting", "Admin"};

        JComboBox assetCB = new JComboBox(assetString);
        JComboBox ouCB = new JComboBox(ouString);
        //////////////////////////////////////////////////////////////////////

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(assetNameLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(assetCB, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(ouNameLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(ouCB, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 2;
        add(changeAmtLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(changeAmtField, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        add(confirmBtn, gc);
    }
}
