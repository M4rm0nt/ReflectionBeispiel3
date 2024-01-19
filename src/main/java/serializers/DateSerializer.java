package serializers;

import utils.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class DateSerializer extends DataSerializer {
    @Override
    public String serialize(Object object, Set<Object> visitedObjects, int indentLevel) {
        if (object instanceof LocalDate date) {
            return String.format(Util.QUOTE_FORMAT, date.format(Util.LOCAL_DATE_FORMATTER));
        } else {
            LocalDateTime dateTime = (LocalDateTime) object;
            return String.format(Util.QUOTE_FORMAT, dateTime.format(Util.LOCAL_DATE_TIME_FORMATTER));
        }
    }
}