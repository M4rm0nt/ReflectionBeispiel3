package strategys;

import interfaces.SerializerStrategy;
import serializers.DataSerializer;
import serializers.MapSerializer;
import serializers.StringSerializer;

import java.util.Map;

public class StringSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object instanceof String;
    }

    @Override
    public DataSerializer getSerializer() {
        return new StringSerializer();
    }
}
