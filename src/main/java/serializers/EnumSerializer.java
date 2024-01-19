package serializers;

import utils.Util;

import java.util.Set;

public class EnumSerializer extends DataSerializer {
    @Override
    public String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        Enum<?> enumValue = (Enum<?>) object;
        return String.format(Util.QUOTE_FORMAT, enumValue.name());
    }
}