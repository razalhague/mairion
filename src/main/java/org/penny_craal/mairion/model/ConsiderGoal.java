package org.penny_craal.mairion.model;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A very simple type of Goal. Does not provide a progress metric, and success is defined as simply
 * not having declined to do the target task.
 */
@Entity
@DiscriminatorValue("consider")
public class ConsiderGoal extends AbstractGoal {
	@Override
	public GoalStatus calculateStatus(ZonedDateTime time) {
		if (getTask().getStatus() != TaskStatus.willNotDo) {
			return GoalStatus.achieved;
		} else {
			return GoalStatus.failed;
		}
	}

	@Override
	public Optional<Double> calculateProgress(ZonedDateTime time) {
		return Optional.of(0.5);
	}

	@Override
	public List<Configurable> getConfigurables() {
		return Collections.emptyList();
	}
}
