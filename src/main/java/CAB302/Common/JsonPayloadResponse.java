package CAB302.Common;

import com.fasterxml.jackson.databind.*;
import CAB302.Common.Enums.JsonPayloadType;

public class JsonPayloadResponse extends JsonBaseObject {

    private BaseObject payloadObject;

    public BaseObject getPayloadObject() {
        try {
            String currentObjectType = getObjectType();

            if (currentObjectType == null) {
                return null;
            }

            Class classType = Class.forName(currentObjectType);

            return (BaseObject)new ObjectMapper().convertValue(payloadObject, classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String objectType;

    public String getObjectType() { return this.objectType; }

    public void setObjectType(String objectType) { this.objectType = objectType; }

    public void setPayloadObject(BaseObject payloadObject) {
        this.payloadObject = payloadObject;

        if (payloadObject != null) {
            this.setObjectType(payloadObject.getClass().getName());
        }
    }
}
