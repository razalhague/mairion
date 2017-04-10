package org.penny_craal.mairion;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.penny_craal.mairion.model.Task;
import org.penny_craal.mairion.model.TaskDao;
import org.penny_craal.mairion.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/task")
public class TaskController {
	private final TaskDao taskDao;

	@Inject
	public TaskController(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@RequestMapping(value="")
	public String taskList(@ModelAttribute("tasks") ArrayList<Task> tasks,
				@ModelAttribute("user") User user, HttpSession session) {
		user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		tasks.clear();
		tasks.addAll(taskDao.getTasksByUser(user));
		return "taskList";
	}

}
