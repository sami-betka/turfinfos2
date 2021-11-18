package turfinfos2.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
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

	public List<TurfInfos> createAllRaceInfosFromParisTurfJson(String url) {

		List<TurfInfos> fromRace = new ArrayList<>();
	    List<Integer> allNumCourses = turfInfosRepository.findAll().stream()
				.map(TurfInfos :: getNumcourse)
				.collect(Collectors.toList());

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
				String uuid = node.get("pageProps").get("race").get("uuid").textValue();

				System.out.println(uuid + "     nnnnnnnnnnnnnnnn");
				//Verifie si les stats sont à null
            	boolean nulStats = true;
				
//	            Lister les ids des réunions
//				  Et infos réunions
            	Integer numberofRunners = null;
            	if(node.get("pageProps").get("race").get("numberOfInitialRunners") != null) {
				numberofRunners = Integer.valueOf(node.get("pageProps").get("race").get("numberOfInitialRunners").textValue());
            	}else {
    				numberofRunners = node.get("pageProps").get("race").get("numberOfRunners").intValue();
//				numberofRunners = node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).size();
            	}
            	
            
            		System.out.println(numberofRunners + "" + numcourse);
                    
            		
	            for(int i= 0; i<numberofRunners; i++) {
	            	TurfInfos turfInfo = new TurfInfos();
	            	turfInfo.setJour(jour);
	            	if(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("pmuNumber").intValue() != 0) {
	                	turfInfo.setR(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("pmuNumber").intValue()));	       
	                	
	                	turfInfo.setC(node.get("pageProps").get("initialState").get("currentPage").get("race").get("number").intValue());
	                	turfInfo.setRaceSpecialty(node.get("pageProps").get("race").get("specialty").textValue());

	                	turfInfo.setTableId(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("id").textValue());
	                	turfInfo.setEntraineur(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("trainerName").textValue());
	                    turfInfo.setNumcourse(Integer.valueOf(numcourse));
	                    turfInfo.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("saddle").intValue());
	                    
	                    turfInfo.setBlinkersFirstTime(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("blinkersFirstTime").booleanValue());
	                    turfInfo.setNoShoesFirstTime(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("noShoesFirstTime").booleanValue());
	                    turfInfo.setProtectionFirstTime(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("protectionFirstTime").booleanValue());

	                    turfInfo.setCl("");
//	                	turfInfosMetting.setRecence(null);
//	                    turfInfosMetting.setTypec(node.get("pageProps").get("race").get("discipline").textValue());
	                   if(!node.get("pageProps").get("initialState").get("currentPage").get("stats").isEmpty()) {
	                	   nulStats = false;
		                turfInfo.setLibel_hippo(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("libel_hippo").textValue());

	                	turfInfo.setCoursescheval(String.valueOf(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("numberOfRuns").intValue()));
	                	turfInfo.setPourcPlaceCheval(100 *node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorse").get("inFirst3Rate").doubleValue());

		                turfInfo.setNbrCourseChevalHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("runs").intValue()));
	                	turfInfo.setChevalTwoOrThreeHippo(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("ranks2or3").intValue());
	                	turfInfo.setNbVictChevalHippo(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("wins").intValue());

	                	turfInfo.setPourcVictChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("winRate").doubleValue());
	                    turfInfo.setPourcPlaceChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfo.setNbrCourseJockHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse").get("runs").intValue()));
	                    turfInfo.setPourcVictJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse").get("winRate").doubleValue());
	                    turfInfo.setPourcPlaceJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse").get("inFirst3Rate").doubleValue());

	                	turfInfo.setNbrCourseEntHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("runs").intValue()));
	                    turfInfo.setPourcVictEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("winRate").doubleValue());
	                    turfInfo.setPourcPlaceEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfo.setNbCourseCouple(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("runs").intValue());
	                    turfInfo.setNbVictCouple(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("wins").intValue());
	               	    turfInfo.setCoupleTwoOrThree(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("ranks2or3").intValue());

	                    turfInfo.setTxVictCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("winRate").doubleValue());
	                    turfInfo.setTxPlaceCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("inFirst3Rate").doubleValue());
//	                    turfInfosMetting.setNbCourseCoupleHippo();

//	                    turfInfosMetting.setTxVictCoupleHippo(null);
//	                    turfInfosMetting.setTxPlaceCoupleHippo(null);
	                   }
	                   ///////nulstats end
	                   
	                   
