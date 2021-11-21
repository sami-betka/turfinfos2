package turfinfos2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "CURRENT_ODDS_GENERATOR", sequenceName = "CURRENT_ODDS_SEQ", initialValue = 1, allocationSize = 1)

public class CurrentOdds {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURRENT_ODDS_SEQ_GENERATOR")
	private Long id;

	private String jour;

	private String time;

	private String R;

	private Integer C;
	
	private Boolean isFavori;

	@ElementCollection
	@CollectionTable(name = "current_odds_mapping", joinColumns = {
			@JoinColumn(name = "odds_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "pmu_number")
	@Column(name = "odd")
	Map<Integer, Double> oddsMap = new HashMap<>();

	public CurrentOdds() {
		super();
	}

	public CurrentOdds(Long id, String jour, String time, String r, Integer c, Map<Integer, Double> oddsMap, Boolean isFavori) {
		super();
		this.id = id;
		this.jour = jour;
		this.time = time;
		this.R = r;
		this.C = c;
		this.oddsMap = oddsMap;
		this.isFavori = isFavori;
	}

	public Boolean getIsFavori() {
		return isFavori;
	}

	public void setIsFavori(Boolean isFavori) {
		this.isFavori = isFavori;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public Map<Integer, Double> getOddsMap() {
		return oddsMap;
	}

	public void setOddsMap(Map<Integer, Double> oddsMap) {
		this.oddsMap = oddsMap;
	}

}
