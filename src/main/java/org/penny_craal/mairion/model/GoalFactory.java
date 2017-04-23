package org.penny_craal.mairion.model;

/**
 * A programmatic way to construct different types of goals.
 */
public class GoalFactory {
	/**
	 * Constructs a Goal based on the given enum value
	 * @param type the enum value
	 * @return a newly constructed Goal
	 */
	public AbstractGoal getGoal(GoalType type) {
		switch (type) {
			case consider:
				return new ConsiderGoal();
			case minimum:
				return new MinimumGoal();
			default:
				throw new IllegalArgumentException("unrecognized goal type");
		}
	}

	/**
	 * Static convenience method for {@link #getGoal(GoalType)}.
	 */
	public static AbstractGoal createGoal(GoalType type) {
		return new GoalFactory().getGoal(type);
	}
}
