package turfinfos2.model;

public class TestJson {
	

	    private String id;
	    private String name;
	    private String time;
//	    private String raceId;

	    
	    
public TestJson(String id, String name, String time, String raceId) {
			super();
			this.id = id;
			this.name = name;
			this.time = time;
//			this.raceId = raceId;

		}
	    
	    
	    
		public TestJson() {
	super();
}



		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}



		@Override
		public String toString() {
			return "TestJson [id=" + id + ", name=" + name + ", time=" + time + "]";
		}


}
