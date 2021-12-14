package turfinfos2.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import turfinfos2.model.ArchiveDeuxSur4;
import turfinfos2.model.Resultat;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ArchiveInfoRepository;
import turfinfos2.repository.TurfInfosRepository;

@Service
public class ArchiveService {

	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	TurfInfoService turfInfoService;
	
	@Autowired
	ArchiveInfoRepository archiveInfosRepository;
	
public Boolean verifyConditions(TurfInfos info) {
		
		if(
				info.getNumberOfInitialRunners() != null && info.getNumberOfInitialRunners() > 1
				&& info.getHasBetTypes() == true 
				&& info.getLiveOdd() != null && info.getLiveOdd() != 0 && info.getLiveOdd() < 2.5
		        && info.getRecence() != null && info.getRecence() < 60
			    && info.getMinRapportProbable() != null && info.getMinRapportProbable() != 0 && info.getMinRapportProbable() > 1.3d
//			        && ti.getJour().contains("2021-11")
//			        && ti.getMaxRapportProbable() != null && ti.getMaxRapportProbable() != 0 && ti.getMaxRapportProbable() < 1.7d
//					&& ti.getIsFavori().equals(true)
//					&& info.getRaceSpecialty().equals("P")
//			        && (info.getFiveEtoile() == true || info.getThreeEtoile() == true)
			        
			        
//				    && ti.getLiveOddPlace() != null && (ti.getLiveOddPlace() == 0 || ti.getLiveOddPlace() < 3)
//					&& info.getIsFavori().equals(true)
//			        && ti.getRaceSpecialty().equals("P")
//			        && ti.getLiveOddPlace(). != 0
//			        && (  ti.getRaceSpecialty().equals("A") || ti.getRaceSpecialty().equals("M")  )
//			        && ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() >= 25
//					&& ti.getPourcPlaceChevalHippo() != null && ti.getPourcPlaceChevalHippo() >= 39.2
//			        && info.getChronoPastille() == false
//			        && info.getJockeyPastille() == false
//			        && info.getChevalPastille() == false
//			        && info.getCouplePastille() == false
	
//			        && ti.getFormFigs() != null && ti.getFormFigs().length()>= 2 && (ti.getFormFigs().charAt(0)=='1' || ti.getFormFigs().charAt(0)=='2' || ti.getFormFigs().charAt(0)=='3') && (ti.getFormFigs().charAt(1)=='p' || ti.getFormFigs().charAt(1)=='a' || ti.getFormFigs().charAt(1)=='m')
	
				) {
			return true;
			
		}else {
			
			return false;

		}
				
	}
	
	public List<TurfInfos> filterAll(List<TurfInfos> all) {
		
		List<TurfInfos> allFiltered = all
				.stream()
				.filter(ti->
		        
		        ti.getNumberOfInitialRunners() != null && ti.getNumberOfInitialRunners() > 1
				&& ti.getHasBetTypes() == true 
				&& ti.getLiveOdd() != null && ti.getLiveOdd() != 0 && ti.getLiveOdd() < 2.5
		        && ti.getRecence() != null && ti.getRecence() < 60
			    && ti.getMinRapportProbable() != null && ti.getMinRapportProbable() != 0 && ti.getMinRapportProbable() > 1.3d
		        )
				.collect(Collectors.toList());
				
		return allFiltered;
	}
	
	public List<TurfInfos> createArchives (List<TurfInfos> listByNoteProno) {
	
	 List<TurfInfos> finalList = new ArrayList<>();
	
	if(listByNoteProno.size()>0) {

			Optional<TurfInfos> optInfo = Optional.of(listByNoteProno.get(0));
		
		if(optInfo.isPresent()) {
			
			TurfInfos info = optInfo.get();
		
		if(info.getLiveOddPlace() == null) {
			info.setLiveOddPlace(0d);
		}
		
        finalList.add(info);		
		}
		}
	return finalList;	
}

public List<ArchiveDeuxSur4> createArchive2sur4(List<TurfInfos> prono, List<Resultat> results) {
	
	List<Resultat> allResults = new ArrayList<>();
	allResults.addAll(results);
	List<ArchiveDeuxSur4> deuxSurQuatrelist = new ArrayList<>();
	ArchiveDeuxSur4 archiveDeuxSur4 = new ArchiveDeuxSur4();

	
	for(TurfInfos ti : prono){
		
		archiveDeuxSur4.setC(ti.getC());
		archiveDeuxSur4.setJour(ti.getJour());
		archiveDeuxSur4.setHour(ti.getHour());
		archiveDeuxSur4.setNumcourse(ti.getNumcourse());
		archiveDeuxSur4.setR(ti.getR());
		
		System.out.println(allResults.get(0).getC());
		
		Resultat resultat = allResults
				.stream()
				.filter(r -> r.getC() == ti.getC() && r.getR() == ti.getR() && r.getJour() == ti.getJour())
				.findFirst().get();
		System.out.println(resultat.toString() +" resultat");

					
		archiveDeuxSur4.setRapport(resultat.getDeuxSurQuatre().getRapport());
		archiveDeuxSur4.setArrivee("-" + resultat.getDeuxSurQuatre().getCombinaison() + "-");
		System.out.println(archiveDeuxSur4.getArrivee() +" arrivee");

		
		if(ti.getFiveEtoile().equals(true) || ti.getThreeEtoile().equals(true)) {
			archiveDeuxSur4.getCombinaison().add(ti.getNumero());
			if(archiveDeuxSur4.getCombinaison().size() >= 2) {
//				archiveDeuxSur4.setIsWon(true);
				deuxSurQuatrelist.add(archiveDeuxSur4);
				archiveDeuxSur4 = new ArchiveDeuxSur4();
			}
			if(archiveDeuxSur4.getArrivee().contains("-" + archiveDeuxSur4.getCombinaison().get(0) + "-") && archiveDeuxSur4.getArrivee().contains("-" + archiveDeuxSur4.getCombinaison().get(1) + "-")) {
				archiveDeuxSur4.setIsWon(true);
			}else{
				archiveDeuxSur4.setIsWon(false);
			};
		}
		
	};
	
	return deuxSurQuatrelist;
}


	public Map<String, Object> setAllPlaceFirstPronoList(List<TurfInfos> allList, List<Resultat> results) {
		
		final List<TurfInfos> all = allList;
		
    	Map<String, Object> map = new HashMap<>();

		List<TurfInfos> finalList = new ArrayList<>();
    	List<ArchiveDeuxSur4> deuxSur4List = new ArrayList<>();
		
		//Jours
				Set<String> distinctJours = all.stream()
				.map(TurfInfos :: getJour)
//				.sorted()
				.collect(Collectors.toSet());
				List<String> list = new ArrayList<String>(distinctJours);
				Collections.reverse(list);
				distinctJours = new LinkedHashSet<>(list);
				
				distinctJours.forEach(j->{
					
					  Set<String> reunions = all.stream()
				 				.filter(ti -> ti.getJour().equals(j))
				        			.map(TurfInfos :: getReunionstring)
				        			.collect(Collectors.toSet());
				        			List<String> reunionsList = new ArrayList<String>(reunions);
//				        			Collections.sort(reunionsList);        			
				        			reunions = new LinkedHashSet<>(reunionsList);
         				
				     reunions.forEach(r->{
				    	 
				    	 ;
				    	 finalList.addAll((List<TurfInfos>) setPlaceFirstPronoList(j, r, all, results).get("turfinfos"));
				    	 deuxSur4List.addAll((List<ArchiveDeuxSur4>) setPlaceFirstPronoList(j, r, all, results).get("2sur4"));

				    	 
				     });
				});
				System.out.println("big list = " + finalList.size());
				map.put("turfinfos", finalList);
				map.put("2sur4", deuxSur4List);
				return map;	}
    
    public Map<String, Object> setPlaceFirstPronoList(String jour,
    		String reunionstring, List<TurfInfos> all, List<Resultat> results
    		) {   
    	
//    	if(principal == null) {
//			return "redirect:/login";
//    	}
    	
    	Map<String, Object> map = new HashMap<>();
    	
    	//RACESLIST
    	List<TurfInfos> allPremiumReunionInfos = all
//    			turfInfosRepository.findAllByJourAndByReunionstring(jour, reunionstring)
    			.stream()
    			.filter(ti-> ti.getJour().equals(jour) && ti.getReunionstring().equals(reunionstring) 
    				&& ti.getIsRunning() != null && ti.getIsRunning().equals(true) && ti.getIsPremium() != null && ti.getIsPremium().equals(true))
				.collect(Collectors.toList());
    	
//    	allPremiumReunionInfos.forEach(ti->{
//    		ti.setIsFirstInProno(false);
//    	});
    	List<TurfInfos> finalList = new ArrayList<>();
    	List<ArchiveDeuxSur4> deuxSur4List = new ArrayList<>();


    			
    	List<TurfInfos> reunionCracks = new ArrayList<>();
    	Map<String, String> tayPronos = new HashMap<>();

    	

    	//Num des courses
		Set<Integer> distinctNumraces = allPremiumReunionInfos.stream()
		.map(TurfInfos :: getC)
		.sorted()
		.collect(Collectors.toSet());
		List<Integer> list = new ArrayList<Integer>(distinctNumraces);
		Collections.reverse(list);
		distinctNumraces = new LinkedHashSet<>(list);
		
		  //Cast
//		 LinkedList<String> distinctNumracesString = new LinkedList<>();
//				 
//		    for(Integer i : distinctNumraces) {
//		    	distinctNumracesString.add(i.toString());
//		    }
		
//		System.out.println(distinctNumraces.size());
		
		
		List<TurfInfos> allraceInfos = new ArrayList<>();
		for(Integer num : distinctNumraces) {
					
			//Infos de la course en question
			allraceInfos = 
					allPremiumReunionInfos
					.stream()
					.filter(ti -> ti.getC().equals(num))
					.collect(Collectors.toList());
			
		
			String nonPartants = "";
			if(allraceInfos.get(0).getNumberOfNonRunners() != null && allraceInfos.get(0).getNumberOfNonRunners() > 0) {
				 nonPartants = "(" + allraceInfos.get(0).getNumberOfNonRunners() + " NP)";
			}
		

//			});
			
//			createClassementList(allraceInfos);
			

			
			//création des listes filtrées et triées par parametre voulu
			
			List<TurfInfos> listBypvch = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictChevalHippo() != null && ti.getPourcVictChevalHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictChevalHippo))
					.collect(Collectors.toList());
			Collections.reverse(listBypvch);			
			
