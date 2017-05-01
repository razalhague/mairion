package org.penny_craal.mairion.model;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.penny_craal.mairion.utils.Util;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A DAO for users.
 */
@Repository("userDao")
public class UserDao {
	@PersistenceContext private EntityManager em;

	/**
	 * Persists the given User into the database.
	 */
	@Transactional
	public void persist(User user) {
		em.persist(user);
	}

	/**
	 * Returns the User with the given ID if it exists in the database, otherwise an empty Optional.
	 */
	public Optional<User> getUser(int id) {
		return Util.getOptionalFromList(em.createNamedQuery("User.selectById", User.class)
				.setParameter("id", id)
				.getResultList());
	}

	/**
	 * Returns the User with the given e-mail if it exists in the database, otherwise an empty Optional.
	 * @param email
	 * @return
	 */
	public Optional<User> getUser(String email) {
		return Util.getOptionalFromList(em.createNamedQuery("User.selectByEmail", User.class)
				.setParameter("email", email)
				.getResultList());
	}

}
