import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public class JsonSerializerFactory {
    DataSerializer getSerializer(Object object) {
        if (object instanceof Map) {
            return new MapSerializer();
        } else if (object instanceof Collection) {
            return new CollectionSerializer();
        } else if (object instanceof Enum) {
            return new EnumSerializer();
        } else if (object instanceof String) {
            return new StringSerializer();
        } else if (object instanceof LocalDate || object instanceof LocalDateTime) {
            return new DateSerializer();
        } else if (object instanceof Number || object instanceof Boolean) {
            return new PrimitiveSerializer();
        } else {
            return new ObjectSerializer();
        }
    }
}