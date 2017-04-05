package org.penny_craal.mairion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceUnit;

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
	
	@ManyToOne(optional = true)
	//@Column(name = "user_fk", nullable = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	/*@OneToMany
	@JoinColumn(name = "todo")
	@Column(name = "user_fk", nullable = false)
	private Task childTask;*/

	// Constructors
	public Task() {
		super();
	}
	
	public Task(String description) {
		this.description = description;
	}
	
	public Task(String description, User user) {
		this.description = description;
		this.user = user;
	}
	
	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String description) {
		this.description = description;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// Overrides
	@Override
	public String toString() {
		return id + " " + description;
	}
	
}
