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

import turfinfos2.model.ArchiveInfo;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ArchiveInfoRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.ResultService;

@Controller
public class ResultController {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	ResultService resultService;
	
	@Autowired
	ArchiveInfoRepository archiveInfosRepository;

	@GetMapping("/archives-places")
	public String placetable(Model model, 
			@RequestParam(name = "bankroll", required = false, defaultValue = "500d") Double bankrollAmount, 
			@RequestParam(name = "diviseur", required = false, defaultValue = "20") Integer divider
			) {
		
		List<TurfInfos> all = turfInfosRepository.findAll()
				.stream()
				.filter(ti->
				        
				        ti.getNumberOfInitialRunners() < 100
						&& ti.getHasBetTypes() == true 
						&& ti.getLiveOdd() != null && ti.getLiveOdd() != 0 && ti.getLiveOdd()<4
				        && ti.getRecence() != null && ti.getRecence()<60
				        && ti.getRaceSpecialty().equals("P")
				        && ( ti.getJour().contains("2021-06") || ti.getJour().contains("2021-07") )

				        )
//				.sorted(Comparator.comparing(TurfInfos::getJour).thenComparing(TurfInfos::getHour))
				.collect(Collectors.toList());
		
		Double total = 0d;
		
		List<ArchiveInfo> list = resultService.setAllPlaceFirstPronoList(all)
				.stream()
//				.filter(ai->
//				        
//				        ai.getNumberOfInitialRunners() > 1
//						&& ai.getHasBetTypes() == true 
//						&& ai.getLiveOdd() != null && ai.getLiveOdd() != 0 && ai.getLiveOdd()<4
//				        && ai.getRecence() != null && ai.getRecence()<60
//				        && ai.getRaceSpecialty().equals("P")
//				        && ai.getJour().contains("2021-09")
//
//				        )
				.sorted(Comparator.comparing(ArchiveInfo::getJour).thenComparing(ArchiveInfo::getHour))
				.collect(Collectors.toList());

		for(int i =0; i<list.size(); i++) {
			total += list.get(i).getLiveOddPlace();
		}
				
		navbarInfos(model);
		model.addAttribute("placelist", simulatedBankroll(list, model, bankrollAmount, divider));
		model.addAttribute("numberofbets", list.size());
		model.addAttribute("totalgains", total);
		model.addAttribute("benef", total - list.size());


		System.out.println("filterbig" + list.size());
		
		return "place-table";
	}
	
	///////////////////////////////////////////////////////////////
	
	public List<ArchiveInfo> simulatedBankroll(List<ArchiveInfo> list, Model model, Double bankAmount, Integer divider){
		
//		List<ArchiveInfo> finalList = new ArrayList<>();
		Double initialBankrollAmount = bankAmount;
		Double minimumBankrollAmount = bankAmount;
		Double precedentBankrollAmount = bankAmount;
		Double newBankrollAmount = bankAmount;
		Double maximalBankrollAmount = bankAmount;

		
		Double totalPlayed = 0d;
		Double totalWon = 0d;

		Double ante = initialBankrollAmount/divider;
		
		for(int i =0; i<list.size(); i++) {
			list.get(i).setAnte(ante);
			newBankrollAmount -= ante; 
			newBankrollAmount += ante * list.get(i).getLiveOddPlace(); 
//			if(list.get(i).getRanking().equals(1)) {
//				newBankrollAmount += ante * list.get(i).getLiveOdd(); 
//			}


			if(newBankrollAmount > maximalBankrollAmount) {
				maximalBankrollAmount = newBankrollAmount;
				ante = maximalBankrollAmount/divider;
			}
			if(newBankrollAmount < minimumBankrollAmount) {
				minimumBankrollAmount = newBankrollAmount;
			}
		}
		
		model.addAttribute("newBankrollAmount", newBankrollAmount);
		model.addAttribute("minimumBankrollAmount", minimumBankrollAmount);

		model.addAttribute("initialBankrollAmount", initialBankrollAmount);
		model.addAttribute("bankrollBenef", newBankrollAmount - initialBankrollAmount);
		
		model.addAttribute("cotecapital", ((newBankrollAmount * 100) /  initialBankrollAmount)/100);



		
		
		
		return list;
	}
	
	
	
	   private void navbarInfos(Model model) {
		   
		   List<TurfInfos> allInfos = turfInfosRepository.findAll();
		   
		   //DATES
	  	 Set<String> dates = allInfos.stream()
					.map(TurfInfos :: getJour)
					.sorted()
					.collect(Collectors.toSet());
	  	List<String> datesSorted = dates.stream().collect(Collectors.toList());
	  	Collections.sort(datesSorted, (o1, o2) -> o1.compareTo(o2));
	        model.addAttribute("datesnav", datesSorted);		   
	       //REUNIONS
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       String jour = LocalDateTime.now().format(formatter);
	       model.addAttribute("journav", jour);
	       
	       
//	    	 Set<String> reunions = allInfos.stream()
//					.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//	  				.map(TurfInfos :: getReunionstring)
////	  				.sorted()
//	  				.collect(Collectors.toSet());
////	    	 reunions.sort( Comparator.comparing( String::toString));
//	         model.addAttribute("reunionsofday", reunions);

	   
	         Set<String> reunions = allInfos.stream()
	 				.filter(ti->ti.getReunionstring() != null &&  ti.getJour().equals(jour) && ti.getR().length()<3)
	        			.map(TurfInfos :: getReunionstring)
	        			.collect(Collectors.toSet());
	        			List<String> list = new ArrayList<String>(reunions);
	        			Collections.sort(list);        			
	        			reunions = new LinkedHashSet<>(list);
	        	         model.addAttribute("reunionsofday", reunions);
	   }
}
