package turfinfos2.model;

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

	private Integer numcourse;

	private Boolean isCoupleGagnant;

	private Boolean isCoupleOrdre;

	private Boolean isTrio;

	private Boolean isTrioOrdre;

	private Boolean isMulti;

	private Boolean isPick5;

	private Boolean isSuper4;

	private Boolean isTierceDesordre;

	private Boolean isTierceOrdre;

	private Boolean isQuarteDesordre;

	private Boolean isQuarteOrdre;

	private Boolean isQuinteDesordre;

	private Boolean isQuinteOrdre;

	public Result(Long id, String jour, Integer numcourse, Boolean isCoupleGagnant, Boolean isCoupleOrdre,
			Boolean isTrio, Boolean isTrioOrdre, Boolean isMulti, Boolean isPick5, Boolean isSuper4,
			Boolean isTierceDesordre, Boolean isTierceOrdre, Boolean isQuarteDesordre, Boolean isQuarteOrdre,
			Boolean isQuinteDesordre, Boolean isQuinteOrdre) {
		super();
		this.id = id;
		this.jour = jour;
		this.numcourse = numcourse;
		this.isCoupleGagnant = isCoupleGagnant;
		this.isCoupleOrdre = isCoupleOrdre;
		this.isTrio = isTrio;
		this.isTrioOrdre = isTrioOrdre;
		this.isMulti = isMulti;
		this.isPick5 = isPick5;
		this.isSuper4 = isSuper4;
		this.isTierceDesordre = isTierceDesordre;
		this.isTierceOrdre = isTierceOrdre;
		this.isQuarteDesordre = isQuarteDesordre;
		this.isQuarteOrdre = isQuarteOrdre;
		this.isQuinteDesordre = isQuinteDesordre;
		this.isQuinteOrdre = isQuinteOrdre;
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

	public Boolean getIsCoupleGagnant() {
		return isCoupleGagnant;
	}

	public void setIsCoupleGagnant(Boolean isCoupleGagnant) {
		this.isCoupleGagnant = isCoupleGagnant;
	}

	public Boolean getIsCoupleOrdre() {
		return isCoupleOrdre;
	}

	public void setIsCoupleOrdre(Boolean isCoupleOrdre) {
		this.isCoupleOrdre = isCoupleOrdre;
	}

	public Boolean getIsTrio() {
		return isTrio;
	}

	public void setIsTrio(Boolean isTrio) {
		this.isTrio = isTrio;
	}

	public Boolean getIsTrioOrdre() {
		return isTrioOrdre;
	}

	public void setIsTrioOrdre(Boolean isTrioOrdre) {
		this.isTrioOrdre = isTrioOrdre;
	}

	public Boolean getIsMulti() {
		return isMulti;
	}

	public void setIsMulti(Boolean isMulti) {
		this.isMulti = isMulti;
	}

	public Boolean getIsPick5() {
		return isPick5;
	}

	public void setIsPick5(Boolean isPick5) {
		this.isPick5 = isPick5;
	}

	public Boolean getIsSuper4() {
		return isSuper4;
	}

	public void setIsSuper4(Boolean isSuper4) {
		this.isSuper4 = isSuper4;
	}

	public Boolean getIsTierceDesordre() {
		return isTierceDesordre;
	}

	public void setIsTierceDesordre(Boolean isTierceDesordre) {
		this.isTierceDesordre = isTierceDesordre;
	}

	public Boolean getIsTierceOrdre() {
		return isTierceOrdre;
	}

	public void setIsTierceOrdre(Boolean isTierceOrdre) {
		this.isTierceOrdre = isTierceOrdre;
	}

	public Boolean getIsQuarteDesordre() {
		return isQuarteDesordre;
	}

	public void setIsQuarteDesordre(Boolean isQuarteDesordre) {
		this.isQuarteDesordre = isQuarteDesordre;
	}

	public Boolean getIsQuarteOrdre() {
		return isQuarteOrdre;
	}

	public void setIsQuarteOrdre(Boolean isQuarteOrdre) {
		this.isQuarteOrdre = isQuarteOrdre;
	}

	public Boolean getIsQuinteDesordre() {
		return isQuinteDesordre;
	}

	public void setIsQuinteDesordre(Boolean isQuinteDesordre) {
		this.isQuinteDesordre = isQuinteDesordre;
	}

	public Boolean getIsQuinteOrdre() {
		return isQuinteOrdre;
	}

	public void setIsQuinteOrdre(Boolean isQuinteOrdre) {
		this.isQuinteOrdre = isQuinteOrdre;
	}
	
	
}
