package com.ticketingsystem.ticketingsystem.SOT.railways.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.Card;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.Email;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.Footfall;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.HourlyFootfall;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.Revenue;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.Station;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.Ticket;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.TrainSearchResult;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.User;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.StationRepository;
import com.ticketingsystem.ticketingsystem.SOT.railways.service.TicketService;
import com.ticketingsystem.ticketingsystem.SOT.railways.service.UserService;

@Controller
public class TicketController {

	private final TicketService ticketService;
	private final UserService userService;
	private final StationRepository stationRepository;

	public TicketController(TicketService ticketService, UserService userService, StationRepository stationRepository) {
		super();
		this.ticketService = ticketService;
		this.userService = userService;
		this.stationRepository = stationRepository;
	}

	@Autowired
	private JavaMailSender mailSenderr;

	@GetMapping("/selectTrain")
	public ModelAndView saveTicketDetails(@RequestParam String departureStation,
			@RequestParam String destinationStation, @RequestParam String departureDate,
			@RequestParam String destinationArrivalDate, @RequestParam String departureTime,
			@RequestParam String destinationArrivalTime, @RequestParam String trainName, @RequestParam String fare,
			@RequestParam String noOfVacantSeats) {

		// System.out.println(message);
		// System.out.println(train.getDepartureStation());
		ModelAndView mav = new ModelAndView("/bookTicket");
		Ticket ticket = new Ticket();
		ticket.setDepartureStationName(departureStation);
		ticket.setDestinationStationName(destinationStation);
		ticket.setDepartureDate(departureDate);
		ticket.setDestinationArrivalDate(destinationArrivalDate);
		ticket.setDepartureTime(departureTime);
		ticket.setDestinationArrivalTime(destinationArrivalTime);
		ticket.setTrainName(trainName);
		ticket.setNoOfVacantSeats(noOfVacantSeats);
		ticket.setFare(fare);
		mav.addObject("ticket", ticket);
		return mav;

	}

	@PostMapping("/bookTicket")
	public String bookTicket(Model model, @ModelAttribute Ticket ticket, HttpSession session)
			throws UnsupportedEncodingException, MessagingException {
		if(session.getAttribute("User")!=null)
		ticket.setUserId(((User) session.getAttribute("User")).getId());
		ticket.setFare(String.valueOf((Integer.parseInt(ticket.getFare()))*(ticket.getNoOfSeatsBooked())));
		Ticket ticket_saved = ticketService.saveTicketDetails(ticket);
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		String formatDateTime = dateTime.format(format);
		formatDateTime = formatDateTime + Long.toString(ticket_saved.getId());
		ticket_saved.setTicketNumber(formatDateTime);
		ticketService.saveTicketDetails(ticket_saved);
		// sendTicketEmail(userService.getUserById(ticket.getUserId()), ticket);
		// return "ticketSuccessful";

		if (ticket.getPaymentType().equals("Card")) {
			Card newCard = new Card();
			newCard.setTicketId(ticket_saved.getId());
			newCard.setFare(ticket_saved.getFare());
			model.addAttribute("card", newCard);
			return "card";
		}
		Email email=new Email();
		email.setTicketId(ticket_saved.getId());
        model.addAttribute("email",email );
		return "bookingSuccessful";
	}
	@PostMapping("/sendTicket")
	public String sendTicket(@ModelAttribute Email email) throws UnsupportedEncodingException, MessagingException {
		
			User guestUser=new User();
			guestUser.setFirstname("Guest");
			guestUser.setLastname("");
			guestUser.setEmail(email.getEmail());
			Ticket ticket=ticketService.getTicketById(email.getTicketId());
			sendTicketEmail(guestUser,ticket);
		
		return "ticketSent";
	}

	public static boolean luhnTest(String number) {
		int s1 = 0, s2 = 0;
		String reverse = new StringBuffer(number).reverse().toString();
		for (int i = 0; i < reverse.length(); i++) {
			int digit = Character.digit(reverse.charAt(i), 10);
			if (i % 2 == 0) {
				// this is for odd digits, they are 1-indexed in the algorithm
				s1 += digit;
			} else {// add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
				s2 += 2 * digit;
				if (digit >= 5) {
					s2 -= 9;
				}

			}
		}
		return (s1 + s2) % 10 == 0;
	}

