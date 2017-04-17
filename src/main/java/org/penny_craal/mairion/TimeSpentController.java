package org.penny_craal.mairion;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.penny_craal.mairion.model.Task;
import org.penny_craal.mairion.model.TaskDao;
import org.penny_craal.mairion.model.TimeSpent;
import org.penny_craal.mairion.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for actions and views related to time spent on tasks.
 */
@Controller
@RequestMapping(value = "/time-spent")
public class TimeSpentController {
	private static final Logger log = LogManager.getLogger(TimeSpentController.class);

	private final TaskDao taskDao;

	@Inject
	public TimeSpentController(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	/**
	 * Displays a list of the records of time spent on the task.
	 * @param id the ID of the task, given in the URL
	 * @return a ModelAndView object with the appropriate information
	 */
	@RequestMapping(value = "/on-task/{id}")
	public ModelAndView timeSpentOnTaskList(@PathVariable int id) {
		log.info("displaying time spent on task #" + id);
		return taskDao.getTaskById(id)
				.map(task -> new ModelAndView("timeSpentOnTaskList", "task", task))
				.orElse(new ModelAndView("notFound", "taskId", id));
	}

	/**
	 * Displays a form for adding a new record of time spent on the task.
	 * @param id the ID of the task, given in the URL
	 * @param model	the model object
	 * @param session the session object
	 * @param timeSpentDuration the model attribute for time spent
	 * @return a view string
	 */
	@RequestMapping(value = "/on-task/{id}/new", method = RequestMethod.GET)
	public String newTimeSpentForm(@PathVariable int id, Model model, HttpSession session,
			@ModelAttribute("durationTimeSpent") DurationTimeSpentDTO timeSpentDuration) {
		log.info("displaying form for new TimeSpent for task #" + id);
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		Optional<Task> optionalTask = taskDao.getTaskById(id);
		if (!optionalTask.isPresent()) {
			log.info("could not find task #" + id);
			model.addAttribute("taskId", id);
			return "notFound";
		} else {
			timeSpentDuration.setLocalEndPoint(LocalDateTime.now());
			timeSpentDuration.setUserZone(user.getTimezone());
			model.addAttribute("task", optionalTask.get());
			return "newTimeSpentOnTask";
		}
	}

	/**
	 * Creates a new record of time spent on the task with the provided information.
	 * @param id the ID of the task, given in the URL
	 * @param model	the model object
	 * @param session the session object
	 * @param timeSpentDuration the model attribute for time spent, filled in from the form data
	 * @param br the results of binding the user's input into timeSpentDuration
	 * @return a view string
	 */
	@RequestMapping(value = "/on-task/{id}/new", method = RequestMethod.POST)
	public String newTimeSpent(@PathVariable int id, Model model, HttpSession session,
				@Validated @ModelAttribute("durationTimeSpent")
				DurationTimeSpentDTO timeSpentDuration,	BindingResult br) {
		log.info("creating new TimeSpent");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		Optional<Task> optionalTask = taskDao.getTaskById(id);
		if (!optionalTask.isPresent()) {
			log.info("could not find task #" + id);
			model.addAttribute("taskId", id);
			return "notFound";
		}
		Task task = optionalTask.get();
		if (br.hasErrors()) {
			log.info("errors with TimeSpent model attribute");
			model.addAttribute("task", task);
			return "newTimeSpentOnTask";
		} else {
			TimeSpent timeSpent = new TimeSpent();
			timeSpentDuration.copyValuesTo(timeSpent);
			timeSpent.setTask(task);
			timeSpent.setUser(user);
			task.getWork().add(timeSpent);
			taskDao.merge(task);
			log.info("time spent added, I guess");
			return "redirect:/time-spent/on-task/" + task.getId();
		}
	}
}
