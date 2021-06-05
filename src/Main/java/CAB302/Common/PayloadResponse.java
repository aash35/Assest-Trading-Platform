package CAB302.Common;

/**
 * Class is required for the server to send objects to the client. Contains a payload type description, a payload object
 * being sent to the server, a description of the payload object type and a checksum.
 */
public class PayloadResponse extends RequestBaseObject {

    private Object payloadObject;

    public Object getPayloadObject() {

        String currentObjectType = getObjectType();

        if (currentObjectType == null) {
            return null;
        }

        try {
            Class classType = Class.forName(currentObjectType);

            return classType.cast(this.payloadObject);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String objectType;

    public String getObjectType() { return this.objectType; }

    public void setObjectType(String objectType) { this.objectType = objectType; }

    public void setPayloadObject(Object payloadObject) {
        this.payloadObject = payloadObject;

        if (payloadObject != null) {
            this.setObjectType(payloadObject.getClass().getName());
        }
    }
}
