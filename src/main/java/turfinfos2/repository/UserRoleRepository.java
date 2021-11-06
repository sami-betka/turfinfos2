package turfinfos2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turfinfos2.model.UserAccount;
import turfinfos2.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	List<UserRole> findByUser(UserAccount user);
	
	List<UserRole> deleteByUser (UserAccount user);

}
