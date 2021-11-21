package turfinfos2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import turfinfos2.model.CurrentOdds;
import turfinfos2.model.TurfInfos;

@Repository
public interface CurrentOddsRepository extends JpaRepository<CurrentOdds, Long>{

	@Query("SELECT co FROM CurrentOdds co "
			+ "WHERE co.jour = :jour AND co.R = :reunion AND co.C = :course "
//			+ "AND ti.pourcVictChevalHippo != 0 "
//			+ "AND ti.pourcVictJockHippo != 0 "
//			+ "AND ti.pourcVictEntHippo != 0 "

			)
	List<CurrentOdds> findAllByJourAndByReunionAndByCourse(String jour, String reunion, Integer course);
}
