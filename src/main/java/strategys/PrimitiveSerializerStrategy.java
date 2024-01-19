package strategys;

import interfaces.SerializerStrategy;
import serializers.DataSerializer;
import serializers.PrimitiveSerializer;

import java.util.Map;

public class PrimitiveSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object instanceof Number;
    }

    @Override
    public DataSerializer getSerializer() {
        return new PrimitiveSerializer();
    }
}
