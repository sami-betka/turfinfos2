package turfinfos2.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turfinfos2.model.TurfInfos;
import turfinfos2.repository.TurfInfosRepository;

@Service
public class TurfInfoService {
	
	@Autowired
	TurfInfosRepository turfInfosRepository;
	
    public TurfInfos update(TurfInfos info, TurfInfos infoToUpdate) {
    	
//    	TurfInfos infoToUpdate = turfInfosRepository.findByNumeroAndNumcourse(info.getNumero(), info.getNumcourse());
    	
    	infoToUpdate.setC(info.getC());
    	infoToUpdate.setCheval(info.getCheval());
    	infoToUpdate.setJour(info.getJour());
    	infoToUpdate.setNumcourse(info.getNumcourse());
    	infoToUpdate.setNumero(info.getNumero());
    	infoToUpdate.setPourcPlaceChevalHippo(info.getPourcPlaceChevalHippo());
    	infoToUpdate.setPourcPlaceCheval(info.getPourcPlaceCheval());

    	infoToUpdate.setPourcPlaceEntHippo(info.getPourcPlaceEntHippo());
    	infoToUpdate.setPourcPlaceJockHippo(info.getPourcPlaceJockHippo());
    	infoToUpdate.setPourcVictChevalHippo(info.getPourcVictChevalHippo());
    	infoToUpdate.setPourcVictEntHippo(info.getPourcVictEntHippo());
    	infoToUpdate.setPourcVictJockHippo(info.getPourcVictJockHippo());
    	infoToUpdate.setR(info.getR());
    	infoToUpdate.setRecence(info.getRecence());
    	infoToUpdate.setTxPlaceCouple(info.getTxPlaceCouple());
    	infoToUpdate.setTxPlaceCoupleHippo(info.getTxPlaceCoupleHippo());
    	infoToUpdate.setTxVictCouple(info.getTxVictCouple());
    	infoToUpdate.setTxVictCoupleHippo(info.getTxVictCoupleHippo());
    	
    	infoToUpdate.setCoursescheval(info.getCoursescheval());
//    	infoToUpdate.setCoursesentraineur(info.getCoursesentraineur());
//    	infoToUpdate.setCoursesjockey(info.getCoursesjockey());
    	infoToUpdate.setNbCourseCouple(info.getNbCourseCouple());
    	infoToUpdate.setNbrCourseChevalHippo(info.getNbrCourseChevalHippo());
    	infoToUpdate.setNbrCourseJockHippo(info.getNbrCourseJockHippo());
    	infoToUpdate.setNbrCourseEntHippo(info.getNbrCourseEntHippo());
    	infoToUpdate.setNbCourseCoupleHippo(info.getNbCourseCoupleHippo());
//    	infoToUpdate.setTypec(info.getTypec());
    	infoToUpdate.setEntraineur(info.getEntraineur());
    	infoToUpdate.setCl(info.getCl());
    	infoToUpdate.setCotedirect(info.getCotedirect());
        	
    	
    	//Special params
//    	infoToUpdate.setBlinkersFirstTime(info.isBlinkersFirstTime());
//    	infoToUpdate.setNoShoesFirstTime(info.isNoShoesFirstTime());
//    	infoToUpdate.setProtectionFirstTime(info.isProtectionFirstTime());
//    	
//    	infoToUpdate.setDistanceAndSpecialtyChrono(info.getDistanceAndSpecialtyChrono());
    	
    	
//    	infoToUpdate.setReunionstring(info.getReunionstring());
   	
    	
//    	infoToUpdate.setNoteProno(null);
//    	infoToUpdate.setChrono(info.getChrono());
//    	infoToUpdate.setNumeroString(info.getNumero().toString());



    	
    	
    	return infoToUpdate;
    	
    }
    
