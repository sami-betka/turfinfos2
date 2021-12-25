package turfinfos2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "resultat")
@SequenceGenerator(name = "RESULT_SEQ_GENERATOR", sequenceName = "RESULT_SEQ", initialValue = 1, allocationSize = 1)
public class Resultat {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SEQ_GENERATOR")
    @Column(name = "id")
	private Long id;

	private String jour;
	
	private String hour;

	private String R;

	private Integer C;
	
	private Integer numcourse;

    //////////////////////////////////////////////////////////////////////
	
	private String simpleGagnant;
	private Double simpleGagnantRapport;


	private String simplePlace1;
	private Double simplePlaceRapport1;
	
	private String simplePlace2;
	private Double simplePlaceRapport2;
	
	private String simplePlace3;
	private Double simplePlaceRapport3;

	private String coupleGagnant;
	private Double coupleGagnantRapport;

	private String couplePlace1;
	private Double couplePlaceRapport1;
	
	private String couplePlace2;
	private Double couplePlaceRapport2;
	
	private String couplePlace3;
	private Double couplePlaceRapport3;

	private String coupleOrdre;
	private Double coupleOrdreRapport;
	
	private String deuxSurQuatre;
	private Double deuxSurQuatreRapport;
	
	private String trio;
	private Double trioRapport;
	
	private String trioOrdre;
	private Double trioOrdreRapport;
	
	private String multi;
	private Double multi4Rapport;
	private Double multi5Rapport;
	private Double multi6Rapport;
	private Double multi7Rapport;
	
	private String pick5;
	private Double pick5Rapport;
	
	private String super4;
	private Double super4Rapport;
	
	private String tierce;
	private Double tierceOrdreRapport;
	private Double tierceDesordreRapport;

	private String quarte;
	private Double quarteOrdreRapport;
	private Double quarteDesordreRapport;
	private Double quarteBonusRapport;
	
	private String quinte;
	private Double quinteOrdreRapport;
	private Double quinteDesordreRapport;
	private Double quinteBonus4Rapport;
	private Double quinteBonus3Rapport;
	
	
	public Resultat(Long id, String jour, String hour, String r, Integer c, Integer numcourse, String simpleGagnant,
			Double simpleGagnantRapport, String simplePlace1, Double simplePlaceRapport1, String simplePlace2,
			Double simplePlaceRapport2, String simplePlace3, Double simplePlaceRapport3, String coupleGagnant,
			Double coupleGagnantRapport, String couplePlace1, Double couplePlaceRapport1, String couplePlace2,
			Double couplePlaceRapport2, String couplePlace3, Double couplePlaceRapport3, String coupleOrdre,
			Double coupleOrdreRapport, String deuxSur4, Double deuxSurQuatreRapport, String trio, Double trioRapport,
			String trioOrdre, Double trioOrdreRapport, String multi, Double multi4Rapport, Double multi5Rapport,
			Double multi6Rapport, Double multi7Rapport, String pick5, Double pick5Rapport, String super4,
			Double super4Rapport, String tierce, Double tierceOrdreRapport, Double tierceDesordreRapport, String quarte,
			Double quarteOrdreRapport, Double quarteDesordreRapport, Double quarteBonusRapport, String quinte,
			Double quinteOrdreRapport, Double quinteDesordreRapport, Double quinteBonus4Rapport,
			Double quinteBonus3Rapport) {
		
		super();
		this.id = id;
		this.jour = jour;
		this.hour = hour;
		this.R = r;
		this.C = c;
		this.numcourse = numcourse;
		this.simpleGagnant = simpleGagnant;
		this.simpleGagnantRapport = simpleGagnantRapport;
		this.simplePlace1 = simplePlace1;
		this.simplePlaceRapport1 = simplePlaceRapport1;
		this.simplePlace2 = simplePlace2;
		this.simplePlaceRapport2 = simplePlaceRapport2;
		this.simplePlace3 = simplePlace3;
		this.simplePlaceRapport3 = simplePlaceRapport3;
		this.coupleGagnant = coupleGagnant;
		this.coupleGagnantRapport = coupleGagnantRapport;
		this.couplePlace1 = couplePlace1;
		this.couplePlaceRapport1 = couplePlaceRapport1;
		this.couplePlace2 = couplePlace2;
		this.couplePlaceRapport2 = couplePlaceRapport2;
		this.couplePlace3 = couplePlace3;
		this.couplePlaceRapport3 = couplePlaceRapport3;
		this.coupleOrdre = coupleOrdre;
		this.coupleOrdreRapport = coupleOrdreRapport;
		this.deuxSurQuatre = deuxSur4;
		this.deuxSurQuatreRapport = deuxSurQuatreRapport;
		this.trio = trio;
		this.trioRapport = trioRapport;
		this.trioOrdre = trioOrdre;
		this.trioOrdreRapport = trioOrdreRapport;
		this.multi = multi;
		this.multi4Rapport = multi4Rapport;
		this.multi5Rapport = multi5Rapport;
		this.multi6Rapport = multi6Rapport;
		this.multi7Rapport = multi7Rapport;
		this.pick5 = pick5;
		this.pick5Rapport = pick5Rapport;
		this.super4 = super4;
		this.super4Rapport = super4Rapport;
		this.tierce = tierce;
		this.tierceOrdreRapport = tierceOrdreRapport;
		this.tierceDesordreRapport = tierceDesordreRapport;
		this.quarte = quarte;
		this.quarteOrdreRapport = quarteOrdreRapport;
		this.quarteDesordreRapport = quarteDesordreRapport;
		this.quarteBonusRapport = quarteBonusRapport;
		this.quinte = quinte;
		this.quinteOrdreRapport = quinteOrdreRapport;
		this.quinteDesordreRapport = quinteDesordreRapport;
		this.quinteBonus4Rapport = quinteBonus4Rapport;
		this.quinteBonus3Rapport = quinteBonus3Rapport;
	}


