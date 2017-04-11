package org.penny_craal.mairion.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.penny_craal.mairion.utils.Util;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("taskDao")
public class TaskDao {
	@PersistenceContext private EntityManager em;

	@Transactional
	public void persist(Task task) {
		em.persist(task);
	}

	@Transactional
	public void merge(Task task) {
		em.merge(task);
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

	public Optional<Task> getTaskById(int id) {
		return Util.getOptionalFromList(em.createNamedQuery("Task.selectById", Task.class)
				.setParameter("id", id)
				.getResultList());
	}
}
