package turfinfos2.service;

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
	
    public void update(TurfInfos info, TurfInfos infoToUpdate) {
    	
//    	TurfInfos infoToUpdate = turfInfosRepository.findByNumeroAndNumcourse(info.getNumero(), info.getNumcourse());
    	
    	infoToUpdate.setC(info.getC());
    	infoToUpdate.setCheval(info.getCheval());
    	infoToUpdate.setJour(info.getJour());
    	infoToUpdate.setNumcourse(info.getNumcourse());
    	infoToUpdate.setNumero(info.getNumero());
    	infoToUpdate.setPourcPlaceChevalHippo(info.getPourcPlaceChevalHippo());
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



    	
    	
    	turfInfosRepository.save(infoToUpdate);
    	
    }
    
    public void updateFromParisTurfJSON(TurfInfos info, boolean nulStats) {
    	
    	TurfInfos infoToUpdate = turfInfosRepository.findByNumeroAndNumcourse(info.getNumero(), info.getNumcourse());
    	
    	infoToUpdate.setC(info.getC());
//    	infoToUpdate.setCheval(info.getCheval());
    	infoToUpdate.setJour(info.getJour());
    	infoToUpdate.setNumcourse(info.getNumcourse());
    	infoToUpdate.setNumero(info.getNumero());
    	infoToUpdate.setR(info.getR());
//    	infoToUpdate.setRecence(info.getRecence());
    	infoToUpdate.setEntraineur(info.getEntraineur());
    	infoToUpdate.setCl(info.getCl());
    	infoToUpdate.setDistanceAndSpecialtyChrono(info.getDistanceAndSpecialtyChrono());
    	infoToUpdate.setReunionstring(info.getReunionstring());
    	
    	//STATS
    	if(nulStats == false) {
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
//    	infoToUpdate.setNbCourseCoupleHippo(info.getNbCourseCoupleHippo());
    	
    	
    	} //////////STATS END
    	
    	
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








    	
    	
//    	infoToUpdate.setNoteProno(null);
//    	infoToUpdate.setChrono(info.getChrono());
//    	infoToUpdate.setNumeroString(info.getNumero().toString());
    	 
    	turfInfosRepository.save(infoToUpdate);

    }
    
    public void updateFromAspiJSON(TurfInfos info, TurfInfos infoToUpdate) {
    	    	
    	infoToUpdate.setRecence(info.getRecence());
    
    	turfInfosRepository.save(infoToUpdate);
    
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
