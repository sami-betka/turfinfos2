package turfinfos2.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

@Controller
public class MainController {
	
	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	 @GetMapping("/")
	    public String home(Model model) {
	    	
	    	 Set<String> dates = turfInfosRepository.findAll().stream()
	 				.map(TurfInfos :: getJour)
	 				.collect(Collectors.toSet());
	    	 
	         navbarInfos(model);
	    	 return "home";
	    }
	
	 @GetMapping("/test")
	    public String test(Model model) {

	        return "test";
	    }

	 @GetMapping("/redirect-to-reunion-infos")
	    public String redirectToReunionInfos(
	    		@RequestParam("jour") String jour,
	    		@RequestParam("reunion") String reunion,
	    		Model model) {

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
 	   
 	   //DATES
   	 Set<String> dates = allInfos.stream()
 				.map(TurfInfos :: getJour)
 				.collect(Collectors.toSet());
        model.addAttribute("datesnav", dates);
 	   
        //REUNIONS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String jour = LocalDateTime.now().format(formatter);
        model.addAttribute("journav", jour);
//        System.out.println(jour);
        
     	 Set<String> reunions = allInfos.stream()
 				.filter(ti-> ti.getJour()==jour)
   				.map(TurfInfos :: getR)
   				.collect(Collectors.toSet());
          model.addAttribute("reunionsofday", reunions);
        
    }
        
}