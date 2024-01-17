import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {

    @Test
    void testSerializeNonNullObject() {
        Mensch person = new Mensch(32, "Celine", List.of("Lesen", "Schwimmen"),
                List.of(List.of("Kochen", "Joggen"), List.of("Malen", "Yoga")));
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);

        assertAll(
                () -> assertTrue(json.contains("\"alter\":32")),
                () -> assertTrue(json.contains("\"name\":\"Celine\"")),
                () -> assertTrue(json.contains("\"hobbies\":[\"Lesen\",\"Schwimmen\"]")),
                () -> assertTrue(json.contains("\"komplexeHobbies\":[[\"Kochen\",\"Joggen\"],[\"Malen\",\"Yoga\"]]"))
        );
    }

    @Test
    void testSerializeWithNullValue() {
        Mensch personWithNull = new Mensch(32, null, List.of("Lesen", "Schwimmen"), null);
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(personWithNull);

        assertAll(
                () -> assertTrue(json.contains("\"alter\":32")),
                () -> assertTrue(json.contains("\"name\":null")),
                () -> assertTrue(json.contains("\"hobbies\":[\"Lesen\",\"Schwimmen\"]")),
                () -> assertTrue(json.contains("\"komplexeHobbies\":null"))
        );
    }

    @Test
    void testSerializeWithEmptyList() {
        Mensch personWithEmptyList = new Mensch(32, "Celine", Collections.emptyList(), Collections.emptyList());
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(personWithEmptyList);

        assertAll(
                () -> assertTrue(json.contains("\"alter\":32")),
                () -> assertTrue(json.contains("\"name\":\"Celine\"")),
                () -> assertTrue(json.contains("\"hobbies\":[]")),
                () -> assertTrue(json.contains("\"komplexeHobbies\":[]"))
        );
    }

    @Test
    void testSerializeComplexObject() {
        Mensch person = new Mensch(32, "Celine", Arrays.asList("Lesen", "Schwimmen"),
                Arrays.asList(Arrays.asList("Kochen", "Joggen"), Arrays.asList("Malen", "Yoga")));
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);

        assertAll(
                () -> assertTrue(json.contains("\"alter\":32")),
                () -> assertTrue(json.contains("\"name\":\"Celine\"")),
                () -> assertTrue(json.contains("\"hobbies\":[\"Lesen\",\"Schwimmen\"]")),
                () -> assertTrue(json.contains("\"komplexeHobbies\":[[\"Kochen\",\"Joggen\"],[\"Malen\",\"Yoga\"]]"))
        );
    }

    @Test
    void testSerializeSpecialCharacters() {
        Mensch person = new Mensch(32, "Celine \"The Coder\"", List.of("Lesen"),
                List.of(List.of("Programmieren \"Java\"", "Schach \"Meister\"")));
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);

        System.out.println(json);

        assertAll(
                () -> assertTrue(json.contains("\"alter\":32")),
                () -> assertTrue(json.contains("\"name\":\"Celine \\\"The Coder\\\"\"")),
                () -> assertTrue(json.contains("\"hobbies\":[\"Lesen\"]")),
                () -> assertTrue(json.contains("\"komplexeHobbies\":[[\"Programmieren \\\"Java\\\"\",\"Schach \\\"Meister\\\"\"]]"))
        );
    }

    @Test
    void testSerializeNullObject() {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(null);

        assertEquals("null", json);
    }
}
