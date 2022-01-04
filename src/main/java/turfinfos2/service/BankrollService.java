package turfinfos2.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import turfinfos2.model.ArchiveDeuxSurQuatre;
import turfinfos2.model.TurfInfos;

@Service
public class BankrollService {

	public List<TurfInfos> simulatedBankroll(List<TurfInfos> list, Model model, Double bankAmount, Integer divider){
		
//		List<ArchiveInfo> finalList = new ArrayList<>();
		Double initialBankrollAmount = bankAmount;
		Double minimumBankrollAmount = bankAmount;
		Double precedentBankrollAmount = bankAmount;
		Double newBankrollAmount = bankAmount;
		Double maximalBankrollAmount = bankAmount;

		
		Double totalPlayed = 0d;
		Double totalWon = 0d;

		Double ante = initialBankrollAmount/divider;
		
		for(int i =0; i<list.size(); i++) {
			
			list.get(i).setAnte(ante);
			
			newBankrollAmount -= ante; 
			newBankrollAmount += ante * list.get(i).getLiveOddPlace(); 
//			if(list.get(i).getRanking().equals(1)) {
//				newBankrollAmount += ante * list.get(i).getLiveOdd(); 
//			}


			if(newBankrollAmount > maximalBankrollAmount) {
				maximalBankrollAmount = newBankrollAmount;
				ante = maximalBankrollAmount/divider;
			}
			////////CALCULATE KELLY ANTE
			
//			Double pourcentage = 0d;
//			Double cote = list.get(i).getMinRapportProbable();
//			Double proba = (10 - list.get(i).getLiveOdd()) / 10;
//			pourcentage = (cote * proba - 1) / (cote - 1) * 100;
//			System.out.println(pourcentage + " %");
//			if(pourcentage < 20 ) {
//				System.out.println(pourcentage + " %");
//				ante = (pourcentage * maximalBankrollAmount) / 100;
//			}
			
			/////////////////////////////
			if(newBankrollAmount < minimumBankrollAmount) {
				minimumBankrollAmount = newBankrollAmount;
			}
			
//			LocalDate date = LocalDate.parse(list.get(i).getJour());
//			date.getm

		}
		
		model.addAttribute("newBankrollAmount", newBankrollAmount);
		model.addAttribute("minimumBankrollAmount", minimumBankrollAmount);

		model.addAttribute("initialBankrollAmount", initialBankrollAmount);
		model.addAttribute("bankrollBenef", newBankrollAmount - initialBankrollAmount);
		
		model.addAttribute("cotecapital", ((newBankrollAmount * 100) /  initialBankrollAmount)/100);

		return list;
	}
	
	public List<ArchiveDeuxSurQuatre> simulatedBankrollDeuxSurQuatre(List<ArchiveDeuxSurQuatre> list, Model model, Double bankAmount, Integer divider){
		
//		List<ArchiveInfo> finalList = new ArrayList<>();
		Double initialBankrollAmount = bankAmount;
		Double minimumBankrollAmount = bankAmount;
		Double precedentBankrollAmount = bankAmount;
		Double newBankrollAmount = bankAmount;
		Double maximalBankrollAmount = bankAmount;

		
		Double totalPlayed = 0d;
		Double totalWon = 0d;

		Double ante = initialBankrollAmount/divider;
		
		for(int i =0; i<list.size(); i++) {
			list.get(i).setAnte(ante);
			newBankrollAmount -= ante; 
			newBankrollAmount += ante * list.get(i).getRapport(); 
//			if(list.get(i).getRanking().equals(1)) {
//				newBankrollAmount += ante * list.get(i).getLiveOdd(); 
//			}


			if(newBankrollAmount > maximalBankrollAmount) {
				maximalBankrollAmount = newBankrollAmount;
				ante = maximalBankrollAmount/divider;
			}
			if(newBankrollAmount < minimumBankrollAmount) {
				minimumBankrollAmount = newBankrollAmount;
			}
		}
		
		model.addAttribute("newBankrollAmount", newBankrollAmount);
		model.addAttribute("minimumBankrollAmount", minimumBankrollAmount);

		model.addAttribute("initialBankrollAmount", initialBankrollAmount);
		model.addAttribute("bankrollBenef", newBankrollAmount - initialBankrollAmount);
		
		model.addAttribute("cotecapital", ((newBankrollAmount * 100) /  initialBankrollAmount)/100);

		return list;
	}
	
	public List<TurfInfos> simulatedBankrollStopCote5(List<TurfInfos> list, Model model, Double bankAmount, Integer divider){
		
//		List<ArchiveInfo> finalList = new ArrayList<>();
		Double initialBankrollAmount = bankAmount;
		Double minimumBankrollAmount = bankAmount;
		Double precedentBankrollAmount = bankAmount;
		Double newBankrollAmount = bankAmount;
		Double maximalBankrollAmount = bankAmount;

		
		Double totalPlayed = 0d;
		Double totalWon = 0d;

		Double ante = initialBankrollAmount/divider;
		Double cote = 1d;
		Double coteVisee = 5d;

		
		for(int i =0; i<list.size(); i++) {
			list.get(i).setAnte(ante);
			newBankrollAmount -= ante; 
//			newBankrollAmount += ante * list.get(i).getLiveOddPlace(); 
			if(list.get(i).getLiveOddPlace() != null && list.get(i).getLiveOddPlace() != 0) {
				
				cote = cote * list.get(i).getLiveOddPlace();
				ante = ante * cote;
			
				for(int j = i+1; j<list.size(); j++) {
					if(list.get(j).getLiveOddPlace() != null && list.get(j).getLiveOddPlace() != 0) {
						
						cote = cote * list.get(j).getLiveOddPlace();
						ante = ante * cote;
						
					}
				}
				
				cote = cote * list.get(i).getLiveOddPlace();
				ante = ante * cote;
				
				
				newBankrollAmount += ante * cote; 
			}


			if(newBankrollAmount > maximalBankrollAmount) {
				maximalBankrollAmount = newBankrollAmount;
				ante = maximalBankrollAmount/divider;
			}
			if(newBankrollAmount < minimumBankrollAmount) {
				minimumBankrollAmount = newBankrollAmount;
			}
		}
		
		model.addAttribute("newBankrollAmount", newBankrollAmount);
		model.addAttribute("minimumBankrollAmount", minimumBankrollAmount);

		model.addAttribute("initialBankrollAmount", initialBankrollAmount);
		model.addAttribute("bankrollBenef", newBankrollAmount - initialBankrollAmount);
		
		model.addAttribute("cotecapital", ((newBankrollAmount * 100) /  initialBankrollAmount)/100);

		return list;
	}
}
