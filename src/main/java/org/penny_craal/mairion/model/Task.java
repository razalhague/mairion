package org.penny_craal.mairion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

@Entity
@NamedQuery(name = "Task.selectAll", query = "SELECT t from Task t")
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

	// Constructors
	public Task() {
		super();
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

	// Overrides
	@Override
	public String toString() {
		return title + " (" + id + ")";
	}

}
