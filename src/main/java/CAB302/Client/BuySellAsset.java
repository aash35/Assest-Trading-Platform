package CAB302.Client;

import CAB302.Common.AssetType;

import javax.swing.*;
import java.awt.*;

public class BuySellAsset extends JPanel {
    private JPanel titlePanel;
    private JPanel buySellPanel;

    private JLabel buyTag;
    private JLabel sellTag;

    private JSpinner buyQuantity;
    private JSpinner buyPrice;
    private JButton buyButton;

    private JSpinner sellQuantity;
    private JSpinner sellPrice;
    private JButton sellButton;

    public BuySellAsset(JFrame frame, AssetType assetType){
        Color c = new Color(243, 244, 246);
        setLayout(new BorderLayout());

        titlePanel = createPanel(c);
        buySellPanel = createPanel(c);

        add(titlePanel, BorderLayout.NORTH);
        add(buySellPanel, BorderLayout.CENTER);

        JLabel assetName = new JLabel(assetType.getName());
        assetName.setFont(new Font(assetName.getFont().getFontName(), Font.PLAIN, 42));

        buyTag = new JLabel(String.format("Buy %s", assetType.getName()));
        buyTag.setFont(new Font(buyTag.getFont().getFontName(), Font.PLAIN, 21));

        sellTag = new JLabel(String.format("Sell %s", assetType.getName()));
        sellTag.setFont(new Font(sellTag.getFont().getFontName(), Font.PLAIN, 21));

        buyQuantity = createSpinner();
        buyPrice = createSpinner();
        sellQuantity = createSpinner();
        sellPrice = createSpinner();

        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");

        layoutBuySellPanel();
    }

    private JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    private JSpinner createSpinner(){
        SpinnerModel model = new SpinnerNumberModel(1, 1, null, 1);
        JSpinner spinner = new JSpinner(model);
        return  spinner;
    }

    private void layoutBuySellPanel() {
        buySellPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 0.5;
        constraints.weighty = 0.5;

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        addToPanel(buySellPanel, buyTag, constraints, 0,0,1,1);
        addToPanel(buySellPanel, buyQuantity, constraints, 0, 1, 2, 1);
        addToPanel(buySellPanel, buyPrice, constraints, 0, 2, 2, 1);
        addToPanel(buySellPanel, buyButton, constraints, 2,2,1,1);

        addToPanel(buySellPanel, sellTag, constraints, 0,4,1,1);
        addToPanel(buySellPanel, sellQuantity, constraints, 0, 5, 2, 1);
        addToPanel(buySellPanel, sellPrice, constraints, 0, 6, 2,1);
        addToPanel(buySellPanel, sellButton, constraints, 2,6,1,1);
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints constraints,
                       int xPos, int yPos, int width, int height){
        constraints.gridx = xPos;
        constraints.gridy = yPos;
        constraints.gridwidth = width;
        constraints.gridheight = height;

        panel.add(component, constraints);
    }
}
