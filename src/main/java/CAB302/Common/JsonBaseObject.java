package CAB302.Common;

import com.google.gson.Gson;

public class JsonBaseObject {

    public String getJsonString()  {
        Gson g = new Gson();

        String jsonString = g.toJson(this);

        return jsonString;
    }
}
