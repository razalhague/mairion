package org.penny_craal.mairion;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * <p>Allows the user to specify time spent with an ending point (in their own timezone), and the
 * amount of hours they spent.</p>
 */
public class DurationTimeSpentDTO extends AbstractTimeSpentDTO {
	@NotNull
	private LocalDateTime localEndPoint;
	// can't apply @DecimalMin to a Double due to rounding issues, so this field is a BigDecimal
	// @DecimalMin(value = "0", inclusive = false) would be preferable, but Spring does not
	// generate the proper error messages for that
	@NotNull
	@DecimalMin("0.0001")	// 0.0001 hours is about one third of a second
	private BigDecimal hours;
	@NotNull
	private ZoneId userZone;

	/**
	 * The number of milliseconds that exist in an hour.
	 */
	private static final BigDecimal MILLIS_IN_HOUR = BigDecimal.valueOf(60 * 60 * 1000);

	public DurationTimeSpentDTO() {
	}

	public DurationTimeSpentDTO(ZoneId userZone) {
		this.userZone = userZone;
	}

	public DurationTimeSpentDTO(LocalDateTime localEndPoint, BigDecimal hours, String message,
				ZoneId userZone) {
		this.localEndPoint = localEndPoint;
		this.hours = hours;
		this.userZone = userZone;
		setMessage(message);
	}

	/**
	 * {@inheritDoc}
	 *
	 * The value is calculated from the end point, duration, and the timezone.
	 */
	@Override
	public ZonedDateTime getZonedStartPoint() {
		if (localEndPoint != null && userZone != null && hours != null) {
			Duration duration = Duration.ofMillis(hours.multiply(MILLIS_IN_HOUR).longValue());
			return localEndPoint.minus(duration).atZone(userZone);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * The value is calculated from the end point, and the timezone.
	 */
	@Override
	public ZonedDateTime getZonedEndPoint() {
		if (localEndPoint != null && userZone != null) {
			return localEndPoint.atZone(userZone);
		} else {
			return null;
		}
	}

	/**
	 * Returns the point in time when the user stopped spending time on the task in local time.
	 */
	public LocalDateTime getLocalEndPoint() {
		return localEndPoint;
	}

	/**
	 * Returns the point in time when the user stopped spending time on the task in local time.
	 */
	public void setLocalEndPoint(LocalDateTime localEndPoint) {
		this.localEndPoint = localEndPoint;
	}

	/**
	 * Returns the amount of time the user spent on the task, in hours.
	 */
	public BigDecimal getHours() {
		return hours;
	}

	/**
	 * Sets the amount of time the user spent on the task, in hours.
	 */
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	/**
	 * Returns the user's timezone.
	 */
	public ZoneId getUserZone() {
		return userZone;
	}

	/**
	 * Sets the user's timezone.
	 * @param userZone the new value for the timezone
	 */
	public void setUserZone(ZoneId userZone) {
		this.userZone = userZone;
	}
}
