import java.util.Set;

public abstract class DataSerializer {
    abstract String serialize(Object object, Set<Object> visitedObjects, int indentLevel);
}