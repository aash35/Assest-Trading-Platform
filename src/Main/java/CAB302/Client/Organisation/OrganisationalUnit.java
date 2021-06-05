package CAB302.Client.Organisation;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrganisationalUnit extends JPanel {
    private JPanel assetPanel;
    private JPanel currentTradesPanel;
    private User focusUser;
    private GridBagConstraints c = new GridBagConstraints();
    private List<Asset> assetsList;
    private List<Trade> tradesList;
    private JTable currentTradesTable;

    public JScrollPane scrollPane;

    public OrganisationalUnit(User user) {
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
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        add(currentTradesPanel, c);

    }
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
        for (Asset asset: assetsList) {
            allPanel.add(createAssets(asset));
        }
        scrollPane = new JScrollPane(allPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(650, 110));
        panelOne.add(scrollPane);
        return panelOne;
    }
    private JPanel createCurrentTradesPanel(){
        JPanel panel = new JPanel();
        try {
            getTradeList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentTradesTable = new JTable(new MyTableModel());
        currentTradesTable.setFillsViewportHeight(true);
        currentTradesTable.setRowSelectionAllowed(false);
        currentTradesTable.setColumnSelectionAllowed(false);

        panel.add(currentTradesTable);
        return panel;

    }
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

    private void getAssetsList() throws IOException {
        PayloadRequest request = new PayloadRequest();
        Asset newAsset = new Asset();
        newAsset.setOrganisationalUnit(focusUser.getOrganisationalUnit());
        request.setPayloadObject(newAsset);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        assetsList = (java.util.List<Asset>)(List<?>)response.getPayloadObject();
    }

    private void getTradeList() throws IOException {
        PayloadRequest request = new PayloadRequest();
        Trade newTrade = new Trade();
        newTrade.setOrganisationalUnit(focusUser.getOrganisationalUnit());
        request.setPayloadObject(newTrade);
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        tradesList = (java.util.List<Trade>)(List<?>)response.getPayloadObject();
    }

    public class MyTableModel extends AbstractTableModel {
        private String[] columnNames= {"Asset Type",
                "Quantity",
                "Price per Unit",
                "Order Type",
                "Date Created"};

        private Object[][] data = new Object[tradesList.size()][6];
        public MyTableModel(){
            int i = 0;
            for (Trade order: tradesList){
                data[i][0] = order.getAssetType().getName();
                data[i][1] = order.getQuantity();
                data[i][2] = order.getPrice();
                data[i][3] = order.getTransactionType().toString();
                data[i][4] = order.getCreatedDate();
                i++;
            }
        }
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        public boolean isCellEditable(int row, int column){
            if (column < 2) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
    }
}
