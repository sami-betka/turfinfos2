package turfinfos2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "QUARTE_SEQ_GENERATOR", sequenceName = "QUARTE_SEQ", initialValue = 1, allocationSize = 1)
public class Quarte {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUARTE_SEQ_GENERATOR")
    @Column(name = "id")
	private Long id;

	private String jour;
	
	private String hour;

	private String R;

	private Integer C;
	
	private Integer numcourse;
	
	private String combinaison;
	
	private Double rapportOrdre;
	
	private Double rapportDesordre;
	
	private Double bonus;


	
	
}
