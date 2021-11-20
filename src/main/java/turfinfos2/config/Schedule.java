//package turfinfos2.config;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import turfinfos2.model.Result;
//import turfinfos2.model.TurfInfos;
//import turfinfos2.repository.ResultRepository;
//import turfinfos2.repository.TurfInfosRepository;
//
//@Configuration
//@EnableScheduling
//public class Schedule {
//	
//	@Autowired
//	ResultRepository resultRepository;
//	
//	@Autowired
//	TurfInfosRepository turfInfosRepository;
//
//	@Scheduled(fixedDelay = 10000)
//	public void scheduleFixedDelayTask() {
//		
//		Result result = new Result();
//		result.setCoupleGagnant(444d);
//		resultRepository.save(result);
//		
//	    System.out.println("done");
//	}
//	
//	public void createRapportsInfosFromPMUJson(String jour) throws ParseException {
//		
//		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat format2 = new SimpleDateFormat("ddMMyyyy");
//		
//		Date date2 = format1.parse(jour);
//	    String finalDate = format2.format(date2).toString();
//		
//		String firstPartOfUrl = "https://offline.turfinfo.api.pmu.fr/rest/client/1/programme/";
//		String day = finalDate;
//		String RC;
//		String finalPartOfUrl = "rapports-definitifs";
//
//		
//
//		List<TurfInfos> allByJour = turfInfosRepository.findAllByJour(jour);
//		Map<String, Set<Integer>> reunionsAndRaces = new HashMap<>();
//		
//	    Set<String> distinctReunions = allByJour.stream()
// 				.filter(ti-> ti.getJour().equals(jour) && ti.getIsPremium().equals(true) && ti.getR().length()<3)
//        			.map(TurfInfos :: getR)
//        			.collect(Collectors.toSet());
//	    
//	    distinctReunions.forEach(r-> {
//	    	reunionsAndRaces.put(r, turfInfosRepository.findAllByJourAndByReunion(jour, r).stream()
//// 				.filter(ti-> ti.getJour().equals(jour))
//        			.map(TurfInfos :: getC)
//        			.collect(Collectors.toSet()));
//	    });
//
//	    for(Entry<String, Set<Integer>> entry : reunionsAndRaces.entrySet()) {
//	    	
//	    	for(Integer race : (Set<Integer>) entry.getValue()) {
//	    		
//	    		String url = firstPartOfUrl + day + "/R" + entry.getKey() + "/C" + race + "/" +finalPartOfUrl;
//	    		System.out.println(url);
//				try {
//					JsonNode node;
//					node = new ObjectMapper().readTree(new URL(url));
//					
//					if(node.isMissingNode() == false) {
//						
//					System.out.println(node.isMissingNode());
//					
//					allByJour.forEach(ti-> {
//						if(ti.getR().equals(entry.getKey()) && ti.getC().equals(race) && ti.getRanking().equals(1)) {
//							ti.setLiveOddPlace(node.get(1).get("rapports").get(0).get("dividendePourUnEuro").doubleValue()/100);
//							turfInfosRepository.save(ti);
//
//						}
//						if(ti.getR().equals(entry.getKey()) && ti.getC().equals(race) && ti.getRanking().equals(2)) {
//						    System.out.println(ti.getRanking() +"rrr" + ti.getR() + "C" + race);
//							ti.setLiveOddPlace(node.get(1).get("rapports").get(1).get("dividendePourUnEuro").doubleValue()/100);
//							turfInfosRepository.save(ti);
//
//						}
//						if(ti.getR().equals(entry.getKey()) && ti.getC().equals(race) && ti.getRanking().equals(3) && node.get(1).get("rapports").size()>2) {
//							ti.setLiveOddPlace(node.get(1).get("rapports").get(2).get("dividendePourUnEuro").doubleValue()/100);
//							turfInfosRepository.save(ti);
//						}
//						
//					});
//				}
//					
//				
//					
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//	    	}
//	    }
//	    
//
//	    
//		System.out.println("STOP");
//		
//	}
//}
