package CAB302.Client.Store;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.AssetType;
import CAB302.Common.Colors.Purple;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BuySellAsset extends JPanel {
    private AssetType assetType;

    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buySellPanel;
    private JPanel tablesPanel;
    private JScrollPane currentOrderPanel;
    private JScrollPane priceHistoryPanel;

    private JLabel buyTag;
    private JLabel sellTag;
    private JLabel priceHistoryTag;
    private JLabel currentOrderTag;
    private JLabel buyQuantityTag;
    private JLabel buyPriceTag;
    private JLabel sellQuantityTag;
    private JLabel sellPriceTag;

    private JSpinner buyQuantity;
    private JSpinner buyPrice;
    private JButton buyButton;

    private JSpinner sellQuantity;
    private JSpinner sellPrice;
    private JButton sellButton;

    private List<Trade> allTrades;
    private JTable currentOrderTable;
    private JTable priceHistoryTable;

    public BuySellAsset(AssetType assetType){
        this.assetType = assetType;
        Color c = new Purple();
        mainPanel = createPanel(c);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(630, 500));
        add(mainPanel);
        setBackground(c);

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

        buyQuantityTag = new JLabel("Quantity");
        buyQuantityTag.setFont(new Font(buyQuantityTag.getFont().getFontName(), Font.PLAIN, 14));

        buyPriceTag = new JLabel(("Price"));
        buyPriceTag.setFont(new Font(buyPriceTag.getFont().getFontName(), Font.PLAIN, 14));

        sellQuantityTag = new JLabel("Quantity");
        sellQuantityTag.setFont(new Font(buyQuantityTag.getFont().getFontName(), Font.PLAIN, 14));

        sellPriceTag = new JLabel(("Price"));
        sellPriceTag.setFont(new Font(buyPriceTag.getFont().getFontName(), Font.PLAIN, 14));

        buyQuantity = createSpinner();
        buyPrice = createSpinner();
        sellQuantity = createSpinner();
        sellPrice = createSpinner();

        buyButton = new JButton("Buy");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trade trade = new Trade();
                trade.setQuantity((Integer) buyQuantity.getValue());
                trade.setAssetType(assetType);
                trade.setPrice((Integer) buyPrice.getValue());
                trade.setCreatedByUser(RuntimeSettings.CurrentUser);
                trade.setCreatedDate(Timestamp.from(Instant.now()));
                trade.setTransactionType(TradeTransactionType.Buying);
                trade.setStatus(TradeStatus.InMarket);

                Client client = new Client();

                PayloadRequest request = new PayloadRequest();

                request.setPayloadObject(trade);
                request.setRequestPayloadType(RequestPayloadType.Buy);

                try {
                    PayloadResponse response = client.SendRequest(request);
                }
               catch(Exception error){

               }
            }
        });

        sellButton = new JButton("Sell");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trade trade = new Trade();
                trade.setQuantity((Integer) sellQuantity.getValue());
                trade.setAssetType(assetType);
                trade.setPrice((Integer) sellPrice.getValue());
                trade.setCreatedByUser(RuntimeSettings.CurrentUser);
                trade.setCreatedDate(Timestamp.from(Instant.now()));
                trade.setTransactionType(TradeTransactionType.Selling);
                trade.setStatus(TradeStatus.InMarket);

                Client client = new Client();

                PayloadRequest request = new PayloadRequest();

                request.setPayloadObject(trade);
                request.setRequestPayloadType(RequestPayloadType.Buy);

                try {
                    PayloadResponse response = client.SendRequest(request);
                }
                catch(Exception error){

                }
            }
        });

        try{
            getTradeList();
        } catch(IOException e) {
            e.printStackTrace();
        }

        priceHistoryTag = new JLabel(String.format("Price History of %s Sales", assetType.getName()));
        priceHistoryTag.setFont(new Font(buyTag.getFont().getFontName(), Font.PLAIN, 21));

        currentOrderTag = new JLabel(String.format("Current Orders for %s", assetType.getName()));
        currentOrderTag.setFont(new Font(sellTag.getFont().getFontName(), Font.PLAIN, 21));

        priceHistoryTable = createOrderTable(findFilledTrades());
        priceHistoryPanel = new JScrollPane(priceHistoryTable);
        priceHistoryTable.setFillsViewportHeight(true);

        currentOrderTable = createOrderTable(findCurrentTrades());
        currentOrderPanel = new JScrollPane(currentOrderTable);
        currentOrderTable.setFillsViewportHeight(true);

        buySellPanel = createPanel(c);
        layoutBuySellPanel();
        mainPanel.add(buySellPanel, BorderLayout.CENTER);

        tablesPanel = createPanel(c);
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        tablesPanel.setPreferredSize(new Dimension(400,100));
        tablesPanel.add(priceHistoryTag);
        tablesPanel.add(priceHistoryPanel);
        tablesPanel.add(currentOrderTag);
        tablesPanel.add(currentOrderPanel);

        mainPanel.add(tablesPanel, BorderLayout.EAST);
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
    
    private void getTradeList() throws IOException {
        PayloadRequest request = new PayloadRequest();
        request.setPayloadObject(new Trade());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        allTrades = (List<Trade>)(List<?>)response.getPayloadObject();
    }

    private ArrayList<Trade> findCurrentTrades(){
        ArrayList<Trade> currentTrades = new ArrayList<>();
        for(Trade trade: allTrades){
            if (trade.getAssetType().getName().equals(this.assetType.getName()) &&
                    trade.getStatus() == TradeStatus.InMarket){
                currentTrades.add(trade);
            }
        }
        return currentTrades;
    }

    private ArrayList<Trade> findFilledTrades(){
        ArrayList<Trade> filledTrades = new ArrayList<>();
        for(Trade trade: allTrades){
            if(trade.getAssetType().getName().equals(this.assetType.getName()) &&
                    trade.getStatus() == TradeStatus.Filled &&
                    trade.getTransactionType() == TradeTransactionType.Selling){
                filledTrades.add(trade);
            }
        }

        filledTrades.sort(new Comparator<Trade>() {
            @Override
            public int compare(Trade o1, Trade o2) {
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });
        return filledTrades;
    }

    private JTable createOrderTable(ArrayList<Trade> currentOrders) {
        String[] columnHeaders = {"Asset Type",
                "Quantity",
                "Price per Unit",
                "Order Type",
                "Created By User",
                "Date Created"};

        Object[][] data = new Object[currentOrders.size()][6];

        int i = 0;
        for (Trade order: currentOrders){
            data[i][0] = order.getAssetType().getName();
            data[i][1] = order.getQuantity();
            data[i][2] = order.getPrice();
            data[i][3] = order.getTransactionType().toString();
            data[i][4] = order.getCreatedByUser().getUsername();
            data[i][5] = order.getCreatedDate();
            i++;
        }
        JTable jTable = new JTable(data, columnHeaders);
        jTable.setRowSelectionAllowed(false);
        jTable.setColumnSelectionAllowed(false);
        return jTable;
    }

    /**
     * Adds all labels, spinboxes and buttons to the Buy/Sell panel of the page.
     */
    private void layoutBuySellPanel() {
        buySellPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 10;
        constraints.weighty = 50;

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 0, 0);

        addToPanel(buySellPanel, buyTag, constraints, 0,0,1,1);

        addToPanel(buySellPanel, buyQuantityTag, constraints, 0,1,1,1);
        addToPanel(buySellPanel, buyQuantity, constraints, 0, 2, 2, 1);

        addToPanel(buySellPanel, buyPriceTag, constraints, 0,3,1,1);
        addToPanel(buySellPanel, buyPrice, constraints, 0, 4, 2, 1);
        addToPanel(buySellPanel, buyButton, constraints, 1,4,1,1);

        addToPanel(buySellPanel, sellTag, constraints, 0,5,1,1);

        addToPanel(buySellPanel, sellQuantityTag, constraints, 0,6,1,1);
        addToPanel(buySellPanel, sellQuantity, constraints, 0, 7, 2, 1);

        addToPanel(buySellPanel, sellPriceTag, constraints, 0,8,1,1);
        addToPanel(buySellPanel, sellPrice, constraints, 0, 9, 2,1);

        addToPanel(buySellPanel, sellButton, constraints, 1,9,1,1);
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