    public List<TurfInfos> updateAllFromParisTurfJSON(List<TurfInfos> infos, List<TurfInfos> all) {
    	
    	List<TurfInfos> allToUpdate = new ArrayList<>();
//    	List<TurfInfos> allByNumcourse = turfInfosRepository.findAllByNumcourse(infos.get(0).getNumcourse());
    	List<TurfInfos> allByNumcourse = all
    			.stream()
    			.filter(ti->ti.getNumcourse().equals(infos.get(0).getNumcourse()))
    			.collect(Collectors.toList());


    	
    	for(TurfInfos info : infos) {
    	
    	TurfInfos infoToUpdate = allByNumcourse.stream().filter(ti->ti.getNumero().equals(info.getNumero())).findFirst().get();
    	
    	infoToUpdate.setHorseId(info.getHorseId());
    	infoToUpdate.setC(info.getC());
//    	infoToUpdate.setCheval(info.getCheval());
    	infoToUpdate.setJour(info.getJour());
    	infoToUpdate.setHour(info.getHour());
    	infoToUpdate.setNumcourse(info.getNumcourse());
    	infoToUpdate.setNumero(info.getNumero());
    	infoToUpdate.setR(info.getR());
    	infoToUpdate.setCountry(info.getCountry());
    	infoToUpdate.setAutostart(info.getAutostart());


//    	infoToUpdate.setRecence(info.getRecence());
    	infoToUpdate.setEntraineur(info.getEntraineur());
    	infoToUpdate.setJockey(info.getJockey());

    	infoToUpdate.setCl(info.getCl());
    	infoToUpdate.setDistanceAndSpecialtyChrono(info.getDistanceAndSpecialtyChrono());
    	infoToUpdate.setReunionstring(info.getReunionstring());
    	
    	//STATS
    	if(info.getNulStats().equals(false)) {
    	infoToUpdate.setPourcPlaceChevalHippo(info.getPourcPlaceChevalHippo());
    	infoToUpdate.setPourcPlaceEntHippo(info.getPourcPlaceEntHippo());
    	infoToUpdate.setPourcPlaceJockHippo(info.getPourcPlaceJockHippo());
    	infoToUpdate.setPourcVictChevalHippo(info.getPourcVictChevalHippo());
    	infoToUpdate.setPourcVictEntHippo(info.getPourcVictEntHippo());
    	infoToUpdate.setPourcVictJockHippo(info.getPourcVictJockHippo());
    	infoToUpdate.setTxVictCouple(info.getTxVictCouple());
    	infoToUpdate.setTxPlaceCouple(info.getTxPlaceCouple());
    	if(info.getTxPlaceCoupleHippo() != null) {
    	infoToUpdate.setTxPlaceCoupleHippo(info.getTxPlaceCoupleHippo());
    	}
    	if(info.getTxVictCoupleHippo() != null) {
    	infoToUpdate.setTxVictCoupleHippo(info.getTxVictCoupleHippo());
    	}
    	
    	infoToUpdate.setPourcPlaceCheval(info.getPourcPlaceCheval());

//    	infoToUpdate.setCoursesentraineur(info.getCoursesentraineur());
//    	infoToUpdate.setCoursesjockey(info.getCoursesjockey());
    	infoToUpdate.setNbCourseCouple(info.getNbCourseCouple());
    	infoToUpdate.setNbVictCouple(info.getNbVictCouple());
    	infoToUpdate.setNbrCourseChevalHippo(info.getNbrCourseChevalHippo());
    	infoToUpdate.setNbrCourseJockHippo(info.getNbrCourseJockHippo());
    	infoToUpdate.setNbrCourseEntHippo(info.getNbrCourseEntHippo());
    	
    	infoToUpdate.setChevalTwoOrThreeHippo(info.getChevalTwoOrThreeHippo());
    	infoToUpdate.setNbVictChevalHippo(info.getNbVictChevalHippo());
    	
    	infoToUpdate.setSurface(info.getSurface());

		   if(infoToUpdate.getRaceSpecialty().equals("P") && infoToUpdate.getSurface().equals("PSF")) {
			   
				infoToUpdate.setNbCourseSurface(info.getNbCourseSurface());
		    	infoToUpdate.setNbCourseSurfaceHippo(info.getNbCourseSurfaceHippo());
		    	infoToUpdate.setPourcVictChevalSurface(info.getPourcVictChevalSurface());
		    	infoToUpdate.setPourcPlaceChevalSurface(info.getPourcPlaceChevalSurface());
		    	infoToUpdate.setPourcVictChevalSurfaceHippo(info.getPourcVictChevalSurfaceHippo());
		    	infoToUpdate.setPourcPlaceChevalSurfaceHippo(info.getPourcPlaceChevalSurfaceHippo());   
		   }

    




    	
    	} 
    	//////////STATS END
    	
    	
//    	infoToUpdate.setTypec(info.getTypec());

//    	infoToUpdate.setCotedirect(info.getCotedirect());
    	infoToUpdate.setCoursescheval(info.getCoursescheval());

    	infoToUpdate.setBlinkersFirstTime(info.isBlinkersFirstTime());
    	infoToUpdate.setNoShoesFirstTime(info.isNoShoesFirstTime());
    	infoToUpdate.setProtectionFirstTime(info.isProtectionFirstTime());
    	
    	
    	infoToUpdate.setRanking(info.getRanking());
    	infoToUpdate.setLiveOdd(info.getLiveOdd());
    	infoToUpdate.setIsRunning(info.getIsRunning());
    	infoToUpdate.setIsTQQ(info.getIsTQQ());
    	infoToUpdate.setNumberOfInitialRunners(info.getNumberOfInitialRunners());
    	infoToUpdate.setIsPick5(info.getIsPick5());
    	
    	infoToUpdate.setCaraList1(info.getCaraList1());
    	infoToUpdate.setCaraList2(info.getCaraList2());
    	infoToUpdate.setIsPremium(info.getIsPremium());
    	
    	infoToUpdate.setHasBetTypes(info.getHasBetTypes());
    	infoToUpdate.setDraw(info.getDraw());
    	infoToUpdate.setNumberOfNonRunners(info.getNumberOfNonRunners());
    	infoToUpdate.setLiveOddPlaceOnline(info.getLiveOddPlaceOnline());
    	infoToUpdate.setLiveOddPlace(info.getLiveOddPlace());
    	infoToUpdate.setIsCanceled(info.getIsCanceled());
    	infoToUpdate.setIsFavori(info.getIsFavori());
    	infoToUpdate.setFormFigs(info.getFormFigs());
    	infoToUpdate.setAge(info.getAge());

    	infoToUpdate.setIsSupplemented(info.getIsSupplemented());
    	infoToUpdate.setPicto(info.getPicto());

//    	infoToUpdate.setNbCourseCoupleHippo(info.getNbCourseCoupleHippo());
//    	infoToUpdate.setTxVictCoupleHippo(info.getTxVictCoupleHippo());
//    	infoToUpdate.setTxPlaceCoupleHippo(info.getTxPlaceCoupleHippo());
    	infoToUpdate.setPronoFavoris(info.getPronoFavoris());
    	infoToUpdate.setPronoChancesRegulieres(info.getPronoChancesRegulieres());
    	
    	infoToUpdate.setValeur(info.getValeur());


    	







        allToUpdate.add(infoToUpdate);






    	}
    	
//    	infoToUpdate.setNoteProno(null);
//    	infoToUpdate.setChrono(info.getChrono());
//    	infoToUpdate.setNumeroString(info.getNumero().toString());
    	 
    	return allToUpdate;

    }
    
