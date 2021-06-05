package CAB302.Client;

import javax.swing.*;

/**
 * Class used to run the client side of the application.
 */
public class Main {

    /**
     * Method initialises the GUI.
     * @param args command line arguments, not used in this application
     */
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
