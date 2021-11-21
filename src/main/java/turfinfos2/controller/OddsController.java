package turfinfos2.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import turfinfos2.model.CurrentOdds;
import turfinfos2.repository.CurrentOddsRepository;

@Controller
public class OddsController {
	
	@Autowired
	CurrentOddsRepository currentOddsRepository;
	
	@GetMapping("current-odds")
	public String getRaceOdds(
			@RequestParam("jour") String jour,
			@RequestParam("reunion") String reunion,
			@RequestParam("course") Integer course,
			Model model) {
		
//		List<CurrentOdds> raceOdds = currentOddsRepository.findAllByJourAndByReunionAndByCourse(jour, reunion, course);
		List<CurrentOdds> raceOdds = currentOddsRepository.findAll()
				.stream()
				.sorted(Comparator.comparing(CurrentOdds::getTime))
				.collect(Collectors.toList());

		model.addAttribute("raceodds", raceOdds);
		
		
		
		return "current-odds";
	}

}
