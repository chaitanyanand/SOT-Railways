package com.ticketingsystem.ticketingsystem.SOT.railways.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.ContactUs;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.ContactUsRepository;

@Controller
public class ContactUsController {
	
	private final ContactUsRepository contactUsRepo;
	
	
	
	public ContactUsController(ContactUsRepository contactUsRepo) {
		super();
		this.contactUsRepo = contactUsRepo;
	}

	@GetMapping( "/contactUs" )
	public ModelAndView getContactUs() {
		ModelAndView mav = new ModelAndView("contactUs");
		mav.addObject("contactUs", new ContactUs());
		return mav;
	}
	
	@PostMapping("/saveContactUs")
	public ModelAndView saveContactUs(@ModelAttribute ContactUs contactUs) {
		ModelAndView mav = new ModelAndView("contactUs");
		contactUsRepo.save(contactUs);
		mav.addObject("contactUs", new ContactUs());
		String message="Thank You for your query. We'll get back to you soon.";
		mav.addObject("message", message);
		return mav;
	}
	
}
