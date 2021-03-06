package turfinfos2.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import turfinfos2.model.TurfInfos;
import turfinfos2.model.UserAccount;
import turfinfos2.model.UserRole;
import turfinfos2.repository.AppRoleRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.repository.UserAccountRepository;
import turfinfos2.repository.UserRoleRepository;
import turfinfos2.utils.EncrytedPasswordUtils;

@Controller
public class UserController {
	
	
	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	AppRoleRepository appRoleRepository;
	
	@Autowired
	static EncrytedPasswordUtils encrytedPasswordUtils;
	
	// Ajouter un utilisateur

	@GetMapping(value = "/create-account")
	public String addUser(Model model, Principal principal) {

		model.addAttribute("emptyUser", new UserAccount());
		navbarInfos(model);

		return "register";
	}

	@PostMapping("/save-user")
	public String saveUser(UserAccount user, BindingResult result, Model model, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("createsuccess", true);
			return "redirect:/create-account";
		}
		if (userRepository.findByUserName(user.getUserName()) != null) {
			redirect.addFlashAttribute("usernamealreadyexists", "Nom d'utilisateur d??ja utilis??");
			return "redirect:/create-account";
		}
		user.setEncrytedPassword(encrytedPasswordUtils.encrytePassword(user.getEncrytedPassword()));

		UserRole userRole = new UserRole(user, appRoleRepository.findById(2L).get());

		userRepository.save(user);
		userRoleRepository.save(userRole);

		return "redirect:/";
	}	
	
	@GetMapping("/my-infos")
	public String getMyInfos (Model model, Principal principal) {
		
//		if (principal == null) {
//			return "redirect:/login";
//		}
		
		UserAccount user = new UserAccount();
		user.setUserName("sam");
		user.setEmail("dfghj@dfghj");
		user.setEncrytedPassword("123");
		
		model.addAttribute("user", user);

		model.addAttribute("active", true);
		navbarInfos(model);

		return "my-infos";
	}	
	
	@RequestMapping(value = "/edit-my-infos")
	public String editUser(Model model, Principal principal) {

//		if (principal == null) {
//			return "redirect:/login";
//		}

//		UserAccount user = userRepository.findByUserName(principal.getName());
		UserAccount user = new UserAccount();
		user.setUserName("sam");
		user.setEmail("dfghj@dfghj");
		user.setEncrytedPassword("123");

		model.addAttribute("user", user);

		navbarInfos(model);
		return "edit-my-infos";

	}

	@PostMapping(value = "/update-my-infos")
	public String updateUser(@Valid UserAccount user, Model model, BindingResult bindingresult, Principal principal,RedirectAttributes redirect) {
		if (bindingresult.hasErrors()) {
			redirect.addFlashAttribute("createsuccess", true);

			return "redirect:/edit-my-infos";
		}
		
//		if (principal == null) {
//			return "redirect:/login";
//		}
//		UserAccount user = userAccountRepository.findByUserName(principal.getName());
//		if (user == null) {
//			return "redirect:/login";
//		}
		
		model.addAttribute("user", user);
		navbarInfos(model);
		
		user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword()));
//			userRepository.save(user);

		return "redirect:/my-infos";
		
	}
	 
		@GetMapping("/login")
		public String loginPage(@RequestParam(name = "error", required = false) boolean error, Model model, Principal principal) {

//			if (principal != null) {
//				return "redirect:/bankroll-list";
//			}
			
			if(error == true) {
				model.addAttribute("errormessage", "Identifiant ou mot de passe incorrect");
			}
			navbarInfos(model);

			return "login";
		}
		
		@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
		public String logoutSuccessfulPage(Model model, Principal principal) {
			model.addAttribute("title", "Logout");
//	        navbarAttributes(model, principal);
			return "redirect:/";

//	        return "logoutSuccessfulPage";
		}
		
		@RequestMapping(value = "/userAccountInfo", method = RequestMethod.GET)
		public String loginSuccess(Model model, Principal principal) {

			UserAccount user = userRepository.findByUserName(principal.getName());
			for (UserRole userRole : userRoleRepository.findAll()) {
				if (userRole.getAppRole().getRoleId() == 1 && userRole.getUser().getId() == user.getId()) {
					navbarInfos(model);
					return "redirect:/admingate";
				}
			}
//			SmsRequest smsRequest = new SmsRequest("+33652463080", "Youhou !");
//			service.sendSms(smsRequest);
			navbarInfos(model);
			return "redirect:/";
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////
		
		   private void navbarInfos(Model model) {
			   
//			   List<TurfInfos> allInfos = turfInfosRepository.findAll();

			   
			   //DATES
				 Set<String> dates = turfInfosRepository.findAllJours()
			  			 .stream()
			  			 .collect(Collectors.toSet());
//		  	 Set<String> dates = allInfos.stream()
//						.map(TurfInfos :: getJour)
//						.sorted()
//						.collect(Collectors.toSet());
		  	 
		  	List<String> datesSorted = dates.stream().collect(Collectors.toList());
		  	Collections.sort(datesSorted, (o1, o2) -> o1.compareTo(o2));
		        model.addAttribute("datesnav", datesSorted);		   
		       //REUNIONS
		       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		       String jour = LocalDateTime.now().format(formatter);
		       model.addAttribute("journav", jour);
		       System.out.println(jour);
		       
		       
//		    	 Set<String> reunions = allInfos.stream()
//						.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//		  				.map(TurfInfos :: getReunionstring)
////		  				.sorted()
//		  				.collect(Collectors.toSet());
////		    	 reunions.sort( Comparator.comparing( String::toString));
//		         model.addAttribute("reunionsofday", reunions);

			   List<TurfInfos> allByJour = turfInfosRepository.findAllByJour(jour);

		         Set<String> reunions = allByJour.stream()
//		 				.filter(ti-> ti.getJour().equals(jour))
		        			.map(TurfInfos :: getReunionstring)
		        			.collect(Collectors.toSet());
		         
		        			List<String> list = new ArrayList<String>(reunions);
		        			Collections.sort(list);        			
		        			reunions = new LinkedHashSet<>(list);
		        	         model.addAttribute("reunionsofday", reunions);
		   }

}
