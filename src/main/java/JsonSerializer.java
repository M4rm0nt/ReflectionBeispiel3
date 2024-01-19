import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonProperty {
}

public class JsonSerializer {

    public static final Map<Class<?>, Field[]> fieldsCache = new HashMap<>();

    public String serialize(Object object) {
        return serializeInternal(object, new HashSet<>(), 0);
    }

    public static String serializeInternal(Object object, Set<Object> visitedObjects, int indentLevel) {
        if (object == null) {
            return "null";
        }
        if (visitedObjects.contains(object)) {
            return "\"[Circular Reference]\"";
        }
        visitedObjects.add(object);

        JsonSerializerFactory factory = new JsonSerializerFactory();
        return factory.getSerializer(object).serialize(object, visitedObjects, indentLevel);
    }

    public static String escapeString(String string) {
        StringBuilder escaped = new StringBuilder();
        for (char c : string.toCharArray()) {
            switch (c) {
                case '\"': escaped.append("\\\""); break;
                case '\\': escaped.append("\\\\"); break;
                case '\b': escaped.append("\\b"); break;
                case '\f': escaped.append("\\f"); break;
                case '\n': escaped.append("\\n"); break;
                case '\r': escaped.append("\\r"); break;
                case '\t': escaped.append("\\t"); break;
                default: escaped.append(c);
            }
        }
        return escaped.toString();
    }

}