	public Resultat() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getJour() {
		return jour;
	}


	public void setJour(String jour) {
		this.jour = jour;
	}


	public String getHour() {
		return hour;
	}


	public void setHour(String hour) {
		this.hour = hour;
	}


	public String getR() {
		return R;
	}


	public void setR(String r) {
		R = r;
	}


	public Integer getC() {
		return C;
	}


	public void setC(Integer c) {
		C = c;
	}


	public Integer getNumcourse() {
		return numcourse;
	}


	public void setNumcourse(Integer numcourse) {
		this.numcourse = numcourse;
	}


	public String getSimpleGagnant() {
		return simpleGagnant;
	}


	public void setSimpleGagnant(String simpleGagnant) {
		this.simpleGagnant = simpleGagnant;
	}


	public Double getSimpleGagnantRapport() {
		return simpleGagnantRapport;
	}


	public void setSimpleGagnantRapport(Double simpleGagnantRapport) {
		this.simpleGagnantRapport = simpleGagnantRapport;
	}


	public String getSimplePlace1() {
		return simplePlace1;
	}


	public void setSimplePlace1(String simplePlace1) {
		this.simplePlace1 = simplePlace1;
	}


	public Double getSimplePlaceRapport1() {
		return simplePlaceRapport1;
	}


	public void setSimplePlaceRapport1(Double simplePlaceRapport1) {
		this.simplePlaceRapport1 = simplePlaceRapport1;
	}


	public String getSimplePlace2() {
		return simplePlace2;
	}


	public void setSimplePlace2(String simplePlace2) {
		this.simplePlace2 = simplePlace2;
	}


	public Double getSimplePlaceRapport2() {
		return simplePlaceRapport2;
	}


	public void setSimplePlaceRapport2(Double simplePlaceRapport2) {
		this.simplePlaceRapport2 = simplePlaceRapport2;
	}


	public String getSimplePlace3() {
		return simplePlace3;
	}


	public void setSimplePlace3(String simplePlace3) {
		this.simplePlace3 = simplePlace3;
	}


	public Double getSimplePlaceRapport3() {
		return simplePlaceRapport3;
	}


	public void setSimplePlaceRapport3(Double simplePlaceRapport3) {
		this.simplePlaceRapport3 = simplePlaceRapport3;
	}


	public String getCoupleGagnant() {
		return coupleGagnant;
	}


	public void setCoupleGagnant(String coupleGagnant) {
		this.coupleGagnant = coupleGagnant;
	}


	public Double getCoupleGagnantRapport() {
		return coupleGagnantRapport;
	}


	public void setCoupleGagnantRapport(Double coupleGagnantRapport) {
		this.coupleGagnantRapport = coupleGagnantRapport;
	}


	public String getCouplePlace1() {
		return couplePlace1;
	}


	public void setCouplePlace1(String couplePlace1) {
		this.couplePlace1 = couplePlace1;
	}


	public Double getCouplePlaceRapport1() {
		return couplePlaceRapport1;
	}


	public void setCouplePlaceRapport1(Double couplePlaceRapport1) {
		this.couplePlaceRapport1 = couplePlaceRapport1;
	}


	public String getCouplePlace2() {
		return couplePlace2;
	}


