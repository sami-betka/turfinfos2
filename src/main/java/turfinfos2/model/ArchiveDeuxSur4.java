package turfinfos2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "DEUX_SUR_4_SEQ_GENERATOR", sequenceName = "DEUX_SUR_4_SEQ", initialValue = 1, allocationSize = 1)
public class ArchiveDeuxSur4 {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEUX_SUR_4_SEQ_GENERATOR")
    @Column(name = "id")
	private Long id;

	private String jour;
	
	private String hour;

	private String R;

	private Integer C;
	
	private Integer numcourse;
	
	private Boolean isWon;
	
	private Double ante;
	
	private String arrivee;

	

	@ElementCollection
	@CollectionTable(name ="deux_sur_quatre_combinaison")	
	private List<Integer> combinaison = new ArrayList<>();
	
	private Double rapport;

	public ArchiveDeuxSur4(Long id, String jour, String hour, String R, Integer C, Integer numcourse, List<Integer> combinaison,
			Double rapport, Boolean isWon, Double ante, String arrivee) {
		super();
		this.id = id;
		this.jour = jour;
		this.R = R;
		this.C = C;
		this.numcourse = numcourse;
		this.combinaison = combinaison;
		this.rapport = rapport;
		this.isWon = isWon;
		this.hour = hour;
		this.ante = ante;
		this.arrivee = arrivee;

	}

	public ArchiveDeuxSur4() {
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

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
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

	public List<Integer> getCombinaison() {
		return combinaison;
	}

	public void setCombinaison(List<Integer> combinaison) {
		this.combinaison = combinaison;
	}

	public Double getRapport() {
		return rapport;
	}

	public void setRapport(Double rapport) {
		this.rapport = rapport;
	}

	public Boolean getIsWon() {
		return isWon;
	}

	public void setIsWon(Boolean isWon) {
		this.isWon = isWon;
	}

	public Double getAnte() {
		return ante;
	}

	public void setAnte(Double ante) {
		this.ante = ante;
	}

	public String getArrivee() {
		return arrivee;
	}

	public void setArrivee(String arrivee) {
		this.arrivee = arrivee;
	}
	
}
