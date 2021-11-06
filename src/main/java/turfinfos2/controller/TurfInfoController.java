package turfinfos2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;

@Controller
public class TurfInfoController {
	
	@Autowired
	TurfInfosRepository repo;
	
	   @PostMapping("/set-chronos")
	    public String enterChronos(
	    		@RequestParam(name = "one", required = false) String one, 
	    		@RequestParam(name = "two", required = false) String two,
	    		@RequestParam(name = "three", required = false) String three,
	    		@RequestParam(name = "four", required = false) String four,
	    		@RequestParam(name = "five", required = false) String five,
	    		@RequestParam(name = "six", required = false) String six,
	    		@RequestParam(name = "seven", required = false) String seven,
	    		@RequestParam(name = "eight", required = false) String eight,
	    		@RequestParam(name = "nine", required = false) String nine,
	    		@RequestParam(name = "jour", required = false) String jour,
	    		@RequestParam(name = "reunion", required = false) String reunion
	    ) {
		   
		   

		   if(one != null && one != "") {
			  TurfInfos tinf = repo.findById(Long.valueOf(one)).get();
			  
			  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 1);
			  list.stream()
			  .forEach(ti -> {
				  ti.setChrono(null);
				  repo.save(ti);
			  } );
			  
			  tinf.setChrono(1);
			  repo.save(tinf);

		   }
//		   else {
//				  Optional<TurfInfos> OptInf = repo.findByChrono(1);
//				  if(OptInf.isPresent()) {
//					  TurfInfos tinf = OptInf.get();
//					  tinf.setChrono(null);
//					  repo.save(tinf);
//				  }
//		   }
		   
		   if(two != null && two != "") {
				  TurfInfos tinf = repo.findById(Long.valueOf(two)).get();
				  
				  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 2);
				  list.stream()
				  .forEach(ti -> {
					  ti.setChrono(null);
					  repo.save(ti);
				  } );
				  
				  tinf.setChrono(2);
				  repo.save(tinf);

			   } if(three != null && three != "") {
					  TurfInfos tinf = repo.findById(Long.valueOf(three)).get();
					  
					  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 3);
					  list.stream()
					  .forEach(ti -> {
						  ti.setChrono(null);
						  repo.save(ti);
					  } );
					  
					  tinf.setChrono(3);
					  repo.save(tinf);

				   } if(four != null && four != "") {
						  TurfInfos tinf = repo.findById(Long.valueOf(four)).get();
						  
						  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 4);
						  list.stream()
						  .forEach(ti -> {
							  ti.setChrono(null);
							  repo.save(ti);
						  } );
						  
						  tinf.setChrono(4);
						  repo.save(tinf);

					   } if(five != null && five != "") {
							  TurfInfos tinf = repo.findById(Long.valueOf(five)).get();
							  
							  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 5);
							  list.stream()
							  .forEach(ti -> {
								  ti.setChrono(null);
								  repo.save(ti);
							  } );
							  
							  tinf.setChrono(5);
							  repo.save(tinf);

						   } if(six != null && six != "") {
								  TurfInfos tinf = repo.findById(Long.valueOf(six)).get();
								  
								  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 6);
								  list.stream()
								  .forEach(ti -> {
									  ti.setChrono(null);
									  repo.save(ti);
								  } );
								  
								  tinf.setChrono(6);
								  repo.save(tinf);

							   } if(seven != null && seven != "") {
									  TurfInfos tinf = repo.findById(Long.valueOf(seven)).get();
									  
									  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 7);
									  list.stream()
									  .forEach(ti -> {
										  ti.setChrono(null);
										  repo.save(ti);
									  } );
									  
									  tinf.setChrono(7);
									  repo.save(tinf);

								   }
							   if(eight != null && eight != "") {
										  TurfInfos tinf = repo.findById(Long.valueOf(eight)).get();
										  
										  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 8);
										  list.stream()
										  .forEach(ti -> {
											  ti.setChrono(null);
											  repo.save(ti);
										  } );
										  
										  tinf.setChrono(8);
										  repo.save(tinf);
									   }
							   
							   if(nine != null && nine != "") {
									  TurfInfos tinf = repo.findById(Long.valueOf(nine)).get();
									  
									  List<TurfInfos> list = repo.findAllByNumcourseAndChrono(tinf.getNumcourse(), 9);
									  list.stream()
									  .forEach(ti -> {
										  ti.setChrono(null);
										  repo.save(ti);
									  } );
									  
									  tinf.setChrono(9);
									  repo.save(tinf);
								   }

			return "redirect:/reunion-infos?jour=" + jour + "&reunion=" + reunion;
	    }
	   
	   
	   @PostMapping("/set-tay-pronos")
	    public String entertayPronos(
	    		@RequestParam(name = "one", required = false) String one, 
	    		@RequestParam(name = "two", required = false) String two,
	    		@RequestParam(name = "three", required = false) String three,
	    		@RequestParam(name = "four", required = false) String four,
	    		@RequestParam(name = "five", required = false) String five,
//	    		@RequestParam(name = "six", required = false) String six,
//	    		@RequestParam(name = "seven", required = false) String seven,
//	    		@RequestParam(name = "eight", required = false) String eight,
//	    		@RequestParam(name = "nine", required = false) String nine,
	    		@RequestParam(name = "jour", required = false) String jour,
	    		@RequestParam(name = "reunion", required = false) String reunion
	    ) {
		   
		   

		   if(one != null && one != "") {
			  TurfInfos tinf = repo.findById(Long.valueOf(one)).get();
			  
			  List<TurfInfos> list = repo.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 1);
			  list.stream()
			  .forEach(ti -> {
				  ti.setTayProno(null);
				  repo.save(ti);
			  } );
			  
			  tinf.setTayProno(1);
			  repo.save(tinf);

		   }
		   if(two != null && two != "") {
				  TurfInfos tinf = repo.findById(Long.valueOf(two)).get();
				  
				  List<TurfInfos> list = repo.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 2);
				  list.stream()
				  .forEach(ti -> {
					  ti.setTayProno(null);
					  repo.save(ti);
				  } );
				  
				  tinf.setTayProno(2);
				  repo.save(tinf);

			   }
		   if(three != null && three != "") {
				  TurfInfos tinf = repo.findById(Long.valueOf(three)).get();
				  
				  List<TurfInfos> list = repo.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 3);
				  list.stream()
				  .forEach(ti -> {
					  ti.setTayProno(null);
					  repo.save(ti);
				  } );
				  
				  tinf.setTayProno(3);
				  repo.save(tinf);

			   }
		   if(four != null && four != "") {
				  TurfInfos tinf = repo.findById(Long.valueOf(four)).get();
				  
				  List<TurfInfos> list = repo.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 4);
				  list.stream()
				  .forEach(ti -> {
					  ti.setTayProno(null);
					  repo.save(ti);
				  } );
				  
				  tinf.setTayProno(4);
				  repo.save(tinf);

			   }
		   if(five != null && five != "") {
				  TurfInfos tinf = repo.findById(Long.valueOf(five)).get();
				  
				  List<TurfInfos> list = repo.findAllByNumcourseAndTayProno(tinf.getNumcourse(), 5);
				  list.stream()
				  .forEach(ti -> {
					  ti.setTayProno(null);
					  repo.save(ti);
				  } );
				  
				  tinf.setTayProno(5);
				  repo.save(tinf);

			   }

			return "redirect:/reunion-infos?jour=" + jour + "&reunion=" + reunion;
	    }

}
