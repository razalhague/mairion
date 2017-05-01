package org.penny_craal.mairion;

import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.penny_craal.mairion.model.Task;
import org.penny_craal.mairion.model.TaskDao;
import org.penny_craal.mairion.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for actions related to tasks.
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {
	private static final Logger log = LogManager.getLogger(TaskController.class);

	private final TaskDao taskDao;

	@Inject
	public TaskController(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	/**
	 * Lists all the user's tasks.
	 * @param tasks	list of tasks that the view should display
	 * @param newTask a model object for the new task button
	 * @param session the session object
	 * @return the appropriate view
	 */
	@RequestMapping(value="")
	public String taskList(@ModelAttribute("tasks") ArrayList<Task> tasks,
				@ModelAttribute("newTask") Task newTask, HttpSession session) {
		log.info("displaying task list");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		tasks.clear();
		tasks.addAll(taskDao.getTasksByUser(user));
		return "taskList";
	}

	/**
	 * Displays a form for creating a new task.
	 * @param task the object for the view's form
	 * @param session the session object
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newTaskForm(@ModelAttribute("task") Task task, HttpSession session) {
		log.info("displaying new task creation form");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		return "newTask";
	}

	/**
	 * Creates a new Task from the provided data.
	 * @param task an object created by Spring from the form data
	 * @param br the result of binding the form data into the above object
	 * @param session the session object
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newTask(@Validated @ModelAttribute("task") Task task, BindingResult br, HttpSession session) {
		log.info("creating new task");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return "redirect:/user/login";
		}
		if (br.hasErrors()) {
			log.info("errors in the task");
			return "newTask";
		}
		task.setOwner(user);
		taskDao.persist(task);
		log.info("task created");
		return "redirect:/task/" + task.getId();
	}

	/**
	 * Displays a form for editing an existing task.
	 * @param id the id of the task to edit
	 * @param session the session object
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView editTaskForm(@PathVariable("id") int id, HttpSession session) {
		log.info("displaying task edit form for task #" + id);
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return new ModelAndView("redirect:/user/login");
		}
		Optional<Task> optionalTask = taskDao.getTaskById(id);
		return optionalTask
				.map(task -> new ModelAndView("editTask", "task", task))
				.orElse(new ModelAndView("notFound", "taskId", id));
	}

	/**
	 * Saves changes made to the Task.
	 * @param id the ID of the Task to be edited.
	 * @param modifiedTask the new data for the task, created by Spring from the form data
	 * @param br the result of binding the form data into the above object
	 * @param session the session object
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ModelAndView editTask(@PathVariable("id") int id,
				@Validated @ModelAttribute("task") Task modifiedTask, BindingResult br,
				HttpSession session) {
		log.info("saving modifications to task #" + id);
		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.info("user not logged in, sending to login page");
			return new ModelAndView("redirect:/user/login");
		}
		if (br.hasErrors()) {
			log.info("errors in the task");
			return new ModelAndView("editTask");
		}
		Optional<Task> originalTask = taskDao.getTaskById(id);
		if (originalTask.isPresent()) {
			Task task = originalTask.get();
			// copy the modified values into the original task
			task.setTitle(modifiedTask.getTitle());
			task.setDescription(modifiedTask.getDescription());
			task.setStatus(modifiedTask.getStatus());
			taskDao.merge(task);
			return new ModelAndView("redirect:/task/" + task.getId());
		} else {
			log.info("task #" + id + " not found in DB");
			return new ModelAndView("notFound", "taskId", id);
		}
	}
}
