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
import turfinfos2.repository.ResultRepository;
import turfinfos2.repository.TurfInfosRepository;
import turfinfos2.service.ResultService;
import turfinfos2.service.TurfInfoService;

@Controller
public class UploadController {

	@Autowired
	TurfInfosRepository turfInfosRepository;
	
	@Autowired
	ResultRepository resultRepository;

	@Autowired
	TurfInfoService turfInfoService;
	
	@Autowired
	ResultService resultService;

	@GetMapping("/upload")
	public String index(Model model) {

//		Set<String> dates = turfInfosRepository.findAll().stream().map(TurfInfos::getJour).collect(Collectors.toSet());
//		List<TurfInfos> allInfos = turfInfosRepository.findAll();

		navbarInfos(model);
		return "upload";
	}

	@PostMapping("/upload-csv-file")
	public String uploadCSVFileByDay(@RequestParam("file") MultipartFile file, Model model,
			RedirectAttributes redirect) {
		
		List<TurfInfos> allInfos = turfInfosRepository.findAll();


		List<TurfInfos> allToSave = new ArrayList<>();
		List<TurfInfos> allToUpdate = new ArrayList<>();
		
		List<TurfInfos> infos = new ArrayList<>();

		// validate file
		if (file.isEmpty()) {

			redirect.addFlashAttribute("messageempty", "Aucun fichier s??lectionn??, veuillez s??lectionner un fichier.");

//            model.addAttribute("messageempty", "Aucun fichier s??lectionn??, veuillez s??lectionner un fichier.");
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
				CsvToBean<TurfInfos> csvToBean = new CsvToBeanBuilder(reader).withType(TurfInfos.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				// convert `CsvToBean` object to list of users
				infos = csvToBean.parse();
//                if(infos.get(0).getKey().)

				// TODO: save users in DB?

				List<TurfInfos> all = turfInfosRepository.findAll();

				List<Integer> numcourses = all.stream().map(TurfInfos::getNumcourse).collect(Collectors.toList());

				for (TurfInfos info : infos) {
					turfInfoService.setMadeUpParams(info);

					if (!numcourses.contains(info.getNumcourse())) {
						allToSave.add(info);
					}

					if (numcourses.contains(info.getNumcourse())) {
						TurfInfos infoToUpdate = all.stream().filter(ti -> ti.getNumero().equals(info.getNumero())
								&& ti.getNumcourse().equals(info.getNumcourse())).findFirst().get();
//                		turfInfoService.patchCSVFromAspi(info, infoToUpdate);
						allToUpdate.add(turfInfoService.update(info, infoToUpdate));

					}
				}
				
				infos.forEach(ti-> {
					System.out.println(ti.getIsRunning() + " isrunning");
					System.out.println(ti.getIsPremium() + " premium");
					System.out.println(ti.getNumcourse() + " numcourse");
					System.out.println(ti.getCoursescheval() + " coursescheval");
					System.out.println(ti.getRaceSpecialty() + " specialty");

				});


				navbarInfos(model);


				model.addAttribute("status", true);
				redirect.addFlashAttribute("messagesuccess",
						"Fichier: (" + file.getResource().getFilename() + ") import?? avec succ??s");
//                model.addAttribute("messagesuccess", "Fichier: (" + file.getResource().getFilename() + ") import?? avec succ??s");

			} catch (Exception ex) {
				redirect.addFlashAttribute("messageerror", "Une erreur est apparue durant l'import du fichier.");

//                model.addAttribute("messageerror", "Une erreur est apparue durant l'import du fichier.");
				model.addAttribute("status", false);

				navbarInfos(model);

			}
		}

		navbarInfos(model);

		allToSave.addAll(allToUpdate);
		turfInfosRepository.saveAll(allToSave);
		System.out.println("StopCSV");
		
		return "redirect:/upload";
//        return "races-page";

	}

