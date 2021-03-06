package org.penny_craal.mairion;

import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.penny_craal.mairion.model.User;
import org.penny_craal.mairion.model.UserDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * A controller for actions related to users.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private static final Logger log = LogManager.getLogger(UserController.class);
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final UserDao userDao;

	@Inject
	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Display a form for creating a new user.
	 * @param registration the data object the view will display
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newUserForm(@ModelAttribute("registration") RegistrationDTO registration) {
		log.info("displaying user registration form");
		return "newUserForm";
	}

	/**
	 * Registers a new user.
	 * @param registration an object created by Spring from the form data
	 * @param br the result of binding the form data into the above ovject
	 * @param request the request object
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newUser(@Validated @ModelAttribute("registration") RegistrationDTO registration,
				BindingResult br, HttpServletRequest request) {
		log.info("registering a new user...");
		if (userDao.getUser(registration.getEmail()).isPresent()) {
			log.debug("e-mail address already exists: " + registration.getEmail());
			br.rejectValue("email", "newUser.emailExists");
		}
		if (br.hasErrors()) {
			log.debug("errors with registration: " + br.toString());
			return "newUserForm";
		} else {
			String hashedPassword = passwordEncoder.encode(registration.getPassword());
			User user = new User(registration.getEmail(), registration.getName(), hashedPassword);
			userDao.persist(user);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			log.info("user successfully registered");
			return "redirect:/task";
		}
	}

	/**
	 * Displays the login form.
	 * @param login the object the view will display
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(@ModelAttribute("login") LoginDTO login) {
		log.info("displaying log in form");
		return "loginForm";
	}

	/**
	 * Logs in the user.
	 * @param login an object created by Spring from the form data
	 * @param br the result of binding the form data into the above object
	 * @param request the request object
	 * @param session the session object
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated @ModelAttribute("login") LoginDTO login, BindingResult br,
				HttpServletRequest request, HttpSession session) {
		log.info("attempting to log in");
		Optional<User> optionalUser = userDao.getUser(login.getEmail());
		if (!optionalUser.isPresent()) {
			log.info("user does not exist");
			br.reject("login.failure");
		} else {
			User user = optionalUser.get();
			if (!passwordEncoder.matches(login.getPassword(), user.getHashedPassword())) {
				log.info("password does not match");
				br.reject("login.failure");
			} else {
				log.info("log in successful");
				session.invalidate();
				HttpSession newSession = request.getSession();
				newSession.setAttribute("user", user);
				return "redirect:/task";
			}
		}
		return "loginForm";
	}

	/**
	 * Display information on the user.
	 * @param id the ID of the user
	 * @return the appropriate view
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getUser(@PathVariable int id) {
		Optional<User> optionalUser = userDao.getUser(id);
		return optionalUser
				.map(user -> new ModelAndView("userView", "user", user))
				.orElse(new ModelAndView("notFound", "userId", id));
	}
}
