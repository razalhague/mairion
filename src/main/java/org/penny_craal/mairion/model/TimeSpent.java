package org.penny_craal.mairion.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.penny_craal.mairion.model.validation.TimeRangeStartsBeforeEnding;

@Entity
@TimeRangeStartsBeforeEnding
public class TimeSpent implements TimeRange {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "goal_pk", nullable = false)
	private int id;

	/**
	 * The task this time was spent on.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

	/**
	 * The user who spent this time.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	/**
	 * The time the user started to spend time on this task.
	 *
	 * Never assume this is in the user's timezone.
	 */
	@Column(name = "start_point", nullable = false)
	private ZonedDateTime zonedStartPoint;

	/**
	 * The time the user stopped spending time on this task.
	 *
	 * Never assume this is in the user's timezone.
	 */
	@Column(name = "end_point", nullable = false)
	private ZonedDateTime zonedEndPoint;

	/**
	 * A message attached to the time spent.
	 */
	@Size(max = 256)
	@Column(name = "message")
	private String message;

	public TimeSpent() {
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public ZonedDateTime getZonedStartPoint() {
		return zonedStartPoint;
	}

	public void setZonedStartPoint(ZonedDateTime startTime) {
		this.zonedStartPoint = startTime;
	}

	@Override
	public ZonedDateTime getZonedEndPoint() {
		return zonedEndPoint;
	}

	public void setZonedEndPoint(ZonedDateTime endTime) {
		this.zonedEndPoint = endTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
