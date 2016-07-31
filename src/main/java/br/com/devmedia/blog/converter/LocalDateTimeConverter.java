package br.com.devmedia.blog.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		
		return Timestamp.valueOf(localDateTime);
	}

	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		
		return timestamp.toLocalDateTime();
	}
}