	@GetMapping("/day-infos")
	public String getDayInfos(@RequestParam("jour") String jour, Model model) {
		
//		List<TurfInfos> allInfos = turfInfosRepository.findAll();

		// R??cuperer chaque reunion (1, 2, 3, 4...)
		Set<String> reunions = turfInfosRepository.findAllByJour(jour).stream()
				.filter(ti -> ti.getJour().equals(jour) && ti.getR().length() < 3).map(TurfInfos::getReunionstring)
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
	public String getReunionInfos(@RequestParam("jour") String jour, @RequestParam("reunion") String reunionstring,
			Model model, Principal principal) {

//    	if(principal == null) {
//			return "redirect:/login";
//    	}
		
//		List<TurfInfos> allInfos = turfInfosRepository.findAll();


		Integer recenceMax = 90;
		
		model.addAttribute("date", jour);
		model.addAttribute("recencemax", recenceMax);
		
		List<TurfInfos> allPremium = turfInfosRepository
				.findAllByJourAndByReunionstring(jour, reunionstring);
		allPremium.forEach(ti->{
			if(ti.getRecence() == null) {
				ti.setRecence(recenceMax -1 );
			}
		});

		// RACESLIST
		List<TurfInfos> allPremiumReunionInfos = allPremium.stream()
				.filter(ti -> 
				ti.getIsRunning() != null && ti.getIsRunning() == true 
				&& ti.getIsPremium() != null && ti.getIsPremium().equals(true)
//				&& ti.getRecence() != null && ti.getRecence() < recenceMax
				)
				.collect(Collectors.toList());
		//Ajouter bon coups du jour
		List<List<TurfInfos>> bigList = new ArrayList<>();
		/////////////////////////

		List<TurfInfos> reunionCracks = new ArrayList<>();
		Map<String, String> tayPronos = new HashMap<>();

		// Num des courses
		Set<Integer> distinctNumraces = allPremiumReunionInfos.stream().map(TurfInfos::getC).sorted()
				.collect(Collectors.toSet());
		List<Integer> list = new ArrayList<Integer>(distinctNumraces);
		Collections.reverse(list);
		distinctNumraces = new LinkedHashSet<>(list);

		

		model.addAttribute("distinctnumraces", distinctNumraces);

		List<TurfInfos> allraceInfos = new ArrayList<>();
		for (Integer num : distinctNumraces) {

			// Infos de la course en question
			allraceInfos = allPremiumReunionInfos.stream().filter(ti -> ti.getC().equals(num))
					.collect(Collectors.toList());
			
			int raceSize = allraceInfos.get(0).getNumberOfInitialRunners();

			model.addAttribute("racesize", raceSize);

			model.addAttribute(numToString(num) + "ispick5", allraceInfos.get(0).getIsPick5());
			model.addAttribute(numToString(num) + "istqq", allraceInfos.get(0).getIsTQQ());
			model.addAttribute(numToString(num) + "runners", allraceInfos.get(0).getNumberOfInitialRunners());

			String nonPartants = "";
			if (allraceInfos.get(0).getNumberOfNonRunners() != null
					&& allraceInfos.get(0).getNumberOfNonRunners() > 0) {
				nonPartants = "(" + allraceInfos.get(0).getNumberOfNonRunners() + " NP)";
			}
			model.addAttribute(numToString(num) + "caralist",
					allraceInfos.get(0).getHour() + " - " + allraceInfos.get(0).getCaraList1() + " - "
							+ allraceInfos.get(0).getCaraList2() + " - "
							+ allraceInfos.get(0).getNumberOfInitialRunners() + " partants " + nonPartants);
			model.addAttribute(numToString(num) + "hasbettypes", allraceInfos.get(0).getHasBetTypes());

//			});

//			createClassementList(allraceInfos);

			
			allraceInfos.forEach(ti -> {
				if(ti.getPourcVictChevalHippo() != null && ti.getPourcPlaceChevalHippo() != null) {
					ti.setPourcPlaceChevalHippo(ti.getPourcPlaceChevalHippo() - ti.getPourcVictChevalHippo());
				}
				if(ti.getTxVictCouple()!= null && ti.getTxPlaceCouple() != null) {
					ti.setTxPlaceCouple(ti.getTxPlaceCouple() - ti.getTxVictCouple());
				}
				if(ti.getTxVictCoupleHippo() != null && ti.getTxPlaceCoupleHippo() != null) {
					ti.setTxPlaceCoupleHippo(ti.getTxPlaceCoupleHippo() - ti.getTxVictCoupleHippo());
				}
				if(ti.getPourcVictChevalSurface() != null && ti.getPourcPlaceChevalSurface() != null) {
					ti.setPourcPlaceChevalSurface(ti.getPourcPlaceChevalSurface() - ti.getPourcVictChevalSurface());
				}
				if(ti.getPourcVictChevalSurfaceHippo() != null && ti.getPourcPlaceChevalSurfaceHippo() != null) {
					ti.setPourcPlaceChevalSurfaceHippo(ti.getPourcPlaceChevalSurfaceHippo() - ti.getPourcVictChevalSurfaceHippo());
				}

		});
			
			// cr??ation des listes filtr??es et tri??es par param??tre voulu

			List<TurfInfos> listBypvch = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictChevalHippo() != null && ti.getPourcVictChevalHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictChevalHippo))
					.collect(Collectors.toList());
			Collections.reverse(listBypvch);

			List<TurfInfos> listBypvjh = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictJockHippo() != null && ti.getPourcVictJockHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictJockHippo)).collect(Collectors.toList());
			Collections.reverse(listBypvjh);

			List<TurfInfos> listBypveh = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictEntHippo() != null && ti.getPourcVictEntHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictEntHippo)).collect(Collectors.toList());
			Collections.reverse(listBypveh);


			List<TurfInfos> listByppch = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceChevalHippo() != null && ti.getPourcPlaceChevalHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceChevalHippo))
					.collect(Collectors.toList());
			Collections.reverse(listByppch);

//			List<TurfInfos> listByppc = allraceInfos.stream()
//					.filter(ti -> ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() != 0d).sorted(Comparator
//							.comparingDouble(TurfInfos::getPourcPlaceCheval).thenComparingInt(TurfInfos::getNumero))
//					.collect(Collectors.toList());
//			Collections.reverse(listByppc);
			List<TurfInfos> listByppc = new ArrayList<>();


			List<TurfInfos> listByppjh = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceJockHippo() != null && ti.getPourcPlaceJockHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceJockHippo)).collect(Collectors.toList());
			Collections.reverse(listByppjh);

			List<TurfInfos> listByppeh = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceEntHippo() != null && ti.getPourcPlaceEntHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceEntHippo)).collect(Collectors.toList());
			Collections.reverse(listByppeh);

			List<TurfInfos> listBytxv = allraceInfos.stream()
					.filter(ti -> ti.getTxVictCouple() != null && ti.getTxVictCouple() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxVictCouple)).collect(Collectors.toList());
			Collections.reverse(listBytxv);

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
//				if (ti.getTxVictCouple() != null && ti.getTxVictCouple().equals(100d)) {
//					ti.setTxPlaceCouple(0d);
//				}
//			});

			List<TurfInfos> listBytxp = allraceInfos.stream()
					.filter(ti -> ti.getTxPlaceCouple() != null && ti.getTxPlaceCouple() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxPlaceCouple)).collect(Collectors.toList());
			Collections.reverse(listBytxp);

			List<TurfInfos> listBytxvh = allraceInfos.stream()
					.filter(ti -> ti.getTxVictCoupleHippo() != null && ti.getTxVictCoupleHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxVictCoupleHippo)).collect(Collectors.toList());
			Collections.reverse(listBytxvh);

