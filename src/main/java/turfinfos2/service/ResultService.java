package turfinfos2.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import turfinfos2.model.ResultType;
import turfinfos2.model.Resultat;

@Service
public class ResultService {

	public Resultat createResult(String jour, String R, Integer race, JsonNode node, String url, List <Resultat> allResults) {
		
		java.util.Optional<Resultat> optResultat = allResults.stream()
				.filter(r->r.getJour().equals(jour) && r.getR().equals(R) && r.getC().equals(race))
				.findFirst();
		Resultat resultat = new Resultat();

		if(optResultat.isPresent()) {
			resultat = optResultat.get();
		} else {
			resultat.setJour(jour);
			resultat.setR(R);
			resultat.setC(race);
		}
		
	
		try {
			node = new ObjectMapper().readTree(new URL(url));
		
//		System.out.println("node size : " + node.size());

				
		for (int i = 0; i < node.size(); i++) {

			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_GAGNANT.name())) {
				resultat.setSimpleGagnant(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setSimpleGagnantRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}

			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_PLACE.name())) {
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

			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_GAGNANT.name())) {
				resultat.setCoupleGagnant(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setCoupleGagnantRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);			
				}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_PLACE.name())) {
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
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_ORDRE.name())) {
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
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.TRIO_ORDRE.name())) {
				resultat.setTrioOrdre(node.get(i).get("rapports").get(0).get("combinaison").textValue());
				resultat.setTrioOrdreRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
				}
			if (node.get(i).get("typePari").textValue().equals(ResultType.TRIO.name())) {
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

}

