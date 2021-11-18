package turfinfos2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "ParisTurfInfosConfig_SEQ_GENERATOR", sequenceName = "ParisTurfInfosConfig_SEQ", initialValue = 1, allocationSize = 1)
public class ParisTurfInfosConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ParisTurfInfosConfig_SEQ_GENERATOR")
	Long id;
	String parisTurfId;
	
	public ParisTurfInfosConfig() {
		super();
	}

	public ParisTurfInfosConfig(Long id, String parisTurfId) {
		super();
		this.id = id;
		this.parisTurfId = parisTurfId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParisTurfId() {
		return parisTurfId;
	}

	public void setParisTurfId(String parisTurfId) {
		this.parisTurfId = parisTurfId;
	}
}