//			allraceInfos.forEach(ti -> {
//				if (ti.getTxVictCoupleHippo() != null && ti.getTxVictCoupleHippo().equals(100d)) {
//					ti.setTxPlaceCoupleHippo(0d);
//				}
//			});

			List<TurfInfos> listBytxph = allraceInfos.stream()
					.filter(ti -> ti.getTxPlaceCoupleHippo() != null && ti.getTxPlaceCoupleHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getTxPlaceCoupleHippo)).collect(Collectors.toList());
			Collections.reverse(listBytxph);
			
			//Surface
			List<TurfInfos> listBypvcs = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictChevalSurface() != null && ti.getPourcVictChevalSurface() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictChevalSurface)).collect(Collectors.toList());
			Collections.reverse(listBypvcs);
			
			List<TurfInfos> listByppcs = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceChevalSurface() != null && ti.getPourcPlaceChevalSurface() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceChevalSurface)).collect(Collectors.toList());
			Collections.reverse(listByppcs);
			
			List<TurfInfos> listBypvcsh = allraceInfos.stream()
					.filter(ti -> ti.getPourcVictChevalSurfaceHippo() != null && ti.getPourcVictChevalSurfaceHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictChevalSurfaceHippo)).collect(Collectors.toList());
			Collections.reverse(listBypvcsh);
			
			List<TurfInfos> listByppcsh = allraceInfos.stream()
					.filter(ti -> ti.getPourcPlaceChevalSurfaceHippo() != null && ti.getPourcPlaceChevalSurfaceHippo() != 0d)
					.sorted(Comparator.comparingDouble(TurfInfos::getPourcPlaceChevalSurfaceHippo)).collect(Collectors.toList());
			Collections.reverse(listByppcsh);

			// CHRONOS
			List<TurfInfos> listByChronos = allraceInfos.stream().filter(ti -> ti.getChrono() != null)
					.sorted(Comparator.comparingInt(TurfInfos::getChrono)).collect(Collectors.toList());
//			Collections.reverse(listBypvch);

			// TayPronos
			List<TurfInfos> listByTayPronos = allraceInfos.stream().filter(ti -> ti.getTayProno() != null)
					.sorted(Comparator.comparingInt(TurfInfos::getTayProno)).collect(Collectors.toList());
//			Collections.reverse(listBypvch);

			if (listByTayPronos.size() > 0) {
				String prono = "";
				for (TurfInfos info : listByTayPronos) {
					prono = prono + " - " + info.getNumero();
				}
				String result = prono.substring(3);
				System.out.println(result + "PRONO C " + num);
				tayPronos.put("C" + num, result);
			}
			// list par chrono Paris turf
//			List<TurfInfos> listByChronoParisTurf = allraceInfos.stream()
//					.filter(ti -> ti.getDistanceAndSpecialtyChrono()!= null)
//					.sorted(Comparator.comparingInt(TurfInfos::getTayProno))
//					.collect(Collectors.toList());
////			Collections.reverse(listBypvch);

			List<TurfInfos> listByChronoParisTurf = allraceInfos.stream()
					.filter(ti -> ti.getDistanceAndSpecialtyChrono() != null).sorted(Comparator
							.comparing(TurfInfos::getDistanceAndSpecialtyChrono).thenComparing(TurfInfos::getNumero))
					.collect(Collectors.toList());
			
			List<TurfInfos> listByValeur = allraceInfos.stream()
					.filter(ti -> ti.getValeur() != null && ti.getValeur() > 0).sorted(Comparator
							.comparing(TurfInfos::getValeur).thenComparing(TurfInfos::getNumero, (int1, int2) -> int2-int1))
					.collect(Collectors.toList());
			Collections.reverse(listByValeur);


			// CLASSEMENT A L'ARRIVEE
			List<TurfInfos> listByRanking = allraceInfos.stream()
					.filter(ti -> ti.getRanking() != null && ti.getRanking() != 0)
					.sorted(Comparator.comparingInt(TurfInfos::getRanking)).collect(Collectors.toList());

			// CORDES
			List<TurfInfos> listByDraw = allraceInfos.stream().filter(ti -> ti.getDraw() != null && ti.getDraw() != 0)
					.sorted(Comparator.comparingInt(TurfInfos::getDraw)).collect(Collectors.toList());

			// Calcul de la note
			List<TurfInfos> listByNoteProno = calculateFinalNoteProno(allraceInfos, listBypvch, listBypvjh, listBypveh,

					listByppch, listByppjh, listByppeh,

					listBytxv, listBytxp, listBytxvh, listBytxph,

					listByppc, listByChronos, listBypvcs, listByppcs, listBypvcsh, listByppcsh, model)

							.stream().filter(ti -> ti.getNoteProno() != null && ti.getNoteProno() > 0)
							.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno)).collect(Collectors.toList());
			Collections.reverse(listByNoteProno);

			////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////

