package turfinfos2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.math3.util.Precision;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "turf_infos"
//,
//uniqueConstraints=
//@UniqueConstraint(columnNames={"id", "numcourse"})
)
@SequenceGenerator(name = "TURF_INFOS_SEQ_GENERATOR", sequenceName = "TURF_INFOS_SEQ", initialValue = 1, allocationSize = 1)

public class TurfInfos {
	
	      

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TURF_INFOS_SEQ_GENERATOR")
	@Column(name = "id")
	@CsvBindByName
	private Long id;

//  @Column(name = "")
	@CsvBindByName
	private String jour;

//@Column(name = "")
	@CsvBindByName
	private String R;

//@Column(name = "")
	@CsvBindByName
	private Integer C;

	@Column(name = "numcourse")
	@CsvBindByName
	private Integer numcourse;

//@Column(name = "")
	@CsvBindByName
	private Integer numero;

//@Column(name = "")
	@CsvBindByName
	private String cheval;

//  @Column(name = "")
	@CsvBindByName
//  @CsvNumber("###.##")
	private Double pourcVictChevalHippo;
	
	private Double pourcPlaceCheval;

//  @Column(name = "")
	@CsvBindByName
//  @CsvNumber("###.##")
	private Double pourcVictJockHippo;

//  @Column(name = "")
	@CsvBindByName
//  @CsvNumber("###.##")
	private Double pourcVictEntHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Double pourcPlaceChevalHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Double pourcPlaceJockHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Double pourcPlaceEntHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Double TxVictCouple;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Double TxPlaceCouple;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Double TxVictCoupleHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Double TxPlaceCoupleHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Integer recence;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String coursescheval;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String nbrCourseChevalHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String nbrCourseJockHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String nbrCourseEntHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private Integer nbCourseCouple;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String nbCourseCoupleHippo;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String typec;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String entraineur;

//@Column(name = "")
	@CsvBindByName
//@CsvNumber("###.##")
	private String cl;

//@Column(name = "")
//	@CsvBindByName
//@CsvNumber("###.##")
	
	private Integer nbVictChevalHippo;
	
	private boolean blinkersFirstTime;
	
	private boolean noShoesFirstTime;
	
	private boolean protectionFirstTime;
	
	private String raceSpecialty;
	
	private String distanceAndSpecialtyChrono;
	
	private Double cotedirect;

	private Integer chrono;

	private Integer tayProno;

	private Double noteProno;

	private String numeroString;
	
	private String reunionstring;

	private Double notePercentageParameter;

	private Integer clInt;

	private String tableId;
	
	private String libel_hippo;
	
	private Integer nbVictCouple;
	
	private Integer coupleTwoOrThree;
	
	private Integer chevalTwoOrThreeHippo;
	
	private Integer ranking;
	
	private Double liveOdd;
	
	private Double liveOddPlace;
	
	private Boolean isRunning;
	
	private Integer numberOfInitialRunners;
	
	private Boolean isTQQ;
	
	private Boolean isPick5;
	
	
	private String caraList1;
	
	private String caraList2;
	
	private Boolean isPremium;
	
	private Boolean hasBetTypes;
	
	private Integer draw;
	
	private String hour;





