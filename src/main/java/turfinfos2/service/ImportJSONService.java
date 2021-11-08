package turfinfos2.service;

import java.io.File;
import java.io.IOException;
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

	public List<TurfInfos> createAllRaceInfosFromJson() {

		List<TurfInfos> fromRace = new ArrayList<>();

		File file = new File("src/main/resources/static/uploadedfiles/one.json");

		JsonNode node;
//		TurfInfos infoToCreate = new TurfInfos();

				try {
				node = new ObjectMapper().readTree(file);

	            //Jour
				String jour = node.get("pageProps").get("race").get("date").textValue();
				//numcourse
				String numcourse = String.valueOf(node.get("pageProps").get("race").get("id").intValue());
				
//	            Lister les ids des réunions
//				  Et infos réunions
				String numberofRunners = node.get("pageProps").get("race").get("numberOfInitialRunners").textValue();
	            System.out.println(" nnn" +numberofRunners);

	            List<TurfInfos> byRace = new ArrayList<>();
	            
	            for(int i= 0; i<Integer.valueOf(numberofRunners); i++) {
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
//	                System.out.println(inf.getNumero());
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
			
		
		return fromRace;
	}

	public List<TurfInfos> createAllDayInfosFromJson() {

		List<TurfInfos> list = new ArrayList<>();
		List<String> horsesIds = new ArrayList<>();

		File file = new File("src/main/resources/static/uploadedfiles/one.json");

		JsonNode productNode;
		TurfInfos infoToCreate = new TurfInfos();

		try {
			productNode = new ObjectMapper().readTree(file);

            //Jour
			String jour = productNode.get("pageProps").get("race").get("date").textValue();
			
//            Lister les ids des réunions
//			  Et infos réunions
			Integer numberofreunions = productNode.get("pageProps").get("initialState").get("racecards").get("meetings").get(jour).size();
            System.out.println(numberofreunions);

            List<TurfInfos> byReunions = new ArrayList<>();
            
            for(int i= 0; i<numberofreunions; i++) {
            	TurfInfos turfInfosMetting = new TurfInfos();
            	turfInfosMetting.setJour(jour);
            	if(productNode.get("pageProps").get("initialState").get("racecards").get("meetings").get(jour).get(i).get("pmuNumber").intValue() != 0) {
                	turfInfosMetting.setR(String.valueOf(productNode.get("pageProps").get("initialState").get("racecards").get("meetings").get(jour).get(i).get("pmuNumber").intValue()));
            	}else {
                	turfInfosMetting.setR(productNode.get("pageProps").get("initialState").get("racecards").get("meetings").get(jour).get(i).get("name").textValue());
            	}
            	
            	byReunions.add(turfInfosMetting);
            }            
            
            //test affichage
            for(TurfInfos inf: byReunions) {
                System.out.println(inf.getR());
            }
            
			
//			infoToCreate.setC(productNode.get("pageProps"));
//			infoToCreate.setCheval(productNode.get("pageProps"));
//			infoToCreate.setJour(productNode.get("pageProps"));
//			infoToCreate.setNumcourse(productNode.get("pageProps"));
//			infoToCreate.setNumero(productNode.get("pageProps"));
//			infoToCreate.setPourcPlaceChevalHippo(productNode.get("pageProps"));
//			infoToCreate.setPourcPlaceEntHippo(productNode.get("pageProps"));
//			infoToCreate.setPourcPlaceJockHippo(productNode.get("pageProps"));
//			infoToCreate.setPourcVictChevalHippo(productNode.get("pageProps"));
//			infoToCreate.setPourcVictEntHippo(productNode.get("pageProps"));
//			infoToCreate.setPourcVictJockHippo(productNode.get("pageProps"));
//			infoToCreate.setR(productNode.get("pageProps"));
//			infoToCreate.setRecence(productNode.get("pageProps"));
//			infoToCreate.setTxPlaceCouple(productNode.get("pageProps"));
//			infoToCreate.setTxPlaceCoupleHippo(productNode.get("pageProps"));
//			infoToCreate.setTxVictCouple(productNode.get("pageProps"));
//			infoToCreate.setTxVictCoupleHippo(productNode.get("pageProps"));
//
//			infoToCreate.setCoursescheval(productNode.get("pageProps"));
////	    	infoToUpdate.setCoursesentraineur(info.getCoursesentraineur());
////	    	infoToUpdate.setCoursesjockey(info.getCoursesjockey());
//			infoToCreate.setNbCourseCouple(productNode.get("pageProps"));
//			infoToCreate.setNbrCourseChevalHippo(productNode.get("pageProps"));
//			infoToCreate.setNbrCourseJockHippo(productNode.get("pageProps"));
//			infoToCreate.setNbrCourseEntHippo(productNode.get("pageProps"));
//			infoToCreate.setNbCourseCoupleHippo(productNode.get("pageProps"));
////	    	infoToUpdate.setTypec(info.getTypec());
//			infoToCreate.setEntraineur(productNode.get("pageProps"));
//			infoToCreate.setCl(productNode.get("pageProps"));
//			infoToCreate.setCotedirect(productNode.get("pageProps"));

//			System.out.println(infoToCreate.toString());

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<TurfInfos> populateHorseFromMeetings() {

		List<TurfInfos> list = new ArrayList<>();

		return list;
	}

	public List<TurfInfos> populateHorseFromRaces() {

		List<TurfInfos> list = new ArrayList<>();

		return list;
	}

	public List<TurfInfos> populateHorseFromRunners() {

		List<TurfInfos> list = new ArrayList<>();

		return list;
	}

	public List<TurfInfos> populateHorseFromStats() {

		List<TurfInfos> list = new ArrayList<>();

		return list;
	}
	

}
