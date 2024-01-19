package strategys;

import interfaces.SerializerStrategy;
import serializers.DataSerializer;
import serializers.ObjectSerializer;

import java.util.Map;

public class ObjectSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object != null;
    }

    @Override
    public DataSerializer getSerializer() {
        return new ObjectSerializer();
    }
}
