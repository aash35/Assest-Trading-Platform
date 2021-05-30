package CAB302.Client;

import CAB302.Common.AssetType;

import javax.swing.*;
import java.awt.*;

public class BuySellAsset extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();

    private JPanel titlePanel;
    private JPanel buySellPanel;

    public BuySellAsset(JFrame frame, AssetType assetType){
        Color c = new Color(243, 244, 246);
        setLayout(new BorderLayout());

        titlePanel = createPanel(c);
        buySellPanel = createPanel(c);

        add(titlePanel, BorderLayout.NORTH);
        add(buySellPanel, BorderLayout.CENTER);

        JLabel assetName = new JLabel(assetType.getName());
        assetName.setFont(new Font(assetName.getFont().getFontName(), Font.PLAIN, 42));

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        // First Column
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

    }

    private JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }
}
