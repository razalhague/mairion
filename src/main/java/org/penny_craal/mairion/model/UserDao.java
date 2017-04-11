package org.penny_craal.mairion.model;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.penny_craal.mairion.utils.Util;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
public class UserDao {
	@PersistenceContext private EntityManager em;

	@Transactional
	public void persist(User user) {
		em.persist(user);
	}

	public Optional<User> getUser(int id) {
		return Util.getOptionalFromList(em.createNamedQuery("User.selectById", User.class)
				.setParameter("id", id)
				.getResultList());
	}

	public Optional<User> getUser(String email) {
		return Util.getOptionalFromList(em.createNamedQuery("User.selectByEmail", User.class)
				.setParameter("email", email)
				.getResultList());
	}

}