//	                	turfInfosMetting.setNbrCourseChevalHippo(numcourse)
//	                	turfInfosMetting.setNbCourseCouple(numcourse)
	                   
	                   if(turfInfo.getRaceSpecialty().equals("A") || turfInfo.getRaceSpecialty().equals("M")) {
	                	   if(turfInfo.getRaceSpecialty().equals(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("records").get("distance").get("specialty").textValue())) {
			                   turfInfo.setDistanceAndSpecialtyChrono(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("records").get("distance").get("redkm").textValue());
	                	   }
	                   }
	                   
	                	turfInfo.setRanking(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("ranking").intValue());
		                turfInfo.setLiveOdd(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("operatorOdds").get("PMU").get("liveOdd").doubleValue());
		                turfInfo.setIsRunning(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("isRunning").booleanValue());
		                turfInfo.setIsTQQ(node.get("pageProps").get("race").get("isTQQ").booleanValue());
		                turfInfo.setNumberOfInitialRunners(numberofRunners);
	                	turfInfo.setDraw(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("draw").intValue());


		                if(node.get("pageProps").get("race").get("operatorBetTypes") != null && node.get("pageProps").get("race").get("operatorBetTypes").size() >0 && node.get("pageProps").get("race").get("operatorBetTypes").get("PMU") != null && node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").size() >0) {
			                turfInfo.setHasBetTypes(true);

			                List<String>strs = new ArrayList<>();
			                for(int j=0; j< node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").size(); j++) {
			                	
			                	strs.add(node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").get(j).textValue());
			                }
			                for(String str : strs) {
				                turfInfo.setIsPick5(false);
			                	if(str != null && str.equals("PK5")) {
					                turfInfo.setIsPick5(true);
			                	}
			                }

			                }
		                else {
				                turfInfo.setIsPick5(false);
				                turfInfo.setHasBetTypes(false);

			                }
		                
		                turfInfo.setCaraList1(node.get("pageProps").get("race").get("caraList1").textValue());
		                turfInfo.setCaraList2(node.get("pageProps").get("race").get("caraList2").textValue());
		                
		                turfInfo.setIsPremium(node.get("pageProps").get("race").get("isPremium").booleanValue());



	            	
	            	}else {
	                	turfInfo.setR(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("name").textValue());
	                	
	                	turfInfo.setC(node.get("pageProps").get("initialState").get("currentPage").get("race").get("number").intValue());
	                	turfInfo.setRaceSpecialty(node.get("pageProps").get("race").get("specialty").textValue());
	                	turfInfo.setTableId(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("id").textValue());
	                	turfInfo.setEntraineur(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("trainerName").textValue());
	                    turfInfo.setNumcourse(Integer.valueOf(numcourse));
	                    turfInfo.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("saddle").intValue());
	                    
	                    turfInfo.setBlinkersFirstTime(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("blinkersFirstTime").booleanValue());
	                    turfInfo.setNoShoesFirstTime(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("noShoesFirstTime").booleanValue());
	                    turfInfo.setProtectionFirstTime(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("protectionFirstTime").booleanValue());
	                    turfInfo.setCl("");
//	                	turfInfosMetting.setRecence(null);
//	                    turfInfosMetting.setTypec(node.get("pageProps").get("race").get("discipline").textValue());
		               if(!node.get("pageProps").get("initialState").get("currentPage").get("stats").isEmpty()) {
	                	   nulStats = false;
			            turfInfo.setLibel_hippo(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("libel_hippo").textValue());

	                	turfInfo.setCoursescheval(String.valueOf(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("numberOfRuns").intValue()));
	                	turfInfo.setPourcPlaceCheval(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorse").get("inFirst3Rate").doubleValue());

	                	turfInfo.setNbrCourseChevalHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("runs").intValue()));
	                	turfInfo.setChevalTwoOrThreeHippo(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("ranks2or3").intValue());
	                	turfInfo.setNbVictChevalHippo(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("wins").intValue());
	                	turfInfo.setPourcVictChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("winRate").doubleValue());
	                    turfInfo.setPourcPlaceChevalHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfo.setNbrCourseJockHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse").get("runs").intValue()));
	                    turfInfo.setPourcVictJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse").get("winRate").doubleValue());
	                    turfInfo.setPourcPlaceJockHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse").get("inFirst3Rate").doubleValue());

	                	turfInfo.setNbrCourseEntHippo(String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("runs").intValue()));
	                    turfInfo.setPourcVictEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("winRate").doubleValue());
	                    turfInfo.setPourcPlaceEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("inFirst3Rate").doubleValue());
	                    
	                    turfInfo.setNbCourseCouple(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("runs").intValue());
	                    turfInfo.setNbVictCouple(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("wins").intValue());
	                    turfInfo.setCoupleTwoOrThree(node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("ranks2or3").intValue());