//			TurfInfos ti = listByNoteProno.get(0);
//			
//				if(ti.getLiveOddPlaceOnline() != null && ti.getLiveOddPlaceOnline() > 0 
//						
////						&& ti.getRaceSpecialty()=="P"
//						) {
//					
//					ti.setIsPlaceWonAndInProno(true);
//				}else if(ti.getLiveOddPlaceOnline() != null && ti.getLiveOddPlaceOnline() == 0){
//					ti.setIsPlaceWonAndInProno(false);
//				}
//				
//				turfInfosRepository.save(ti);

			////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////

			reunionCracks.addAll(listByNoteProno);

			///////////// AFFECTER PASTILLES///////////////
			setPastilles(listBypvjh, listByChronos, listBypvch, listByppch, listBytxv, listBytxp, listByNoteProno,
					listBypvcs, listByppcs,
					allraceInfos.size());

            ///////////// AFFECTER ETOILES///////////////
			setEtoiles(listByChronos, listBypveh, listBypvjh, listBypvch, listByppch, listByppc, listBytxv, listBytxp, listBytxvh, listBytxph,
					listBypvcs, listByppcs, listBypvcsh, listByppcsh,
					allraceInfos,
					allraceInfos.size());

			Optional<TurfInfos> optTinf = allraceInfos.stream().findFirst();
			if (optTinf.isPresent()) {
				TurfInfos tinf = optTinf.get();
				model.addAttribute(numToString(num) + "title", "R" + tinf.getR() + "-C" + num);

				model.addAttribute(numToString(num) + "pvch", listBypvch);
				model.addAttribute(numToString(num) + "pvjh", listBypvjh);
				model.addAttribute(numToString(num) + "pveh", calculateEntraineur(listBypveh));

				model.addAttribute(numToString(num) + "ppch", listByppch);
				model.addAttribute(numToString(num) + "ppc", listByppc);

//			model.addAttribute(numToString(num) + "ppjh", listByppjh);
//			model.addAttribute(numToString(num) + "ppeh", calculateEntraineur(listByppeh));

				model.addAttribute(numToString(num) + "txv", listBytxv);
				model.addAttribute(numToString(num) + "txp", listBytxp);
				model.addAttribute(numToString(num) + "txvh", listBytxvh);
				model.addAttribute(numToString(num) + "txph", listBytxph);
				
				model.addAttribute(numToString(num) + "surface", allraceInfos.get(0).getSurface());
				model.addAttribute(numToString(num) + "pvcs", listBypvcs);
				model.addAttribute(numToString(num) + "ppcs", listByppcs);
				model.addAttribute(numToString(num) + "pvcsh", listBypvcsh);
				model.addAttribute(numToString(num) + "ppcsh", listByppcsh);



				if (listByChronoParisTurf.size() < 90) {
					model.addAttribute(numToString(num) + "paristurfchronoslist", listByChronoParisTurf);
				}
//				if (listByChronoParisTurf.size() >= 9) {
//					model.addAttribute(numToString(num) + "paristurfchronoslist", listByChronoParisTurf.subList(0, 9));
//				}

				model.addAttribute(numToString(num) + "chronoslist", listByChronos);
				model.addAttribute(numToString(num) + "taypronoslist", listByTayPronos);
				model.addAttribute(numToString(num) + "drawlist", listByDraw);
				
				
				Integer sub = 0;

				if (!listByNoteProno.isEmpty() && listByNoteProno.get(0).getNoteProno() > 0
						&& raceSize >= 10) {
//					model.addAttribute(numToString(num) + "pronoslist", listByNoteProno.subList(0, 8 + numberOfRecence));
			        sub = 8;
				}
				if (!listByNoteProno.isEmpty() && listByNoteProno.get(0).getNoteProno() > 0
						 && raceSize >= 8 && raceSize < 10) {
//					model.addAttribute(numToString(num) + "pronoslist", listByNoteProno.subList(0, 6 + numberOfRecence));
			        sub = 6;
				}
				if (!listByNoteProno.isEmpty() && listByNoteProno.get(0).getNoteProno() > 0
						&& raceSize > 4 && raceSize <= 7) {
//					model.addAttribute(numToString(num) + "pronoslist", listByNoteProno.subList(0, 5 + numberOfRecence));
			        sub = 5;
				}
				
				if (!listByNoteProno.isEmpty() && listByNoteProno.get(0).getNoteProno() > 0
						&& raceSize < 5) {
//					model.addAttribute(numToString(num) + "pronoslist", listByNoteProno);
					sub = listByNoteProno.size();
				}
				
				int numberOfRecence = 0;
				
				if(!listByNoteProno.isEmpty() && listByNoteProno.size() >= sub) {
					for(TurfInfos i : listByNoteProno.subList(0, sub)) {
						if(i.getRecence() > recenceMax) {
							numberOfRecence += 1;
							if((sub + numberOfRecence) >= raceSize) {
								numberOfRecence -= 1;
							}
						}
					}
					if(numberOfRecence > 1 ) {
						numberOfRecence = 1;
					};
				}
				
				//A modifier  et numberOfRecence si besoin d'un cheval en plus
				numberOfRecence = 0;
				
								
				if(!sub.equals(null) && (sub + numberOfRecence) <= listByNoteProno.size()) {
					model.addAttribute(numToString(num) + "pronoslist", listByNoteProno.subList(0, sub + numberOfRecence));
					bigList.add(listByNoteProno.subList(0, sub + numberOfRecence));
				} else {
					model.addAttribute(numToString(num) + "pronoslist", listByNoteProno);
					bigList.add(listByNoteProno);
				}
//				model.addAttribute("bonscoupsdujour", resultService.createBonCoupsDuJour(bigList, resultRepository.findAllByJourAndReunion(jour, allraceInfos.get(0).getR())));
//						System.out.println(resultService.createBonCoupsDuJour(bigList, resultRepository.findAllByJourAndReunion(jour, allraceInfos.get(0).getR())).size() + " mapsizeb");
//				
				if (listByNoteProno.isEmpty()) {
					model.addAttribute(numToString(num) + "pronoslist", new ArrayList<>());
				}

				if (listByRanking.size() < 5) {
					model.addAttribute(numToString(num) + "classementlist", listByRanking);
				}
				if (listByRanking.size() >= 5) {
					model.addAttribute(numToString(num) + "classementlist", listByRanking.subList(0, 5));
				}

//			model.addAttribute(numToString(num) + "pronoslist", listByPronos);

				if (!listByChronos.isEmpty() || listByChronos.size() > 0) {
					model.addAttribute(numToString(num) + "chronos", true);
				}
				if (listByChronos.isEmpty() || listByChronos.size() == 0) {
					model.addAttribute(numToString(num) + "chronos", false);
				}

				if (!listByTayPronos.isEmpty() || listByTayPronos.size() > 0) {
					model.addAttribute(numToString(num) + "taypronos", true);
				}
				if (listByTayPronos.isEmpty() || listByTayPronos.size() == 0) {
					model.addAttribute(numToString(num) + "taypronos", false);
				}

				System.out.println(allraceInfos.get(0).getRaceSpecialty());

				if (allraceInfos.get(0).getRaceSpecialty() != null) {

					if (allraceInfos.get(0).getRaceSpecialty().equals("A")
							|| allraceInfos.get(0).getRaceSpecialty().equals("M")) {
						model.addAttribute("specialty", "trot");
					} else if (!allraceInfos.get(0).getRaceSpecialty().equals("A")
							&& !allraceInfos.get(0).getRaceSpecialty().equals("M")) {
						model.addAttribute("specialty", "galop");
						model.addAttribute(numToString(num) + "valeurlist", listByValeur);
					}
				} else {
					model.addAttribute("specialty", "none");
				}

				model.addAttribute(numToString(num) + "exists", true);

			}

			List<TurfInfos> listByNumCheval = allraceInfos.stream()
//					.map(TurfInfos :: getNumero)
					.sorted(Comparator.comparingInt(TurfInfos::getNumero)).collect(Collectors.toList());
