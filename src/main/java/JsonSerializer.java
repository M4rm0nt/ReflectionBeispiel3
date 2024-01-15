import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class JsonSerializer {
    public String serialize(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> fieldMap = new HashMap<>();

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
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }

        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }

    public static void main(String[] args) {
        Mensch celine = new Mensch(32, "Celine");
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(celine);
        System.out.println(json);
    }
}

class Mensch {
    private int alter;
    private String name;

    public Mensch(int alter, String name) {
        this.alter = alter;
        this.name = name;
    }

    public int getAlter() { return alter; }
    public void setAlter(int alter) { this.alter = alter; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Mensch{" +
                "alter=" + alter +
                ", name='" + name + '}';
    }
}