//	                    turfInfosMetting.setNbCourseCoupleHippo(numcourse);
	                    turfInfo.setTxVictCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("winRate").doubleValue());
	                    turfInfo.setTxPlaceCouple(100 * node.get("pageProps").get("initialState").get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("inFirst3Rate").doubleValue());
//	                    turfInfosMetting.setTxVictCoupleHippo(null);
//	                    turfInfosMetting.setTxPlaceCoupleHippo(null);
		               }
//	                	turfInfosMetting.setNbrCourseChevalHippo(numcourse)
//	                	turfInfosMetting.setNbCourseCouple(numcourse)
		               
		               if(turfInfo.getRaceSpecialty().equals("A") || turfInfo.getRaceSpecialty().equals("M")) {
	                	   if(turfInfo.getRaceSpecialty().equals(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("records").get("distance").get("specialty").textValue())) {
			                   turfInfo.setDistanceAndSpecialtyChrono(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("records").get("distance").get("redkm").textValue());
	                	   }
	                   }
		               
	                	turfInfo.setRanking(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("ranking").intValue());
		                turfInfo.setLiveOdd(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("operatorOdds").get("PMU").get("liveOdd").doubleValue());
		                turfInfo.setIsRunning(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("isRunning").booleanValue());
		                turfInfo.setIsTQQ(node.get("pageProps").get("race").get("isTQQ").booleanValue());
		                turfInfo.setNumberOfInitialRunners(numberofRunners);
	                	turfInfo.setDraw(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("draw").intValue());


		                if(node.get("pageProps").get("race").get("operatorBetTypes") != null && node.get("pageProps").get("race").get("operatorBetTypes").size() >0 && node.get("pageProps").get("race").get("operatorBetTypes").get("PMU") != null && node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").size() >0) {
			                turfInfo.setHasBetTypes(true);

			                List<String>strs = new ArrayList<>();
			                for(int j=0; j< node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").size(); j++) {
			                	
			                	strs.add(node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").get(j).textValue());
			                }
			                for(String str : strs) {
				                turfInfo.setIsPick5(false);
			                	if(str != null && str.equals("PK5")) {
					                turfInfo.setIsPick5(true);
			                	}
			                }
			                } else {
				                turfInfo.setIsPick5(false);
				                turfInfo.setHasBetTypes(false);

			                }

		                turfInfo.setCaraList1(node.get("pageProps").get("race").get("caraList1").textValue());
		                turfInfo.setCaraList2(node.get("pageProps").get("race").get("caraList2").textValue());
		                
		                turfInfo.setIsPremium(node.get("pageProps").get("race").get("isPremium").booleanValue());


	            	
	            	}
	            	
