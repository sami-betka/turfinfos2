package turfinfos2.model;

public enum ResultType {

	SIMPLE_GAGNANT("SIMPLE_GAGNANT"),
	SIMPLE_PLACE("SIMPLE_PLACE"),
	COUPLE_GAGNANT("COUPLE_GAGNANT"),
	COUPLE_PLACE("COUPLE_PLACE"),
	COUPLE_ORDRE("COUPLE_ORDRE"),
	TRIO_ORDRE("TRIO_ORDRE"),
	SUPER_QUATRE("SUPER_QUATRE"),	
	DEUX_SUR_QUATRE("DEUX_SUR_QUATRE"),
	TIERCE("TIERCE"),
	QUARTE_PLUS("QUARTE_PLUS"),
	QUINTE_PLUS("QUINTE_PLUS"),
	MULTI("MULTI"),
	PICK5("PICK5"),
	TRIO("TRIO"),
	MINI_MULTI("MINI_MULTI");


	
	private String name;
	
	private ResultType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
