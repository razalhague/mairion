package org.penny_craal.mairion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Test {

	public static void main(String[] args) {
		System.out.print("TEsti");
		 EntityManagerFactory tehdas = Persistence.createEntityManagerFactory("org/penny_craal/mairion");
	        EntityManager manageri = tehdas.createEntityManager();

	        EntityTransaction transaktio = manageri.getTransaction();	        
	        transaktio.begin();
	        
	        Task t1 = new Task("Kalevi");
	        Task t2 = new Task("Paavo");
	        
	        manageri.persist(t1);
	        manageri.persist(t2);
	        
	        transaktio.commit();

	}

}
