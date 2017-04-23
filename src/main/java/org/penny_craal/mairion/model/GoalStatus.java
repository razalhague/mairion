package org.penny_craal.mairion.model;

/**
 * Describes the possible states a Goal can be in.
 */
public enum GoalStatus {
	/**
	 * The goal may end up either achieved or failed.
	 */
	uncertain,
	/**
	 * The goal has been achieved.
	 */
	achieved,
	/**
	 * It is impossible to achieve the goal.
	 */
	failed,
	;
}