			List<TurfInfos> listBypvjh = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictJockHippo() != null && ti.getPourcVictJockHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictJockHippo))
					.collect(Collectors.toList());
			Collections.reverse(listBypvjh);
			
			List<TurfInfos> listBypveh = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictEntHippo() != null && ti.getPourcVictEntHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictEntHippo))
					.collect(Collectors.toList());
			Collections.reverse(listBypveh);
			
			
			allraceInfos.forEach(ti -> {
				if(ti.getPourcVictChevalHippo()!= null && ti.getPourcVictChevalHippo().equals(ti.getPourcPlaceChevalHippo())) {
					ti.setPourcPlaceChevalHippo(0d);
				}
			});	
			List<TurfInfos> listByppch = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceChevalHippo() != null && ti.getPourcPlaceChevalHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceChevalHippo))
					.collect(Collectors.toList());
			Collections.reverse(listByppch);	
			
			List<TurfInfos> listByppc = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceCheval).thenComparingInt(TurfInfos::getNumero))
					.collect(Collectors.toList());
			Collections.reverse(listByppc);
						
			List<TurfInfos> listByppjh = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceJockHippo() != null && ti.getPourcPlaceJockHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceJockHippo))
					.collect(Collectors.toList());
			Collections.reverse(listByppjh);
				
			List<TurfInfos> listByppeh = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceEntHippo() != null && ti.getPourcPlaceEntHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceEntHippo))
					.collect(Collectors.toList());
			Collections.reverse(listByppeh);
		
			List<TurfInfos> listBytxv = allraceInfos.stream()
					.filter(ti -> ti.getTxVictCouple() != null && ti.getTxVictCouple() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxVictCouple))
					.collect(Collectors.toList());
			Collections.reverse(listBytxv);
					
			allraceInfos.forEach(ti -> {
//				System.out.println(ti.getR()+ti.getC());
//				System.out.println("t-"+ti.getTxVictCouple());
//				System.out.println("tttt-"+ti.getTxPlaceCouple());
//				if(ti.getCoupleTwoOrThree() != null && !ti.getCoupleTwoOrThree().equals(0) && ti.getNbCourseCouple() != null && !ti.getNbCourseCouple().equals( 0)) {
//				ti.setTxPlaceCouple(calculateNewPlacePercentage(ti.getCoupleTwoOrThree(), ti.getNbCourseCouple()-ti.getNbVictCouple()).doubleValue());
//				}
//				if(ti.getCoupleTwoOrThree() != null && ti.getCoupleTwoOrThree().equals(0)) {
//					ti.setTxPlaceCouple(0d);
//					}
//				System.out.println("tttt-"+ti.getTxPlaceCouple());
//				System.out.println();
//				
				if(ti.getTxVictCouple() != null && ti.getTxVictCouple().equals(ti.getTxPlaceCouple())) {
					ti.setTxPlaceCouple(0d);
				}
			});	
			List<TurfInfos> listBytxp = allraceInfos.stream()
					.filter(ti -> ti.getTxPlaceCouple() != null && ti.getTxPlaceCouple() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxPlaceCouple))
					.collect(Collectors.toList());
			Collections.reverse(listBytxp);
				
			List<TurfInfos> listBytxvh = allraceInfos.stream()
					.filter(ti -> ti.getTxVictCoupleHippo() != null && ti.getTxVictCoupleHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxVictCoupleHippo))
					.collect(Collectors.toList());
			Collections.reverse(listBytxvh);
				
//			allraceInfos.forEach(ti -> {
//				ti.setTxPlaceCoupleHippo(calculateNewPlacePercentage(ti.getNbCourseCoupleHippo()-ti.getnb, ti.getNbCourseCoupleHippo()).doubleValue());
//
//				if(ti.getTxVictCoupleHippo() != null && ti.getTxVictCoupleHippo() == 100) {
//					ti.setTxPlaceCoupleHippo(0d);
//				};
//				
//			});	
			List<TurfInfos> listBytxph = allraceInfos.stream()
					.filter(ti -> ti.getTxPlaceCoupleHippo() != null && ti.getTxPlaceCoupleHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxPlaceCoupleHippo))
					.collect(Collectors.toList());
			Collections.reverse(listBytxph);
		
			//CHRONOS
			List<TurfInfos> listByChronos = allraceInfos.stream()
					.filter(ti -> ti.getChrono()!= null)
					.sorted(Comparator.comparingInt(TurfInfos::getChrono))
					.collect(Collectors.toList());
//			Collections.reverse(listBypvch);
			
			//TayPronos
			List<TurfInfos> listByTayPronos = allraceInfos.stream()
					.filter(ti -> ti.getTayProno()!= null)
					.sorted(Comparator.comparingInt(TurfInfos::getTayProno))
					.collect(Collectors.toList());
//			Collections.reverse(listBypvch);
			
			if(listByTayPronos.size()>0) {
			            String prono = "";	
                        for(TurfInfos info : listByTayPronos) {
    			            prono = prono + " - " + info.getNumero();
                        }
                        String result = prono.substring(3);
						System.out.println(result + "PRONO C " +num);
			tayPronos.put("C" + num, result);
			}
			//list par chrono Paris turf
//			List<TurfInfos> listByChronoParisTurf = allraceInfos.stream()
//					.filter(ti -> ti.getDistanceAndSpecialtyChrono()!= null)
//					.sorted(Comparator.comparingInt(TurfInfos::getTayProno))
//					.collect(Collectors.toList());
////			Collections.reverse(listBypvch);
			
			 List<TurfInfos> listByChronoParisTurf = allraceInfos.stream()
						    .filter(ti -> ti.getDistanceAndSpecialtyChrono()!= null)
							.sorted(Comparator.comparing(TurfInfos::getDistanceAndSpecialtyChrono).thenComparing(TurfInfos::getNumero))
		        			.collect(Collectors.toList());
//		        			for(TurfInfos info: listByChronoParisTurf) {
//		        				System.out.println(info.getDistanceAndSpecialtyChrono());
//		        			}
			 
			//CLASSEMENT A L'ARRIVEE
				List<TurfInfos> listByRanking = allraceInfos.stream()
						.filter(ti -> ti.getRanking()!= null && ti.getRanking()!= 0)
						.sorted(Comparator.comparingInt(TurfInfos::getRanking))
						.collect(Collectors.toList());
				
				//CORDES
				List<TurfInfos> listByDraw = allraceInfos.stream()
						.filter(ti -> ti.getDraw()!= null && ti.getDraw()!= 0)
						.sorted(Comparator.comparingInt(TurfInfos::getDraw))
						.collect(Collectors.toList());
			
			//Calcul de la note
			List<TurfInfos> listByNoteProno = calculateFinalNoteProno(allraceInfos,
				    listBypvch,
				    listBypvjh,
				    listBypveh,
				   
				    listByppch,
				    listByppjh,
				    listByppeh,
				   
				    listBytxv,
				    listBytxp,
				    listBytxvh,
				    listBytxph,
				   
				    listByppc,
				    listByChronos
		)
					
					.stream()
					.filter(ti -> ti.getNoteProno() != null && ti.getNoteProno()>0)
					.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno))
					.collect(Collectors.toList());
			Collections.reverse(listByNoteProno);
			
			////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////
			
		

			
			////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////	////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////
			
			reunionCracks.addAll(listByNoteProno);
			
			/////////////AFFECTER PASTILLES///////////////
			setPastilles(listBypvjh, listByChronos, listBypvch, listByppch, listBytxv, listBytxp, listByNoteProno, allraceInfos.size());
		    ///////////// AFFECTER ETOILES///////////////
			setEtoiles(listByChronos,listBypveh, listBypvjh, listBypvch, listByppch, listByppc, listBytxv, listBytxp, listBytxvh, listBytxph,
					listByNoteProno,
					allraceInfos.size());
			
//			setPastilles(listBypvjh, listByChronos, listBypvch, listByppch, listBytxv, listBytxp, listByNoteProno, allraceInfos.size()).forEach(ti->{
			listByNoteProno.forEach(ti->{
				if(verifyConditions(ti).equals(true)) {
					if(ti.getLiveOddPlace() == null) {
						ti.setLiveOddPlace(0d);
					}
		            finalList.add(ti);		

				}
			});
			deuxSur4List.addAll(createArchive2sur4(listByNoteProno, results));
	
//			if(listByNoteProno.size()>0) {
//
//				Optional<TurfInfos> optInfo = Optional.of(listByNoteProno.get(0));
//			
//			if(optInfo.isPresent()) {
//				
//				TurfInfos info = optInfo.get();
//			
//			if(info.getLiveOddPlace() == null) {
//				info.setLiveOddPlace(0d);
//			}
//
//            finalList.add(info);		
//			}
//			}	
			
//			deuxSur4List.addAll(create2sur4(listByNoteProno, results));

			
			List<TurfInfos> listByNumCheval = allraceInfos.stream()
//					.map(TurfInfos :: getNumero)
					.sorted(Comparator.comparingInt(TurfInfos::getNumero))
					.collect(Collectors.toList());
