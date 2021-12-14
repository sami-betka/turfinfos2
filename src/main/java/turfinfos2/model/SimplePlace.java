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
@SequenceGenerator(name = "SIMPLE_PLACE_SEQ_GENERATOR", sequenceName = "SIMPLE_PLACE_SEQ", initialValue = 1, allocationSize = 1)
public class SimplePlace {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMPLE_PLACE_SEQ_GENERATOR")
    @Column(name = "id")
	private Long id;

	private String jour;
	
	private String hour;

	private String R;

	private Integer C;
	
	private Integer numcourse;

	private String first;
	
	private String second;

	private String third;

	private Double firstRapport;
	
	private Double secondRapport;

	private Double thirdRapport;

	
	
}
