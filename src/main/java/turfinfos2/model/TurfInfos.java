package turfinfos2.model;

import java.text.NumberFormat;

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
	private String nbCourseCouple;

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
	@CsvBindByName
//@CsvNumber("###.##")
	private Double cotedirect;

	private Integer chrono;

	private Integer tayProno;

	private Double noteProno;

	private String numeroString;

	private Double notePercentageParameter;

	private Integer clInt;

	private String tableId;

	public TurfInfos(Long id, String jour, String R, Integer C, Integer numcourse, Integer numero, String cheval,
			Double pourcVictChevalHippo, Double pourcVictJockHippo, Double pourcVictEntHippo,
			Double pourcPlaceChevalHippo, Double pourcPlaceJockHippo, Double pourcPlaceEntHippo, Double TxVictCouple,
			Double TxPlaceCouple, Double TxVictCoupleHippo, Double TxPlaceCoupleHippo, Integer chrono,
			Integer noteProno, Integer recence, String numeroString, String coursescheval, String coursesjockey,
			String coursesentraineur, String nbCourseCouple, String nbrCourseChevalHippo, String nbrCourseJockHippo,
			String nbrCourseEntHippo, Integer tayProno, String typec, String entraineur, Double notePercentageParameter,
			String cl, Integer clInt, Double cotedirect, String tableId) {
		this.id = id;
		this.R = R;
		this.C = C;
		this.pourcVictChevalHippo = pourcVictChevalHippo;
		this.pourcVictJockHippo = pourcVictJockHippo;
		this.pourcVictEntHippo = pourcVictEntHippo;
		this.numero = numero;
		this.cheval = cheval;
		this.jour = jour;
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

		// Paramètres crées

		this.chrono = chrono;
		this.tayProno = tayProno;
		this.noteProno = 0d;
		this.numeroString = numeroString;
		this.notePercentageParameter = notePercentageParameter;
		this.clInt = clInt;
		this.tableId = tableId;

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
		this.noteProno = noteProno;
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

//	public String getCoursescheval() {
//		return coursescheval;
//	}
//
//	public void setCoursescheval(String coursescheval) {
//		this.coursescheval = coursescheval;
//	}
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

	public String getNbCourseCouple() {
		return nbCourseCouple;
	}

	public void setNbCourseCouple(String nbCourseCouple) {
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

}