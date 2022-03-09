package turfinfos2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import turfinfos2.model.DeletedReunions;

@Repository
public interface DeletedReunionsRepository extends JpaRepository<DeletedReunions, Long>{
	
	@Query("SELECT dr FROM DeletedReunions dr "
			+ "WHERE dr.jour = :jour"
//			+ "AND ti.pourcVictChevalHippo != 0 "
//			+ "AND ti.pourcVictJockHippo != 0 "
//			+ "AND ti.pourcVictEntHippo != 0 "
			)
	List<DeletedReunions> findAllByJour(String jour);
}







