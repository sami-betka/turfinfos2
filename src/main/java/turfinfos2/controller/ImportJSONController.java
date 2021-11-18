package turfinfos2.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import turfinfos2.model.ParisTurfInfosConfig;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ParisTurfInfoConfigRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.ImportJSONService;

@Controller
public class ImportJSONController {
	
	@Autowired
	ImportJSONService importJSONService;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	ParisTurfInfoConfigRepository parisTurfInfoConfigRepository;
	
	@PostMapping("/upload-json-data-from-url")
    public String uploadJSONFileByDay(@RequestParam("jour") String jour, Model model, RedirectAttributes redirect) {
		

				importJSONService.createAllDayInfosFromParisTurfJson(jour, false);

					importJSONService.updateAllDayInfosFromAspiJson(jour);				
			
				
//					System.out.println(node.get("pageProps").get("name").textValue());
//					jour = node.get("pageProps").get("race").get("date").textValue();
//					importJSONService.createAllRaceInfosFromParisTurfJson(url);
//					return "redirect:/day-infos?jour=" + jour;

					return "redirect:/day-infos?jour=" + jour;
	}
	
	@PostMapping("/update-paris-turf-json-data-from-url")
    public String uploadJSONFileByRace(@RequestParam("url") String url, Model model, RedirectAttributes redirect) {

		 JsonNode node;
         String jour = "";
         
			try {
				node = new ObjectMapper().readTree(new URL(url));
				jour = node.get("pageProps").get("date").textValue();
				importJSONService.createAllDayInfosFromParisTurfJson(url, true);
				return "redirect:/day-infos?jour=" + jour;


				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		return "redirect:/";
	}
	
	@PostMapping("/upload-rapports-json-data-from-url")
    public String uploadRapportsFile(@RequestParam("jour") String jour, Model model, RedirectAttributes redirect) {

		
			try {
				importJSONService.createRapportsInfosFromPMUJson(jour);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return "redirect:/day-infos?jour=" + jour;
		
	}
	
	///////////////////////////////////////////////////////////
	
	private String setParisTurfId (String parisTurfId, Model model) {
		ParisTurfInfosConfig configToUpdate = parisTurfInfoConfigRepository.findAll().stream().findFirst().get();
		
		configToUpdate.setParisTurfId(parisTurfId);
		parisTurfInfoConfigRepository.save(configToUpdate);
		
		model.addAttribute("idupdated", true);
		
		return "update";
		
	}
	
	   private void navbarInfos(Model model) {
		   
		   List<TurfInfos> allInfos = turfInfosRepository.findAll();
		   
		   //DATES
	  	 Set<String> dates = allInfos.stream()
					.map(TurfInfos :: getJour)
					.sorted()
					.collect(Collectors.toSet());
	       model.addAttribute("datesnav", dates);
		   
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

	   
	         Set<String> reunions = allInfos.stream()
	 				.filter(ti-> ti.getJour().equals(jour))
	        			.map(TurfInfos :: getReunionstring)
	        			.collect(Collectors.toSet());
	        			List<String> list = new ArrayList<String>(reunions);
	        			Collections.sort(list);        			
	        			reunions = new LinkedHashSet<>(list);
	        	         model.addAttribute("reunionsofday", reunions);
	   }

}
