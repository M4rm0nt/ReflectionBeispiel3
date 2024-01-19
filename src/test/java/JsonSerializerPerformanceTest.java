import interfaces.JsonProperty;
import org.junit.jupiter.api.Test;
import serializers.JsonSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonSerializerPerformanceTest {

    @Test
    void testLargeObjectSerializationPerformance() {
        JsonSerializer serializer = new JsonSerializer();
        LargeObject largeObject = new LargeObject();
        long startTime = System.currentTimeMillis();
        serializer.serialize(largeObject);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertTrue(duration < 1000, "Die Serialisierung sollte weniger als 1 Sekunde dauern.");
    }

    private static class LargeObject {
        @JsonProperty
        private int number = 123;
        @JsonProperty
        private String text = "Ein langer Beispieltext";
        @JsonProperty
        private LocalDateTime dateTime = LocalDateTime.now();
        @JsonProperty
        private List<String> list = new ArrayList<>();
        @JsonProperty
        private Map<String, String> map = new HashMap<>();

        LargeObject() {
            for (int i = 0; i < 1000; i++) {
                list.add("Element " + i);
                map.put("Schlüssel " + i, "Wert " + i);
            }
        }
    }
}
