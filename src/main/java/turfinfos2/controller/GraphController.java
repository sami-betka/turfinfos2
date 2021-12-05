package turfinfos2.controller;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.ArchiveService;
import turfinfos2.service.ResultService;

@Controller
public class GraphController {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	ResultService resultService;
	
	@Autowired
	ArchiveService archiveService;

	@GetMapping("/bar-chart")
	public String barChart(Model model){
		

		List<TurfInfos> all = archiveService.setAllPlaceFirstPronoList(turfInfosRepository.findAll())
				.stream()
				.filter(ti->
				        
				        ti.getNumberOfInitialRunners() > 1
						&& ti.getHasBetTypes() == true 
						&& ti.getLiveOdd() != null && ti.getLiveOdd() != 0 && ti.getLiveOdd()<4
				        && ti.getRecence() != null && ti.getRecence()<60
				        && ti.getRaceSpecialty().equals("P")
				        && ti.getFormFigs() != null && ti.getFormFigs().length()>= 2 && ti.getFormFigs().charAt(0)=='1' && ti.getFormFigs().charAt(1)=='p'
//				        && ( ti.getJour().contains("2021-04") || ti.getJour().contains("2021-04") )

				        )
				.sorted(Comparator.comparing(TurfInfos::getJour).thenComparing(TurfInfos::getHour))
				.collect(Collectors.toList());
		
		
		
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
		labels.add("RappMAX");
		labels.add("RappMIN");


		
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
		Double rappMaxTotalWon = 0d;
		Double rappMinTotalWon = 0d;
		
		Double vehMoyenneWon = 0d;
		Double vjhMoyenneWon = 0d;
		Double vchMoyenneWon = 0d;
		Double pchMoyenneWon = 0d;
		Double pcMoyenneWon = 0d;
		Double vcoupleMoyenneWon = 0d;
		Double pcoupleMoyenneWon = 0d;
		Double rappMaxMoyenneWon = 0d;
		Double rappMinMoyenneWon = 0d;

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
			if(info.getMaxRapportProbable() != null) {
				rappMaxTotalWon += info.getMaxRapportProbable();
				}
			if(info.getMinRapportProbable() != null) {
				rappMinTotalWon += info.getMinRapportProbable();
				}
			
			////Taille = 9
		}
		vehMoyenneWon = vehTotalWon/won.size();
		vjhMoyenneWon = vjhTotalWon/won.size();
		vchMoyenneWon = vchTotalWon/won.size();
		pchMoyenneWon = pchTotalWon/won.size();
		pcMoyenneWon = pcTotalWon/won.size();
		vcoupleMoyenneWon = vcoupleTotalWon/won.size();
		pcoupleMoyenneWon = pcoupleTotalWon/won.size();
		rappMaxMoyenneWon = rappMaxTotalWon/won.size();
		rappMinMoyenneWon = rappMinTotalWon/won.size();
		
		wonMoyennes.add(vehMoyenneWon);
		wonMoyennes.add(vjhMoyenneWon);
		wonMoyennes.add(vchMoyenneWon);
		wonMoyennes.add(pchMoyenneWon);
		wonMoyennes.add(pcMoyenneWon);
		wonMoyennes.add(vcoupleMoyenneWon);
		wonMoyennes.add(pcoupleMoyenneWon);
		wonMoyennes.add(rappMaxMoyenneWon);
		wonMoyennes.add(rappMinMoyenneWon);



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
		Double rappMaxTotalLost = 0d;
		Double rappMinTotalLost = 0d;
		
		Double vehMoyenneLost = 0d;
		Double vjhMoyenneLost = 0d;
		Double vchMoyenneLost = 0d;
		Double pchMoyenneLost = 0d;
		Double pcMoyenneLost = 0d;
		Double vcoupleMoyenneLost = 0d;
		Double pcoupleMoyenneLost = 0d;
		Double rappMaxMoyenneLost = 0d;
		Double rappMinMoyenneLost = 0d;
		
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
				if(info.getMaxRapportProbable() != null) {
					rappMaxTotalLost += info.getMaxRapportProbable();
					}
				if(info.getMinRapportProbable() != null) {
					rappMinTotalLost += info.getMinRapportProbable();
					}
			////Taille = 9
		}
		
		vehMoyenneLost = vehTotalLost/lost.size();
		vjhMoyenneLost = vjhTotalLost/lost.size();
		vchMoyenneLost = vchTotalLost/lost.size();
		pchMoyenneLost = pchTotalLost/lost.size();
		pcMoyenneLost = pcTotalLost/lost.size();
		vcoupleMoyenneLost = vcoupleTotalLost/lost.size();
		pcoupleMoyenneLost = pcoupleTotalLost/lost.size();
		rappMaxMoyenneLost = rappMaxTotalLost/lost.size();
		rappMinMoyenneLost = rappMinTotalLost/lost.size();
		
		lostMoyennes.add(vehMoyenneLost);
		lostMoyennes.add(vjhMoyenneLost);
		lostMoyennes.add(vchMoyenneLost);
		lostMoyennes.add(pchMoyenneLost);
		lostMoyennes.add(pcMoyenneLost);
		lostMoyennes.add(vcoupleMoyenneLost);
		lostMoyennes.add(pcoupleMoyenneLost);
		lostMoyennes.add(rappMaxMoyenneLost);
		lostMoyennes.add(rappMinMoyenneLost);


		model.addAttribute("categorie", labels);
		model.addAttribute("series1", wonMoyennes);
		model.addAttribute("series2", lostMoyennes);
		
		return "barchart";
	}
	
}
