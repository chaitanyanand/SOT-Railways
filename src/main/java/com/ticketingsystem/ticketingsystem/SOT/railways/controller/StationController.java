package com.ticketingsystem.ticketingsystem.SOT.railways.controller;

import java.io.InputStream;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.SearchContent;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.Station;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.TicketFare;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.Train;
import com.ticketingsystem.ticketingsystem.SOT.railways.model.TrainSearchResult;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.StationRepository;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.TicketFareRepository;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.TrainRepository;
import com.ticketingsystem.ticketingsystem.SOT.railways.service.TicketService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Controller
public class StationController {

	@Autowired
	private final StationRepository stationRepo;
	private final TrainRepository trainRepo;
	private TicketService bookingService;
	private final Map<String, Map<String, String>> map;
	private final List<Station> stationList;
	private final TicketFareRepository ticketFareRepo;

	public StationController(StationRepository stationRepo, TrainRepository trainRepo, TicketService bookingService,TicketFareRepository ticketFareRepo) {
		super();
		this.stationRepo = stationRepo;
		this.trainRepo = trainRepo;
		this.bookingService = bookingService;
		this.ticketFareRepo= ticketFareRepo;
		map = new HashMap<>();
		stationList = stationRepo.findAll();
		Collections.sort(stationList);
		// for(Station station:stationList)
		// System.out.println(station.getName());
		List<Train> trainList = trainRepo.findAll();
		for (Station station : stationList) {
			map.put(station.getName(), new HashMap<>());
		}
		for (Station station : stationList) {
			for (Train train : trainList) {
				// System.out.println("Inside loop");
				int num = train.getNumber();
				String time = station.getFirstTrainDepartureTime();
				String reverseTime = station.getFirstReverseTrainDepartureTime();
				String[] arr = time.split(":");
				int hours = (Integer.parseInt(arr[0]) + num - 1);
				arr[0] = Integer.toString(hours);
				if(arr[0].length()==1) {
					arr[0]="0"+arr[0];
				}
				time = arr[0] + ":" + arr[1];
				arr = reverseTime.split(":");
				hours = (Integer.parseInt(arr[0]) + num - 1);
				arr[0] = Integer.toString(hours);
				if(arr[0].length()==1) {
					arr[0]="0"+arr[0];
				}
				reverseTime = arr[0] + ":" + arr[1];
				// System.out.println(time);
				map.get(station.getName()).put(train.getName(), time);
				map.get(station.getName()).put(train.getName() + "(Reverse)", reverseTime);
			}
		}
		// System.out.println(map.isEmpty());

	}

	public List<Station> getStations() {
		List<Station> list = stationRepo.findAll();
		return list;
	}

	@GetMapping("/searchTrains")
	public ModelAndView searchStations() {
		ModelAndView mav = new ModelAndView("/searchTrains");
		SearchContent searchContent = new SearchContent();
		searchContent.setAllStations(stationRepo.findAll());
		mav.addObject("searchContent", searchContent);
		return mav;
	}

	public int compareTimes(String time1, String time2) {
		String[] arr1 = time1.split(":");
		String[] arr2 = time2.split(":");
		int hours1 = Integer.parseInt(arr1[0]);
		int min1 = Integer.parseInt(arr1[1]);
		int hours2 = Integer.parseInt(arr2[0]);
		int min2 = Integer.parseInt(arr2[1]);
		if (hours1 > hours2)
			return 1;
		else if (hours1 < hours2) {
			return -1;
		} else {
			return min1 - min2;
		}

	}

