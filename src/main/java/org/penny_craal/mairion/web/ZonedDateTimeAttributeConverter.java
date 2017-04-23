package org.penny_craal.mairion.web;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Automatically converts ZonedDateTime values into Timestamps at UTC. ZonedDateTime values
 * retrieved from the database are not identical to the ones saved due to (most likely) having a
 * different time zone, but they do represent the same instant in time.
 */
@Converter(autoApply = true)
public class ZonedDateTimeAttributeConverter
		implements AttributeConverter<ZonedDateTime, Timestamp> {
	public static final ZoneOffset dbTimeZone = ZoneOffset.ofHours(0);

	@Override
	public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
		if (zonedDateTime == null) {
			return null;
		} else {
			return Timestamp.valueOf(zonedDateTime
					.withZoneSameInstant(dbTimeZone).toLocalDateTime());
		}
	}

	@Override
	public ZonedDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
		if (sqlTimestamp == null) {
			return null;
		} else {
			return sqlTimestamp.toLocalDateTime().atZone(dbTimeZone);
		}
	}
}