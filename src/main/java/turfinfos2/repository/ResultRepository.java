package turfinfos2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import turfinfos2.model.Resultat;

@Repository
public interface ResultRepository extends JpaRepository<Resultat, Long>{
	
	@Query("SELECT r FROM Resultat r "
			+ "WHERE r.jour = :jour"
//			+ "AND ti.pourcVictChevalHippo != 0 "
//			+ "AND ti.pourcVictJockHippo != 0 "
//			+ "AND ti.pourcVictEntHippo != 0 "

			)
	List<Resultat> findAllByJour(String jour);
}