//	                System.out.println(iter);
//	                System.out.println("R" + turfInfo.getR() + "C" + turfInfo.getC() );

	                
	            	turfInfoService.setMadeUpParams(turfInfo);
                	if(!allNumCourses.contains(turfInfo.getNumcourse())) {
                	turfInfosRepository.save(turfInfo);
                	}
                	if(allNumCourses.contains(turfInfo.getNumcourse())) {
                		turfInfoService.updateFromParisTurfJSON(turfInfo, nulStats);
                    	}	           
                	
                	
                	
    	            //For LOOP END
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

	public void createAllDayInfosFromParisTurfJson(String url, Boolean isUpdate) {
				
		String firstPartOfUrl = "https://www.paris-turf.com/_next/data/";
		String parisTurfId = "d7lsdp4kqsZsUGSJ5gBnM";
		String thirdPartOfUrl = "/fr/course/-idc-";
		String raceUuid = "";
		String extension = ".json";

		JsonNode node;
		int iter = 0;
		
	    try {
			node = new ObjectMapper().readTree(new URL(url));
			String jour = node.get("pageProps").get("date").textValue();
			Integer numberofRaces = node.get("pageProps").get("initialState").get("racecards").get("races").get(jour).size();
			List<String> uuids = new ArrayList<>();
			
			for(int i= 0; i<numberofRaces; i++) {
				
				if(node.get("pageProps").get("initialState").get("racecards").get("races").get(jour).get(i).get("isPremium").booleanValue() == true) {
					String uuid = node.get("pageProps").get("initialState").get("racecards").get("races").get(jour).get(i).get("uuid").textValue();
					uuids.add(uuid);
				}
			}
			for(String uuid: uuids) {
				raceUuid = uuid;
				String raceUrl = firstPartOfUrl + parisTurfId + thirdPartOfUrl + raceUuid + extension;
				if(isUpdate.equals(false)) {
					createAllRaceInfosFromParisTurfJson(raceUrl);
				}
				if(isUpdate.equals(true)) {
					updateAllRaceInfosFromParisTurfJson(raceUrl);
				}
				iter+=1;
				System.out.println(iter + " / " + uuids.size());
			}

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("STOP");
		
	}
	
	public void createRapportsInfosFromPMUJson(String jour) throws ParseException {
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("ddMMyyyy");
		
			Date date2 = format1.parse(jour);
			System.out.println(format2.format(date2).toString());
		
		String firstPartOfUrl = "https://offline.turfinfo.api.pmu.fr/rest/client/1/programme/";
		String day = format2.format(date2).toString();
		String RC;
		String finalPartOfUrl = "rapports-definitifs";

		

		List<TurfInfos> allByJour = turfInfosRepository.findAllByJour(jour);
		Map<String, Set<Integer>> reunionsAndRaces = new HashMap<>();
		
	    Set<String> distinctReunions = allByJour.stream()
 				.filter(ti-> ti.getJour().equals(jour))
        			.map(TurfInfos :: getR)
        			.collect(Collectors.toSet());
	    
	    distinctReunions.forEach(r-> {
	    	reunionsAndRaces.put(r, turfInfosRepository.findAllByJourAndByReunion(jour, r).stream()
// 				.filter(ti-> ti.getJour().equals(jour))
        			.map(TurfInfos :: getC)
        			.collect(Collectors.toSet()));
	    });

	    for(Entry<String, Set<Integer>> entry : reunionsAndRaces.entrySet()) {
	    	
	    	for(Integer race : (Set<Integer>) entry.getValue()) {
	    		
	    		String url = firstPartOfUrl + day + "/R" + entry.getKey() + "/C" + race + "/" +finalPartOfUrl;
	    		System.out.println(url);
				try {
					JsonNode node;
					node = new ObjectMapper().readTree(new URL(url));
					
					if(node.isMissingNode() == false) {
						
					System.out.println(node.isMissingNode());
					
					allByJour.forEach(ti-> {
						if(ti.getR().equals(entry.getKey()) && ti.getC().equals(race) && ti.getRanking().equals(1)) {
							ti.setLiveOddPlace(node.get(1).get("rapports").get(0).get("dividendePourUnEuro").doubleValue()/100);
							turfInfosRepository.save(ti);
//							System.out.println(ti.getLiveOddPlace());

							
						}
						if(ti.getR().equals(entry.getKey()) && ti.getC().equals(race) && ti.getRanking().equals(2)) {
						    System.out.println(ti.getRanking() +"rrr" + ti.getR() + "C" + race);
							ti.setLiveOddPlace(node.get(1).get("rapports").get(1).get("dividendePourUnEuro").doubleValue()/100);
							turfInfosRepository.save(ti);
//							System.out.println(ti.getLiveOddPlace());


						}
						if(ti.getR().equals(entry.getKey()) && ti.getC().equals(race) && ti.getRanking().equals(3) && node.get(1).get("rapports").size()>2) {
							ti.setLiveOddPlace(node.get(1).get("rapports").get(2).get("dividendePourUnEuro").doubleValue()/100);
							turfInfosRepository.save(ti);
//							System.out.println(ti.getLiveOddPlace());


						}
						
					});
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
	    

	    

		System.out.println("STOP");
		
	}
	
	public void updateAllRaceInfosFromParisTurfJson(String url) {
		
    	List<TurfInfos> all = turfInfosRepository.findAll();

	    List<Integer> allNumCourses = all.stream()
				.map(TurfInfos :: getNumcourse)
				.collect(Collectors.toList());


		JsonNode node;

				try {
//				node = new ObjectMapper().readTree(file);
			    node = new ObjectMapper().readTree(new URL(url));

	            //Jour
				String jour = node.get("pageProps").get("race").get("date").textValue();
				//numcourse
				String numcourse = String.valueOf(node.get("pageProps").get("race").get("id").intValue());
				String uuid = node.get("pageProps").get("race").get("uuid").textValue();

				System.out.println(uuid + "     nnnnnnnnnnnnnnnn");
				//Verifie si les stats sont à null
            	boolean nulStats = true;
				
//	            Lister les ids des réunions
//				  Et infos réunions
            	Integer numberofRunners = null;
            	if(node.get("pageProps").get("race").get("numberOfInitialRunners") != null) {
				numberofRunners = Integer.valueOf(node.get("pageProps").get("race").get("numberOfInitialRunners").textValue());
            	}else {
    				numberofRunners = node.get("pageProps").get("race").get("numberOfRunners").intValue();
//				numberofRunners = node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).size();
            	}
            	
            
            		System.out.println(numberofRunners + "" + numcourse);
    
            		/////////////Début///////////////
            		
//	            	if(!allNumCourses.contains(numcourse)) {
//	                    for(int i= 0; i<numberofRunners; i++) {
//	    	            	TurfInfos turfInfo = new TurfInfos();
//	    	            	turfInfo.setNumcourse(Integer.valueOf(numcourse));
//		                    turfInfo.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("saddle").intValue());
//	    	            	turfInfo.setRanking(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("ranking").intValue());
//	    	                turfInfo.setLiveOdd(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("operatorOdds").get("PMU").get("liveOdd").doubleValue());	                	
//	    	          
////	    	                TurfInfos infoToUpdate = all.stream()
////	    	           				.filter(ti-> ti.getNumero().equals(turfInfo.getNumero()) && ti.getNumcourse().equals(turfInfo.getNumcourse()))
////	    	           				.findFirst().get();
//	    	                
//	    	                	turfInfosRepository.save(turfInfo);
//
//	        	            //For LOOP END
//	    	            }
//	            	}
	            	
	            	if(allNumCourses.contains(Integer.valueOf(numcourse))) {
	            		   for(int i= 0; i<numberofRunners; i++) {
		    	            	TurfInfos turfInfo = new TurfInfos();
		    	            	turfInfo.setNumcourse(Integer.valueOf(numcourse));
			                    turfInfo.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("saddle").intValue());
		    	            	turfInfo.setRanking(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("ranking").intValue());
		    	                turfInfo.setLiveOdd(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("operatorOdds").get("PMU").get("liveOdd").doubleValue());	                	
		    	          
		    	                TurfInfos infoToUpdate = all.stream()
		    	           				.filter(ti-> ti.getNumero().equals(turfInfo.getNumero()) && ti.getNumcourse().equals(turfInfo.getNumcourse()))
		    	           				.findFirst().get();
		    	                
    	                		turfInfoService.updateRankingsFromParisTurfJSON(turfInfo, infoToUpdate);

		        	            //For LOOP END
		    	            }                    	
	            		   }
            		
	  
	                               

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//				catch (NullPointerException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			
	
	
	}
	
	public void updateAllDayInfosFromAspiJson(String url) {
		
		JsonNode node;
    	List<TurfInfos> all = turfInfosRepository.findAll();
    	
 	   List<Integer> allNumCourses = all.stream()
				.map(TurfInfos :: getNumcourse)
				.collect(Collectors.toList());

		
	    try {
			node = new ObjectMapper().readTree(new URL(url));
			
			Integer numberofRunners = node.size();

            System.out.println(" nnn" +numberofRunners);

            List<TurfInfos> toRace = new ArrayList<>();
            
            for(int i= 0; i<numberofRunners; i++) {
            	TurfInfos turfInfo = new TurfInfos();
            	
            	turfInfo.setNumcourse(node.get(i).get("numcourse").get("id").intValue());
                System.out.println(" numcccc" +turfInfo.getNumcourse());
            	turfInfo.setNumero(node.get(i).get("numero").intValue());
                System.out.println(" numerrrr" +turfInfo.getNumero());


            	turfInfo.setRecence(node.get(i).get("recence").intValue());
        		System.out.println(i);

            	   
            	   Optional<TurfInfos> optInfoToUpdate = all.stream()
           				.filter(ti-> ti.getNumero().equals(turfInfo.getNumero()) && ti.getNumcourse().equals(turfInfo.getNumcourse()) && ti.getIsPremium().equals(true))
           				.findFirst();
            	   
            	
//                	if(!allNumCourses.contains(turfInfo.getNumcourse())) {
//                	turfInfosRepository.save(turfInfo);
//                	}
                	if(optInfoToUpdate.isPresent()) {
                		TurfInfos infoToUpdate = optInfoToUpdate.get();
                		turfInfoService.updateFromAspiJSON(turfInfo, infoToUpdate);
                    	}
                
                
            	
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
