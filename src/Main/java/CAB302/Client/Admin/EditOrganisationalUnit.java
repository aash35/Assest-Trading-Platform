package CAB302.Client.Admin;

import CAB302.Client.Client;
import CAB302.Client.Helper.Toast;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
import CAB302.Common.Helpers.NavigationHelper;
import CAB302.Common.ServerPackages.PayloadRequest;
import CAB302.Common.ServerPackages.PayloadResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Class creates the edit organisational unit page of the application GUI.
 */
public class EditOrganisationalUnit extends JPanel {
    private JPanel focusPanel;
    private JPanel titlePanel;
    private JPanel mainPanel;
    private JPanel innerPanel;

    private JLabel assetNameLabel = new JLabel("Asset: ");
    private JComboBox assetCB;

    private JLabel ouNameLabel = new JLabel("Organisational Unit: ");
    private JComboBox ouCB;

    private JLabel changeAmtLabel = new JLabel("Change Amount To: ");
    private JSpinner changeAmtField = createSpinner();

    private JButton confirmBtn = new JButton("Confirm");

    private List<OrganisationalUnit> ouList;
    private List<AssetType> assetsTypeList;

    /**
     * Constructs the application page to edit organisational units.
     */
    public EditOrganisationalUnit(JPanel panel) {
        //Get list of all OrgUnits and add to combobox
        try {
            getOUList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] organisationalUnits = new String[ouList.size()];
        for (int i = 0; i < ouList.size(); i++) {
            organisationalUnits[i] = ouList.get(i).getUnitName();
        }
        ouCB = new JComboBox(organisationalUnits);

        //Get list of all assets and add to combobox
        try {
            getAssetTypeList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] assetString = new String[assetsTypeList.size() + 1];
        assetString[0] = "Credits";
        for (int i = 1; i < assetString.length; i++) {
            assetString[i] = (assetsTypeList.get(i - 1)).getName();
        }
        assetCB = new JComboBox(assetString);

        //GUI stuff
        focusPanel = panel;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(630,500));
        add(mainPanel);

        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());

        JLabel title = new JLabel("Edit Organisational Unit");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28));
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        innerPanel = new JPanel();
        mainPanel.add(innerPanel, BorderLayout.CENTER);

        innerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        //Left Column
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        innerPanel.add(ouNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        innerPanel.add(assetNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        innerPanel.add(changeAmtLabel, gbc);

        //Right Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        innerPanel.add(ouCB, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        innerPanel.add(assetCB, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        innerPanel.add(changeAmtField, gbc);

        //Middle
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        innerPanel.add(confirmBtn, gbc);

        confirmBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        OrganisationalUnit oUnit = ouList.get(ouCB.getSelectedIndex());
                        Integer changeAmt = (Integer) changeAmtField.getValue();

                        PayloadResponse response = null;
                        if (assetCB.getSelectedIndex() == 0) {
                            response = editCredits(oUnit, changeAmt);
                        } else {
                            AssetType assetType = assetsTypeList.get(assetCB.getSelectedIndex()-1);
                            Asset asset = getAsset(oUnit, assetType);
                            if (asset == null)
                            {
                                if (changeAmt != 0){
                                    response = createAsset(oUnit, assetType, changeAmt);
                                }
                            }
                            else
                            {
                                if (checkTrades(oUnit, assetType)){
                                    response = editDeleteAssets(asset, changeAmt, false);
                                }
                                else
                                {
                                    response = editDeleteAssets(asset, changeAmt, true);
                                }
                            }
                        }
                        if (response != null){
                            Toast t;
                            t = new Toast("Asset Successfully Changed", focusPanel);
                            t.showtoast();
                            NavigationHelper.changePanel(focusPanel, new Administration(focusPanel));
                        }
                    }
                });

    }

    /**
     * Checks if an organisational unit has trades for a specified asset
     * @param ou The organisational unit to be checked.
     * @param assetType The asset type that is being searched for.
     * @return a boolean value that is the resulting from if a trade is found.
     */
    private boolean checkTrades(OrganisationalUnit ou, AssetType assetType){
        Trade type = new Trade();

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.List);
        PayloadResponse response = null;
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        List<Trade> tradesList = (List<Trade>)(List<?>) response.getPayloadObject();
        boolean exists = false;

        for (int i = 0; i < tradesList.size(); i++)
        {
            if ((tradesList.get(i).getOrganisationalUnit().getUnitName() == ou.getUnitName())
            && (tradesList.get(i).getAssetType().getName() == assetType.getName())
            && tradesList.get(i).getStatus() == TradeStatus.InMarket)
            {
                exists = true;
            }
        }
        return exists;
    }

    /**
     * Retrieves an asset of a specified Asset Type
     * from a specified Organisational Unit from the database,
     * @param ou The Organisational Unit to be checked.
     * @param assetType The asset type that is being searched for.
     * @param changeAmt the amount that the asset will be changed to.
     * @return a response on if the method was successful or not.
     */
    private PayloadResponse createAsset(OrganisationalUnit ou, AssetType assetType, int changeAmt){
        Asset type = new Asset();
        type.setQuantity(changeAmt);
        type.setAssetType(assetType);
        type.setOrganisationalUnit(ou);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(type);
        request.setRequestPayloadType(RequestPayloadType.Create);

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
     * Edits/Deletes a specified Organisational Units Assets
     * @param asset the asset being edited.
     * @param changeAmt the amount that the asset will be changed to.
     * @param tradeExists determines if an asset is able to be deleted if set to 0.
     * @return a response on if the method was successful or not.
     */
    private PayloadResponse editDeleteAssets (Asset asset, int changeAmt, boolean tradeExists){
        asset.setQuantity(changeAmt);

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(asset);
        if (changeAmt == 0 && !tradeExists)
        {
            request.setRequestPayloadType(RequestPayloadType.Delete);
        }
        else
        {
            request.setRequestPayloadType(RequestPayloadType.Update);
        }

        PayloadResponse response = null;
        try {
            response = new Client().SendRequest(request);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return response;
    }

    /**
     * Edits a specified Organisational Units Credits
     * @param ou the organisational unit being edited.
     * @param changeAmt the amount that the credits will be changed to.
     * @return a response on if the method was successful or not.
     */
    private PayloadResponse editCredits(OrganisationalUnit ou, int changeAmt)
    {
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
     * Creates an integer spinnerbox with a minimum value of 0 and no max value.
     * @return a JSpinner object
     */
    private JSpinner createSpinner ()
    {
        SpinnerModel model = new SpinnerNumberModel(0, 0, null, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 25));
        return spinner;
    }

    /**
     * Retrieves the list of Organisational Units from the database,
     * then assigns it to the ouList property.
     */
    private void getOUList () throws IOException
    {
        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new OrganisationalUnit());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        ouList = (List<OrganisationalUnit>) (List<?>) response.getPayloadObject();
    }

    /**
     * Retrieves the list of Asset Types from the database,
     * then assigns it to the assetTypeList property.
     */
    private void getAssetTypeList () throws IOException
    {
        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new AssetType());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        assetsTypeList = (List<AssetType>) (List<?>) response.getPayloadObject();
    }

}