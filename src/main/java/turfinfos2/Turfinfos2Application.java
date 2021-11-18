package turfinfos2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Turfinfos2Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Turfinfos2Application.class, args);
		
//		AppRoleRepository appRoleRepository = ctx.getBean(AppRoleRepository.class);
//		if (appRoleRepository.findAll().isEmpty()) {
//			appRoleRepository.save(new AppRole(1l, "ROLE_ADMIN"));
//			appRoleRepository.save(new AppRole(2l, "ROLE_USER"));
//		}
//
//		UserAccountRepository userAccountRepository = ctx.getBean(UserAccountRepository.class);
//		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
//
//		if (userAccountRepository.findByUserName("admin") == null) {
//			UserAccount user = new UserAccount();
//			user.setUserName("admin");
//			user.setPassword(EncrytedPasswordUtils.encrytePassword("123"));
//			UserRole userRole = new UserRole(user, appRoleRepository.findById(1L).get());
//
//			userAccountRepository.save(user);
//			userRoleRepository.save(userRole);
//		}
//
//		if (userAccountRepository.findByUserName("user") == null) {
//			UserAccount user = new UserAccount();
//			user.setUserName("user");
//			user.setPassword(EncrytedPasswordUtils.encrytePassword("123"));
//			UserRole userRole = new UserRole(user, appRoleRepository.findById(2L).get());
//
//			userAccountRepository.save(user);
//			userRoleRepository.save(userRole);
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		TurfInfosRepository turfInfosRepository = ctx.getBean(TurfInfosRepository.class);
//
//		
//		
//        List<TurfInfos> all = turfInfosRepository.findAll();
//        
//        all.forEach(ti-> {
//        	
//        	if(ti.getHasBetTypes() == null) {
//        		ti.setHasBetTypes(false);
//            	turfInfosRepository.save(ti);
//        	}
//        
//        });
//        System.out.println("STOP");
		
		
		
		
		
		
//		ImportJSONService service = ctx.getBean(ImportJSONService.class);
//		service.createAllRaceInfosFromJson();
		
		
		
//		String jour = "2021-11-17";
//		
//		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
//	       String jour2 = formatter.parse(jour).toString();
//	       System.out.println(jour2);

	}
	

}
