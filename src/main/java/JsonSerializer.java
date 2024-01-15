import java.lang.reflect.Field;

class JsonSerializer {
    public String serialize(Object object) {

        Class<?> clazz = object.getClass();
        StringBuilder json = new StringBuilder("{");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                json.append("\"").append(field.getName()).append("\":\"").append(value).append("\",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        json.deleteCharAt(json.length() - 1);
        json.append("}");

        return json.toString();
    }

    public static void main(String[] args) {
        Mensch celine = new Mensch(32, "Celine", 3000000);
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(celine);
        System.out.println(json);
    }
}

class Mensch {
    private int alter;
    private String name;
    private int geld;

    public Mensch(int alter, String name, int geld) {
        this.alter = alter;
        this.name = name;
        this.geld = geld;
    }

    public int getAlter() { return alter; }
    public void setAlter(int alter) { this.alter = alter; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getGeld() { return geld; }
    public void setGeld(int geld) { this.geld = geld; }

    @Override
    public String toString() {
        return "Mensch{" +
                "alter=" + alter +
                ", name='" + name + '\'' +
                ", geld=" + geld +
                '}';
    }
}
