package org.penny_craal.mairion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Task.selectAll", query = "SELECT t from Task t")
public class Task {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "task_pk", nullable = false)
	private Integer id;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;

	// Constructors
	public Task() {
		super();
	}

	public Task(String description) {
		this.description = description;
	}

	public Task(String description, User owner) {
		this.description = description;
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

	// Overrides
	@Override
	public String toString() {
		return id + " " + description;
	}

}
