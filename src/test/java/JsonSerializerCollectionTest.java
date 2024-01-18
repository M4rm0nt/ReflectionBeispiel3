import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonSerializerCollectionTest {

    @Test
    void testListSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        List<String> list = List.of("Apple", "Banana", "Cherry");
        String json = serializer.serialize(list);

        String expectedJson = "[\n" +
                "  \"Apple\",\n" +
                "  \"Banana\",\n" +
                "  \"Cherry\"\n" +
                "]";
        assertEquals(expectedJson, json);
    }



    @Test
    void testSetSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
        String json = serializer.serialize(set);
        assertTrue(json.contains("1"));
        assertTrue(json.contains("2"));
        assertTrue(json.contains("3"));
    }
}
