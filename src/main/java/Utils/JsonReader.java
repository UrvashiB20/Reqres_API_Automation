package Utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.IOException;

public class JsonReader {

    public static String readJsonData(String key) throws IOException, ParseException {
        return (String) getJsonData().get(key);
    }

    public static JSONObject getJsonData() throws IOException, ParseException {

        File jsonFilepath = new File("Resources/Testdata/Testdata.json");
        String jsonString = FileUtils.readFileToString(jsonFilepath,"UTF-8");
        Object object = new JSONParser().parse(jsonString);
        return (JSONObject) object;
    }
}
