package turfinfos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turfinfos2.model.Resultat;

@Repository
public interface ResultRepository extends JpaRepository<Resultat, Long>{
	
	
}

