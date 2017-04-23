package org.penny_craal.mairion.model;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.penny_craal.mairion.utils.Util;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A DAO for TimePeriods. TimePeriods are shared across all users, so modification is not allowed.
 */
@Repository("timePeriodDao")
public class TimePeriodDao {
	@PersistenceContext
	private EntityManager em;

	/**
	 * Persists the given TimePeriod into the database.
	 */
	@Transactional
	public void persist(TimePeriod timePeriod) {
		em.persist(timePeriod);
	}

	/**
	 * Returns a TimePeriod with the given ID, if one exists in the database.
	 */
	public Optional<TimePeriod> getTimePeriodById(int id) {
		return Util.getOptionalFromList(em.createNamedQuery("TimePeriod.selectById", TimePeriod.class)
				.setParameter("id", id)
				.getResultList());
	}
}
