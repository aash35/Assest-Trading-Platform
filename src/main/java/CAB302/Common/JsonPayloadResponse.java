package CAB302.Common;

import com.fasterxml.jackson.databind.*;
import CAB302.Common.Enums.JsonPayloadType;

public class JsonPayloadResponse {

    private BaseClass payloadObject;

    public BaseClass getPayloadObject() {
        try {
            Class classType = Class.forName(getObjectType());

            return (BaseClass)new ObjectMapper().convertValue(payloadObject, classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String objectType;

    public String getObjectType() { return this.objectType; }

    public void setObjectType(String objectType) { this.objectType = objectType; }

    public void setPayloadObject(BaseClass payloadObject) {
        this.payloadObject = payloadObject;
    }
}
