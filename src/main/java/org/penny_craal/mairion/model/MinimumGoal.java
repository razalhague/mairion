package org.penny_craal.mairion.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

/**
 * A Goal that tracks the amount of time spent on a task in the specified TimePeriod. A minimum
 * amount of time must be spent on the task in order for the Goal to be achieved.
 */
@Entity
@DiscriminatorValue("minimum")
public class MinimumGoal extends AbstractGoal {
	// the configurable variable that this goal type provides. see getHours()
	@Min(0)
	private BigDecimal hours;

	// see getConfigurables()
	private static final List<Configurable> CONFIGURABLES = Collections.singletonList(
			new Configurable("hours", BigDecimal.valueOf(1.0))
	);

	// the number of milliseconds in an hour
	private static final double MILLIS_IN_HOUR = Duration.ofHours(1).toMillis();

	@Override
	public GoalStatus calculateStatus(ZonedDateTime time) {
		double totalTimeSpentInHours = calculateTimeSpent();
		if (hours.compareTo(BigDecimal.valueOf(totalTimeSpentInHours)) < 0) {
			return GoalStatus.achieved;
		} else if (time.isAfter(getTimePeriod().getZonedEndPoint())) {
			return GoalStatus.failed;
		} else {
			double workLeftInHours = hours.doubleValue() - totalTimeSpentInHours;
			double timeLeftInHours = Duration.between(time, getTimePeriod().getZonedEndPoint()).toMillis()
					/ MILLIS_IN_HOUR;
			if (workLeftInHours > timeLeftInHours) {
				return GoalStatus.failed;
			} else {
				return GoalStatus.uncertain;
			}
		}
	}

	@Override
	public Optional<Double> calculateProgress(ZonedDateTime time) {
		return Optional.of(calculateTimeSpent() / hours.doubleValue());
	}

	// calculates the amount of time spent on this task, in hours
	private double calculateTimeSpent() {
		// get every span of time spent on the target task
		return getTask().getWork().stream()
				// calculate how much of each span of spent time belongs in the target time period
				.map(timeSpent -> TimeRangeHelper.overlapDuration(getTimePeriod(), timeSpent))
				// sum up
				.reduce(Duration.ZERO, Duration::plus)
				.toMillis() / MILLIS_IN_HOUR;
	}

	@Override
	public List<Configurable> getConfigurables() {
		return CONFIGURABLES;
	}

	/**
	 * Returns the minimum number of hours that must be spent on the task for the goal to be achieved.
	 */
	public BigDecimal getHours() {
		return hours;
	}

	/**
	 * Sets the minimum number of hours that must be spent on the task for the goal to be achieved.
	 */
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}
}
