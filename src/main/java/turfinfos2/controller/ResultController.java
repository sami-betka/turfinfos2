package turfinfos2.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ArchiveInfoRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.BankrollService;
import turfinfos2.service.ResultService;

@Controller
public class ResultController {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	ResultService resultService;
	
	@Autowired
	BankrollService bankrollService;
	
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
				        
				        ti.getNumberOfInitialRunners() > 1
						&& ti.getHasBetTypes() == true 
						&& ti.getLiveOdd() != null && ti.getLiveOdd() != 0 && ti.getLiveOdd()<4
				        && ti.getRecence() != null && ti.getRecence()<60
//				        && ti.getRaceSpecialty().equals("P")
//				        && ti.getLiveOddPlace(). != 0

//				        && (  ti.getRaceSpecialty().equals("M") || ti.getRaceSpecialty().equals("M")  )
//				        && ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() >= 53.6
//						&& ti.getPourcPlaceChevalHippo() != null && ti.getPourcPlaceChevalHippo() >= 39.2

//				        && ti.getChronoPastille() == false
//				        && ti.getJockeyPastille() == false
//				        && ti.getChevalPastille() == false
//				        && ti.getCouplePastille() == false

//				        && ti.getFormFigs() != null && ti.getFormFigs().length()>= 2 && ti.getFormFigs().charAt(0)=='1' && ti.getFormFigs().charAt(1)=='p'
				        && !ti.getJour().contains("2021-03")

				        )
				.sorted(Comparator.comparing(TurfInfos::getJour).thenComparing(TurfInfos::getHour))
				.collect(Collectors.toList());
		
		Double total = 0d;
		
		List<TurfInfos> list = resultService.setAllPlaceFirstPronoList(all)
				.stream()
				
				.filter(ti -> ti.getChronoPastille() == false
				        && ti.getJockeyPastille() == false
				        && ti.getChevalPastille() == false
				        && ti.getCouplePastille() == false
                        )
				
				.sorted(Comparator.comparing(TurfInfos::getJour).thenComparing(TurfInfos::getHour))
				.collect(Collectors.toList());

		for(int i =0; i<list.size(); i++) {
			if(list.get(i).getLiveOddPlace() != null) {
			total += list.get(i).getLiveOddPlace();
			}
		}
				
		navbarInfos(model);
		model.addAttribute("placelist", bankrollService.simulatedBankroll(list, model, bankrollAmount, divider));
		model.addAttribute("numberofbets", list.size());
		model.addAttribute("totalgains", total);
		model.addAttribute("benef", total - list.size());
		
		/////////////////BAR CHART/////////////////////////////////////
		
		LinkedList<Double> wonMoyennes = new LinkedList<>();
		LinkedList<Double> lostMoyennes = new LinkedList<>();
		LinkedList<String> labels = new LinkedList<>();
		labels.add("VEH");
		labels.add("VJH");
		labels.add("VCH");
		labels.add("PCH");
		labels.add("PC");
		labels.add("VCPLE");
		labels.add("PCPLE");

		
		List<TurfInfos> won = all
				.stream()
				.filter(ti-> ti.getLiveOddPlace() != null && ti.getLiveOddPlace() > 0)
				.sorted(Comparator.comparing(TurfInfos::getJour).thenComparing(TurfInfos::getHour))
				.collect(Collectors.toList());
		
		Double vehTotalWon = 0d;
		Double vjhTotalWon = 0d;
		Double vchTotalWon = 0d;
		Double pchTotalWon = 0d;
		Double pcTotalWon = 0d;
		Double vcoupleTotalWon = 0d;
		Double pcoupleTotalWon = 0d;
		
		Double vehMoyenneWon = 0d;
		Double vjhMoyenneWon = 0d;
		Double vchMoyenneWon = 0d;
		Double pchMoyenneWon = 0d;
		Double pcMoyenneWon = 0d;
		Double vcoupleMoyenneWon = 0d;
		Double pcoupleMoyenneWon = 0d;

		for(TurfInfos info: won) {
			if(info.getPourcVictEntHippo() != null) {
			vehTotalWon += info.getPourcVictEntHippo();
			}
			if(info.getPourcVictJockHippo() != null) {
				vjhTotalWon += info.getPourcVictJockHippo();
				}
			if(info.getPourcVictChevalHippo() != null) {
				vchTotalWon += info.getPourcVictChevalHippo();
				}
			if(info.getPourcPlaceChevalHippo() != null) {
				pchTotalWon += info.getPourcPlaceChevalHippo();
				}
			if(info.getPourcPlaceCheval() != null) {
				pcTotalWon += info.getPourcPlaceCheval();
				}
			if(info.getTxVictCouple() != null) {
				vcoupleTotalWon += info.getTxVictCouple();
				}
			if(info.getTxPlaceCouple() != null) {
				pcoupleTotalWon += info.getTxPlaceCouple();
				}
			
			////Taille = 7
		}
		vehMoyenneWon = vehTotalWon/won.size();
		vjhMoyenneWon = vjhTotalWon/won.size();
		vchMoyenneWon = vchTotalWon/won.size();
		pchMoyenneWon = pchTotalWon/won.size();
		pcMoyenneWon = pcTotalWon/won.size();
		vcoupleMoyenneWon = vcoupleTotalWon/won.size();
		pcoupleMoyenneWon = pcoupleTotalWon/won.size();
		
		wonMoyennes.add(vehMoyenneWon);
		wonMoyennes.add(vjhMoyenneWon);
		wonMoyennes.add(vchMoyenneWon);
		wonMoyennes.add(pchMoyenneWon);
		wonMoyennes.add(pcMoyenneWon);
		wonMoyennes.add(vcoupleMoyenneWon);
		wonMoyennes.add(pcoupleMoyenneWon);



		List<TurfInfos> lost = all
				.stream()
				.filter(ti-> ti.getLiveOddPlace() == null || ti.getLiveOddPlace() == 0)
				.sorted(Comparator.comparing(TurfInfos::getJour).thenComparing(TurfInfos::getHour))
				.collect(Collectors.toList());

		
		Double vehTotalLost = 0d;
		Double vjhTotalLost = 0d;
		Double vchTotalLost = 0d;
		Double pchTotalLost = 0d;
		Double pcTotalLost = 0d;
		Double vcoupleTotalLost = 0d;
		Double pcoupleTotalLost = 0d;	
		
		Double vehMoyenneLost = 0d;
		Double vjhMoyenneLost = 0d;
		Double vchMoyenneLost = 0d;
		Double pchMoyenneLost = 0d;
		Double pcMoyenneLost = 0d;
		Double vcoupleMoyenneLost = 0d;
		Double pcoupleMoyenneLost = 0d;
		
		for(TurfInfos info: lost) {
			if(info.getPourcVictEntHippo() != null) {
				vehTotalLost += info.getPourcVictEntHippo();
				}
				if(info.getPourcVictJockHippo() != null) {
					vjhTotalLost += info.getPourcVictJockHippo();
					}
				if(info.getPourcVictChevalHippo() != null) {
					vchTotalLost += info.getPourcVictChevalHippo();
					}
				if(info.getPourcPlaceChevalHippo() != null) {
					pchTotalLost += info.getPourcPlaceChevalHippo();
					}
				if(info.getPourcPlaceCheval() != null) {
					pcTotalLost += info.getPourcPlaceCheval();
					}
				if(info.getTxVictCouple() != null) {
					vcoupleTotalLost += info.getTxVictCouple();
					}
				if(info.getTxPlaceCouple() != null) {
					pcoupleTotalLost += info.getTxPlaceCouple();
					}
			////Taille = 7
		}
		
		vehMoyenneLost = vehTotalLost/lost.size();
		vjhMoyenneLost = vjhTotalLost/lost.size();
		vchMoyenneLost = vchTotalLost/lost.size();
		pchMoyenneLost = pchTotalLost/lost.size();
		pcMoyenneLost = pcTotalLost/lost.size();
		vcoupleMoyenneLost = vcoupleTotalLost/lost.size();
		pcoupleMoyenneLost = pcoupleTotalLost/lost.size();
		
		lostMoyennes.add(vehMoyenneLost);
		lostMoyennes.add(vjhMoyenneLost);
		lostMoyennes.add(vchMoyenneLost);
		lostMoyennes.add(pchMoyenneLost);
		lostMoyennes.add(pcMoyenneLost);
		lostMoyennes.add(vcoupleMoyenneLost);
		lostMoyennes.add(pcoupleMoyenneLost);


		model.addAttribute("categorie", labels);
		model.addAttribute("series1", wonMoyennes);
		model.addAttribute("series2", lostMoyennes);


		System.out.println("filterbig" + list.size());
		
		return "place-table";
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
