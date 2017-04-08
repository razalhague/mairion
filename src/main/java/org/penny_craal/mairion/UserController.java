package org.penny_craal.mairion;

import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

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

@Controller
@RequestMapping(value = "/user")
public class UserController {
	private static final Logger log = LogManager.getLogger(UserController.class);

	private final UserDao userDao;

	@Inject
	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newUserForm(@ModelAttribute("registration") RegistrationDTO registration) {
		log.info("displaying user registration form");
		return "newUserForm";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newUser(@Validated @ModelAttribute("registration") RegistrationDTO registration, BindingResult br) {
		log.info("registering a new user...");
		if (userDao.getUser(registration.getEmail()).isPresent()) {
			log.debug("e-mail address already exists: " + registration.getEmail());
			br.rejectValue("email", "newUser.emailExists");
		}
		if (br.hasErrors()) {
			log.debug("errors with registration: " + br.toString());
			return "newUserForm";
		} else {
			String hashedPassword = new BCryptPasswordEncoder().encode(registration.getPassword());
			User user = new User(registration.getEmail(), registration.getName(), hashedPassword);
			userDao.persist(user);
			log.info("user successfully registered");
			return "redirect:/user/" + user.getId();
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getUser(@PathVariable int id) {
		Optional<User> optionalUser = userDao.getUser(id);
		return optionalUser
				.map(user -> new ModelAndView("userView", "user", user))
				.orElse(new ModelAndView("notFound", "userId", id));
	}
}
