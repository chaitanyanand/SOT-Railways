package com.ticketingsystem.ticketingsystem.SOT.railways.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ticketingsystem.ticketingsystem.SOT.railways.exceptions.IncorrectPassword;
import com.ticketingsystem.ticketingsystem.SOT.railways.exceptions.InvalidEmail;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.User;
import com.ticketingsystem.ticketingsystem.SOT.railways.service.UserService;
import com.ticketingsystem.ticketingsystem.SOT.railways.utility.Utility;

import net.bytebuddy.utility.RandomString;

@Controller
public class LoginController {

	private UserService userService;

	public LoginController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping({ "/login" })
	public ModelAndView Home() {
		ModelAndView mav = new ModelAndView("Login");
		User newUser = new User();
		mav.addObject("user", newUser);
		return mav;
	}

	@GetMapping("/logout")
	public String LogoutUser(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		ModelAndView mav = new ModelAndView("index");
		return "redirect:/index";
	}

	@PostMapping("/saveUser")
	public ModelAndView saveUser(@ModelAttribute User user, HttpServletRequest request) throws Exception {

		user.setEnabled(false);

		ModelAndView mav = new ModelAndView("/login");
		User temp=userService.getUserByEmail(user.getEmail());
		if(temp!=null&&temp.isEnabled()==false) {
			userService.deleteById(temp.getId());
		}
		else if (temp!=null&&temp.isEnabled()==true) {
			String message = "User already exists with the given email address. Please Login below";
			mav.addObject("message", message);
			return mav;
		}

		String message = "Please click the link sent to your email address for verification";
		mav.addObject("message", message);
		userService.registerUser(user);
		String siteURL = Utility.getSiteURL(request);
		userService.sendVerificationEmail(user, siteURL); 
		
		

		return mav;
	}

	@PostMapping("/validateUser")
	public ModelAndView validateUser(@ModelAttribute User user, HttpSession session) throws Exception {
		User temp = userService.getUserByEmail(user.getEmail());
		ModelAndView mav;
		String message;
		// System.out.println(temp);
		if (temp != null&& temp.isEnabled()) {
			if (temp.getPassword().equals(user.getPassword())) {
				session.setAttribute("User", temp);
				// System.out.println(((User)session.getAttribute("user")).getEmail()+"
				// "+((User)session.getAttribute("user")).getRole());
				mav = new ModelAndView("home");
				// message="Logged In";
				// mav.addObject("message",message);
				return mav;
			} else {
				mav = new ModelAndView("login");
				message = "Incorrect password";
				mav.addObject("message", message);
			}
		} else {
			message = "Given email is not registered. please register!!!";
			mav = new ModelAndView("login");
			mav.addObject("message", message);
		}
		return mav;
	}

	@GetMapping("/verify")
	public ModelAndView verifyUser(@Param("code") String code) {
		ModelAndView mav=new ModelAndView("login");
		String message;
		if (userService.verify(code)) {
			message="Registration Success";
		
		} else {
			message="Registration Failed";
		}
		User user=new User();
		mav.addObject("user", user);
		mav.addObject("message",message);
		return mav;
	}
	

}