//					List<Integer> listNums = new ArrayList<Integer>(distinctNumsCheval);
//					Collections.reverse(listNums);
//					distinctNumsCheval = new LinkedHashSet<>(listNums);
			

						
		}
		
		////////////CRACKS/////////////////
		List<TurfInfos> crackList = reunionCracks.stream()
		.filter(ti-> ti.getNoteProno()>= 30)
		.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno))
		.collect(Collectors.toList());
		Collections.reverse(crackList);

	
		
////////////Placés du jour/////////////////
	List<TurfInfos> placeList = allPremiumReunionInfos.stream()
	.filter(ti-> ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval()== 100)
	.sorted(Comparator.comparingInt(TurfInfos::getC).thenComparingInt(TurfInfos::getNumero))
	.collect(Collectors.toList());
//	Collections.reverse(placeList);

		
		//////////COUPLES DU JOUR//////////////
		Set<String> cples = new HashSet<>();
		for(TurfInfos info : crackList) {
			
			List<TurfInfos> couple = crackList.stream()
					.filter(ti-> ti.getC() == info.getC())
					.sorted(Comparator.comparingInt(TurfInfos::getNumero))
					.collect(Collectors.toList());
			if(couple.size()>=2) {
				String C = couple.get(0).getC().toString();
				String one = couple.get(0).getNumero().toString();
				String two = couple.get(1).getNumero().toString();
				
				if(couple.get(0).getNumero()<10) {
					one = "0" + couple.get(0).getNumero();
				}
				if(couple.get(1).getNumero()<10) {
					two = "0" + couple.get(1).getNumero().toString();
				}
				
				String cple = C + one + " - " + C +two ;
				cples.add(cple);
			}
		}
		List<String> couples = cples.stream()
				.sorted()
				.collect(Collectors.toList());

		

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				/////////////reusOfDay
//		 Set<String> reunions = turfInfosRepository.findAllByJour(jour).stream()
//	 				.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//	        			.map(TurfInfos :: getReunionstring)
//	        			.collect(Collectors.toSet());
//	        			List<String> list2 = new ArrayList<String>(reunions);
//	        			Collections.sort(list2);        			
//	        			reunions = new LinkedHashSet<>(list2);
//	        	


	         
	    				System.out.println("list = " + finalList.size());

//	        			return finalList;
	    				map.put("turfinfos", finalList);
	    				map.put("2sur4", deuxSur4List);
	    				return map;
    }
    
    ///////////////////////////////////////////////////////////////
    private String numToString(Integer num) {
    	
    	String str = null;
    	
    	 switch(num){
    	   
         case 1: 
             str = "one";
             break;
     
         case 2:
             str = "two";
             break;
     
         case 3:
             str = "three";
             break;
             
         case 4: 
             str = "four";
             break;
     
         case 5:
             str = "five";
             break;
     
         case 6:
             str = "six";
             break;
             
         case 7: 
                 str = "seven";
                 break;
         
             case 8:
                 str = "eight";
                 break;
         
             case 9:
                 str = "nine";
                 break;
                 
             case 10: 
                 str = "ten";
                 break;
         
             case 11:
                 str = "eleven";
                 break;
         
             case 12:
                 str = "twelve";
                 break;
             
     }
    
    	return str;
    }
    
   private String numToStringNumber(Integer num) {
    	
    	String str = null;
    	
    	 switch(num){
    	   
         case 1: 
             str = "1";
             break;
     
         case 2:
             str = "2";
             break;
     
         case 3:
             str = "3";
             break;
             
         case 4: 
             str = "4";
             break;
     
         case 5:
             str = "5";
             break;
     
         case 6:
             str = "6";
             break;
             
         case 7: 
                 str = "7";
                 break;
         
             case 8:
                 str = "8";
                 break;
         
             case 9:
                 str = "9";
                 break;
                 
             case 10: 
                 str = "10";
                 break;
         
             case 11:
                 str = "11";
                 break;
         
             case 12:
                 str = "12";
                 break;
             
     }
    
    	return str;
    }
   
   private List<TurfInfos> calculateFinalNoteProno(List<TurfInfos>allraceInfos,
		   List<TurfInfos> listBypvch,
		   List<TurfInfos> listBypvjh,
		   List<TurfInfos> listBypveh,
		   
		   List<TurfInfos> listByppch,
		   List<TurfInfos> listByppjh,
		   List<TurfInfos> listByppeh,
		   
		   List<TurfInfos> listBytxv,
		   List<TurfInfos> listBytxp,
		   List<TurfInfos> listBytxvh,
		   List<TurfInfos> listBytxph,
		   List<TurfInfos> listByppc,

		   
		   List<TurfInfos> listByChronos
		   ) {	 
	   
	   calculateNoteProno(allraceInfos, listBypvch, 1d);
	   calculateNoteProno(allraceInfos, listBypvjh, 2d);
	   calculateNoteProno(allraceInfos, listBypveh, 3d);

	   calculateNoteProno(allraceInfos, listByppch, 4d);
//	   calculateNoteProno(allraceInfos, listByppjh, 5d);
//	   calculateNoteProno(allraceInfos, listByppeh, 6d);

	   calculateNoteProno(allraceInfos, listBytxv, 7d);
	   calculateNoteProno(allraceInfos, listBytxp, 8d);
//	   calculateNoteProno(allraceInfos, listBytxvh, 9d);
//	   calculateNoteProno(allraceInfos, listBytxph, 10d);

//	   calculateNoteProno(allraceInfos, listByChronos, 11d);
	   calculateNoteProno(allraceInfos, listByppc, 12d);

	   
//	   System.out.println((70d/100)*75d + "tauxcrack");

		return allraceInfos;
   }
   
   private List<TurfInfos> calculateNoteProno(List<TurfInfos>allRaceInfos, List<TurfInfos>list, Double percentageParameter) {
	   	   
	   
	for(int i =0; i<list.size(); i++) {
			
			if(list.size() > 0 && i == 0) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
			}	
			
			if(list.size() > 1 && i == 1) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			
			if(list.size() > 2 && i == 2) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 3 && i == 3) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 4 && i == 4) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 5 && i == 5) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 6 && i == 6) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 7 && i == 7) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 8 && i == 8) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 9 && i == 9) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}	
			if(list.size() > 10 && i == 10) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 11 && i == 11) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 12 && i == 12) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 13 && i == 13) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 14 && i == 14) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 15 && i == 15) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 16 && i == 16) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 17 && i == 17) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 18 && i == 18) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 19 && i == 19) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
			if(list.size() > 20 && i == 20) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10 );
				}
		}
	
	   return list;
   }
   
   private TurfInfos setPercentageParam(TurfInfos tinf, Double percentParam){
	   
	   if(percentParam == 1d) {
		   tinf.setNotePercentageParameter(tinf.getPourcVictChevalHippo());
	   }
	   if(percentParam == 2d) {
		   tinf.setNotePercentageParameter(tinf.getPourcVictJockHippo());
	   }
	   if(percentParam == 3d) {
		   tinf.setNotePercentageParameter(tinf.getPourcVictEntHippo());
	   }
	   if(percentParam == 4d) {
		   tinf.setNotePercentageParameter(tinf.getPourcPlaceChevalHippo());
	   }
	   if(percentParam == 5d) {
		   tinf.setNotePercentageParameter(tinf.getPourcPlaceJockHippo());
	   }
	   if(percentParam == 6d) {
		   tinf.setNotePercentageParameter(tinf.getPourcPlaceEntHippo());
	   }
	   if(percentParam == 7d) {
		   tinf.setNotePercentageParameter(tinf.getTxVictCouple());
	   }
	   if(percentParam == 8d) {
		   tinf.setNotePercentageParameter(tinf.getTxPlaceCouple());
	   }
//	   if(percentParam == 9d) {
//		   tinf.setNotePercentageParameter(tinf.getTxVictCoupleHippo());
//	   }
//	   if(percentParam == 10d) {
//		   tinf.setNotePercentageParameter(tinf.getTxPlaceCoupleHippo());
//	   }
	   
//	   if(percentParam == 11d) {
//		   tinf.setNotePercentageParameter(tinf.getChrono().doubleValue());
//	   }
	   if(percentParam == 12d) {
		   tinf.setNotePercentageParameter(tinf.getPourcPlaceCheval());
	   }
	   
	   return tinf;
   }
   
   private List<TurfInfos> calculateEntraineur(List<TurfInfos>AllRaceinfos) {
	   
	   LinkedList<TurfInfos> newList = new LinkedList<TurfInfos>();
	   
	   Set<String> distinctEntraineurs = AllRaceinfos.stream()
				.map(TurfInfos :: getEntraineur)
//				.sorted()
				.collect(Collectors.toSet());
	   
	   
	   for(String entraineur : distinctEntraineurs) {
		   
//		   List<TurfInfos> listByEnt = turfInfosRepository.findAllByNumcourseAndEntraineur(numcourse, entraineur);
		   
		   List<TurfInfos> listByEnt = AllRaceinfos.stream()
				   .filter(ti-> ti.getEntraineur().equals(entraineur))
				   .collect(Collectors.toList());

		   
		   
		   if(listByEnt.size() == 1) {
			   TurfInfos tinf = listByEnt.get(0);
			   tinf.setNumeroString(tinf.getNumero().toString());
			   newList.add(tinf);
		   }
		   
		   if(listByEnt.size() > 1) {
			   
			   TurfInfos tinf = new TurfInfos();
			   
			   tinf.setNumeroString("[" + listByEnt.get(0).getNumero().toString());
			   if(listByEnt.get(0).getPourcPlaceEntHippo()!=null) {
			   tinf.setPourcPlaceEntHippo(listByEnt.get(0).getPourcPlaceEntHippo());
			   }
			   if(listByEnt.get(0).getPourcVictEntHippo()!=null) {
			   tinf.setPourcVictEntHippo(listByEnt.get(0).getPourcVictEntHippo());
//			   tinf.setPourcVictEntHippo(listByEnt.get(0).getPourcVictEntHippo());
			   }
			   
			   for(int i =1; i<listByEnt.size(); i++) {
				   
				   tinf.setNumeroString(tinf.getNumeroString() + ", " + listByEnt.get(i).getNumero().toString());
			   }
			   tinf.setNumeroString(tinf.getNumeroString() + "]");

			   
			   newList.add(tinf);
		   }
	   }
	   List<TurfInfos> reverseList = newList.stream().sorted(Comparator.comparingDouble(TurfInfos::getPourcVictEntHippo)).collect(Collectors.toList());
	   Collections.reverse(reverseList);

	   
	   return reverseList;
   }
   
