package CAB302.Common;

import CAB302.Common.Enums.JsonPayloadType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPayload {

    private String checksum;

    public String getChecksum() {
        return this.checksum;
    }

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

    private BaseClass payloadObject;

    public BaseClass getPayloadObject() {
        return this.payloadObject;
    }

    public void setPayloadObject(BaseClass payloadObject) {
        this.payloadObject = payloadObject;
    }

    public String getJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(this);

        return jsonString;
    }
}
