package CAB302.Client.Organisation;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.OrganisationalUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EditTrade extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();

    private JLabel messageStackLabel = new JLabel("");

    private JLabel title;

    private JLabel editQuantityLabel = new JLabel("Change Quantity To: ");
    private JSpinner editQuantityField = createSpinner();

    private JLabel editPriceLabel = new JLabel("Change Price To: ");
    private JSpinner editPriceField = createSpinner();

    private JButton confirmButton = new JButton("Confirm");

    public EditTrade(Trade trade){

        title = new JLabel("Edit Trade - "+ trade.getTransactionType() + " - " + (String)trade.getAssetType().getName());
        setLayout(new GridBagLayout());
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        // First Column
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(editQuantityLabel, gbc);

        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(editPriceLabel, gbc);

        // Second Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(editQuantityField, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(editPriceField, gbc);

        confirmButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Integer newQuantity = (Integer) editQuantityField.getValue();
                        Integer newPrice = (Integer) editPriceField.getValue();

                        //Edit Org Assets
                        editOrg(trade, newQuantity, newPrice);

                        //Edit Trade in Database
                        editTrade(trade, newQuantity, newPrice);

                        messageStackLabel.setText("Successfully saved");

                        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                        gbc.weighty = 5;
                        gbc.insets = new Insets(20,0,0,0);
                        gbc.gridx = 1;
                        gbc.gridy = 2;
                        add(messageStackLabel, gbc);
                        remove(confirmButton);

                        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                        gbc.weighty = 5;
                        gbc.insets = new Insets(20,0,0,0);
                        gbc.gridx = 1;
                        gbc.gridy = 3;
                        add(confirmButton, gbc);
                    }
                });

        // Last Row
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 5;
        gbc.insets = new Insets(20,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(confirmButton, gbc);
    }

    private void editOrg(Trade trade, int newQuantity, int newPrice){
        TradeTransactionType tradeType = trade.getTransactionType();
        int oldQuantity = trade.getQuantity();
        int oldPrice = trade.getPrice();
        OrganisationalUnit ou = trade.getOrganisationalUnit();
        AssetType assetType = null;
        if (tradeType == TradeTransactionType.Selling)
        {
            assetType = trade.getAssetType();
        }

        int quantityDiff = oldQuantity - newQuantity;
        if (tradeType == TradeTransactionType.Buying)
        {
            int priceDiff = oldPrice - newPrice;
            int creditDiff = quantityDiff * priceDiff;
            int changeAmt = ou.getAvailableCredit() + creditDiff;
            if (changeAmt >= 0)
            {
                editCredits(ou, changeAmt);
            }
            else
            {
                //Toast that says error, not enough credits
            }
        }
        else
        {
            Asset asset = getAsset(ou, assetType);
            int changeAmt = asset.getQuantity() + quantityDiff;
            editAssets(asset, changeAmt);
        }
    }

    private void editAssets (Asset asset, int changeAmt){
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
    }

    private Asset getAsset(OrganisationalUnit ou, AssetType assetType) {
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

    private void editCredits(OrganisationalUnit ou, int changeAmt){
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
    }

    private void editTrade(Trade trade, int newQuantity, int newPrice){
        trade.setQuantity(newQuantity);
        trade.setPrice(newPrice);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(trade);

        PayloadResponse response = null;

        request.setRequestPayloadType(RequestPayloadType.Update);
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private JSpinner createSpinner () {
        SpinnerModel model = new SpinnerNumberModel(1, 1, null, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 25));
        return spinner;
    }


}
