package turfinfos2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
//@Data
@Entity
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "App_Role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_ROLE_UK", columnNames = "Role_Name") })

@SequenceGenerator(
		  name = "APP_ROLE_SEQ_GENERATOR",
		  sequenceName = "APP_ROLE_SEQ",
		  initialValue = 1, allocationSize = 1)

public class AppRole {
     
    public AppRole() {
		super();
	}

	public AppRole(Long roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_ROLE_SEQ_GENERATOR")
    @Column(name = "Role_Id", nullable = false)
    private Long roleId;
 
    @Column(name = "Role_Name", length = 30, nullable = false)
    private String roleName;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
     
}