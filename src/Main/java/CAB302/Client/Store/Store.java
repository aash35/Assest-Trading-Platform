package CAB302.Client.Store;

import CAB302.Client.Client;
import CAB302.Common.AssetType;
import CAB302.Common.BaseObject;
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

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(630,500));
        add(mainPanel);

        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());

        innerAssetsPanel = new JPanel();
        innerAssetsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        outerAssetsPanel = new JScrollPane(innerAssetsPanel);
        outerAssetsPanel.setPreferredSize(new Dimension(400,400));
        outerAssetsPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        outerAssetsPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerAssetsPanel.setWheelScrollingEnabled(true);

        JLabel storeLabel = new JLabel("Store");
        storeLabel.setFont(new Font(storeLabel.getFont().getFontName(), Font.PLAIN, 42));
        titlePanel.add(storeLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        try {
            getAssetsList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        innerAssetsPanel.setPreferredSize(new Dimension(400, 70 * (assetsList.size()/4)));

        for (BaseObject assetType: assetsList) {
            JButton assetButton = createAssetButton((AssetType) assetType);
            innerAssetsPanel.add(assetButton);
        }

        mainPanel.add(outerAssetsPanel, BorderLayout.CENTER);
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
        JButton button = new JButton(assetType.getName());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.changePanel(panel, new BuySellAsset(panel, assetType));
            }
        });
        button.setPreferredSize(new Dimension(140,50));
        return button;
    }
}
