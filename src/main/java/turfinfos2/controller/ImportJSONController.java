package turfinfos2.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import turfinfos2.model.ParisTurfInfosConfig;
import turfinfos2.model.Resultat;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ParisTurfInfoConfigRepository;
import turfinfos2.repository.ResultRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.ImportJSONService;

@Controller
public class ImportJSONController {
	
	@Autowired
	ImportJSONService importJSONService;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@Autowired
	ParisTurfInfoConfigRepository parisTurfInfoConfigRepository;
	
	
	@PostMapping("/upload-json-data-from-url")
    public String uploadJSONFileByDay(@RequestParam("jour") String jour, Model model, RedirectAttributes redirect) {
		
		turfInfosRepository.saveAll(importJSONService.createAllDayInfosFromParisTurfJson(jour, false));

		turfInfosRepository.saveAll(importJSONService.updateAllDayInfosFromAspiJson(jour));				

					return "redirect:/day-infos?jour=" + jour;
	}
	
	@PostMapping("/upload-data-date-range")
    public String uploadJSONFileDateRange(
    		@RequestParam(name = "datedebut", required = false, defaultValue = "2018-07-01") String datedebut, 
			@RequestParam(name = "datefin", required = false, defaultValue = "2018-08-31") String datefin) {
		
		List<TurfInfos> allTurfToSave = new ArrayList<>();
		List<TurfInfos> allAspiToSave = new ArrayList<>();
		List<TurfInfos> allRapportsToSave = new ArrayList<>();
		
//		List<TurfInfos> all = turfInfosRepository.findAll();

		final LocalDate start = LocalDate.parse(datedebut, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		  final LocalDate end = LocalDate.parse(datefin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
		  final long days = start.until(end, ChronoUnit.DAYS);
	
		  List<LocalDate> dates = LongStream.rangeClosed(0, days)
		      .mapToObj(start::plusDays)
		      .collect(Collectors.toList());
		  
		  
//		  dates.forEach(ld->{
//			  allTurfToSave.addAll(importJSONService.createAllDayInfosFromParisTurfJson(ld.toString(), false));
////				importJSONService.updateAllDayInfosFromAspiJson(ld.toString());		  
//				});
//		  turfInfosRepository.saveAll(allTurfToSave);
	
//		  dates.forEach(ld->{
////			  importJSONService.createAllDayInfosFromParisTurfJson(ld.toString(), false);
//				allAspiToSave.addAll(importJSONService.updateAllDayInfosFromAspiJson(ld.toString()));	
//				System.out.println(ld.toString());
//				});
//		  turfInfosRepository.saveAll(allAspiToSave);
		  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		  

		  dates.forEach(ld->{
			  turfInfosRepository.saveAll(importJSONService.createAllDayInfosFromParisTurfJson(ld.toString(), false));
//			importJSONService.updateAllDayInfosFromAspiJson(ld.toString());		  
			});
		  
		  dates.forEach(ld->{
//			  importJSONService.createAllDayInfosFromParisTurfJson(ld.toString(), false);
			  turfInfosRepository.saveAll(importJSONService.updateAllDayInfosFromAspiJson(ld.toString()));	
				System.out.println(ld.toString());
			});
		  

            System.out.println("STOOOP");
					return "redirect:/";
	}
	
	@PostMapping("/upload-rapports-date-range")
    public String uploadRapportsFileDateRange(
    		@RequestParam(name = "datedebut", required = false, defaultValue = "2018-08-31") String datedebut, 
			@RequestParam(name = "datefin", required = false, defaultValue = "2018-12-31") String datefin) {
		
		List<TurfInfos> allTurfInfosToSave = new ArrayList<>();
		List<Resultat> allResultToSave = new ArrayList<>();

//		List<TurfInfos> all = turfInfosRepository.findAll();
//		List<Resultat> allResults = resultRepository.findAll();



		final LocalDate start = LocalDate.parse(datedebut, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		  final LocalDate end = LocalDate.parse(datefin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
		  final long days = start.until(end, ChronoUnit.DAYS);
	
		  List<LocalDate> dates = LongStream.rangeClosed(0, days)
		      .mapToObj(start::plusDays)
		      .collect(Collectors.toList());
		  
		  
		  dates.forEach(ld->{
			 
				try {
					List<TurfInfos> all2 = turfInfosRepository.findAllByJour(ld.toString()).stream()
//							.filter(ti->ti.getJour().equals(ld.toString()))
							.collect(Collectors.toList());
					List<Resultat> allResults2 = resultRepository.findAllByJour(ld.toString()).stream()
//							.filter(r->r.getJour().equals(ld.toString()))
							.collect(Collectors.toList());
					
					Map<String, Object> map = importJSONService.createRapportsInfosFromPMUJson(ld.toString(), all2, allResults2);
					
//					allTurfInfosToSave.addAll((List<TurfInfos>) importJSONService.createRapportsInfosFromPMUJson(ld.toString(), all, allResults).get("turfinfos"));
//					allResultToSave.addAll((List< Resultat>) importJSONService.createRapportsInfosFromPMUJson(ld.toString(), all, allResults).get("results"));
					allTurfInfosToSave.addAll((List<TurfInfos>) map.get("turfinfos"));
					allResultToSave.addAll((List< Resultat>) map.get("results"));
					//					importJSONService.createRapportsInfosFromPMUJson(ld.toString());
				} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		  });
		  
			System.out.println(allTurfInfosToSave.size());

            turfInfosRepository.saveAll(allTurfInfosToSave);
			resultRepository.saveAll(allResultToSave);
		  
			System.out.println("STOP RAPPORTS");
			return "redirect:/";
	}
	
//	@PostMapping("/update-paris-turf-json-data-from-url")
//    public String uploadJSONFileByRace(@RequestParam("url") String url, Model model, RedirectAttributes redirect) {
//
//		 JsonNode node;
//         String jour = "";
//         
//			try {
//				node = new ObjectMapper().readTree(new URL(url));
//				jour = node.get("pageProps").get("date").textValue();
//				importJSONService.createAllDayInfosFromParisTurfJson(url, true);
//				return "redirect:/day-infos?jour=" + jour;
//
//
//				
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		
//		return "redirect:/";
//	}
	
	@PostMapping("/upload-rapports-json-data-from-url")
    public String uploadRapportsFile(@RequestParam("jour") String jour, Model model, RedirectAttributes redirect) {

//		List<TurfInfos> all = turfInfosRepository.findAll();
//		List<Resultat> allResults = resultRepository.findAll();
		List<TurfInfos> all = turfInfosRepository.findAllByJour(jour);
		List<Resultat> allResults = resultRepository.findAllByJour(jour);

		
			try {
				turfInfosRepository.saveAll((List<TurfInfos>) importJSONService.createRapportsInfosFromPMUJson(jour, all, allResults).get("turfinfos"));
				resultRepository.saveAll((List<Resultat>) importJSONService.createRapportsInfosFromPMUJson(jour, all, allResults).get("results"));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "redirect:/day-infos?jour=" + jour;
	}

	
	@PostMapping("/upload-rapports-json-data-from-url-online")
    public String uploadRapportsFileOnline(@RequestParam("jour") String jour, Model model, RedirectAttributes redirect) {

		
			try {
				importJSONService.createRapportsInfosFromPMUJsonOnline(jour);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "redirect:/day-infos?jour=" + jour;
     	}
	
	@PostMapping("/update-paris-turf-id")
	public String setParisTurfId (@RequestParam("paristurfid") String parisTurfId, Model model) {
		
		parisTurfInfoConfigRepository.deleteAll();
		
		
		ParisTurfInfosConfig newConfig = new ParisTurfInfosConfig();
		
		newConfig.setParisTurfId(parisTurfId);
		parisTurfInfoConfigRepository.save(newConfig);
		
		model.addAttribute("idupdated", true);
		
		return "redirect:/upload";
		
	}
	
	///////////////////////////////////////////////////////////

	   private void navbarInfos(Model model) {
		   
//		   List<TurfInfos> allInfos = turfInfosRepository.findAll();

		   
		   //DATES
			 Set<String> dates = turfInfosRepository.findAllJours()
		  			 .stream()
		  			 .collect(Collectors.toSet());
//	  	 Set<String> dates = allInfos.stream()
//					.map(TurfInfos :: getJour)
//					.sorted()
//					.collect(Collectors.toSet());
	  	 
	  	List<String> datesSorted = dates.stream().collect(Collectors.toList());
	  	Collections.sort(datesSorted, (o1, o2) -> o1.compareTo(o2));
	        model.addAttribute("datesnav", datesSorted);		   
	       //REUNIONS
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       String jour = LocalDateTime.now().format(formatter);
	       model.addAttribute("journav", jour);
	       System.out.println(jour);
	       
	       
//	    	 Set<String> reunions = allInfos.stream()
//					.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//	  				.map(TurfInfos :: getReunionstring)
////	  				.sorted()
//	  				.collect(Collectors.toSet());
////	    	 reunions.sort( Comparator.comparing( String::toString));
//	         model.addAttribute("reunionsofday", reunions);

		   List<TurfInfos> allByJour = turfInfosRepository.findAllByJour(jour);

	         Set<String> reunions = allByJour.stream()
//	 				.filter(ti-> ti.getJour().equals(jour))
	        			.map(TurfInfos :: getReunionstring)
	        			.collect(Collectors.toSet());
	        			List<String> list = new ArrayList<String>(reunions);
	        			Collections.sort(list);        			
	        			reunions = new LinkedHashSet<>(list);
	        	         model.addAttribute("reunionsofday", reunions);
	   }

}
