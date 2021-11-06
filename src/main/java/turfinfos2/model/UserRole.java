package turfinfos2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@SequenceGenerator(
		  name = "USER_ROLE_SEQ_GENERATOR",
		  sequenceName = "USER_ROLE_SEQ",
		  initialValue = 1, allocationSize = 1)
@Table(name = "User_Role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "USER_ROLE_UK", columnNames = { "User_Id", "Role_Id" }) })
public class UserRole {
 
    public UserRole() {
		super();
	}

	public UserRole(Long id, UserAccount user, AppRole appRole) {
		super();
		this.id = id;
		this.user = user;
		this.appRole = appRole;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ROLE_SEQ_GENERATOR")
    @Column(name = "Id", nullable = false)
    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private UserAccount user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_Id", nullable = false)
    private AppRole appRole;

	public UserRole(UserAccount user, AppRole appRole) {
		super();
		this.user = user;
		this.appRole = appRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public AppRole getAppRole() {
		return appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}
 
     
}