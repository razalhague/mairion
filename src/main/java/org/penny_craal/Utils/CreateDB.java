package org.penny_craal.Utils;

import org.penny_craal.mairion.Task;
import org.penny_craal.mairion.User;

public class CreateDB {

	public static void main(String[] args) {
		try {
			EntityDBManager em = new EntityDBManager();
			
			Task t1 = new Task("Tee oppari");
			Task t2 = new Task("Tee JavaEE harkkatyö");
			
			User u1 = new User("Mikko", "Mallikas");
			u1.AddTask(t1);
			u1.AddTask(t2);
			
			em.commitSingle(u1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
