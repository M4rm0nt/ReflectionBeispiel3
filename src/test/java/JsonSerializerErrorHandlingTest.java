import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerErrorHandlingTest {

    @Test
    void testInaccessibleFieldSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        PrivateFieldObject obj = new PrivateFieldObject();
        String json = serializer.serialize(obj);

        assertTrue(json.contains("\"privateField\": \"should not be accessible\""),
                "Das private Feld sollte im JSON-Ergebnis enthalten sein.");
    }

    private static class PrivateFieldObject {
        @JsonProperty
        private String privateField = "should not be accessible";
    }
}
