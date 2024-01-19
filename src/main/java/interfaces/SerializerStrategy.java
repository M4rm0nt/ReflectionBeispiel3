package interfaces;

import serializers.DataSerializer;

public interface SerializerStrategy {
    boolean isApplicable(Object object);
    DataSerializer getSerializer();
}