	public void setCouplePlace2(String couplePlace2) {
		this.couplePlace2 = couplePlace2;
	}


	public Double getCouplePlaceRapport2() {
		return couplePlaceRapport2;
	}


	public void setCouplePlaceRapport2(Double couplePlaceRapport2) {
		this.couplePlaceRapport2 = couplePlaceRapport2;
	}


	public String getCouplePlace3() {
		return couplePlace3;
	}


	public void setCouplePlace3(String couplePlace3) {
		this.couplePlace3 = couplePlace3;
	}


	public Double getCouplePlaceRapport3() {
		return couplePlaceRapport3;
	}


	public void setCouplePlaceRapport3(Double couplePlaceRapport3) {
		this.couplePlaceRapport3 = couplePlaceRapport3;
	}


	public String getCoupleOrdre() {
		return coupleOrdre;
	}


	public void setCoupleOrdre(String coupleOrdre) {
		this.coupleOrdre = coupleOrdre;
	}


	public Double getCoupleOrdreRapport() {
		return coupleOrdreRapport;
	}


	public void setCoupleOrdreRapport(Double coupleOrdreRapport) {
		this.coupleOrdreRapport = coupleOrdreRapport;
	}


	public String getDeuxSurQuatre() {
		return deuxSurQuatre;
	}


	public void setDeuxSurQuatre(String deuxSur4) {
		this.deuxSurQuatre = deuxSur4;
	}


	public Double getDeuxSurQuatreRapport() {
		return deuxSurQuatreRapport;
	}


	public void setDeuxSurQuatreRapport(Double deuxSurQuatreRapport) {
		this.deuxSurQuatreRapport = deuxSurQuatreRapport;
	}


	public String getTrio() {
		return trio;
	}


	public void setTrio(String trio) {
		this.trio = trio;
	}


	public Double getTrioRapport() {
		return trioRapport;
	}


	public void setTrioRapport(Double trioRapport) {
		this.trioRapport = trioRapport;
	}


	public String getTrioOrdre() {
		return trioOrdre;
	}


	public void setTrioOrdre(String trioOrdre) {
		this.trioOrdre = trioOrdre;
	}


	public Double getTrioOrdreRapport() {
		return trioOrdreRapport;
	}


	public void setTrioOrdreRapport(Double trioOrdreRapport) {
		this.trioOrdreRapport = trioOrdreRapport;
	}


	public String getMulti() {
		return multi;
	}


	public void setMulti(String multi) {
		this.multi = multi;
	}


	public Double getMulti4Rapport() {
		return multi4Rapport;
	}


	public void setMulti4Rapport(Double multi4Rapport) {
		this.multi4Rapport = multi4Rapport;
	}


	public Double getMulti5Rapport() {
		return multi5Rapport;
	}


	public void setMulti5Rapport(Double multi5Rapport) {
		this.multi5Rapport = multi5Rapport;
	}


	public Double getMulti6Rapport() {
		return multi6Rapport;
	}


	public void setMulti6Rapport(Double multi6Rapport) {
		this.multi6Rapport = multi6Rapport;
	}


	public Double getMulti7Rapport() {
		return multi7Rapport;
	}


	public void setMulti7Rapport(Double multi7Rapport) {
		this.multi7Rapport = multi7Rapport;
	}


	public String getPick5() {
		return pick5;
	}


	public void setPick5(String pick5) {
		this.pick5 = pick5;
	}


	public Double getPick5Rapport() {
		return pick5Rapport;
	}


	public void setPick5Rapport(Double pick5Rapport) {
		this.pick5Rapport = pick5Rapport;
	}


	public String getSuper4() {
		return super4;
	}


	public void setSuper4(String super4) {
		this.super4 = super4;
	}


	public Double getSuper4Rapport() {
		return super4Rapport;
	}


	public void setSuper4Rapport(Double super4Rapport) {
		this.super4Rapport = super4Rapport;
	}


	public String getTierce() {
		return tierce;
	}


	public void setTierce(String tierce) {
		this.tierce = tierce;
	}


	public Double getTierceOrdreRapport() {
		return tierceOrdreRapport;
	}


	public void setTierceOrdreRapport(Double tierceOrdreRapport) {
		this.tierceOrdreRapport = tierceOrdreRapport;
	}


	public Double getTierceDesordreRapport() {
		return tierceDesordreRapport;
	}


	public void setTierceDesordreRapport(Double tierceDesordreRapport) {
		this.tierceDesordreRapport = tierceDesordreRapport;
	}


	public String getQuarte() {
		return quarte;
	}


