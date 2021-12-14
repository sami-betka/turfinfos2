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

import turfinfos2.model.ArchiveDeuxSur4;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ArchiveInfoRepository;
import turfinfos2.repository.ResultRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.ArchiveService;
import turfinfos2.service.BankrollService;

@Controller
public class ArchiveController {

		
		@Autowired
		TurfInfosRepository turfInfosRepository;
		
		@Autowired
		ArchiveService archiveService;
		
		@Autowired
		BankrollService bankrollService;
		
		@Autowired
		ArchiveInfoRepository archiveInfosRepository;
		
		@Autowired
		ResultRepository resultRepository;

		@GetMapping("/archives-deux-sur-quatre")
		public String deuxSurQuatreTable(Model model, 
				@RequestParam(name = "bankroll", required = false, defaultValue = "500d") Double bankrollAmount, 
				@RequestParam(name = "diviseur", required = false, defaultValue = "20") Integer divider
				) {
			
//			List<TurfInfos> all = turfInfosRepository.findAll()
//					.stream()
//					.filter(ti->
//					        
//					        ti.getNumberOfInitialRunners() != null && ti.getNumberOfInitialRunners() > 1
//							&& ti.getHasBetTypes() == true 
//							&& ti.getLiveOdd() != null && ti.getLiveOdd() != 0 && ti.getLiveOdd() < 2.5
//					        && ti.getRecence() != null && ti.getRecence() < 60
//						    && ti.getMinRapportProbable() != null && ti.getMinRapportProbable() != 0 && ti.getMinRapportProbable() > 1.3d
////					        && ti.getJour().contains("2021-11")
////					        && ti.getMaxRapportProbable() != null && ti.getMaxRapportProbable() != 0 && ti.getMaxRapportProbable() < 1.7d
////							&& ti.getIsFavori().equals(true)
////							&& ti.getRaceSpecialty().equals("P")
//					        
//					        
////						    && ti.getLiveOddPlace() != null && (ti.getLiveOddPlace() == 0 || ti.getLiveOddPlace() < 3)
////							&& ti.getIsFavori().equals(true)
////					        && ti.getRaceSpecialty().equals("P")
////					        && ti.getLiveOddPlace(). != 0
////					        && (  ti.getRaceSpecialty().equals("A") || ti.getRaceSpecialty().equals("M")  )
////					        && ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() >= 25
////							&& ti.getPourcPlaceChevalHippo() != null && ti.getPourcPlaceChevalHippo() >= 39.2
////					        && ti.getChronoPastille() == false
////					        && ti.getJockeyPastille() == false
////					        && ti.getChevalPastille() == false
////					        && ti.getCouplePastille() == false
	//
////					        && ti.getFormFigs() != null && ti.getFormFigs().length()>= 2 && (ti.getFormFigs().charAt(0)=='1' || ti.getFormFigs().charAt(0)=='2' || ti.getFormFigs().charAt(0)=='3') && (ti.getFormFigs().charAt(1)=='p' || ti.getFormFigs().charAt(1)=='a' || ti.getFormFigs().charAt(1)=='m')
	//
//					        )
//					.sorted(Comparator.comparing(TurfInfos::getJour).thenComparing(TurfInfos::getHour))
//					.collect(Collectors.toList());
			
			Double total = 0d;

			
			List<ArchiveDeuxSur4> list = ((List<ArchiveDeuxSur4>) archiveService.setAllPlaceFirstPronoList(turfInfosRepository.findAll(), resultRepository.findAll()).get("2sur4"))
					.stream()
					
//					.filter(ti -> 
//					        ti.getChronoPastille() == false
////					         ti.getJockeyPastille() == false
//					        && ti.getChevalPastille() == false
//					        && ti.getCouplePastille() == false
//	                        )
					
					.sorted(Comparator.comparing(ArchiveDeuxSur4::getJour).thenComparing(ArchiveDeuxSur4::getHour))
					.collect(Collectors.toList());

			for(int i =0; i<list.size(); i++) {
				if(list.get(i).getRapport() != null) {
				total += list.get(i).getRapport();
				}
			}
					
			navbarInfos(model);
			model.addAttribute("deuxsurquatrelist", bankrollService.simulatedBankroll2sur4(list, model, bankrollAmount, divider));
			model.addAttribute("numberofbets", list.size());
			model.addAttribute("totalgains", total);
			model.addAttribute("benef", total - list.size());
			model.addAttribute("multiplication", total / list.size());
			model.addAttribute("firstday", list.get(0).getJour());
			model.addAttribute("lastday", list.get(list.size() - 1).getJour());
			model.addAttribute("wonnumber", list.stream()
					.filter(ds4->ds4.getRapport() != null && ds4.getRapport() != 0)
					.collect(Collectors.toList())
					.size());
			
			model.addAttribute("lostnumber", list.stream()
					.filter(ds4->ds4.getRapport() != null && ds4.getRapport() == 0)
					.collect(Collectors.toList())
					.size());
			
			/////////////////BAR CHART/////////////////////////////////////


			System.out.println("filterbig" + list.size());
			
			return "deux-sur-quatre-table";
		}
		
		///////////////////////////////////////////////////////////////
		
		
		
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
		       
		       
//		    	 Set<String> reunions = allInfos.stream()
//						.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//		  				.map(TurfInfos :: getReunionstring)
////		  				.sorted()
//		  				.collect(Collectors.toSet());
////		    	 reunions.sort( Comparator.comparing( String::toString));
//		         model.addAttribute("reunionsofday", reunions);

		   
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