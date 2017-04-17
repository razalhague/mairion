package org.penny_craal.mairion.model.validation;

import java.time.ZonedDateTime;

/**
 * This interface is for any kind of class that represents a time range and wants to validate that
 * it starts before ending with {@link TimeRangeStartsBeforeEnding}.
 */
public interface TimeRange {
	/**
	 * Returns the start time of the time range.
	 */
	ZonedDateTime getZonedStartPoint();

	/**
	 * Returns the end time of the time range.
	 */
	ZonedDateTime getZonedEndPoint();
}
