package CAB302.Client.Organisation;

import CAB302.Client.Admin.Administration;
import CAB302.Client.Client;
import CAB302.Client.Helper.Toast;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeTransactionType;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.OrganisationalUnit;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Class creates the edit trade page of the application GUI.
 */
public class EditTrade extends JPanel {
    private JPanel focusPanel;
    private User focusUser;
    private GridBagConstraints gbc = new GridBagConstraints();

    private JLabel title;

    private JLabel editQuantityLabel = new JLabel("Change Quantity To: ");
    private JSpinner editQuantityField = createSpinner();

    private JLabel editPriceLabel = new JLabel("Change Price To: ");
    private JSpinner editPriceField = createSpinner();

    private JButton confirmButton = new JButton("Confirm");

    /**
     * Constructs the application page to edit a trade.
     * @param trade the trade being edited.
     */
    public EditTrade(Trade trade, User user, JPanel panel){
        focusPanel = panel;
        focusUser = user;
        createGUI(trade);
        confirmButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Integer newQuantity = (Integer) editQuantityField.getValue();
                        Integer newPrice = (Integer) editPriceField.getValue();

                        //Edit Org Assets
                        PayloadResponse response = editOrg(trade, newQuantity, newPrice, focusPanel);

                        if (response != null){
                            //Edit Trade in Database
                            response = editTrade(trade, newQuantity, newPrice);
                        }

                        if (response != null){
                            Toast t;
                            t = new Toast("Trade Successfully Changed", focusPanel);
                            t.showtoast();
                            NavigationHelper.changePanel(focusPanel, new CAB302.Client.Organisation.OrganisationalUnit(focusUser, focusPanel));
                        }
                    }
                });
    }

    /**
     * Constructs the application page to edit a trade.
     * @param trade used to create the title
     */
    private void createGUI(Trade trade){
        title = new JLabel("Edit Trade - "+ trade.getTransactionType() + " - " + (String)trade.getAssetType().getName());
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20));
        setLayout(new GridBagLayout());
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        //Middle Top
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        // First Column
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridwidth = 1;
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
        gbc.gridy = 1;
        editQuantityField.setValue(trade.getQuantity());
        add(editQuantityField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        editPriceField.setValue(trade.getPrice());
        add(editPriceField, gbc);

        //Middle Bottom
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(confirmButton, gbc);
    }

    /**
     * Edits a specified Organisational Units Assets based on
     * the change in quantity and price of a trade.
     * @param trade the trade being edited.
     * @param newQuantity the amount that the asset will be changed to
     * @param newPrice the amount that the asset will be changed to.
     * @param focusPanel helps display notification messages.
     */
    private PayloadResponse editOrg(Trade trade, int newQuantity, int newPrice, JPanel focusPanel){
        TradeTransactionType tradeType = trade.getTransactionType();
        int oldQuantity = trade.getQuantity();
        int oldPrice = trade.getPrice();
        OrganisationalUnit ou = trade.getOrganisationalUnit();
        AssetType assetType = null;
        if (tradeType == TradeTransactionType.Selling)
        {
            assetType = trade.getAssetType();
        }

        PayloadResponse response = null;

        if (tradeType == TradeTransactionType.Buying)
        {
            int oldTotal = oldQuantity * oldPrice;
            int newTotal = newQuantity * newPrice;
            int priceDifference = oldTotal - newTotal;
            int changeAmt = ou.getAvailableCredit() + priceDifference;
            if (changeAmt >= 0)
            {
                response = editCredits(ou, changeAmt);
            }
            else
            {
                Toast t;
                t = new Toast("Error: Not enough credits", focusPanel);
                t.showtoast();
            }
        }
        else
        {
            int quantityDiff = oldQuantity - newQuantity;
            Asset asset = getAsset(ou, assetType);
            int changeAmt = asset.getQuantity() + quantityDiff;
            if (changeAmt >= 0)
            {
                response = editAssets(asset, changeAmt);
            }
            else
            {
                String string = String.format("Error: Not enough %s to sell", trade.getAssetType().getName());
                Toast t;
                t = new Toast(string, focusPanel);
                t.showtoast();
            }
        }
        return response;
    }

    /**
     * Edits a specified Organisational Units Assets
     * @param asset the asset being edited.
     * @param changeAmt the amount that the asset will be changed to.
     * @return a response on if the method was successful or not.
     */
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

    /**
     * Retrieves an asset of a specified Asset Type
     * from a specified Organisational Unit from the database,
     * @param ou The Organisational Unit to be checked.
     * @param assetType The asset type that is being searched for.
     * @return the asset if it is found or null.
     */
    private Asset getAsset(OrganisationalUnit ou, AssetType assetType) {
        //Create the conditions for the search
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

    /**
     * Edits a specified Organisational Units Credits
     * @param ou the organisational unit being edited.
     * @param changeAmt the amount that the credits will be changed to.
     * @return a response on if the method was successful or not.
     */
    private PayloadResponse editCredits(OrganisationalUnit ou, int changeAmt){
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

    /**
     * Edits the specified trade to its new quantity and price
     * @param trade The Organisational Unit to be checked.
     * @param newQuantity The quantity the trade is changed to.
     * @param newPrice The price the trade is changed to.
     * @return a response on if the method was successful or not.
     */
    private PayloadResponse editTrade(Trade trade, int newQuantity, int newPrice){
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
        return response;
    }

    /**
     * Creates an integer spinnerbox with a minimum value of 0 and no max value.
     * @return a JSpinner object
     */
    private JSpinner createSpinner () {
        SpinnerModel model = new SpinnerNumberModel(1, 1, null, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 25));
        return spinner;
    }


}
