package turfinfos2.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ImportJSONController {
	
	@PostMapping("/upload-json-file")
    public String uploadJSONFileByRace(@RequestParam("file") File file, Model model, RedirectAttributes redirect) {


		
		return "redirect:/";
	}
	
	///////////////////////////////////////////////////////////
	

}
