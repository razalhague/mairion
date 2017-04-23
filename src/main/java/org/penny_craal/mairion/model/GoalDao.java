package org.penny_craal.mairion.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * A DAO for Goals. Goals must be persisted through the use of another DAO for now.
 */
@Repository("goalDao")
public class GoalDao {
	@PersistenceContext private EntityManager em;

	/**
	 * Returns a list of Goals for the given User and TimePeriod.
	 */
	public List<? extends Goal> getByUserAndTimePeriod(User user, TimePeriod timePeriod) {
		return em.createNamedQuery("AbstractGoal.selectByUserAndTimePeriod", AbstractGoal.class)
				.setParameter("user", user)
				.setParameter("timePeriod", timePeriod)
				.getResultList();
	}
}
