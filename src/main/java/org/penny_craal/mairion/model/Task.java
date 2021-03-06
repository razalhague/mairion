package org.penny_craal.mairion.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * A Task, the most important object in the system.
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Task.selectAll", query = "SELECT t from Task t"),
		@NamedQuery(name = "Task.selectByUserId", query = "SELECT t FROM Task t where t.owner.id = :id"),
		@NamedQuery(name = "Task.selectById", query = "SELECT t FROM Task t where t.id = :id"),
})
public class Task {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "task_pk", nullable = false)
	private Integer id;

	/**
	 * The title of the task.
	 */
	@Size(max = 128)
	@Column(name = "title", nullable = false)
	private String title = "";

	/**
	 * A longer description of the task.
	 */
	@Size(max = 8192)
	@Column(name = "description", nullable = false)
	private String description = "";

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;

	@Column(name = "status", nullable = false)
	private TaskStatus status = TaskStatus.unfinished;

	@OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TimeSpent> work;

	@OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AbstractGoal> goals;

	// Constructors
	public Task() {
		super();
	}

	public Task(User owner) {
		this.owner = owner;
	}

	public Task(String title) {
		this.title = title;
	}

	public Task(String title, User owner) {
		this.title = title;
		this.owner = owner;
	}

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User user) {
		this.owner = user;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TimeSpent> getWork() {
		return work;
	}

	public void setWork(List<TimeSpent> work) {
		this.work = work;
	}

	public List<AbstractGoal> getGoals() {
		return goals;
	}

	public void addGoal(AbstractGoal goal) {
		goals.add(goal);
	}

	// Overrides
	@Override
	public String toString() {
		return title + " (" + id + ")";
	}

}
