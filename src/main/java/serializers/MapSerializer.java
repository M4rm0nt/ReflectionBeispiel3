package serializers;

import utils.Util;

import java.util.Map;
import java.util.Set;

public class MapSerializer extends DataSerializer {
    @Override
    public String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        StringBuilder jsonMap = new StringBuilder("{\n");
        String indent = " ".repeat(indentLevel * 2);
        String innerIndent = " ".repeat((indentLevel + 1) * 2);
        boolean first = true;
        Map<?, ?> map = (Map<?, ?>) object;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                jsonMap.append(",\n");
            }
            jsonMap.append(innerIndent)
                    .append(JsonSerializer.serializeInternal(entry.getKey(), visitedObjects, indentLevel + 1))
                    .append(Util.COLON)
                    .append(" ")
                    .append(JsonSerializer.serializeInternal(entry.getValue(), visitedObjects, indentLevel + 1));
            first = false;
        }
        jsonMap.append("\n").append(indent).append("}");
        return jsonMap.toString();
    }
}