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

    public String serialize(Object object) {
        return serializeInternal(object, new HashSet<>(), 0);
    }

    private String serializeInternal(Object object, Set<Object> visitedObjects, int indentLevel) {
        String indent = " ".repeat(indentLevel * 2);
        String innerIndent = " ".repeat((indentLevel + 1) * 2);

        if (object == null) {
            return "null";
        }
        if (visitedObjects.contains(object)) {
            return "\"[Circular Reference]\"";
        }
        visitedObjects.add(object);

        if (object instanceof Map) {
            return mapToJson((Map<?, ?>) object, visitedObjects, indentLevel);
        } else if (object instanceof List) {
            return listToJson((List<?>) object, visitedObjects, indentLevel);
        } else if (object instanceof Set) {
            return setToJson((Set<?>) object, visitedObjects, indentLevel);
        } else if (object instanceof Enum) {
            return String.format(QUOTE_FORMAT, ((Enum<?>) object).name());
        } else if (object instanceof String) {
            return String.format(QUOTE_FORMAT, escapeString((String) object));
        } else if (object instanceof LocalDate) {
            return String.format(QUOTE_FORMAT, ((LocalDate) object).format(LOCAL_DATE_FORMATTER));
        } else if (object instanceof LocalDateTime) {
            return String.format(QUOTE_FORMAT, ((LocalDateTime) object).format(LOCAL_DATE_TIME_FORMATTER));
        } else if (object instanceof Number || object instanceof Boolean) {
            return object.toString();
        } else {
            StringBuilder jsonObject = new StringBuilder("{\n");
            boolean firstField = true;
            for (Field field : object.getClass().getDeclaredFields()) {
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
            jsonObject.append("\n").append(indent).append("}");
            return jsonObject.toString();
        }
    }

    private String mapToJson(Map<?, ?> map, Set<Object> visitedObjects, int indentLevel) {
        StringBuilder jsonMap = new StringBuilder("{\n");
        String indent = " ".repeat(indentLevel * 2);
        String innerIndent = " ".repeat((indentLevel + 1) * 2);
        boolean first = true;
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

    private String listToJson(List<?> list, Set<Object> visitedObjects, int indentLevel) {
        StringBuilder jsonList = new StringBuilder("[\n");
        String indent = " ".repeat(indentLevel * 2);
        String innerIndent = " ".repeat((indentLevel + 1) * 2);
        boolean first = true;
        for (Object item : list) {
            if (!first) {
                jsonList.append(",\n");
            }
            jsonList.append(innerIndent)
                    .append(serializeInternal(item, visitedObjects, indentLevel + 1));
            first = false;
        }
        jsonList.append("\n").append(indent).append("]");
        return jsonList.toString();
    }

    private String setToJson(Set<?> set, Set<Object> visitedObjects, int indentLevel) {
        StringBuilder jsonSet = new StringBuilder("[\n");
        String indent = " ".repeat(indentLevel * 2);
        String innerIndent = " ".repeat((indentLevel + 1) * 2);
        boolean first = true;
        for (Object item : set) {
            if (!first) {
                jsonSet.append(",\n");
            }
            jsonSet.append(innerIndent)
                    .append(serializeInternal(item, visitedObjects, indentLevel + 1));
            first = false;
        }
        jsonSet.append("\n").append(indent).append("]");
        return jsonSet.toString();
    }

    private String escapeString(String string) {
        return string.replace("\"", "\\\"")
                .replace("\\", "\\\\")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
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

class Mensch {
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
