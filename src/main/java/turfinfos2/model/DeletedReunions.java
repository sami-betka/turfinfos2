package turfinfos2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.opencsv.bean.CsvBindByName;

@Entity
@SequenceGenerator(name = "DELETED_REUNIONS_SEQ_GENERATOR", sequenceName = "DELETED_REUNIONS_SEQ", initialValue = 1, allocationSize = 1)
public class DeletedReunions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELETED_REUNIONS_SEQ_GENERATOR")
	@Column(name = "id")
	@CsvBindByName
	private Long id;
	
	private String jour;
	
	private String R;
	
	private Integer C;

	public DeletedReunions(Long id, String jour, String r, Integer c) {
		super();
		this.id = id;
		this.jour = jour;
		R = r;
		C = c;
	}

	public DeletedReunions() {
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

	
	

}
