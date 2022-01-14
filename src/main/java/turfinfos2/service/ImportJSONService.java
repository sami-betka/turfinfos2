package turfinfos2.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

import turfinfos2.model.Resultat;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ResultRepository;
import turfinfos2.repository.TurfInfosRepository;

@Service
public class ImportJSONService {

	@Autowired
	TurfInfosRepository turfInfosRepository;

	@Autowired
	TurfInfoService turfInfoService;

	@Autowired
	ResultService resultService;

	@Autowired
	ResultRepository resultRepository;

	public List<TurfInfos> createAllRaceInfosFromParisTurfJson(String url, List<TurfInfos> all) {

		List<TurfInfos> allToSave = new ArrayList<>();
		List<TurfInfos> allToUpdate = new ArrayList<>();

		List<TurfInfos> fromRace = new ArrayList<>();

//	    List<Integer> allNumCourses = all.stream()
//				.map(TurfInfos :: getNumcourse)
//				.collect(Collectors.toList());

		List<String> allTableIds = new ArrayList<>();
		allTableIds.addAll(all.stream().map(TurfInfos::getTableId).collect(Collectors.toList()));

//		File file = new File("src/main/resources/static/uploadedfiles/one.json");

		JsonNode node;
//		TurfInfos infoToCreate = new TurfInfos();

		try {
//				node = new ObjectMapper().readTree(file);
			node = new ObjectMapper().readTree(new URL(url));

			// Jour
			String jour = node.get("pageProps").get("race").get("date").textValue();
			// Heure
			String hour = node.get("pageProps").get("race").get("time").textValue();
			DateTimeFormatter format1 = DateTimeFormatter.ofPattern("H:mm:ss");
			DateTimeFormatter format2 = DateTimeFormatter.ofPattern("H");
			DateTimeFormatter format3 = DateTimeFormatter.ofPattern("mm");
			TemporalAccessor hour2 = format1.parse(hour);
			String hourParse1 = format2.format(hour2).toString();
			String hourParse2 = format3.format(hour2).toString();
			String finalHour = hourParse1 + "h" + hourParse2;

			// numcourse
			String numcourse = String.valueOf(node.get("pageProps").get("race").get("id").intValue());
			String uuid = node.get("pageProps").get("race").get("uuid").textValue();

			// Verifie si les stats sont à null
			boolean nulStats = true;

//	            Lister les ids des réunions
//				  Et infos réunions
			Integer numberofRunners = null;
			if (node.get("pageProps").get("race").get("numberOfInitialRunners") != null) {
				numberofRunners = Integer
						.valueOf(node.get("pageProps").get("race").get("numberOfInitialRunners").textValue());
			} else {
				numberofRunners = node.get("pageProps").get("race").get("numberOfRunners").intValue();
//				numberofRunners = node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).size();
			}

			for (int i = 0; i < numberofRunners; i++) {

//	            	Boolean isPremium = node.get("pageProps").get("race").get("isPremium").booleanValue();
//	            	
//	            	if(isPremium.equals(true)) {

				TurfInfos turfInfo = new TurfInfos();
				turfInfo.setJour(jour);
				turfInfo.setHour(finalHour);

				if (node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("pmuNumber")
						.intValue() != 0) {
					turfInfo.setR(String.valueOf(node.get("pageProps").get("initialState").get("currentPage")
							.get("meeting").get("pmuNumber").intValue()));
//                    turfInfo.setPronoFavoris(node.get("pageProps").get("initialState").get("currentPage").get("webTips").get("tips").get("A").get("horseIdList").textValue());
//                    turfInfo.setPronoChancesRegulieres(node.get("pageProps").get("initialState").get("currentPage").get("webTips").get("tips").get("C").get("horseIdList").textValue());

				
                    turfInfo.setCountry(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("country").textValue());
					turfInfo.setIsCanceled(node.get("pageProps").get("race").get("isCanceled").booleanValue());
//		                turfInfo.setIsFavori(node.get("pageProps"));

					turfInfo.setC(node.get("pageProps").get("initialState").get("currentPage").get("race").get("number")
							.intValue());
					turfInfo.setAutostart(node.get("pageProps").get("initialState").get("currentPage").get("race").get("autostart").booleanValue());
					turfInfo.setRaceSpecialty(node.get("pageProps").get("race").get("specialty").textValue());
					turfInfo.setSurface(node.get("pageProps").get("race").get("surface").textValue());

//					turfInfo.setHorseId(node.get("pageProps").get("initialState").get("racecards").get("runners")
//							.get(numcourse).get(i).get("horseId").intValue());
					turfInfo.setTableId(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("id").textValue());
					turfInfo.setEntraineur(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("trainerName").textValue());
					turfInfo.setJockey(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("jockeyName").textValue());
					turfInfo.setNumcourse(Integer.valueOf(numcourse));
					turfInfo.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("saddle").intValue());

//					turfInfo.setBlinkersFirstTime(node.get("pageProps").get("initialState").get("racecards")
//							.get("runners").get(numcourse).get(i).get("blinkersFirstTime").booleanValue());
//					turfInfo.setNoShoesFirstTime(node.get("pageProps").get("initialState").get("racecards")
//							.get("runners").get(numcourse).get(i).get("noShoesFirstTime").booleanValue());
//					turfInfo.setProtectionFirstTime(node.get("pageProps").get("initialState").get("racecards")
//							.get("runners").get(numcourse).get(i).get("protectionFirstTime").booleanValue());
//					turfInfo.setFormFigs(node.get("pageProps").get("initialState").get("racecards").get("runners")
//							.get(numcourse).get(i).get("formFigs").textValue());
					turfInfo.setAge(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("age").intValue());

					turfInfo.setCl("");

					turfInfo.setLibel_hippo(node.get("pageProps").get("initialState").get("currentPage")
							.get("meeting").get("name").textValue().toUpperCase());
//	                	turfInfosMetting.setRecence(null);
//	                    turfInfosMetting.setTypec(node.get("pageProps").get("race").get("discipline").textValue());
					if (!node.get("pageProps").get("initialState").get("currentPage").get("stats").isEmpty()) {
						turfInfo.setNulStats(false);

						turfInfo.setCoursescheval(
								String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsHorse").get("runs").intValue()));
						turfInfo.setPourcPlaceCheval(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsHorse").get("inFirst3Rate")
								.doubleValue());

						turfInfo.setNbrCourseChevalHippo(String.valueOf(node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse")
								.get("runs").intValue()));
						turfInfo.setChevalTwoOrThreeHippo(node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("ranks2or3")
								.intValue());
						turfInfo.setNbVictChevalHippo(
								node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsHorseRacecourse").get("wins").intValue());

						turfInfo.setPourcVictChevalHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse")
								.get("winRate").doubleValue());
						turfInfo.setPourcPlaceChevalHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse")
								.get("inFirst3Rate").doubleValue());

						turfInfo.setNbrCourseJockHippo(String.valueOf(node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse")
								.get("runs").intValue()));
						turfInfo.setPourcVictJockHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse")
								.get("winRate").doubleValue());
						turfInfo.setPourcPlaceJockHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse")
								.get("inFirst3Rate").doubleValue());

						turfInfo.setNbrCourseEntHippo(String.valueOf(node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId())
								.get("statsTrainerRacecourse").get("runs").intValue()));
						turfInfo.setPourcVictEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("winRate")
								.doubleValue());
						turfInfo.setPourcPlaceEntHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId())
								.get("statsTrainerRacecourse").get("inFirst3Rate").doubleValue());

						turfInfo.setNbCourseCouple(
								node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsJockeyHorse").get("runs").intValue());
						turfInfo.setNbVictCouple(
								node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsJockeyHorse").get("wins").intValue());
						turfInfo.setCoupleTwoOrThree(node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("ranks2or3")
								.intValue());

						turfInfo.setTxVictCouple(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("winRate")
								.doubleValue());
						turfInfo.setTxPlaceCouple(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("inFirst3Rate")
								.doubleValue());
						
					   if(turfInfo.getRaceSpecialty().equals("P") && turfInfo.getSurface().equals("PSF")) {
						   
							turfInfo.setNbCourseSurface(
									node.get("pageProps").get("initialState").get("currentPage").get("stats")
											.get(turfInfo.getTableId()).get("statsHorseSurface").get("runs").intValue());
							turfInfo.setPourcVictChevalSurface(100 * node.get("pageProps").get("initialState")
									.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseSurface")
									.get("winRate").doubleValue());
							turfInfo.setPourcPlaceChevalSurface(100 * node.get("pageProps").get("initialState")
									.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseSurface")
									.get("inFirst3Rate").doubleValue());
							turfInfo.setNbCourseSurfaceHippo(
									node.get("pageProps").get("initialState").get("currentPage").get("stats")
											.get(turfInfo.getTableId()).get("statsHorseRacecourseSurface").get("runs").intValue());
							turfInfo.setPourcVictChevalSurfaceHippo(100 * node.get("pageProps").get("initialState")
									.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourseSurface")
									.get("winRate").doubleValue());
							turfInfo.setPourcPlaceChevalSurfaceHippo(100 * node.get("pageProps").get("initialState")
									.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourseSurface")
									.get("inFirst3Rate").doubleValue());
							
					   }
						
					
//	                    turfInfosMetting.setNbCourseCoupleHippo();

//	                    turfInfosMetting.setTxVictCoupleHippo(null);
//	                    turfInfosMetting.setTxPlaceCoupleHippo(null);
					} else {
						turfInfo.setNulStats(true);
					}
					/////// nulstats end

					if (turfInfo.getRaceSpecialty().equals("A") || turfInfo.getRaceSpecialty().equals("M")) {
						if (turfInfo.getRaceSpecialty()
								.equals(node.get("pageProps").get("initialState").get("racecards").get("runners")
										.get(numcourse).get(i).get("records").get("distance").get("specialty")
										.textValue())) {
							turfInfo.setDistanceAndSpecialtyChrono(node.get("pageProps").get("initialState")
									.get("racecards").get("runners").get(numcourse).get(i).get("records")
									.get("distance").get("redkm").textValue());
						}
					}

					turfInfo.setRanking(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("ranking").intValue());
					turfInfo.setLiveOdd(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("operatorOdds").get("PMU").get("liveOdd").doubleValue());
					turfInfo.setIsRunning(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("isRunning").booleanValue());
					turfInfo.setIsTQQ(node.get("pageProps").get("race").get("isTQQ").booleanValue());
					turfInfo.setNumberOfInitialRunners(numberofRunners);
					turfInfo.setNumberOfNonRunners(
							node.get("pageProps").get("race").get("numberOfNonRunners").intValue());
					turfInfo.setDraw(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("draw").intValue());

					if (node.get("pageProps").get("race").get("operatorBetTypes") != null
							&& node.get("pageProps").get("race").get("operatorBetTypes").size() > 0
							&& node.get("pageProps").get("race").get("operatorBetTypes").get("PMU") != null
							&& node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").size() > 0) {
						turfInfo.setHasBetTypes(true);

						List<String> strs = new ArrayList<>();
						for (int j = 0; j < node.get("pageProps").get("race").get("operatorBetTypes").get("PMU")
								.size(); j++) {

							strs.add(node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").get(j)
									.textValue());
						}
						for (String str : strs) {
							turfInfo.setIsPick5(false);
							if (str != null && str.equals("PK5")) {
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

//		                turfInfo.setIsFavori(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("operatorOdds").get("PMU").get("favorite").booleanValue());

					//					turfInfo.setPicto(node.get("pageProps").get("initialState").get("racecards").get("runners")
//							.get(numcourse).get(i).get("operatorOdds").get("PMU").get("picto").textValue());
					turfInfo.setIsSupplemented(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("isSupplemented").booleanValue());

				} else {
					turfInfo.setR(node.get("pageProps").get("initialState").get("currentPage").get("meeting")
							.get("name").textValue());
//					  turfInfo.setPronoFavoris(node.get("pageProps").get("initialState").get("currentPage").get("webTips").get("tips").get("A").get("horseIdList").textValue());
//	                  turfInfo.setPronoChancesRegulieres(node.get("pageProps").get("initialState").get("currentPage").get("webTips").get("tips").get("C").get("horseIdList").textValue());
					turfInfo.setIsCanceled(node.get("pageProps").get("race").get("isCanceled").booleanValue());
//		                turfInfo.setIsFavori(null);
                    turfInfo.setCountry(node.get("pageProps").get("initialState").get("currentPage").get("meeting").get("country").textValue());

					

					turfInfo.setC(node.get("pageProps").get("initialState").get("currentPage").get("race").get("number")
							.intValue());
					turfInfo.setAutostart(node.get("pageProps").get("initialState").get("currentPage").get("race").get("autostart").booleanValue());

					turfInfo.setRaceSpecialty(node.get("pageProps").get("race").get("specialty").textValue());
					turfInfo.setSurface(node.get("pageProps").get("race").get("surface").textValue());


//					turfInfo.setHorseId(node.get("pageProps").get("initialState").get("racecards").get("runners")
//							.get(numcourse).get(i).get("horseId").intValue());
					turfInfo.setTableId(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("id").textValue());
					turfInfo.setEntraineur(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("trainerName").textValue());
					turfInfo.setJockey(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("jockeyName").textValue());
					turfInfo.setNumcourse(Integer.valueOf(numcourse));
					turfInfo.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("saddle").intValue());

//					turfInfo.setBlinkersFirstTime(node.get("pageProps").get("initialState").get("racecards")
//							.get("runners").get(numcourse).get(i).get("blinkersFirstTime").booleanValue());
//					turfInfo.setNoShoesFirstTime(node.get("pageProps").get("initialState").get("racecards")
//							.get("runners").get(numcourse).get(i).get("noShoesFirstTime").booleanValue());
//					turfInfo.setProtectionFirstTime(node.get("pageProps").get("initialState").get("racecards")
//							.get("runners").get(numcourse).get(i).get("protectionFirstTime").booleanValue());
//					turfInfo.setFormFigs(node.get("pageProps").get("initialState").get("racecards").get("runners")
//							.get(numcourse).get(i).get("formFigs").textValue());
//					turfInfo.setAge(node.get("pageProps").get("initialState").get("racecards").get("runners")
//							.get(numcourse).get(i).get("age").intValue());
					turfInfo.setLibel_hippo(node.get("pageProps").get("initialState").get("currentPage")
							.get("meeting").get("name").textValue().toUpperCase());
//	                	turfInfosMetting.setRecence(null);
//	                    turfInfosMetting.setTypec(node.get("pageProps").get("race").get("discipline").textValue());
					if (!node.get("pageProps").get("initialState").get("currentPage").get("stats").isEmpty()) {
						turfInfo.setNulStats(false);

						turfInfo.setCoursescheval(
								String.valueOf(node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsHorse").get("runs").intValue()));
						turfInfo.setPourcPlaceCheval(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsHorse").get("inFirst3Rate")
								.doubleValue());

						turfInfo.setNbrCourseChevalHippo(String.valueOf(node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse")
								.get("runs").intValue()));
						turfInfo.setChevalTwoOrThreeHippo(node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse").get("ranks2or3")
								.intValue());
						turfInfo.setNbVictChevalHippo(
								node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsHorseRacecourse").get("wins").intValue());
						turfInfo.setPourcVictChevalHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse")
								.get("winRate").doubleValue());
						turfInfo.setPourcPlaceChevalHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourse")
								.get("inFirst3Rate").doubleValue());

						turfInfo.setNbrCourseJockHippo(String.valueOf(node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse")
								.get("runs").intValue()));
						turfInfo.setPourcVictJockHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse")
								.get("winRate").doubleValue());
						turfInfo.setPourcPlaceJockHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsJockeyRacecourse")
								.get("inFirst3Rate").doubleValue());

						turfInfo.setNbrCourseEntHippo(String.valueOf(node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId())
								.get("statsTrainerRacecourse").get("runs").intValue()));
						turfInfo.setPourcVictEntHippo(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsTrainerRacecourse").get("winRate")
								.doubleValue());
						turfInfo.setPourcPlaceEntHippo(100 * node.get("pageProps").get("initialState")
								.get("currentPage").get("stats").get(turfInfo.getTableId())
								.get("statsTrainerRacecourse").get("inFirst3Rate").doubleValue());

						turfInfo.setNbCourseCouple(
								node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsJockeyHorse").get("runs").intValue());
						turfInfo.setNbVictCouple(
								node.get("pageProps").get("initialState").get("currentPage").get("stats")
										.get(turfInfo.getTableId()).get("statsJockeyHorse").get("wins").intValue());
						turfInfo.setCoupleTwoOrThree(node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("ranks2or3")
								.intValue());

//	                    turfInfosMetting.setNbCourseCoupleHippo(numcourse);
						turfInfo.setTxVictCouple(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("winRate")
								.doubleValue());
						turfInfo.setTxPlaceCouple(100 * node.get("pageProps").get("initialState").get("currentPage")
								.get("stats").get(turfInfo.getTableId()).get("statsJockeyHorse").get("inFirst3Rate")
								.doubleValue());
						
						
						   if(turfInfo.getRaceSpecialty().equals("P") && turfInfo.getSurface().equals("PSF")) {
							   
								turfInfo.setNbCourseSurface(
										node.get("pageProps").get("initialState").get("currentPage").get("stats")
												.get(turfInfo.getTableId()).get("statsHorseSurface").get("runs").intValue());
								turfInfo.setPourcVictChevalSurface(100 * node.get("pageProps").get("initialState")
										.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseSurface")
										.get("winRate").doubleValue());
								turfInfo.setPourcPlaceChevalSurface(100 * node.get("pageProps").get("initialState")
										.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseSurface")
										.get("inFirst3Rate").doubleValue());
								turfInfo.setNbCourseSurfaceHippo(
										node.get("pageProps").get("initialState").get("currentPage").get("stats")
												.get(turfInfo.getTableId()).get("statsHorseRacecourseSurface").get("runs").intValue());
								turfInfo.setPourcVictChevalSurfaceHippo(100 * node.get("pageProps").get("initialState")
										.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourseSurface")
										.get("winRate").doubleValue());
								turfInfo.setPourcPlaceChevalSurfaceHippo(100 * node.get("pageProps").get("initialState")
										.get("currentPage").get("stats").get(turfInfo.getTableId()).get("statsHorseRacecourseSurface")
										.get("inFirst3Rate").doubleValue());
								
						   }
						
//	                    turfInfosMetting.setTxVictCoupleHippo(null);
//	                    turfInfosMetting.setTxPlaceCoupleHippo(null);
					} else {
						turfInfo.setNulStats(true);
					}
					/////// nulstats end

					if (turfInfo.getRaceSpecialty().equals("A") || turfInfo.getRaceSpecialty().equals("M")) {
						if (turfInfo.getRaceSpecialty()
								.equals(node.get("pageProps").get("initialState").get("racecards").get("runners")
										.get(numcourse).get(i).get("records").get("distance").get("specialty")
										.textValue())) {
							turfInfo.setDistanceAndSpecialtyChrono(node.get("pageProps").get("initialState")
									.get("racecards").get("runners").get(numcourse).get(i).get("records")
									.get("distance").get("redkm").textValue());
						}
					}

					turfInfo.setRanking(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("ranking").intValue());
					turfInfo.setLiveOdd(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("operatorOdds").get("PMU").get("liveOdd").doubleValue());
					turfInfo.setIsRunning(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("isRunning").booleanValue());
					turfInfo.setIsTQQ(node.get("pageProps").get("race").get("isTQQ").booleanValue());
					turfInfo.setNumberOfInitialRunners(numberofRunners);
					turfInfo.setNumberOfNonRunners(
							node.get("pageProps").get("race").get("numberOfNonRunners").intValue());
					turfInfo.setDraw(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("draw").intValue());

					if (node.get("pageProps").get("race").get("operatorBetTypes") != null
							&& node.get("pageProps").get("race").get("operatorBetTypes").size() > 0
							&& node.get("pageProps").get("race").get("operatorBetTypes").get("PMU") != null
							&& node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").size() > 0) {
						turfInfo.setHasBetTypes(true);

						List<String> strs = new ArrayList<>();
						for (int j = 0; j < node.get("pageProps").get("race").get("operatorBetTypes").get("PMU")
								.size(); j++) {

							strs.add(node.get("pageProps").get("race").get("operatorBetTypes").get("PMU").get(j)
									.textValue());
						}
						for (String str : strs) {
							turfInfo.setIsPick5(false);
							if (str != null && str.equals("PK5")) {
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

//		                turfInfo.setIsFavori(node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).get(i).get("operatorOdds").get("PMU").get("favorite").booleanValue());

					//					turfInfo.setPicto(node.get("pageProps").get("initialState").get("racecards").get("runners")
//							.get(numcourse).get(i).get("operatorOdds").get("PMU").get("picto").textValue());
					turfInfo.setIsSupplemented(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("isSupplemented").booleanValue());

				}

//	                System.out.println(iter);
	                System.out.println("R" + turfInfo.getR() + "C" + turfInfo.getC() );

				turfInfoService.setMadeUpParams(turfInfo);

				if (allTableIds.contains(turfInfo.getTableId())) {
					allToUpdate.add(turfInfo);
//                		turfInfoService.updateFromParisTurfJSON(turfInfo, nulStats);
				}
				if (!allTableIds.contains(turfInfo.getTableId())) {
//                    	turfInfosRepository.save(turfInfo);
					allToSave.add(turfInfo);
				}

//	            	}
				// For LOOP END
			}
//			System.out.println(allToUpdate.size() + " - update size");
			if (allToUpdate.size() > 0) {
				allToSave.addAll(turfInfoService.updateAllFromParisTurfJSON(allToUpdate, all));
			}
//			System.out.println(allToSave.size() + " - save size");

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			throw e;
		}

		return allToSave;
	}

	public List<TurfInfos> createAllDayInfosFromParisTurfJson(String jour, Boolean isUpdate) {

		List<TurfInfos> allToSave = new ArrayList<>();
		List<TurfInfos> all = turfInfosRepository.findAllByJour(jour);

		String parisTurfId = "8aPuWVUYIPg4F_ER1ywnZ";
		String extension = ".json";

		/////////// create all day url/////////
		String firstPartOfDayUrl = "https://www.paris-turf.com/_next/data/";
		String thirdPartOfDayUrl = "/fr/programme-courses/";
		String day = jour;
		String finaldayUrl = firstPartOfDayUrl + parisTurfId + thirdPartOfDayUrl + day + extension;

		//////// Create race url////////////
		String firstPartOfRaceUrl = "https://www.paris-turf.com/_next/data/";
		String thirdPartOfRaceUrl = "/fr/course/-idc-";
		String raceUuid = "";

		JsonNode node;
		int iter = 0;

		try {
			node = new ObjectMapper().readTree(new URL(finaldayUrl));
//			String jour = node.get("pageProps").get("date").textValue();
			Integer numberofRaces = node.get("pageProps").get("initialState").get("racecards").get("races").get(jour)
					.size();
			List<String> uuids = new ArrayList<>();

			for (int i = 0; i < numberofRaces; i++) {

				if (node.get("pageProps").get("initialState").get("racecards").get("races").get(jour).get(i)
						.get("isPremium").booleanValue() == true
						&& node.get("pageProps").get("initialState").get("racecards").get("races").get(jour).get(i)
								.get("isCanceled").booleanValue() == false) {
					String uuid = node.get("pageProps").get("initialState").get("racecards").get("races").get(jour)
							.get(i).get("uuid").textValue();
					uuids.add(uuid);
				}
			}
			for (String uuid : uuids) {
				raceUuid = uuid;
				String raceUrl = firstPartOfRaceUrl + parisTurfId + thirdPartOfRaceUrl + raceUuid + extension;
				if (isUpdate.equals(false)) {
					allToSave.addAll(createAllRaceInfosFromParisTurfJson(raceUrl, all));
				}
				if (isUpdate.equals(true)) {
					updateAllRaceInfosFromParisTurfJson(raceUrl, jour);
				}
				iter += 1;
				System.out.println(iter + " / " + uuids.size());
				System.out.println(jour);

			}

		} 
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		turfInfosRepository.saveAll(allToSave);
		System.out.println("STOP");
		return allToSave;

	}

	public Map<String, Object> createRapportsInfosFromPMUJson(String jour, List<TurfInfos> all, List<Resultat> allResults) throws ParseException {

		List<TurfInfos> allTurfInfosToSave = new ArrayList<>();
		List<Resultat> allResultToSave = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("ddMMyyyy");

		Date date2 = format1.parse(jour);
		String finalDate = format2.format(date2).toString();

		//////////// URL 1 ///////////////
		String firstPartOfUrl = "https://offline.turfinfo.api.pmu.fr/rest/client/1/programme/";
		String day = finalDate;
		String RC;
		String finalPartOfUrl = "rapports-definitifs";

		//////////// URL 2 ///////////////
		String firstPartOfUrl2 = "https://offline.turfinfo.api.pmu.fr/rest/client/7/programme/";
//		String day = finalDate;
//		String RC;
		String finalPartOfUrl2 = "rapports/SIMPLE_PLACE";

//		List<TurfInfos> allByJour = all.stream().filter(ti -> ti.getJour().equals(jour)).collect(Collectors.toList());
		List<TurfInfos> allByJour = all;


		Map<String, Set<Integer>> reunionsAndRaces = new HashMap<>();

		Set<String> distinctReunions = null;
		try {
			distinctReunions = all.stream()
					.filter(ti -> ti.getJour().equals(jour) && ti.getIsPremium() == true && ti.getR().length() < 3)
					.map(TurfInfos::getR).collect(Collectors.toSet());
			
			distinctReunions.forEach(r -> {
				reunionsAndRaces.put(r, all.stream().filter(ti -> ti.getJour().equals(jour) && ti.getR().equals(r))
						.map(TurfInfos::getC).collect(Collectors.toSet()));
			});
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		

		for (Entry<String, Set<Integer>> entry : reunionsAndRaces.entrySet()) {

			for (Integer race : (Set<Integer>) entry.getValue()) {

				String url = firstPartOfUrl + day + "/R" + entry.getKey() + "/C" + race + "/" + finalPartOfUrl;
				String url2 = firstPartOfUrl2 + day + "/R" + entry.getKey() + "/C" + race + "/" + finalPartOfUrl2;
				String url3 = firstPartOfUrl2 + day + "/R" + entry.getKey() + "/C" + race + "/" + "rapports/SIMPLE_GAGNANT";


				System.out.println(url);
				try {
					JsonNode node;
					node = new ObjectMapper().readTree(new URL(url));
					JsonNode node2;
					node2 = new ObjectMapper().readTree(new URL(url2));
					if(node2.isMissingNode() == true) {
						node2 = new ObjectMapper().readTree(new URL(firstPartOfUrl2 + day + "/R" + entry.getKey() + "/C" + race + "/" + "rapports/SIMPLE_PLACE_INTERNATIONAL"));
					}
					final JsonNode node3 = node2;

					if (node.isMissingNode() == false) {

			
//						Resultat resultat = resultService.createResult(jour, entry.getKey(), race, node, url, allResults);
						allResultToSave.add(resultService.createResult(jour, entry.getKey(), race, node, url, allResults));

						allByJour.forEach(ti -> {

							if (node.get(1) != null) {

								if (ti.getR().equals(entry.getKey()) && ti.getC().equals(race)
										&& ti.getRanking().equals(1)) {
									ti.setLiveOdd(
											node.get(0).get("rapports").get(0).get("dividendePourUnEuro").doubleValue()
													/ 100);
									ti.setLiveOddPlace(
											node.get(1).get("rapports").get(0).get("dividendePourUnEuro").doubleValue()
													/ 100);
//							allTurfInfosToSave.add(ti);
//							turfInfosRepository.save(ti);

								}
								if (ti.getR().equals(entry.getKey()) && ti.getC().equals(race)
										&& ti.getRanking().equals(2)) {
									ti.setLiveOddPlace(
											node.get(1).get("rapports").get(1).get("dividendePourUnEuro").doubleValue()
													/ 100);
//							allTurfInfosToSave.add(ti);
//							turfInfosRepository.save(ti);

								}
								if (ti.getR().equals(entry.getKey()) && ti.getC().equals(race)
										&& ti.getRanking().equals(3) && ti.getNumberOfInitialRunners() != null && ti.getNumberOfInitialRunners() > 7) {
									ti.setLiveOddPlace(
											node.get(1).get("rapports").get(2).get("dividendePourUnEuro").doubleValue()
													/ 100);
//							allTurfInfosToSave.add(ti);
//							turfInfosRepository.save(ti);
								}


							}
							/////////////// Create Rapports probables////////////////////////

							if(node3 != null && node3.get("rapportsParticipant") != null && !node3.get("rapportsParticipant").isEmpty()) {
								for (int i = 0; i < node3.get("rapportsParticipant").size(); i++) {

									Double numero = node3.get("rapportsParticipant").get(i).get("numPmu").doubleValue();
									if (ti.getNumero().equals(numero.intValue()) && ti.getR().equals(entry.getKey()) && ti.getC().equals(race)) {
										ti.setMinRapportProbable(node3.get("rapportsParticipant").get(i)
												.get("minRapportProbable").doubleValue());
										ti.setMaxRapportProbable(node3.get("rapportsParticipant").get(i)
												.get("maxRapportProbable").doubleValue());
										Boolean favoris = node3.get("rapportsParticipant").get(i).get("favoris").booleanValue();
											ti.setIsFavori(favoris);
										
									}
								}
							}
						
							allTurfInfosToSave.add(ti);
//							turfInfosRepository.save(ti);

						});


					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		System.out.println(allTurfInfosToSave.size());
		System.out.println("STOP");

		map.put("turfinfos", allTurfInfosToSave);
		map.put("results", allResultToSave);

		return map;

	}

	public void createRapportsInfosFromPMUJsonOnline(String jour) throws ParseException {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("ddMMyyyy");

		Date date2 = format1.parse(jour);
		String finalDate = format2.format(date2).toString();

//		String firstPartOfUrl = "https://offline.turfinfo.api.pmu.fr/rest/client/1/programme/";
		String firstPartOfUrl = "https://online.turfinfo.api.pmu.fr/rest/client/1/programme/";
		String day = finalDate;
		String RC;
//		String finalPartOfUrl = "rapports-definitifs";
		String finalPartOfUrl = "rapports-definitifs?specialisation=INTERNET";

		List<TurfInfos> allByJour = turfInfosRepository.findAllByJour(jour);
		Map<String, Set<Integer>> reunionsAndRaces = new HashMap<>();

		Set<String> distinctReunions = allByJour.stream()
				.filter(ti -> ti.getJour().equals(jour) && ti.getIsPremium().equals(true) && ti.getR().length() < 3)
				.map(TurfInfos::getR).collect(Collectors.toSet());

		distinctReunions.forEach(r -> {
			reunionsAndRaces.put(r, turfInfosRepository.findAllByJourAndByReunion(jour, r).stream()
// 				.filter(ti-> ti.getJour().equals(jour))
					.map(TurfInfos::getC).collect(Collectors.toSet()));
		});

		for (Entry<String, Set<Integer>> entry : reunionsAndRaces.entrySet()) {

			for (Integer race : (Set<Integer>) entry.getValue()) {

				String url = firstPartOfUrl + day + "/R" + entry.getKey() + "/C" + race + "/" + finalPartOfUrl;
				System.out.println(url);
				try {
					JsonNode node;
					node = new ObjectMapper().readTree(new URL(url));

					if (node.isMissingNode() == false) {

						allByJour.forEach(ti -> {
							if (ti.getR().equals(entry.getKey()) && ti.getC().equals(race)
									&& ti.getRanking().equals(1)) {
								ti.setLiveOddPlaceOnline(
										node.get(1).get("rapports").get(0).get("dividendePourUnEuro").doubleValue()
												/ 100);
								turfInfosRepository.save(ti);

							}
							if (ti.getR().equals(entry.getKey()) && ti.getC().equals(race)
									&& ti.getRanking().equals(2)) {
								ti.setLiveOddPlaceOnline(
										node.get(1).get("rapports").get(1).get("dividendePourUnEuro").doubleValue()
												/ 100);
								turfInfosRepository.save(ti);

							}
							if (ti.getR().equals(entry.getKey()) && ti.getC().equals(race) && ti.getRanking().equals(3)
									&& node.get(1).get("rapports").size() > 2) {
								ti.setLiveOddPlaceOnline(
										node.get(1).get("rapports").get(2).get("dividendePourUnEuro").doubleValue()
												/ 100);
								turfInfosRepository.save(ti);
							}

						});

						/////////////// Create result objects////////////////////////

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

	public void updateAllRaceInfosFromParisTurfJson(String url, String jour) {

		List<TurfInfos> all = turfInfosRepository.findAllByJour(jour);

		List<Integer> allNumCourses = all.stream().map(TurfInfos::getNumcourse).collect(Collectors.toList());

		JsonNode node;

		try {
//				node = new ObjectMapper().readTree(file);
			node = new ObjectMapper().readTree(new URL(url));

			// Jour
//			String jour = node.get("pageProps").get("race").get("date").textValue();
			// numcourse
			String numcourse = String.valueOf(node.get("pageProps").get("race").get("id").intValue());
			String uuid = node.get("pageProps").get("race").get("uuid").textValue();

			// Verifie si les stats sont à null
			boolean nulStats = true;

//	            Lister les ids des réunions
//				  Et infos réunions
			Integer numberofRunners = null;
			if (node.get("pageProps").get("race").get("numberOfInitialRunners") != null) {
				numberofRunners = Integer
						.valueOf(node.get("pageProps").get("race").get("numberOfInitialRunners").textValue());
			} else {
				numberofRunners = node.get("pageProps").get("race").get("numberOfRunners").intValue();
//				numberofRunners = node.get("pageProps").get("initialState").get("racecards").get("runners").get(numcourse).size();
			}

			///////////// Début///////////////

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

			if (allNumCourses.contains(Integer.valueOf(numcourse))) {
				for (int i = 0; i < numberofRunners; i++) {
					TurfInfos turfInfo = new TurfInfos();
					turfInfo.setNumcourse(Integer.valueOf(numcourse));
					turfInfo.setNumero(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("saddle").intValue());
					turfInfo.setRanking(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("ranking").intValue());
					turfInfo.setLiveOdd(node.get("pageProps").get("initialState").get("racecards").get("runners")
							.get(numcourse).get(i).get("operatorOdds").get("PMU").get("liveOdd").doubleValue());

					TurfInfos infoToUpdate = all.stream().filter(ti -> ti.getNumero().equals(turfInfo.getNumero())
							&& ti.getNumcourse().equals(turfInfo.getNumcourse())).findFirst().get();

					turfInfoService.updateRankingsFromParisTurfJSON(turfInfo, infoToUpdate);

					// For LOOP END
				}
			}

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//				catch (NullPointerException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

	}

	public List<TurfInfos> updateAllDayInfosFromAspiJson(String jour) {

		List<TurfInfos> alltoUpdate = new ArrayList<>();

		JsonNode node;
		///////// Create URL///////////
		String firstpartOfUrl = "https://aspiturf.com/api/api?uid=";
		String userId = "oG0cgLS6U8UmNPNvBlQm3ovrl0n1";
		String thirdPartOfUrl = "&jour[]=";
		String day = jour;

		String finalUrl = firstpartOfUrl + userId + thirdPartOfUrl + day;

		List<TurfInfos> all = turfInfosRepository.findAllByJour(jour);

		List<Integer> allNumCourses = all.stream().map(TurfInfos::getNumcourse).collect(Collectors.toList());

		try {
			node = new ObjectMapper().readTree(new URL(finalUrl));

			Integer numberofRunners = node.size();

			List<TurfInfos> toRace = new ArrayList<>();

			for (int i = 0; i < numberofRunners; i++) {
				TurfInfos turfInfo = new TurfInfos();

//				turfInfo.setNumcourse(node.get(i).get("numcourse").get("id").intValue());
				final Integer numcourse = node.get(i).get("numcourse").get("id").intValue();
//				turfInfo.setR(node.get(i).get("numcourse").get("reun").textValue());
//				turfInfo.setC(node.get(i).get("numcourse").get("prix").intValue());
//				if(node.get(i).get("numcourse").get("typec").textValue().equals("Attelé")) {
//					turfInfo.setRaceSpecialty("A");
//				}
//				if(node.get(i).get("numcourse").get("typec").textValue().equals("Monté")) {
//					turfInfo.setRaceSpecialty("M");
//				}
//				if(node.get(i).get("numcourse").get("typec").textValue().equals("Plat")) {
//					turfInfo.setRaceSpecialty("P");
//				}
//				if(node.get(i).get("numcourse").get("typec").textValue().equals("Haies")) {
//					turfInfo.setRaceSpecialty("H");
//				}
//				turfInfo.setNumero(node.get(i).get("numero").intValue());
				final Integer numero = node.get(i).get("numero").intValue();
//				turfInfo.setRecence(node.get(i).get("recence").intValue());
//				turfInfo.setJour(node.get(i).get("numcourse").get("jour").textValue());
//				turfInfo.setHour(node.get(i).get("numcourse").get("heure").textValue());
//
//				turfInfo.setNumberOfInitialRunners(node.get(i).get("partant").intValue());
//				turfInfo.setAge(node.get(i).get("age").intValue());
//				turfInfo.setLiveOdd(node.get(i).get("cotedirect").doubleValue());
//				turfInfo.setDraw(node.get(i).get("corde").intValue());
//				if(turfInfo.getDraw().equals(0)) {
//					turfInfo.setDraw(null);
//				}
//				if(node.get(i).get("oeilFirstTime").intValue() == 0) {
//					turfInfo.setBlinkersFirstTime(false);;
//				}
//				if(node.get(i).get("oeilFirstTime").intValue() == 1) {
//					turfInfo.setBlinkersFirstTime(true);;
//				}
//				/////////////////////////////////////////////////////////////////////////////
//				//////////////////////////////////////////////////////////////////////////////
//				turfInfo.setNbCourseCoupleHippo(node.get(i).get("nbCourseCoupleHippo").intValue());
//				turfInfo.setTxVictCoupleHippo(node.get(i).get("TxVictCoupleHippo").doubleValue());
//				turfInfo.setTxPlaceCoupleHippo(node.get(i).get("TxPlaceCoupleHippo").doubleValue());
				
//				turfInfo.setNbrCourseChevalHippo(String.valueOf(node.get(i).get("nbrCourseChevalHippo").intValue()));
//				turfInfo.setCoursescheval(String.valueOf(node.get(i).get("coursescheval").intValue()));
//				
//				if(node.get(i).get("placescheval").intValue() != 0) {
//					turfInfo.setPourcPlaceCheval((double) (1.0*(100 * node.get(i).get("placescheval").intValue()) / node.get(i).get("coursescheval").intValue()));
//				} else {
//					turfInfo.setPourcPlaceCheval(0d);
//				}
//								
//				turfInfo.setNbrCourseEntHippo(String.valueOf(node.get(i).get("nbrCourseEntHippo").intValue()));
//				turfInfo.setNbrCourseJockHippo(String.valueOf(node.get(i).get("nbrCourseJockHippo").intValue()));
//				turfInfo.setNbCourseCouple(node.get(i).get("nbCourseCouple").intValue());
//				turfInfo.setNbCourseCoupleHippo(node.get(i).get("nbCourseCoupleHippo").intValue());
//
//				turfInfo.setPourcVictEntHippo(node.get(i).get("pourcVictEntHippo").doubleValue());
//				turfInfo.setPourcVictJockHippo(node.get(i).get("pourcVictJockHippo").doubleValue());
//				turfInfo.setPourcVictChevalHippo(node.get(i).get("pourcVictChevalHippo").doubleValue());
//				turfInfo.setPourcPlaceChevalHippo(node.get(i).get("pourcPlaceChevalHippo").doubleValue());
////				infoToUpdate.setPourcPlaceCheval(node.get(i).get("pourcVictChevalHippo").doubleValue());
//				turfInfo.setTxVictCouple(node.get(i).get("TxVictCouple").doubleValue());
//				turfInfo.setTxPlaceCouple(node.get(i).get("TxPlaceCouple").doubleValue());
//				//////////////////////////////////////////////////////////////////////////////
//				/////////////////////////////////////////////////////////////////////////////
				System.out.println(i);

				Optional<TurfInfos> optInfoToUpdate = all.stream()
						.filter(ti -> ti.getNumero().equals(numero)
//								&& ti.getId().equals(368630l)
								&& ti.getNumcourse().equals(numcourse) && ti.getIsPremium().equals(true))
						.findFirst();

//                	if(!allNumCourses.contains(turfInfo.getNumcourse())) {
//                	turfInfosRepository.save(turfInfo);
//                	}
				if (optInfoToUpdate.isPresent()) {
					turfInfo = optInfoToUpdate.get();
					
//					turfInfo.setNumcourse(node.get(i).get("numcourse").get("id").intValue());
//					turfInfo.setR(node.get(i).get("numcourse").get("reun").textValue());
//					turfInfo.setC(node.get(i).get("numcourse").get("prix").intValue());
//					if(node.get(i).get("numcourse").get("typec").textValue().equals("Attelé")) {
//						turfInfo.setRaceSpecialty("A");
//					}
//					if(node.get(i).get("numcourse").get("typec").textValue().equals("Monté")) {
//						turfInfo.setRaceSpecialty("M");
//					}
//					if(node.get(i).get("numcourse").get("typec").textValue().equals("Plat")) {
//						turfInfo.setRaceSpecialty("P");
//					}
//					if(node.get(i).get("numcourse").get("typec").textValue().equals("Haies")) {
//						turfInfo.setRaceSpecialty("H");
//					}
//					turfInfo.setNumero(node.get(i).get("numero").intValue());
					turfInfo.setRecence(node.get(i).get("recence").intValue());
//					turfInfo.setJour(node.get(i).get("numcourse").get("jour").textValue());
//					turfInfo.setHour(node.get(i).get("numcourse").get("heure").textValue());

//					turfInfo.setNumberOfInitialRunners(node.get(i).get("partant").intValue());
//					turfInfo.setAge(node.get(i).get("age").intValue());
//					turfInfo.setLiveOdd(node.get(i).get("cotedirect").doubleValue());
//					turfInfo.setDraw(node.get(i).get("corde").intValue());
//					if(turfInfo.getDraw().equals(0)) {
//						turfInfo.setDraw(null);
//					}
//					if(node.get(i).get("oeilFirstTime").intValue() == 0) {
//						turfInfo.setBlinkersFirstTime(false);;
//					}
//					if(node.get(i).get("oeilFirstTime").intValue() == 1) {
//						turfInfo.setBlinkersFirstTime(true);;
//					}
					/////////////////////////////////////////////////////////////////////////////
					//////////////////////////////////////////////////////////////////////////////
					turfInfo.setNbCourseCoupleHippo(node.get(i).get("nbCourseCoupleHippo").intValue());
					turfInfo.setTxVictCoupleHippo(node.get(i).get("TxVictCoupleHippo").doubleValue());
					turfInfo.setTxPlaceCoupleHippo(node.get(i).get("TxPlaceCoupleHippo").doubleValue());
					
//					turfInfo.setNbrCourseChevalHippo(String.valueOf(node.get(i).get("nbrCourseChevalHippo").intValue()));
//					turfInfo.setCoursescheval(String.valueOf(node.get(i).get("coursescheval").intValue()));
//					
//					if(node.get(i).get("placescheval").intValue() != 0) {
//						turfInfo.setPourcPlaceCheval((double) (1.0*(100 * node.get(i).get("placescheval").intValue()) / node.get(i).get("coursescheval").intValue()));
//					} else {
//						turfInfo.setPourcPlaceCheval(0d);
//					}
//									
//					turfInfo.setNbrCourseEntHippo(String.valueOf(node.get(i).get("nbrCourseEntHippo").intValue()));
//					turfInfo.setNbrCourseJockHippo(String.valueOf(node.get(i).get("nbrCourseJockHippo").intValue()));
//					turfInfo.setNbCourseCouple(node.get(i).get("nbCourseCouple").intValue());
//					turfInfo.setNbCourseCoupleHippo(node.get(i).get("nbCourseCoupleHippo").intValue());
//
//					turfInfo.setPourcVictEntHippo(node.get(i).get("pourcVictEntHippo").doubleValue());
//					turfInfo.setPourcVictJockHippo(node.get(i).get("pourcVictJockHippo").doubleValue());
//					turfInfo.setPourcVictChevalHippo(node.get(i).get("pourcVictChevalHippo").doubleValue());
//					turfInfo.setPourcPlaceChevalHippo(node.get(i).get("pourcPlaceChevalHippo").doubleValue());
////					infoToUpdate.setPourcPlaceCheval(node.get(i).get("pourcVictChevalHippo").doubleValue());
//					turfInfo.setTxVictCouple(node.get(i).get("TxVictCouple").doubleValue());
//					turfInfo.setTxPlaceCouple(node.get(i).get("TxPlaceCouple").doubleValue());
					//////////////////////////////////////////////////////////////////////////////
					/////////////////////////////////////////////////////////////////////////////
					
					
					alltoUpdate.add(turfInfo);
//                		turfInfoService.updateAllFromAspiJSON(turfInfo, infoToUpdate);
				}
				
//				alltoUpdate.add(turfInfo);


			}

//			turfInfoService.updateAllFromAspiJSON(alltoUpdate);
			System.out.println("Stop Aspi");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		System.out.println(alltoUpdate.size() + " SIZE");
		return alltoUpdate;


	}

}
