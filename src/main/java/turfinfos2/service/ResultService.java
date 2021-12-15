package turfinfos2.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import turfinfos2.model.Resultat;
import turfinfos2.model.ResultType;

@Service
public class ResultService {

	public Resultat createResult(String jour, String R, Integer race, JsonNode node, String url) {
		
		Resultat resultat = new Resultat();

		try {
			node = new ObjectMapper().readTree(new URL(url));
		
		System.out.println("node size : " + node.size());

		resultat.setJour(jour);
		resultat.setR(R);
		resultat.setC(race);

		
		for (int i = 0; i < node.size(); i++) {

			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_GAGNANT.name())) {
				resultat.getSimpleGagnant().put(node.get(i).get("rapports").get(0).get("libelle").textValue(),
						node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}

			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_PLACE.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					resultat.getSimplePlaces().put(node.get(i).get("rapports").get(j).get("libelle").textValue() + "," + node.get(i).get("rapports").get(j).get("combinaison").textValue(),
							node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
				}
			}

			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_GAGNANT.name())) {
				resultat.getCoupleGagnant().put(node.get(i).get("rapports").get(0).get("libelle").textValue() + "," + node.get(i).get("rapports").get(0).get("combinaison").textValue(),
						node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_PLACE.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					resultat.getCouplePlaces().put(node.get(i).get("rapports").get(j).get("libelle").textValue() + "," + node.get(i).get("rapports").get(j).get("combinaison").textValue(),
							node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_ORDRE.name())) {
				resultat.getCoupleOrdre().put(node.get(i).get("rapports").get(0).get("libelle").textValue() + "," + node.get(i).get("rapports").get(0).get("combinaison").textValue(),
						node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.DEUX_SUR_QUATRE.name())) {
				resultat.getDeuxSurQuatre().put(node.get(i).get("rapports").get(0).get("libelle").textValue() + "," + node.get(i).get("rapports").get(0).get("combinaison").textValue(),
						node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.MULTI.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					resultat.getMultis().put(node.get(i).get("rapports").get(j).get("libelle").textValue() + "," + node.get(i).get("rapports").get(j).get("combinaison").textValue(),
							node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.PICK5.name())) {
				resultat.getPick5().put(node.get(i).get("rapports").get(0).get("libelle").textValue() + "," + node.get(i).get("rapports").get(0).get("combinaison").textValue(),
						node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.QUARTE_PLUS.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					resultat.getQuarte().put(node.get(i).get("rapports").get(j).get("libelle").textValue() + "," + node.get(i).get("rapports").get(j).get("combinaison").textValue(),
							node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.QUINTE_PLUS.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					resultat.getQuinte().put(node.get(i).get("rapports").get(j).get("libelle").textValue() + "," + node.get(i).get("rapports").get(j).get("combinaison").textValue(),
							node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.SUPER_QUATRE.name())) {
				resultat.getSuper4().put(node.get(i).get("rapports").get(0).get("libelle").textValue() + "," + node.get(i).get("rapports").get(0).get("combinaison").textValue(),
						node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.TIERCE.name())) {
				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
					resultat.getTierce().put(node.get(i).get("rapports").get(j).get("libelle").textValue() + "," + node.get(i).get("rapports").get(j).get("combinaison").textValue(),
							node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue() / 100);
				}
			}
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.TRIO_ORDRE.name())) {
				resultat.getTrioOrdre().put(node.get(i).get("rapports").get(0).get("libelle").textValue() + "," + node.get(i).get("rapports").get(0).get("combinaison").textValue(),
						node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
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