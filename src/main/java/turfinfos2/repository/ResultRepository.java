package turfinfos2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import turfinfos2.model.Resultat;
import turfinfos2.model.TurfInfos;

@Repository
public interface ResultRepository extends JpaRepository<Resultat, Long>{
	
	@Transactional
	List<Resultat> deleteByJour(String jour);

}
