import org.junit.jupiter.api.Test;
import serializers.JsonSerializer;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerCycleHandlingTest {

    @Test
    void testCycleHandling() {
        Node node1 = new Node("Node 1", null);
        Node node2 = new Node("Node 2", node1);
        node1.setNext(node2);

        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(node1);

        assertTrue(json.contains("[Circular Reference]"), "Zyklische Referenzen sollten als '[Circular Reference]' dargestellt werden.");
    }
}
