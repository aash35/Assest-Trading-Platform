package CAB302.Client;

import CAB302.Common.*;
import CAB302.Common.AssetType;
import CAB302.Common.Colors.Purple;
import CAB302.Common.Enums.JsonPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Exceptions.IllegalTradeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Instant;

public class BuySellAsset extends JPanel {
    private JPanel mainPanel;
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

    public BuySellAsset(AssetType assetType){
        Color c = new Purple();
        mainPanel = createPanel(c);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(630, 500));
        add(mainPanel);

        titlePanel = createPanel(c);
        titlePanel.setLayout(new FlowLayout());

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JLabel assetName = new JLabel(assetType.getName());
        assetName.setFont(new Font(assetName.getFont().getFontName(), Font.PLAIN, 42));
        titlePanel.add(assetName);

        buyTag = new JLabel(String.format("Buy %s", assetType.getName()));
        buyTag.setFont(new Font(buyTag.getFont().getFontName(), Font.PLAIN, 21));

        sellTag = new JLabel(String.format("Sell %s", assetType.getName()));
        sellTag.setFont(new Font(sellTag.getFont().getFontName(), Font.PLAIN, 21));

        buyQuantity = createSpinner();
        buyPrice = createSpinner();
        sellQuantity = createSpinner();
        sellPrice = createSpinner();

        buyButton = new JButton("Buy");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Asset asset = new Asset();
                asset.setAssetType(assetType);
                asset.setCreatedByUserID(RuntimeSettings.CurrentUser);

                Trade trade = new Trade();
                trade.setQuantity((Integer) buyQuantity.getValue());
                trade.setAsset(asset);
                trade.setPrice((Double) buyPrice.getValue());
                trade.setCreatedByUser(RuntimeSettings.CurrentUser);
                trade.setCreatedDate(Timestamp.from(Instant.now()));
                trade.setTransactionType(TradeTransactionType.Buying);
                trade.setStatus(TradeStatus.InMarket);

                Client client = new Client();

                JsonPayloadRequest request = new JsonPayloadRequest();

                request.setPayloadObject(trade);
                request.setJsonPayloadType(JsonPayloadType.Buy);

                try {
                    JsonPayloadResponse response = client.SendRequest(request);
                }
               catch(Exception error){

               }
            }
        });

        sellButton = new JButton("Sell");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Asset asset = new Asset();
                asset.setAssetType(assetType);
                asset.setCreatedByUserID(RuntimeSettings.CurrentUser);

                Trade trade = new Trade();
                trade.setQuantity((Integer) sellQuantity.getValue());
                trade.setAsset(asset);
                trade.setPrice((Double) sellPrice.getValue());
                trade.setCreatedByUser(RuntimeSettings.CurrentUser);
                trade.setCreatedDate(Timestamp.from(Instant.now()));
                trade.setTransactionType(TradeTransactionType.Selling);
                trade.setStatus(TradeStatus.InMarket);

                Client client = new Client();

                JsonPayloadRequest request = new JsonPayloadRequest();

                request.setPayloadObject(trade);
                request.setJsonPayloadType(JsonPayloadType.Buy);

                try {
                    JsonPayloadResponse response = client.SendRequest(request);
                }
                catch(Exception error){

                }
            }
        });

        buySellPanel = createPanel(c);
        layoutBuySellPanel();
        mainPanel.add(buySellPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a JPanel object
     * @param c the background color of the panel.
     * @return a JPanel object
     */
    private JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    /**
     * Creates an integer spinnerbox with a minimum value of 1 and no max value.
     * @return a JSpinner object
     */
    private JSpinner createSpinner(){
        SpinnerModel model = new SpinnerNumberModel(1, 1, null, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 30));
        return  spinner;
    }

    /**
     * Adds all labels, spinboxes and buttons to the Buy/Sell panel of the page.
     */
    private void layoutBuySellPanel() {
        buySellPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 100;
        constraints.weighty = 100;

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 0, 0);

        addToPanel(buySellPanel, buyTag, constraints, 0,0,1,1);
        addToPanel(buySellPanel, buyQuantity, constraints, 0, 1, 1, 1);
        addToPanel(buySellPanel, buyPrice, constraints, 0, 2, 1, 1);
        addToPanel(buySellPanel, buyButton, constraints, 1,2,1,1);

        addToPanel(buySellPanel, sellTag, constraints, 0,4,1,1);
        addToPanel(buySellPanel, sellQuantity, constraints, 0, 5, 1, 1);
        addToPanel(buySellPanel, sellPrice, constraints, 0, 6, 1,1);
        addToPanel(buySellPanel, sellButton, constraints, 1,6,1,1);
    }

    /**
     * Adds a component to a given panel in a specified grid bag layout organisation
     * @param panel the panel the component is being added to.
     * @param component the component being added.
     * @param constraints the GridBagContraints object used to define the panel.
     * @param xPos x position in the grid bag layout
     * @param yPos y position in the grid bag layout
     * @param width GridBagConstraint gridwidth
     * @param height GridBagConstraint gridheight
     */
    private void addToPanel(JPanel panel, Component component, GridBagConstraints constraints,
                       int xPos, int yPos, int width, int height){
        constraints.gridx = xPos;
        constraints.gridy = yPos;
        constraints.gridwidth = width;
        constraints.gridheight = height;

        panel.add(component, constraints);
    }
}
