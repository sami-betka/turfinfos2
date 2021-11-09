package turfinfos2.config;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InfoConfig {
	
	@Id
	private Long id;
	
	private String parisTurfKey;

	public InfoConfig() {
		super();
	}

	public InfoConfig(String parisTurfKey) {
		super();
		this.parisTurfKey = parisTurfKey;
	}

	public String getParisTurfKey() {
		return parisTurfKey;
	}

	public void setParisTurfKey(String parisTurfKey) {
		this.parisTurfKey = parisTurfKey;
	}

}
