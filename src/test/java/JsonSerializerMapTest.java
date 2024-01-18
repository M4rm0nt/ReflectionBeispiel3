import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonSerializerMapTest {

    @Test
    void testMapSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        Map<String, Integer> map = Map.of("Apple", 1, "Banana", 2);
        String json = serializer.serialize(map);

        assertTrue(json.contains("\"Apple\": 1"), "JSON sollte 'Apple: 1' enthalten");
        assertTrue(json.contains("\"Banana\": 2"), "JSON sollte 'Banana: 2' enthalten");
    }
}
