package com.ticketingsystem.ticketingsystem.SOT.railways.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.Ticket;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.TicketRepository;

@Service
public class TicketService {

	private final TicketRepository ticketRepository;

	public TicketService(TicketRepository ticketRepository) {
		super();
		this.ticketRepository = ticketRepository;
	}
	
	public Ticket saveTicketDetails(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	public String getNumberOfSeatsBooked(String DepartureDate, String TrainName,String DepartureStationName,String DestinationStationName ) {
		
		return ticketRepository.getNumberOfSeatsBooked(DepartureDate,TrainName,DepartureStationName,DestinationStationName);
		
		//return ticketRepository.getNumberOfSeatsBooked(DepartureDate,TrainName,DepartureStationName,DestinationStationName);
	}
	
	public List<Ticket>getTicketsByUserId(Long userId){
		return ticketRepository.findTicketsByUserId(userId);
	}

	public void deleteTicketById(Long ticketId) {
		ticketRepository.deleteById(ticketId);
	}

	public Ticket getTicketById(Long ticketId) {
		return ticketRepository.getReferenceById(ticketId);
	}

	public String getRevenue(String departureStationName,String startDate,String endDate ) {
		return ticketRepository.getRevenue(departureStationName, startDate, endDate);
	}
	
	public String getFootfall(String departureStationName,String startDate,String endDate) {
		return ticketRepository.getFootfall(departureStationName, startDate, endDate);
	}
	
	public String getHourlyFootfall(String departureStationName, String Date,String start,String close) {
		return ticketRepository.getHourlyFootfall(departureStationName, Date,start,close);
	}
}
