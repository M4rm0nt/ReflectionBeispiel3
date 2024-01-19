package strategys;

import interfaces.SerializerStrategy;
import serializers.DataSerializer;
import serializers.EnumSerializer;
import serializers.MapSerializer;

import java.util.Map;

public class EnumSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object instanceof Enum<?>;
    }

    @Override
    public DataSerializer getSerializer() {
        return new EnumSerializer();
    }
}
