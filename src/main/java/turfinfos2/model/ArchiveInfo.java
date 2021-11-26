package turfinfos2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "ARCHIVE_INFO_SEQ_GENERATOR", sequenceName = "ARCHIVE_INFOS_SEQ", initialValue = 1, allocationSize = 1)

public class ArchiveInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARCHIVE_INFOS_SEQ_GENERATOR")
	private Long id;

	private String jour;
	
	private String hour;

	private String R;

	private Integer C;

	private Integer numcourse;

	private Integer numero;

	private Double noteProno;

	private String numeroString;

	private String reunionstring;
	
    private Integer ranking;
	
	private Double liveOdd;
	
	private Double liveOddPlace;
	
	private Integer numberOfInitialRunners;

	private Boolean isCross;
	
	private Boolean isFirstInProno;
	
	private Double liveOddPlaceOnline;
	
	private String raceSpecialty;
	
	private Boolean hasBetTypes;
	
	private Integer initialBankrollAmount;
	
	private Double ante;
	
	private Boolean isFavori;
	
	private Integer recence;
	
	
	
	
	private Boolean chronoPastille;

	private Boolean jockeyPastille;
	
	private Boolean couplePastille;
	
	private Boolean chevalPastille;
		
	private Boolean isCanceled;
	
	private String formFigs;

	



	
	public ArchiveInfo(Long id, String jour, String hour, String r, Integer c, Integer numcourse, Integer numero,
			Double noteProno, String numeroString, String reunionstring, Integer ranking, Double liveOdd,
			Double liveOddPlace, Integer numberOfInitialRunners, Boolean isCross, Boolean isFirstInProno,
			Double liveOddPlaceOnline, String raceSpecialty, Boolean hasBetTypes, Integer initialBankrollAmount,
			Double ante, Boolean isFavori, Integer recence, Boolean isCanceled, Boolean chronoPastille, Boolean jockeyPastille,
			Boolean couplePastille, Boolean chevalPastille, String formFigs) {
		super();
		this.id = id;
		this.jour = jour;
		this.hour = hour;
		this.R = r;
		this.C = c;
		this.numcourse = numcourse;
		this.numero = numero;
		this.noteProno = noteProno;
		this.numeroString = numeroString;
		this.reunionstring = reunionstring;
		this.ranking = ranking;
		this.liveOdd = liveOdd;
		this.liveOddPlace = liveOddPlace;
		this.numberOfInitialRunners = numberOfInitialRunners;
		this.isCross = isCross;
		this.isFirstInProno = isFirstInProno;
		this.liveOddPlaceOnline = liveOddPlaceOnline;
		this.raceSpecialty = raceSpecialty;
		this.hasBetTypes = hasBetTypes;
		this.initialBankrollAmount = initialBankrollAmount;
		this.ante = ante;
		this.isFavori = isFavori;
		this.recence = recence;
		this.isCanceled = isCanceled;
		this.chevalPastille = chevalPastille;
		this.chronoPastille = chronoPastille;
		this.couplePastille = couplePastille;
		this.jockeyPastille = couplePastille;
		this.formFigs = formFigs;



		



	}


	public ArchiveInfo() {
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


	public Integer getNumero() {
		return numero;
	}


	public void setNumero(Integer numero) {
		this.numero = numero;
	}


	public Double getNoteProno() {
		return noteProno;
	}


	public void setNoteProno(Double noteProno) {
		this.noteProno = noteProno;
	}


	public String getNumeroString() {
		return numeroString;
	}


	public void setNumeroString(String numeroString) {
		this.numeroString = numeroString;
	}


	public String getReunionstring() {
		return reunionstring;
	}


	public void setReunionstring(String reunionstring) {
		this.reunionstring = reunionstring;
	}


	public Integer getRanking() {
		return ranking;
	}


	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}


	public Double getLiveOdd() {
		return liveOdd;
	}


	public void setLiveOdd(Double liveOdd) {
		this.liveOdd = liveOdd;
	}


	public Double getLiveOddPlace() {
		return liveOddPlace;
	}


	public void setLiveOddPlace(Double liveOddPlace) {
		this.liveOddPlace = liveOddPlace;
	}


	public Integer getNumberOfInitialRunners() {
		return numberOfInitialRunners;
	}


	public void setNumberOfInitialRunners(Integer numberOfInitialRunners) {
		this.numberOfInitialRunners = numberOfInitialRunners;
	}


	public Boolean getIsCross() {
		return isCross;
	}


	public void setIsCross(Boolean isCross) {
		this.isCross = isCross;
	}


	public Boolean getIsFirstInProno() {
		return isFirstInProno;
	}


	public void setIsFirstInProno(Boolean isFirstInProno) {
		this.isFirstInProno = isFirstInProno;
	}


	public Double getLiveOddPlaceOnline() {
		return liveOddPlaceOnline;
	}


	public void setLiveOddPlaceOnline(Double liveOddPlaceOnline) {
		this.liveOddPlaceOnline = liveOddPlaceOnline;
	}


	public String getRaceSpecialty() {
		return raceSpecialty;
	}


	public void setRaceSpecialty(String raceSpecialty) {
		this.raceSpecialty = raceSpecialty;
	}


	public Boolean getHasBetTypes() {
		return hasBetTypes;
	}


	public void setHasBetTypes(Boolean hasBetTypes) {
		this.hasBetTypes = hasBetTypes;
	}


	public Integer getInitialBankrollAmount() {
		return initialBankrollAmount;
	}


	public void setInitialBankrollAmount(Integer initialBankrollAmount) {
		this.initialBankrollAmount = initialBankrollAmount;
	}


	public Double getAnte() {
		return ante;
	}


	public void setAnte(Double ante) {
		this.ante = ante;
	}


	public Boolean getIsFavori() {
		return isFavori;
	}


	public void setIsFavori(Boolean isFavori) {
		this.isFavori = isFavori;
	}


	public Integer getRecence() {
		return recence;
	}


	public void setRecence(Integer recence) {
		this.recence = recence;
	}


	public Boolean getChronoPastille() {
		return chronoPastille;
	}


	public void setChronoPastille(Boolean chronoPastille) {
		this.chronoPastille = chronoPastille;
	}


	public Boolean getJockeyPastille() {
		return jockeyPastille;
	}


	public void setJockeyPastille(Boolean jockeyPastille) {
		this.jockeyPastille = jockeyPastille;
	}


	public Boolean getCouplePastille() {
		return couplePastille;
	}


	public void setCouplePastille(Boolean couplePastille) {
		this.couplePastille = couplePastille;
	}


	public Boolean getChevalPastille() {
		return chevalPastille;
	}


	public void setChevalPastille(Boolean chevalPastille) {
		this.chevalPastille = chevalPastille;
	}


	public Boolean getIsCanceled() {
		return isCanceled;
	}


	public void setIsCanceled(Boolean isCanceled) {
		this.isCanceled = isCanceled;
	}


	public String getFormFigs() {
		return formFigs;
	}


	public void setFormFigs(String formFigs) {
		this.formFigs = formFigs;
	}

}
