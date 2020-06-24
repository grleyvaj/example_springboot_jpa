package cu.example.infrastructure.shared;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.util.Date;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDateTime) {
        return localDateTime != null ? DateUtil.convert(localDateTime) : null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date != null ? DateUtil.convertToLocalDate(date) : null;
    }
}
