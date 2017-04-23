package org.penny_craal.mairion.model;

import org.springframework.beans.PropertyAccessorFactory;

/**
 * Describes a configurable variable for an implementation of Goal. Immutable.
 */
public class Configurable {
	private final String name;
	private final Object defaultValue;

	/**
	 * Constructs a configurable definition.
	 * @param name See {@link Configurable#getName()}
	 * @param defaultValue See {@link Configurable#getDefaultValue()}
	 */
	public Configurable(String name, Object defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	/**
	 * Returns the name of the variable. The Goal must provide a public getter and setter for this.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the default value for this configurable variable.
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Returns the value of this configurable variable from the given goal.
	 * @param goal goal to retrieve the value from
	 */
	public Object getConfigurableFromGoal(AbstractGoal goal) {
		return PropertyAccessorFactory.forBeanPropertyAccess(goal).getPropertyValue(getName());
	}

	/**
	 * Sets the value of this configurable variable in the given goal.
	 * @param newValue the new value
	 * @param goal goal to retrieve the value from
	 */
	public void setConfigurableInGoal(Object newValue, AbstractGoal goal) {
		PropertyAccessorFactory.forBeanPropertyAccess(goal).setPropertyValue(getName(), newValue);
	}
}
