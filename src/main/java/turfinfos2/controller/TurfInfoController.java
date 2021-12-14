package turfinfos2.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import turfinfos2.model.Resultat;
import turfinfos2.model.TurfInfos;
import turfinfos2.repository.ResultRepository;
import turfinfos2.repository.TurfInfosRepository;

@Controller
public class TurfInfoController {

	@Autowired
	TurfInfosRepository turfInforepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@GetMapping("/delete-day")
	public String deleteDay(@RequestParam("jour") String jour){
		
		List<TurfInfos> del = turfInforepository.deleteByJour(jour);
		List<Resultat> delResult = resultRepository.deleteByJour(jour);

		
		return "redirect:/";
	}


	@PostMapping("/set-chronos")
	public String enterChronos(@RequestParam(name = "one", required = false) String one,
			@RequestParam(name = "two", required = false) String two,
			@RequestParam(name = "three", required = false) String three,
			@RequestParam(name = "four", required = false) String four,
			@RequestParam(name = "five", required = false) String five,
			@RequestParam(name = "six", required = false) String six,
			@RequestParam(name = "seven", required = false) String seven,
			@RequestParam(name = "eight", required = false) String eight,
			@RequestParam(name = "nine", required = false) String nine,
			@RequestParam(name = "jour", required = false) String jour,
			@RequestParam(name = "reunion", required = false) String reunion) {

		if (one != null && one != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(one)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 1);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(1);
			turfInforepository.save(tinf);

		}
//		   else {
//				  Optional<TurfInfos> OptInf = turfInforepository.findByChrono(1);
//				  if(OptInf.isPresent()) {
//					  TurfInfos tinf = OptInf.get();
//					  tinf.setChrono(null);
//					  turfInforepository.save(tinf);
//				  }
//		   }

