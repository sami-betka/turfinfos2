package turfinfos2.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.repository.UserAccountRepository;
import turfinfos2.repository.UserRoleRepository;
import turfinfos2.service.TwilioService;

@Controller
public class MainController {
	
	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	TwilioService service;
	
	 @GetMapping("/")
	    public String home(Model model) {
	    	
//	    	 Set<String> dates = turfInfosRepository.findAll().stream()
//	 				.map(TurfInfos :: getJour)
//	 				.collect(Collectors.toSet());
	    	 
	         navbarInfos(model);
	         
	         //"+33752447037"
//	 		SmsRequest smsRequest = new SmsRequest("+33652463080", "          Salut à toi Maitre DjeTay ! Merci pour cette connexion, sache que nous sommes tous avec toi ! Surtout moi, Sami, comme ça un jour on va faire un énorme billet ensemble ! Bonne nuit, et que le Sky soit avec toi !");
//	 		service.sendSms(smsRequest);
	         
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	         String jour = LocalDateTime.now().format(formatter);
//	    	 return "redirect:/day-infos?jour=" + jour;
	    	 return "home";

	    }
	
	 @GetMapping("/test")
	    public String test(Model model) {

	        return "fire";
	    }

	 @GetMapping("/redirect-to-reunion-infos")
	    public String redirectToReunionInfos(
	    		@RequestParam("jour") String jour,
	    		@RequestParam("reunion") String reunion,
	    		Model model) {
		 
		 navbarInfos(model);

			return "redirect:/reunion-infos?jour=" + jour + "&reunion=" + reunion;
	    }
	
    @GetMapping("/nav")
    public String nav(Model model) {

        return "_nav-components";
    }
    
    @GetMapping("/doigt")
    public String doigt(Model model) {
    	
        return "doigt";
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    
    private void navbarInfos(Model model) {
 	   
 	   List<TurfInfos> allInfos = turfInfosRepository.findAll();
 	   System.out.println("gggg" +allInfos.size());
 	   
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
        System.out.println(jour);
        
        
//     	 Set<String> reunions = allInfos.stream()
// 				.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//   				.map(TurfInfos :: getReunionstring)
////   				.sorted()
//   				.collect(Collectors.toSet());
//          model.addAttribute("reunionsofday", reunions);

    
          Set<String> reunions = allInfos.stream()
  				.filter(ti-> ti.getReunionstring() != null && ti.getJour().equals(jour) && ti.getR().length()<3)
         			.map(TurfInfos :: getReunionstring)
         			.collect(Collectors.toSet());
         			List<String> list = new ArrayList<String>(reunions);
         			Collections.sort(list);        			
         			reunions = new LinkedHashSet<>(list);
         	         model.addAttribute("reunionsofday", reunions);
    }
        
}