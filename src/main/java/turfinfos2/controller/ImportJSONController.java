package turfinfos2.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import turfinfos2.service.ImportJSONService;

@Controller
public class ImportJSONController {
	
	@Autowired
	ImportJSONService importJSONService;
	
	@PostMapping("/upload-json-data-from-url")
    public String uploadJSONFileByDay(@RequestParam("url") String url, Model model, RedirectAttributes redirect) {

		importJSONService.createAllDayInfosFromJson(url);
		
		return "redirect:/";
	}
	
	///////////////////////////////////////////////////////////
	

}
