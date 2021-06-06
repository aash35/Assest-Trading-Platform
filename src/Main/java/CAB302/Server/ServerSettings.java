package CAB302.Server;

import CAB302.Common.ServerPackages.RuntimeSettings;

import java.io.*;
import java.util.Properties;

/**
 * Class initialises the server settings in the RuntimeSettings class.
 */
public class ServerSettings {

    InputStream inputStream;

    /**
     * Construct the server settings and set the appropriate variables in the runtime settings.
     */
    public ServerSettings() {

        try {
            Properties prop = new Properties();
            String propFileName = "server.properties";

            if (!new File(propFileName).exists()) {
                byte[] array = getClass().getClassLoader().getResourceAsStream(propFileName).readAllBytes();

                FileOutputStream out = new FileOutputStream(propFileName);

                out.write(array);
                out.close();
            }

            File file = new File(propFileName);

            inputStream = new FileInputStream(file);

            prop.load(inputStream);

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
