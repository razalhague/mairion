package org.penny_craal.mairion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "User.selectAll", query = "SELECT t from User t")
public class User {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_pk", nullable = false)
	private Integer id;
	
	@Column(name = "firstname", nullable = false)
	private String firstName;
	
	@Column(name = "surname")
	private String surName;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST)
	private List<Task> tasks = new ArrayList<>();
	
	// Constructors
	public User() {
		super();
	}
	
	public User(String firstName, String surName) {
		super();
		this.firstName = firstName;
		this.surName = surName;
	}
	
	public User(String firstName, String surName, List<Task> tasks) {
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.tasks = tasks;
	}
	
	public User(Integer id, String firstName, String surName, List<Task> tasks) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
		this.tasks = tasks;
	}
	
	public void AddTask(Task task) {
		task.setUser(this);
		tasks.add(task);
	}
	
	@Override
	public String toString() {
		return id + " " + firstName + " " + surName + " " + tasks;
	}
	
}
