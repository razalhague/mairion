package org.penny_craal.mairion;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.penny_craal.mairion.model.TaskDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/task")
public class TaskController {
	private final TaskDao taskDao;

	@Inject
	public TaskController(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@RequestMapping(value="")
	public ModelAndView tasklist(HttpServletRequest request) {
		return new ModelAndView("taskList", "taskDao", taskDao);
	}

}