	@PostMapping("/trainSearchResult")
	public ModelAndView trainSearchResult(@ModelAttribute SearchContent searchContent) throws ParseException {
		ModelAndView mav = new ModelAndView("/trainSearchResult");
		// System.out.println(map.isEmpty());

		int capacity = 500;
		List<TicketFare> ticketFareList=ticketFareRepo.findAll();
		TicketFare ticketFare=ticketFareList.get(0);
		int fareDuringNonPeakHour = ticketFare.getFareDuringNonPeakHour();
		int fareDuringPeakHour = ticketFare.getFareDuringPeakHour();
		String peakHourStart=ticketFare.getPeakHourStart();
		String peakHourEnd=ticketFare.getPeakHourEnd();
		List<TrainSearchResult> list = new ArrayList<>();
		String departureStation = searchContent.getDepartureStation();// e
		// System.out.println(departureStation);

		String destinationStation = searchContent.getDestinationStation();// a
		if(departureStation.equals(destinationStation)) {
			mav=new ModelAndView("/searchTrains");
			searchContent = new SearchContent();
			searchContent.setAllStations(stationRepo.findAll());
			mav.addObject("searchContent", searchContent);
			String message="Departure Station and Destination Station need to be different";
			mav.addObject("message", message);
			return mav;
		}
		String departureDate = searchContent.getDepartureDate();
		String destinationArrivalDate=departureDate; // will change this variable later in the code for train reaching the next day. 
		for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
			if (entry.getKey().equals(departureStation)) {
				// System.out.println("Yes");
				for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
					String trainName = entry2.getKey();
					String departureTime = entry2.getValue();
					destinationArrivalDate=departureDate;
					if(compareTimes(departureTime,"24:00")>0)
						continue;
					//System.out.println("departureTime: "+departureTime);
					String destinationArrivalTime = map.get(destinationStation).get(trainName);
					LocalDateTime currentTime = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					String formattedCurrentTime = currentTime.format(formatter);
					LocalDateTime parsedCurrentTime = LocalDateTime.parse(formattedCurrentTime, formatter);
					LocalDateTime parsedDepartureTime = LocalDateTime.parse(departureDate +" "+ departureTime, formatter);
					if (parsedCurrentTime.compareTo(parsedDepartureTime) > 0) {
						continue;
					}

					// System.out.println(departureTime+"$"+destinationArrivalTime+"$"+departureTime.compareTo(destinationArrivalTime));

					if (compareTimes(departureTime, destinationArrivalTime) > 0) {
						continue;
					}
					boolean flag = true;
					int index1 = 0, index2 = 0;
					for (; index1 < stationList.size(); index1++) {
						if (stationList.get(index1).getName().equals(departureStation)) {
							break;
						}
					}
					for (; index2 < stationList.size(); index2++) {
						if (stationList.get(index2).getName().equals(destinationStation)) {
							break;
						}
					}
					if (compareTimes(destinationArrivalTime, "24:00") > 0) {
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Calendar c = Calendar.getInstance();
						c.setTime(sdf.parse(departureDate));
						c.add(Calendar.DATE, 1);  // number of days to add
						destinationArrivalDate = sdf.format(c.getTime());  // dt is now the new date
						if (index1 < index2) {
							int i1 = index1, i2 = index2;
							for (; i1 < i2; i1++) {

								if (compareTimes(map.get(stationList.get(i1).getName()).get(trainName), "24:00") > 0) {
									flag = false;
									break;
								}
							}
						} else {
							int i1 = index1, i2 = index2;
							for (; i1 > i2; i1--) {
								if (compareTimes(map.get(stationList.get(i1).getName()).get(trainName), "24:00") > 0) {
									flag = false;
									break;
								}
							}

						}
					}

					if (flag == false)
						continue;
					int noOfSeatsBooked = 0;
					if (index1 < index2) {
						for (int s = 0; s < index2; s++) {
							for (int e = index1 + 1; e < stationList.size(); e++) {
								if (s < e) {
									String ans = bookingService.getNumberOfSeatsBooked(departureDate, trainName,
											stationList.get(s).getName(), stationList.get(e).getName());
									if (ans != null)
										noOfSeatsBooked += Integer.parseInt(ans);
								}
							}
						}
					} else {
						for (int s = stationList.size() - 1; s > index2; s--) {
							for (int e = index1 - 1; e >= 0; e--) {
								if (e < s) {

									String ans = bookingService.getNumberOfSeatsBooked(departureDate, trainName,
											stationList.get(s).getName(), stationList.get(e).getName());
									if (ans != null)
										noOfSeatsBooked += Integer.parseInt(ans);
								}
							}
						}

					}
					int noOfVacantSeats = capacity - noOfSeatsBooked;
					if (noOfVacantSeats == 0)
						continue;
					if (flag == true) {
						// System.out.println("S");
						String[] arr = destinationArrivalTime.split(":");
						int hours = (Integer.parseInt(arr[0])) % 24;
						arr[0] = Integer.toString(hours);
						if(arr[0].length()==1) {
							arr[0]="0"+arr[0];
						}
						destinationArrivalTime = arr[0] + ":" + arr[1];
						if (trainName.contains("(Reverse)"))
							trainName = trainName.substring(0, trainName.indexOf("(Reverse)"));
						
						int noOfStationsPassed=Math.abs(index2-index1);
						int fare=0;
						if(compareTimes(departureTime,peakHourStart)>=0&&compareTimes(departureTime,peakHourEnd)<=0) {
							fare=noOfStationsPassed*fareDuringPeakHour;
						}
						else {
							fare=noOfStationsPassed*fareDuringNonPeakHour;
						}
						list.add(new TrainSearchResult(trainName, departureDate,destinationArrivalDate, departureTime, destinationArrivalTime,
								departureStation, destinationStation, noOfVacantSeats,fare));
					}
				}

			}
		}
		Collections.sort(list);

		// System.out.println(list.size());
		// System.out.println(list.isEmpty());
		mav.addObject("trainSearchResultList", list);

		return mav;

	}

	@GetMapping("/showStations")
	public ModelAndView showStations() {
		ModelAndView mav = new ModelAndView("list-stations");
		List<Station> list = stationRepo.findAll();
		mav.addObject("stations", list);
		return mav;
	}

	@GetMapping("/addStationForm")
	public ModelAndView addStation() {
		ModelAndView mav = new ModelAndView("add-station-form");
		Station newStation = new Station();
		mav.addObject("station", newStation);
		return mav;
	}

	@GetMapping("/uploadStationFile")
	public ModelAndView uploadStationFile() {
		ModelAndView mav = new ModelAndView("upload-station-file");
		return mav;
	}

	@PostMapping("/saveStation")
	public String saveStation(@ModelAttribute Station station) {
		stationRepo.save(station);
		return "redirect:/showStations";
	}

	@PostMapping("/saveStationFile")
	public String uploadStationData(@RequestParam("StationFile") MultipartFile file) throws Exception {
		stationRepo.deleteAll();
		List<Station> stationList = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(setting);
		List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
		parseAllRecords.forEach(record -> {
			Station station = new Station();
			station.setName(record.getString("StationName"));
			station.setFirstTrainDepartureTime(record.getString("firstTrainDepartureTime"));
			//System.out.println(record.getString("firstTrainDepartureTime"));
			station.setFirstReverseTrainDepartureTime(record.getString("firstReverseTrainDepartureTime"));
			stationList.add(station);

		});
		stationRepo.saveAll(stationList);
		return "redirect:/showStations";
	}

	@GetMapping("/showUpdateStationForm")
	public ModelAndView showUpdateStation(@RequestParam Long stationId) {
		ModelAndView mav = new ModelAndView("add-station-form");
		Station station = stationRepo.findById(stationId).get();
		mav.addObject("station", station);
		return mav;
	}

	@GetMapping("/deleteStation")
	public String deleteStation(@RequestParam Long stationId) {
		stationRepo.deleteById(stationId);
		return "redirect:/showStations";
	}

}