	@PostMapping("/makePayment")
	public String makePayment(@ModelAttribute Card card,Model model) throws UnsupportedEncodingException, MessagingException {
		String cardNumber = card.getCardNumber();
		Ticket ticket=ticketService.getTicketById(card.getTicketId());
		if(luhnTest(cardNumber)==false) {
			if(ticket!=null)
			ticketService.deleteTicketById(card.getTicketId());
			String message="Invalid Card";
			model.addAttribute("message", message);
			model.addAttribute("card", card);
			return "card";
		}
		
		String fare=ticket.getFare();
		model.addAttribute("fare", fare);
		return "bookingSuccessful";
		
		
	}

	public void sendTicketEmail(User user, Ticket ticket) throws MessagingException, UnsupportedEncodingException {
		String subject = "Ticket Details";
		String senderName = "SOT Railways";
		String mailContent = "<p>Dear " + user.getFirstname() + " " + user.getLastname() + ",</p>";
		mailContent += "<p>Please check the ticket details below:</p>";

		mailContent += " Ticket Number</th>\r" + ticket.getTicketNumber() + "\n" + "	   Number Of Seats</th>\r"
				+ ticket.getNoOfSeatsBooked() + "\n" + "					<th>Departure Station</th>\r"
				+ ticket.getDepartureStationName() + "\n" + "					<th>Destination Station</th>\r"
				+ ticket.getDestinationStationName() + "\n" + "					<th>Departure Date</th>\r"
				+ ticket.getDepartureDate() + "\n" + "					<th>Destination Arrival Date</th>\r"
				+ ticket.getDestinationArrivalDate() + "\n" + "					<th>Departure Time</th>\r"
				+ ticket.getDepartureTime() + "\n" + "					<th>Destination Arrival Time</th>\r"
				+ ticket.getDestinationArrivalTime() + "\n" + "					<th>Train Name</th>\r"
				+ ticket.getTrainName() + "\n" + "					<th>Ticket Fare</th>\r" + ticket.getFare() + "\n"
				+ "					<th>payment Type</th>\r" + ticket.getPaymentType() + "\n" + "				\r\n";
		mailContent += "<p>Thank You<br>SOT Railways</p>";
		String toAddress = user.getEmail();
		String fromAddress = "railways.sot@gmail.com";

		MimeMessage message = mailSenderr.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		helper.setText(mailContent, true);

		mailSenderr.send(message);

	}

