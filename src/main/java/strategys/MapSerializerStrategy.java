package strategys;

import interfaces.SerializerStrategy;
import serializers.DataSerializer;
import serializers.MapSerializer;

import java.util.Map;

public class MapSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object instanceof Map;
    }

    @Override
    public DataSerializer getSerializer() {
        return new MapSerializer();
    }
}