    public void updateAllFromAspiJSON(List<TurfInfos> infosToUpdate) {
    	    	
//    	infoToUpdate.setRecence(info.getRecence());
    
    	
			turfInfosRepository.saveAll(infosToUpdate);
		
    
    }
    
    public void updateRankingsFromParisTurfJSON(TurfInfos info, TurfInfos infoToUpdate) {
    	
    	infoToUpdate.setRanking(info.getRanking());
    	infoToUpdate.setLiveOdd(info.getLiveOdd());

    
    	turfInfosRepository.save(infoToUpdate);
    
    }
    public void patchCSVFromAspi(TurfInfos info, TurfInfos infoToUpdate) {
    	
    	infoToUpdate.setRecence(info.getRecence());
    
    	turfInfosRepository.save(infoToUpdate);
    
    }
	
	public LinkedList<List<String>> createRaceInfosList(List<TurfInfos> raceInfos){
		
		LinkedList<List<String>> raceInfosList = new LinkedList<>();
		
//		List<TurfInfos> raceInfos = turfInfosRepository.findAllByNumcourse(numcourse);
				
		//get(0)
	    List<Double> raceSortedByPvch =  raceInfos.stream()
 				.map(TurfInfos :: getPourcVictChevalHippo)
				.sorted()
				.collect(Collectors.toList());
	    //Cast
		 LinkedList<String> raceSortedByPvchString = new LinkedList<>();
	    for(Double d : raceSortedByPvch) {
	    	raceSortedByPvchString.add(d.toString());
	    }
		
		//get(1)
		List<Double> raceSortedByPvjh = raceInfos.stream()
 				.map(TurfInfos :: getPourcVictJockHippo)
				.sorted()
				.collect(Collectors.toList());
	    //Cast
		 LinkedList<String> raceSortedByPvjhString = new LinkedList<>();
		    for(Double d : raceSortedByPvjh) {
		    	raceSortedByPvjhString.add(d.toString());
		    }
		
		//get(2)
		List<Double> raceSortedByPveh = raceInfos.stream()
 				.map(TurfInfos :: getPourcVictEntHippo)
				.sorted()
				.collect(Collectors.toList());
	    //Cast
		 LinkedList<String> raceSortedByPvehString = new LinkedList<>();
		    for(Double d : raceSortedByPveh) {
		    	raceSortedByPvehString.add(d.toString());
		    }


		raceInfosList.add(raceSortedByPvchString);
		raceInfosList.add(raceSortedByPvjhString);
		raceInfosList.add(raceSortedByPvehString);

		
		return raceInfosList;
	}
	
	public TurfInfos setMadeUpParams(TurfInfos info){
		
		if(info.getLibel_hippo() == null) {
			info.setReunionstring(info.getR());
			return info;
		}
			 
		try {
			Integer test = Integer.valueOf(info.getR());

		} catch (Exception e) {
			info.setReunionstring(info.getR());
			return info;
		}

		info.setReunionstring(info.getR() + " - (" + info.getLibel_hippo() + ")");

		return info;
	}

}
