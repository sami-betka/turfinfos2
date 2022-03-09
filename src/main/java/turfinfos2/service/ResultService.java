package turfinfos2.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import turfinfos2.model.ResultType;
import turfinfos2.model.Resultat;
import turfinfos2.model.TurfInfos;

@Service
public class ResultService {

	public Resultat createResult(String jour, String R, Integer race, JsonNode node, String url, List <Resultat> allResults, Integer numberOfInitialRunners) {
		
		java.util.Optional<Resultat> optResultat = allResults.stream()
				.filter(r->r.getJour().equals(jour) && r.getR().equals(R) && r.getC().equals(race))
				.findFirst();
		Resultat resultat = new Resultat();

		if(optResultat.isPresent()) {
			resultat = optResultat.get();
			resultat.setNumberOfInitialRunners(numberOfInitialRunners);
		} else {
			resultat.setJour(jour);
			resultat.setR(R);
			resultat.setC(race);
			resultat.setNumberOfInitialRunners(numberOfInitialRunners);
		}
		
	
		try {
			node = new ObjectMapper().readTree(new URL(url));
		
//		System.out.println("node size : " + node.size());

				
		for (int i = 0; i < node.size(); i++) {

			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_GAGNANT.name()) || node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_GAGNANT_INTERNATIONAL.name())) {
				resultat.setSimpleGagnant(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setSimpleGagnantRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}

			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_PLACE.name()) || node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_PLACE_INTERNATIONAL.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					if(j == 0) {
						resultat.setSimplePlace1(node.get(i).get("rapports").get(j).get("combinaison").textValue());
						resultat.setSimplePlaceRapport1(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 1) {
						resultat.setSimplePlace2(node.get(i).get("rapports").get(j).get("combinaison").textValue());
						resultat.setSimplePlaceRapport2(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 2) {
						resultat.setSimplePlace3(node.get(i).get("rapports").get(j).get("combinaison").textValue());
						resultat.setSimplePlaceRapport3(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
				
				}
			}

			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_GAGNANT.name()) || node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_GAGNANT_INTERNATIONAL.name())) {
				resultat.setCoupleGagnant(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setCoupleGagnantRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);			
				}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_PLACE.name()) || node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_PLACE_INTERNATIONAL.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					if(j == 0) {
						resultat.setCouplePlace1(node.get(i).get("rapports").get(j).get("combinaison").textValue());
						resultat.setCouplePlaceRapport1(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 1) {
						resultat.setCouplePlace2(node.get(i).get("rapports").get(j).get("combinaison").textValue());
						resultat.setCouplePlaceRapport2(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 2) {
						resultat.setCouplePlace3(node.get(i).get("rapports").get(j).get("combinaison").textValue());
						resultat.setCouplePlaceRapport3(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
				
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_ORDRE.name()) || node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_ORDRE_INTERNATIONAL.name())) {
				resultat.setCoupleOrdre(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setCoupleOrdreRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);			
				}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.DEUX_SUR_QUATRE.name())) {
//				resultat.setDeuxSurQuatre(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setDeuxSurQuatreRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);			
				}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.MULTI.name()) || node.get(i).get("typePari").textValue().equals(ResultType.MINI_MULTI.name())) {
				resultat.setMulti(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setDeuxSurQuatre(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
			
					if(j == 0) {
						resultat.setMulti4Rapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 1) {
						resultat.setMulti5Rapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 2) {
						resultat.setMulti6Rapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 3) {
						resultat.setMulti7Rapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
				
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.PICK5.name())) {
				resultat.setPick5(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setPick5Rapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);			
				}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.QUARTE_PLUS.name())) {
				resultat.setQuarte(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					if(j == 0) {
						resultat.setQuarteOrdreRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 1) {
						resultat.setQuarteDesordreRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 2) {
						resultat.setQuarteBonusRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.QUINTE_PLUS.name())) {
				resultat.setQuinte(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					if(j == 0) {
						resultat.setQuinteOrdreRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 1) {
						resultat.setQuinteDesordreRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 2) {
						resultat.setQuinteBonus4Rapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 3) {
						resultat.setQuinteBonus3Rapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.SUPER_QUATRE.name())) {
				resultat.setSuper4(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setSuper4Rapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);				
				}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.TIERCE.name())) {
				resultat.setTierce(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					if(j == 0) {
						resultat.setTierceOrdreRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
					if(j == 1) {
						resultat.setTierceDesordreRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
					}
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.TRIO_ORDRE.name()) || node.get(i).get("typePari").textValue().equals(ResultType.TRIO_ORDRE_INTERNATIONAL.name())) {
				resultat.setTrioOrdre(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setTrioOrdreRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
				}
			if (node.get(i).get("typePari").textValue().equals(ResultType.TRIO.name()) || node.get(i).get("typePari").textValue().equals(ResultType.TRIO_INTERNATIONAL.name())) {
				resultat.setTrio(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setTrioRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
				}

		}
		
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		FOR LOOP END

		return resultat;
	}
	
	
//	public Map<String, String> createBonCoupsDuJour (List<List<TurfInfos>> listsByNoteProno, List<Resultat> reunionResultats){
	public Map<String, List<String>> createBonCoupsDuJour (List<List<TurfInfos>> listsByNoteProno, List<Resultat> reunionResultats){

				
//		Map<String, String> map = new HashMap<>();
		Map<String, List<String>> map = new HashMap<>();
		List<String> simpleGagnantList = new ArrayList<>();
		List<String> coupleGagnantList = new ArrayList<>();
		List<String> coupleOrdreList = new ArrayList<>();
		List<String> trioList = new ArrayList<>();
		List<String> trioOrdreList = new ArrayList<>();
		List<String> multiList = new ArrayList<>();
		List<String> pick5List = new ArrayList<>();
		List<String> quinteList = new ArrayList<>();
		List<String> quarteList = new ArrayList<>();
		List<String> tierceList = new ArrayList<>();






		
		
		Resultat resultat = new Resultat();
		
		for(List<TurfInfos> raceByNoteProno : listsByNoteProno) {
			if(raceByNoteProno.size() > 0) {
				
				Optional<Resultat> optResultat = reunionResultats.stream().filter(r -> r.getC().equals(raceByNoteProno.get(0).getC())).findFirst();
				if(optResultat.isPresent()) {
					resultat = optResultat.get();
			        System.out.println("present");

					
			
			        System.out.println(resultat.toString());
			        if(!optResultat.isPresent()) {
				        System.out.println("notp");

			        }
					//Resultat en liste
					
					
					String[] array = null;
					List<String> arrivee = new ArrayList<>();
					
					if(resultat.getNumberOfInitialRunners() >= 10) {
						array = resultat.getDeuxSurQuatre().split("-");
					    arrivee = new ArrayList<String>(Arrays.asList(array));
					}
					if(resultat.getNumberOfInitialRunners() > 4 && resultat.getNumberOfInitialRunners() <= 9) {
						array = resultat.getSuper4().split("-");
					    arrivee = new ArrayList<String>(Arrays.asList(array));
					}
					if(resultat.getNumberOfInitialRunners() <= 4) {
						array = resultat.getTrioOrdre().split("-");
					    arrivee = new ArrayList<String>(Arrays.asList(array));
					}
					if(raceByNoteProno.get(0).getIsTQQ().equals(true)) {
						array = resultat.getQuinte().split("-");
					    arrivee = new ArrayList<String>(Arrays.asList(array));
					}
					if(raceByNoteProno.get(0).getIsPick5().equals(true)) {
						array = resultat.getPick5().split("-");
					    arrivee = new ArrayList<String>(Arrays.asList(array));
					}
					
					//Verifier simple gagnant
					if(raceByNoteProno.get(0).getNumero().toString().equals(arrivee.get(0))) {
						simpleGagnantList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - SG - " + resultat.getSimpleGagnant() + " (" + resultat.getSimpleGagnantRapport() + " pour 1€)" );
					}
					
					//Verifier COUPLES GAGNANTS 
					       
						Integer numberOfCheckedSelections = 0;
						Integer numberOfSelectionInResult = 0;

					  if(resultat.getNumberOfInitialRunners() > 7 && arrivee.size() >= 2) {
						  for(int i = 0; i< raceByNoteProno.size(); i++) {
							  
							  TurfInfos inf = raceByNoteProno.get(i);

							  numberOfCheckedSelections += 1;
							  
							  if(arrivee.subList(0, 2).contains(inf.getNumero().toString())) {
									numberOfSelectionInResult += 1;
								}
							  if(numberOfSelectionInResult == 2) {
									coupleGagnantList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - CG en " +numberOfCheckedSelections + " chevaux - " + resultat.getCoupleGagnant() + " (" + resultat.getCoupleGagnantRapport() + " pour 1€)" );
						
									break;
							  }
						  }
						 
					  }
						numberOfCheckedSelections = 0;
						numberOfSelectionInResult = 0;
						
						
						  //Verifier COUPLE ORDRE
						  if(resultat.getCoupleOrdre() != null && arrivee.size() >= 2) {
							  List<String> listToVerifyOrder = new ArrayList<>();
							  
							  for(int i = 0; i< raceByNoteProno.size(); i++) {
								  
								  TurfInfos inf = raceByNoteProno.get(i);

								  numberOfCheckedSelections += 1;
								  
								  if(arrivee.subList(0, 2).contains(inf.getNumero().toString())) {
										numberOfSelectionInResult += 1;
										listToVerifyOrder.add(inf.getNumero().toString());
									}
								
								  if(numberOfSelectionInResult == 2 && listToVerifyOrder.equals(arrivee)) {
									  coupleOrdreList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Couplé ordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getCoupleOrdre() + " (" + resultat.getCoupleOrdreRapport() + " pour 1€)" );
										break;
								  }
							  }
						  }
							numberOfCheckedSelections = 0;
							numberOfSelectionInResult = 0;
			   
			        //////////////////////
			        
			        //Verifier Trio 
					  if(resultat.getTrio() != null && arrivee.size() >= 3) {
						  for(int i = 0; i< raceByNoteProno.size(); i++) {
							  
							  TurfInfos inf = raceByNoteProno.get(i);

							  numberOfCheckedSelections += 1;
							  
							  if(arrivee.subList(0, 3).contains(inf.getNumero().toString())) {
									numberOfSelectionInResult += 1;
								}
							  if(numberOfSelectionInResult == 3) {
									trioList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Trio en " +numberOfCheckedSelections + " chevaux - " + resultat.getTrio() + " (" + resultat.getTrioRapport() + " pour 1€)" );
								
									break;
							  }
						  }
					  }
						numberOfCheckedSelections = 0;
						numberOfSelectionInResult = 0;
						
						
						  //Verifier TRIO ORDRE
						  if(resultat.getTrioOrdre() != null && arrivee.size() >= 3) {
							  List<String> listToVerifyOrder = new ArrayList<>();
							  
							  for(int i = 0; i< raceByNoteProno.size(); i++) {
								  
								  TurfInfos inf = raceByNoteProno.get(i);

								  numberOfCheckedSelections += 1;
								  
								  if(arrivee.subList(0, 3).contains(inf.getNumero().toString())) {
										numberOfSelectionInResult += 1;
										listToVerifyOrder.add(inf.getNumero().toString());
									}
								
								  if(numberOfSelectionInResult == 3 && listToVerifyOrder.equals(arrivee)) {
									  trioOrdreList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Trio ordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getTrioOrdre() + " (" + resultat.getTrioOrdreRapport() + " pour 1€)" );
										break;
								  }
							  }
						  }
							numberOfCheckedSelections = 0;
							numberOfSelectionInResult = 0;
					  
				      //Verifier Multis
								  if(resultat.getNumberOfInitialRunners() > 9 && arrivee.size() >= 4) {
									  for(int i = 0; i< raceByNoteProno.size(); i++) {
										  
										  TurfInfos inf = raceByNoteProno.get(i);

										  numberOfCheckedSelections += 1;
										  
										  if(arrivee.subList(0, 4).contains(inf.getNumero().toString())) {
												numberOfSelectionInResult += 1;
											}
										  if(numberOfSelectionInResult == 4) {
											  String newLine = System.getProperty("line.separator");
											  String if14 = "";
											  String miniMulti = "";

											  if(resultat.getMulti7Rapport() != null){
												  if14 = " (En 7 : " + String.format("%.2f",  resultat.getMulti7Rapport()*3) + " pour 3€)";
											  } else {
												  miniMulti = "Mini ";
											  }

												multiList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - " + miniMulti + " Multi en " +numberOfCheckedSelections + " chevaux - " + resultat.getMulti() 
												        + " (En 4 : " + String.format("%.2f",  resultat.getMulti4Rapport()*3) + " pour 3€)"
														+ newLine + " (En 5 : " + String.format("%.2f",  resultat.getMulti5Rapport()*3) + " pour 3€)"
														+ "\n" + " (En 6 : " + String.format("%.2f",  resultat.getMulti6Rapport()*3) + " pour 3€)"
														+ "\n" + if14

														);
								
												break;
										  }
									  }
								  }
									numberOfCheckedSelections = 0;
									numberOfSelectionInResult = 0;
			        
						 //Verifier PICK5 
									  if(resultat.getPick5() != null && arrivee.size() >= 5) {
										  for(int i = 0; i< raceByNoteProno.size(); i++) {
											  
											  TurfInfos inf = raceByNoteProno.get(i);

											  numberOfCheckedSelections += 1;
											  
											  if(arrivee.subList(0, 5).contains(inf.getNumero().toString())) {
													numberOfSelectionInResult += 1;
												}
											  if(numberOfSelectionInResult == 5) {
													pick5List.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Pick5 en " +numberOfCheckedSelections + " chevaux - " + resultat.getPick5() + " (" + resultat.getPick5Rapport() + " pour 1€)" );
												
													break;
											  }
										  }
									  }
										numberOfCheckedSelections = 0;
										numberOfSelectionInResult = 0;
					
	                       //Verifier QUINTE
										  if(resultat.getQuinte() != null && arrivee.size() >= 5) {
											  List<String> listToVerifyOrder = new ArrayList<>();
											  
											  for(int i = 0; i< raceByNoteProno.size(); i++) {
												  
												  TurfInfos inf = raceByNoteProno.get(i);

												  numberOfCheckedSelections += 1;
												  
												  if(arrivee.subList(0, 5).contains(inf.getNumero().toString())) {
														numberOfSelectionInResult += 1;
														listToVerifyOrder.add(inf.getNumero().toString());
													}
												  
												  if(numberOfSelectionInResult == 5 && !listToVerifyOrder.equals(arrivee)) {
														quinteList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Quinté désordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getQuinte() + " (" + resultat.getQuinteDesordreRapport()*2 + " pour 2€)" );
														break;
												  }
												  if(numberOfSelectionInResult == 5 && listToVerifyOrder.equals(arrivee)) {
														quinteList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Quinté ordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getQuinte() + " (" + resultat.getQuinteOrdreRapport()*2 + " pour 2€)" );
														break;
												  }
											  }
										  }
											numberOfCheckedSelections = 0;
											numberOfSelectionInResult = 0;	
					
											
										      //Verifier QUARTE
											  if(resultat.getQuinte() != null && arrivee.size() >= 4) {
												  List<String> listToVerifyOrder = new ArrayList<>();
												  
												  for(int i = 0; i< raceByNoteProno.size(); i++) {
													  
													  TurfInfos inf = raceByNoteProno.get(i);

													  numberOfCheckedSelections += 1;
													  
													  if(arrivee.subList(0, 4).contains(inf.getNumero().toString())) {
															numberOfSelectionInResult += 1;
															listToVerifyOrder.add(inf.getNumero().toString());
														}
													  
													  if(numberOfSelectionInResult == 4 && !listToVerifyOrder.equals(arrivee)) {
															quarteList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Quarté désordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getQuarte() + " (" + String.format("%.2f", resultat.getQuarteDesordreRapport()*1.3)+ " pour 1,30€)" );
															break;
													  }
													  if(numberOfSelectionInResult == 4 && listToVerifyOrder.equals(arrivee)) {
															quarteList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Quarté ordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getQuarte() + " (" + String.format("%.2f", resultat.getQuarteOrdreRapport()*1.3) + " pour 1,30€)" );
															break;
													  }
												  }
											  }
												numberOfCheckedSelections = 0;
												numberOfSelectionInResult = 0;			
											
											
											      //Verifier TIERCE
												  if(resultat.getTierce() != null && arrivee.size() >= 3) {
													  List<String> listToVerifyOrder = new ArrayList<>();
													  
													  for(int i = 0; i< raceByNoteProno.size(); i++) {
														  
														  TurfInfos inf = raceByNoteProno.get(i);

														  numberOfCheckedSelections += 1;
														  
														  if(arrivee.subList(0, 3).contains(inf.getNumero().toString())) {
																numberOfSelectionInResult += 1;
																listToVerifyOrder.add(inf.getNumero().toString());
															}
														  
														  if(numberOfSelectionInResult == 3 && !listToVerifyOrder.equals(arrivee)) {
																tierceList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Tiercé désordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getTierce() + " (" + resultat.getTierceDesordreRapport() + " pour 1€)" );
																break;
														  }
														  if(numberOfSelectionInResult == 3 && listToVerifyOrder.equals(arrivee)) {
																tierceList.add("R" + raceByNoteProno.get(0).getR() + "C" + raceByNoteProno.get(0).getC() + " - Quarté ordre en " +numberOfCheckedSelections + " chevaux - " + resultat.getTierce() + " (" + resultat.getTierceOrdreRapport() + " pour 1€)" );
																break;
														  }
													  }
												  }
													numberOfCheckedSelections = 0;
													numberOfSelectionInResult = 0;	

				}
			
				
			}
				

		}
		

        map.put("simplegagnant", simpleGagnantList);
        map.put("couplegagnant", coupleGagnantList);
        map.put("coupleordre", coupleOrdreList);
        map.put("trio", trioList);
        map.put("trioordre", trioOrdreList);
        map.put("multi", multiList);
        map.put("pick5", pick5List);
        map.put("quinte", quinteList);
        map.put("quarte", quarteList);
        map.put("tierce", tierceList);
        //
        //




        

		
		return map;
	} 
	
	

}

