package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static String readProperty(String key) throws IOException {

        String filepath = "Config.properties";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        Properties properties = new Properties();
        properties.load(bufferedReader);
        return properties.getProperty(key);
    }
}
