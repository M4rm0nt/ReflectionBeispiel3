import java.lang.reflect.Field;
import java.util.*;

class JsonSerializer {

    public String serialize(Object object) {
        if (object == null) {
            return "null";
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> fieldMap = new TreeMap<>();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                fieldMap.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return mapToJson(fieldMap);
    }

    private String mapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();

            if (value instanceof Number) {
                json.append(value);
            } else if (value instanceof List) {
                json.append(listToJson((List<?>) value));
            } else if (value == null) {
                json.append("null");
            } else {
                json.append("\"").append(value.toString().replace("\"", "\\\"")).append("\"");
            }

            json.append(",");
        }

        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }

    private String listToJson(List<?> list) {
        StringBuilder jsonList = new StringBuilder("[");
        for (Object item : list) {
            if (item instanceof List) {
                jsonList.append(listToJson((List<?>) item)).append(",");
            } else if (item instanceof String) {
                String escapedString = ((String) item).replace("\"", "\\\"");
                jsonList.append("\"").append(escapedString).append("\",");
            } else {
                jsonList.append(item).append(",");
            }
        }
        if (jsonList.charAt(jsonList.length() - 1) == ',') {
            jsonList.deleteCharAt(jsonList.length() - 1);
        }
        jsonList.append("]");
        return jsonList.toString();
    }


    // Beispielmain
    public static void main(String[] args) {
        // Beispielobjekt mit komplexen Datenstrukturen
        Mensch person = new Mensch(32, "Celine", List.of("Lesen", "Schwimmen"),
                List.of(List.of("Kochen", "Joggen"), List.of("Malen", "Yoga")));
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);
        System.out.println(json);
    }
}

class Mensch {
    private int alter;
    private String name;
    private List<String> hobbies;
    private List<List<String>> komplexeHobbies;

    public Mensch(int alter, String name, List<String> hobbies, List<List<String>> komplexeHobbies) {
        this.alter = alter;
        this.name = name;
        this.hobbies = hobbies;
        this.komplexeHobbies = komplexeHobbies;
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

    public List<List<String>> getKomplexeHobbies() {
        return komplexeHobbies;
    }

    public void setKomplexeHobbies(List<List<String>> komplexeHobbies) {
        this.komplexeHobbies = komplexeHobbies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensch mensch = (Mensch) o;
        return alter == mensch.alter && Objects.equals(name, mensch.name) && Objects.equals(hobbies, mensch.hobbies) && Objects.equals(komplexeHobbies, mensch.komplexeHobbies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alter, name, hobbies, komplexeHobbies);
    }

    @Override
    public String toString() {
        return "Mensch{" +
                "alter=" + alter +
                ", name='" + name + '\'' +
                ", hobbies=" + hobbies +
                ", komplexeHobbies=" + komplexeHobbies +
                '}';
    }

}
