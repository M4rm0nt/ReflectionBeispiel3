import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.StringEscapeUtils;

public class JsonSerializer {

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public String serialize(Object object) {
        if (object == null) {
            return "null";
        }

        if (object instanceof Map<?, ?>) {
            return mapToJson((Map<?, ?>) object);
        } else if (object instanceof List<?>) {
            return listToJson((List<?>) object);
        } else if (object instanceof Number || object instanceof Boolean) {
            return object.toString();
        } else if (object instanceof String) {
            return String.format("\"%s\"", escapeString((String) object));
        } else if (object instanceof Date) {
            return String.format("\"%s\"", DATE_FORMAT.format((Date) object));
        } else if (object instanceof LocalDate) {
            return String.format("\"%s\"", ((LocalDate) object).format(LOCAL_DATE_FORMATTER));
        } else if (object instanceof LocalDateTime) {
            return String.format("\"%s\"", ((LocalDateTime) object).format(LOCAL_DATE_TIME_FORMATTER));
        } else if (object instanceof Enum) {
            return String.format("\"%s\"", ((Enum<?>) object).name());
        } else {
            return objectToJson(object);
        }
    }

    private String mapToJson(Map<?, ?> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                json.append(",");
            }
            json.append(serialize(entry.getKey().toString())).append(":");
            json.append(serialize(entry.getValue()));
            first = false;
        }
        json.append("}");
        return json.toString();
    }

    private String listToJson(List<?> list) {
        StringBuilder jsonList = new StringBuilder("[");
        boolean first = true;
        for (Object item : list) {
            if (!first) {
                jsonList.append(",");
            }
            jsonList.append(serialize(item));
            first = false;
        }
        jsonList.append("]");
        return jsonList.toString();
    }

    private String objectToJson(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> fieldMap = new TreeMap<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    fieldMap.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Fehler beim Zugriff auf das Feld " + field.getName(), e);
            }
        }
        return mapToJson(fieldMap);
    }

    private String escapeString(String string) {
        return StringEscapeUtils.escapeJson(string);
    }

    public static void main(String[] args) {
        Mensch person = new Mensch(
                32,
                "Celine",
                List.of("Lesen", "Schwimmen"),
                List.of("Mila", "Chris"),
                List.of("Max", "Moritz")
        );

        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);
        System.out.println(json);
    }
}

class Mensch {
    private int alter;
    private String name;
    private List<String> hobbies;
    private Map<String, List<String>> haustiere;

    public Mensch(int alter, String name, List<String> hobbies, List<String> katzen, List<String> hunde) {
        this.alter = alter;
        this.name = name;
        this.hobbies = hobbies;
        this.haustiere = new HashMap<>();
        this.haustiere.put("Katzen", katzen);
        this.haustiere.put("Hunde", hunde);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensch mensch = (Mensch) o;
        return alter == mensch.alter && Objects.equals(name, mensch.name) && Objects.equals(hobbies, mensch.hobbies) && Objects.equals(haustiere, mensch.haustiere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alter, name, hobbies, haustiere);
    }

    @Override
    public String toString() {
        return "Mensch{" +
                "alter=" + alter +
                ", name='" + name + '\'' +
                ", hobbies=" + hobbies +
                ", haustiere=" + haustiere +
                '}';
    }
}
