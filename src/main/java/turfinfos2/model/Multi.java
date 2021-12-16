package turfinfos2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "MULTI_GENERATOR", sequenceName = "MULTI_SEQ", initialValue = 1, allocationSize = 1)
public class Multi {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTI_SEQ_GENERATOR")
	private Long id;

	private String jour;

	private String R;

	private Integer C;

	private Integer numcourse;

	private Double multi4;

	private Double multi5;

	private Double multi6;

	private Double multi7;

	@OneToOne
	private Resultat resultat;

	

	public Multi(Long id, String jour, String r, Integer c, Integer numcourse, Double multi4, Double multi5,
			Double multi6, Double multi7, Resultat resultat) {
		super();
		this.id = id;
		this.jour = jour;
		R = r;
		C = c;
		this.numcourse = numcourse;
		this.multi4 = multi4;
		this.multi5 = multi5;
		this.multi6 = multi6;
		this.multi7 = multi7;
		this.resultat = resultat;
	}

	public Multi() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMulti4() {
		return multi4;
	}

	public void setMulti4(Double multi4) {
		this.multi4 = multi4;
	}

	public Double getMulti5() {
		return multi5;
	}

	public void setMulti5(Double multi5) {
		this.multi5 = multi5;
	}

	public Double getMulti6() {
		return multi6;
	}

	public void setMulti6(Double multi6) {
		this.multi6 = multi6;
	}

	public Double getMulti7() {
		return multi7;
	}

	public void setMulti7(Double multi7) {
		this.multi7 = multi7;
	}

	public Resultat getResult() {
		return resultat;
	}

	public void setResult(Resultat resultat) {
		this.resultat = resultat;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
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

}
//package turfinfos2.model;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.SequenceGenerator;
//
//@Entity
//@SequenceGenerator(name = "MULTI_GENERATOR", sequenceName = "MULTI_SEQ", initialValue = 1, allocationSize = 1)
//public class Multi {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTI_SEQ_GENERATOR")
//	private Long id;
//
//	private String jour;
//
//	private String R;
//
//	private Integer C;
//
//	private Integer numcourse;
//
//	private Double multi4;
//
//	private Double multi5;
//
//	private Double multi6;
//
//	private Double multi7;
//
////	@OneToOne
////	private Resultat resultat;
//
//	
//
//	public Multi(Long id, String jour, String r, Integer c, Integer numcourse, Double multi4, Double multi5,
//			Double multi6, Double multi7, Resultat resultat) {
//		super();
//		this.id = id;
//		this.jour = jour;
//		R = r;
//		C = c;
//		this.numcourse = numcourse;
//		this.multi4 = multi4;
//		this.multi5 = multi5;
//		this.multi6 = multi6;
//		this.multi7 = multi7;
//		this.resultat = resultat;
//	}
//
//	public Multi() {
//		super();
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Double getMulti4() {
//		return multi4;
//	}
//
//	public void setMulti4(Double multi4) {
//		this.multi4 = multi4;
//	}
//
//	public Double getMulti5() {
//		return multi5;
//	}
//
//	public void setMulti5(Double multi5) {
//		this.multi5 = multi5;
//	}
//
//	public Double getMulti6() {
//		return multi6;
//	}
//
//	public void setMulti6(Double multi6) {
//		this.multi6 = multi6;
//	}
//
//	public Double getMulti7() {
//		return multi7;
//	}
//
//	public void setMulti7(Double multi7) {
//		this.multi7 = multi7;
//	}
//
//	public Resultat getResult() {
//		return resultat;
//	}
//
//	public void setResult(Resultat resultat) {
//		this.resultat = resultat;
//	}
//
//	public String getJour() {
//		return jour;
//	}
//
//	public void setJour(String jour) {
//		this.jour = jour;
//	}
//
//	public String getR() {
//		return R;
//	}
//
//	public void setR(String r) {
//		R = r;
//	}
//
//	public Integer getC() {
//		return C;
//	}
//
//	public void setC(Integer c) {
//		C = c;
//	}
//
//	public Integer getNumcourse() {
//		return numcourse;
//	}
//
//	public void setNumcourse(Integer numcourse) {
//		this.numcourse = numcourse;
//	}
//
//}
