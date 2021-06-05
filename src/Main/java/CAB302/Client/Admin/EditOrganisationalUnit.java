package CAB302.Client.Admin;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;
import CAB302.Common.Enums.TradeStatus;
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
    private JLabel messageStackLabel = new JLabel("");

    private JLabel assetNameLabel = new JLabel("Select Asset: ");
    private JComboBox assetCB;

    private JLabel ouNameLabel = new JLabel("Select Organisational Unit: ");
    private JComboBox ouCB;

    private JLabel changeAmtLabel = new JLabel("Change Amount To: ");
    private JSpinner changeAmtField = createSpinner();

    private JButton confirmBtn = new JButton("Confirm");

    private List<OrganisationalUnit> ouList;
    private List<AssetType> assetsTypeList;
    private List<Asset> assets;

    /**
     * Constructs the application page to edit organisational units.
     */
    public EditOrganisationalUnit() {

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
            assetString[i] = ((AssetType) assetsTypeList.get(i - 1)).getName();
        }
        assetCB = new JComboBox(assetString);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Left Column
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(ouNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(assetNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(changeAmtLabel, gbc);

        //Right Column
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(ouCB, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(assetCB, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(changeAmtField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(confirmBtn, gbc);

        confirmBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        OrganisationalUnit oUnit = ouList.get(ouCB.getSelectedIndex());
                        Integer changeAmt = (Integer) changeAmtField.getValue();

                        AssetType assetType;
                        if (assetCB.getSelectedIndex() == 0) {
                            editCredits(oUnit, changeAmt);
                        } else {
                            assetType = assetsTypeList.get(assetCB.getSelectedIndex()-1);

                            Asset asset = getAsset(oUnit, assetType);

                            if (asset == null)
                            {
                                if (changeAmt != 0){
                                    createAsset(oUnit, assetType, changeAmt);
                                }
                            }
                            else
                            {
                                if (checkTrades(oUnit, assetType)){
                                    editDeleteAssets(asset, changeAmt, false);
                                }
                                else
                                {
                                    editDeleteAssets(asset, changeAmt, true);
                                }


                            }
                        }
                        messageStackLabel.setText("Successfully saved");

                        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                        gbc.weighty = 5;
                        gbc.insets = new Insets(20, 0, 0, 0);
                        gbc.gridx = 1;
                        gbc.gridy = 3;
                        add(messageStackLabel, gbc);
                        remove(confirmBtn);

                        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                        gbc.weighty = 5;
                        gbc.insets = new Insets(20, 0, 0, 0);
                        gbc.gridx = 1;
                        gbc.gridy = 4;
                        add(confirmBtn, gbc);
                    }
                });

    }

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


    private void createAsset(OrganisationalUnit ou, AssetType assetType, int changeAmt){
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

    private void editDeleteAssets (Asset asset, int changeAmt, boolean tradeExists){
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

    private JSpinner createSpinner () {
        SpinnerModel model = new SpinnerNumberModel(0, 0, null, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 25));
        return spinner;
    }

    private void getOUList () throws IOException {
        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new OrganisationalUnit());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        ouList = (List<OrganisationalUnit>) (List<?>) response.getPayloadObject();
    }

    private void getAssetTypeList () throws IOException {
        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new AssetType());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        assetsTypeList = (List<AssetType>) (List<?>) response.getPayloadObject();
    }

}