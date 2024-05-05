package com.ticketingsystem.ticketingsystem.SOT.railways.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.TicketFare;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.TicketFareRepository;

@Controller
public class TicketFareController {
	private final TicketFareRepository ticketFareRepo;

	
	public TicketFareController(TicketFareRepository ticketFareRepo) {
		super();
		this.ticketFareRepo = ticketFareRepo;
		if(this.ticketFareRepo.count()==0) {
			this.ticketFareRepo.save(new TicketFare(50,65,"08:00","09:00"));
		}
	}
	
	@GetMapping("/ticketFare")
	public ModelAndView getTicketFare() {
		ModelAndView mav=new ModelAndView("ticket-fare");
		List<TicketFare> ticketFareList=ticketFareRepo.findAll();
		TicketFare ticketFare=ticketFareList.get(0);
		mav.addObject("ticketFare", ticketFare);
		return mav;
	}
	@GetMapping("/showUpdateTicketFareForm")
	public ModelAndView updateTicketFare() {
		ModelAndView mav=new ModelAndView("updateTicketFare");
		List<TicketFare> ticketFareList=ticketFareRepo.findAll();
		TicketFare ticketFare=ticketFareList.get(0);
		mav.addObject("ticketFare", ticketFare);
		return mav;
	}
	@PostMapping("/saveTicketFare")
	public ModelAndView saveTicketFare(@ModelAttribute TicketFare ticketFare ) {
		ticketFareRepo.deleteAll();
		ticketFareRepo.save(ticketFare);
		ModelAndView mav=new ModelAndView("ticket-fare");
		mav.addObject("ticketFare",ticketFare );
		return mav;
	}
}
