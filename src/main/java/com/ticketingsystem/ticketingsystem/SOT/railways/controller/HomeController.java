package com.ticketingsystem.ticketingsystem.SOT.railways.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.User;
import com.ticketingsystem.ticketingsystem.SOT.railways.service.TicketService;
import com.ticketingsystem.ticketingsystem.SOT.railways.service.UserService;

@Controller
public class HomeController {

	private UserService userService;
	private TicketService ticketService;

	public HomeController(UserService userService,TicketService ticketService) {
		super();
		this.userService = userService;
		this.ticketService=ticketService;
	}

	@GetMapping({ "/home", "/" })
	public ModelAndView Home(HttpSession session) {
		// System.out.println(((User)session.getAttribute("User")).getEmail()+"
		// "+((User)session.getAttribute("User")).getRole());
		ModelAndView mav = new ModelAndView("Home");
		return mav;
	}

	@GetMapping({ "/index" })
	public ModelAndView Index(HttpSession session) {

		ModelAndView mav = new ModelAndView("index");
		User user = new User();
		mav.addObject(user);
		return mav;
	}

	@GetMapping({ "/searchUser" })
	public ModelAndView getUser() {
		ModelAndView mav = new ModelAndView("searchUser");
		mav.addObject("users", userService.getAllUsers());
		return mav;
	}
	@GetMapping({"/getUserDetails"})
	public ModelAndView getUserDetails(@RequestParam String userId) {
		User user=userService.getUserById(Long.parseLong(userId));
		ModelAndView mav=new ModelAndView("passengerDetail");
		mav.addObject("user", user);
		mav.addObject("tickets", ticketService.getTicketsByUserId(Long.parseLong(userId)));
		return mav;
		
	}

	@PostMapping("/searchUser")
	public ModelAndView getUser(@ModelAttribute User user) {
		ModelAndView mav = new ModelAndView("searchUser");
		user = userService.getUserByEmail(user.getEmail());
		mav.addObject("user", user);
		return mav;
	}

}
