package strategys;

import interfaces.SerializerStrategy;
import serializers.DataSerializer;
import serializers.DateSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class DateSerializerStrategy implements SerializerStrategy {
    @Override
    public boolean isApplicable(Object object) {
        return object instanceof LocalDateTime;
    }

    @Override
    public DataSerializer getSerializer() {
        return new DateSerializer();
    }
}
