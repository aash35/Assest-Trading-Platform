package CAB302.Client;

import CAB302.Common.Asset;
import CAB302.Common.AssetType;
import CAB302.Common.BaseObject;
import CAB302.Common.Helpers.NavigationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Store extends JPanel {
    private JPanel titlePanel;
    private JPanel assetsPanel;
    private List<BaseObject> assetsList;
    private JFrame frame;

    public Store(JFrame frame) {
        this.frame = frame;
        Color c = new Color(243, 244, 246);
        setLayout(new BorderLayout());

        titlePanel = createPanel(c);
        assetsPanel = createPanel(c);

        add(titlePanel, BorderLayout.NORTH);
        add(assetsPanel, BorderLayout.CENTER);

        JLabel storeLabel = new JLabel("Store");
        storeLabel.setFont(new Font(storeLabel.getFont().getFontName(), Font.PLAIN, 42));
        titlePanel.add(storeLabel);

        getAssetsList();

        layoutAssetsPanel();
    }

    private JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    private void getAssetsList() {
        AssetType emptyType = new AssetType();

        assetsList = emptyType.list();
    }

    private void layoutAssetsPanel() {
        assetsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 0.5;
        constraints.weighty = 0.5;

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        int xPos = 0;
        int yPos = 0;
        int width = 1;
        int height = 1;

        for (BaseObject assetType: assetsList
             ) {
            JButton assetButton = createAssetButton((AssetType) assetType);
            addToPanel(assetsPanel, assetButton, constraints, xPos, yPos, width, height);

            if (xPos < 4) {
                xPos++;
            }
            else{
                xPos = 0;
                yPos++;
            }
        }
    }

    private JButton createAssetButton(AssetType assetType){
        JButton button = new JButton();
        button.setText(assetType.getName());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationHelper.buySellOrder(frame, assetType);
            }
        });
        return button;
    }

    private void addToPanel(JPanel panel, Component component,GridBagConstraints constraints,
                            int xPos, int yPos, int width, int height){
        constraints.gridx = xPos;
        constraints.gridy = yPos;
        constraints.gridwidth = width;
        constraints.gridheight = height;

        panel.add(component, constraints);
    }
}