		if (two != null && two != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(two)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 2);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(2);
			turfInforepository.save(tinf);

		}
		if (three != null && three != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(three)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 3);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(3);
			turfInforepository.save(tinf);

		}
		if (four != null && four != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(four)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 4);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(4);
			turfInforepository.save(tinf);

		}
		if (five != null && five != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(five)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 5);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(5);
			turfInforepository.save(tinf);

		}
		if (six != null && six != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(six)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 6);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(6);
			turfInforepository.save(tinf);

		}
		if (seven != null && seven != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(seven)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 7);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(7);
			turfInforepository.save(tinf);

		}
		if (eight != null && eight != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(eight)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 8);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(8);
			turfInforepository.save(tinf);
		}

		if (nine != null && nine != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(nine)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndChrono(tinf.getNumcourse(), 9);
			list.stream().forEach(ti -> {
				ti.setChrono(null);
				turfInforepository.save(ti);
			});

			tinf.setChrono(9);
			turfInforepository.save(tinf);
		}

		return "redirect:/reunion-infos?jour=" + jour + "&reunion=" + reunion;
	}

	@PostMapping("/set-tay-pronos")
	public String entertayPronos(@RequestParam(name = "one", required = false) String one,
			@RequestParam(name = "two", required = false) String two,
			@RequestParam(name = "three", required = false) String three,
			@RequestParam(name = "four", required = false) String four,
			@RequestParam(name = "five", required = false) String five,
	    		@RequestParam(name = "six", required = false) String six,
	    		@RequestParam(name = "seven", required = false) String seven,
	    		@RequestParam(name = "eight", required = false) String eight,
	    		@RequestParam(name = "nine", required = false) String nine,
			@RequestParam(name = "jour", required = false) String jour,
			@RequestParam(name = "reunion", required = false) String reunion,
			@RequestParam(name = "numcourse", required = false) String numcourse) {

		if (one == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 1);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (one != null && one != "") {
			TurfInfos tinf1 = turfInforepository.findById(Long.valueOf(one)).get();
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 1);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
			tinf1.setTayProno(1);
			turfInforepository.save(tinf1);
		}
		
		if (two == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 2);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (two != null && two != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(two)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 2);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});

			tinf.setTayProno(2);
			turfInforepository.save(tinf);
		}
		
		if (three == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 3);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (three != null && three != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(three)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 3);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});

			tinf.setTayProno(3);
			turfInforepository.save(tinf);

		}
		
		if (four == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 4);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (four != null && four != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(four)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 4);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});

			tinf.setTayProno(4);
			turfInforepository.save(tinf);

		}
		
		if (five == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 5);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (five != null && five != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(five)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 5);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});

			tinf.setTayProno(5);
			turfInforepository.save(tinf);

		}
		
		
		if (six == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 6);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (six != null && six != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(six)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 6);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});

			tinf.setTayProno(6);
			turfInforepository.save(tinf);

		}
		
		
		if (seven == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 7);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (seven != null && seven != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(seven)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 7);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});

			tinf.setTayProno(7);
			turfInforepository.save(tinf);

		}
		
		
		if (eight == "") {
			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(Integer.valueOf(numcourse), 8);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});
		}
		else if (eight != null && eight != "") {
			TurfInfos tinf = turfInforepository.findById(Long.valueOf(eight)).get();

			List<TurfInfos> list = turfInforepository.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 8);
			list.stream().forEach(ti -> {
				ti.setTayProno(null);
				turfInforepository.save(ti);
			});

			tinf.setTayProno(8);
			turfInforepository.save(tinf);

		}

		return "redirect:/reunion-infos?jour=" + jour + "&reunion=" + reunion;
	}
	
	@PostMapping("/cross-prono")
	public String crossPronos(
			@RequestParam(name = "one", required = false) String one,
			@RequestParam(name = "two", required = false) String two,
			@RequestParam(name = "three", required = false) String three,
			@RequestParam(name = "four", required = false) String four,
//			@RequestParam(name = "five", required = false) String five,
//	    		@RequestParam(name = "six", required = false) String six,
//	    		@RequestParam(name = "seven", required = false) String seven,
//	    		@RequestParam(name = "eight", required = false) String eight,
//	    		@RequestParam(name = "nine", required = false) String nine,
			@RequestParam(name = "jour", required = false) String jour,
			@RequestParam(name = "reunion", required = false) String reunion,
			@RequestParam(name = "numcourse", required = false) String numcourse) {
		System.out.println(one);
		System.out.println(two);
		System.out.println(three);
		System.out.println(four);


		 if (one != null && one != "") {
			TurfInfos tinf1 = turfInforepository.findById(Long.valueOf(one)).get();
			if(tinf1.getIsCross().equals(false)) {
				tinf1.setIsCross(true);
				turfInforepository.save(tinf1);
			}
			else if(tinf1.getIsCross().equals(true)) {
				tinf1.setIsCross(false);
				turfInforepository.save(tinf1);
			}
			System.out.println(one);
		}
		
		 if (two != null && two != "") {
				TurfInfos tinf1 = turfInforepository.findById(Long.valueOf(two)).get();
				if(tinf1.getIsCross().equals(false)) {
					tinf1.setIsCross(true);
					turfInforepository.save(tinf1);
				}
				else if(tinf1.getIsCross().equals(true)) {
					tinf1.setIsCross(false);
					turfInforepository.save(tinf1);
				}
				System.out.println(two);
			}
		
		 if (three != null && three != "") {
				TurfInfos tinf1 = turfInforepository.findById(Long.valueOf(three)).get();
				if(tinf1.getIsCross().equals(false)) {
					tinf1.setIsCross(true);
					turfInforepository.save(tinf1);
				}
				else if(tinf1.getIsCross().equals(true)) {
					tinf1.setIsCross(false);
					turfInforepository.save(tinf1);
				}
				System.out.println(three);
			}
		
		 if (four != null && four != "") {
				TurfInfos tinf1 = turfInforepository.findById(Long.valueOf(four)).get();
				if(tinf1.getIsCross().equals(false)) {
					tinf1.setIsCross(true);
					turfInforepository.save(tinf1);
				}
				else if(tinf1.getIsCross().equals(true)) {
					tinf1.setIsCross(false);
					turfInforepository.save(tinf1);
				}
				System.out.println(four);
			}
		

		return "redirect:/reunion-infos?jour=" + jour + "&reunion=" + reunion;
	}

	////////////////////////////////////////////////////////////////////////////////

	private void navbarInfos(Model model) {

		List<TurfInfos> allInfos = turfInforepository.findAll();

		// DATES
		Set<String> dates = allInfos.stream()
				.map(TurfInfos::getJour)
 				.sorted()
				.collect(Collectors.toSet());
		List<String> datesSorted = dates.stream().collect(Collectors.toList());
	  	Collections.sort(datesSorted, (o1, o2) -> o1.compareTo(o2));
	        model.addAttribute("datesnav", datesSorted);
		// REUNIONS
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

		Set<String> reunions = allInfos.stream().filter(ti -> ti.getJour().equals(jour) && ti.getR().length() < 3)
				.map(TurfInfos::getReunionstring).collect(Collectors.toSet());
		List<String> list = new ArrayList<String>(reunions);
		Collections.sort(list);
		reunions = new LinkedHashSet<>(list);
		model.addAttribute("reunionsofday", reunions);

	}

}
