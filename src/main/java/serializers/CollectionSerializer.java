package serializers;

import java.util.Collection;
import java.util.Set;

public class CollectionSerializer extends DataSerializer {
    @Override
    public String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        StringBuilder jsonCollection = new StringBuilder("[\n");
        String indent = " ".repeat(indentLevel * 2);
        String innerIndent = " ".repeat((indentLevel + 1) * 2);
        boolean first = true;
        Collection<?> collection = (Collection<?>) object;
        for (Object item : collection) {
            if (!first) {
                jsonCollection.append(",\n");
            }
            jsonCollection.append(innerIndent)
                    .append(JsonSerializer.serializeInternal(item, visitedObjects, indentLevel + 1));
            first = false;
        }
        jsonCollection.append("\n").append(indent).append("]");
        return jsonCollection.toString();
    }
}