	public TurfInfos(Long id, String jour, String R, Integer C, Integer numcourse, Integer numero, String cheval,
			Double pourcVictChevalHippo, Double pourcPlaceCheval, Double pourcVictJockHippo, Double pourcVictEntHippo,
			Double pourcPlaceChevalHippo, Double pourcPlaceJockHippo, Double pourcPlaceEntHippo, Double TxVictCouple,
			Double TxPlaceCouple, Double TxVictCoupleHippo, Double TxPlaceCoupleHippo, Integer chrono,
			Double noteProno, Integer recence, String numeroString, String coursescheval, String coursesjockey,
			String coursesentraineur, Integer nbCourseCouple, String nbrCourseChevalHippo, String nbrCourseJockHippo,
			String nbrCourseEntHippo, Integer tayProno, String typec, String entraineur, Double notePercentageParameter,
			String cl, Integer clInt, Double cotedirect, String tableId, boolean blinkersFirstTime, boolean noShoesFirstTime,
			boolean protectionFirstTime, String raceSpecialty, String distanceAndSpecialtyChrono, String reunionstring, String libel_hippo,
			Integer nbVictCouple, Integer coupleTwoOrThree,Integer chevalTwoOrThreeHippo, Integer nbVictChevalHippo,
			Integer ranking, Double liveOdd, Boolean isRunning, Integer numberOfInitialRunners, Boolean isTQQ, Boolean isPick5,
			String caraList1, String caraList2, Boolean isPremium, Boolean hasBetTypes, Integer draw, Double liveOddPlace,
			String hour) {
		
		this.id = id;
		this.R = R;
		this.C = C;
		this.pourcVictChevalHippo = pourcVictChevalHippo;
		this.pourcVictJockHippo = pourcVictJockHippo;
		this.pourcVictEntHippo = pourcVictEntHippo;
		this.numero = numero;
		this.cheval = cheval;
		this.jour = jour;
		this.hour = hour;
		this.pourcPlaceChevalHippo = pourcPlaceChevalHippo;
		this.pourcPlaceJockHippo = pourcPlaceJockHippo;
		this.pourcPlaceEntHippo = pourcPlaceEntHippo;
		this.TxVictCouple = TxVictCouple;
		this.TxPlaceCouple = TxPlaceCouple;
		this.TxVictCoupleHippo = TxVictCoupleHippo;
		this.TxPlaceCoupleHippo = TxPlaceCoupleHippo;
		this.recence = recence;
		this.coursescheval = coursescheval;
//    this.coursesjockey = coursesjockey;
//    this.coursesentraineur = coursesentraineur;
		this.nbCourseCouple = nbCourseCouple;
		this.nbrCourseChevalHippo = nbrCourseChevalHippo;
		this.nbrCourseJockHippo = nbrCourseJockHippo;
		this.nbrCourseEntHippo = nbrCourseEntHippo;
		this.typec = typec;
		this.entraineur = entraineur;
		this.cl = cl;
		this.cotedirect = cotedirect;
		this.blinkersFirstTime = blinkersFirstTime;
		this.noShoesFirstTime = blinkersFirstTime;
		this.protectionFirstTime = protectionFirstTime;
		this.raceSpecialty = raceSpecialty;
		this.distanceAndSpecialtyChrono = distanceAndSpecialtyChrono;
		this.reunionstring = reunionstring;
		this.libel_hippo = libel_hippo;
		this.nbVictCouple = nbVictCouple;
		this.coupleTwoOrThree = coupleTwoOrThree;
		this.chevalTwoOrThreeHippo = chevalTwoOrThreeHippo;
		this.nbVictChevalHippo = nbVictChevalHippo;
		this.pourcPlaceCheval = pourcPlaceCheval;
		this.ranking = ranking;
		this.liveOdd = liveOdd;
		this.liveOddPlace = liveOddPlace;
		this.isRunning = isRunning;
		this.numberOfInitialRunners = numberOfInitialRunners;
		this.isTQQ = isTQQ;
		this.isPick5 = isPick5;
		
		this.caraList1 = caraList1;
		this.caraList2 = caraList2;
		
		this.isPremium = isPremium;
		this.draw = draw;







		// Paramètres crées

		this.chrono = chrono;
		this.tayProno = tayProno;
		this.noteProno = 0d;
		this.numeroString = numeroString;
		this.notePercentageParameter = notePercentageParameter;
		this.clInt = clInt;
		this.tableId = tableId;
		this.hasBetTypes = hasBetTypes;

	}

