package strategys;

import interfaces.SerializerStrategy;
import serializers.CollectionSerializer;
import serializers.DataSerializer;

import java.util.Collection;
import java.util.Map;

public class CollectionSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object instanceof Collection<?>;
    }

    @Override
    public DataSerializer getSerializer() {
        return new CollectionSerializer();
    }
}
