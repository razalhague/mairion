package org.penny_craal.mairion.utils;

import java.util.List;
import java.util.Optional;

/**
 * Utility methods, used in various places for various things.
 */
public class Util {
	/**
	 * Tries to get exactly one value from a list.
	 * @param list list of values
	 * @param <T>
	 * @return the sole value in the list, now in an Optional, or an empty Optional
	 */
	public static <T> Optional<T> getOptionalFromList(List<T> list) {
		if (list.isEmpty() || list.size() > 1) {
			return Optional.empty();
		} else { // there is exactly one result
			return Optional.of(list.get(0));
		}
	}
}
