package org.penny_craal.mairion.model;

/**
 * Represents the status of a task.
 */
public enum TaskStatus {
	/**
	 * A task that is not finished, but that the user is still planning to do.
	 */
	unfinished,

	/**
	 * A task that has been completed.
	 */
	done,

	/**
	 * A task that has not been completed, but the user will no longer work on.
	 */
	willNotDo,
	;

	/**
	 * Has this task has been handled, or is it something the user still wants to do.
	 */
	public boolean isHandled() {
		return this != unfinished;
	}
}
