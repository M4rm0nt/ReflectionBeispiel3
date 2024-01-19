package serializers;

import java.util.Set;

public class PrimitiveSerializer extends DataSerializer {
    @Override
    String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        return object.toString();
    }
}