import org.junit.jupiter.api.Test;
import serializers.JsonSerializer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerBasicTest {

    @Test
    void testStringSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize("Hello World");
        assertEquals("\"Hello World\"", json);
    }

    @Test
    void testNumberSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(123);
        assertEquals("123", json);
    }

    @Test
    void testBooleanSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(true);
        assertEquals("true", json);
    }

    @Test
    void testDateSerialization() {
        JsonSerializer serializer = new JsonSerializer();
        LocalDate date = LocalDate.of(2021, 1, 1);
        String json = serializer.serialize(date);
        assertEquals("\"2021-01-01\"", json);
    }
}
