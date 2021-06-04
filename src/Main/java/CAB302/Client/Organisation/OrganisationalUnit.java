package CAB302.Client.Organisation;

import CAB302.Client.Client;
import CAB302.Common.*;
import CAB302.Common.Enums.RequestPayloadType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class OrganisationalUnit extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel assetPanel;
    JPanel currentTradesPanel;
    private List<Asset> assetsList;

    public OrganisationalUnit(User user) {
        assetPanel = createAssetPanel();
        currentTradesPanel = createCurrentTradesPanel();

        setLayout(new GridBagLayout());


        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(assetPanel);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(currentTradesPanel);


    }
    private JPanel createAssetPanel(){
        GridBagConstraints constraintsOne = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Current Assets");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28));

        constraintsOne.anchor = GridBagConstraints.LINE_START;
        constraintsOne.gridx = 0;
        constraintsOne.gridy = 0;
        panel.add(title);


        JScrollPane scrollPane = new JScrollPane();

        try {
            getAssetsList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scrollPane.add(creditPanel());


        constraintsOne.fill = GridBagConstraints.HORIZONTAL;
        constraintsOne.anchor = GridBagConstraints.LINE_START;
        constraintsOne.gridx = 0;
        constraintsOne.gridy = 1;
        JLabel test = new JLabel("Current Assets");
        test.setFont(title.getFont().deriveFont(Font.BOLD, 28));
        //panel.add(scrollPane);
        panel.add(test);

        return panel;
    }
    private JPanel createCurrentTradesPanel(){
        JPanel panel = new JPanel();

        return panel;

    }
    private JPanel creditPanel(){
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        panel.setSize(200, 150);

        JLabel creditTitle = new JLabel("Credits");
        creditTitle.setFont(creditTitle.getFont().deriveFont(Font.BOLD));
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 2;
        panel.add(creditTitle);

        return panel;
    }

    private void getAssetsList() throws IOException {

        PayloadRequest request = new PayloadRequest();

        request.setPayloadObject(new Asset());
        request.setRequestPayloadType(RequestPayloadType.List);

        PayloadResponse response = new Client().SendRequest(request);
        assetsList = (java.util.List<Asset>)(List<?>)response.getPayloadObject();
    }
}
