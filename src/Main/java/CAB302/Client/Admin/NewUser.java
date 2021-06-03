package CAB302.Client.Admin;

import javax.swing.*;
import java.awt.*;

public class NewUser extends JPanel {
    JLabel enterNameLabel = new JLabel("Enter Full Name: ");
    JTextField enterNameField = new JTextField(20);

    JLabel enterUserLabel = new JLabel("Enter Username: ");
    JTextField enterUserField = new JTextField(20);

    JLabel enterPassLabel = new JLabel("Enter Password: ");
    JTextField enterPassField = new JTextField(20);

    JLabel accountTypeLabel = new JLabel("Account Type: ");
    JComboBox accountTypeCB;

    JLabel ouNameLabel = new JLabel("Organisational Unit: ");
    JComboBox ouCB;

    JButton confirmBtn = new JButton("Confirm");

    public NewUser(){

        //Placeholder - NEED TO GET DATA FROM DATABASE//////////////////////
        String[] accountType = { "Normal", "Admin"};
        String[] ouString = { "Marketing", "Finance", "Accounting", "Admin"};

        accountTypeCB = new JComboBox(accountType);
        ouCB = new JComboBox(ouString);
        //////////////////////////////////////////////////////////////////////

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(enterNameLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(enterNameField, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(enterUserLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(enterUserField, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 2;
        add(enterPassLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(enterPassField, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 3;
        add(ouNameLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 3;
        add(ouCB, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 4;
        add(accountTypeLabel, gc);

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 4;
        add(accountTypeCB, gc);

        gc.gridx = 1;
        gc.gridy = 5;
        add(confirmBtn, gc);
    }
}
