package org.penny_craal.mairion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import org.penny_craal.mairion.model.TimeRange;
import org.penny_craal.mairion.model.validation.TimeRangeStartsBeforeEnding;

@TimeRangeStartsBeforeEnding
public class TimePeriodDTO implements TimeRange {
	@NotNull
	private LocalDateTime localStartPoint;

	@NotNull
	private LocalDateTime localEndPoint;

	@NotNull
	private ZoneId userZone;

	@Override
	public ZonedDateTime getZonedStartPoint() {
		if (userZone != null && localStartPoint != null) {
			return localStartPoint.atZone(userZone);
		} else {
			return null;
		}
	}

	@Override
	public ZonedDateTime getZonedEndPoint() {
		if (userZone != null && localEndPoint != null) {
			return localEndPoint.atZone(userZone);
		} else {
			return null;
		}
	}

	public LocalDateTime getLocalStartPoint() {
		return localStartPoint;
	}

	public void setLocalStartPoint(LocalDateTime localStartPoint) {
		this.localStartPoint = localStartPoint;
	}

	public LocalDateTime getLocalEndPoint() {
		return localEndPoint;
	}

	public void setLocalEndPoint(LocalDateTime localEndPoint) {
		this.localEndPoint = localEndPoint;
	}

	public ZoneId getUserZone() {
		return userZone;
	}

	public void setUserZone(ZoneId userZone) {
		this.userZone = userZone;
	}
}
