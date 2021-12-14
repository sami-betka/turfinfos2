package turfinfos2;

import java.util.ArrayList;
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
		
		//////CRON//
//		second, minute, hour, day of month, month, day(s) of week

//		        * "0 0 * * * *" = the top of every hour of every day.
//				* "*/10 * * * * *" = every ten seconds.
//				* "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
//				* "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
//				* "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
//				* "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
//				* "0 0 0 25 12 ?" = every Christmas Day at midnight
		
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
//////	
//        List<TurfInfos> all = new ArrayList<>();
//        all.addAll(turfInfosRepository.findAll());
////        		
//        List<TurfInfos> toSave = new ArrayList<>();
////        List<TurfInfos> toDelete = new ArrayList<>();
////
////
////        
//        all.forEach(ti-> {
//        	
//        	if(ti.getIsFavori() == null) {
//        		ti.setIsFavori(false);
//        		toSave.add(ti);
//        	}
//        
//        });
//        turfInfosRepository.saveAll(toSave);
//        System.out.println("STOP");
////        System.out.println(toSave.size());

		
		
		
		
		
		
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

		//////////////////////////////////////
		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
//		String time = LocalDateTime.now().format(formatter);
//        System.out.println(time);

//		CurrentOddsRepository currentOddsRepository = ctx.getBean(CurrentOddsRepository.class);
//		
//		CurrentOdds odd = new CurrentOdds();
//		odd.setC(1);
//		odd.setId(1l);
//		odd.setJour("2021-11-21");
//		odd.setR("1");
//		odd.getOddsMap().put(1, 1.3);
//		odd.getOddsMap().put(2, 2.3);
//		odd.getOddsMap().put(3, 3.3);
//        odd.setTime("01:22:34");
//
//		currentOddsRepository.save(odd);
//		
//		CurrentOdds odd2 = new CurrentOdds();
//		odd2.setC(1);
//		odd2.setId(2l);
//		odd2.setJour("2021-11-21");
//		odd2.setR("1");
//		odd2.getOddsMap().put(1, 12.3);
//		odd2.getOddsMap().put(2, 13.3);
//		odd2.getOddsMap().put(3, 14.3);
//        odd2.setTime("01:23:34");
//
//		currentOddsRepository.save(odd2);
//		
//		CurrentOdds odd3 = new CurrentOdds();
//		
//		odd3.setC(1);
//		odd3.setId(3l);
//		odd3.setJour("2021-11-21");
//		odd3.setR("1");
//		odd3.getOddsMap().put(1, 15d);
//		odd3.getOddsMap().put(2, 23d);
//		odd3.getOddsMap().put(3, 3.3);
//        odd3.setTime("01:24:34");
//		currentOddsRepository.save(odd3);
//		

//		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	       String jour = LocalDateTime.now().format(formatter);
//	       
//	       LocalDateTime.
//	       
//	       System.out.println();
		
//		ResultRepository resultRepository = ctx.getBean(ResultRepository.class);

//		Resultat resultat = resultRepository.findById(1l).get();
//		System.out.println(resultat.getJour());
//		System.out.println(resultat.getR());
//		System.out.println(resultat.getC());
//		System.out.println(resultat.getSimpleGagnant().get("Simple gagnant"));
//		System.out.println(resultat.getCoupleGagnant().get("Couplé gagnant"));
		
	}
	

}
