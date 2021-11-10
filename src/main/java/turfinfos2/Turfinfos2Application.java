package turfinfos2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import turfinfos2.model.AppRole;
import turfinfos2.model.ParisTurfJSON;
import turfinfos2.model.TestJson;
import turfinfos2.model.TurfInfos;
import turfinfos2.model.UserAccount;
import turfinfos2.model.UserRole;
import turfinfos2.repository.AppRoleRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.repository.UserAccountRepository;
import turfinfos2.repository.UserRoleRepository;
import turfinfos2.service.ImportJSONService;
import turfinfos2.utils.EncrytedPasswordUtils;

@SpringBootApplication
public class Turfinfos2Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Turfinfos2Application.class, args);
		
		AppRoleRepository appRoleRepository = ctx.getBean(AppRoleRepository.class);
		if (appRoleRepository.findAll().isEmpty()) {
			appRoleRepository.save(new AppRole(1l, "ROLE_ADMIN"));
			appRoleRepository.save(new AppRole(2l, "ROLE_USER"));
		}

		UserAccountRepository userAccountRepository = ctx.getBean(UserAccountRepository.class);
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);

		if (userAccountRepository.findByUserName("admin") == null) {
			UserAccount user = new UserAccount();
			user.setUserName("admin");
			user.setPassword(EncrytedPasswordUtils.encrytePassword("123"));
			UserRole userRole = new UserRole(user, appRoleRepository.findById(1L).get());

			userAccountRepository.save(user);
			userRoleRepository.save(userRole);
		}

		if (userAccountRepository.findByUserName("user") == null) {
			UserAccount user = new UserAccount();
			user.setUserName("user");
			user.setPassword(EncrytedPasswordUtils.encrytePassword("123"));
			UserRole userRole = new UserRole(user, appRoleRepository.findById(2L).get());

			userAccountRepository.save(user);
			userRoleRepository.save(userRole);
		}
		
//		ImportJSONService service = ctx.getBean(ImportJSONService.class);
//		service.createAllRaceInfosFromJson();
		
		

	}
	

}
