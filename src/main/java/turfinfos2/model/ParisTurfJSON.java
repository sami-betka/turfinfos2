package turfinfos2.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

public class ParisTurfJSON {
	
	private Long id;  
	private Integer horseId;  
	private String saddle;  //numero
	private String date;   //jour
	private Integer raceId; //numcourse
	private String trainerName;  //entraineur
	private String numRace;  //C
	
	private String tableId;

	
    private Map<String, Object> statsHorseRacecourse;
    private Map<String, Object> statsJockeyRacecourse;
    private Map<String, Object> statsTrainerRacecourse;
    
    
	public ParisTurfJSON(Long id, Integer horseId, String saddle, String date, Integer raceId, String trainerName,
			String numRace, String tableId, Map<String, Object> statsHorseRacecourse, Map<String, Object> statsJockeyRacecourse,
			Map<String, Object> statsTrainerRacecourse) {
		super();
		this.id = id;
		this.horseId = horseId;
		this.saddle = saddle;
		this.date = date;
		this.raceId = raceId;
		this.trainerName = trainerName;
		this.numRace = numRace;
		this.tableId = tableId;

		this.statsHorseRacecourse = statsHorseRacecourse;
		this.statsJockeyRacecourse = statsJockeyRacecourse;
		this.statsTrainerRacecourse = statsTrainerRacecourse;
	}


	public ParisTurfJSON() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getHorseId() {
		return horseId;
	}


	public void setHorseId(Integer horseId) {
		this.horseId = horseId;
	}


	public String getSaddle() {
		return saddle;
	}


	public void setSaddle(String saddle) {
		this.saddle = saddle;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public Integer getRaceId() {
		return raceId;
	}


	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}


	public String getTrainerName() {
		return trainerName;
	}


	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}


	public String getNumRace() {
		return numRace;
	}


	public void setNumRace(String numRace) {
		this.numRace = numRace;
	}


	public Map<String, Object> getStatsHorseRacecourse() {
		return statsHorseRacecourse;
	}


	public void setStatsHorseRacecourse(Map<String, Object> statsHorseRacecourse) {
		this.statsHorseRacecourse = statsHorseRacecourse;
	}


	public Map<String, Object> getStatsJockeyRacecourse() {
		return statsJockeyRacecourse;
	}


	public void setStatsJockeyRacecourse(Map<String, Object> statsJockeyRacecourse) {
		this.statsJockeyRacecourse = statsJockeyRacecourse;
	}


	public Map<String, Object> getStatsTrainerRacecourse() {
		return statsTrainerRacecourse;
	}


	public void setStatsTrainerRacecourse(Map<String, Object> statsTrainerRacecourse) {
		this.statsTrainerRacecourse = statsTrainerRacecourse;
	}


	@Override
	public String toString() {
		return "ParisTurfJSON [id=" + id + ", horseId=" + horseId + ", saddle=" + saddle + ", date=" + date
				+ ", raceId=" + raceId + ", trainerName=" + trainerName + ", numRace=" + numRace
				+ ", statsHorseRacecourse=" + statsHorseRacecourse + ", statsJockeyRacecourse=" + statsJockeyRacecourse
				+ ", statsTrainerRacecourse=" + statsTrainerRacecourse + "]";
	}


	public String getTableId() {
		return tableId;
	}


	public void setTableId(String tableId) {
		this.tableId = tableId;
	}



}
