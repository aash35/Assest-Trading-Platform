package CAB302.Client;

import CAB302.Common.AssetType;

import javax.swing.*;
import java.awt.*;

public class BuySellAsset extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel buySellLabel = new JLabel("Buy or Sell Asset");

    public BuySellAsset(JFrame frame, AssetType assetType){
        setBackground(new Color(243, 244, 246));
        setLayout(new GridBagLayout());

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        // First Column
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        buySellLabel.setFont(new Font(buySellLabel.getFont().getFontName(), Font.PLAIN, 42));

        add(buySellLabel, gbc);
    }
}
