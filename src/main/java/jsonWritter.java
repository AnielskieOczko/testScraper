import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class jsonWritter {

    public static JSONObject writeToJson(Map articleData) throws IOException {

        JSONObject obj = new JSONObject();

        obj.put("Title",articleData.get("title"));
        obj.put("Date",articleData.get("date"));
        obj.put("Category",articleData.get("category"));
        obj.put("Body",articleData.get("body"));

        return obj;
        }

    }



