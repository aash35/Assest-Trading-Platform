package CAB302.Client;

import CAB302.Common.AssetType;
import CAB302.Common.BaseObject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Store extends JPanel {
    private JPanel titlePanel;
    private JPanel assetsPanel;
    private List<BaseObject> assetsList;

    public Store(JFrame frame) {
        Color c = new Color(243, 244, 246);
        setLayout(new BorderLayout());

        titlePanel = createPanel(c);
        assetsPanel = createPanel(c);

        add(titlePanel, BorderLayout.NORTH);
        add(assetsPanel, BorderLayout.CENTER);

        JLabel storeLabel = new JLabel("Store");
        storeLabel.setFont(new Font(storeLabel.getFont().getFontName(), Font.PLAIN, 42));
        titlePanel.add(storeLabel);
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

        // First Column
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(0, 0, 0, 0);
    }
}
