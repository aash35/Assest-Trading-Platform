package CAB302.Common;

import CAB302.Common.Enums.RequestPayloadType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class is required for the client to send objects to the server. Contains a payload type description, a payload object
 * being sent to the server, a description of the payload object type and a checksum.
 */
public class PayloadRequest extends RequestBaseObject {

    private String checksum;

    public String getChecksum() { return this.checksum; }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    private RequestPayloadType requestPayloadType;

    public RequestPayloadType getRequestPayloadType() {
        return this.requestPayloadType;
    }

    public void setRequestPayloadType(RequestPayloadType requestPayloadType) {
        this.requestPayloadType = requestPayloadType;
    }

    private Object payloadObject;

    public Object getPayloadObject() {
        try {
            Class classType = Class.forName(getObjectType());
            return (BaseObject)new ObjectMapper().convertValue(payloadObject, classType);
        } catch (Exception e) {
            return null;
        }
    }

    public void setPayloadObject(Object payloadObject) {
        this.payloadObject = payloadObject;

        if (payloadObject != null) {
            this.setObjectType(payloadObject.getClass().getName());
        }
    }

    private String objectType;

    public String getObjectType() { return this.objectType; }

    public void setObjectType(String objectType) { this.objectType = objectType; }
}
