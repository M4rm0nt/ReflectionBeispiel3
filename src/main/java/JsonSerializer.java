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

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final String QUOTE_FORMAT = "\"%s\"";
    private static final String COLON = ":";
    private static final Map<Class<?>, Field[]> fieldsCache = new HashMap<>();

    public String serialize(Object object) {
        return serializeInternal(object, new HashSet<>(), 0);
    }

    private String serializeInternal(Object object, Set<Object> visitedObjects, int indentLevel) {
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

    private abstract class DataSerializer {
        abstract String serialize(Object object, Set<Object> visitedObjects, int indentLevel);
    }

    private class JsonSerializerFactory {
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

    private class MapSerializer extends DataSerializer {
        @Override
        String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
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
                        .append(serializeInternal(entry.getKey(), visitedObjects, indentLevel + 1))
                        .append(COLON)
                        .append(" ")
                        .append(serializeInternal(entry.getValue(), visitedObjects, indentLevel + 1));
                first = false;
            }
            jsonMap.append("\n").append(indent).append("}");
            return jsonMap.toString();
        }
    }

    private class CollectionSerializer extends DataSerializer {
        @Override
        String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
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
                        .append(serializeInternal(item, visitedObjects, indentLevel + 1));
                first = false;
            }
            jsonCollection.append("\n").append(indent).append("]");
            return jsonCollection.toString();
        }
    }

    private class EnumSerializer extends DataSerializer {
        @Override
        String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
            Enum<?> enumValue = (Enum<?>) object;
            return String.format(QUOTE_FORMAT, enumValue.name());
        }
    }

    private class StringSerializer extends DataSerializer {
        @Override
        String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
            String str = (String) object;
            return String.format(QUOTE_FORMAT, escapeString(str));
        }
    }

    private class DateSerializer extends DataSerializer {
        @Override
        String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
            if (object instanceof LocalDate) {
                LocalDate date = (LocalDate) object;
                return String.format(QUOTE_FORMAT, date.format(LOCAL_DATE_FORMATTER));
            } else {
                LocalDateTime dateTime = (LocalDateTime) object;
                return String.format(QUOTE_FORMAT, dateTime.format(LOCAL_DATE_TIME_FORMATTER));
            }
        }
    }

    private class PrimitiveSerializer extends DataSerializer {
        @Override
        String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
            return object.toString();
        }
    }

    private class ObjectSerializer extends DataSerializer {
        @Override
        String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
            StringBuilder jsonObject = new StringBuilder("{\n");
            boolean firstField = true;
            String indent = " ".repeat(indentLevel * 2);
            String innerIndent = " ".repeat((indentLevel + 1) * 2);

            Class<?> objClass = object.getClass();
            while (objClass != null && objClass != Object.class) {
                Field[] fields = fieldsCache.computeIfAbsent(objClass, Class::getDeclaredFields);
                for (Field field : fields) {
                    if (field.isAnnotationPresent(JsonProperty.class)) {
                        if (!firstField) {
                            jsonObject.append(",\n");
                        }
                        field.setAccessible(true);
                        try {
                            jsonObject.append(innerIndent)
                                    .append(String.format(QUOTE_FORMAT, field.getName()))
                                    .append(COLON)
                                    .append(" ")
                                    .append(serializeInternal(field.get(object), visitedObjects, indentLevel + 1));
                        } catch (IllegalAccessException e) {
                            jsonObject.append(innerIndent)
                                    .append(String.format(QUOTE_FORMAT, field.getName()))
                                    .append(COLON)
                                    .append(" \"Access Error\"");
                        }
                        firstField = false;
                    }
                }
                objClass = objClass.getSuperclass();
            }

            jsonObject.append("\n").append(indent).append("}");
            return jsonObject.toString();
        }
    }

    private String escapeString(String string) {
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

    public static void main(String[] args) {
        Mensch person = new Mensch(
                32,
                "Celine",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );

        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);
        System.out.println(json);
    }
}

class Lebewesen {
    @JsonProperty
    protected String art;

    public Lebewesen(String art) {
        this.art = art;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }
}

class Mensch extends Lebewesen {
    @JsonProperty
    private int alter;
    @JsonProperty
    private String name;
    @JsonProperty
    private LocalDateTime geburtstag;
    @JsonProperty
    private List<String> hobbies;
    @JsonProperty
    private Map<String, List<String>> haustiere;
    @JsonProperty
    private Geschlecht geschlecht;

    public Mensch(int alter, String name, LocalDateTime geburtstag, List<String> hobbies, Map<String, List<String>> haustiere, Geschlecht geschlecht) {
        super("Homo sapiens");
        this.alter = alter;
        this.name = name;
        this.geburtstag = geburtstag;
        this.hobbies = hobbies;
        this.haustiere = haustiere;
        this.geschlecht = geschlecht;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(LocalDateTime geburtstag) {
        this.geburtstag = geburtstag;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, List<String>> getHaustiere() {
        return haustiere;
    }

    public void setHaustiere(Map<String, List<String>> haustiere) {
        this.haustiere = haustiere;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensch mensch = (Mensch) o;
        return alter == mensch.alter && Objects.equals(name, mensch.name) && Objects.equals(geburtstag, mensch.geburtstag) && Objects.equals(hobbies, mensch.hobbies) && Objects.equals(haustiere, mensch.haustiere) && geschlecht == mensch.geschlecht;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alter, name, geburtstag, hobbies, haustiere, geschlecht);
    }

    @Override
    public String toString() {
        return "Mensch{" +
                "alter=" + alter +
                ", name='" + name + '\'' +
                ", geburtstag=" + geburtstag +
                ", hobbies=" + hobbies +
                ", haustiere=" + haustiere +
                ", geschlecht=" + geschlecht +
                '}';
    }

}

enum Geschlecht {
    MÄNNLICH, WEIBLICH, ANDERES
}
