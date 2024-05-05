package com.ticketingsystem.ticketingsystem.SOT.railways.controller;

import java.io.InputStream;


import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ticketingsystem.ticketingsystem.SOT.railways.model.Train;
import com.ticketingsystem.ticketingsystem.SOT.railways.repository.TrainRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Controller
public class TrainController {

	
	public TrainController(TrainRepository trainRepo) {
		super();
		this.trainRepo = trainRepo;
	}

	private final TrainRepository trainRepo;
	
	@GetMapping("/showTrains")
	public ModelAndView showTrains() {
		ModelAndView mav = new ModelAndView("list-trains");
		List<Train> list = trainRepo.findAll();
		mav.addObject("trains", list);
		return mav;
	}

	@GetMapping("/addTrainForm")
	public ModelAndView addTrain() {
		ModelAndView mav = new ModelAndView("add-train-form");
		Train newTrain = new Train();
		mav.addObject("train", newTrain);
		return mav;
	}

	@GetMapping("/uploadTrainFile")
	public ModelAndView uploadTrainFile() {
		ModelAndView mav = new ModelAndView("upload-train-file");
		return mav;
	}

	@PostMapping("/saveTrain")
	public String saveTrain(@ModelAttribute Train train) {
		trainRepo.deleteAll();
		trainRepo.save(train);
		return "redirect:/showTrains";
	}

	@PostMapping("/saveTrainFile")
	public String uploadTrainData(@RequestParam("TrainFile") MultipartFile file) throws Exception {
		trainRepo.deleteAll();
		List<Train> trainList = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(setting);
		List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
		parseAllRecords.forEach(record -> {
			Train train = new Train();
			train.setName(record.getString("TrainName"));
			train.setNumber(record.getInt("TrainNumber"));
			trainList.add(train);

		});
		trainRepo.saveAll(trainList);
		return "redirect:/showTrains";
	}

	@GetMapping("/showUpdateTrainForm")
	public ModelAndView showUpdateTrain(@RequestParam Long trainId) {
		ModelAndView mav = new ModelAndView("add-train-form");
		Train train = trainRepo.findById(trainId).get();
		mav.addObject("train", train);
		return mav;
	}

	@GetMapping("/deleteTrain")
	public String deleteTrain(@RequestParam Long trainId) {
		trainRepo.deleteById(trainId);
		return "redirect:/showTrains";
	}

}