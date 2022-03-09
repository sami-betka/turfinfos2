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
import turfinfos2.repository.ResultRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.repository.UserAccountRepository;
import turfinfos2.repository.UserRoleRepository;
import turfinfos2.service.EmailService;
import turfinfos2.service.TwilioService;

@Controller
public class MainController {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@Autowired
	TwilioService service;
	
	@Autowired
	EmailService emailService;
	

	 @GetMapping("/test-email")
	    public String testEmail(Model model) {
		 
//		 String to = "sami1206@hotmail.fr";
		 String to = "djetayyy@gmail.com";

	    	
		 emailService.sendMail(to, "Salut mon cochon, c'est Sami de SamTay Infos !", "Cet email pour te confirmer que j'ai bien mis en place l'envoi des emails. On va les baiser bientot !");

         navbarInfos(model);

	    	 return "home";

	    }
	
	 @GetMapping("/")
	    public String home(Model model) {
	    	
//             Resultat resultat =  resultRepository.findAll()
//             .stream()
//             .filter(ti->ti.getR().equals("1") && ti.getC().equals(3))
//             .findAny().get();
//             
//             System.out.println(resultat.toString());
		 
		 
	         navbarInfos(model);
	         
	         //"+33752447037"
//	 		SmsRequest smsRequest = new SmsRequest("+33652463080", "          Salut à toi Maitre DjeTay ! Merci pour cette connexion, sache que nous sommes tous avec toi ! Surtout moi, Sami, comme ça un jour on va faire un énorme billet ensemble ! Bonne nuit, et que le Sky soit avec toi !");
//	 		service.sendSms(smsRequest);
	         
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	         String jour = LocalDateTime.now().format(formatter);
//	    	 return "redirect:/day-infos?jour=" + jour;
	    	 return "home";

	    }
	
	 @GetMapping("/checkout")
	    public String checkout(Model model) {

	        return "checkout";
	    }
	 
	 @GetMapping("/contact")
	    public String contact(Model model) {

         navbarInfos(model);

	     return "contact";
	    }

	 @GetMapping("/redirect-to-reunion-infos")
	    public String redirectToReunionInfos(
	    		@RequestParam("jour") String jour,
	    		@RequestParam("reunion") String reunion,
	    		Model model) {
		 
		 navbarInfos(model);

			return "redirect:/reunion-infos?jour=" + jour + "&reunion=" + reunion;
	    }
	
//		@GetMapping("/sendmail")
//		public String sendMail(Model model, Principal principal) {
//
//			UserAccount user = userAccountRepository.findByUserName(principal.getName());
//
//			Mail mail = new Mail();
//			mail.setMailFrom("samtetedestup@gmail.com");
//			mail.setMailTo(user.getEmail());
//			mail.setMailSubject("Confirmation de commande");
//			mail.setMailContent("Commande confirmée");
//			mailService.sendEmail(mail, principal);
//
//			navbarAttributes(model, principal);
//
//			return "mailconfirmation";
//
//		}
    
    ///////////////////////////////////////////////////////////////////////////////
    
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