	public void setQuarte(String quarte) {
		this.quarte = quarte;
	}


	public Double getQuarteOrdreRapport() {
		return quarteOrdreRapport;
	}


	public void setQuarteOrdreRapport(Double quarteOrdreRapport) {
		this.quarteOrdreRapport = quarteOrdreRapport;
	}


	public Double getQuarteDesordreRapport() {
		return quarteDesordreRapport;
	}


	public void setQuarteDesordreRapport(Double quarteDesordreRapport) {
		this.quarteDesordreRapport = quarteDesordreRapport;
	}


	public Double getQuarteBonusRapport() {
		return quarteBonusRapport;
	}


	public void setQuarteBonusRapport(Double quarteBonusRapport) {
		this.quarteBonusRapport = quarteBonusRapport;
	}


	public String getQuinte() {
		return quinte;
	}


	public void setQuinte(String quinte) {
		this.quinte = quinte;
	}


	public Double getQuinteOrdreRapport() {
		return quinteOrdreRapport;
	}


	public void setQuinteOrdreRapport(Double quinteOrdreRapport) {
		this.quinteOrdreRapport = quinteOrdreRapport;
	}


	public Double getQuinteDesordreRapport() {
		return quinteDesordreRapport;
	}


	public void setQuinteDesordreRapport(Double quinteDesordreRapport) {
		this.quinteDesordreRapport = quinteDesordreRapport;
	}


	public Double getQuinteBonus4Rapport() {
		return quinteBonus4Rapport;
	}


	public void setQuinteBonus4Rapport(Double quinteBonus4Rapport) {
		this.quinteBonus4Rapport = quinteBonus4Rapport;
	}


	public Double getQuinteBonus3Rapport() {
		return quinteBonus3Rapport;
	}


	public void setQuinteBonus3Rapport(Double quinteBonus3Rapport) {
		this.quinteBonus3Rapport = quinteBonus3Rapport;
	}


	@Override
	public String toString() {
		return "Resultat [jour=" + jour + ", R=" + R + ", C=" + C + ", simpleGagnant=" + simpleGagnant
				+ ", simpleGagnantRapport=" + simpleGagnantRapport + ", simplePlace1=" + simplePlace1
				+ ", simplePlaceRapport1=" + simplePlaceRapport1 + ", simplePlace2=" + simplePlace2
				+ ", simplePlaceRapport2=" + simplePlaceRapport2 + ", simplePlace3=" + simplePlace3
				+ ", simplePlaceRapport3=" + simplePlaceRapport3 + ", coupleGagnant=" + coupleGagnant
				+ ", coupleGagnantRapport=" + coupleGagnantRapport + ", couplePlace1=" + couplePlace1
				+ ", couplePlaceRapport1=" + couplePlaceRapport1 + ", couplePlace2=" + couplePlace2
				+ ", couplePlaceRapport2=" + couplePlaceRapport2 + ", couplePlace3=" + couplePlace3
				+ ", couplePlaceRapport3=" + couplePlaceRapport3 + ", coupleOrdre=" + coupleOrdre
				+ ", coupleOrdreRapport=" + coupleOrdreRapport + ", deuxSurQuatre=" + deuxSurQuatre
				+ ", deuxSurQuatreRapport=" + deuxSurQuatreRapport + ", trio=" + trio + ", trioRapport=" + trioRapport
				+ ", trioOrdre=" + trioOrdre + ", trioOrdreRapport=" + trioOrdreRapport + ", multi=" + multi
				+ ", multi4Rapport=" + multi4Rapport + ", multi5Rapport=" + multi5Rapport + ", multi6Rapport="
				+ multi6Rapport + ", multi7Rapport=" + multi7Rapport + ", pick5=" + pick5 + ", pick5Rapport="
				+ pick5Rapport + ", super4=" + super4 + ", super4Rapport=" + super4Rapport + ", tierce=" + tierce
				+ ", tierceOrdreRapport=" + tierceOrdreRapport + ", tierceDesordreRapport=" + tierceDesordreRapport
				+ ", quarte=" + quarte + ", quarteOrdreRapport=" + quarteOrdreRapport + ", quarteDesordreRapport="
				+ quarteDesordreRapport + ", quarteBonusRapport=" + quarteBonusRapport + ", quinte=" + quinte
				+ ", quinteOrdreRapport=" + quinteOrdreRapport + ", quinteDesordreRapport=" + quinteDesordreRapport
				+ ", quinteBonus4Rapport=" + quinteBonus4Rapport + ", quinteBonus3Rapport=" + quinteBonus3Rapport + "]";
	}



}
