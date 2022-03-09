package turfinfos2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig
{
	@Bean
	public SimpleMailMessage emailTemplate()
	{
		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo("sami1206@hotmail.fr");
//		message.setFrom("samtetedestup@gmail.com");
//		message.setSubject("Important email");
//	    message.setText("FATAL - Application crash. Save your job !!");
	    return message;
	}
}