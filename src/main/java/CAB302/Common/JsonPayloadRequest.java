package CAB302.Common;

import CAB302.Common.Enums.JsonPayloadType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonPayloadRequest {

    private String checksum;

    public String getChecksum() { return this.checksum; }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    private JsonPayloadType jsonPayloadType;

    public JsonPayloadType getJsonPayloadType() {
        return this.jsonPayloadType;
    }

    public void setJsonPayloadType(JsonPayloadType jsonPayloadType) {
        this.jsonPayloadType = jsonPayloadType;
    }

    private Object payloadObject;

    public Object getPayloadObject() {
        try {
            Class classType = Class.forName(getObjectType());
            return (BaseClass)new ObjectMapper().convertValue(payloadObject, classType);
        } catch (Exception e) {
            return null;
        }
    }

    public void setPayloadObject(Object payloadObject) {
        this.payloadObject = payloadObject;
        this.setObjectType(payloadObject.getClass().getName());
    }

    private String objectType;

    public String getObjectType() { return this.objectType; }

    public void setObjectType(String objectType) { this.objectType = objectType; }

    public String getJsonString()  {
        Gson g = new Gson();

        String jsonString = g.toJson(this);

        return jsonString;
    }
}
