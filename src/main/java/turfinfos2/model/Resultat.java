package turfinfos2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resultat")
@SequenceGenerator(name = "RESULTAT_SEQ_GENERATOR", sequenceName = "RESULTAT_SEQ", initialValue = 1, allocationSize = 1)
public class Resultat {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULTAT_SEQ_GENERATOR")
    @Column(name = "id")
	private Long id;

	private String jour;
	
	private String hour;

	private String R;

	private Integer C;
	
	private Integer numcourse;
	
	@OneToOne
	private SimpleGagnant simpleGagnant;

	@OneToOne
	private SimplePlace simplePlace;
	
	@OneToOne
    private CoupleGagnant coupleGagnant;

	@OneToOne
	private CouplePlace couplePlace;

	@OneToOne
	private CoupleOrdre coupleOrdre;

	@OneToOne
	private DeuxSurQuatre deuxSurQuatre;

	@OneToOne
	private Trio trio;

	@OneToOne
	private TrioOrdre trioOrdre;

	@OneToOne
	private Multi multis;

	@OneToOne
	private Pick5 pick5;

	@OneToOne
	private Super4 super4;

	@OneToOne
	private Tierce tierce;

	@OneToOne
	private Quarte quarte;

	@OneToOne
	private Quinte quinte;


}