package serializers;

import interfaces.JsonProperty;
import utils.Util;

import java.lang.reflect.Field;
import java.util.Set;

public class ObjectSerializer extends DataSerializer {
    @Override
    public String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        StringBuilder jsonObject = new StringBuilder("{\n");
        boolean firstField = true;
        String indent = " ".repeat(indentLevel * 2);
        String innerIndent = " ".repeat((indentLevel + 1) * 2);

        Class<?> objClass = object.getClass();
        while (objClass != null && objClass != Object.class) {
            Field[] fields = JsonSerializer.fieldsCache.computeIfAbsent(objClass, Class::getDeclaredFields);
            for (Field field : fields) {
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    if (!firstField) {
                        jsonObject.append(",\n");
                    }
                    field.setAccessible(true);
                    try {
                        jsonObject.append(innerIndent)
                                .append(String.format(Util.QUOTE_FORMAT, field.getName()))
                                .append(Util.COLON)
                                .append(" ")
                                .append(JsonSerializer.serializeInternal(field.get(object), visitedObjects, indentLevel + 1));
                    } catch (IllegalAccessException e) {
                        jsonObject.append(innerIndent)
                                .append(String.format(Util.QUOTE_FORMAT, field.getName()))
                                .append(Util.COLON)
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