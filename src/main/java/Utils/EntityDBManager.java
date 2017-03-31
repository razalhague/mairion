package Utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EntityDBManager {
	private EntityManagerFactory factory;
	private EntityManager em;
	private EntityTransaction transaction;
	
	public EntityDBManager() {
		factory = Persistence.createEntityManagerFactory("mairion");
		em = factory.createEntityManager();
		transaction = em.getTransaction();	
	}
	
	// Commits list of any objects into database
	public void commitList(List<Object> list) {
		try {
			transaction.begin();
			
			transaction.commit();
		}
		catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}
	
	
	// Commits a single object into database
	public void commitSingle(Object o) {
		try {
			transaction.begin();
			em.persist(o);
			transaction.commit();		
		}
		catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}
}
