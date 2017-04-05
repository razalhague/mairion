package org.penny_craal.mairion;

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
}
