import enums.Geschlecht;
import model.Mensch;
import org.junit.jupiter.api.Test;
import serializers.JsonSerializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonSerializerInheritanceTest {

    @Test
    void testInheritanceSerialization() {
        Mensch person = new Mensch(
                33, "Celine",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                LocalDateTime.now(),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );

        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);

        assertTrue(json.contains("\"art\":\"Homo sapiens\""), "Die ererbte Eigenschaft 'art' sollte in der Serialisierung enthalten sein.");
        assertTrue(json.contains("\"name\":\"Celine\""), "Die Eigenschaft 'name' sollte in der Serialisierung enthalten sein.");
    }
}
