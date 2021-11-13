package turfinfos2.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.TurfInfoService;

@Controller
public class UploadController {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	TurfInfoService turfInfoService;

    @GetMapping("/upload")
    public String index(Model model) {
    	
    	 Set<String> dates = turfInfosRepository.findAll().stream()
 				.map(TurfInfos :: getJour)
 				.collect(Collectors.toSet());
    	 
         navbarInfos(model);
    	 return "upload";
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFileByRace(@RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirect) {
    	    	
    	List<TurfInfos> infos = new ArrayList<>();
    	
        // validate file
        if (file.isEmpty()) {
        	
			redirect.addFlashAttribute("messageempty", "Aucun fichier sélectionné, veuillez sélectionner un fichier.");

//            model.addAttribute("messageempty", "Aucun fichier sélectionné, veuillez sélectionner un fichier.");
            model.addAttribute("status", false);
            // Envoyer les dates au model
//            Set<String> dates = turfInfosRepository.findAll().stream()
//    				.map(TurfInfos :: getJour)
//    				.collect(Collectors.toSet());
//            model.addAttribute("dates", dates);
            navbarInfos(model);

        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<TurfInfos> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(TurfInfos.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                infos = csvToBean.parse();
//                if(infos.get(0).getKey().)

                // TODO: save users in DB?
                
            	List<TurfInfos> all = turfInfosRepository.findAll();
               
                List<Integer> numcourses = all.stream()
        				.map(TurfInfos :: getNumcourse)
        				.collect(Collectors.toList());
                
                             
                for(TurfInfos info : infos) {
	            	turfInfoService.setMadeUpParams(info);

                	if(!numcourses.contains(info.getNumcourse())) {
                	turfInfosRepository.save(info);
                	}
                	if(numcourses.contains(info.getNumcourse())) {
                		  TurfInfos infoToUpdate = all.stream()
                     				.filter(ti-> ti.getNumero().equals(info.getNumero()) && ti.getNumcourse().equals(info.getNumcourse()))
                     				.findFirst().get(); 
                		turfInfoService.patchCSVFromAspi(info, infoToUpdate);
                    	}
                }

                // Envoyer les dates au model
//                Set<String> dates = turfInfosRepository.findAll().stream()
//        				.map(TurfInfos :: getJour)
//        				.collect(Collectors.toSet());
//                model.addAttribute("dates", dates);
                navbarInfos(model);

//                model.addAttribute("infos", infos);
//                model.addAttribute("pvch", turfInfosRepository.findAllByOrderByPourcVictChevalHippoDesc());
                                
                model.addAttribute("status", true);
    			redirect.addFlashAttribute("messagesuccess", "Fichier: (" + file.getResource().getFilename() + ") importé avec succès");
//                model.addAttribute("messagesuccess", "Fichier: (" + file.getResource().getFilename() + ") importé avec succès");


            } catch (Exception ex) {
    			redirect.addFlashAttribute("messageerror", "Une erreur est apparue durant l'import du fichier.");

//                model.addAttribute("messageerror", "Une erreur est apparue durant l'import du fichier.");
                model.addAttribute("status", false);
                // Envoyer les dates au model
//                Set<String> dates = turfInfosRepository.findAll().stream()
//        				.map(TurfInfos :: getJour)
//        				.collect(Collectors.toSet());
//                model.addAttribute("dates", dates);
                navbarInfos(model);

            }
        }
        
        navbarInfos(model);
//        return "upload";
		return "redirect:/upload";
//        return "races-page";

    }
    
    @GetMapping("/day-infos")
    public String getDayInfos(@RequestParam("jour") String jour, Model model) {
    	
    	//Récuperer chaque reunion (1, 2, 3, 4...)
   	 Set<String> reunions = turfInfosRepository.findAllByJour(jour).stream()
	 				.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
	        			.map(TurfInfos :: getReunionstring)
	        			.collect(Collectors.toSet());
	        			List<String> list2 = new ArrayList<String>(reunions);
	        			Collections.sort(list2);        			
	        			reunions = new LinkedHashSet<>(list2);
    	     	 
    	 
     	model.addAttribute("reunions", reunions);
    	model.addAttribute("jour", jour);
    	
        navbarInfos(model);
    	return "day-infos";
    }
    
    @GetMapping("/reunion-infos")
    public String getReunionInfos(@RequestParam("jour") String jour,
    		@RequestParam("reunion") String reunionstring,
    		Model model, Principal principal) {   
    	
//    	if(principal == null) {
//			return "redirect:/login";
//    	}
    	
    	model.addAttribute("date", jour);
    	model.addAttribute("recencemax", 60);
    	
    	//RACESLIST
    	List<TurfInfos> allReunionInfos = turfInfosRepository.findAllByJourAndByReunionstring(jour, reunionstring);
    	List<TurfInfos> reunionCracks = new ArrayList<>();


    	
    	//biglist
//		LinkedList<LinkedList<TurfInfos>> biglist = new LinkedList<LinkedList<TurfInfos>>();

    	//Num des courses
		Set<Integer> distinctNumraces = allReunionInfos.stream()
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
		
		model.addAttribute("distinctnumraces", distinctNumraces);
		
		List<TurfInfos> allraceInfos = new ArrayList<>();
		for(Integer num : distinctNumraces) {
					
			//Infos de la course en question
			allraceInfos = 
					allReunionInfos
					.stream()
					.filter(ti -> ti.getC().equals(num))
					.collect(Collectors.toList());
			
			allraceInfos.forEach(ti-> {
			       System.out.println(ti.getR()+ti.getC()+ "--" +ti.getRecence());
			});
			
			createClassementList(allraceInfos);
			

			
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
			
			List<TurfInfos> listBypveh = calculateEntraineur(allraceInfos).stream()
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
						
			List<TurfInfos> listByppjh = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceJockHippo() != null && ti.getPourcPlaceJockHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceJockHippo))
					.collect(Collectors.toList());
			Collections.reverse(listByppjh);
				
			List<TurfInfos> listByppeh =  calculateEntraineur(allraceInfos).stream()
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
				   
				    listByChronos, model).stream()
					.filter(ti -> ti.getNoteProno() != null && ti.getNoteProno()>0)
					.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno))
					.collect(Collectors.toList());
			Collections.reverse(listByNoteProno);
			
			reunionCracks.addAll(listByNoteProno);
						
			
			
			Optional<TurfInfos> optTinf = allraceInfos.stream().findFirst();
			if(optTinf.isPresent()) {
				TurfInfos tinf = optTinf.get();
			model.addAttribute(numToString(num) + "title","R" + tinf.getR() + "-C" + num );
			
			model.addAttribute(numToString(num) + "pvch", listBypvch);
			model.addAttribute(numToString(num) + "pvjh", listBypvjh);
			model.addAttribute(numToString(num) + "pveh", listBypveh);
			
			model.addAttribute(numToString(num) + "ppch", listByppch);
			model.addAttribute(numToString(num) + "ppjh", listByppjh);
			model.addAttribute(numToString(num) + "ppeh", listByppeh);
			
			
			model.addAttribute(numToString(num) + "txv", listBytxv);
			model.addAttribute(numToString(num) + "txp", listBytxp);
			model.addAttribute(numToString(num) + "txvh", listBytxvh);
			model.addAttribute(numToString(num) + "txph", listBytxph);
			
			
			if(listByChronoParisTurf.size() < 9) {
				model.addAttribute(numToString(num) + "paristurfchronoslist", listByChronoParisTurf);
			}
			if(listByChronoParisTurf.size() >= 9){
				model.addAttribute(numToString(num) + "paristurfchronoslist", listByChronoParisTurf.subList(0, 9));
			}

			model.addAttribute(numToString(num) + "chronoslist", listByChronos);
			model.addAttribute(numToString(num) + "taypronoslist", listByTayPronos);
						
			
			if(!listByNoteProno.isEmpty() && listByNoteProno.get(0).getNoteProno() > 0 && listByNoteProno.size() < 9) {
				model.addAttribute(numToString(num) + "pronoslist", listByNoteProno);
			}
			if(!listByNoteProno.isEmpty() && listByNoteProno.get(0).getNoteProno() > 0 &&  listByNoteProno.size() >= 9){
				model.addAttribute(numToString(num) + "pronoslist", listByNoteProno.subList(0, 9));
			}
			if(listByNoteProno.isEmpty()) {
				model.addAttribute(numToString(num) + "pronoslist", new ArrayList<>());
			}
			
			
			
			if(createClassementList(allraceInfos).size() < 8) {
				model.addAttribute(numToString(num) + "classementlist", createClassementList(allraceInfos));
			}
			if(createClassementList(allraceInfos).size() >= 8){
				model.addAttribute(numToString(num) + "classementlist", createClassementList(allraceInfos).subList(0, 8));
			}
						
						
			
//			model.addAttribute(numToString(num) + "pronoslist", listByPronos);
			
			if(!listByChronos.isEmpty() || listByChronos.size()>0) {
				model.addAttribute(numToString(num) + "chronos", true); 
			}
			if(listByChronos.isEmpty() || listByChronos.size()==0) {
				model.addAttribute(numToString(num) + "chronos", false); 
			}
			
			if(!listByTayPronos.isEmpty() || listByTayPronos.size()>0) {
				model.addAttribute(numToString(num) + "taypronos", true); 
			}
			if(listByTayPronos.isEmpty() || listByTayPronos.size()==0) {
				model.addAttribute(numToString(num) + "taypronos", false); 
			}
			
			
			System.out.println(allraceInfos.get(0).getRaceSpecialty());
			
			if(allraceInfos.get(0).getRaceSpecialty() != null) {

			if(allraceInfos.get(0).getRaceSpecialty().equals("A") || allraceInfos.get(0).getRaceSpecialty().equals("M")) {
				model.addAttribute("specialty", "trot");
			} else {
				model.addAttribute("specialty", "galop");
			}
			}else {
				model.addAttribute("specialty", "");
			}
						
			model.addAttribute(numToString(num) + "exists", true);
						

			}	

			
			List<TurfInfos> listByNumCheval = allraceInfos.stream()
