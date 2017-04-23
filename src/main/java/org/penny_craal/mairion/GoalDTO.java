package org.penny_craal.mairion;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.penny_craal.mairion.model.GoalType;

/**
 * A DTO for any type of Goal. Any {@link org.penny_craal.mairion.model.Configurable}s the Goal has
 * will be set into a Map, accessible through {@link #getConfigurables()}.
 */
public class GoalDTO {
	@NotNull
	private Integer taskId;

	@NotNull
	private GoalType type;

	private Map<String, Object> configurables = new HashMap<>();

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public GoalType getType() {
		return type;
	}

	public void setType(GoalType type) {
		this.type = type;
	}

	public Map<String, Object> getConfigurables() {
		return configurables;
	}

	public void setConfigurables(Map<String, Object> configurables) {
		this.configurables = configurables;
	}
}
