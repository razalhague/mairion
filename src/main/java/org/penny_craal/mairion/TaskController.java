package org.penny_craal.mairion;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskController {
	private final TaskDao taskDao;

	@Inject
	public TaskController(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@RequestMapping(value="/")
	public ModelAndView tasklist(HttpServletRequest request) {
		String name = request.getParameter("name");
		if (name != null)
			taskDao.persist(new Task(name));

		return new ModelAndView("task.jsp", "taskDao", taskDao);
	}

}