//					List<Integer> listNums = new ArrayList<Integer>(distinctNumsCheval);
//					Collections.reverse(listNums);
//					distinctNumsCheval = new LinkedHashSet<>(listNums);

			model.addAttribute(numToString(num) + "horses", listByNumCheval);
			model.addAttribute(numToString(num) + "numcourse", allraceInfos.get(0).getNumcourse());
			model.addAttribute("reunion", allPremiumReunionInfos.get(0).getR());
			model.addAttribute(numToString(num) + "race", num);

		}
		
		////////////Boncoupsdu jour//////////////
		Collections.reverse(bigList);
		model.addAttribute("bonscoupsdujour", resultService.createBonCoupsDuJour(bigList, resultRepository.findAllByJourAndReunion(jour, allraceInfos.get(0).getR())));
		System.out.println(resultService.createBonCoupsDuJour(bigList, resultRepository.findAllByJourAndReunion(jour, allraceInfos.get(0).getR())).size() + " mapsizeb");
        System.out.println(bigList.size() + " big");

		//////////// CRACKS/////////////////
		List<TurfInfos> crackList = reunionCracks.stream().filter(ti -> ti.getNoteProno() >= 30)
				.sorted(Comparator.comparingDouble(TurfInfos::getNoteProno)).collect(Collectors.toList());
		Collections.reverse(crackList);

		model.addAttribute("cracklist", crackList);
		model.addAttribute("supercracknote", 35);

////////////Plac??s du jour/////////////////
		List<TurfInfos> placeList = allPremiumReunionInfos.stream()
				.filter(ti -> ti.getPourcPlaceCheval() != null && ti.getPourcPlaceCheval() == 100)
				.sorted(Comparator.comparingInt(TurfInfos::getC).thenComparingInt(TurfInfos::getNumero))
				.collect(Collectors.toList());
//	Collections.reverse(placeList);

		model.addAttribute("placelist", placeList);

		////////// COUPLES DU JOUR//////////////
		Set<String> cples = new HashSet<>();
		for (TurfInfos info : crackList) {

			List<TurfInfos> couple = crackList.stream().filter(ti -> ti.getC() == info.getC())
					.sorted(Comparator.comparingInt(TurfInfos::getNumero)).collect(Collectors.toList());
			if (couple.size() >= 2) {
				String C = couple.get(0).getC().toString();
				String one = couple.get(0).getNumero().toString();
				String two = couple.get(1).getNumero().toString();

				if (couple.get(0).getNumero() < 10) {
					one = "0" + couple.get(0).getNumero();
				}
				if (couple.get(1).getNumero() < 10) {
					two = "0" + couple.get(1).getNumero().toString();
				}

				String cple = C + one + " - " + C + two;
				cples.add(cple);
			}
		}
		List<String> couples = cples.stream().sorted().collect(Collectors.toList());
//  		model.addAttribute("couples", new ArrayList<>());
		model.addAttribute("couples", couples);

		model.addAttribute("taypronos", tayPronos);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////// reusOfDay
		Set<String> reunions = turfInfosRepository.findAllByJour(jour).stream()
				.filter(ti -> ti.getJour().equals(jour) && ti.getR().length() < 3).map(TurfInfos::getReunionstring)
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
		
