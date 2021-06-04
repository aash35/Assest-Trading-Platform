package CAB302.Client.Organisation;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class OrganisationalUnit extends JPanel {
    JPanel assetPanel;
    JPanel currentTradesPanel;
    User focusUser;
    GridBagConstraints c = new GridBagConstraints();
    private List<Asset> assetsList;

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



        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(assetPanel, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        //add(currentTradesPanel, c);


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
        JScrollPane scrollPane = new JScrollPane(allPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollPane.setPreferredSize(new Dimension(650, 110));
        panelOne.add(scrollPane);

        return panelOne;
    }
    private JPanel createCurrentTradesPanel(){
        JPanel panel = new JPanel();

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
}
