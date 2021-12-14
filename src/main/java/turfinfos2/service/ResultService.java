package turfinfos2.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import turfinfos2.model.DeuxSurQuatre;
import turfinfos2.model.ResultType;
import turfinfos2.model.Resultat;
import turfinfos2.repository.CoupleGagnantRepository;
import turfinfos2.repository.CoupleOrdreRepository;
import turfinfos2.repository.CouplePlaceRepository;
import turfinfos2.repository.DeuxSurQuatreRepository;
import turfinfos2.repository.SimpleGagnantRepository;
import turfinfos2.repository.SimplePlaceRepository;

@Service
public class ResultService {
	
	@Autowired
	SimpleGagnantRepository simpleGagnantRepository;
	@Autowired
	SimplePlaceRepository simplePlaceRepository;
	@Autowired
	CoupleGagnantRepository coupleGagnantRepository;
	@Autowired
	CouplePlaceRepository couplePlaceRepository;
	@Autowired
	CoupleOrdreRepository coupleOrdreRepository;
	@Autowired
	DeuxSurQuatreRepository deuxSurQuatreRepository;
	

	public Resultat createResult(String jour, String R, Integer race, JsonNode node, String url) {
		
		Resultat resultat = new Resultat();

		try {
			node = new ObjectMapper().readTree(new URL(url));
		
		System.out.println("node size : " + node.size());

		resultat.setJour(jour);
		resultat.setR(R);
		resultat.setC(race);
//		resultat.set

		
		for (int i = 0; i < node.size(); i++) {
			
			
			if (node.get(i).get("typePari").textValue().equals(ResultType.DEUX_SUR_QUATRE.name())) {
				DeuxSurQuatre ds4 = new DeuxSurQuatre();
				ds4.setC(race);
				ds4.setJour(jour);
				ds4.setR(R);
				List<JsonNode> nodes = node.findValues("typePari");
				for(JsonNode n : nodes)
				if(n.textValue() == ResultType.MULTI.name() || n.textValue() == ResultType.MINI_MULTI.name()) {
					ds4.setCombinaison(n.textValue());
				}
				ds4.setRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
				
		        resultat.setDeuxSurQuatre(deuxSurQuatreRepository.save(ds4));
			}
			
//
//
//			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_GAGNANT.name())) {
//				SimpleGagnant sg = new SimpleGagnant();
//				sg.setC(race);
//				sg.setJour(jour);
//				sg.setR(R);
//				sg.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				sg.setRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
//		        resultat.setSimpleGagnant(sg);
//			}
//
//			if (node.get(i).get("typePari").textValue().equals(ResultType.SIMPLE_PLACE.name())) {
//				SimplePlace sp = new SimplePlace();
//				sp.setC(race);
//				sp.setJour(jour);
//				sp.setR(R);
//				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
//					if(j == 0) {
//						sp.setFirst(node.get(i).get("rapports").get(j).get("combinaison").textValue());
//						sp.setFirstRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//					if(j == 1) {
//						sp.setSecond(node.get(i).get("rapports").get(j).get("combinaison").textValue());
//						sp.setSecondRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//					if(j == 2) {
//						sp.setThird(node.get(i).get("rapports").get(j).get("combinaison").textValue());
//						sp.setThirdRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//				}
//				resultat.setSimplePlace(sp);
//			}
//
//			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_GAGNANT.name())) {
//				CoupleGagnant cg = new CoupleGagnant();
//				cg.setC(race);
//				cg.setJour(jour);
//				cg.setR(R);
//				cg.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				cg.setRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
//		        resultat.setCoupleGagnant(cg);			
//		        }
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_PLACE.name())) {
//				CouplePlace cp = new CouplePlace();
//				cp.setC(race);
//				cp.setJour(jour);
//				cp.setR(R);
//				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
//					if(j == 0) {
//						cp.setFirst(node.get(i).get("rapports").get(j).get("combinaison").textValue());
//						cp.setFirstRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//					if(j == 1) {
//						cp.setSecond(node.get(i).get("rapports").get(j).get("combinaison").textValue());
//						cp.setSecondRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//					if(j == 2) {
//						cp.setThird(node.get(i).get("rapports").get(j).get("combinaison").textValue());
//						cp.setThirdRapport(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//				}
//				resultat.setCouplePlace(cp);
//
//			}
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.COUPLE_ORDRE.name())) {
//				CoupleOrdre co = new CoupleOrdre();
//				co.setC(race);
//				co.setJour(jour);
//				co.setR(R);
//				co.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				co.setRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
//		        resultat.setCoupleOrdre(co);
//			}
//		
//			if (node.get(i).get("typePari").textValue().equals(ResultType.MULTI.name()) || node.get(i).get("typePari").textValue().equals(ResultType.MINI_MULTI.name())) {
//				Multi mul = new Multi();
//			
//
//
//				mul.setC(race);
//				mul.setJour(jour);
//				mul.setR(R);
//				mul.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				for (int j = 0; j < node.get(i).get("rapports").size(); j++) {
//					if(j == 0) {
//						mul.setRapport4(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//					if(j == 1) {
//						mul.setRapport5(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//					if(j == 2) {
//						mul.setRapport6(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//					if(j == 3) {
//						mul.setRapport7(node.get(i).get("rapports").get(j).get("dividendePourUnEuro").doubleValue());
//					}
//				}
//				resultat.setMultis(mul);
//
//			}
//				
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.PICK5.name())) {
//				Pick5 p5 = new Pick5();
//				p5.setC(race);
//				p5.setJour(jour);
//				p5.setR(R);
//				p5.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				p5.setRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
//		        resultat.setPick5(p5);
//			}
//			
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.TIERCE.name())) {
//				Tierce ti = new Tierce();
//				ti.setC(race);
//				ti.setJour(jour);
//				ti.setR(R);
//				ti.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				ti.setRapportOrdre(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue());
//				ti.setRapportDesordre(node.get(i).get("rapports").get(1).get("dividendePourUnEuro").doubleValue());
//				resultat.setTierce(ti);
//			}
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.QUARTE_PLUS.name())) {
//				Quarte qu = new Quarte();
//				qu.setC(race);
//				qu.setJour(jour);
//				qu.setR(R);
//				qu.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				qu.setRapportOrdre(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue());
//				qu.setRapportDesordre(node.get(i).get("rapports").get(1).get("dividendePourUnEuro").doubleValue());
//				qu.setBonus(node.get(i).get("rapports").get(2).get("dividendePourUnEuro").doubleValue());
//				resultat.setQuarte(qu);
//			}
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.QUINTE_PLUS.name())) {
//				Quinte qu = new Quinte();
//				qu.setC(race);
//				qu.setJour(jour);
//				qu.setR(R);
//				qu.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				qu.setRapportOrdre(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue());
//				qu.setRapportDesordre(node.get(i).get("rapports").get(1).get("dividendePourUnEuro").doubleValue());
//				qu.setBonus4(node.get(i).get("rapports").get(2).get("dividendePourUnEuro").doubleValue());
//				qu.setBonus3(node.get(i).get("rapports").get(3).get("dividendePourUnEuro").doubleValue());
//				resultat.setQuinte(qu);
//			}
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.SUPER_QUATRE.name())) {
//				Super4 s4 = new Super4();
//				s4.setC(race);
//				s4.setJour(jour);
//				s4.setR(R);
//				s4.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				s4.setRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
//		        resultat.setSuper4(s4);
//			}
//
//			
//			if (node.get(i).get("typePari").textValue().equals(ResultType.TRIO_ORDRE.name())) {
//				TrioOrdre to = new TrioOrdre();
//				to.setC(race);
//				to.setJour(jour);
//				to.setR(R);
//				to.setCombinaison(node.get(i).get("rapports").get(0).get("combinaison").textValue());
//				to.setRapport(node.get(i).get("rapports").get(0).get("dividendePourUnEuro").doubleValue() / 100);
//		        resultat.setTrioOrdre(to);
//			}

			
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