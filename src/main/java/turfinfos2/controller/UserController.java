package turfinfos2.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
			redirect.addFlashAttribute("usernamealreadyexists", "Nom d'utilisateur déja utilisé");
			return "redirect:/create-account";
		}
		user.setPassword(encrytedPasswordUtils.encrytePassword(user.getPassword()));

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
		
		model.addAttribute("active", true);
		navbarInfos(model);

		return "upload";
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
//		       System.out.println(jour);
		       
		    	 Set<String> reunions = allInfos.stream()
						.filter(ti-> ti.getJour()==jour)
		  				.map(TurfInfos :: getR)
		  				.collect(Collectors.toSet());
		         model.addAttribute("reunionsofday", reunions);
		   }

}