//					.map(TurfInfos :: getNumero)
					.sorted(Comparator.comparingInt(TurfInfos::getNumero))
					.collect(Collectors.toList());
//					List<Integer> listNums = new ArrayList<Integer>(distinctNumsCheval);
//					Collections.reverse(listNums);
//					distinctNumsCheval = new LinkedHashSet<>(listNums);
			
			model.addAttribute(numToString(num) + "horses", listByNumCheval);
						
		}
		
		////////////CRACKLIST/////////////////
		List<TurfInfos> crackList = reunionCracks.stream()
		.filter(ti-> ti.getNoteProno()>= 52.5)
		.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno))
		.collect(Collectors.toList());
		Collections.reverse(crackList);

crackList.forEach(ti-> {
	if(ti.getNumero()<10) {
		ti.setNumero(Integer.valueOf("0"+ti.getNumero()));
	}
});
		
		model.addAttribute("cracklist", crackList);

		
				/////////////reusOfDay
		 Set<String> reunions = turfInfosRepository.findAllByJour(jour).stream()
	 				.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
	        			.map(TurfInfos :: getReunionstring)
	        			.collect(Collectors.toSet());
	        			List<String> list2 = new ArrayList<String>(reunions);
	        			Collections.sort(list2);        			
	        			reunions = new LinkedHashSet<>(list2);
	        	         model.addAttribute("reunionsofday", reunions);
		 
		 
	         model.addAttribute("reusofday", reunions);
	         model.addAttribute("reunionstring", reunionstring);
	         model.addAttribute("jour", jour);


	         
