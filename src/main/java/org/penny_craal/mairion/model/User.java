package org.penny_craal.mairion.model;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

@Entity
@NamedQueries({
		@NamedQuery(name = "User.selectAll", query = "SELECT u from User u"),
		@NamedQuery(name = "User.selectById", query = "SELECT u from User u WHERE u.id = :id"),
		@NamedQuery(name = "User.selectByEmail", query = "SELECT u from User u WHERE u.email = :email")
})
@Table(name = "user_table") // 'user' is a reserved word in some DBs
public class User {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_pk", nullable = false)
	private Integer id;

	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "password", nullable = false)
	private String hashedPassword;

	@Column(name = "timezone", nullable = false)
	private ZoneId timezone = ZoneId.systemDefault();

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

	// Constructors
	public User() {
		super();
	}

	public User(String name) {
		super();
		this.name = name;
	}

	public User(String name, List<Task> tasks) {
		super();
		this.name = name;
		this.tasks = tasks;
	}

	public User(String email, String name, String hashedPassword) {
		this.email = email;
		this.name = name;
		this.hashedPassword = hashedPassword;
	}

	public User(Integer id, String name, List<Task> tasks) {
		super();
		this.id = id;
		this.name = name;
		this.tasks = tasks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void addTask(Task task) {
		task.setOwner(this);
		tasks.add(task);
	}

	public ZoneId getTimezone() {
		return timezone;
	}

	public void setTimezone(ZoneId timezone) {
		this.timezone = timezone;
	}

	@Override
	public String toString() {
		return id + " " + name;
	}
}
