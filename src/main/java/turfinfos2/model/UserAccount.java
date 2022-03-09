package turfinfos2.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
//@Data
//@Getter
//@Setter
@SequenceGenerator(
		  name = "USER_ACCOUNT_SEQ_GENERATOR",
		  sequenceName = "USER_ACCOUNT_SEQ",
		  initialValue = 1, allocationSize = 1)
public class UserAccount {
	
	public UserAccount() {
		super();
	}

	public UserAccount(Long id, String userName, String encrytedPassword, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.encrytedPassword = encrytedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ACCOUNT_SEQ_GENERATOR")
	private Long id;
	
	@Column(name = "user_name", nullable = false, unique = true)
	private String userName;
	
	private String encrytedPassword;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
//	@OneToMany(mappedBy="gambler")
//	private List <HorseRacingBet> bets = new ArrayList<HorseRacingBet>();
//	
//	@OneToMany(mappedBy="gambler")
//	private List <Combi> combis = new ArrayList<Combi>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncrytedPassword() {
		return encrytedPassword;
	}

	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