	@GetMapping("/history")
	public ModelAndView getTickets(HttpSession session) {
		ModelAndView mav = new ModelAndView("history");
		List<Ticket> tickets = ticketService.getTicketsByUserId(((User) session.getAttribute("User")).getId());
		List<Ticket> journeyCompletedTickets = new ArrayList<>();
		for (Ticket ticket : tickets) {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String formattedDateTime = dateTime.format(formatter);
			LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter);
			String departureTime = ticket.getDepartureTime();
			String departureDate = ticket.getDepartureDate();
			LocalDateTime parsedDepartureTime = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
			if (parsedDateTime.compareTo(parsedDepartureTime) > 0) {
				journeyCompletedTickets.add(ticket);
			}
		}
		mav.addObject("tickets", journeyCompletedTickets);
		return mav;
	}

	@GetMapping("/upcomingJourneys")
	public ModelAndView getUpcomingJourneyTickets(HttpSession session) {
		ModelAndView mav = new ModelAndView("upcomingJourneys");
		List<Ticket> tickets = ticketService.getTicketsByUserId(((User) session.getAttribute("User")).getId());
		List<Ticket> upcomingJourneyTickets = new ArrayList<>();
		for (Ticket ticket : tickets) {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String formattedDateTime = dateTime.format(formatter);
			LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter);
			String departureTime = ticket.getDepartureTime();
			String departureDate = ticket.getDepartureDate();
			LocalDateTime parsedDepartureTime = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
			if (parsedDateTime.compareTo(parsedDepartureTime) < 0) {
				upcomingJourneyTickets.add(ticket);
			}
		}
		mav.addObject("tickets", upcomingJourneyTickets);
		return mav;
	}

	public boolean validCancellation(Ticket ticket) {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedDateTime = dateTime.format(formatter);
		LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter);
		String departureTime = ticket.getDepartureTime();
		String departureDate = ticket.getDepartureDate();
		LocalDateTime parsedDepartureTime = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
		if (parsedDateTime.compareTo(parsedDepartureTime) > 0) {
			return false;
		}
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowDate = dateTime.format(formatDate);
		if (nowDate.equals(departureDate)) {
			DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
			String nowTime = dateTime.format(formatTime);
			String[] Arr1 = nowTime.split(":");
			int nowHours = Integer.parseInt(Arr1[0]);
			int nowMinutes = Integer.parseInt(Arr1[1]);

			String[] Arr2 = departureTime.split(":");
			int departureHours = Integer.parseInt(Arr2[0]);
			int departureMinutes = Integer.parseInt(Arr2[1]);
			int diff = (departureHours * 60 + departureMinutes) - (nowHours * 60 + nowMinutes);
			if (diff < 60)
				return false;
		}
		return true;
	}

	@GetMapping("/confirmCancellation")
	public ModelAndView confirmCancelltion(@RequestParam Long ticketId) {
		Ticket ticket = ticketService.getTicketById(ticketId);
		ModelAndView mav = new ModelAndView("confirmCancellation");
		mav.addObject("ticket", ticket);
		return mav;
	}

	@GetMapping("/cancelTicket")
	public ModelAndView cancelTicket(@RequestParam Long ticketId, HttpSession session) {
		Ticket ticket = ticketService.getTicketById(ticketId);
		ModelAndView mav = new ModelAndView("upcomingJourneys");
		if (validCancellation(ticket) == false) {
			mav.addObject("message", "You can cancel ticket only 60 minutes before the departure time");
			mav.addObject("tickets", ticketService.getTicketsByUserId(((User) session.getAttribute("User")).getId()));
			return mav;
		}
		mav.addObject("message", "ticket deleted successfully");
		ticketService.deleteTicketById(ticketId);
		mav.addObject("tickets", ticketService.getTicketsByUserId(((User) session.getAttribute("User")).getId()));
		return mav;
	}

	@GetMapping("/creditCard")
	public ModelAndView creditCard() {
		ModelAndView mav = new ModelAndView("creditCard");

		return mav;
	}

	@GetMapping("/revenue")
	public ModelAndView getRevenue() {
		ModelAndView mav = new ModelAndView("revenue");
		List<String> stationList = new ArrayList<String>();
		for (Station station : stationRepository.findAll()) {
			stationList.add(station.getName());
		}

		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String Date = now.format(formatter);
		String startDate = Date;
		String endDate = Date;
		String date = Date;
		List<Double> revenueList = new ArrayList<Double>();
		List<Double> footfallList = new ArrayList<Double>();
		List<Double> hourlyFootfallList = new ArrayList<Double>();

		for (String station : stationList) {

			String str = ticketService.getRevenue(station, startDate, endDate);
			if (str != null)
				revenueList.add(Double.parseDouble(str));
			else
				revenueList.add(0.0);

		}
		for (String station : stationList) {
			String str = ticketService.getFootfall(station, startDate, endDate);
			if (str != null)
				footfallList.add(Double.parseDouble(str));
			else
				footfallList.add(0.0);
		}
		for (int k = 6; k < 24; k++) {
			int i = k, j = k + 1;
			String start, close;
			if (i < 10)
				start = "0" + (Integer.toString(i)) + ":00";
			else
				start = Integer.toString(i) + ":00";
			if (i < 10)
				close = "0" + (Integer.toString(j)) + ":00";
			else
				close = Integer.toString(j) + ":00";

			String str = ticketService.getHourlyFootfall(stationList.get(0), date, start, close);
			if (str != null)
				hourlyFootfallList.add(Double.parseDouble(str));
			else
				hourlyFootfallList.add(0.0);

		}

		mav.addObject("revenue", new Revenue(Date, Date, stationList, revenueList));
		return mav;
	}

	@GetMapping("/footfall")
	public ModelAndView getFootfall() {
		ModelAndView mav = new ModelAndView("footfall");
		List<String> stationList = new ArrayList<String>();
		for (Station station : stationRepository.findAll()) {
			stationList.add(station.getName());
		}

		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String Date = now.format(formatter);
		String startDate = Date;
		String endDate = Date;
		List<Double> footfallList = new ArrayList<Double>();

		for (String station : stationList) {
			String str = ticketService.getFootfall(station, startDate, endDate);
			if (str != null)
				footfallList.add(Double.parseDouble(str));
			else
				footfallList.add(0.0);
		}
		mav.addObject("footfall", new Footfall(Date, Date, stationList, footfallList));
		return mav;
	}

	@GetMapping("/hourlyFootfall")
	public ModelAndView gethourlyFootfall() {
		ModelAndView mav = new ModelAndView("hourlyFootfall");
		List<String> stationList = new ArrayList<String>();
		for (Station station : stationRepository.findAll()) {
			stationList.add(station.getName());
		}

		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String Date = now.format(formatter);
		String startDate = Date;
		String endDate = Date;
		String date = Date;
		List<Double> hourlyFootfallList = new ArrayList<Double>();

		for (int k = 6; k < 24; k++) {
			int i = k, j = k + 1;
			String start, close;
			if (i < 10)
				start = "0" + (Integer.toString(i)) + ":00";
			else
				start = Integer.toString(i) + ":00";
			if (j < 10)
				close = "0" + (Integer.toString(j)) + ":00";
			else
				close = Integer.toString(j) + ":00";

			String str = ticketService.getHourlyFootfall(stationList.get(0), date, start, close);
			if (str != null)
				hourlyFootfallList.add(Double.parseDouble(str));
			else
				hourlyFootfallList.add(0.0);

		}

		mav.addObject("hourlyFootfall", new HourlyFootfall(stationList.get(0), date, stationList, hourlyFootfallList));
		return mav;
	}

	@PostMapping("/revenue")
	public ModelAndView getRevenue(@ModelAttribute Revenue revenue) {
		ModelAndView mav = new ModelAndView("revenue");
		List<String> stationList = new ArrayList<String>();
		for (Station station : stationRepository.findAll()) {
			stationList.add(station.getName());
		}

		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String Date = now.format(formatter);
		String startDate = revenue.getStartDate();
		String endDate = revenue.getEndDate();

		List<Double> revenueList = new ArrayList<Double>();

		for (String station : stationList) {

			String str = ticketService.getRevenue(station, startDate, endDate);
			if (str != null)
				revenueList.add(Double.parseDouble(str));
			else
				revenueList.add(0.0);

		}
		mav.addObject("revenue", new Revenue(startDate, endDate, stationList, revenueList));
		return mav;

	}

	@PostMapping("/footfall")
	public ModelAndView getFootfall(@ModelAttribute Footfall footfall) {
		ModelAndView mav = new ModelAndView("footfall");
		List<String> stationList = new ArrayList<String>();
		for (Station station : stationRepository.findAll()) {
			stationList.add(station.getName());
		}

		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String Date = now.format(formatter);
		String startDate = footfall.getStartDate();
		String endDate = footfall.getEndDate();

		List<Double> footfallList = new ArrayList<Double>();
		List<Double> hourlyFootfallList = new ArrayList<Double>();

		for (String station : stationList) {
			String str = ticketService.getFootfall(station, startDate, endDate);
			if (str != null)
				footfallList.add(Double.parseDouble(str));
			else
				footfallList.add(0.0);
		}

		mav.addObject("footfall", new Footfall(startDate, endDate, stationList, footfallList));
		return mav;

	}

	@PostMapping("/hourlyFootfall")
	public ModelAndView gethourlyFootfall(@ModelAttribute HourlyFootfall hourlyFootfall) {
		ModelAndView mav = new ModelAndView("hourlyFootfall");
		List<String> stationList = new ArrayList<String>();
		for (Station station : stationRepository.findAll()) {
			stationList.add(station.getName());
		}

		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String Date = now.format(formatter);
		String startDate = Date;
		String endDate = Date;
		List<Double> hourlyFootfallList = new ArrayList<Double>();

		String date = hourlyFootfall.getDate();
		String stationName = hourlyFootfall.getStation();
		for (int k = 6; k < 24; k++) {
			int i = k, j = k + 1;
			String start, close;
			if (i < 10)
				start = "0" + (Integer.toString(i)) + ":00";
			else
				start = Integer.toString(i) + ":00";
			if (j < 10)
				close = "0" + (Integer.toString(j)) + ":00";
			else
				close = Integer.toString(j) + ":00";

			String str = ticketService.getHourlyFootfall(stationName, date, start, close);
			if (str != null)
				hourlyFootfallList.add(Double.parseDouble(str));
			else
				hourlyFootfallList.add(0.0);

		}

		mav.addObject("hourlyFootfall", new HourlyFootfall(stationName, date, stationList, hourlyFootfallList));
		return mav;
	}

}
