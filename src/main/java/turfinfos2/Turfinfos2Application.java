package turfinfos2;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;

@SpringBootApplication
public class Turfinfos2Application {

	public static void main(String[] args) {
//		create table if not exists persistent_logins ( 
//				  username varchar_ignorecase(100) not null, 
//				  series varchar(64) primary key, 
//				  token varchar(64) not null, 
//				  last_used timestamp not null 
//				);
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
//        List<TurfInfos> all = turfInfosRepository.findAll();
//        
//        all.forEach(ti-> {
//        	
//        	if(ti.getChronoPastille() == null) {
//        		ti.setChronoPastille(false);
//            	turfInfosRepository.save(ti);
//        	}
//        	if(ti.getJockeyPastille() == null) {
//        		ti.setJockeyPastille(false);
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
		
		
		
//		String hour = "16:27:00";
//
//		DateTimeFormatter format1 = DateTimeFormatter.ofPattern("H:mm:ss");
//		DateTimeFormatter format2 = DateTimeFormatter.ofPattern("H");
//		DateTimeFormatter format3 = DateTimeFormatter.ofPattern("mm");
//
//		TemporalAccessor hour2 = format1.parse(hour);
//		
//	    String hourParse1 = format2.format(hour2).toString();
//	    String hourParse2 = format3.format(hour2).toString();
//	    String finalHour = hourParse1 + "h" + hourParse2;
//
//	       System.out.println(hourParse1 + "h" + hourParse2);

		
		
		
	}
	

}
