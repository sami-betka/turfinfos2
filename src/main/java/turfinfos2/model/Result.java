package turfinfos2.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "RESULT_SEQ_GENERATOR", sequenceName = "RESULT_SEQ", initialValue = 1, allocationSize = 1)
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SEQ_GENERATOR")
	private Long id;

	private String jour;
	
	private String R;

	private Integer C;

	private Integer numcourse;

	private Double coupleGagnant;

	private Double coupleOrdre;

	private Double trio;

	private Double trioOrdre;

//	private List<Double> multis;

	private Double pick5;

	private Double super4;

	private Double tierceDesordre;

	private Double tierceOrdre;

	private Double quarteDesordre;

	private Double quarteOrdre;

	private Double quinteDesordre;

	private Double quinteOrdre;

	

	public Result(Long id, String jour, String R, Integer C, Integer numcourse, Double coupleGagnant, Double coupleOrdre, Double trio,
			Double trioOrdre, List<Double> multis, Double pick5, Double super4, Double tierceDesordre,
			Double tierceOrdre, Double quarteDesordre, Double quarteOrdre, Double quinteDesordre, Double quinteOrdre) {
		super();
		this.id = id;
		this.jour = jour;
		this.R = R;
		this.C = C;
		this.numcourse = numcourse;
		this.coupleGagnant = coupleGagnant;
		this.coupleOrdre = coupleOrdre;
		this.trio = trio;
		this.trioOrdre = trioOrdre;
//		this.multis = multis;
		this.pick5 = pick5;
		this.super4 = super4;
		this.tierceDesordre = tierceDesordre;
		this.tierceOrdre = tierceOrdre;
		this.quarteDesordre = quarteDesordre;
		this.quarteOrdre = quarteOrdre;
		this.quinteDesordre = quinteDesordre;
		this.quinteOrdre = quinteOrdre;
	}



	public Result() {
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



	public Integer getNumcourse() {
		return numcourse;
	}



	public void setNumcourse(Integer numcourse) {
		this.numcourse = numcourse;
	}



	public Double getCoupleGagnant() {
		return coupleGagnant;
	}



	public void setCoupleGagnant(Double coupleGagnant) {
		this.coupleGagnant = coupleGagnant;
	}



	public Double getCoupleOrdre() {
		return coupleOrdre;
	}



	public void setCoupleOrdre(Double coupleOrdre) {
		this.coupleOrdre = coupleOrdre;
	}



	public Double getTrio() {
		return trio;
	}



	public void setTrio(Double trio) {
		this.trio = trio;
	}



	public Double getTrioOrdre() {
		return trioOrdre;
	}



	public void setTrioOrdre(Double trioOrdre) {
		this.trioOrdre = trioOrdre;
	}



//	public List<Double> getMultis() {
//		return multis;
//	}
//
//
//
//	public void setMultis(List<Double> multis) {
//		this.multis = multis;
//	}



	public Double getPick5() {
		return pick5;
	}



	public void setPick5(Double pick5) {
		this.pick5 = pick5;
	}



	public Double getSuper4() {
		return super4;
	}



	public void setSuper4(Double super4) {
		this.super4 = super4;
	}



	public Double getTierceDesordre() {
		return tierceDesordre;
	}



	public void setTierceDesordre(Double tierceDesordre) {
		this.tierceDesordre = tierceDesordre;
	}



	public Double getTierceOrdre() {
		return tierceOrdre;
	}



	public void setTierceOrdre(Double tierceOrdre) {
		this.tierceOrdre = tierceOrdre;
	}



	public Double getQuarteDesordre() {
		return quarteDesordre;
	}



	public void setQuarteDesordre(Double quarteDesordre) {
		this.quarteDesordre = quarteDesordre;
	}



	public Double getQuarteOrdre() {
		return quarteOrdre;
	}



	public void setQuarteOrdre(Double quarteOrdre) {
		this.quarteOrdre = quarteOrdre;
	}



	public Double getQuinteDesordre() {
		return quinteDesordre;
	}



	public void setQuinteDesordre(Double quinteDesordre) {
		this.quinteDesordre = quinteDesordre;
	}



	public Double getQuinteOrdre() {
		return quinteOrdre;
	}



	public void setQuinteOrdre(Double quinteOrdre) {
		this.quinteOrdre = quinteOrdre;
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

}