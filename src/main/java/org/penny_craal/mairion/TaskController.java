package org.penny_craal.mairion;

import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/task")
public class TaskController {
	private static final Logger log = LogManager.getLogger(TaskController.class);

	private final TaskDao taskDao;

	@Inject
	public TaskController(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

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
}
