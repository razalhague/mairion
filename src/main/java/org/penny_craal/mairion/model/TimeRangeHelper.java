package org.penny_craal.mairion.model;

import java.time.Duration;

/**
 * Helper functions for dealing with {@link TimeRange}s.
 */
public class TimeRangeHelper {
	/**
	 * Calculates the how long
	 * @param a
	 * @param b
	 * @return
	 */
	public static Duration overlapDuration(TimeRange a, TimeRange b) {
		if (doesNotOverlap(a, b)) {
			return Duration.ZERO;
		} else if (contains(a, b)) {
			return Duration.between(b.getZonedStartPoint(), b.getZonedEndPoint());
		} else if (contains(b, a)) {
			return Duration.between(a.getZonedStartPoint(), a.getZonedEndPoint());
		} else if (a.getZonedStartPoint().compareTo(b.getZonedStartPoint()) <= 0) {
			return Duration.between(b.getZonedStartPoint(), a.getZonedEndPoint());
		} else if (b.getZonedStartPoint().compareTo(a.getZonedStartPoint()) <= 0) {
			return Duration.between(a.getZonedStartPoint(), b.getZonedEndPoint());
		} else {
			// we messed up somewhere
			throw new RuntimeException("failed to determine the overlapping of " + toString(a)
					+ " and " + toString(b));
		}
	}

	/**
	 * Returns whether the two TimeRanges overlap at all. Does not consider it an overlap if one
	 * starts exactly where the other ends.
	 */
	public static boolean overlaps(TimeRange a, TimeRange b) {
		return !doesNotOverlap(a, b);
	}

	/**
	 * Returns whether the two TimeRanges do not overlap at all. Does not consider it an overlap if
	 * one starts exactly where the other ends.
	 */
	public static boolean doesNotOverlap(TimeRange a, TimeRange b) {
		return a.getZonedEndPoint().compareTo(b.getZonedStartPoint()) <= 0
				|| a.getZonedStartPoint().compareTo(b.getZonedEndPoint()) >= 0;
	}

	/**
	 * Returns whether all of containee falls within container (true if the two are equal).
	 */
	public static boolean contains(TimeRange container, TimeRange containee) {
		return container.getZonedStartPoint().compareTo(containee.getZonedStartPoint()) <= 0
				&& container.getZonedEndPoint().compareTo(containee.getZonedEndPoint()) >= 0;
	}

	/**
	 * Returns whether the two TimeRanges describe the same span of time.
	 */
	public static boolean isEqual(TimeRange a, TimeRange b) {
		return a.getZonedStartPoint().isEqual(b.getZonedStartPoint())
				&& a.getZonedEndPoint().isEqual(b.getZonedEndPoint());
	}

	/**
	 * Returns a simple String representation of a TimeRange.
	 */
	public static String toString(TimeRange timeRange) {
		return "[" + timeRange.getZonedStartPoint() + " -- " + timeRange.getZonedEndPoint() + "]";
	}
}
