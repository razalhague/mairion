package mairion;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskController {
	@Autowired
	private TaskDao taskDao;
	
	@RequestMapping(value="/task")
	public ModelAndView tasklist(HttpServletRequest request) {
		String name = request.getParameter("name");
		if (name != null)	
			taskDao.persist(new Task(name));
		
		return new ModelAndView("task.jsp", "taskDao", taskDao);
	}

}
