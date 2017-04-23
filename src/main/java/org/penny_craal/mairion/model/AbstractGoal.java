package org.penny_craal.mairion.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The base class for a variety of different kinds of goals. Defines the members common to all
 * Goals: id, task, time period, and user.
 */
@Entity
@NamedQuery(name = "AbstractGoal.selectByUserAndTimePeriod", query =
		"SELECT ag FROM AbstractGoal ag WHERE ag.planner = :user AND ag.timePeriod = :timePeriod")
@Inheritance
@DiscriminatorColumn(name = "goal_type")
@Table(name = "goal")
public abstract class AbstractGoal implements Goal {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "goal_pk", nullable = false)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

	@ManyToOne(optional = false)
	@JoinColumn(name = "timeperiod_id", nullable = false)
	private TimePeriod timePeriod;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User planner;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public User getPlanner() {
		return planner;
	}

	public void setPlanner(User planner) {
		this.planner = planner;
	}
}
