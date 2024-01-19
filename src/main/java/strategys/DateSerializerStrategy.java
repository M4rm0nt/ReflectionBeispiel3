package strategys;

import interfaces.SerializerStrategy;
import serializers.DataSerializer;
import serializers.DateSerializer;

import java.util.Map;

public class DateSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object instanceof Map;
    }

    @Override
    public DataSerializer getSerializer() {
        return new DateSerializer();
    }
}
