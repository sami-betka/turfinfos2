package turfinfos2.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import turfinfos2.model.Result;
import turfinfos2.repository.CurrentOddsRepository;
import turfinfos2.repository.ResultRepository;
import turfinfos2.repository.TurfInfosRepository;

@Configuration
//@EnableScheduling
public class Schedule {
	
	@Autowired
	ResultRepository resultRepository;
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	CurrentOddsRepository currentOddsRepository;

//	@Scheduled(fixedDelay = 10000)
	public void scheduleFixedDelayTask() {
		
		Result result = new Result();
		result.setCoupleGagnant(444d);
		resultRepository.save(result);
		
	    System.out.println("done");
	    System.out.println(result.getCoupleGagnant());

	}
	
//	public void updateOddsFromPMUJson() throws ParseException {
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String jour = LocalDateTime.now().format(formatter);
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
//		String finalPartOfUrl = "rapports/SIMPLE_GAGNANT";
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
//		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("hh:mm:ss");
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
//				    Integer numberOfRunners = node.get("nbParticipants").intValue();
//					
//					CurrentOdds odds = new CurrentOdds(); 
//					
//					odds.setJour(jour);
//					odds.setR(entry.getKey());
//					odds.setC(race);
//					
//					for(int i=0; i<numberOfRunners; i++) {
//						odds.getOddsMap().put(node.get("rapportsParticipant").get(i).get("numPmu").intValue(), node.get("rapportsParticipant").get(i).get("rapportDirect").doubleValue());
//					}
//					odds.setTime(LocalDateTime.now().format(formatter2));
//
//					
//					currentOddsRepository.save(odds);
//
//
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
}
