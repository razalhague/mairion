package org.penny_craal.mairion.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("taskDao")
public class TaskDao {
	@PersistenceContext private EntityManager em;

	@Transactional
	public void persist(Task task) {
		em.persist(task);
	}

	public List<Task> getAllTasks() {
		TypedQuery<Task> task = em.createQuery("SELECT t FROM Task t", Task.class);
		return task.getResultList();
	}

	public List<Task> getTasksByUser(User user) {
		return em.createNamedQuery("Task.selectByUserId", Task.class)
				.setParameter("id", user.getId())
				.getResultList();
	}
}
