package serializers;

import utils.Util;

import java.util.Set;

public class StringSerializer extends DataSerializer {
    @Override
    String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        String str = (String) object;
        return String.format(Util.QUOTE_FORMAT, JsonSerializer.escapeString(str));
    }
}