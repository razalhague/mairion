package org.penny_craal.mairion.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.penny_craal.mairion.model.validation.TimeRangeStartsBeforeEnding;

/**
 * A time period, used for attaching Goals to. These are shared by all users for their plans, and
 * thus should not be changed once persisted into the database.
 */
@Entity
@NamedQuery(name = "TimePeriod.selectById", query = "SELECT tp FROM TimePeriod tp WHERE tp.id = :id")
@TimeRangeStartsBeforeEnding
public class TimePeriod implements TimeRange {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "timeperiod_pk", nullable = false)
	private int id;

	@Column(name = "start_point", nullable = false)
	private ZonedDateTime zonedStartPoint;

	@Column(name = "end_point", nullable = false)
	private ZonedDateTime zonedEndPoint;

	@OneToMany(mappedBy = "timePeriod", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AbstractGoal> goals = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public ZonedDateTime getZonedStartPoint() {
		return zonedStartPoint;
	}

	public void setZonedStartPoint(ZonedDateTime zonedStartPoint) {
		this.zonedStartPoint = zonedStartPoint;
	}

	@Override
	public ZonedDateTime getZonedEndPoint() {
		return zonedEndPoint;
	}

	public void setZonedEndPoint(ZonedDateTime zonedEndPoint) {
		this.zonedEndPoint = zonedEndPoint;
	}

	/**
	 * Returns all the goals set on this TimePeriod.
	 */
	public List<AbstractGoal> getGoals() {
		return goals;
	}

	/**
	 * Adds a new goal into the list of goals set to this TimePeriod.
	 */
	public void addGoal(AbstractGoal goal) {
		goal.setTimePeriod(this);
		goals.add(goal);
	}
}
