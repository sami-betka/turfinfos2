package turfinfos2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import turfinfos2.model.TurfInfos;


public interface TurfInfosRepository extends JpaRepository<TurfInfos, Long> {

	@Transactional
	List<TurfInfos> deleteByJour(String jour);
	
	List<TurfInfos> findAllByOrderByPourcVictChevalHippoDesc();
	
//	TurfInfos findByNumcourse(Integer numcourse);
	
	TurfInfos findByNumeroAndNumcourse(Integer numero, Integer numcourse);
	
	Optional <TurfInfos> findByChronoAndNumcourse(Integer chrono, Integer numcourse);

	List<TurfInfos> findAllByNumcourse(Integer numcourse);
	
	@Query("SELECT ti FROM TurfInfos ti "
			+ "WHERE ti.jour = :jour"
//			+ "AND ti.pourcVictChevalHippo != 0 "
//			+ "AND ti.pourcVictJockHippo != 0 "
//			+ "AND ti.pourcVictEntHippo != 0 "

			)
	List<TurfInfos> findAllByJour(String jour);
	

	List<TurfInfos> findByIsFirstInPronoTrue();
	
	@Query("SELECT ti FROM TurfInfos ti "
			+ "WHERE ti.jour = :jour AND ti.R = :reunion "
//			+ "AND ti.pourcVictChevalHippo != 0 "
//			+ "AND ti.pourcVictJockHippo != 0 "
//			+ "AND ti.pourcVictEntHippo != 0 "

			)
	List<TurfInfos> findAllByJourAndByReunion(String jour, String reunion);
	
	@Query("SELECT ti FROM TurfInfos ti "
			+ "WHERE ti.jour = :jour AND ti.reunionstring = :reunionstring "
//			+ "AND ti.pourcVictChevalHippo != 0 "
//			+ "AND ti.pourcVictJockHippo != 0 "
//			+ "AND ti.pourcVictEntHippo != 0 "

			)
	List<TurfInfos> findAllByJourAndByReunionstring(String jour, String reunionstring);
	
	List<TurfInfos> findAllByNumcourseAndChrono(Integer numcourse, Integer chrono);

	List<TurfInfos> findAllByNumcourseAndTayProno(Integer numcourse, Integer tayprono);
	
	List<TurfInfos> findAllByNumcourseAndEntraineur(Integer numcourse, String entraineur);

}