//		allraceInfos.forEach(ti-> {
//			System.out.println(ti.getIsRunning() + " isrunning");
//			System.out.println(ti.getIsPremium() + " premium");
//			System.out.println(ti.getNumcourse() + " numcourse");
//			System.out.println(ti.getCoursescheval() + " coursescheval");
//			System.out.println(ti.getRaceSpecialty() + " specialty");
//
//		});

		return "reunion-infos";
	}

	///////////////////////////////////////////////////////////////
	private String numToString(Integer num) {

		String str = null;

		switch (num) {

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

		switch (num) {

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

	private List<TurfInfos> calculateFinalNoteProno(List<TurfInfos> allraceInfos, List<TurfInfos> listBypvch,
			List<TurfInfos> listBypvjh, List<TurfInfos> listBypveh,

			List<TurfInfos> listByppch, List<TurfInfos> listByppjh, List<TurfInfos> listByppeh,

			List<TurfInfos> listBytxv, List<TurfInfos> listBytxp, List<TurfInfos> listBytxvh,
			List<TurfInfos> listBytxph, List<TurfInfos> listByppc,

			List<TurfInfos> listByChronos,
			
			List<TurfInfos> listBypvcs, List<TurfInfos> listByppcs, List<TurfInfos> listBypvcsh,  List<TurfInfos> listByppcsh,
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

//	   calculateNoteProno(allraceInfos, listByChronos, 11d);
		calculateNoteProno(allraceInfos, listByppc, 12d);
		
		calculateNoteProno(allraceInfos, listBypvcs, 13d);
		calculateNoteProno(allraceInfos, listByppcs, 14d);
		calculateNoteProno(allraceInfos, listBypvcsh, 15d);
		calculateNoteProno(allraceInfos, listByppcsh, 16d);


		return allraceInfos;
	}

	private List<TurfInfos> calculateNoteProno(List<TurfInfos> allRaceInfos, List<TurfInfos> list,
			Double percentageParameter) {

		for (int i = 0; i < list.size(); i++) {

			if (list.size() > 0 && i == 0) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}

			if (list.size() > 1 && i == 1) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}

			if (list.size() > 2 && i == 2) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 3 && i == 3) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 4 && i == 4) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 5 && i == 5) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 6 && i == 6) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 7 && i == 7) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 8 && i == 8) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 9 && i == 9) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 10 && i == 10) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 11 && i == 11) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 12 && i == 12) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 13 && i == 13) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 14 && i == 14) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 15 && i == 15) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 16 && i == 16) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 17 && i == 17) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 18 && i == 18) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 19 && i == 19) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
			if (list.size() > 20 && i == 20) {
				setPercentageParam(list.get(i), percentageParameter);
				list.get(i).setNoteProno(list.get(i).getNoteProno() + list.get(i).getNotePercentageParameter() / 10);
			}
		}

		return list;
	}

	private TurfInfos setPercentageParam(TurfInfos tinf, Double percentParam) {

		if (percentParam == 1d) {
			tinf.setNotePercentageParameter(tinf.getPourcVictChevalHippo());
		}
		if (percentParam == 2d) {
			tinf.setNotePercentageParameter(tinf.getPourcVictJockHippo());
		}
		if (percentParam == 3d) {
			tinf.setNotePercentageParameter(tinf.getPourcVictEntHippo());
		}
		if (percentParam == 4d) {
			tinf.setNotePercentageParameter(tinf.getPourcPlaceChevalHippo());
		}
		if (percentParam == 5d) {
			tinf.setNotePercentageParameter(tinf.getPourcPlaceJockHippo());
		}
		if (percentParam == 6d) {
			tinf.setNotePercentageParameter(tinf.getPourcPlaceEntHippo());
		}
		if (percentParam == 7d) {
			tinf.setNotePercentageParameter(tinf.getTxVictCouple());
		}
		if (percentParam == 8d) {
			tinf.setNotePercentageParameter(tinf.getTxPlaceCouple());
		}
		if (percentParam == 9d) {
			tinf.setNotePercentageParameter(tinf.getTxVictCoupleHippo());
		}
		if (percentParam == 10d) {
			tinf.setNotePercentageParameter(tinf.getTxPlaceCoupleHippo());
		}

//	   if(percentParam == 11d) {
//		   tinf.setNotePercentageParameter(tinf.getChrono().doubleValue());
//	   }
		if (percentParam == 12d) {
			tinf.setNotePercentageParameter(tinf.getPourcPlaceCheval());
		}
		///////////////////////////////////////////
		
		if (percentParam == 13d) {
			tinf.setNotePercentageParameter(tinf.getPourcVictChevalSurface());
		}
		if (percentParam == 14d) {
			tinf.setNotePercentageParameter(tinf.getPourcPlaceChevalSurface());
		}
		if (percentParam == 15d) {
			tinf.setNotePercentageParameter(tinf.getPourcVictChevalSurfaceHippo());
		}
		if (percentParam == 16d) {
			tinf.setNotePercentageParameter(tinf.getPourcPlaceChevalSurfaceHippo());
		}

		return tinf;
	}

	private List<TurfInfos> calculateEntraineur(List<TurfInfos> AllRaceinfos) {

		LinkedList<TurfInfos> newList = new LinkedList<TurfInfos>();

		Set<String> distinctEntraineurs = AllRaceinfos.stream().map(TurfInfos::getEntraineur)
//				.sorted()
				.collect(Collectors.toSet());

		for (String entraineur : distinctEntraineurs) {

//		   List<TurfInfos> listByEnt = turfInfosRepository.findAllByNumcourseAndEntraineur(numcourse, entraineur);

			List<TurfInfos> listByEnt = AllRaceinfos.stream().filter(ti -> ti.getEntraineur().equals(entraineur))
					.collect(Collectors.toList());

			if (listByEnt.size() == 1) {
				TurfInfos tinf = listByEnt.get(0);
				tinf.setNumeroString(tinf.getNumero().toString());
				tinf.setCalculateEntraineurNumber(1);
				newList.add(tinf);
			}

			if (listByEnt.size() > 1) {

				TurfInfos tinf = new TurfInfos();
				tinf.setBlueEtoile(listByEnt.get(0).getBlueEtoile());
				tinf.setNbrCourseEntHippo(listByEnt.get(0).getNbrCourseEntHippo());

				tinf.setNumeroString("[" + listByEnt.get(0).getNumero().toString());
				tinf.setCalculateEntraineurNumber(1);
				if (listByEnt.get(0).getPourcPlaceEntHippo() != null) {
					tinf.setPourcPlaceEntHippo(listByEnt.get(0).getPourcPlaceEntHippo());
				}
				if (listByEnt.get(0).getPourcVictEntHippo() != null) {
					tinf.setPourcVictEntHippo(listByEnt.get(0).getPourcVictEntHippo());
//			   tinf.setPourcVictEntHippo(listByEnt.get(0).getPourcVictEntHippo());
				}

				for (int i = 1; i < listByEnt.size(); i++) {

					tinf.setNumeroString(tinf.getNumeroString() + ", " + listByEnt.get(i).getNumero().toString());
					tinf.setCalculateEntraineurNumber(tinf.getCalculateEntraineurNumber() + 1);
				}
				tinf.setNumeroString(tinf.getNumeroString() + "]");
				
//				for(TurfInfos info : AllRaceinfos) {
//					if(info.getEntraineur().equals(entraineur)) {
//						info.setCalculateEntraineurNumber(tinf.getCalculateEntraineurNumber());
//					}
//				};

				newList.add(tinf);
			}
		}
		List<TurfInfos> reverseList = newList.stream()
				.sorted(Comparator.comparingDouble(TurfInfos::getPourcVictEntHippo)).collect(Collectors.toList());
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

	private List<TurfInfos> setPastilles(List<TurfInfos> jockeys, List<TurfInfos> chronos, List<TurfInfos> vchevalh,
			List<TurfInfos> pchevalh, List<TurfInfos> vcouple, List<TurfInfos> pcouple, List<TurfInfos> pronos,
			List<TurfInfos> vchevals, List<TurfInfos> pchevals,
			int raceSize) {

		
			pronos.forEach(ti->{
				ti.setChronoPastille(false);
				ti.setChevalPastille(false);
				ti.setJockeyPastille(false);
				ti.setCouplePastille(false);
				ti.setSurfacePastille(false);
			});
		

		if (raceSize <= 7) {
			pronos.forEach(ti -> {
				if (jockeys.size() >= 5) {
					if (!jockeys.subList(0, 5).contains(ti)) {
						ti.setJockeyPastille(true);
					} else {
						ti.setJockeyPastille(false);
					}
				}
				if (chronos.size() >= 5) {
					if (!chronos.subList(0, 5).contains(ti)) {
						ti.setChronoPastille(true);
					} else {
						ti.setChronoPastille(false);
					}
				}
			});
		}

		if (raceSize == 8 || raceSize == 9) {
			pronos.forEach(ti -> {
				if (jockeys.size() >= 6) {
					if (!jockeys.subList(0, 6).contains(ti)) {
						ti.setJockeyPastille(true);
					} else {
						ti.setJockeyPastille(false);
					}
				}
				if (chronos.size() >= 6) {
					if (!chronos.subList(0, 6).contains(ti)) {
						ti.setChronoPastille(true);
					} else {
						ti.setChronoPastille(false);
					}
				}
			});
		}

		if (raceSize == 10 || raceSize == 11) {
			pronos.forEach(ti -> {
				if (jockeys.size() >= 7) {
					if (!jockeys.subList(0, 7).contains(ti)) {
						ti.setJockeyPastille(true);
					} else {
						ti.setJockeyPastille(false);
					}
				}
				if (chronos.size() >= 7) {
					if (!chronos.subList(0, 7).contains(ti)) {
						ti.setChronoPastille(true);
					} else {
						ti.setChronoPastille(false);
					}
				}
			});
		}

		if (raceSize == 12 || raceSize == 13) {
			pronos.forEach(ti -> {
				if (jockeys.size() >= 8) {
					if (!jockeys.subList(0, 8).contains(ti)) {
						ti.setJockeyPastille(true);
					} else {
						ti.setJockeyPastille(false);
					}
				}
				if (chronos.size() >= 8) {
					if (!chronos.subList(0, 8).contains(ti)) {
						ti.setChronoPastille(true);
					} else {
						ti.setChronoPastille(false);
					}
				}
			});
		}

		if (raceSize >= 14) {
			pronos.forEach(ti -> {
				if (jockeys.size() >= 9) {
					if (!jockeys.subList(0, 9).contains(ti)) {
						ti.setJockeyPastille(true);
					} else {
						ti.setJockeyPastille(false);
					}
				}
				if (chronos.size() >= 9) {
					if (!chronos.subList(0, 9).contains(ti)) {
						ti.setChronoPastille(true);
					} else {
						ti.setChronoPastille(false);
					}
				}
			});
		}

		pronos.forEach(ti -> {
			if (!vchevalh.contains(ti) && !pchevalh.contains(ti)) {
				ti.setChevalPastille(true);
			} else {
				ti.setChevalPastille(false);
			}
			
			if (!vcouple.contains(ti) && !pcouple.contains(ti)) {
				ti.setCouplePastille(true);
			} else {
				ti.setCouplePastille(false);
			}
			
			if (!vchevals.contains(ti) && !pchevals.contains(ti) && pronos.get(0).getSurface().equals("PSF")) {
				ti.setSurfacePastille(true);
			} else {
				ti.setSurfacePastille(false);
			}

		});
		


		return pronos;
	}
	
	private List<TurfInfos> crossProno(List<TurfInfos> pronos, int raceSize){
		
		
		if(pronos.size() > 3) {
		

		if(raceSize < 14) {
            Integer pastillesnumber = 0;
            TurfInfos info = pronos.get(pronos.size()-1);
            if(pronos.get(pronos.size()-1).getChevalPastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pronos.get(pronos.size()-1).getChronoPastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pronos.get(pronos.size()-1).getJockeyPastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pronos.get(pronos.size()-1).getCouplePastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pastillesnumber >= 3) {
            info.setIsCross(true);
            pronos.set(pronos.size()-1, info);
            }
            pastillesnumber = 0;

            if(pronos.get(pronos.size()-2).getChevalPastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pronos.get(pronos.size()-2).getChronoPastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pronos.get(pronos.size()-2).getJockeyPastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pronos.get(pronos.size()-2).getCouplePastille().equals(true)) {
            	pastillesnumber += 1;
            }
            if(pastillesnumber >= 3) {
            	pronos.get(pronos.size()-2).setIsCross(true);
            }
            System.out.println(pastillesnumber + " pas");
            System.out.println(pronos.get(pronos.size()-2).getIsCross());
		}

        if(raceSize > 13) {
        	   Integer pastillesnumber = 0;
               if(pronos.get(pronos.size()-1).getChevalPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-1).getChronoPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-1).getJockeyPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-1).getCouplePastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pastillesnumber >= 3) {
               pronos.get(pronos.size()-1).setIsCross(true);
               }
               pastillesnumber = 0;

               if(pronos.get(pronos.size()-2).getChevalPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-2).getChronoPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-2).getJockeyPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-2).getCouplePastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pastillesnumber >= 3) {
               	pronos.get(pronos.size()-2).setIsCross(true);
               }
               pastillesnumber = 0;

               if(pronos.get(pronos.size()-3).getChevalPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-3).getChronoPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-3).getJockeyPastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pronos.get(pronos.size()-3).getCouplePastille().equals(true)) {
               	pastillesnumber += 1;
               }
               if(pastillesnumber >= 3) {
               	pronos.get(pronos.size()-3).setIsCross(true);
               }
               System.out.println(pastillesnumber + " pas");
               System.out.println(pronos.get(pronos.size()-3).getIsCross());
		}
        
		}
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
		  
		  List<TurfInfos> vsurface,
		  List<TurfInfos> psurface,
		  List<TurfInfos> vsurfaceh,
		  List<TurfInfos> psurfaceh,
		  
		  List<TurfInfos> allRace, int raceSize){
	  
	   
		allRace.forEach(ti-> {
			   
			  ///// Three Etoiles////////////

			   Integer threeEtoilesNumber = 0;
			   Integer fiveEtoilesNumber = 0;
			   Integer blueEtoilesNumber = 0;
			   Integer purpleEtoilesNumber = 0;


			   for(int i = 0; i < chronos.size(); i++) {
				   if(chronos.get(i).getId().equals(ti.getId())) {
					   threeEtoilesNumber += 1;
				   }
				   if(i == 3) {
					   break;
				   }
			   }
			   
			   Integer endIterAt = 3;
			   Double pourcRef = 0d;
			   if(entraineurs.size() > 0) {
				   pourcRef = entraineurs.get(0).getPourcVictEntHippo();
			   } 
			   for(int i = 0; i < entraineurs.size(); i++) {
				   
				   if(entraineurs.get(i).getId().equals(ti.getId())) {
					   threeEtoilesNumber += 1;
				   }
				   if(i > 0 && entraineurs.get(i).getPourcVictEntHippo().equals(pourcRef)) {
					   endIterAt += 1;
				   } else {
					   pourcRef = entraineurs.get(i).getPourcVictEntHippo();
				   }

				   if(i == endIterAt) {
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
			   
				  ///// Five and purple Etoiles////////////
					   
			  
               for(int i = 0; i < chronos.size(); i++) {
				   if(chronos.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               endIterAt = 4;
               if(entraineurs.size() > 0) {
				   pourcRef = entraineurs.get(0).getPourcVictEntHippo();
			   }                
               for(int i = 0; i < entraineurs.size(); i++) {
            	   
            	   if(entraineurs.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
            	   
            	   if(i > 0 && entraineurs.get(i).getPourcVictEntHippo().equals(pourcRef)) {
					   endIterAt += 1;
				   } else {
					   pourcRef = entraineurs.get(i).getPourcVictEntHippo();
				   }

				   if(i == endIterAt) {
					   break;
				   }
			   }
               for(int i = 0; i < jockeys.size(); i++) {
				   if(jockeys.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               for(int i = 0; i < vchevalh.size(); i++) {
				   if(vchevalh.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               for(int i = 0; i < pchevalh.size(); i++) {
				   if(pchevalh.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               for(int i = 0; i < pcheval.size(); i++) {
				   if(pcheval.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               for(int i = 0; i < vcouple.size(); i++) {
				   if(vcouple.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               for(int i = 0; i < pcouple.size(); i++) {
				   if(pcouple.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               for(int i = 0; i < vcoupleh.size(); i++) {
				   if(vcoupleh.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               for(int i = 0; i < pcoupleh.size(); i++) {
				   if(pcoupleh.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               
               for(int i = 0; i < vsurface.size(); i++) {
				   if(vsurface.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               
               for(int i = 0; i < psurface.size(); i++) {
				   if(psurface.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               
               for(int i = 0; i < vsurfaceh.size(); i++) {
				   if(vsurfaceh.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               
               for(int i = 0; i < psurfaceh.size(); i++) {
				   if(psurfaceh.get(i).getId().equals(ti.getId())) {
					   fiveEtoilesNumber += 1;
					   if(allRace.get(0).getRaceSpecialty().equals("P")) {
						   purpleEtoilesNumber += 1;
					   }
				   }
				   if(i == 4) {
					   break;
				   }
			   }
               
               //// Blue Etoile
               endIterAt = 2;
               if(entraineurs.size() > 0) {
				   pourcRef = entraineurs.get(0).getPourcVictEntHippo();
			   } 
               
               if(allRace.get(0).getRaceSpecialty().equals("P")) {
               for(int i = 0; i < entraineurs.size(); i++) {
            	   
            	   if(entraineurs.get(i).getId().equals(ti.getId())) {
					   blueEtoilesNumber += 1;
				   }
            	   
            	   if(i > 0 && entraineurs.get(i).getPourcVictEntHippo().equals(pourcRef)) {
					   endIterAt += 1;
				   } else {
					   pourcRef = entraineurs.get(i).getPourcVictEntHippo();
				   }
        
				   if(i == endIterAt) {
					   break;
				   }
			   }
               
			   for(int i = 0; i < jockeys.size(); i++) {
				   if(jockeys.get(i).getId().equals(ti.getId())) {
					   blueEtoilesNumber += 1;
				   }
				   if(i == 2) {
					   break;
				   }
			   }
		   }
               
               
			 
               if(threeEtoilesNumber >= 3) {
				   ti.setThreeEtoile(true);
			   } else {
				   ti.setThreeEtoile(false);
			   }
			   
               
			   ti.setFiveEtoile(false);

			   if(allRace.size()>0 && allRace.get(0).getSurface() != "PSF" && fiveEtoilesNumber >= 6) {
				   ti.setFiveEtoile(true);
			   } else if(allRace.size()>0 && allRace.get(0).getSurface() == "PSF" && fiveEtoilesNumber >= 7) {
				   ti.setFiveEtoile(true);
			   }
			   else {
				   ti.setFiveEtoile(false);
			   }
			   
			   
			   if(blueEtoilesNumber >= 2) {
				   ti.setBlueEtoile(true);
			   } else {
				   ti.setBlueEtoile(false);
			   }
			   
//			   if(purpleEtoilesNumber == 5) {
//				   ti.setPurpleEtoile(true);
//			   } else {
				   ti.setPurpleEtoile(false);
//			   }
			      
		   });
		   

	   return allRace;
   }

	   private void navbarInfos(Model model) {
		   
//		   List<TurfInfos> allInfos = turfInfosRepository.findAll();

		   
		   //DATES
			 Set<String> dates = turfInfosRepository.findAllJours()
		  			 .stream()
		  			 .collect(Collectors.toSet());
//	  	 Set<String> dates = allInfos.stream()
//					.map(TurfInfos :: getJour)
//					.sorted()
//					.collect(Collectors.toSet());
	  	 
	  	List<String> datesSorted = dates.stream().collect(Collectors.toList());
	  	Collections.sort(datesSorted, (o1, o2) -> o1.compareTo(o2));
	        model.addAttribute("datesnav", datesSorted);		   
	       //REUNIONS
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	       String jour = LocalDateTime.now().format(formatter);
	       model.addAttribute("journav", jour);
	       System.out.println(jour);
	       
	       
//	    	 Set<String> reunions = allInfos.stream()
//					.filter(ti-> ti.getJour().equals(jour) && ti.getR().length()<3)
//	  				.map(TurfInfos :: getReunionstring)
////	  				.sorted()
//	  				.collect(Collectors.toSet());
////	    	 reunions.sort( Comparator.comparing( String::toString));
//	         model.addAttribute("reunionsofday", reunions);

		   List<TurfInfos> allByJour = turfInfosRepository.findAllByJour(jour);

	         Set<String> reunions = allByJour.stream()
//	 				.filter(ti-> ti.getJour().equals(jour))
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