	public TurfInfos() {

		this.noteProno = 0d;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPourcVictChevalHippo() {
		return pourcVictChevalHippo;
	}

	public void setPourcVictChevalHippo(Double pourcVictChevalHippo) {
		this.pourcVictChevalHippo = Precision.round(pourcVictChevalHippo, 2);
	}

	public Double getPourcVictJockHippo() {
		return pourcVictJockHippo;
	}

	public void setPourcVictJockHippo(Double pourcVictJockHippo) {
		this.pourcVictJockHippo = Precision.round(pourcVictJockHippo, 2);
	}

	public Double getPourcVictEntHippo() {
		return pourcVictEntHippo;
	}

	public void setPourcVictEntHippo(Double pourcVictEntHippo) {
		this.pourcVictEntHippo = Precision.round(pourcVictEntHippo, 2);
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCheval() {
		return cheval;
	}

	public void setCheval(String cheval) {
		this.cheval = cheval;
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

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public Integer getChrono() {
		return chrono;
	}

	public void setChrono(Integer chrono) {
		this.chrono = chrono;
	}

	public Double getPourcPlaceChevalHippo() {
		return pourcPlaceChevalHippo;
	}

	public void setPourcPlaceChevalHippo(Double pourcPlaceChevalHippo) {
		this.pourcPlaceChevalHippo = Precision.round(pourcPlaceChevalHippo, 2) ;
	}

	public Double getPourcPlaceJockHippo() {
		return pourcPlaceJockHippo;
	}

	public void setPourcPlaceJockHippo(Double pourcPlaceJockHippo) {
		this.pourcPlaceJockHippo = Precision.round(pourcPlaceJockHippo, 2) ;
	}

	public Double getPourcPlaceEntHippo() {
		return pourcPlaceEntHippo;
	}

	public void setPourcPlaceEntHippo(Double pourcPlaceEntHippo) {
		this.pourcPlaceEntHippo = Precision.round(pourcPlaceEntHippo, 2);
	}

	public Double getTxVictCouple() {
		return TxVictCouple;
	}

	public void setTxVictCouple(Double txVictCouple) {
		TxVictCouple = Precision.round(txVictCouple, 2);
	}

	public Double getTxPlaceCouple() {
		return TxPlaceCouple;
	}

	public void setTxPlaceCouple(Double txPlaceCouple) {
		TxPlaceCouple = Precision.round(txPlaceCouple, 2);
	}

	public Double getTxVictCoupleHippo() {
		return TxVictCoupleHippo;
	}

	public void setTxVictCoupleHippo(Double txVictCoupleHippo) {
		TxVictCoupleHippo = Precision.round(txVictCoupleHippo, 2);
	}

	public Double getTxPlaceCoupleHippo() {
		return TxPlaceCoupleHippo;
	}

	public void setTxPlaceCoupleHippo(Double txPlaceCoupleHippo) {
		TxPlaceCoupleHippo =  Precision.round(txPlaceCoupleHippo, 2);
	}

	public Double getNoteProno() {
		return noteProno;
	}

	public void setNoteProno(Double noteProno) {
		this.noteProno =  Precision.round(noteProno, 2);
	}

	public Integer getRecence() {
		return recence;
	}

	public void setRecence(Integer recence) {
		this.recence = recence;
	}

	public String getNumeroString() {
		return numeroString;
	}

	public void setNumeroString(String numeroString) {
		this.numeroString = numeroString;
	}

//
//public String getCoursesjockey() {
//	return coursesjockey;
//}
//
//public void setCoursesjockey(String coursesjockey) {
//	this.coursesjockey = coursesjockey;
//}
//
//public String getCoursesentraineur() {
//	return coursesentraineur;
//}
//
//public void setCoursesentraineur(String coursesentraineur) {
//	this.coursesentraineur = coursesentraineur;
//}

	public Integer getNbCourseCouple() {
		return nbCourseCouple;
	}

	public void setNbCourseCouple(Integer nbCourseCouple) {
		this.nbCourseCouple = nbCourseCouple;
	}

	public String getNbrCourseChevalHippo() {
		return nbrCourseChevalHippo;
	}

	public void setNbrCourseChevalHippo(String nbrCourseChevalHippo) {
		this.nbrCourseChevalHippo = nbrCourseChevalHippo;
	}

	public String getNbrCourseJockHippo() {
		return nbrCourseJockHippo;
	}

	public void setNbrCourseJockHippo(String nbrCourseJockHippo) {
		this.nbrCourseJockHippo = nbrCourseJockHippo;
	}

	public String getNbrCourseEntHippo() {
		return nbrCourseEntHippo;
	}

	public void setNbrCourseEntHippo(String nbrCourseEntHippo) {
		this.nbrCourseEntHippo = nbrCourseEntHippo;
	}

	public String getNbCourseCoupleHippo() {
		return nbCourseCoupleHippo;
	}

	public void setNbCourseCoupleHippo(String nbCourseCoupleHippo) {
		this.nbCourseCoupleHippo = nbCourseCoupleHippo;
	}

	public Integer getTayProno() {
		return tayProno;
	}

	public void setTayProno(Integer tayProno) {
		this.tayProno = tayProno;
	}

	public String getTypec() {
		return typec;
	}

	public void setTypec(String typec) {
		this.typec = typec;
	}

	public String getEntraineur() {
		return entraineur;
	}

	public void setEntraineur(String entraineur) {
		this.entraineur = entraineur;
	}

	public Double getNotePercentageParameter() {
		return notePercentageParameter;
	}

	public void setNotePercentageParameter(Double notePercentageParameter) {
		this.notePercentageParameter = notePercentageParameter;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public Integer getClInt() {
		return clInt;
	}

	public void setClInt(Integer clInt) {
		this.clInt = clInt;
	}

	public Double getCotedirect() {
		return cotedirect;
	}

	public void setCotedirect(Double cotedirect) {
		this.cotedirect = cotedirect;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public boolean isBlinkersFirstTime() {
		return blinkersFirstTime;
	}

	public void setBlinkersFirstTime(boolean blinkersFirstTime) {
		this.blinkersFirstTime = blinkersFirstTime;
	}

	public boolean isNoShoesFirstTime() {
		return noShoesFirstTime;
	}

	public void setNoShoesFirstTime(boolean noShoesFirstTime) {
		this.noShoesFirstTime = noShoesFirstTime;
	}

	public boolean isProtectionFirstTime() {
		return protectionFirstTime;
	}

	public void setProtectionFirstTime(boolean protectionFirstTime) {
		this.protectionFirstTime = protectionFirstTime;
	}

	public String getRaceSpecialty() {
		return raceSpecialty;
	}

	public void setRaceSpecialty(String raceSpecialty) {
		this.raceSpecialty = raceSpecialty;
	}

	public String getDistanceAndSpecialtyChrono() {
		return distanceAndSpecialtyChrono;
	}

	public void setDistanceAndSpecialtyChrono(String distanceAndSpecialtyChrono) {
		this.distanceAndSpecialtyChrono = distanceAndSpecialtyChrono;
	}

	public String getReunionstring() {
		return reunionstring;
	}

	public void setReunionstring(String reunionString) {
		this.reunionstring = reunionString;
	}

	public String getLibel_hippo() {
		return libel_hippo;
	}

	public void setLibel_hippo(String libel_hippo) {
		this.libel_hippo = libel_hippo;
	}

	public Integer getNbVictCouple() {
		return nbVictCouple;
	}

	public void setNbVictCouple(Integer nbVictCouple) {
		this.nbVictCouple = nbVictCouple;
	}

	public String getCoursescheval() {
		return coursescheval;
	}

	public void setCoursescheval(String coursescheval) {
		this.coursescheval = coursescheval;
	}

	public Integer getCoupleTwoOrThree() {
		return coupleTwoOrThree;
	}

	public void setCoupleTwoOrThree(Integer coupleTwoOrThree) {
		this.coupleTwoOrThree = coupleTwoOrThree;
	}

	public Integer getChevalTwoOrThreeHippo() {
		return chevalTwoOrThreeHippo;
	}

	public void setChevalTwoOrThreeHippo(Integer chevalTwoOrThreeHippo) {
		this.chevalTwoOrThreeHippo = chevalTwoOrThreeHippo;
	}

	public Integer getNbVictChevalHippo() {
		return nbVictChevalHippo;
	}

	public void setNbVictChevalHippo(Integer nbVictChevalHippo) {
		this.nbVictChevalHippo = nbVictChevalHippo;
	}

	public Double getPourcPlaceCheval() {
		return pourcPlaceCheval;
	}

	public void setPourcPlaceCheval(Double pourcPlaceCheval) {
		this.pourcPlaceCheval = Precision.round(pourcPlaceCheval, 2);
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

	public Integer getNumberOfInitialRunners() {
		return numberOfInitialRunners;
	}

	public void setNumberOfInitialRunners(Integer numberOfInitialRunners) {
		this.numberOfInitialRunners = numberOfInitialRunners;
	}

	public Boolean getIsTQQ() {
		return isTQQ;
	}

	public void setIsTQQ(Boolean isTQQ) {
		this.isTQQ = isTQQ;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Boolean getIsPick5() {
		return isPick5;
	}

	public void setIsPick5(Boolean isPick5) {
		this.isPick5 = isPick5;
	}

	public String getCaraList1() {
		return caraList1;
	}

	public void setCaraList1(String caraList1) {
		this.caraList1 = caraList1;
	}

	public String getCaraList2() {
		return caraList2;
	}

	public void setCaraList2(String caraList2) {
		this.caraList2 = caraList2;
	}

	public Boolean getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}

	public Boolean getHasBetTypes() {
		return hasBetTypes;
	}

	public void setHasBetTypes(Boolean hasBetTypes) {
		this.hasBetTypes = hasBetTypes;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Double getLiveOddPlace() {
		return liveOddPlace;
	}

	public void setLiveOddPlace(Double liveOddPlace) {
		this.liveOddPlace = liveOddPlace;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

}