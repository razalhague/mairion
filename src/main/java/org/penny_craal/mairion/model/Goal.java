package org.penny_craal.mairion.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface for the various types of goals that can be set in the system. Describes the common
 * operations to all goals, and a method ({@link Goal#getConfigurables()}) for discovering any
 * configurable variables that may be set on the type of goal in question.
 */
public interface Goal {
	/**
	 * Calculates the status of the goal, given the date and time.
	 * @param time date and time
	 * @return status of the goal
	 *
	 * @see GoalStatus
	 */
	GoalStatus calculateStatus(ZonedDateTime time);

	/**
	 * Calculates how much work has been done towards the goal at the given time.
	 * @param time date and time
	 * @return a Double wrapped up in an Optional. An empty optional means that the Goal does not
	 * 		support calculating progress. A value of 0 means no work has been done, and 1 means
	 * 		there has been enough work to achieve the goal. The value is not guaranteed to be
	 * 		between 0 and 1.
	 */
	Optional<Double> calculateProgress(ZonedDateTime time);

	/**
	 * Returns an immutable list of configurable variables the goal provides. May be empty.
	 *
	 * @see Configurable
	 */
	List<Configurable> getConfigurables();

	/**
	 * Returns the Goal's ID.
	 */
	int getId();

	/**
	 * Sets the goal's ID.
	 * @param id the new ID
	 */
	void setId(int id);

	/**
	 * Returns the task associated with this goal.
	 */
	Task getTask();

	/**
	 * Sets the task associated with this goal
	 * @param task the new task
	 */
	void setTask(Task task);

	/**
	 * Returns the time period this goal is for.
	 */
	TimePeriod getTimePeriod();

	/**
	 * Sets the time period this goal is for.
	 * @param timePeriod the new time period
	 */
	void setTimePeriod(TimePeriod timePeriod);

	/**
	 * Returns the user who planned this Goal.
	 */
	User getPlanner();

	/**
	 * Sets the user who planned this Goal.
	 * @param planner the user
	 */
	void setPlanner(User planner);
}
