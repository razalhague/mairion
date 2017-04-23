package org.penny_craal.mairion.view;

import java.util.Locale;

import org.penny_craal.mairion.model.GoalType;
import org.penny_craal.mairion.model.TaskStatus;

/**
 * Helper functions for JSPs.
 */
public class Functions {
	/**
	 * Calculates a CSS class name from the goal progress value.
	 * @param progress goal progress
	 * @return "goalProgressXX" where XX is between 0 and 10, with a leading zero
	 */
	public static String progressToCssClassName(double progress) {
		// cap the progress value to between 0 and 1
		double cappedProgress = Math.min(1, Math.max(0, progress));
		return String.format(Locale.ROOT, "goalProgress%02d",
				(int) Math.floor(cappedProgress * 10));
	}

	/**
	 * A convenience function for accessing the static method TaskStatus.values() inside EL.
	 * @return an array of all the possible values a TaskStatus enum can have
	 */
	public static TaskStatus[] taskStatuses() {
		return TaskStatus.values();
	}

	/**
	 * A convenience function for accessing the static method GoalType.values inside EL.
	 * @return an array of all the possible values a GoalType enum can have
	 */
	public static GoalType[] goalTypes() {
		return GoalType.values();
	}
}
