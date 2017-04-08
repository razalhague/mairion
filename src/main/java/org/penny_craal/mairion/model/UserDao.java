package org.penny_craal.mairion.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		return getOptionalUser(em.createNamedQuery("User.selectById", User.class)
				.setParameter("id", id)
				.getResultList());
	}

	public Optional<User> getUser(String email) {
		return getOptionalUser(em.createNamedQuery("User.selectByEmail", User.class)
				.setParameter("email", email)
				.getResultList());
	}

	private Optional<User> getOptionalUser(List<User> users) {
		if (users.isEmpty() || users.size() > 1) {
			return Optional.empty();
		} else { // there is exactly one result
			return Optional.of(users.get(0));
		}
	}
}
