package turfinfos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import turfinfos2.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findByUserName(String userName);
}
