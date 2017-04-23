package org.penny_craal.mairion;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.penny_craal.mairion.model.Goal;
import org.penny_craal.mairion.model.GoalDao;
import org.penny_craal.mairion.model.TimePeriod;
import org.penny_craal.mairion.model.TimePeriodDao;
import org.penny_craal.mairion.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for things related to TimePeriods.
 */
@Controller
@RequestMapping(value = "/time-period")
public class TimePeriodController {
	private static final Logger log = LogManager.getLogger(TimePeriodController.class);

	private final TimePeriodDao timePeriodDao;
	private final GoalDao goalDao;

	@Inject
	public TimePeriodController(TimePeriodDao timePeriodDao, GoalDao goalDao) {
		this.timePeriodDao = timePeriodDao;
		this.goalDao = goalDao;
	}

	/**
	 * Displays the TimePeriod with the given ID.
	 * @return the name of the view
	 */
	@RequestMapping(value = "/{id}")
	public String timePeriodView(@PathVariable int id, HttpSession session, Model model) {
		log.info("displaying time period #" + id);
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		Optional<TimePeriod> optionalTimePeriod = timePeriodDao.getTimePeriodById(id);
		if (!optionalTimePeriod.isPresent()) {
			model.addAttribute("timePeriodId", id);
			return "notFound";
		}
		TimePeriod tp = optionalTimePeriod.get();
		List<? extends Goal> goals = goalDao.getByUserAndTimePeriod(user, tp);
		model.addAttribute("timePeriod", tp);
		model.addAttribute("goals", goals);
		model.addAttribute("now", ZonedDateTime.now());
		return "timePeriod";
	}

	/**
	 * The form for creating new TimePeriods.
	 * @param tp the DTO for the new TimePeriod
	 * @param session the session object
	 * @return the name of the view
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newTimePeriodForm(@ModelAttribute("timePeriod") TimePeriodDTO tp,
				HttpSession session) {
		log.info("displaying the new time period form");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		tp.setUserZone(user.getTimezone());
		LocalDateTime userLocalTime = ZonedDateTime.now()
				.withZoneSameInstant(user.getTimezone()).toLocalDateTime();
		tp.setLocalStartPoint(userLocalTime);
		tp.setLocalEndPoint(userLocalTime);
		return "newTimePeriod";
	}

	/**
	 * Handles the creation of new TimePeriods
	 * @param timePeriodDto the DTO for the TimePeriod
	 * @param br the results of binding the form to the DTO
	 * @param session the session object
	 * @return the name of the view
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newTimePeriod(@Validated @ModelAttribute("timePeriod") TimePeriodDTO timePeriodDto,
				BindingResult br, HttpSession session) {
		log.info("creating new time period...");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		if (br.hasErrors()) {
			log.info("errors with time period DTO");
			return "newTimePeriod";
		}
		TimePeriod timePeriod = new TimePeriod();
		timePeriod.setZonedStartPoint(timePeriodDto.getZonedStartPoint());
		timePeriod.setZonedEndPoint(timePeriodDto.getZonedEndPoint());
		timePeriodDao.persist(timePeriod);
		return "redirect:/time-period/" + timePeriod.getId();
	}
}