//	         allraceInfos.forEach(null)
	         
		navbarInfos(model);
    	
    	return "reunion-infos";
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
		   
		   List<TurfInfos> listByChronos,
		   Model model) {	 
	   
	   calculateNoteProno(allraceInfos, listBypvch, 1d);
	   calculateNoteProno(allraceInfos, listBypvjh, 2d);
	   calculateNoteProno(allraceInfos, listBypveh, 3d);

	   calculateNoteProno(allraceInfos, listByppch, 4d);
//	   calculateNoteProno(allraceInfos, listByppjh, 5d);
//	   calculateNoteProno(allraceInfos, listByppeh, 6d);

	   calculateNoteProno(allraceInfos, listBytxv, 7d);
	   calculateNoteProno(allraceInfos, listBytxp, 8d);
	   calculateNoteProno(allraceInfos, listBytxvh, 9d);
	   calculateNoteProno(allraceInfos, listBytxph, 10d);

	   calculateNoteProno(allraceInfos, listByChronos, 11d);
	   
//	   System.out.println((70d/100)*75d + "tauxcrack");

		return allraceInfos;
   }
   
   private List<TurfInfos> calculateNoteProno(List<TurfInfos>allRaceInfos, List<TurfInfos>list, Double percentageParameter) {
	   	   
	   
	for(int i =0; i<list.size(); i++) {
			
			if(list.size() > 0 && i == 0) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 10 );
			}	
			if(list.size() > 1 && i == 1) {
				setPercentageParam(list.get(i), percentageParameter);
				if(list.get(i).getNotePercentageParameter()!=list.get(i-1).getNotePercentageParameter()) {
					list.get(i).setNoteProno(list.get(i).getNoteProno() + 9 );
				}
				if(list.get(i).getNotePercentageParameter()==list.get(i-1).getNotePercentageParameter()) {
					list.get(i).setNoteProno(list.get(i-1).getNoteProno());
				}
				
			}
			if(list.size() > 2 && i == 2) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 8 );
			}
			if(list.size() > 3 && i == 3) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 7 );
			}
			if(list.size() > 4 && i == 4) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 6 );
			}
			if(list.size() > 5 && i == 5) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 5 );
			}
			if(list.size() > 6 && i == 6) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 4);
			}
			if(list.size() > 7 && i == 7) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 3);
			}
			if(list.size() > 8 && i == 8) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 2);
			}
			if(list.size() > 9 && i == 9) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}	
			if(list.size() > 10 && i == 10) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 11 && i == 11) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 12 && i == 12) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 13 && i == 13) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 14 && i == 14) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 15 && i == 15) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 16 && i == 16) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 17 && i == 17) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 18 && i == 18) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 19 && i == 19) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
			}
			if(list.size() > 20 && i == 20) {
				list.get(i).setNoteProno(list.get(i).getNoteProno() + 1);
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
	   if(percentParam == 9d) {
		   tinf.setNotePercentageParameter(tinf.getTxVictCoupleHippo());
	   }
	   if(percentParam == 10d) {
		   tinf.setNotePercentageParameter(tinf.getTxPlaceCoupleHippo());
	   }
//	   if(percentParam == 11d) {
//		   tinf.setNotePercentageParameter(tinf.getChrono().doubleValue());
//	   }
	   
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
			   System.out.println(tinf.getNumeroString() + "     bbbbbbbbbbbbbbb");

			   
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
				   System.out.println(tinf.getNumeroString() + "     bbbbbbbbbbbbbbb");
			   }
			   tinf.setNumeroString(tinf.getNumeroString() + "]");

			   newList.add(tinf);
		   }
	   }
	   
	   return newList;
   }
   
   private List<TurfInfos> createClassementList(List<TurfInfos> allraceInfos){

	   LinkedList<TurfInfos> list = new LinkedList<>();
	   

	   for(TurfInfos t: allraceInfos) {
//			System.out.println(t.getCl());
		   
			if(t.getCl().equals("1er")) {
				t.setClInt(1);
				list.add(t);
			}
			if(t.getCl().equals("2e")) {
				t.setClInt(2);
				list.add(t);

			}
			if(t.getCl().equals("3e")) {
				t.setClInt(3);
				list.add(t);

			}
			if(t.getCl().equals("4e")) {
				t.setClInt(4);
				list.add(t);

			}
			if(t.getCl().equals("5e")) {
				t.setClInt(5);
				list.add(t);

			}
			if(t.getCl().equals("6e")) {
				t.setClInt(6);
				list.add(t);

			}
			if(t.getCl().equals("7e")) {
				t.setClInt(7);
				list.add(t);

			}
			if(t.getCl().equals("8e")) {
				t.setClInt(8);
				list.add(t);

			}
			if(t.getCl().equals("9e")) {
				t.setClInt(9);
				list.add(t);

			}
			if(t.getCl().equals("10e")) {
				t.setClInt(10);
				list.add(t);
			}
			if(t.getCl().equals("11e")) {
				t.setClInt(11);
				list.add(t);
			}
			if(t.getCl().equals("12e")) {
				t.setClInt(12);
				list.add(t);
			}
			if(t.getCl().equals("13e")) {
				t.setClInt(13);
				list.add(t);

			}
			if(t.getCl().equals("14e")) {
				t.setClInt(14);
				list.add(t);
			}
			if(t.getCl().equals("15e")) {
				t.setClInt(15);
				list.add(t);
			}
			if(t.getCl().equals("16e")) {
				t.setClInt(16);
				list.add(t);
			}
			if(t.getCl().equals("17e")) {
				t.setClInt(17);
				list.add(t);
			}
			if(t.getCl().equals("18e")) {
				t.setClInt(18);
				list.add(t);
			}
			if(t.getCl().equals("19e")) {
				t.setClInt(19);
				list.add(t);
			}
			if(t.getCl().equals("20e")) {
				t.setClInt(20);
				list.add(t);
			}
		}
	   
	   List<TurfInfos> listByCl =  list.stream()
				.filter(ti -> ti.getClInt()!= null)
				.sorted(Comparator.comparingInt(TurfInfos::getClInt))
				.collect(Collectors.toList());
	   
//	   for(TurfInfos t: listByCl) {
//		   System.out.println(t.getClInt());
////		   System.out.println(t.getCl());
//
//	   }
//		System.out.println("Stop");
	   
	   return listByCl;
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