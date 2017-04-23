package org.penny_craal.mairion;

import java.time.ZonedDateTime;

import javax.validation.constraints.Size;

import org.penny_craal.mairion.model.TimeSpent;
import org.penny_craal.mairion.model.TimeRange;
import org.penny_craal.mairion.model.validation.TimeRangeStartsBeforeEnding;

/**
 * This abstract class exists to facilitate different ways for the user to input time spent.
 */
@TimeRangeStartsBeforeEnding
public abstract class AbstractTimeSpentDTO implements TimeRange {
	@Size(max = 256)
	private String message;

	/**
	 * Returns the point in time when the user started to spend time on the task, with timezone.
	 */
	public abstract ZonedDateTime getZonedStartPoint();

	/**
	 * Returns the point in time when the user stopped spending time on the task, with timezone.
	 */
	public abstract ZonedDateTime getZonedEndPoint();

	/**
	 * Returns the message the user attached to this time spent.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Attaches a message to this time spent.
	 * @param message the message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Copies values (start and end points, and the message) from this DTO to a TimeSpent object.
	 * @param timeSpent target object
	 */
	public void copyValuesTo(TimeSpent timeSpent) {
		timeSpent.setZonedStartPoint(getZonedStartPoint());
		timeSpent.setZonedEndPoint(getZonedEndPoint());
		timeSpent.setMessage(message);
	}
}
