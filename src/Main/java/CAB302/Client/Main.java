package CAB302.Client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GUI GUI = new GUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
