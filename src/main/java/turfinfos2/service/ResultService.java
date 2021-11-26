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

import turfinfos2.model.ArchiveInfo;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ArchiveInfoRepository;
import turfinfos2.repository.TurfInfosRepository;

@Service
public class ResultService {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	TurfInfoService turfInfoService;
	
	@Autowired
	ArchiveInfoRepository archiveInfosRepository;


	public List<ArchiveInfo> setAllPlaceFirstPronoList( ) {
		
		List<TurfInfos> all = turfInfosRepository.findAll();
		
		List<ArchiveInfo> finalList = new ArrayList<>();
		
		//Jours
				Set<String> distinctJours = all.stream()
				.map(TurfInfos :: getJour)
//				.sorted()
				.collect(Collectors.toSet());
				List<String> list = new ArrayList<String>(distinctJours);
				Collections.reverse(list);
				distinctJours = new LinkedHashSet<>(list);
				
				distinctJours.forEach(j->{
					
					  Set<String> reunions = turfInfosRepository.findAllByJour(j).stream()
//				 				.filter(ti->ti.getReunionstring() != null &&  ti.getJour().equals(jour) && ti.getR().length()<3)
				        			.map(TurfInfos :: getReunionstring)
				        			.collect(Collectors.toSet());
				        			List<String> reunionsList = new ArrayList<String>(reunions);
//				        			Collections.sort(reunionsList);        			
				        			reunions = new LinkedHashSet<>(reunionsList);
         				
				     reunions.forEach(r->{
				    	 
				    	 ;
				    	 finalList.addAll(setPlaceFirstPronoList(j, r, all));
				    	 
				     });
				});
				System.out.println("big list = " + finalList.size());
				return finalList;
	}
    
    public List<ArchiveInfo> setPlaceFirstPronoList(String jour,
    		String reunionstring, List<TurfInfos> all
    		) {   
    	
//    	if(principal == null) {
//			return "redirect:/login";
//    	}
    	

    	
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
    	List<ArchiveInfo> finalList = new ArrayList<>();

    			
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
			setPastilles(listBypvjh, listByChronos, listByNoteProno, allraceInfos.size());
						
			
			
			if(listByNoteProno.size()>0) {
								
		
			TurfInfos ti = listByNoteProno.get(0);
			ArchiveInfo archive = new ArchiveInfo();
			
			archive.setIsFirstInProno(ti.getIsFirstInProno());
			archive.setC(ti.getC());
			archive.setHour(ti.getHour());
			archive.setIsFirstInProno(true);
			archive.setJour(ti.getJour());
			archive.setLiveOdd(ti.getLiveOdd());
			archive.setLiveOddPlace(ti.getLiveOddPlace());
			if(ti.getLiveOddPlace() == null) {
				archive.setLiveOddPlace(0d);
			}
			archive.setLiveOddPlaceOnline(ti.getLiveOddPlaceOnline());
			archive.setNumberOfInitialRunners(ti.getNumberOfInitialRunners());
			archive.setNumero(ti.getNumero());
			archive.setR(ti.getR());
			archive.setRanking(ti.getRanking());
			archive.setReunionstring(ti.getReunionstring());
			archive.setRaceSpecialty(ti.getRaceSpecialty());
			archive.setHasBetTypes(ti.getHasBetTypes());
			archive.setRecence(ti.getRecence());

            finalList.add(archive);		
	
			}	

			
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

	        			return finalList;
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
   
   private List<TurfInfos> setPastilles(List<TurfInfos> jockeys, List<TurfInfos> chronos, List<TurfInfos> pronos, int raceSize){
	   
	   if(raceSize == 7) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 5) {
			   if(!jockeys.subList(0, 5).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }
			   }
			   if(chronos.size() >= 5) {
			   if(!chronos.subList(0, 5).contains(ti)) {
				   ti.setChronoPastille(true);
			   }
			   }
		   });
	   }
	   
	   if(raceSize == 8 || raceSize == 9) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 6) {
			   if(!jockeys.subList(0, 6).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }
			   }
			   if(chronos.size() >= 6) {
			   if(!chronos.subList(0, 6).contains(ti)) {
				   ti.setChronoPastille(true);
			   }
			   }
		   });
	   }
	   
	   if(raceSize == 10 || raceSize == 11) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 7) {
			   if(!jockeys.subList(0, 7).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }
			   }
			   if(chronos.size() >= 7) {
			   if(!chronos.subList(0, 7).contains(ti)) {
				   ti.setChronoPastille(true);
			   }
			   }
		   });
	   }
	   
	   if(raceSize == 12 || raceSize == 13) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 8) {
			   if(!jockeys.subList(0, 8).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }
			   }
			   if(chronos.size() >= 8) {
			   if(!chronos.subList(0, 8).contains(ti)) {
				   ti.setChronoPastille(true);
			   }
			   }
		   });
	   }
	   
	   if(raceSize >= 14) {
		   pronos.forEach(ti-> {
			   if(jockeys.size() >= 9) {
			   if(!jockeys.subList(0, 9).contains(ti)) {
				   ti.setJockeyPastille(true);
			   }
			   }
			   if(chronos.size() >= 9) {
			   if(!chronos.subList(0, 9).contains(ti)) {
				   ti.setChronoPastille(true);
			   }
			   }
		   });
	   }
	   

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