package turfinfos2.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.ImportJSONService;

@Controller
public class ImportJSONController {
	
	@Autowired
	ImportJSONService importJSONService;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@PostMapping("/upload-json-data-from-url")
    public String uploadJSONFileByDay(@RequestParam("url") String url, Model model, RedirectAttributes redirect) {
		
	              JsonNode node;
	              String jour = "";
		
			try {
				node = new ObjectMapper().readTree(new URL(url));
				
				if(node.get("pageProps") == null) {
					jour = node.get(0).get("numcourse").get("jour").textValue();
					importJSONService.createAllDayInfosFromAspiJson(url);
					navbarInfos(model);
					return "redirect:/day-infos?jour=" + jour;
				}
				if(node.get("pageProps").get("name").textValue().equals("program")) {
					System.out.println(node.get("pageProps").get("name").textValue());

					jour = node.get("pageProps").get("date").textValue();
					importJSONService.createAllDayInfosFromParisTurfJson(url);
					navbarInfos(model);
					return "redirect:/day-infos?jour=" + jour;
				}
				if(node.get("pageProps").get("name").textValue().equals("race")) {
					System.out.println(node.get("pageProps").get("name").textValue());
					jour = node.get("pageProps").get("race").get("date").textValue();
					importJSONService.createAllRaceInfosFromParisTurfJson(url);
					navbarInfos(model);
					return "redirect:/day-infos?jour=" + jour;
				}
				System.out.println("NODE EMPTY ? " + node.isEmpty());
				

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			navbarInfos(model);

			return "redirect:/";
	}
	
//	@PostMapping("/upload-json-race-data")
//    public String uploadJSONFileByRace(@RequestParam("url") String url, Model model, RedirectAttributes redirect) {
//
//		importJSONService.createAllRaceInfosFromJson(url);
//		
//		return "redirect:/";
//	}
	
	///////////////////////////////////////////////////////////
	
	   private void navbarInfos(Model model) {
		   
		   List<TurfInfos> allInfos = turfInfosRepository.findAll();
		   
		   //DATES
	  	 Set<String> dates = allInfos.stream()
					.map(TurfInfos :: getJour)
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
	 				.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
	        			.map(TurfInfos :: getReunionstring)
	        			.collect(Collectors.toSet());
	        			List<String> list = new ArrayList<String>(reunions);
	        			Collections.sort(list);        			
	        			reunions = new LinkedHashSet<>(list);
	        	         model.addAttribute("reunionsofday", reunions);

	   }

}
