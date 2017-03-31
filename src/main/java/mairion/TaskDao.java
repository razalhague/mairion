package mairion;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaskDao {
	 @PersistenceContext private EntityManager em;
	 
	 @Transactional
	 public void persist(Task task) {
		 em.persist(task);
	 }
	 
	 public List<Task> getAllTasks() {
		 TypedQuery<Task> task = em.createQuery("SELECT t FROM task t", Task.class);
		 return task.getResultList();
	 }
}
