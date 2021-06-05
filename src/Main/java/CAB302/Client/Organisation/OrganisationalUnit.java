package CAB302.Client.Organisation;

import CAB302.Client.Client;
import CAB302.Client.Helper.ButtonColumn;
import CAB302.Client.Helper.Toast;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

/**
 * Class creates the organisational unit page of the application GUI.
 */
public class OrganisationalUnit extends JPanel {
    private JPanel assetPanel;
    private JPanel currentTradesPanel;
    private User focusUser;
    private GridBagConstraints c = new GridBagConstraints();
    private List<Asset> assetsList;
    private List<Trade> tradesList;
    private JTable currentTradesTable;
    public JScrollPane currentTradesPanelOne;

    public JScrollPane scrollPane;
    public JPanel focusPanel;

    /**
     * Constructs the application page for the organisational unit the currently logged in user belongs to.
     * @param user the currently logged in user.
     */
    public OrganisationalUnit(User user, JPanel panel) {
        focusPanel = panel;
        focusUser = user;
        setLayout(new GridBagLayout());

        assetPanel = createAssetPanel();
        currentTradesPanel = createCurrentTradesPanel();

        JLabel title = new JLabel("Organisation Assets");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28));

        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        add(title, c);



        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        add(assetPanel, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        add(currentTradesPanel, c);

    }

    /**
     * Constructs a panel to display the assets owned by the organisational unit.
     * @return the assets panel.
     */
    private JPanel createAssetPanel(){
        JPanel panelOne = new JPanel();

        try {
            getAssetsList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel allPanel = new JPanel();
        JPanel credit = creditPanel();
        allPanel.add(credit);

        if (assetsList != null) {
            for (Asset asset : assetsList) {
                allPanel.add(createAssets(asset));
            }
        }
        scrollPane = new JScrollPane(allPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrollPane.setPreferredSize(new Dimension(650, 110));
        panelOne.add(scrollPane);
        return panelOne;
    }

    /**
     * Constructs a panel to display the currently active trades owned by the organisational unit.
     * @return the current trades panel
     */
    private JPanel createCurrentTradesPanel(){
        JPanel panel = new JPanel();
        try {
            getTradeList();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Action delete = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                Trade order =  tradesList.get(modelRow);
                /**
                 * Modified code from: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#button
                 */
                Object[] options = {"Yes",
                        "No"};
                int n = JOptionPane.showOptionDialog(focusPanel,
                        "Would you like to delete trade",
                        "Confirmation",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.QUESTION_MESSAGE,
                        new Icon() {
                            @Override
                            public void paintIcon(Component c, Graphics g, int x, int y) {

                            }

                            @Override
                            public int getIconWidth() {
                                return 0;
                            }

                            @Override
                            public int getIconHeight() {
                                return 0;
                            }
                        },
                        options,
                        options[0]);
                if(n == 0){
                    PayloadResponse response = deleteTrade(order);

                    if(response != null){
                        Toast t;
                        t = new Toast("Delete Complete", focusPanel);
                        t.showtoast();
                        NavigationHelper.changePanel(focusPanel, new OrganisationalUnit(focusUser, focusPanel));
                    }
                }
            }
        };
        Action edit = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                Trade order =  tradesList.get(modelRow);
                NavigationHelper.changePanel(panel, new EditTrade(order));
            }
        };
        currentTradesTable = new JTable(new MyTableModel());
        currentTradesTable.setRowHeight(30);


        ButtonColumn buttonColumnEdit = new ButtonColumn(currentTradesTable, edit, 0);
        buttonColumnEdit.setMnemonic(KeyEvent.VK_D);

        ButtonColumn buttonColumnDelete = new ButtonColumn(currentTradesTable, delete, 1);
        buttonColumnDelete.setMnemonic(KeyEvent.VK_D);


        currentTradesPanelOne = new JScrollPane(currentTradesTable);
        currentTradesPanelOne.setPreferredSize(new Dimension(600, 300));


        panel.add(currentTradesPanelOne);
        return panel;
    }

    /**
     * Constructs a panel to display the organisational unit's available credits.
     * @return the organisational unit credit panel.
     */
    private JPanel creditPanel(){
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setSize(200, 150);

        JLabel creditTitle = new JLabel("Credits");
        creditTitle.setFont(creditTitle.getFont().deriveFont(Font.BOLD));
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10,0,10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(creditTitle, constraints);

        JLabel creditAmount = new JLabel("" +focusUser.getOrganisationalUnit().getAvailableCredit() );
        creditAmount.setFont(creditAmount.getFont().deriveFont(Font.BOLD, 28));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(creditAmount, constraints);
        return panel;
    }

    /**
     * Constructs a panel to display the information relating to a given asset.
     * @param asset the asset to be displayed.
     * @return the asset panel.
     */
    private JPanel createAssets(Asset asset){
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setSize(200, 150);
        constraints.insets = new Insets(10, 10,0,10);
        JLabel creditTitle = new JLabel(""+ asset.getAssetType().getName());
        creditTitle.setFont(creditTitle.getFont().deriveFont(Font.BOLD));
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 2;
        constraints.weighty = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(creditTitle, constraints);

        JLabel creditAmount = new JLabel("" + asset.getQuantity() );
        creditAmount.setFont(creditAmount.getFont().deriveFont(Font.BOLD, 28));
        constraints.weighty = 2;
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(creditAmount, constraints);
        return panel;
    }

    /**
     * Sends a request to the server to return the assets owned by the organisational unit.
     * @throws IOException
     */
    private void getAssetsList() throws IOException {
        PayloadRequest request = new PayloadRequest();
        Asset newAsset = new Asset();
        newAsset.setOrganisationalUnit(focusUser.getOrganisationalUnit());
        request.setPayloadObject(newAsset);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        assetsList = (java.util.List<Asset>)(List<?>)response.getPayloadObject();
    }

    /**
     * Sends a request to the server to return the trades owned by the organisational unit.
     * @throws IOException
     */
    private void getTradeList() throws IOException {
        PayloadRequest request = new PayloadRequest();
        Trade newTrade = new Trade();
        newTrade.setOrganisationalUnit(focusUser.getOrganisationalUnit());
        newTrade.setStatus(TradeStatus.InMarket);
        request.setPayloadObject(newTrade);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        tradesList = (java.util.List<Trade>)(List<?>)response.getPayloadObject();
    }

    /**
     * Nested class describes the layout of the current trades table.
     */
    public class MyTableModel extends AbstractTableModel {
        private String[] columnNames= {"",
                "",
                "Asset Type",
                "Quantity",
                "Price per Unit",
                "Order Type",
                "Date Created"};

        private Object[][] data = new Object[tradesList != null ? tradesList.size() : 0][8];


        /**
         * Constructs the table data.
         */
        public MyTableModel(){
            int i = 0;
            if (tradesList != null) {
                for (Trade order : tradesList) {

                    if (order != null) {
                        data[i][0] = "Edit";
                        data[i][1] = "Delete";
                        data[i][2] = order.getAssetType().getName();
                        data[i][3] = order.getQuantity();
                        data[i][4] = order.getPrice();
                        data[i][5] = order.getTransactionType().toString();
                        data[i][6] = order.getCreatedDate();
                        i++;
                    }
                }
            }
        }

        /**
         * Gets the name of the column header.
         * @param column index of the column.
         * @return the column header.
         */
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        /**
         * Gets the number of rows.
         * @return the number of rows.
         */
        @Override
        public int getRowCount() {
            return data.length;
        }

        /**
         * Gets the number of columns.
         * @return the number of columns.
         */
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        /**
         * Checks if a given cell in the table is editable.
         * @param row the index of the row.
         * @param column the index of the columns.
         * @return true if the cell is editable, false otherwise.
         */
        public boolean isCellEditable(int row, int column){
            if (column >= 2) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * Gets the value of a given cell.
         * @param rowIndex the index of the row.
         * @param columnIndex the index of the column.
         * @return the object in the cell.
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
    }
    private PayloadResponse deleteTrade(Trade trade)
    {
        CAB302.Common.OrganisationalUnit ou = trade.getOrganisationalUnit();
        PayloadResponse response = null;
        if(trade.getTransactionType() == TradeTransactionType.Buying)
        {
            int creditRefund = trade.getPrice() * trade.getQuantity();
            int changeAmount = trade.getOrganisationalUnit().getAvailableCredit() + creditRefund;
            response = editCredits(ou, changeAmount);
        }
        else
        {
            AssetType assetType = trade.getAssetType();
            Asset asset = getAsset(ou, assetType);
            int assetRefund = trade.getQuantity();
            int changeAmt = asset.getQuantity() + assetRefund;
            response = editAssets(asset, changeAmt);
        }
        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(trade);
        request.setRequestPayloadType(RequestPayloadType.Delete);
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return response;
    }
    private PayloadResponse editCredits(CAB302.Common.OrganisationalUnit ou, int changeAmt){
        ou.setAvailableCredit(changeAmt);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(ou);

        PayloadResponse response = null;

        request.setRequestPayloadType(RequestPayloadType.Update);
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return response;
    }
    private PayloadResponse editAssets (Asset asset, int changeAmt){
        asset.setQuantity(changeAmt);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(asset);
        request.setRequestPayloadType(RequestPayloadType.Update);

        PayloadResponse response = null;
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return response;
    }
    private Asset getAsset(CAB302.Common.OrganisationalUnit ou, AssetType assetType) {
        Asset type = new Asset();

        type.setOrganisationalUnit(ou);
        type.setAssetType(assetType);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Get);
        PayloadResponse response = null;
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return (Asset)response.getPayloadObject();
    }
}
