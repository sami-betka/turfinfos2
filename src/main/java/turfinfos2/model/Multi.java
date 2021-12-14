package turfinfos2.model;

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
@SequenceGenerator(name = "MULTI_GENERATOR", sequenceName = "MULTI_SEQ", initialValue = 1, allocationSize = 1)
public class Multi {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTI_SEQ_GENERATOR")
	private Long id;

	private String jour;

	private String R;

	private Integer C;

	private Integer numcourse;
	
	private String combinaison;

	private Double rapport4;

	private Double rapport5;

	private Double rapport6;

	private Double rapport7;


	


}
