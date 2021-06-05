package CAB302.Client;

import CAB302.Common.ServerPackages.RuntimeSettings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class initialises the server settings in the RuntimeSettings class.
 */
public class ClientSettings {

    InputStream inputStream;

    /**
     * Construct the client settings and set the appropriate variables in the runtime settings.
     */
    public ClientSettings() {

        try {
            Properties prop = new Properties();
            String propFileName = "client.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Config file '" + propFileName + "' not found");
            }

            RuntimeSettings.IP = prop.getProperty("IP");
            RuntimeSettings.Port = Integer.parseInt(prop.getProperty("Port"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
