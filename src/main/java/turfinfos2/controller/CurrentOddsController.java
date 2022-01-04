package turfinfos2.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import turfinfos2.model.CurrentOdds;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.CurrentOddsRepository;

@Controller
public class CurrentOddsController {
	
	@Autowired
	CurrentOddsRepository currentOddsRepository;
	
	@GetMapping("current-odds")
	public String getRaceOdds(
			@RequestParam("jour") String jour,
			@RequestParam("reunion") String reunion,
			@RequestParam("course") Integer course,
			Model model) {
		
//		List<CurrentOdds> raceOdds = currentOddsRepository.findAll()

		List<CurrentOdds> raceOdds = currentOddsRepository.findAllByJourAndByReunionAndByCourse(jour, reunion, course)
				.stream()
				.sorted(Comparator.comparing(CurrentOdds::getTime))
				.collect(Collectors.toList());
		
		
			
		
		model.addAttribute("raceodds", raceOdds);
		
		
		
		return "current-odds";
	}
	
//	   private void navbarInfos(Model model) {
//		   
////		   List<TurfInfos> allInfos = turfInfosRepository.findAll();
//
//		   
//		   //DATES
//			 Set<String> dates = turfInfosRepository.findAllJours()
//		  			 .stream()
//		  			 .collect(Collectors.toSet());
////	  	 Set<String> dates = allInfos.stream()
////					.map(TurfInfos :: getJour)
////					.sorted()
////					.collect(Collectors.toSet());
//	  	 
//	  	List<String> datesSorted = dates.stream().collect(Collectors.toList());
//	  	Collections.sort(datesSorted, (o1, o2) -> o1.compareTo(o2));
//	        model.addAttribute("datesnav", datesSorted);		   
//	       //REUNIONS
//	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	       String jour = LocalDateTime.now().format(formatter);
//	       model.addAttribute("journav", jour);
//	       System.out.println(jour);
//	       
//	       
////	    	 Set<String> reunions = allInfos.stream()
////					.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
////	  				.map(TurfInfos :: getReunionstring)
//////	  				.sorted()
////	  				.collect(Collectors.toSet());
//////	    	 reunions.sort( Comparator.comparing( String::toString));
////	         model.addAttribute("reunionsofday", reunions);
//
//		   List<TurfInfos> allByJour = turfInfosRepository.findAllByJour(jour);
//
//	         Set<String> reunions = allByJour.stream()
////	 				.filter(ti-> ti.getJour().equals(jour))
//	        			.map(TurfInfos :: getReunionstring)
//	        			.collect(Collectors.toSet());
//	         
//	        			List<String> list = new ArrayList<String>(reunions);
//	        			Collections.sort(list);        			
//	        			reunions = new LinkedHashSet<>(list);
//	        	         model.addAttribute("reunionsofday", reunions);
//	   }

}
