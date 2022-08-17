package codemetropolis.integration.spigot.plugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


/**
 * @author Crunchify.com
 * @source https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
 * This file gets the path of the XML file from config.properties
 */
public class CrunchifyGetPropertyValues {
    String result = "";
    InputStream inputStream;

    public String getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            String url = prop.getProperty("url");


            result = url;
            System.out.println(result + " <=\n");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;

    }
}