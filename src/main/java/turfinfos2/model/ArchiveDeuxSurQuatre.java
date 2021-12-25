package turfinfos2.model;

//@Entity
//@SequenceGenerator(name = "ARCHIVE_DEUX_SUR_QUATRE_SEQ_GENERATOR", sequenceName = "ARCHIVE_DEUX_SUR_QUATRE_SEQ", initialValue = 1, allocationSize = 1)

public class ArchiveDeuxSurQuatre {

//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARCHIVE_DEUX_SUR_QUATRE_SEQ_GENERATOR")
	private Long id;

	private String jour;
	
	private String hour;

	private String R;

	private Integer C;
	
	private Integer first;
	
	private Integer second;

	private Integer third;

	private Integer fourth;
	
	private Double ante;
	
	private Double rapport;
	
	private Boolean isWon;

	

	public ArchiveDeuxSurQuatre(Long id, String jour, String hour, String r, Integer c, Integer first, Integer second,
			Integer third, Integer fourth, Double ante, Double rapport, Boolean isWon) {
		super();
		this.id = id;
		this.jour = jour;
		this.hour = hour;
		this.R = r;
		this.C = c;
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.ante = ante;
		this.rapport = rapport;
		this.isWon = isWon;

	}

	public ArchiveDeuxSurQuatre() {
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

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
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

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public Integer getThird() {
		return third;
	}

	public void setThird(Integer third) {
		this.third = third;
	}

	public Integer getFourth() {
		return fourth;
	}

	public void setFourth(Integer fourth) {
		this.fourth = fourth;
	}

	public Double getRapport() {
		return rapport;
	}

	public void setRapport(Double rapport) {
		this.rapport = rapport;
	}

	public Double getAnte() {
		return ante;
	}

	public void setAnte(Double ante) {
		this.ante = ante;
	}

	public Boolean getIsWon() {
		return isWon;
	}

	public void setIsWon(Boolean isWon) {
		this.isWon = isWon;
	}



}
