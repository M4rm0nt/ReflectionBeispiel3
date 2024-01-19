package serializers;

import java.util.Set;

public class PrimitiveSerializer extends DataSerializer {
    @Override
    public String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        return object.toString();
    }
}