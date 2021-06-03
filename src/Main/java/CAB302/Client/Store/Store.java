package CAB302.Client.Store;

import CAB302.Client.Client;
import CAB302.Common.AssetType;
import CAB302.Common.BaseObject;
import CAB302.Common.Colors.Grey;
import CAB302.Common.Colors.Purple;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.PayloadRequest;
import CAB302.Common.PayloadResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Store extends JPanel {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel innerAssetsPanel;
    private JScrollPane outerAssetsPanel;
    private List<AssetType> assetsList;
    private JPanel panel;

    public Store(JPanel panel) {
        this.panel = panel;
        Color c = new Purple();
        setBackground(c);

        mainPanel = createPanel(c);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(630,500));
        add(mainPanel);

        titlePanel = createPanel(c);
        titlePanel.setLayout(new FlowLayout());

        innerAssetsPanel = createPanel(c);
        innerAssetsPanel.setLayout(new GridLayout(0,4,10,10));

        outerAssetsPanel = new JScrollPane(innerAssetsPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerAssetsPanel.setWheelScrollingEnabled(true);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(outerAssetsPanel, BorderLayout.CENTER);

        JLabel storeLabel = new JLabel("Store");
        storeLabel.setFont(new Font(storeLabel.getFont().getFontName(), Font.PLAIN, 42));
        titlePanel.add(storeLabel);

        try {
            getAssetsList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (BaseObject assetType: assetsList
             ) {
            JButton assetButton = createAssetButton((AssetType) assetType);
            innerAssetsPanel.add(assetButton);
        }
    }

    /**
     * Creates a JPanel object
     * @param c the background color of the panel.
     * @return a JPanel object
     */
    private JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        //panel.setBackground(c);
        return panel;
    }

    /**
     * Retrieves the list of current asset types from the database, then assigns it to the assetsList property.
     */
    private void getAssetsList() throws IOException {

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new AssetType());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        assetsList = (List<AssetType>)(List<?>)response.getPayloadObject();
    }

    /**
     * Creates a button for an asset, that will direct to the appropriate BuySellAsset page when clicked.
     * @param assetType
     * @return a JButton object.
     */
    private JButton createAssetButton(AssetType assetType){
        JButton button = new JButton();
        button.setText(assetType.getName());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.changePanel(panel, new BuySellAsset(assetType));
            }
        });
        return button;
    }
}
