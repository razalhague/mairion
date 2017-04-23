package org.penny_craal.mairion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.penny_craal.mairion.model.AbstractGoal;
import org.penny_craal.mairion.model.Configurable;
import org.penny_craal.mairion.model.GoalFactory;
import org.penny_craal.mairion.model.Task;
import org.penny_craal.mairion.model.TaskDao;
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
 * A controller for things related to goals.
 */
@Controller
@RequestMapping(value = "/goal")
public class GoalController {
	private static final Logger log = LogManager.getLogger(GoalController.class);

	private final TaskDao taskDao;
	private final TimePeriodDao timePeriodDao;

	@Inject
	public GoalController(TaskDao taskDao, TimePeriodDao timePeriodDao) {
		this.taskDao = taskDao;
		this.timePeriodDao = timePeriodDao;
	}

	/**
	 * The form for creating new Goals in a TimePeriod.
	 * @param timePeriodId ID of the target TimePeriod
	 * @param session the session object
	 * @param goalDto the DTO object for the Goal
	 * @param model the model object
	 * @return the name of the view
	 */
	@RequestMapping(value = "/in/{timePeriodId}/new", method = RequestMethod.GET)
	public String newGoalInTimePeriodForm(@PathVariable int timePeriodId, HttpSession session,
				@ModelAttribute("goal") GoalDTO goalDto, Model model) {
		log.info("displaying form for creating a new goal in time period #" + timePeriodId);
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		Optional<TimePeriod> optionalTimePeriod = timePeriodDao.getTimePeriodById(timePeriodId);
		if (!optionalTimePeriod.isPresent()) {
			log.info("time period not found");
			model.addAttribute("timePeriodId", timePeriodId);
			return "notFound";
		}
		model.addAttribute("timePeriod", optionalTimePeriod.get());
		model.addAttribute("tasks", taskDao.getTasksByUser(user));
		return "newGoalInTimePeriod";
	}

	/**
	 * Handles the actual creation of new TimePeriods.
	 * @param timePeriodId ID of the target TimePeriod
	 * @param session the session object
	 * @param goalDto the DTO for the Goal
	 * @param br the results of binding the form into the DTO
	 * @param model the model object
	 * @return the name of the view
	 */
	@RequestMapping(value = "/in/{timePeriodId}/new", method = RequestMethod.POST)
	public String newGoalInTimePeriod(@PathVariable int timePeriodId, HttpSession session,
				@Validated @ModelAttribute("goal") GoalDTO goalDto, BindingResult br, Model model) {
		log.info("creating new goal in time period #" + timePeriodId);
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		Optional<TimePeriod> optionalTimePeriod = timePeriodDao.getTimePeriodById(timePeriodId);
		if (!optionalTimePeriod.isPresent()) {
			log.info("time period not found");
			model.addAttribute("timePeriodId", timePeriodId);
			return "notFound";
		}
		TimePeriod timePeriod = optionalTimePeriod.get();
		Optional<Task> optionalTask = taskDao.getTaskById(goalDto.getTaskId());
		if (!optionalTask.isPresent()) {
			br.rejectValue("taskId", "goal.taskId.notFound");
			return "newGoalInTimePeriod";
		}
		Task task = optionalTask.get();
		if (br.hasErrors()) {
			log.info("errors with goal");
			return "newGoalInTimePeriod";
		}
		AbstractGoal goal = GoalFactory.createGoal(goalDto.getType());
		List<Configurable> requiredConfigurables = goal.getConfigurables();
		if (!requiredConfigurables.isEmpty()) {
			if (goalDto.getConfigurables().isEmpty()) {
				log.info("chosen goal type has configurables, but DTO does not");
				goalDto.setConfigurables(requiredConfigurables.stream()
						.collect(Collectors.toMap(Configurable::getName,
								Configurable::getDefaultValue)));
				model.addAttribute("timePeriod", timePeriod);
				model.addAttribute("configurables", requiredConfigurables);
				return "newGoalInTimePeriodWithConfigurables";
			} else {
				for (Configurable c : requiredConfigurables) {
					if (goalDto.getConfigurables().containsKey(c.getName())) {
						c.setConfigurableInGoal(goalDto.getConfigurables().get(c.getName()), goal);
					} else {
						br.rejectValue("configurables['" + c.getName() + "']",
								"configurable.mandatoryField");
						return "newGoalInTimePeriodWithConfigurables";
					}
				}
			}
		} else if (requiredConfigurables.isEmpty() && !goalDto.getConfigurables().isEmpty()) {
			// this is most likely because we somehow received an incorrect goal type, so there's
			// an error somewhere. still, just log it and continue.
			log.error("chosen goal type does not have configurables, but DTO does");
		}
		goal.setTask(task);
		task.addGoal(goal);
		goal.setPlanner(user);
		user.addGoal(goal);
		goal.setTimePeriod(timePeriod);
		taskDao.merge(task);
		log.info("goal created");
		return "redirect:/time-period/" + timePeriod.getId();
	}
}
