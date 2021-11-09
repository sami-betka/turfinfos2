package turfinfos2.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;

@Service
public class ImportJSONService {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	TurfInfoService turfInfoService;

	public List<TurfInfos> createAllRaceInfosFromJson(String url) {

		List<TurfInfos> fromRace = new ArrayList<>();

//		File file = new File("src/main/resources/static/uploadedfiles/one.json");

		JsonNode node;
//		TurfInfos infoToCreate = new TurfInfos();

				try {
//				node = new ObjectMapper().readTree(file);
			    node = new ObjectMapper().readTree(new URL(url));

	            //Jour
				String jour = node.get("pageProps").get("race").get("date").textValue();
				//numcourse
				String numcourse = String.valueOf(node.get("pageProps").get("race").get("id").intValue());
				
//	            Lister les ids des réunions
//				  Et infos réunions
//				String numberofRunners = node.get("pageProps").get("race").get("numberOfInitialRunners").textValue();
				Integer numberofRunners = node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).size();

	            System.out.println(" nnn" +numberofRunners);

	            List<TurfInfos> byRace = new ArrayList<>();
	            
	            for(int i= 0; i<numberofRunners; i++) {
	            	TurfInfos turfInfosMetting = new TurfInfos();
	            	turfInfosMetting.setJour(jour);
	            	if(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("pmuNumber").intValue() != 0) {
	                	turfInfosMetting.setR(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("pmuNumber").intValue()));
	       
	                	
	                	turfInfosMetting.setC(node.get("pageProps").get("initialState").get("currentPage").get("race").get("number").intValue());
	                	turfInfosMetting.setTableId(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("id").textValue());
	                	turfInfosMetting.setEntraineur(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("trainerName").textValue());
	                    turfInfosMetting.setNumcourse(Integer.valueOf(numcourse));
	                    turfInfosMetting.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("saddle").intValue());
	                    turfInfosMetting.setCl("");
	                    //	                	turfInfosMetting.setRecence(null);
//	                    turfInfosMetting.setTypec(node.get("pageProps").get("race").get("discipline").textValue());
	                    
//	                	turfInfosMetting.setCoursescheval(String.valueOf(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("numberOfRuns").intValue()));
	                    turfInfosMetting.setNbrCourseChevalHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsHorseRacecourse").get("runs").intValue()));
	                    turfInfosMetting.setPourcVictChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsHorseRacecourse").get("winRate").doubleValue());
	                    turfInfosMetting.setPourcPlaceChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsHorseRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfosMetting.setNbrCourseJockHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyRacecourse").get("runs").intValue()));
	                    turfInfosMetting.setPourcVictJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyRacecourse").get("winRate").doubleValue());
	                    turfInfosMetting.setPourcPlaceJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyRacecourse").get("inFirst3Rate").doubleValue());

	                	turfInfosMetting.setNbrCourseEntHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsTrainerRacecourse").get("runs").intValue()));
	                    turfInfosMetting.setPourcVictEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsTrainerRacecourse").get("winRate").doubleValue());
	                    turfInfosMetting.setPourcPlaceEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsTrainerRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfosMetting.setNbCourseCouple(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyHorse").get("runs").intValue()));
//	                    turfInfosMetting.setNbCourseCoupleHippo(numcourse);
	                    turfInfosMetting.setTxVictCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyHorse").get("winRate").doubleValue());
	                    turfInfosMetting.setTxPlaceCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyHorse").get("inFirst3Rate").doubleValue());
//	                    turfInfosMetting.setTxVictCoupleHippo(null);
//	                    turfInfosMetting.setTxPlaceCoupleHippo(null);

//	                	turfInfosMetting.setNbrCourseChevalHippo(numcourse)
//	                	turfInfosMetting.setNbCourseCouple(numcourse)
	                	

	            	
	            	}else {
	                	turfInfosMetting.setR(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("name").textValue());
	                	
	                	turfInfosMetting.setC(node.get("pageProps").get("initialState").get("currentPage").get("race").get("number").intValue());
	                	turfInfosMetting.setTableId(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("id").textValue());
	                	turfInfosMetting.setEntraineur(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("trainerName").textValue());
	                    turfInfosMetting.setNumcourse(Integer.valueOf(numcourse));
	                    turfInfosMetting.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("saddle").intValue());
	                    turfInfosMetting.setCl("");
	                    //	                	turfInfosMetting.setRecence(null);
//	                    turfInfosMetting.setTypec(node.get("pageProps").get("race").get("discipline").textValue());
	                    
//	                	turfInfosMetting.setCoursescheval(String.valueOf(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("numberOfRuns").intValue()));
	                    turfInfosMetting.setNbrCourseChevalHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsHorseRacecourse").get("runs").intValue()));
	                    turfInfosMetting.setPourcVictChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsHorseRacecourse").get("winRate").doubleValue());
	                    turfInfosMetting.setPourcPlaceChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsHorseRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfosMetting.setNbrCourseJockHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyRacecourse").get("runs").intValue()));
	                    turfInfosMetting.setPourcVictJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyRacecourse").get("winRate").doubleValue());
	                    turfInfosMetting.setPourcPlaceJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyRacecourse").get("inFirst3Rate").doubleValue());

	                	turfInfosMetting.setNbrCourseEntHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsTrainerRacecourse").get("runs").intValue()));
	                    turfInfosMetting.setPourcVictEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsTrainerRacecourse").get("winRate").doubleValue());
	                    turfInfosMetting.setPourcPlaceEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsTrainerRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfosMetting.setNbCourseCouple(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyHorse").get("runs").intValue()));
//	                    turfInfosMetting.setNbCourseCoupleHippo(numcourse);
	                    turfInfosMetting.setTxVictCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyHorse").get("winRate").doubleValue());
	                    turfInfosMetting.setTxPlaceCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfosMetting.getTableId()).get("statsJockeyHorse").get("inFirst3Rate").doubleValue());
//	                    turfInfosMetting.setTxVictCoupleHippo(null);
//	                    turfInfosMetting.setTxPlaceCoupleHippo(null);

//	                	turfInfosMetting.setNbrCourseChevalHippo(numcourse)
//	                	turfInfosMetting.setNbCourseCouple(numcourse)
	                	

	            	
	            	}
	            	
	            	byRace.add(turfInfosMetting);
	            }            
	            
	            //test affichage
	            for(TurfInfos inf: byRace) {
	                System.out.println("R" + inf.getR() + "C" + inf.getC() );
//	                System.out.println(inf.getTableId());
////	                System.out.println(inf.getCoursescheval());
//	                System.out.println(inf.getEntraineur());
	                System.out.println(inf.getNumcourse());
	                System.out.println(inf.getNumero());
////	                System.out.println(inf.getTypec());
//	                System.out.println(inf.getNbrCourseChevalHippo());
//	                System.out.println(inf.getPourcVictChevalHippo());
//	                System.out.println(inf.getPourcPlaceChevalHippo());
//	                System.out.println(inf.getNbrCourseJockHippo());
//	                System.out.println(inf.getPourcVictJockHippo());
//	                System.out.println(inf.getPourcPlaceJockHippo());
//	                System.out.println(inf.getNbrCourseEntHippo());
//	                System.out.println(inf.getPourcVictEntHippo());
//	                System.out.println(inf.getPourcPlaceEntHippo());
//	                System.out.println(inf.getNbCourseCouple());
//	                System.out.println(inf.getTxVictCouple());
//	                System.out.println(inf.getTxPlaceCouple());
//
//	                System.out.println();
	            }
	            
	            List<Integer> allNumCourses = turfInfosRepository.findAll().stream()
        				.map(TurfInfos :: getNumcourse)
        				.collect(Collectors.toList());
	            
	            for(TurfInfos info : byRace) {
                	if(!allNumCourses.contains(info.getNumcourse())) {
                	turfInfosRepository.save(info);
                	}
                	if(allNumCourses.contains(info.getNumcourse())) {
                		turfInfoService.updateFromJSON(info);
                    	}
                }	 
	            

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		System.out.println(fromRace.size());
		return fromRace;
	}

	public void createAllDayInfosFromJson(String url) {
		
		String firstPartOfUrl = "https://www.paris-turf.com/_next/data/";
		String parisTurfId = "3ZY_4rMcpwzYwArtQJLKf";
		String thirdPartOfUrl = "/fr/course/-idc-";
		String raceUuid = "";
		String extension = ".json";

		JsonNode node;
		
	    try {
			node = new ObjectMapper().readTree(new URL(url));
			String jour = node.get("pageProps").get("date").textValue();
			Integer numberofRaces = node.get("pageProps").get("initialState").get("racecards").get("races").get(jour).size();
			List<String> uuids = new ArrayList<>();
			
			for(int i= 0; i<numberofRaces; i++) {
				
				String uuid = node.get("pageProps").get("initialState").get("racecards").get("races").get(jour).get(i).get("uuid").textValue();
				uuids.add(uuid);
			}
			for(String uuid: uuids) {
				raceUuid = uuid;
				String raceUrl = firstPartOfUrl + parisTurfId + thirdPartOfUrl + raceUuid + extension;
				createAllRaceInfosFromJson(raceUrl);
			}

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}
	

}
