import enums.Geschlecht;
import models.Mensch;
import org.junit.jupiter.api.Test;
import serializers.JsonSerializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonSerializerObjectTest {

    @Test
    void testCustomObjectSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        Mensch person = new Mensch(
                33, "Celine",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                LocalDateTime.now(),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );
        String json = serializer.serialize(person);

        assertTrue(json.contains("\"name\": \"Celine\""), "JSON sollte 'name: Celine' enthalten");
        assertTrue(json.contains("\"alter\": 33"), "JSON sollte 'alter: 33' enthalten");
    }
}