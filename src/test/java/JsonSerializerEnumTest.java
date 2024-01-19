import enums.Geschlecht;
import org.junit.jupiter.api.Test;
import serializers.JsonSerializer;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerEnumTest {

    @Test
    void testEnumSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(Geschlecht.WEIBLICH);
        assertEquals("\"WEIBLICH\"", json);
    }
}
