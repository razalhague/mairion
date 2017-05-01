package org.penny_craal.mairion.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.penny_craal.mairion.utils.Util;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A DAO for Tasks.
 */
@Repository("taskDao")
public class TaskDao {
	@PersistenceContext private EntityManager em;

	/**
	 * Persists the given task into the database.
	 */
	@Transactional
	public void persist(Task task) {
		em.persist(task);
	}

	/**
	 * Modifies an existing task in the database.
	 * @param task the updated task
	 */
	@Transactional
	public void merge(Task task) {
		em.merge(task);
	}

	/**
	 * Returns a list of all tasks in the system.
	 */
	public List<Task> getAllTasks() {
		TypedQuery<Task> task = em.createQuery("SELECT t FROM Task t", Task.class);
		return task.getResultList();
	}

	/**
	 * Returns all tasks owned by the given user.
	 */
	public List<Task> getTasksByUser(User user) {
		return em.createNamedQuery("Task.selectByUserId", Task.class)
				.setParameter("id", user.getId())
				.getResultList();
	}

	/**
	 * Returns the task with the given ID, if such exists in the system, an empty Optional otherwise.
	 */
	public Optional<Task> getTaskById(int id) {
		return Util.getOptionalFromList(em.createNamedQuery("Task.selectById", Task.class)
				.setParameter("id", id)
				.getResultList());
	}
}
