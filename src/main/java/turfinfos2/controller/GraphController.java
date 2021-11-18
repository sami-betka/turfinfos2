package turfinfos2.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;

@Controller
public class GraphController {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;

	@GetMapping("/bar-chart")
	public String barChart(Model model){
		
//		List<TurfInfos> allByJour = turfInfosRepository.findAll();
		
		List<TurfInfos> raceR1C1 = turfInfosRepository.findAll().stream()
				.filter(ti->ti.getJour().equals("2021-11-17") && ti.getR().equals("1") && ti.getC().equals(1))
				.sorted(Comparator.comparingInt(TurfInfos::getNumero))
				.collect(Collectors.toList());
		
		System.out.println(raceR1C1.size());
		
		
		Map<String, Integer> data = new LinkedHashMap<String, Integer>();
		for(TurfInfos info : raceR1C1) {
			data.put(String.valueOf(info.getNumero()), info.getRanking());

		}
		
//		data.put("Ashish", 30);
//		data.put("Ankit", 50);
//		data.put("Gurpreet", 70);
//		data.put("Mohit", 90);
//		data.put("Manish", 25);
		model.addAttribute("keySet", data.keySet());
		model.addAttribute("values", data.values());
		model.addAttribute("size", data.size());
		
		return "barchart";
	}
	
}
