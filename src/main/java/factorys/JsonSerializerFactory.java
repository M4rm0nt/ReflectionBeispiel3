package factorys;

import interfaces.SerializerStrategy;
import serializers.*;
import strategys.*;

import java.util.ArrayList;
import java.util.List;

public class JsonSerializerFactory {
    private List<SerializerStrategy> strategies;

    public JsonSerializerFactory() {
        strategies = new ArrayList<>();
        strategies.add(new StringSerializerStrategy());
        strategies.add(new DateSerializerStrategy());
        strategies.add(new EnumSerializerStrategy());
        strategies.add(new PrimitiveSerializerStrategy());
        strategies.add(new CollectionSerializerStrategy());
        strategies.add(new MapSerializerStrategy());
        strategies.add(new ObjectSerializerStrategy());
    }

    public DataSerializer getSerializer(Object object) {
        for (SerializerStrategy strategy : strategies) {
            if (strategy.isApplicable(object)) {
                return strategy.getSerializer();
            }
        }
        return new ObjectSerializer();
    }
}