//   private List<TurfInfos> createClassementList(List<TurfInfos> allraceInfos){
//
//	   LinkedList<TurfInfos> list = new LinkedList<>();
//	   
//
//	   for(TurfInfos t: allraceInfos) {
////			System.out.println(t.getCl());
//		   
//			if(t.getRanking() == 1) {
//				t.setRanking(1);
//				list.add(t);
//			}
//			if(t.getRanking() == 2) {
//				t.setRanking(2);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 3) {
//				t.setRanking(3);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 4) {
//				t.setRanking(4);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 5) {
//				t.setRanking(5);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 6) {
//				t.setRanking(6);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 7) {
//				t.setRanking(7);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 8) {
//				t.setRanking(8);
//				list.add(t);
//
//			}
//			
//			if(t.getRanking() == 9) {
//				t.setRanking(9);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 10) {
//				t.setRanking(10);
//				list.add(t);
//			}
//			if(t.getRanking() == 11) {
//				t.setRanking(11);
//				list.add(t);
//			}
//			if(t.getRanking() == 12) {
//				t.setRanking(12);
//				list.add(t);
//			}
//			if(t.getRanking() == 13) {
//				t.setRanking(13);
//				list.add(t);
//
//			}
//			if(t.getRanking() == 14) {
//				t.setRanking(14);
//				list.add(t);
//			}
//			if(t.getRanking() == 15) {
//				t.setRanking(15);
//				list.add(t);
//			}
//			if(t.getRanking() == 16) {
//				t.setRanking(16);
//				list.add(t);
//			}
//			if(t.getRanking() == 17) {
//				t.setRanking(17);
//				list.add(t);
//			}
//			if(t.getRanking() == 18) {
//				t.setRanking(18);
//				list.add(t);
//			}
//			if(t.getRanking() == 19) {
//				t.setRanking(19);
//				list.add(t);
//			}
//			if(t.getRanking() == 20) {
//				t.setRanking(20);
//				list.add(t);
//			}
//		}
//	   
//	   List<TurfInfos> listByCl =  list.stream()
//				.filter(ti -> ti.getClInt()!= null)
//				.sorted(Comparator.comparingInt(TurfInfos::getClInt))
//				.collect(Collectors.toList());
//	   
//	   for(TurfInfos t: listByCl) {
//		   System.out.println(t.getClInt());
////		   System.out.println(t.getCl());
//
//	   }
//		System.out.println("Stop");
//	   
//	   return listByCl;
//   }
   
   private List<TurfInfos> setPastilles(List<TurfInfos> jockeys, List<TurfInfos> chronos, List<TurfInfos> vchevalh, List<TurfInfos> pchevalh, List<TurfInfos> vcouple,  List<TurfInfos> pcouple, List<TurfInfos> pronos, int raceSize){
	   
	   
	   
	   if(raceSize <= 7) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 5) {
			   if(!jockeys.subList(0, 5).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   }else {
				   ti.setJockeyPastille(false);
			   }
			  
			   
			   if(chronos.size() >= 5) {
			   if(!chronos.subList(0, 5).contains(ti)) {
				   ti.setChronoPastille(true);
			   }else {
				   ti.setChronoPastille(false);
			   }
			   }else {
				   ti.setChronoPastille(false);
			   }
		   });
	   }
	   
	   if(raceSize == 8 || raceSize == 9) {
		   pronos.forEach(ti-> {
			   if(jockeys == null) {
				   ti.setJockeyPastille(false);
			   }
			   if(chronos == null) {
				   ti.setChronoPastille(false);
			   }
			   if(vchevalh == null) {
				   ti.setChevalPastille(false);
			   }
			   if(jockeys.size() >= 6) {
			   if(!jockeys.subList(0, 6).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   if(chronos.size() >= 6) {
			   if(!chronos.subList(0, 6).contains(ti)) {
				   ti.setChronoPastille(true);
			   }else {
				   ti.setChronoPastille(false);
			   }
			   }else {
				   ti.setChronoPastille(false);
			   }
		   });
	   }
	   
	   if(raceSize == 10 || raceSize == 11) {
		   pronos.forEach(ti-> {
			   if(jockeys == null) {
				   ti.setJockeyPastille(false);
			   }
			   if(chronos == null) {
				   ti.setChronoPastille(false);
			   }
			   if(vchevalh == null) {
				   ti.setChevalPastille(false);
			   }
			   if(jockeys.size() >= 7) {
			   if(!jockeys.subList(0, 7).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   if(chronos.size() >= 7) {
			   if(!chronos.subList(0, 7).contains(ti)) {
				   ti.setChronoPastille(true);
			   }else {
				   ti.setChronoPastille(false);
			   }
			   }else {
				   ti.setChronoPastille(false);
			   }
		   });
	   }
	   
	   if(raceSize == 12 || raceSize == 13) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 8) {
			   if(!jockeys.subList(0, 8).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   if(chronos.size() >= 8) {
			   if(!chronos.subList(0, 8).contains(ti)) {
				   ti.setChronoPastille(true);
			   }else {
				   ti.setChronoPastille(false);
			   }
			   }else {
				   ti.setChronoPastille(false);
			   }
		   });
	   }
	   
	   if(raceSize >= 14) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 9) {
			   if(!jockeys.subList(0, 9).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   }else {
				   ti.setJockeyPastille(false);
			   }
			   if(chronos.size() >= 9) {
			   if(!chronos.subList(0, 9).contains(ti)) {
				   ti.setChronoPastille(true);
			   }else {
				   ti.setChronoPastille(false);
			   }
			   }else {
				   ti.setChronoPastille(false);
			   }
		   });
	   }
	   
	   pronos.forEach(ti-> {
		   if(!vchevalh.contains(ti) && !pchevalh.contains(ti)) {
			   ti.setChevalPastille(true);
		   }else {
			   ti.setChevalPastille(false);
		   }
		   if(!vcouple.contains(ti) && !pcouple.contains(ti)) {
			   ti.setCouplePastille(true);
		   }else {
			   ti.setCouplePastille(false);
		   }
		  	  
	   });
	   
	   

	   return pronos;
   }
   
	private List<TurfInfos> setEtoiles(
			  List<TurfInfos> chronos,
			  List<TurfInfos> entraineurs,
			  List<TurfInfos> jockeys,
			  List<TurfInfos> vchevalh,
			  List<TurfInfos> pchevalh,
			  List<TurfInfos> pcheval,
			  List<TurfInfos> vcouple,
			  List<TurfInfos> pcouple,
			  List<TurfInfos> vcoupleh,
			  List<TurfInfos> pcoupleh,
			  
			  List<TurfInfos> pronos, int raceSize){
		  
		  ///// Three Etoiles////////////
		   
			   pronos.forEach(ti-> {
				   
				   Integer threeEtoilesNumber = 0;
				   Integer fiveEtoilesNumber = 0;


				   
				   for(int i = 0; i < chronos.size(); i++) {
					   if(chronos.get(i).getId().equals(ti.getId())) {
						   threeEtoilesNumber += 1;
					   }
					   if(i == 3) {
						   break;
					   }
				   }
				   for(int i = 0; i < entraineurs.size(); i++) {
					   if(entraineurs.get(i).getId().equals(ti.getId())) {
						   threeEtoilesNumber += 1;
					   }
					   if(i == 3) {
						   break;
					   }
				   }
				   for(int i = 0; i < jockeys.size(); i++) {
					   if(jockeys.get(i).getId().equals(ti.getId())) {
						   threeEtoilesNumber += 1;
					   }
					   if(i == 3) {
						   break;
					   }
				   }
				   
					  ///// Five Etoiles////////////
						   
				  
	               for(int i = 0; i < chronos.size(); i++) {
					   if(chronos.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < entraineurs.size(); i++) {
					   if(entraineurs.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < jockeys.size(); i++) {
					   if(jockeys.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < vchevalh.size(); i++) {
					   if(vchevalh.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < pchevalh.size(); i++) {
					   if(pchevalh.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < pcheval.size(); i++) {
					   if(pcheval.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < vcouple.size(); i++) {
					   if(vcouple.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < pcouple.size(); i++) {
					   if(pcouple.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < vcoupleh.size(); i++) {
					   if(vcoupleh.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
	               for(int i = 0; i < pcoupleh.size(); i++) {
					   if(pcoupleh.get(i).getId().equals(ti.getId())) {
						   fiveEtoilesNumber += 1;
					   }
					   if(i == 4) {
						   break;
					   }
				   }
				   
				 
	               if(threeEtoilesNumber >= 3) {
					   ti.setThreeEtoile(true);
				   } else {
					   ti.setThreeEtoile(false);
				   }
				   
				   if(fiveEtoilesNumber >= 6) {
					   ti.setFiveEtoile(true);
				   } else {
					   ti.setFiveEtoile(false);
				   }
				   
				 
			   });
		   ////////////////////////////////////

			   

		   return pronos;
	   }
   
   private void navbarInfos(Model model) {
	   
	   List<TurfInfos> allInfos = turfInfosRepository.findAll();
	   
	   //DATES
  	 Set<String> dates = allInfos.stream()
				.map(TurfInfos :: getJour)
				.collect(Collectors.toSet());
       model.addAttribute("datesnav", dates);
	   
       //REUNIONS
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       String jour = LocalDateTime.now().format(formatter);
       model.addAttribute("journav", jour);
       
       
//    	 Set<String> reunions = allInfos.stream()
//				.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//  				.map(TurfInfos :: getReunionstring)
////  				.sorted()
//  				.collect(Collectors.toSet());
////    	 reunions.sort( Comparator.comparing( String::toString));
//         model.addAttribute("reunionsofday", reunions);

   
         Set<String> reunions = allInfos.stream()
 				.filter(ti->ti.getReunionstring() != null &&  ti.getJour().equals(jour) && ti.getR().length()<3)
        			.map(TurfInfos :: getReunionstring)
        			.collect(Collectors.toSet());
        			List<String> list = new ArrayList<String>(reunions);
        			Collections.sort(list);        			
        			reunions = new LinkedHashSet<>(list);
        	         model.addAttribute("reunionsofday", reunions);
   }
   
//   private Integer calculateNewPlacePercentage(Integer twoOrThreeRank, Integer nbCourses) {
//	   
//	   return (twoOrThreeRank * 100) /  nbCourses ;
//   }
    
}








































//package turfinfos2.service;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//
//import turfinfos2.model.ArchiveDeuxSur4;
//import turfinfos2.model.Resultat;
//import turfinfos2.model.TurfInfos;
//import turfinfos2.repository.ArchiveInfoRepository;
//import turfinfos2.repository.ResultRepository;
//import turfinfos2.repository.TurfInfosRepository;
//
//@Service
//public class ArchiveService {
//
//	@Autowired
//	TurfInfosRepository turfInfosRepository;
//	
//	@Autowired
//	TurfInfoService turfInfoService;
//	
//	@Autowired
//	ArchiveInfoRepository archiveInfosRepository;
//	
//	@Autowired
//	ResultRepository resultRepository;
//	
//	public List<TurfInfos> filterAll(List<TurfInfos> all) {
//		
//		List<TurfInfos> allFiltered = all
//				.stream()
//				.filter(ti->
//		        
//		        ti.getNumberOfInitialRunners() != null && ti.getNumberOfInitialRunners() > 1
////				&& ti.getHasBetTypes() == true 
//				&& ti.getLiveOdd() != null && ti.getLiveOdd() != 0 && ti.getLiveOdd() < 2.5
//		        && ti.getRecence() != null && ti.getRecence() < 60
//			    && ti.getMinRapportProbable() != null && ti.getMinRapportProbable() != 0 && ti.getMinRapportProbable() > 1.3d
////  	            && ti.getJour().contains("2021-12")
//
//						)
//				.collect(Collectors.toList());
//				
//		return allFiltered;
//	}
//	
//	public List<TurfInfos> createArchives (List<TurfInfos> listByNoteProno) {
//		
//		 List<TurfInfos> finalList = new ArrayList<>();
//		
//		if(listByNoteProno.size()>0) {
////			if(listByppc.size()>0) {		
//		
////			Optional<TurfInfos> optInfo = setPastilles(listBypvjh, listByChronos, listBypvch, listByppch, listBytxv, listBytxp, listByNoteProno, allraceInfos.size())
////					.stream()
//////					.filter(ti->ti.getLiveOdd()<2.5)
////					.findFirst();
//				Optional<TurfInfos> optInfo = Optional.of(listByNoteProno.get(0));
//			
//			if(optInfo.isPresent()) {
//				
//				TurfInfos info = optInfo.get();
//			
//			if(info.getLiveOddPlace() == null) {
//				info.setLiveOddPlace(0d);
//			}
//			
//            finalList.add(info);		
//			}
//			}
//		return finalList;	
//	}
//	
//	public List<ArchiveDeuxSur4> create2sur4(List<TurfInfos> prono, List<Resultat> results) {
//		
//		List<Resultat> allResults = new ArrayList<>();
//		allResults.addAll(results);
//		List<ArchiveDeuxSur4> deuxSurQuatrelist = new ArrayList<>();
//		ArchiveDeuxSur4 deuxSur4 = new ArchiveDeuxSur4();
//
//		
//		for(TurfInfos ti : prono){
//			
//			deuxSur4.setC(ti.getC());
//			deuxSur4.setJour(ti.getJour());
//			deuxSur4.setNumcourse(ti.getNumcourse());
//			deuxSur4.setR(ti.getR());
//			
//			System.out.println(allResults.get(0).getC());
//			
//			Resultat resultat = allResults
//					.stream()
//					.filter(r -> r.getC() == ti.getC() && r.getR() == ti.getR() && r.getJour() == ti.getJour())
//					.findFirst().get();
//			System.out.println(resultat.toString() +" resultat");
//
//						
//			Double rapport = resultat.getDeuxSurQuatre().get("type-name");
//			System.out.println(rapport +" rapp");
//
//			deuxSur4.setRapport(rapport);
//			deuxSur4.setArrivee("-" + resultat.getDeuxSurQuatre().get("type-name") + "-");
//			System.out.println(deuxSur4.getArrivee() +" arrivee");
//
//			
//			if(ti.getFiveEtoile().equals(true) || ti.getThreeEtoile().equals(true)) {
//				deuxSur4.getCombinaison().add(ti.getNumero());
//				if(deuxSur4.getCombinaison().size() >= 2) {
////					if(deuxSur4.getCombinaison().get(0).getr)
//					deuxSur4.setIsWon(true);
//					deuxSurQuatrelist.add(deuxSur4);
//					deuxSur4 = new ArchiveDeuxSur4();
//				}
//			}
//			
//		};
//		
//		return deuxSurQuatrelist;
//	}
//
////	public List<TurfInfos> setAllPlaceFirstPronoList(List<TurfInfos> allList) {
//	public Map<String, Object> setAllPlaceFirstPronoList(List<TurfInfos> allList, List<Resultat> allResults, Model model) {
//				
//		Map<String, Object> map = new HashMap<>();
//		
////		final List<TurfInfos> all = allList;
//		
//		List<TurfInfos> finalList = new ArrayList<>();
//		List<ArchiveDeuxSur4> deuxSur4List = new ArrayList<>();
//
//		
//		//Jours
//				Set<String> distinctJours = allList.stream()
//				.map(TurfInfos :: getJour)
////				.sorted()
//				.collect(Collectors.toSet());
//				List<String> list = new ArrayList<String>(distinctJours);
//				Collections.reverse(list);
//				distinctJours = new LinkedHashSet<>(list);
//				
//				distinctJours.forEach(j->{
//					
//					  Set<String> reunions = allList.stream()
//				 				.filter(ti -> ti.getJour().equals(j))
//				        			.map(TurfInfos :: getReunionstring)
//				        			.collect(Collectors.toSet());
//				        			List<String> reunionsList = new ArrayList<String>(reunions);
////				        			Collections.sort(reunionsList);        			
//				        			reunions = new LinkedHashSet<>(reunionsList);
//         				
//				     reunions.forEach(r->{
//				    	 
//				    	 ;
//				    	 deuxSur4List.addAll((List<ArchiveDeuxSur4>) setPlaceFirstPronoListByDay(j, r, allList, allResults, model).get("2sur4"));
//				    	 finalList.addAll((List<TurfInfos>) setPlaceFirstPronoListByDay(j, r, allList, allResults, model).get("turfinfos"));
//
//				    	 
//				     });
//				});
//				map.put("turfinfos", finalList);
//				map.put("2sur4", deuxSur4List);
//				System.out.println("big list = " + finalList.size());
//				return map;
//	}
//	
////	  public List<TurfInfos> setPlaceFirstPronoListByDay(String jour,
////	    		String reunionstring, List<TurfInfos> all, Model model
////	    		) { 
//    public Map<String, Object> setPlaceFirstPronoListByDay(String jour,
//    		String reunionstring, List<TurfInfos> all, List<Resultat> allResults, Model model
//    		) {   
//    	
////    	if(principal == null) {
////			return "redirect:/login";
////    	}
//
//		model.addAttribute("date", jour);
//		model.addAttribute("recencemax", 60);
//		
//		Map<String, Object> map = new HashMap<>();
//		
//		List<TurfInfos> finalList = new ArrayList<>();
//		List<ArchiveDeuxSur4> deuxSur4list = new ArrayList<>();
//
//
//		// RACESLIST
//		List<TurfInfos> allPremiumReunionInfos = all.stream().filter(ti -> ti.getJour() == jour && ti.getReunionstring() == reunionstring && ti.getIsRunning() != null
//						&& ti.getIsRunning() == true && ti.getIsPremium() != null && ti.getIsPremium().equals(true))
//				.collect(Collectors.toList());
//
//		List<TurfInfos> reunionCracks = new ArrayList<>();
//		Map<String, String> tayPronos = new HashMap<>();
//
//		// Num des courses
//		Set<Integer> distinctNumraces = allPremiumReunionInfos.stream().map(TurfInfos::getC).sorted()
//				.collect(Collectors.toSet());
//		List<Integer> list = new ArrayList<Integer>(distinctNumraces);
//		Collections.reverse(list);
//		distinctNumraces = new LinkedHashSet<>(list);
//
//		// Cast
////		 LinkedList<String> distinctNumracesString = new LinkedList<>();
////				 
////		    for(Integer i : distinctNumraces) {
////		    	distinctNumracesString.add(i.toString());
////		    }
//
////		System.out.println(distinctNumraces.size());
//
//		model.addAttribute("distinctnumraces", distinctNumraces);
//
//		List<TurfInfos> allraceInfos = new ArrayList<>();
//		for (Integer num : distinctNumraces) {
//
//			// Infos de la course en question
//			allraceInfos = allPremiumReunionInfos.stream().filter(ti -> ti.getC().equals(num))
//					.collect(Collectors.toList());
//
//			model.addAttribute("racesize", allraceInfos.size());
//
//			model.addAttribute(numToString(num) + "ispick5", allraceInfos.get(0).getIsPick5());
//			model.addAttribute(numToString(num) + "istqq", allraceInfos.get(0).getIsTQQ());
//			model.addAttribute(numToString(num) + "runners", allraceInfos.get(0).getNumberOfInitialRunners());
//
//			String nonPartants = "";
//			if (allraceInfos.get(0).getNumberOfNonRunners() != null
//					&& allraceInfos.get(0).getNumberOfNonRunners() > 0) {
//				nonPartants = "(" + allraceInfos.get(0).getNumberOfNonRunners() + " NP)";
//			}
//			model.addAttribute(numToString(num) + "caralist",
//					allraceInfos.get(0).getHour() + " - " + allraceInfos.get(0).getCaraList1() + " - "
//							+ allraceInfos.get(0).getCaraList2() + " - "
//							+ allraceInfos.get(0).getNumberOfInitialRunners() + " partants " + nonPartants);
//			model.addAttribute(numToString(num) + "hasbettypes", allraceInfos.get(0).getHasBetTypes());
//
////			});
//
////			createClassementList(allraceInfos);
//
//			// création des listes filtrées et triées par parametre voulu
//
//			List<TurfInfos> listBypvch = allraceInfos.stream()
//					.filter(ti -> ti.getPourcVictChevalHippo() != null && ti.getPourcVictChevalHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictChevalHippo))
//					.collect(Collectors.toList());
//			Collections.reverse(listBypvch);
//
//			List<TurfInfos> listBypvjh = allraceInfos.stream()
//					.filter(ti -> ti.getPourcVictJockHippo() != null && ti.getPourcVictJockHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictJockHippo)).collect(Collectors.toList());
//			Collections.reverse(listBypvjh);
//
//			List<TurfInfos> listBypveh = allraceInfos.stream()
//					.filter(ti -> ti.getPourcVictEntHippo() != null && ti.getPourcVictEntHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictEntHippo)).collect(Collectors.toList());
//			Collections.reverse(listBypveh);
//
//			allraceInfos.forEach(ti -> {
//				if (ti.getPourcVictChevalHippo() != null
//						&& ti.getPourcVictChevalHippo().equals(ti.getPourcPlaceChevalHippo())) {
//					ti.setPourcPlaceChevalHippo(0d);
//				}
//			});
//
//			List<TurfInfos> listByppch = allraceInfos.stream()
//					.filter(ti -> ti.getPourcPlaceChevalHippo() != null && ti.getPourcPlaceChevalHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceChevalHippo))
//					.collect(Collectors.toList());
//			Collections.reverse(listByppch);
//
//			List<TurfInfos> listByppc = allraceInfos.stream()
//					.filter(ti -> ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() != 0d).sorted(Comparator
//							.comparingDouble(TurfInfos::getPourcPlaceCheval).thenComparingInt(TurfInfos::getNumero))
//					.collect(Collectors.toList());
//			Collections.reverse(listByppc);
//
//			List<TurfInfos> listByppjh = allraceInfos.stream()
//					.filter(ti -> ti.getPourcPlaceJockHippo() != null && ti.getPourcPlaceJockHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceJockHippo)).collect(Collectors.toList());
//			Collections.reverse(listByppjh);
//
//			List<TurfInfos> listByppeh = allraceInfos.stream()
//					.filter(ti -> ti.getPourcPlaceEntHippo() != null && ti.getPourcPlaceEntHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceEntHippo)).collect(Collectors.toList());
//			Collections.reverse(listByppeh);
//
//			List<TurfInfos> listBytxv = allraceInfos.stream()
//					.filter(ti -> ti.getTxVictCouple() != null && ti.getTxVictCouple() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getTxVictCouple)).collect(Collectors.toList());
//			Collections.reverse(listBytxv);
//
//			allraceInfos.forEach(ti -> {
////				System.out.println(ti.getR()+ti.getC());
////				System.out.println("t-"+ti.getTxVictCouple());
////				System.out.println("tttt-"+ti.getTxPlaceCouple());
////				if(ti.getCoupleTwoOrThree() != null && !ti.getCoupleTwoOrThree().equals(0) && ti.getNbCourseCouple() != null && !ti.getNbCourseCouple().equals( 0)) {
////				ti.setTxPlaceCouple(calculateNewPlacePercentage(ti.getCoupleTwoOrThree(), ti.getNbCourseCouple()-ti.getNbVictCouple()).doubleValue());
////				}
////				if(ti.getCoupleTwoOrThree() != null && ti.getCoupleTwoOrThree().equals(0)) {
////					ti.setTxPlaceCouple(0d);
////					}
////				System.out.println("tttt-"+ti.getTxPlaceCouple());
////				System.out.println();
////				
//				if (ti.getTxVictCouple() != null && ti.getTxVictCouple().equals(ti.getTxPlaceCouple())) {
//					ti.setTxPlaceCouple(0d);
//				}
//			});
//
//			List<TurfInfos> listBytxp = allraceInfos.stream()
//					.filter(ti -> ti.getTxPlaceCouple() != null && ti.getTxPlaceCouple() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getTxPlaceCouple)).collect(Collectors.toList());
//			Collections.reverse(listBytxp);
//
//			List<TurfInfos> listBytxvh = allraceInfos.stream()
//					.filter(ti -> ti.getTxVictCoupleHippo() != null && ti.getTxVictCoupleHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getTxVictCoupleHippo)).collect(Collectors.toList());
//			Collections.reverse(listBytxvh);
//
//			allraceInfos.forEach(ti -> {
//				if (ti.getTxVictCoupleHippo() != null && ti.getTxVictCoupleHippo().equals(ti.getTxPlaceCoupleHippo())) {
//					ti.setTxPlaceCoupleHippo(0d);
//				}
//			});
//
//			List<TurfInfos> listBytxph = allraceInfos.stream()
//					.filter(ti -> ti.getTxPlaceCoupleHippo() != null && ti.getTxPlaceCoupleHippo() != 0d)
//					.sorted(Comparator.comparingDouble(TurfInfos::getTxPlaceCoupleHippo)).collect(Collectors.toList());
//			Collections.reverse(listBytxph);
//
//			// CHRONOS
//			List<TurfInfos> listByChronos = allraceInfos.stream().filter(ti -> ti.getChrono() != null)
//					.sorted(Comparator.comparingInt(TurfInfos::getChrono)).collect(Collectors.toList());
////			Collections.reverse(listBypvch);
//
//			// TayPronos
//			List<TurfInfos> listByTayPronos = allraceInfos.stream().filter(ti -> ti.getTayProno() != null)
//					.sorted(Comparator.comparingInt(TurfInfos::getTayProno)).collect(Collectors.toList());
////			Collections.reverse(listBypvch);
//
//			if (listByTayPronos.size() > 0) {
//				String prono = "";
//				for (TurfInfos info : listByTayPronos) {
//					prono = prono + " - " + info.getNumero();
//				}
//				String result = prono.substring(3);
//				System.out.println(result + "PRONO C " + num);
//				tayPronos.put("C" + num, result);
//			}
//			// list par chrono Paris turf
////			List<TurfInfos> listByChronoParisTurf = allraceInfos.stream()
////					.filter(ti -> ti.getDistanceAndSpecialtyChrono()!= null)
////					.sorted(Comparator.comparingInt(TurfInfos::getTayProno))
////					.collect(Collectors.toList());
//////			Collections.reverse(listBypvch);
//
//			List<TurfInfos> listByChronoParisTurf = allraceInfos.stream()
//					.filter(ti -> ti.getDistanceAndSpecialtyChrono() != null).sorted(Comparator
//							.comparing(TurfInfos::getDistanceAndSpecialtyChrono).thenComparing(TurfInfos::getNumero))
//					.collect(Collectors.toList());
////		        			for(TurfInfos info: listByChronoParisTurf) {
////		        				System.out.println(info.getDistanceAndSpecialtyChrono());
////		        			}
//
//			// CLASSEMENT A L'ARRIVEE
//			List<TurfInfos> listByRanking = allraceInfos.stream()
//					.filter(ti -> ti.getRanking() != null && ti.getRanking() != 0)
//					.sorted(Comparator.comparingInt(TurfInfos::getRanking)).collect(Collectors.toList());
//
//			// CORDES
//			List<TurfInfos> listByDraw = allraceInfos.stream().filter(ti -> ti.getDraw() != null && ti.getDraw() != 0)
//					.sorted(Comparator.comparingInt(TurfInfos::getDraw)).collect(Collectors.toList());
//
//			// Calcul de la note
//			List<TurfInfos> listByNoteProno = calculateFinalNoteProno(allraceInfos, listBypvch, listBypvjh, listBypveh,
//
//					listByppch, listByppjh, listByppeh,
//
//					listBytxv, listBytxp, listBytxvh, listBytxph,
//
//					listByppc, listByChronos, model)
//
//							.stream().filter(ti -> ti.getNoteProno() != null && ti.getNoteProno() > 0)
//							.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno)).collect(Collectors.toList());
//			Collections.reverse(listByNoteProno);
//
//
//			reunionCracks.addAll(listByNoteProno);
//
//			///////////// AFFECTER PASTILLES///////////////
//			setPastilles(listBypvjh, listByChronos, listBypvch, listByppch, listBytxv, listBytxp, listByNoteProno,
//					allraceInfos.size());
//            ///////////// AFFECTER ETOILES///////////////
//			setEtoiles(listByChronos,listBypveh, listBypvjh, listBypvch, listByppch, listByppc, listBytxv, listBytxp, listBytxvh, listBytxph,
//					listByNoteProno,
//					allraceInfos.size());
//			
//			
//			
//			////////////////////////////////
//			
//			
////			if(allraceInfos.stream().findFirst().get().getNumberOfInitialRunners() > 9) {
////				deuxSur4list.addAll(create2sur4(listByNoteProno, allResults));
////			}
//
//			finalList.addAll(createArchives(listByNoteProno));
//			
//			
//			/////////////////////////////////
//			
//			
//		}
////
////		//////////// CRACKS/////////////////
////		List<TurfInfos> crackList = reunionCracks.stream().filter(ti -> ti.getNoteProno() >= 30)
////				.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno)).collect(Collectors.toList());
////		Collections.reverse(crackList);
////
////		model.addAttribute("cracklist", crackList);
////		model.addAttribute("supercracknote", 35);
////
////////////////Placés du jour/////////////////
////		List<TurfInfos> placeList = allPremiumReunionInfos.stream()
////				.filter(ti -> ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() == 100)
////				.sorted(Comparator.comparingInt(TurfInfos::getC).thenComparingInt(TurfInfos::getNumero))
////				.collect(Collectors.toList());
//////	Collections.reverse(placeList);
////
////		model.addAttribute("placelist", placeList);
////
////		////////// COUPLES DU JOUR//////////////
////		Set<String> cples = new HashSet<>();
////		for (TurfInfos info : crackList) {
////
////			List<TurfInfos> couple = crackList.stream().filter(ti -> ti.getC() == info.getC())
////					.sorted(Comparator.comparingInt(TurfInfos::getNumero)).collect(Collectors.toList());
////			if (couple.size() >= 2) {
////				String C = couple.get(0).getC().toString();
////				String one = couple.get(0).getNumero().toString();
////				String two = couple.get(1).getNumero().toString();
////
////				if (couple.get(0).getNumero() < 10) {
////					one = "0" + couple.get(0).getNumero();
////				}
////				if (couple.get(1).getNumero() < 10) {
////					two = "0" + couple.get(1).getNumero().toString();
////				}
////
////				String cple = C + one + " - " + C + two;
////				cples.add(cple);
////			}
////		}
////		List<String> couples = cples.stream().sorted().collect(Collectors.toList());
//////  		model.addAttribute("couples", new ArrayList<>());
////		model.addAttribute("couples", couples);
////
////		model.addAttribute("taypronos", tayPronos);
////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////		///////////// reusOfDay
////		Set<String> reunions = turfInfosRepository.findAllByJour(jour).stream()
////				.filter(ti -> ti.getJour().equals(jour) && ti.getR().length() < 3).map(TurfInfos::getReunionstring)
////				.collect(Collectors.toSet());
////		List<String> list2 = new ArrayList<String>(reunions);
////		Collections.sort(list2);
////		reunions = new LinkedHashSet<>(list2);
////		model.addAttribute("reunionsofday", reunions);
////
////		model.addAttribute("reusofday", reunions);
////		model.addAttribute("reunionstring", reunionstring);
////		model.addAttribute("jour", jour);
////
//////	         allraceInfos.forEach(null)
////
//
////		return finalList;
//		map.put("turfinfos", finalList);
//		map.put("2sur4", deuxSur4list);
//		return map;
//
//	}
//
//	///////////////////////////////////////////////////////////////
//	private String numToString(Integer num) {
//
//		String str = null;
//
//		switch (num) {
//
//		case 1:
//			str = "one";
//			break;
//
//		case 2:
//			str = "two";
//			break;
//
//		case 3:
//			str = "three";
//			break;
//
//		case 4:
//			str = "four";
//			break;
//
//		case 5:
//			str = "five";
//			break;
//
//		case 6:
//			str = "six";
//			break;
//
//		case 7:
//			str = "seven";
//			break;
//
//		case 8:
//			str = "eight";
//			break;
//
//		case 9:
//			str = "nine";
//			break;
//
//		case 10:
//			str = "ten";
//			break;
//
//		case 11:
//			str = "eleven";
//			break;
//
//		case 12:
//			str = "twelve";
//			break;
//
//		}
//
//		return str;
//	}
//
//	private String numToStringNumber(Integer num) {
//
//		String str = null;
//
//		switch (num) {
//
//		case 1:
//			str = "1";
//			break;
//
//		case 2:
//			str = "2";
//			break;
//
//		case 3:
//			str = "3";
//			break;
//
//		case 4:
//			str = "4";
//			break;
//
//		case 5:
//			str = "5";
//			break;
//
//		case 6:
//			str = "6";
//			break;
//
//		case 7:
//			str = "7";
//			break;
//
//		case 8:
//			str = "8";
//			break;
//
//		case 9:
//			str = "9";
//			break;
//
//		case 10:
//			str = "10";
//			break;
//
//		case 11:
//			str = "11";
//			break;
//
//		case 12:
//			str = "12";
//			break;
//
//		}
//
//		return str;
//	}
//
//	private List<TurfInfos> calculateFinalNoteProno(List<TurfInfos> allraceInfos, List<TurfInfos> listBypvch,
//			List<TurfInfos> listBypvjh, List<TurfInfos> listBypveh,
//
//			List<TurfInfos> listByppch, List<TurfInfos> listByppjh, List<TurfInfos> listByppeh,
//
//			List<TurfInfos> listBytxv, List<TurfInfos> listBytxp, List<TurfInfos> listBytxvh,
//			List<TurfInfos> listBytxph, List<TurfInfos> listByppc,
//
//			List<TurfInfos> listByChronos, Model model) {
//
//		calculateNoteProno(allraceInfos, listBypvch, 1d);
//		calculateNoteProno(allraceInfos, listBypvjh, 2d);
//		calculateNoteProno(allraceInfos, listBypveh, 3d);
//
//		calculateNoteProno(allraceInfos, listByppch, 4d);
////	   calculateNoteProno(allraceInfos, listByppjh, 5d);
////	   calculateNoteProno(allraceInfos, listByppeh, 6d);
//
//		calculateNoteProno(allraceInfos, listBytxv, 7d);
//		calculateNoteProno(allraceInfos, listBytxp, 8d);
//		calculateNoteProno(allraceInfos, listBytxvh, 9d);
//		calculateNoteProno(allraceInfos, listBytxph, 10d);
//
////	   calculateNoteProno(allraceInfos, listByChronos, 11d);
//		calculateNoteProno(allraceInfos, listByppc, 12d);
//
////	   System.out.println((70d/100)*75d + "tauxcrack");
//
//		return allraceInfos;
//	}
//
//	private List<TurfInfos> calculateNoteProno(List<TurfInfos> allRaceInfos, List<TurfInfos> list,
//			Double percentageParameter) {
//
//		for (int i = 0; i < list.size(); i++) {
//
//			if (list.size() > 0 && i == 0) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//
//			if (list.size() > 1 && i == 1) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//
//			if (list.size() > 2 && i == 2) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 3 && i == 3) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 4 && i == 4) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 5 && i == 5) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 6 && i == 6) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 7 && i == 7) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 8 && i == 8) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 9 && i == 9) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 10 && i == 10) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 11 && i == 11) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 12 && i == 12) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 13 && i == 13) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 14 && i == 14) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 15 && i == 15) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 16 && i == 16) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 17 && i == 17) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 18 && i == 18) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 19 && i == 19) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//			if (list.size() > 20 && i == 20) {
//				setPercentageParam(list.get(i), percentageParameter);
//				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
//			}
//		}
//
//		return list;
//	}
//
//	private TurfInfos setPercentageParam(TurfInfos tinf, Double percentParam) {
//
//		if (percentParam == 1d) {
//			tinf.setNotePercentageParameter(tinf.getPourcVictChevalHippo());
//		}
//		if (percentParam == 2d) {
//			tinf.setNotePercentageParameter(tinf.getPourcVictJockHippo());
//		}
//		if (percentParam == 3d) {
//			tinf.setNotePercentageParameter(tinf.getPourcVictEntHippo());
//		}
//		if (percentParam == 4d) {
//			tinf.setNotePercentageParameter(tinf.getPourcPlaceChevalHippo());
//		}
//		if (percentParam == 5d) {
//			tinf.setNotePercentageParameter(tinf.getPourcPlaceJockHippo());
//		}
//		if (percentParam == 6d) {
//			tinf.setNotePercentageParameter(tinf.getPourcPlaceEntHippo());
//		}
//		if (percentParam == 7d) {
//			tinf.setNotePercentageParameter(tinf.getTxVictCouple());
//		}
//		if (percentParam == 8d) {
//			tinf.setNotePercentageParameter(tinf.getTxPlaceCouple());
//		}
//		if (percentParam == 9d) {
//			tinf.setNotePercentageParameter(tinf.getTxVictCoupleHippo());
//		}
//		if (percentParam == 10d) {
//			tinf.setNotePercentageParameter(tinf.getTxPlaceCoupleHippo());
//		}
//
////	   if(percentParam == 11d) {
////		   tinf.setNotePercentageParameter(tinf.getChrono().doubleValue());
////	   }
//		if (percentParam == 12d) {
//			tinf.setNotePercentageParameter(tinf.getPourcPlaceCheval());
//		}
//
//		return tinf;
//	}
//
//	private List<TurfInfos> calculateEntraineur(List<TurfInfos> AllRaceinfos) {
//
//		LinkedList<TurfInfos> newList = new LinkedList<TurfInfos>();
//
//		Set<String> distinctEntraineurs = AllRaceinfos.stream().map(TurfInfos::getEntraineur)
////				.sorted()
//				.collect(Collectors.toSet());
//
//		for (String entraineur : distinctEntraineurs) {
//
////		   List<TurfInfos> listByEnt = turfInfosRepository.findAllByNumcourseAndEntraineur(numcourse, entraineur);
//
//			List<TurfInfos> listByEnt = AllRaceinfos.stream().filter(ti -> ti.getEntraineur().equals(entraineur))
//					.collect(Collectors.toList());
//
//			if (listByEnt.size() == 1) {
//				TurfInfos tinf = listByEnt.get(0);
//				tinf.setNumeroString(tinf.getNumero().toString());
//				newList.add(tinf);
//			}
//
//			if (listByEnt.size() > 1) {
//
//				TurfInfos tinf = new TurfInfos();
//
//				tinf.setNumeroString("[" + listByEnt.get(0).getNumero().toString());
//				if (listByEnt.get(0).getPourcPlaceEntHippo() != null) {
//					tinf.setPourcPlaceEntHippo(listByEnt.get(0).getPourcPlaceEntHippo());
//				}
//				if (listByEnt.get(0).getPourcVictEntHippo() != null) {
//					tinf.setPourcVictEntHippo(listByEnt.get(0).getPourcVictEntHippo());
////			   tinf.setPourcVictEntHippo(listByEnt.get(0).getPourcVictEntHippo());
//				}
//
//				for (int i = 1; i < listByEnt.size(); i++) {
//
//					tinf.setNumeroString(tinf.getNumeroString() + ", " + listByEnt.get(i).getNumero().toString());
//				}
//				tinf.setNumeroString(tinf.getNumeroString() + "]");
//
//				newList.add(tinf);
//			}
//		}
//		List<TurfInfos> reverseList = newList.stream()
//				.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictEntHippo)).collect(Collectors.toList());
//		Collections.reverse(reverseList);
//
//		return reverseList;
//	}
//
//
//
//	private List<TurfInfos> setPastilles(List<TurfInfos> jockeys, List<TurfInfos> chronos, List<TurfInfos> vchevalh,
//			List<TurfInfos> pchevalh, List<TurfInfos> vcouple, List<TurfInfos> pcouple, List<TurfInfos> pronos,
//			int raceSize) {
//
//		if (raceSize == 7) {
//			pronos.forEach(ti -> {
//				if (jockeys.size() >= 5) {
//					if (!jockeys.subList(0, 5).contains(ti)) {
//						ti.setJockeyPastille(true);
//					} else {
//						ti.setJockeyPastille(false);
//					}
//				}
//				if (chronos.size() >= 5) {
//					if (!chronos.subList(0, 5).contains(ti)) {
//						ti.setChronoPastille(true);
//					} else {
//						ti.setChronoPastille(false);
//					}
//				}
//			});
//		}
//
//		if (raceSize == 8 || raceSize == 9) {
//			pronos.forEach(ti -> {
//				if (jockeys.size() >= 6) {
//					if (!jockeys.subList(0, 6).contains(ti)) {
//						ti.setJockeyPastille(true);
//					} else {
//						ti.setJockeyPastille(false);
//					}
//				}
//				if (chronos.size() >= 6) {
//					if (!chronos.subList(0, 6).contains(ti)) {
//						ti.setChronoPastille(true);
//					} else {
//						ti.setChronoPastille(false);
//					}
//				}
//			});
//		}
//
//		if (raceSize == 10 || raceSize == 11) {
//			pronos.forEach(ti -> {
//				if (jockeys.size() >= 7) {
//					if (!jockeys.subList(0, 7).contains(ti)) {
//						ti.setJockeyPastille(true);
//					} else {
//						ti.setJockeyPastille(false);
//					}
//				}
//				if (chronos.size() >= 7) {
//					if (!chronos.subList(0, 7).contains(ti)) {
//						ti.setChronoPastille(true);
//					} else {
//						ti.setChronoPastille(false);
//					}
//				}
//			});
//		}
//
//		if (raceSize == 12 || raceSize == 13) {
//			pronos.forEach(ti -> {
//				if (jockeys.size() >= 8) {
//					if (!jockeys.subList(0, 8).contains(ti)) {
//						ti.setJockeyPastille(true);
//					} else {
//						ti.setJockeyPastille(false);
//					}
//				}
//				if (chronos.size() >= 8) {
//					if (!chronos.subList(0, 8).contains(ti)) {
//						ti.setChronoPastille(true);
//					} else {
//						ti.setChronoPastille(false);
//					}
//				}
//			});
//		}
//
//		if (raceSize >= 14) {
//			pronos.forEach(ti -> {
//				if (jockeys.size() >= 9) {
//					if (!jockeys.subList(0, 9).contains(ti)) {
//						ti.setJockeyPastille(true);
//					} else {
//						ti.setJockeyPastille(false);
//					}
//				}
//				if (chronos.size() >= 9) {
//					if (!chronos.subList(0, 9).contains(ti)) {
//						ti.setChronoPastille(true);
//					} else {
//						ti.setChronoPastille(false);
//					}
//				}
//			});
//		}
//
//		pronos.forEach(ti -> {
//			if (!vchevalh.contains(ti) && !pchevalh.contains(ti)) {
//				ti.setChevalPastille(true);
//			} else {
//				ti.setChevalPastille(false);
//			}
//			if (!vcouple.contains(ti) && !pcouple.contains(ti)) {
//				ti.setCouplePastille(true);
//			} else {
//				ti.setCouplePastille(false);
//			}
//
//		});
//
//		return pronos;
//	}
//
//	private List<TurfInfos> setEtoiles(
//		  List<TurfInfos> chronos,
//		  List<TurfInfos> entraineurs,
//		  List<TurfInfos> jockeys,
//		  List<TurfInfos> vchevalh,
//		  List<TurfInfos> pchevalh,
//		  List<TurfInfos> pcheval,
//		  List<TurfInfos> vcouple,
//		  List<TurfInfos> pcouple,
//		  List<TurfInfos> vcoupleh,
//		  List<TurfInfos> pcoupleh,
//		  
//		  List<TurfInfos> pronos, int raceSize){
//	  
//	  ///// Three Etoiles////////////
//	   
//		   pronos.forEach(ti-> {
//			   
//			   Integer threeEtoilesNumber = 0;
//			   Integer fiveEtoilesNumber = 0;
//
//
//			   
//			   for(int i = 0; i < chronos.size(); i++) {
//				   if(chronos.get(i).getId().equals(ti.getId())) {
//					   threeEtoilesNumber += 1;
//				   }
//				   if(i == 3) {
//					   break;
//				   }
//			   }
//			   for(int i = 0; i < entraineurs.size(); i++) {
//				   if(entraineurs.get(i).getId().equals(ti.getId())) {
//					   threeEtoilesNumber += 1;
//				   }
//				   if(i == 3) {
//					   break;
//				   }
//			   }
//			   for(int i = 0; i < jockeys.size(); i++) {
//				   if(jockeys.get(i).getId().equals(ti.getId())) {
//					   threeEtoilesNumber += 1;
//				   }
//				   if(i == 3) {
//					   break;
//				   }
//			   }
//			   
//				  ///// Five Etoiles////////////
//					   
//			  
//               for(int i = 0; i < chronos.size(); i++) {
//				   if(chronos.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < entraineurs.size(); i++) {
//				   if(entraineurs.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < jockeys.size(); i++) {
//				   if(jockeys.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < vchevalh.size(); i++) {
//				   if(vchevalh.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < pchevalh.size(); i++) {
//				   if(pchevalh.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < pcheval.size(); i++) {
//				   if(pcheval.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < vcouple.size(); i++) {
//				   if(vcouple.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < pcouple.size(); i++) {
//				   if(pcouple.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < vcoupleh.size(); i++) {
//				   if(vcoupleh.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//               for(int i = 0; i < pcoupleh.size(); i++) {
//				   if(pcoupleh.get(i).getId().equals(ti.getId())) {
//					   fiveEtoilesNumber += 1;
//				   }
//				   if(i == 4) {
//					   break;
//				   }
//			   }
//			   
//			 
//               if(threeEtoilesNumber >= 3) {
//				   ti.setThreeEtoile(true);
//			   } else {
//				   ti.setThreeEtoile(false);
//			   }
//			   
//			   if(fiveEtoilesNumber >= 6) {
//				   ti.setFiveEtoile(true);
//			   } else {
//				   ti.setFiveEtoile(false);
//			   }
//			   
//			 
//		   });
//	   ////////////////////////////////////
//
//		   
//
//	   return pronos;
//   }
//
//	private void navbarInfos(Model model) {
//
//		List<TurfInfos> allInfos = turfInfosRepository.findAll();
//
//		// DATES
//		Set<String> dates = allInfos.stream().map(TurfInfos::getJour).sorted().collect(Collectors.toSet());
//		List<String> datesSorted = dates.stream().collect(Collectors.toList());
//		Collections.sort(datesSorted, (o1, o2) -> o1.compareTo(o2));
//		model.addAttribute("datesnav", datesSorted);
//		// REUNIONS
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String jour = LocalDateTime.now().format(formatter);
//		model.addAttribute("journav", jour);
//
////    	 Set<String> reunions = allInfos.stream()
////				.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
////  				.map(TurfInfos :: getReunionstring)
//////  				.sorted()
////  				.collect(Collectors.toSet());
//////    	 reunions.sort( Comparator.comparing( String::toString));
////         model.addAttribute("reunionsofday", reunions);
//
//		Set<String> reunions = allInfos.stream()
//				.filter(ti -> ti.getReunionstring() != null && ti.getJour().equals(jour) && ti.getR().length() < 3)
//				.map(TurfInfos::getReunionstring).collect(Collectors.toSet());
//		List<String> list = new ArrayList<String>(reunions);
//		Collections.sort(list);
//		reunions = new LinkedHashSet<>(list);
//		model.addAttribute("reunionsofday", reunions);
//	}
//
////   private Integer calculateNewPlacePercentage(Integer twoOrThreeRank, Integer nbCourses) {
////	   
////	   return (twoOrThreeRank * 100) /  nbCourses ;
////   }
//
//}