package serializers;

import java.util.Set;

public abstract class DataSerializer {
    public abstract String serialize(Object object, Set<Object> visitedObjects, int indentLevel);
}